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

package com.liferay.portlet.asset.lar;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.adapter.ModelAdapterUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.model.adapter.StagedAssetTag;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.exportimport.lar.BaseStagedModelDataHandler;
import com.liferay.portlet.exportimport.lar.ExportImportPathUtil;
import com.liferay.portlet.exportimport.lar.PortletDataContext;

import java.util.List;

/**
 * @author Daniel Kocsis
 */
public class StagedAssetTagStagedModelDataHandler
	extends BaseStagedModelDataHandler<StagedAssetTag> {

	public static final String[] CLASS_NAMES = {StagedAssetTag.class.getName()};

	@Override
	public void deleteStagedModel(StagedAssetTag stagedAssetTag)
		throws PortalException {

		AssetTagLocalServiceUtil.deleteTag(stagedAssetTag);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		StagedAssetTag stagedAssetTag = fetchStagedModelByUuidAndGroupId(
			uuid, groupId);

		if (stagedAssetTag != null) {
			deleteStagedModel(stagedAssetTag);
		}
	}

	@Override
	public StagedAssetTag fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		AssetTag assetTag = AssetTagLocalServiceUtil.fetchTag(groupId, uuid);

		if (assetTag == null) {
			return null;
		}

		return ModelAdapterUtil.adapt(
			assetTag, AssetTag.class, StagedAssetTag.class);
	}

	@Override
	public List<StagedAssetTag> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		DynamicQuery dynamicQuery = AssetTagLocalServiceUtil.dynamicQuery();

		Property companyIdProperty = PropertyFactoryUtil.forName("companyId");

		dynamicQuery.add(companyIdProperty.eq(companyId));

		Property nameProperty = PropertyFactoryUtil.forName("name");

		dynamicQuery.add(nameProperty.eq(uuid));

		List<AssetTag> assetTags = AssetTagLocalServiceUtil.dynamicQuery(
			dynamicQuery);

		if (ListUtil.isEmpty(assetTags)) {
			return null;
		}

		return ModelAdapterUtil.adapt(
			assetTags, AssetTag.class, StagedAssetTag.class);
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(StagedAssetTag stagedAssetTag) {
		return stagedAssetTag.getName();
	}

	protected ServiceContext createServiceContext(
		PortletDataContext portletDataContext, StagedAssetTag stagedAssetTag) {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setCreateDate(stagedAssetTag.getCreateDate());
		serviceContext.setModifiedDate(stagedAssetTag.getModifiedDate());
		serviceContext.setScopeGroupId(portletDataContext.getScopeGroupId());

		return serviceContext;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			StagedAssetTag stagedAssetTag)
		throws Exception {

		Element assetTagElement = portletDataContext.getExportDataElement(
			stagedAssetTag);

		portletDataContext.addClassedModel(
			assetTagElement, ExportImportPathUtil.getModelPath(stagedAssetTag),
			stagedAssetTag);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			StagedAssetTag stagedAssetTag)
		throws Exception {

		long userId = portletDataContext.getUserId(
			stagedAssetTag.getUserUuid());

		ServiceContext serviceContext = createServiceContext(
			portletDataContext, stagedAssetTag);

		AssetTag existingAssetTag = fetchStagedModelByUuidAndGroupId(
			stagedAssetTag.getName(), portletDataContext.getScopeGroupId());

		AssetTag importedAssetTag = null;

		if (existingAssetTag == null) {
			importedAssetTag = AssetTagLocalServiceUtil.addTag(
				userId, portletDataContext.getScopeGroupId(),
				stagedAssetTag.getName(), serviceContext);
		}
		else {
			importedAssetTag = AssetTagLocalServiceUtil.updateTag(
				userId, existingAssetTag.getTagId(), stagedAssetTag.getName(),
				serviceContext);
		}

		portletDataContext.importClassedModel(stagedAssetTag, importedAssetTag);
	}

}