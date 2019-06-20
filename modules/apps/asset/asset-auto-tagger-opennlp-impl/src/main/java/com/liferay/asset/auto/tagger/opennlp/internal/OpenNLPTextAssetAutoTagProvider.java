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
import com.liferay.asset.auto.tagger.opennlp.api.OpenNLPDocumentAssetAutoTagger;
import com.liferay.asset.auto.tagger.opennlp.internal.configuration.OpenNLPDocumentAssetAutoTaggerCompanyConfiguration;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.info.extractor.TextExtractor;
import com.liferay.info.extractor.TextExtractorProvider;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Collection;
import java.util.Collections;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = "model.class.name=com.liferay.asset.kernel.model.AssetEntry",
	service = AssetAutoTagProvider.class
)
public class OpenNLPTextAssetAutoTagProvider
	implements AssetAutoTagProvider<AssetEntry> {

	@Override
	public Collection<String> getTagNames(AssetEntry assetEntry) {
		try {
			if (_isEnabled(assetEntry)) {
				TextExtractor textExtractor =
					_textExtractorProvider.getTextExtractor(
						assetEntry.getClassName());

				if (textExtractor != null) {
					return _openNLPDocumentAssetAutoTagger.getTagNames(
						assetEntry.getCompanyId(),
						textExtractor.getText(_getAssetObject(assetEntry)),
						LocaleUtil.fromLanguageId(
							assetEntry.getDefaultLanguageId()),
						ContentTypes.TEXT_PLAIN);
				}
			}
		}
		catch (Exception e) {
			_log.error(e, e);
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
		OpenNLPTextAssetAutoTagProvider.class);

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private OpenNLPDocumentAssetAutoTagger _openNLPDocumentAssetAutoTagger;

	@Reference
	private TextExtractorProvider _textExtractorProvider;

}