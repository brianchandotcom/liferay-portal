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

package com.liferay.journal.asset.auto.tagger.google.cloud.natural.language.internal;

import com.liferay.asset.auto.tagger.AssetAutoTagProvider;
import com.liferay.asset.auto.tagger.google.cloud.natural.language.api.GCloudNaturalLanguageDocumentAssetAutoTagger;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.dynamic.data.mapping.util.DDMIndexer;
import com.liferay.dynamic.data.mapping.util.FieldsToDDMFormValuesConverter;
import com.liferay.journal.asset.auto.tagger.google.cloud.natural.language.internal.configuration.GCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration;
import com.liferay.journal.asset.auto.tagger.google.cloud.natural.language.internal.constants.GCloudNaturalLanguageAssetAutoTagProviderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.util.JournalConverter;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.Collection;
import java.util.Collections;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 * @author Alicia García
 */
@Component(
	property = "model.class.name=com.liferay.journal.model.JournalArticle",
	service = AssetAutoTagProvider.class
)
public class GCloudNaturalLanguageDocumentAssetAutoTagProvider
	implements AssetAutoTagProvider<JournalArticle> {

	@Override
	public Collection<String> getTagNames(JournalArticle journalArticle) {
		try {
			return _getTagNames(journalArticle);
		}
		catch (Exception e) {
			_log.error(e, e);

			return Collections.emptyList();
		}
	}

	protected String extractDDMContent(JournalArticle journalArticle) {
		DDMStructure ddmStructure = _ddmStructureLocalService.fetchStructure(
			_portal.getSiteGroupId(journalArticle.getGroupId()),
			_portal.getClassNameId(JournalArticle.class),
			journalArticle.getDDMStructureKey(), true);

		if (ddmStructure == null) {
			return StringPool.BLANK;
		}

		DDMFormValues ddmFormValues = null;

		try {
			Fields fields = _journalConverter.getDDMFields(
				ddmStructure, journalArticle.getDocument());

			ddmFormValues = _fieldsToDDMFormValuesConverter.convert(
				ddmStructure, fields);
		}
		catch (Exception e) {
			return StringPool.BLANK;
		}

		if (ddmFormValues == null) {
			return StringPool.BLANK;
		}

		return _ddmIndexer.extractIndexableAttributes(
			ddmStructure, ddmFormValues,
			LocaleUtil.fromLanguageId(journalArticle.getDefaultLanguageId()));
	}

	private GCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration
			_getConfiguration(JournalArticle journalArticle)
		throws ConfigurationException {

		return _configurationProvider.getConfiguration(
			GCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration.class,
			new CompanyServiceSettingsLocator(
				journalArticle.getCompanyId(),
				GCloudNaturalLanguageAssetAutoTagProviderConstants.
					SERVICE_NAME));
	}

	private Collection<String> _getTagNames(JournalArticle journalArticle)
		throws Exception {

		return _gCloudNaturalLanguageDocumentAssetAutoTagger.getTagNames(
			_getConfiguration(journalArticle),
			extractDDMContent(journalArticle), ContentTypes.TEXT_PLAIN);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GCloudNaturalLanguageDocumentAssetAutoTagProvider.class);

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private DDMIndexer _ddmIndexer;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private FieldsToDDMFormValuesConverter _fieldsToDDMFormValuesConverter;

	@Reference
	private GCloudNaturalLanguageDocumentAssetAutoTagger
		_gCloudNaturalLanguageDocumentAssetAutoTagger;

	@Reference
	private JournalConverter _journalConverter;

	@Reference
	private Portal _portal;

}