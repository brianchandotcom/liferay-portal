/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.tags.internal.exportimport.data.handler;

import com.liferay.asset.kernel.exception.DuplicateTagException;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetTagGroupRelLocalService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.asset.tags.internal.configuration.AssetTagsServiceConfigurationValues;
import com.liferay.depot.service.DepotEntryService;
import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerControl;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.UniqueUtil;
import com.liferay.portal.kernel.xml.Element;

import java.util.ArrayList;
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
	public AssetTag fetchStagedModelByExternalReferenceCodeAndGroupId(
		String externalReferenceCode, long groupId) {

		return _assetTagLocalService.fetchAssetTagByExternalReferenceCode(
			externalReferenceCode, groupId);
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

		Group group = _groupLocalService.getGroup(
			portletDataContext.getScopeGroupId());

		if (FeatureFlagManagerUtil.isEnabled(
				group.getCompanyId(), "LPD-17564") &&
			group.isCMS()) {

			_exportAssetTagGroupRel(
				portletDataContext, assetTagElement, assetTag);
		}

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

		AssetTag existingAssetTag = fetchExistingStagedModel(
			assetTag, portletDataContext.getScopeGroupId());

		Map<String, String[]> parameterMap =
			portletDataContext.getParameterMap();

		boolean hasMergeParameter = parameterMap.containsKey(
			PortletDataHandlerControl.getNamespacedName(
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

			importedAssetTag = _assetTagLocalService.addTag(
				assetTag.getExternalReferenceCode(), userId,
				portletDataContext.getScopeGroupId(),
				_getUniqueName(
					portletDataContext.getScopeGroupId(), assetTag.getName()),
				serviceContext);
		}
		else {
			try {
				importedAssetTag = _assetTagLocalService.updateTag(
					existingAssetTag.getExternalReferenceCode(), userId,
					existingAssetTag.getTagId(), assetTag.getName(),
					serviceContext);
			}
			catch (DuplicateTagException duplicateTagException) {
				if (_log.isDebugEnabled()) {
					_log.debug(duplicateTagException);
				}

				importedAssetTag = _assetTagLocalService.updateTag(
					existingAssetTag.getExternalReferenceCode(), userId,
					existingAssetTag.getTagId(),
					_getUniqueName(
						portletDataContext.getScopeGroupId(),
						assetTag.getName()),
					serviceContext);
			}
		}

		Group group = _groupLocalService.fetchGroup(
			portletDataContext.getScopeGroupId());

		if (FeatureFlagManagerUtil.isEnabled(
				group.getCompanyId(), "LPD-17564") &&
			group.isCMS()) {

			_importAssetTagGroupRel(
				portletDataContext,
				portletDataContext.getImportDataElement(assetTag),
				importedAssetTag.getTagId());
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

	private void _exportAssetTagGroupRel(
		PortletDataContext portletDataContext, Element assetTagElement,
		AssetTag assetTag) {

		String assetTagGroupRelPath = ExportImportPathUtil.getModelPath(
			assetTag, _ASSET_TAG_GROUP_REL_PATH + ".json");

		JSONObject assetTagGroupRelsJSONObject =
			_jsonFactory.createJSONObject();

		assetTagGroupRelsJSONObject.put(
			"groupKeys",
			TransformUtil.transform(
				_assetTagGroupRelLocalService.getAssetTagGroupRelsByTagId(
					assetTag.getTagId()),
				assetTagGroupRel -> {
					if (assetTagGroupRel.getGroupId() == -1) {
						return null;
					}

					Group group = _groupLocalService.fetchGroup(
						assetTagGroupRel.getGroupId());

					if (group != null) {
						return group.getGroupKey();
					}

					return null;
				}));

		assetTagElement.addAttribute(
			_ASSET_TAG_GROUP_REL_PATH, assetTagGroupRelPath);

		portletDataContext.addZipEntry(
			assetTagGroupRelPath, assetTagGroupRelsJSONObject.toString());
	}

	private String _getUniqueName(long groupId, String name)
		throws PortalException {

		AssetTag assetTag = _assetTagLocalService.fetchTag(groupId, name);

		if (assetTag == null) {
			return name;
		}

		return UniqueUtil.getUniqueValue(
			"duplicate",
			uniqueValue -> {
				if (_assetTagLocalService.fetchTag(groupId, uniqueValue) ==
						null) {

					return true;
				}

				return false;
			},
			name);
	}

	private void _importAssetTagGroupRel(
			PortletDataContext portletDataContext, Element tagElement,
			long importedTagId)
		throws Exception {

		String assetTagGroupRelPath = tagElement.attributeValue(
			_ASSET_TAG_GROUP_REL_PATH);

		JSONObject assetTagGroupRelsJSONObject = _jsonFactory.createJSONObject(
			portletDataContext.getZipEntryAsString(assetTagGroupRelPath));

		JSONArray assetTagGroupRelsJSONArray =
			assetTagGroupRelsJSONObject.getJSONArray("groupKeys");

		List<Long> groupIdList = new ArrayList<>();

		for (int i = 0; i < assetTagGroupRelsJSONArray.length(); i++) {
			String groupKey = assetTagGroupRelsJSONArray.getString(i);

			Group group = _groupLocalService.fetchGroup(
				portletDataContext.getCompanyId(), groupKey);

			if (group == null) {
				continue;
			}

			if (_depotEntryService.fetchGroupDepotEntry(group.getGroupId()) !=
					null) {

				groupIdList.add(group.getGroupId());
			}
		}

		if (groupIdList.isEmpty()) {
			groupIdList.add(_GROUP_ID_ALL);
		}

		_assetTagGroupRelLocalService.setAssetTagGroupRels(
			importedTagId, ListUtil.toLongArray(groupIdList, Long::longValue));
	}

	private static final String _ASSET_TAG_GROUP_REL_PATH =
		"asset-tag-group-rel";

	private static final long _GROUP_ID_ALL = -1L;

	private static final Log _log = LogFactoryUtil.getLog(
		AssetTagStagedModelDataHandler.class);

	@Reference
	private AssetTagGroupRelLocalService _assetTagGroupRelLocalService;

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private DepotEntryService _depotEntryService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private JSONFactory _jsonFactory;

}