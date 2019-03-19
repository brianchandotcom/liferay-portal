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

package com.liferay.asset.tags.internal.exportimport.data.handler;

import com.liferay.asset.kernel.exception.DuplicateTagException;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.asset.tags.internal.configuration.AssetTagsServiceConfigurationValues;
import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerControl;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.xml.Element;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

		Map<String, String[]> parameterMap =
			portletDataContext.getParameterMap();

		boolean hasMergeParameter = parameterMap.containsKey(
			PortletDataHandlerControl.getNamespacedControlName(
				AssetTagsPortletDataHandler.NAMESPACE, "merge-tags-by-name"));

		if (portletDataContext.getBooleanParameter(
				AssetTagsPortletDataHandler.NAMESPACE, "merge-tags-by-name",
				false) ||
			(!hasMergeParameter &&
			 AssetTagsServiceConfigurationValues.STAGING_MERGE_TAGS_BY_NAME)) {

			existingAssetTag = Optional.ofNullable(
				_assetTagLocalService.fetchTag(
					portletDataContext.getScopeGroupId(), assetTag.getName())
			).orElse(
				existingAssetTag
			);
		}

		AssetTag importedAssetTag = null;

		if (existingAssetTag == null) {
			serviceContext.setUuid(assetTag.getUuid());

			try {
				importedAssetTag = _assetTagLocalService.addTag(
					userId, portletDataContext.getScopeGroupId(),
					assetTag.getName(), serviceContext);
			}
			catch (DuplicateTagException dte) {
				if (_log.isDebugEnabled()) {
					_log.debug(dte, dte);
				}

				importedAssetTag = _assetTagLocalService.addTag(
					userId, portletDataContext.getScopeGroupId(),
					assetTag.getName() + " (Duplicate)", serviceContext);
			}
		}
		else {
			try {
				importedAssetTag = _assetTagLocalService.updateTag(
					userId, existingAssetTag.getTagId(), assetTag.getName(),
					serviceContext);
			}
			catch (DuplicateTagException dte) {
				if (_log.isDebugEnabled()) {
					_log.debug(dte, dte);
				}

				importedAssetTag = _assetTagLocalService.updateTag(
					userId, existingAssetTag.getTagId(),
					assetTag.getName() + " (Duplicate)", serviceContext);
			}
		}

		portletDataContext.importClassedModel(assetTag, importedAssetTag);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetTagStagedModelDataHandler.class);

	@Reference
	private AssetTagLocalService _assetTagLocalService;

}