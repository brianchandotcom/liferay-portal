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

package com.liferay.asset.auto.tagger.opennlp.internal;

import com.liferay.asset.auto.tagger.AssetAutoTagProvider;
import com.liferay.asset.auto.tagger.opennlp.internal.configuration.OpenNLPDocumentAssetAutoTaggerCompanyConfiguration;
import com.liferay.asset.auto.tagger.text.extractor.TextExtractor;
import com.liferay.asset.auto.tagger.text.extractor.TextExtractorTracker;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
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
public class OpenNLPDocumentAssetAutoTagProvider
	implements AssetAutoTagProvider<AssetEntry> {

	@Override
	public Collection<String> getTagNames(AssetEntry assetEntry) {
		try {
			if (_isEnabled(assetEntry)) {
				TextExtractor textExtractor =
					_textExtractorTracker.getTextExtractor(
						assetEntry.getClassName());

				if (textExtractor != null) {
					Locale locale = LocaleUtil.fromLanguageId(
						assetEntry.getDefaultLanguageId());

					return _openNLPDocumentAssetAutoTaggerImpl.getTagNames(
						assetEntry.getCompanyId(),
						() -> textExtractor.extract(
							_getAssetObject(assetEntry), locale),
						locale, assetEntry.getMimeType());
				}
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception, exception);
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

		OpenNLPDocumentAssetAutoTaggerCompanyConfiguration
			openNLPDocumentAssetAutoTagProviderCompanyConfiguration =
				_configurationProvider.getCompanyConfiguration(
					OpenNLPDocumentAssetAutoTaggerCompanyConfiguration.class,
					assetEntry.getCompanyId());

		return ArrayUtil.contains(
			openNLPDocumentAssetAutoTagProviderCompanyConfiguration.
				enabledClassNames(),
			assetEntry.getClassName());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OpenNLPDocumentAssetAutoTagProvider.class);

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private OpenNLPDocumentAssetAutoTaggerImpl
		_openNLPDocumentAssetAutoTaggerImpl;

	@Reference
	private TextExtractorTracker _textExtractorTracker;

}