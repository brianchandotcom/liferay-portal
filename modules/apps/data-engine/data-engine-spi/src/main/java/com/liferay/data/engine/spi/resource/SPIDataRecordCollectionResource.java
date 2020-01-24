/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.data.engine.spi.resource;

import com.liferay.data.engine.field.type.util.LocalizedValueUtil;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordSetConstants;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.SearchUtil;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.Locale;
import java.util.Map;

import javax.validation.ValidationException;

/**
 * @author Jeyvison Nascimento
 */
public class SPIDataRecordCollectionResource<T> {

	public SPIDataRecordCollectionResource(
		DDLRecordSetLocalService ddlRecordSetLocalService,
		DDMStructureLocalService ddmStructureLocalService, Portal portal,
		ResourceLocalService resourceLocalService,
		UnsafeFunction<DDLRecordSet, T, Exception> transformUnsafeFunction) {

		_ddlRecordSetLocalService = ddlRecordSetLocalService;
		_ddmStructureLocalService = ddmStructureLocalService;
		_portal = portal;
		_resourceLocalService = resourceLocalService;
		_transformUnsafeFunction = transformUnsafeFunction;
	}

	public T addDataRecordCollection(
			long dataDefinitionId, String dataRecordCollectionKey,
			Map<String, Object> description, Map<String, Object> name)
		throws Exception {

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			dataDefinitionId);

		ServiceContext serviceContext = new ServiceContext();

		DDLRecordSet ddlRecordSet = _ddlRecordSetLocalService.addRecordSet(
			PrincipalThreadLocal.getUserId(), ddmStructure.getGroupId(),
			dataDefinitionId, dataRecordCollectionKey,
			LocalizedValueUtil.toLocaleStringMap(name),
			LocalizedValueUtil.toLocaleStringMap(description), 0,
			DDLRecordSetConstants.SCOPE_DATA_ENGINE, serviceContext);

		_resourceLocalService.addModelResources(
			ddmStructure.getCompanyId(), ddmStructure.getGroupId(),
			PrincipalThreadLocal.getUserId(), _getResourceName(ddlRecordSet),
			ddlRecordSet.getPrimaryKey(), serviceContext.getModelPermissions());

		return _transformUnsafeFunction.apply(ddlRecordSet);
	}

	public void deleteDataRecordCollection(Long dataRecordCollectionId)
		throws Exception {

		_ddlRecordSetLocalService.deleteRecordSet(dataRecordCollectionId);
	}

	public T getDataRecordCollection(long dataRecordCollectionId)
		throws Exception {

		return _transformUnsafeFunction.apply(
			_ddlRecordSetLocalService.getRecordSet(dataRecordCollectionId));
	}

	public Page<T> getDataRecordCollections(
			long dataDefinitionId, String keywords, Locale locale,
			Pagination pagination)
		throws Exception {

		if (pagination.getPageSize() > 250) {
			throw new ValidationException(
				LanguageUtil.format(
					locale, "page-size-is-greater-than-x", 250));
		}

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			dataDefinitionId);

		if (Validator.isNull(keywords)) {
			return Page.of(
				TransformUtil.transform(
					_ddlRecordSetLocalService.search(
						ddmStructure.getCompanyId(), ddmStructure.getGroupId(),
						keywords, DDLRecordSetConstants.SCOPE_DATA_ENGINE,
						pagination.getStartPosition(),
						pagination.getEndPosition(), null),
					_transformUnsafeFunction),
				pagination,
				_ddlRecordSetLocalService.searchCount(
					ddmStructure.getCompanyId(), ddmStructure.getGroupId(),
					keywords, DDLRecordSetConstants.SCOPE_DATA_ENGINE));
		}

		return SearchUtil.search(
			booleanQuery -> {
			},
			null, DDLRecordSet.class, keywords, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.setAttribute(Field.DESCRIPTION, keywords);
				searchContext.setAttribute(Field.NAME, keywords);
				searchContext.setAttribute(
					"DDMStructureId", ddmStructure.getStructureId());
				searchContext.setAttribute(
					"scope", DDLRecordSetConstants.SCOPE_DATA_ENGINE);
				searchContext.setCompanyId(ddmStructure.getCompanyId());
				searchContext.setGroupIds(
					new long[] {ddmStructure.getGroupId()});
			},
			document -> _transformUnsafeFunction.apply(
				_ddlRecordSetLocalService.getRecordSet(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))),
			null);
	}

	public T getSiteDataRecordCollection(
			String dataRecordCollectionKey, long groupId)
		throws Exception {

		return _transformUnsafeFunction.apply(
			_ddlRecordSetLocalService.getRecordSet(
				groupId, dataRecordCollectionKey));
	}

	public T updateDataRecordCollection(
			long dataRecordCollectionId, Map<String, Object> description,
			Map<String, Object> name)
		throws Exception {

		DDLRecordSet ddlRecordSet = _ddlRecordSetLocalService.getRecordSet(
			dataRecordCollectionId);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setUserId(PrincipalThreadLocal.getUserId());

		return _transformUnsafeFunction.apply(
			_ddlRecordSetLocalService.updateRecordSet(
				dataRecordCollectionId, ddlRecordSet.getDDMStructureId(),
				LocalizedValueUtil.toLocaleStringMap(name),
				LocalizedValueUtil.toLocaleStringMap(description), 0,
				serviceContext));
	}

	private String _getResourceName(DDLRecordSet ddlRecordSet)
		throws PortalException {

		DDMStructure ddmStructure = ddlRecordSet.getDDMStructure();

		return ResourceActionsUtil.getCompositeModelName(
			_portal.getClassName(ddmStructure.getClassNameId()),
			DDLRecordSet.class.getName());
	}

	private final DDLRecordSetLocalService _ddlRecordSetLocalService;
	private final DDMStructureLocalService _ddmStructureLocalService;
	private final Portal _portal;
	private final ResourceLocalService _resourceLocalService;
	private final UnsafeFunction<DDLRecordSet, T, Exception>
		_transformUnsafeFunction;

}