/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Kocsis
 */
@Component(service = StagedModelDataHandler.class)
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
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long tagId)
		throws Exception {

		AssetTag existingTag = fetchMissingReference(uuid, groupId);

		if (existingTag == null) {
			return;
		}

		Map<Long, Long> tagIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				AssetTag.class);

		tagIds.put(tagId, existingTag.getTagId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, AssetTag assetTag)
		throws Exception {

		long userId = portletDataContext.getUserId(assetTag.getUserUuid());

		ServiceContext serviceContext = _createServiceContext(
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

			AssetTag fetchedAssetTag = _assetTagLocalService.fetchTag(
				portletDataContext.getScopeGroupId(), assetTag.getName());

			if (fetchedAssetTag != null) {
				existingAssetTag = fetchedAssetTag;
			}
		}

		AssetTag importedAssetTag = null;

		if (existingAssetTag == null) {
			serviceContext.setUuid(assetTag.getUuid());

			try {
				importedAssetTag = _assetTagLocalService.addTag(
					userId, portletDataContext.getScopeGroupId(),
					assetTag.getName(), serviceContext);
			}
			catch (DuplicateTagException duplicateTagException) {
				if (_log.isDebugEnabled()) {
					_log.debug(duplicateTagException);
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
			catch (DuplicateTagException duplicateTagException) {
				if (_log.isDebugEnabled()) {
					_log.debug(duplicateTagException);
				}

				importedAssetTag = _assetTagLocalService.updateTag(
					userId, existingAssetTag.getTagId(),
					assetTag.getName() + " (Duplicate)", serviceContext);
			}
		}

		portletDataContext.importClassedModel(assetTag, importedAssetTag);
	}

	private ServiceContext _createServiceContext(
		PortletDataContext portletDataContext, AssetTag assetTag) {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCreateDate(assetTag.getCreateDate());
		serviceContext.setModifiedDate(assetTag.getModifiedDate());
		serviceContext.setScopeGroupId(portletDataContext.getScopeGroupId());

		return serviceContext;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetTagStagedModelDataHandler.class);

	@Reference
	private AssetTagLocalService _assetTagLocalService;

}