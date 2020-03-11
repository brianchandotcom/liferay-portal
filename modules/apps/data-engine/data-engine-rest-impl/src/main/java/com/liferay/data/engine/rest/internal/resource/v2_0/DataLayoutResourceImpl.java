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

package com.liferay.data.engine.rest.internal.resource.v2_0;

import com.liferay.data.engine.content.type.DataDefinitionContentType;
import com.liferay.data.engine.manager.DataLayoutManager;
import com.liferay.data.engine.rest.dto.v2_0.DataLayout;
import com.liferay.data.engine.rest.internal.constants.DataActionKeys;
import com.liferay.data.engine.rest.internal.content.type.DataDefinitionContentTypeTracker;
import com.liferay.data.engine.rest.internal.dto.v2_0.util.DataLayoutUtil;
import com.liferay.data.engine.rest.internal.odata.entity.v2_0.DataLayoutEntityModel;
import com.liferay.data.engine.rest.internal.security.permission.resource.DataDefinitionModelResourcePermission;
import com.liferay.data.engine.rest.resource.v2_0.DataLayoutResource;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.util.comparator.StructureLayoutCreateDateComparator;
import com.liferay.dynamic.data.mapping.util.comparator.StructureLayoutModifiedDateComparator;
import com.liferay.dynamic.data.mapping.util.comparator.StructureLayoutNameComparator;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.ValidationException;

import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Jeyvison Nascimento
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v2_0/data-layout.properties",
	scope = ServiceScope.PROTOTYPE, service = DataLayoutResource.class
)
public class DataLayoutResourceImpl
	extends BaseDataLayoutResourceImpl implements EntityModelResource {

	@Override
	public void deleteDataLayout(Long dataLayoutId) throws Exception {
		DDMStructureLayout ddmStructureLayout =
			_ddmStructureLayoutLocalService.getStructureLayout(dataLayoutId);

		DDMStructure ddmStructure = ddmStructureLayout.getDDMStructure();

		_dataDefinitionModelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(),
			ddmStructure.getStructureId(), ActionKeys.DELETE);

		_dataLayoutManager.deleteDataLayout(dataLayoutId);
	}

	@Override
	public Page<DataLayout> getDataDefinitionDataLayoutsPage(
			Long dataDefinitionId, String keywords, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		if (pagination.getPageSize() > 250) {
			throw new ValidationException(
				LanguageUtil.format(
					contextAcceptLanguage.getPreferredLocale(),
					"page-size-is-greater-than-x", 250));
		}

		if (ArrayUtil.isEmpty(sorts)) {
			sorts = new Sort[] {
				new Sort(
					Field.getSortableFieldName(Field.MODIFIED_DATE),
					Sort.STRING_TYPE, true)
			};
		}

		List<com.liferay.data.engine.DataLayout> dataLayouts =
			_dataLayoutManager.getDataLayouts(
				dataDefinitionId, pagination.getEndPosition(), keywords,
				_toOrderByComparator(sorts[0]), pagination.getStartPosition());

		Stream<com.liferay.data.engine.DataLayout> dataLayoutStream =
			dataLayouts.stream();

		return Page.of(
			dataLayoutStream.map(
				DataLayoutUtil::toDataLayout
			).collect(
				Collectors.toList()
			),
			pagination,
			_dataLayoutManager.getDataLayoutsCount(dataDefinitionId, keywords));
	}

	@Override
	public DataLayout getDataLayout(Long dataLayoutId) throws Exception {
		DDMStructureLayout ddmStructureLayout =
			_ddmStructureLayoutLocalService.getStructureLayout(dataLayoutId);

		_dataDefinitionModelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(),
			ddmStructureLayout.getDDMStructureId(), ActionKeys.VIEW);

		return DataLayoutUtil.toDataLayout(
			_dataLayoutManager.getDataLayout(dataLayoutId));
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap)
		throws Exception {

		return _entityModel;
	}

	@Override
	public DataLayout getSiteDataLayoutByContentTypeByDataLayoutKey(
			Long siteId, String contentType, String dataLayoutKey)
		throws Exception {

		DataDefinitionContentType dataDefinitionContentType =
			_dataDefinitionContentTypeTracker.getDataDefinitionContentType(
				contentType);

		DDMStructureLayout ddmStructureLayout =
			_ddmStructureLayoutLocalService.getStructureLayout(
				siteId, dataDefinitionContentType.getClassNameId(),
				dataLayoutKey);

		_dataDefinitionModelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(),
			ddmStructureLayout.getDDMStructureId(), ActionKeys.VIEW);

		return DataLayoutUtil.toDataLayout(
			_dataLayoutManager.getDataLayout(
				dataDefinitionContentType.getClassNameId(), dataLayoutKey,
				siteId));
	}

	@Override
	public DataLayout postDataDefinitionDataLayout(
			Long dataDefinitionId, DataLayout dataLayout)
		throws Exception {

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			dataDefinitionId);

		_dataDefinitionModelResourcePermission.checkPortletPermission(
			PermissionThreadLocal.getPermissionChecker(), ddmStructure,
			DataActionKeys.ADD_DATA_DEFINITION);

		dataLayout.setDataDefinitionId(dataDefinitionId);

		return DataLayoutUtil.toDataLayout(
			_dataLayoutManager.addDataLayout(
				DataLayoutUtil.toDataLayout(dataLayout)));
	}

	@Override
	public DataLayout putDataLayout(Long dataLayoutId, DataLayout dataLayout)
		throws Exception {

		DDMStructureLayout ddmStructureLayout =
			_ddmStructureLayoutLocalService.getStructureLayout(dataLayoutId);

		_dataDefinitionModelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(),
			ddmStructureLayout.getDDMStructureId(), ActionKeys.UPDATE);

		dataLayout.setId(dataLayoutId);

		return DataLayoutUtil.toDataLayout(
			_dataLayoutManager.updateDataLayout(
				DataLayoutUtil.toDataLayout(dataLayout)));
	}

	private OrderByComparator<DDMStructureLayout> _toOrderByComparator(
		Sort sort) {

		boolean ascending = !sort.isReverse();

		String sortFieldName = sort.getFieldName();

		if (StringUtil.startsWith(sortFieldName, "createDate")) {
			return new StructureLayoutCreateDateComparator(ascending);
		}
		else if (StringUtil.startsWith(sortFieldName, "localized_name")) {
			return new StructureLayoutNameComparator(ascending);
		}

		return new StructureLayoutModifiedDateComparator(ascending);
	}

	private static final EntityModel _entityModel = new DataLayoutEntityModel();

	@Reference
	private DataDefinitionContentTypeTracker _dataDefinitionContentTypeTracker;

	@Reference
	private DataDefinitionModelResourcePermission
		_dataDefinitionModelResourcePermission;

	@Reference
	private DataLayoutManager _dataLayoutManager;

	@Reference
	private DDMStructureLayoutLocalService _ddmStructureLayoutLocalService;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

}