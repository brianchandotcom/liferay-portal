/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.AssetVocabularyLocalServiceUtil;

/**
 * @author Zsolt Berentey
 * @author Gergely Mathe
 */
public class AssetVocabularyStagedModelDataHandler
	extends BaseStagedModelDataHandler<AssetVocabulary> {

	public static final String[] CLASS_NAMES =
		{AssetVocabulary.class.getName()};

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws SystemException {

		AssetVocabulary vocabulary =
			AssetVocabularyLocalServiceUtil.
				fetchAssetVocabularyByUuidAndGroupId(uuid, groupId);

		if (vocabulary != null) {
			AssetVocabularyLocalServiceUtil.deleteAssetVocabulary(vocabulary);
		}
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(AssetVocabulary vocabulary) {
		return vocabulary.getTitleCurrentValue();
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, AssetVocabulary vocabulary)
		throws Exception {

		String path = getAssetVocabulariesPath(
			portletDataContext, assetVocabulary.getVocabularyId());

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		Element assetVocabularyElement = assetVocabulariesElement.addElement(
			"vocabulary");

		assetVocabularyElement.addAttribute("path", path);

		assetVocabulary.setUserUuid(assetVocabulary.getUserUuid());

		portletDataContext.addZipEntry(path, assetVocabulary);

		portletDataContext.addPermissions(
			AssetVocabulary.class, assetVocabulary.getVocabularyId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, AssetVocabulary vocabulary)
		throws Exception {

		long userId = portletDataContext.getUserId(
			assetVocabulary.getUserUuid());
		long groupId = portletDataContext.getScopeGroupId();

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setCreateDate(assetVocabulary.getCreateDate());
		serviceContext.setModifiedDate(assetVocabulary.getModifiedDate());
		serviceContext.setScopeGroupId(portletDataContext.getScopeGroupId());

		AssetVocabulary importedAssetVocabulary = null;

		AssetVocabulary existingAssetVocabulary =
			AssetVocabularyUtil.fetchByUUID_G(
				assetVocabulary.getUuid(), groupId);

		if (existingAssetVocabulary == null) {
			existingAssetVocabulary = AssetVocabularyUtil.fetchByUUID_G(
				assetVocabulary.getUuid(),
				portletDataContext.getCompanyGroupId());
		}

		if (existingAssetVocabulary == null) {
			String name = getAssetVocabularyName(
				null, groupId, assetVocabulary.getName(), 2);

			serviceContext.setUuid(assetVocabulary.getUuid());

			importedAssetVocabulary =
				AssetVocabularyLocalServiceUtil.addVocabulary(
					userId, StringPool.BLANK,
					getAssetVocabularyTitleMap(groupId, assetVocabulary, name),
					assetVocabulary.getDescriptionMap(),
					assetVocabulary.getSettings(), serviceContext);
		}
		else if (portletDataContext.isCompanyStagedGroupedModel(
					existingAssetVocabulary)) {

			return;
		}
		else {
			String name = getAssetVocabularyName(
				assetVocabulary.getUuid(), groupId, assetVocabulary.getName(),
				2);

			importedAssetVocabulary =
				AssetVocabularyLocalServiceUtil.updateVocabulary(
					existingAssetVocabulary.getVocabularyId(), StringPool.BLANK,
					getAssetVocabularyTitleMap(groupId, assetVocabulary, name),
					assetVocabulary.getDescriptionMap(),
					assetVocabulary.getSettings(), serviceContext);
		}

		assetVocabularyPKs.put(
			assetVocabulary.getVocabularyId(),
			importedAssetVocabulary.getVocabularyId());

		portletDataContext.importPermissions(
			AssetVocabulary.class, assetVocabulary.getVocabularyId(),
			importedAssetVocabulary.getVocabularyId());
	}

	protected String getAssetVocabularyName(
			String uuid, long groupId, String name, int count)
		throws Exception {

		AssetVocabulary assetVocabulary = AssetVocabularyUtil.fetchByG_N(
			groupId, name);

		if (assetVocabulary == null) {
			return name;
		}

		if (Validator.isNotNull(uuid) &&
			uuid.equals(assetVocabulary.getUuid())) {

			return name;
		}

		name = StringUtil.appendParentheticalSuffix(name, count);

		return getAssetVocabularyName(uuid, groupId, name, ++count);
	}

	protected Map<Locale, String> getAssetVocabularyTitleMap(
			long groupId, AssetVocabulary assetVocabulary, String name)
		throws PortalException, SystemException {

		Map<Locale, String> titleMap = assetVocabulary.getTitleMap();

		if (titleMap == null) {
			titleMap = new HashMap<Locale, String>();
		}

		titleMap.put(PortalUtil.getSiteDefaultLocale(groupId), name);

		return titleMap;
	}

	@Override
	protected boolean validateMissingReference(
			String uuid, long companyId, long groupId)
		throws Exception {

		AssetVocabulary vocabulary =
			AssetVocabularyLocalServiceUtil.
				fetchAssetVocabularyByUuidAndGroupId(uuid, groupId);

		if (vocabulary == null) {
			return false;
		}

		return true;
	}

}