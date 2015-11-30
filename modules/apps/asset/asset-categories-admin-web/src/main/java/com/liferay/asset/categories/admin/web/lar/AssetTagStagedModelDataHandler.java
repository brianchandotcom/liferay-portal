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

package com.liferay.asset.categories.admin.web.lar;

import com.liferay.exportimport.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.service.AssetTagLocalService;
import com.liferay.portlet.exportimport.lar.ExportImportPathUtil;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandler;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Kocsis
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class AssetTagStagedModelDataHandler
	extends BaseStagedModelDataHandler<AssetTag> {

	public static final String[] CLASS_NAMES = {AssetTag.class.getName()};

	@Override
	public void deleteStagedModel(AssetTag stagedAssetTag)
		throws PortalException {

		_assetTagLocalService.deleteTag(stagedAssetTag);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		AssetTag assetTag = fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (assetTag != null) {
			deleteStagedModel(assetTag);
		}
	}

	@Override
	public AssetTag fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		AssetTag assetTag = _assetTagLocalService.fetchAssetTagByUuidAndGroupId(
			uuid, groupId);

		if (assetTag == null) {
			return null;
		}

		return assetTag;
	}

	@Override
	public List<AssetTag> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _assetTagLocalService.getAssetTagsByUuidAndCompanyId(
			uuid, companyId);
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(AssetTag assetTag) {
		return assetTag.getName();
	}

	protected ServiceContext createServiceContext(
		PortletDataContext portletDataContext, AssetTag assetTag) {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setCreateDate(assetTag.getCreateDate());
		serviceContext.setModifiedDate(assetTag.getModifiedDate());
		serviceContext.setScopeGroupId(portletDataContext.getScopeGroupId());

		return serviceContext;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, AssetTag assetTag)
		throws Exception {

		Element assetTagElement = portletDataContext.getExportDataElement(
			assetTag);

		portletDataContext.addClassedModel(
			assetTagElement, ExportImportPathUtil.getModelPath(assetTag),
			assetTag);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, AssetTag assetTag)
		throws Exception {

		long userId = portletDataContext.getUserId(assetTag.getUserUuid());

		ServiceContext serviceContext = createServiceContext(
			portletDataContext, assetTag);

		AssetTag existingAssetTag = fetchStagedModelByUuidAndGroupId(
			assetTag.getUuid(), portletDataContext.getScopeGroupId());

		AssetTag importedAssetTag = null;

		if (existingAssetTag == null) {
			serviceContext.setUuid(assetTag.getUuid());

			importedAssetTag = _assetTagLocalService.addTag(
				userId, portletDataContext.getScopeGroupId(),
				assetTag.getName(), serviceContext);
		}
		else {
			importedAssetTag = _assetTagLocalService.updateTag(
				userId, existingAssetTag.getTagId(), assetTag.getName(),
				serviceContext);
		}

		portletDataContext.importClassedModel(assetTag, importedAssetTag);
	}

	@Reference(unbind = "-")
	protected void setAssetTagLocalService(
		AssetTagLocalService assetTagLocalService) {

		_assetTagLocalService = assetTagLocalService;
	}

	private volatile AssetTagLocalService _assetTagLocalService;

}