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

package com.liferay.data.engine.rest.internal.resource.common;

import com.liferay.data.engine.field.type.util.LocalizedValueUtil;
import com.liferay.data.engine.rest.internal.constants.DataActionKeys;
import com.liferay.data.engine.rest.internal.constants.DataRecordCollectionConstants;
import com.liferay.data.engine.rest.internal.model.InternalDataRecordCollection;
import com.liferay.data.engine.rest.internal.resource.v1_0.util.DataEnginePermissionUtil;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordSetConstants;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.SearchUtil;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.ValidationException;

/**
 * @author Leonardo Barros
 */
public class CommonDataRecordCollectionResource<T> {

	public CommonDataRecordCollectionResource(
		DDLRecordSetLocalService ddlRecordSetLocalService,
		DDMStructureLocalService ddmStructureLocalService,
		GroupLocalService groupLocalService,
		ModelResourcePermission<InternalDataRecordCollection>
			modelResourcePermission,
		ResourceLocalService resourceLocalService,
		ResourcePermissionLocalService resourcePermissionLocalService,
		RoleLocalService roleLocalService,
		UnsafeFunction<DDLRecordSet, T, Exception> transformUnsafeFunction) {

		_ddlRecordSetLocalService = ddlRecordSetLocalService;
		_ddmStructureLocalService = ddmStructureLocalService;
		_groupLocalService = groupLocalService;
		_modelResourcePermission = modelResourcePermission;
		_resourceLocalService = resourceLocalService;
		_resourcePermissionLocalService = resourcePermissionLocalService;
		_roleLocalService = roleLocalService;
		_transformUnsafeFunction = transformUnsafeFunction;
	}

	public void deleteDataRecordCollection(Long dataRecordCollectionId)
		throws Exception {

		_modelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(),
			dataRecordCollectionId, ActionKeys.DELETE);

		_ddlRecordSetLocalService.deleteRecordSet(dataRecordCollectionId);
	}

	public Page<T> getDataDefinitionDataRecordCollectionsPage(
			AcceptLanguage acceptLanguage, Company company,
			Long dataDefinitionId, String keywords, Pagination pagination)
		throws Exception {

		if (pagination.getPageSize() > 250) {
			throw new ValidationException(
				LanguageUtil.format(
					acceptLanguage.getPreferredLocale(),
					"page-size-is-greater-than-x", 250));
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
				searchContext.setCompanyId(company.getCompanyId());
				searchContext.setGroupIds(
					new long[] {ddmStructure.getGroupId()});
			},
			document -> _transformUnsafeFunction.apply(
				_ddlRecordSetLocalService.getRecordSet(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))),
			null);
	}

	public T getDataRecordCollection(Long dataRecordCollectionId)
		throws Exception {

		_modelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(),
			dataRecordCollectionId, ActionKeys.VIEW);

		return _transformUnsafeFunction.apply(
			_ddlRecordSetLocalService.getRecordSet(dataRecordCollectionId));
	}

	public T getSiteDataRecordCollection(
			String dataRecordCollectionKey, Long siteId)
		throws Exception {

		return _transformUnsafeFunction.apply(
			_ddlRecordSetLocalService.getRecordSet(
				siteId, dataRecordCollectionKey));
	}

	public Page<T> getSiteDataRecordCollectionsPage(
			AcceptLanguage acceptLanguage, Company company, String keywords,
			Pagination pagination, Long siteId)
		throws Exception {

		if (pagination.getPageSize() > 250) {
			throw new ValidationException(
				LanguageUtil.format(
					acceptLanguage.getPreferredLocale(),
					"page-size-is-greater-than-x", 250));
		}

		if (Validator.isNull(keywords)) {
			return Page.of(
				TransformUtil.transform(
					_ddlRecordSetLocalService.search(
						company.getCompanyId(), siteId, keywords,
						DDLRecordSetConstants.SCOPE_DATA_ENGINE,
						pagination.getStartPosition(),
						pagination.getEndPosition(), null),
					_transformUnsafeFunction),
				pagination,
				_ddlRecordSetLocalService.searchCount(
					company.getCompanyId(), siteId, keywords,
					DDLRecordSetConstants.SCOPE_DATA_ENGINE));
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
					"scope", DDLRecordSetConstants.SCOPE_DATA_ENGINE);
				searchContext.setCompanyId(company.getCompanyId());
				searchContext.setGroupIds(new long[] {siteId});
			},
			document -> _transformUnsafeFunction.apply(
				_ddlRecordSetLocalService.getRecordSet(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))),
			null);
	}

	public T postDataDefinitionDataRecordCollection(
			Company company, Long dataDefinitionId,
			String dataRecordCollectionKey, Map<String, Object> description,
			Map<String, Object> name)
		throws Exception {

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			dataDefinitionId);

		DataEnginePermissionUtil.checkPermission(
			DataActionKeys.ADD_DATA_RECORD_COLLECTION, _groupLocalService,
			ddmStructure.getGroupId());

		ServiceContext serviceContext = new ServiceContext();

		DDLRecordSet ddlRecordSet = _ddlRecordSetLocalService.addRecordSet(
			PrincipalThreadLocal.getUserId(), ddmStructure.getGroupId(),
			dataDefinitionId, dataRecordCollectionKey,
			LocalizedValueUtil.toLocaleStringMap(name),
			LocalizedValueUtil.toLocaleStringMap(description), 0,
			DDLRecordSetConstants.SCOPE_DATA_ENGINE, serviceContext);

		_resourceLocalService.addModelResources(
			company.getCompanyId(), ddmStructure.getGroupId(),
			PrincipalThreadLocal.getUserId(),
			InternalDataRecordCollection.class.getName(),
			ddlRecordSet.getPrimaryKey(), serviceContext.getModelPermissions());

		return _transformUnsafeFunction.apply(ddlRecordSet);
	}

	public void postDataRecordCollectionDataRecordCollectionPermissions(
			Company company, Long dataRecordCollectionId,
			boolean hasAddDataRecordPermission, boolean hasDeletePermission,
			boolean hasDeleteDataRecordPermission, boolean hasUpdatePermission,
			boolean hasUpdateDataRecordPermission, boolean hasViewPermission,
			boolean hasViewDataRecordPermission,
			boolean hasExportDataRecordPermission, String operation,
			String[] roleNames)
		throws Exception {

		DDLRecordSet ddlRecordSet = _ddlRecordSetLocalService.getRecordSet(
			dataRecordCollectionId);

		DataEnginePermissionUtil.checkOperationPermission(
			_groupLocalService, operation, ddlRecordSet.getGroupId());

		List<String> actionIds = new ArrayList<>();

		if (hasAddDataRecordPermission) {
			actionIds.add(DataActionKeys.ADD_DATA_RECORD);
		}

		if (hasDeletePermission) {
			actionIds.add(ActionKeys.DELETE);
		}

		if (hasDeleteDataRecordPermission) {
			actionIds.add(DataActionKeys.DELETE_DATA_RECORD);
		}

		if (hasExportDataRecordPermission) {
			actionIds.add(DataActionKeys.EXPORT_DATA_RECORDS);
		}

		if (hasUpdatePermission) {
			actionIds.add(ActionKeys.UPDATE);
		}

		if (hasUpdateDataRecordPermission) {
			actionIds.add(DataActionKeys.UPDATE_DATA_RECORD);
		}

		if (hasViewPermission) {
			actionIds.add(ActionKeys.VIEW);
		}

		if (hasViewDataRecordPermission) {
			actionIds.add(DataActionKeys.VIEW_DATA_RECORD);
		}

		if (actionIds.isEmpty()) {
			return;
		}

		DataEnginePermissionUtil.persistModelPermission(
			actionIds, company, dataRecordCollectionId, operation,
			DataRecordCollectionConstants.RESOURCE_NAME,
			_resourcePermissionLocalService, _roleLocalService, roleNames,
			ddlRecordSet.getGroupId());
	}

	public void postSiteDataRecordCollectionPermissions(
			Company company, boolean hasAddDataRecordCollectionPermission,
			boolean hasDefinePermissionsPermission, String operation,
			String[] roleNames, Long siteId)
		throws Exception {

		DataEnginePermissionUtil.checkOperationPermission(
			_groupLocalService, operation, siteId);

		List<String> actionIds = new ArrayList<>();

		if (hasAddDataRecordCollectionPermission) {
			actionIds.add(DataActionKeys.ADD_DATA_RECORD_COLLECTION);
		}

		if (hasDefinePermissionsPermission) {
			actionIds.add(DataActionKeys.DEFINE_PERMISSIONS);
		}

		if (actionIds.isEmpty()) {
			return;
		}

		DataEnginePermissionUtil.persistPermission(
			actionIds, company, operation, _resourcePermissionLocalService,
			_roleLocalService, roleNames);
	}

	public T putDataRecordCollection(
			Long dataRecordCollectionId, Map<String, Object> description,
			Map<String, Object> name)
		throws Exception {

		_modelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(),
			dataRecordCollectionId, ActionKeys.UPDATE);

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

	private final DDLRecordSetLocalService _ddlRecordSetLocalService;
	private final DDMStructureLocalService _ddmStructureLocalService;
	private final GroupLocalService _groupLocalService;
	private final ModelResourcePermission<InternalDataRecordCollection>
		_modelResourcePermission;
	private final ResourceLocalService _resourceLocalService;
	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;
	private final RoleLocalService _roleLocalService;
	private final UnsafeFunction<DDLRecordSet, T, Exception>
		_transformUnsafeFunction;

}