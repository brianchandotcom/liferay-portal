/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.auto.tagger.google.cloud.natural.language.internal;

import com.liferay.asset.auto.tagger.AssetAutoTagProvider;
import com.liferay.asset.auto.tagger.google.cloud.natural.language.internal.configuration.GCloudNaturalLanguageAssetAutoTaggerCompanyConfiguration;
import com.liferay.asset.auto.tagger.text.extractor.TextExtractor;
import com.liferay.asset.auto.tagger.text.extractor.TextExtractorRegistry;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Collection;
import java.util.Collections;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = "model.class.name=com.liferay.asset.kernel.model.AssetEntry",
	service = AssetAutoTagProvider.class
)
public class GCloudNaturalLanguageDocumentAssetAutoTagProvider
	implements AssetAutoTagProvider<AssetEntry> {

	@Override
	public Collection<String> getTagNames(AssetEntry assetEntry) {
		try {
			if (_isEnabled(assetEntry)) {
				TextExtractor<Object> textExtractor =
					(TextExtractor<Object>)
						_textExtractorRegistry.getTextExtractor(
							assetEntry.getClassName());

				if (textExtractor != null) {
					Locale locale = LocaleUtil.fromLanguageId(
						assetEntry.getDefaultLanguageId());

					return _gCloudNaturalLanguageDocumentAssetAutoTagger.
						getTagNames(
							assetEntry.getCompanyId(),
							() -> textExtractor.extract(
								_getAssetObject(assetEntry), locale),
							locale, assetEntry.getMimeType());
				}
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}
		}

		return Collections.emptyList();
	}

	private Object _getAssetObject(AssetEntry assetEntry) {
		AssetRenderer<?> assetRenderer = assetEntry.getAssetRenderer();

		return assetRenderer.getAssetObject();
	}

	private boolean _isEnabled(AssetEntry assetEntry)
		throws ConfigurationException {

		GCloudNaturalLanguageAssetAutoTaggerCompanyConfiguration
			gCloudNaturalLanguageAssetAutoTaggerCompanyConfiguration =
				_configurationProvider.getCompanyConfiguration(
					GCloudNaturalLanguageAssetAutoTaggerCompanyConfiguration.
						class,
					assetEntry.getCompanyId());

		return ArrayUtil.contains(
			gCloudNaturalLanguageAssetAutoTaggerCompanyConfiguration.
				enabledClassNames(),
			assetEntry.getClassName());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GCloudNaturalLanguageDocumentAssetAutoTagProvider.class);

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private GCloudNaturalLanguageDocumentAssetAutoTagger
		_gCloudNaturalLanguageDocumentAssetAutoTagger;

	@Reference
	private TextExtractorRegistry _textExtractorRegistry;

}