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

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import com.liferay.data.engine.content.type.DataDefinitionContentType;
import com.liferay.data.engine.field.type.util.LocalizedValueUtil;
import com.liferay.data.engine.rest.dto.v2_0.DataLayout;
import com.liferay.data.engine.rest.internal.constants.DataActionKeys;
import com.liferay.data.engine.rest.internal.content.type.DataDefinitionContentTypeTracker;
import com.liferay.data.engine.rest.internal.dto.v2_0.util.DataLayoutUtil;
import com.liferay.data.engine.rest.internal.odata.entity.v2_0.DataLayoutEntityModel;
import com.liferay.data.engine.rest.internal.security.permission.resource.DataDefinitionModelResourcePermission;
import com.liferay.data.engine.rest.resource.v2_0.DataLayoutResource;
import com.liferay.data.engine.service.DEDataDefinitionFieldLinkLocalService;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializer;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalService;
import com.liferay.dynamic.data.mapping.util.comparator.StructureLayoutCreateDateComparator;
import com.liferay.dynamic.data.mapping.util.comparator.StructureLayoutModifiedDateComparator;
import com.liferay.dynamic.data.mapping.util.comparator.StructureLayoutNameComparator;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.SearchUtil;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

		_delete(ddmStructure, ddmStructureLayout);
	}

	@Override
	public void deleteDataLayoutsDataDefinition(Long dataDefinitionId)
		throws Exception {

		DDMStructure ddmStructure = _ddmStructureLocalService.getDDMStructure(
			dataDefinitionId);

		_dataDefinitionModelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(),
			ddmStructure.getStructureId(), ActionKeys.DELETE);

		List<DDMStructureVersion> ddmStructureVersions =
			_ddmStructureVersionLocalService.getStructureVersions(
				dataDefinitionId);

		for (DDMStructureVersion ddmStructureVersion : ddmStructureVersions) {
			List<DDMStructureLayout> ddmStructureLayouts =
				_ddmStructureLayoutLocalService.getStructureLayouts(
					ddmStructure.getGroupId(), ddmStructure.getClassNameId(),
					ddmStructureVersion.getStructureVersionId());

			for (DDMStructureLayout ddmStructureLayout : ddmStructureLayouts) {
				_delete(ddmStructure, ddmStructureLayout);
			}
		}
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

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			dataDefinitionId);

		if (Validator.isNull(keywords)) {
			return Page.of(
				TransformUtil.transform(
					_ddmStructureLayoutLocalService.getStructureLayouts(
						ddmStructure.getGroupId(),
						ddmStructure.getClassNameId(),
						_getDDMStructureVersionId(dataDefinitionId),
						pagination.getStartPosition(),
						pagination.getEndPosition(),
						_toOrderByComparator(
							(Sort)ArrayUtil.getValue(sorts, 0))),
					DataLayoutUtil::toDataLayout),
				pagination,
				_ddmStructureLayoutLocalService.getStructureLayoutsCount(
					ddmStructure.getGroupId(), ddmStructure.getClassNameId(),
					_getDDMStructureVersionId(dataDefinitionId)));
		}

		return SearchUtil.search(
			booleanQuery -> {
			},
			null, DDMStructureLayout.class, keywords, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.setAttribute(
					Field.CLASS_NAME_ID, ddmStructure.getClassNameId());
				searchContext.setAttribute(Field.DESCRIPTION, keywords);
				searchContext.setAttribute(Field.NAME, keywords);
				searchContext.setAttribute(
					"structureVersionId",
					_getDDMStructureVersionId(dataDefinitionId));
				searchContext.setCompanyId(ddmStructure.getCompanyId());
				searchContext.setGroupIds(
					new long[] {ddmStructure.getGroupId()});
			},
			document -> DataLayoutUtil.toDataLayout(
				_ddmStructureLayoutLocalService.getStructureLayout(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))),
			sorts);
	}

	@Override
	public DataLayout getDataLayout(Long dataLayoutId) throws Exception {
		DDMStructureLayout ddmStructureLayout =
			_ddmStructureLayoutLocalService.getStructureLayout(dataLayoutId);

		_dataDefinitionModelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(),
			ddmStructureLayout.getDDMStructureId(), ActionKeys.VIEW);

		return DataLayoutUtil.toDataLayout(ddmStructureLayout);
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

		return DataLayoutUtil.toDataLayout(ddmStructureLayout);
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

		String content = DataLayoutUtil.serialize(
			dataLayout, _ddmFormLayoutSerializer);

		_validate(content, dataLayout.getName());

		DDMStructureLayout ddmStructureLayout =
			_ddmStructureLayoutLocalService.addStructureLayout(
				contextUser.getUserId(), ddmStructure.getGroupId(),
				ddmStructure.getClassNameId(),
				Optional.ofNullable(
					dataLayout.getDataLayoutKey()
				).orElse(
					StringUtil.randomString(6)
				),
				_getDDMStructureVersionId(dataDefinitionId),
				LocalizedValueUtil.toLocaleStringMap(dataLayout.getName()),
				LocalizedValueUtil.toLocaleStringMap(
					dataLayout.getDescription()),
				content, new ServiceContext());

		_addDataDefinitionFieldLinks(
			ddmStructure.getClassNameId(), dataDefinitionId,
			ddmStructureLayout.getStructureLayoutId(), _getFieldNames(content),
			ddmStructureLayout.getGroupId());

		return DataLayoutUtil.toDataLayout(ddmStructureLayout);
	}

	@Override
	public DataLayout putDataLayout(Long dataLayoutId, DataLayout dataLayout)
		throws Exception {

		DDMStructureLayout ddmStructureLayout =
			_ddmStructureLayoutLocalService.getStructureLayout(dataLayoutId);

		_dataDefinitionModelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(),
			ddmStructureLayout.getDDMStructureId(), ActionKeys.UPDATE);

		String content = DataLayoutUtil.serialize(
			dataLayout, _ddmFormLayoutSerializer);

		_validate(content, dataLayout.getName());

		DDMStructure ddmStructure = ddmStructureLayout.getDDMStructure();

		ddmStructureLayout =
			_ddmStructureLayoutLocalService.updateStructureLayout(
				dataLayoutId,
				_getDDMStructureVersionId(ddmStructure.getStructureId()),
				LocalizedValueUtil.toLocaleStringMap(dataLayout.getName()),
				LocalizedValueUtil.toLocaleStringMap(
					dataLayout.getDescription()),
				content, new ServiceContext());

		_deDataDefinitionFieldLinkLocalService.deleteDEDataDefinitionFieldLinks(
			ddmStructure.getClassNameId(), dataLayoutId);

		_addDataDefinitionFieldLinks(
			ddmStructure.getClassNameId(), ddmStructure.getStructureId(),
			dataLayoutId, _getFieldNames(content), ddmStructure.getGroupId());

		return DataLayoutUtil.toDataLayout(ddmStructureLayout);
	}

	private void _addDataDefinitionFieldLinks(
			long classNameId, long dataDefinitionId, long dataLayoutId,
			List<String> fieldNames, long siteId)
		throws PortalException {

		for (String fieldName : fieldNames) {
			_deDataDefinitionFieldLinkLocalService.addDEDataDefinitionFieldLink(
				siteId, classNameId, dataLayoutId, dataDefinitionId, fieldName);
		}
	}

	private void _delete(
			DDMStructure ddmStructure, DDMStructureLayout ddmStructureLayout)
		throws Exception {

		_ddmStructureLayoutLocalService.deleteDDMStructureLayout(
			ddmStructureLayout);

		_deDataDefinitionFieldLinkLocalService.deleteDEDataDefinitionFieldLinks(
			ddmStructure.getClassNameId(),
			ddmStructureLayout.getStructureLayoutId());
	}

	private long _getDDMStructureVersionId(long deDataDefinitionId)
		throws PortalException {

		DDMStructureVersion ddmStructureVersion =
			_ddmStructureVersionLocalService.getLatestStructureVersion(
				deDataDefinitionId);

		return ddmStructureVersion.getStructureVersionId();
	}

	private List<String> _getFieldNames(String content) {
		DocumentContext documentContext = JsonPath.parse(content);

		return documentContext.read(
			"$[\"pages\"][*][\"rows\"][*][\"columns\"][*][\"fieldNames\"][*]");
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

	private void _validate(String content, Map<String, Object> name) {
		if (MapUtil.isEmpty(name)) {
			throw new ValidationException("Name is required");
		}

		name.forEach(
			(locale, localizedName) -> {
				if (Validator.isNull(localizedName)) {
					throw new ValidationException("Name is required");
				}
			});

		List<String> fieldNames = _getFieldNames(content);

		if (ListUtil.isEmpty(fieldNames)) {
			throw new ValidationException("Layout is empty");
		}
	}

	private static final EntityModel _entityModel = new DataLayoutEntityModel();

	@Reference
	private DataDefinitionContentTypeTracker _dataDefinitionContentTypeTracker;

	@Reference
	private DataDefinitionModelResourcePermission
		_dataDefinitionModelResourcePermission;

	@Reference(target = "(ddm.form.layout.serializer.type=json)")
	private DDMFormLayoutSerializer _ddmFormLayoutSerializer;

	@Reference
	private DDMStructureLayoutLocalService _ddmStructureLayoutLocalService;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private DDMStructureVersionLocalService _ddmStructureVersionLocalService;

	@Reference
	private DEDataDefinitionFieldLinkLocalService
		_deDataDefinitionFieldLinkLocalService;

}