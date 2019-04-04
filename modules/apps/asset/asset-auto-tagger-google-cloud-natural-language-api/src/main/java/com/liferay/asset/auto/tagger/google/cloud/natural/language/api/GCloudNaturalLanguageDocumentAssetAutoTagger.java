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

package com.liferay.asset.auto.tagger.google.cloud.natural.language.api;

import com.liferay.asset.auto.tagger.google.cloud.natural.language.api.configuration.GCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration;

import java.util.Collection;

/**
 * Models a Google Cloud Natural Language Document Asset Auto Tagger.
 *
 * @author Cristina González
 * @review
 */
public interface GCloudNaturalLanguageDocumentAssetAutoTagger {

	/**
	 * Returns a list of tag names from Google Cloud Natural Language
	 * Classification API from the configuration, the text and its mimetype.
	 *
	 * @param gCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration the configuration.
	 * @param content the text to be tagged.
	 * @param mimeType the text mimeType.
	 * @return a list of tag names.
	 * @review
	 */
	public Collection<String> getTagNames(
			GCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration
				gCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration,
			String content, String mimeType)
		throws Exception;

}