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

package com.liferay.asset.categories.admin.web.internal.exportimport.data.handler;

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Zsolt Berentey
 * @author Gergely Mathe
 * @author Máté Thurzó
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class AssetVocabularyStagedModelDataHandler
	extends BaseStagedModelDataHandler<AssetVocabulary> {

	public static final String[] CLASS_NAMES = {
		AssetVocabulary.class.getName()
	};

	@Override
	public void deleteStagedModel(AssetVocabulary vocabulary)
		throws PortalException {

		_assetVocabularyLocalService.deleteVocabulary(vocabulary);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		AssetVocabulary vocabulary = fetchStagedModelByUuidAndGroupId(
			uuid, groupId);

		if (vocabulary != null) {
			deleteStagedModel(vocabulary);
		}
	}

	@Override
	public AssetVocabulary fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _assetVocabularyLocalService.
			fetchAssetVocabularyByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public List<AssetVocabulary> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _assetVocabularyLocalService.
			getAssetVocabulariesByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator<AssetVocabulary>());
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
	public boolean validateReference(
		PortletDataContext portletDataContext, Element referenceElement) {

		validateMissingGroupReference(portletDataContext, referenceElement);

		String uuid = referenceElement.attributeValue("uuid");

		Map<Long, Long> groupIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Group.class);

		long groupId = GetterUtil.getLong(
			referenceElement.attributeValue("group-id"));

		groupId = MapUtil.getLong(groupIds, groupId);

		String displayName = referenceElement.attributeValue("display-name");

		return validateMissingReference(uuid, groupId, displayName);
	}

	protected ServiceContext createServiceContext(
		PortletDataContext portletDataContext, AssetVocabulary vocabulary) {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setCreateDate(vocabulary.getCreateDate());
		serviceContext.setModifiedDate(vocabulary.getModifiedDate());
		serviceContext.setScopeGroupId(portletDataContext.getScopeGroupId());

		return serviceContext;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, AssetVocabulary vocabulary)
		throws Exception {

		Locale locale = _portal.getSiteDefaultLocale(
			portletDataContext.getScopeGroupId());

		Element vocabularyElement = portletDataContext.getExportDataElement(
			vocabulary);

		String vocabularyPath = ExportImportPathUtil.getModelPath(vocabulary);

		vocabularyElement.addAttribute("path", vocabularyPath);

		vocabulary.setUserUuid(vocabulary.getUserUuid());

		exportSettingsMetadata(
			portletDataContext, vocabulary, vocabularyElement, locale);

		portletDataContext.addReferenceElement(
			vocabulary, vocabularyElement, vocabulary,
			PortletDataContext.REFERENCE_TYPE_DEPENDENCY, false);

		portletDataContext.addPermissions(
			AssetVocabulary.class, vocabulary.getVocabularyId());

		portletDataContext.addZipEntry(vocabularyPath, vocabulary);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long vocabularyId)
		throws Exception {

		AssetVocabulary existingVocabulary = fetchMissingReference(
			uuid, groupId);

		if (existingVocabulary == null) {
			return;
		}

		Map<Long, Long> vocabularyIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				AssetVocabulary.class);

		vocabularyIds.put(vocabularyId, existingVocabulary.getVocabularyId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, AssetVocabulary vocabulary)
		throws Exception {

		long userId = portletDataContext.getUserId(vocabulary.getUserUuid());

		ServiceContext serviceContext = createServiceContext(
			portletDataContext, vocabulary);

		vocabulary.setSettings(
			getImportSettings(portletDataContext, vocabulary));

		AssetVocabulary importedVocabulary = null;

		AssetVocabulary existingVocabulary = fetchStagedModelByUuidAndGroupId(
			vocabulary.getUuid(), portletDataContext.getScopeGroupId());

		if (existingVocabulary == null) {
			String name = getVocabularyName(
				null, portletDataContext.getScopeGroupId(),
				vocabulary.getName(), 2);

			serviceContext.setUuid(vocabulary.getUuid());

			importedVocabulary = _assetVocabularyLocalService.addVocabulary(
				userId, portletDataContext.getScopeGroupId(), StringPool.BLANK,
				getVocabularyTitleMap(
					portletDataContext.getScopeGroupId(), vocabulary, name),
				vocabulary.getDescriptionMap(), vocabulary.getSettings(),
				serviceContext);
		}
		else {
			String name = getVocabularyName(
				vocabulary.getUuid(), portletDataContext.getScopeGroupId(),
				vocabulary.getName(), 2);

			importedVocabulary = _assetVocabularyLocalService.updateVocabulary(
				existingVocabulary.getVocabularyId(), StringPool.BLANK,
				getVocabularyTitleMap(
					portletDataContext.getScopeGroupId(), vocabulary, name),
				vocabulary.getDescriptionMap(), vocabulary.getSettings(),
				serviceContext);
		}

		Map<Long, Long> vocabularyIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				AssetVocabulary.class);

		vocabularyIds.put(
			vocabulary.getVocabularyId(), importedVocabulary.getVocabularyId());

		portletDataContext.importPermissions(
			AssetVocabulary.class, vocabulary.getVocabularyId(),
			importedVocabulary.getVocabularyId());
	}

	protected void exportSettingsMetadata(
			PortletDataContext portletDataContext, AssetVocabulary vocabulary,
			Element vocabularyElement, Locale locale)
		throws PortalException {

		String settingsMetadataPath = ExportImportPathUtil.getModelPath(
			vocabulary, _SETTINGS_METADATA + ".json");

		vocabularyElement.addAttribute(
			_SETTINGS_METADATA, settingsMetadataPath);

		AssetVocabularySettingsExportHelper
			assetVocabularySettingsExportHelper =
				new AssetVocabularySettingsExportHelper(
					vocabulary.getSettings(), _jsonFactory, locale);

		portletDataContext.addZipEntry(
			settingsMetadataPath,
			assetVocabularySettingsExportHelper.getSettingsMetadata());
	}

	protected String getImportSettings(
			PortletDataContext portletDataContext, AssetVocabulary vocabulary)
		throws PortalException {

		Element vocabularyElement = portletDataContext.getImportDataElement(
			vocabulary);

		JSONObject settingsMetadataJSONObject =
			getImportSettingsMetadataJSONObject(
				portletDataContext, vocabularyElement);

		if (settingsMetadataJSONObject.length() == 0) {
			return vocabulary.getSettings();
		}

		long groupId = portletDataContext.getScopeGroupId();

		long[] groupIds = {portletDataContext.getCompanyGroupId(), groupId};

		Locale locale = _portal.getSiteDefaultLocale(groupId);

		AssetVocabularySettingsImportHelper
			assetVocabularySettingsImportHelper =
				new AssetVocabularySettingsImportHelper(
					vocabulary.getSettings(), _classNameLocalService, groupIds,
					locale, settingsMetadataJSONObject);

		return assetVocabularySettingsImportHelper.getSettings();
	}

	protected JSONObject getImportSettingsMetadataJSONObject(
			PortletDataContext portletDataContext, Element vocabularyElement)
		throws PortalException {

		String settingsMetadataPath = vocabularyElement.attributeValue(
			_SETTINGS_METADATA);

		String serializedSettingsMetadata =
			portletDataContext.getZipEntryAsString(settingsMetadataPath);

		return _jsonFactory.createJSONObject(serializedSettingsMetadata);
	}

	protected String getVocabularyName(
			String uuid, long groupId, String name, int count)
		throws Exception {

		AssetVocabulary vocabulary =
			_assetVocabularyLocalService.fetchGroupVocabulary(groupId, name);

		if (vocabulary == null) {
			return name;
		}

		if (Validator.isNotNull(uuid) && uuid.equals(vocabulary.getUuid())) {
			return name;
		}

		name = StringUtil.appendParentheticalSuffix(name, count);

		return getVocabularyName(uuid, groupId, name, ++count);
	}

	protected Map<Locale, String> getVocabularyTitleMap(
			long groupId, AssetVocabulary vocabulary, String name)
		throws PortalException {

		Map<Locale, String> titleMap = vocabulary.getTitleMap();

		Locale locale = _portal.getSiteDefaultLocale(groupId);

		if (titleMap.isEmpty() || !Objects.equals(vocabulary.getName(), name) ||
			!titleMap.containsKey(locale)) {

			titleMap.put(locale, name);
		}

		return titleMap;
	}

	protected boolean validateMissingReference(
		String uuid, long groupId, String name) {

		AssetVocabulary existingStagedModel = fetchMissingReference(
			uuid, groupId);

		if (existingStagedModel == null) {
			existingStagedModel =
				_assetVocabularyLocalService.fetchGroupVocabulary(
					groupId, name);
		}

		if (existingStagedModel == null) {
			return false;
		}

		return true;
	}

	private static final String _SETTINGS_METADATA = "settings-metadata";

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Portal _portal;

}