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

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.AssetVocabularyLocalServiceUtil;
import com.liferay.portlet.asset.service.persistence.AssetVocabularyUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Zsolt Berentey
 * @author Gergely Mathe
 * @author Mate Thurzo
 */
@OSGiBeanProperties
public class AssetVocabularyStagedModelDataHandler
	extends BaseStagedModelDataHandler<AssetVocabulary> {

	public static final String[] CLASS_NAMES =
		{AssetVocabulary.class.getName()};

	@Override
	public void deleteStagedModel(
		String uuid, long groupId, String className, String extraData) {

		AssetVocabulary vocabulary = fetchStagedModelByUuidAndGroupId(
			uuid, groupId);

		if (vocabulary != null) {
			AssetVocabularyLocalServiceUtil.deleteAssetVocabulary(vocabulary);
		}
	}

	@Override
	public AssetVocabulary fetchStagedModelByUuidAndCompanyId(
		String uuid, long companyId) {

		List<AssetVocabulary> vocabularies =
			AssetVocabularyLocalServiceUtil.
				getAssetVocabulariesByUuidAndCompanyId(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					new StagedModelModifiedDateComparator<AssetVocabulary>());

		if (ListUtil.isEmpty(vocabularies)) {
			return null;
		}

		return vocabularies.get(0);
	}

	@Override
	public AssetVocabulary fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return AssetVocabularyLocalServiceUtil.
			fetchAssetVocabularyByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(AssetVocabulary vocabulary) {
		return vocabulary.getTitleCurrentValue();
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

		Element vocabularyElement = portletDataContext.getExportDataElement(
			vocabulary);

		String vocabularyPath = ExportImportPathUtil.getModelPath(vocabulary);

		vocabularyElement.addAttribute("path", vocabularyPath);

		vocabulary.setUserUuid(vocabulary.getUserUuid());

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

		AssetVocabulary importedVocabulary = null;

		AssetVocabulary existingVocabulary = fetchStagedModelByUuidAndGroupId(
			vocabulary.getUuid(), portletDataContext.getScopeGroupId());

		if (existingVocabulary == null) {
			String name = getVocabularyName(
				null, portletDataContext.getScopeGroupId(),
				vocabulary.getName(), 2);

			serviceContext.setUuid(vocabulary.getUuid());

			importedVocabulary =
				AssetVocabularyLocalServiceUtil.addVocabulary(
					userId, StringPool.BLANK,
					getVocabularyTitleMap(
						portletDataContext.getScopeGroupId(), vocabulary, name),
					vocabulary.getDescriptionMap(), vocabulary.getSettings(),
					serviceContext);
		}
		else {
			String name = getVocabularyName(
				vocabulary.getUuid(), portletDataContext.getScopeGroupId(),
				vocabulary.getName(), 2);

			importedVocabulary =
				AssetVocabularyLocalServiceUtil.updateVocabulary(
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

	protected String getVocabularyName(
			String uuid, long groupId, String name, int count)
		throws Exception {

		AssetVocabulary vocabulary = AssetVocabularyUtil.fetchByG_N(
			groupId, name);

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

		if (titleMap == null) {
			titleMap = new HashMap<>();
		}

		titleMap.put(PortalUtil.getSiteDefaultLocale(groupId), name);

		return titleMap;
	}

}