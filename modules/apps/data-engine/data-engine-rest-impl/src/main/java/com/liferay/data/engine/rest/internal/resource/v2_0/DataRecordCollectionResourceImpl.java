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

import com.liferay.data.engine.rest.dto.v2_0.DataRecordCollection;
import com.liferay.data.engine.rest.internal.constants.DataActionKeys;
import com.liferay.data.engine.rest.internal.dto.v2_0.util.DataRecordCollectionUtil;
import com.liferay.data.engine.rest.internal.resource.util.DataEnginePermissionUtil;
import com.liferay.data.engine.rest.internal.security.permission.resource.DataRecordCollectionModelResourcePermission;
import com.liferay.data.engine.rest.resource.v2_0.DataRecordCollectionResource;
import com.liferay.data.engine.spi.resource.SPIDataRecordCollectionResource;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Jeyvison Nascimento
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v2_0/data-record-collection.properties",
	scope = ServiceScope.PROTOTYPE, service = DataRecordCollectionResource.class
)
public class DataRecordCollectionResourceImpl
	extends BaseDataRecordCollectionResourceImpl {

	@Override
	public void deleteDataRecordCollection(Long dataRecordCollectionId)
		throws Exception {

		_dataRecordCollectionModelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(),
			dataRecordCollectionId, ActionKeys.DELETE);

		SPIDataRecordCollectionResource<DataRecordCollection>
			spiDataRecordCollectionResource =
				_getSPIDataRecordCollectionResource();

		spiDataRecordCollectionResource.deleteDataRecordCollection(
			dataRecordCollectionId);
	}

	@Override
	public DataRecordCollection getDataDefinitionDataRecordCollection(
			Long dataDefinitionId)
		throws Exception {

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			dataDefinitionId);

		return DataRecordCollectionUtil.toDataRecordCollection(
			_ddlRecordSetLocalService.getRecordSet(
				ddmStructure.getGroupId(), ddmStructure.getStructureKey()));
	}

	@Override
	public Page<DataRecordCollection>
			getDataDefinitionDataRecordCollectionsPage(
				Long dataDefinitionId, String keywords, Pagination pagination)
		throws Exception {

		SPIDataRecordCollectionResource<DataRecordCollection>
			spiDataRecordCollectionResource =
				_getSPIDataRecordCollectionResource();

		return spiDataRecordCollectionResource.getDataRecordCollections(
			dataDefinitionId, keywords,
			contextAcceptLanguage.getPreferredLocale(), pagination);
	}

	@Override
	public DataRecordCollection getDataRecordCollection(
			Long dataRecordCollectionId)
		throws Exception {

		_dataRecordCollectionModelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(),
			dataRecordCollectionId, ActionKeys.VIEW);

		SPIDataRecordCollectionResource<DataRecordCollection>
			spiDataRecordCollectionResource =
				_getSPIDataRecordCollectionResource();

		return spiDataRecordCollectionResource.getDataRecordCollection(
			dataRecordCollectionId);
	}

	@Override
	public String getDataRecordCollectionPermissionByCurrentUser(
			Long dataRecordCollectionId)
		throws Exception {

		JSONArray actionIdsJSONArray = JSONFactoryUtil.createJSONArray();

		DDLRecordSet ddlRecordSet = _ddlRecordSetLocalService.getRecordSet(
			dataRecordCollectionId);

		String resourceName = _getResourceName(ddlRecordSet);

		List<ResourceAction> resourceActions =
			resourceActionLocalService.getResourceActions(resourceName);

		for (ResourceAction resourceAction : resourceActions) {
			PermissionChecker permissionChecker =
				PermissionThreadLocal.getPermissionChecker();

			if (permissionChecker.hasPermission(
					ddlRecordSet.getGroupId(), resourceName,
					dataRecordCollectionId, resourceAction.getActionId())) {

				actionIdsJSONArray.put(resourceAction.getActionId());
			}
		}

		return actionIdsJSONArray.toString();
	}

	@Override
	public DataRecordCollection
			getSiteDataRecordCollectionByDataRecordCollectionKey(
				Long siteId, String dataRecordCollectionKey)
		throws Exception {

		SPIDataRecordCollectionResource<DataRecordCollection>
			spiDataRecordCollectionResource =
				_getSPIDataRecordCollectionResource();

		return spiDataRecordCollectionResource.getSiteDataRecordCollection(
			dataRecordCollectionKey, siteId);
	}

	@Override
	public DataRecordCollection postDataDefinitionDataRecordCollection(
			Long dataDefinitionId, DataRecordCollection dataRecordCollection)
		throws Exception {

		DDMStructure ddmStructure = _ddmStructureLocalService.getDDMStructure(
			dataDefinitionId);

		DataEnginePermissionUtil.checkPermission(
			DataActionKeys.ADD_DATA_RECORD_COLLECTION, groupLocalService,
			ddmStructure.getGroupId());

		String dataRecordCollectionKey =
			dataRecordCollection.getDataRecordCollectionKey();

		if (Validator.isNull(dataRecordCollectionKey)) {
			dataRecordCollectionKey = ddmStructure.getStructureKey();
		}

		SPIDataRecordCollectionResource<DataRecordCollection>
			spiDataRecordCollectionResource =
				_getSPIDataRecordCollectionResource();

		return spiDataRecordCollectionResource.addDataRecordCollection(
			dataDefinitionId, dataRecordCollectionKey,
			dataRecordCollection.getDescription(),
			dataRecordCollection.getName());
	}

	@Override
	public DataRecordCollection putDataRecordCollection(
			Long dataRecordCollectionId,
			DataRecordCollection dataRecordCollection)
		throws Exception {

		_dataRecordCollectionModelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(),
			dataRecordCollectionId, ActionKeys.UPDATE);

		SPIDataRecordCollectionResource<DataRecordCollection>
			spiDataRecordCollectionResource =
				_getSPIDataRecordCollectionResource();

		return spiDataRecordCollectionResource.updateDataRecordCollection(
			dataRecordCollectionId, dataRecordCollection.getDescription(),
			dataRecordCollection.getName());
	}

	@Override
	protected Long getPermissionCheckerGroupId(Object id) throws Exception {
		DDLRecordSet ddlRecordSet = _ddlRecordSetLocalService.getRecordSet(
			(long)id);

		return ddlRecordSet.getGroupId();
	}

	@Override
	protected String getPermissionCheckerResourceName(Object id)
		throws Exception {

		DDLRecordSet ddlRecordSet = _ddlRecordSetLocalService.getRecordSet(
			(long)id);

		return _getResourceName(ddlRecordSet);
	}

	private String _getResourceName(DDLRecordSet ddlRecordSet)
		throws PortalException {

		DDMStructure ddmStructure = ddlRecordSet.getDDMStructure();

		return ResourceActionsUtil.getCompositeModelName(
			_portal.getClassName(ddmStructure.getClassNameId()),
			DDLRecordSet.class.getName());
	}

	private SPIDataRecordCollectionResource<DataRecordCollection>
		_getSPIDataRecordCollectionResource() {

		return new SPIDataRecordCollectionResource<>(
			_ddlRecordSetLocalService, _ddmStructureLocalService, _portal,
			_resourceLocalService,
			DataRecordCollectionUtil::toDataRecordCollection);
	}

	@Reference
	private DataRecordCollectionModelResourcePermission
		_dataRecordCollectionModelResourcePermission;

	@Reference
	private DDLRecordSetLocalService _ddlRecordSetLocalService;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private ResourceLocalService _resourceLocalService;

}