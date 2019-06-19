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

package com.liferay.asset.auto.tagger.opennlp.internal.configuration.admin.definition;

import com.liferay.asset.auto.tagger.opennlp.internal.extractor.TextExtractorProvider;
import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.configuration.admin.definition.ConfigurationFieldOptionsProvider;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = {
		"configuration.field.name=enabledClassNames",
		"configuration.pid=com.liferay.asset.auto.tagger.opennlp.internal.configuration.OpenNLPDocumentAssetAutoTaggerCompanyConfiguration"
	},
	service = ConfigurationFieldOptionsProvider.class
)
public class EnabledClassNamesConfigurationFieldOptionsProvider
	implements ConfigurationFieldOptionsProvider {

	public List<Option> getOptions() {
		return _textExtractorProvider.getTextExtractors(
		).stream(
		).map(
			textExtractor -> new Option() {

				@Override
				public String getLabel(Locale locale) {
					AssetRendererFactory<?> assetRendererFactory =
						AssetRendererFactoryRegistryUtil.
							getAssetRendererFactoryByClassName(
								textExtractor.getClassName());

					return assetRendererFactory.getTypeName(locale);
				}

				@Override
				public String getValue() {
					return textExtractor.getClassName();
				}

			}
		).collect(
			Collectors.toList()
		);
	}

	@Reference
	private TextExtractorProvider _textExtractorProvider;

}