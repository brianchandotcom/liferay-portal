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

package com.liferay.document.library.asset.auto.tagger.google.cloud.natural.language.internal;

import com.liferay.asset.auto.tagger.AssetAutoTagProvider;
import com.liferay.asset.auto.tagger.google.cloud.natural.language.api.GCloudNaturalLanguageDocumentAssetAutoTagger;
import com.liferay.document.library.asset.auto.tagger.google.cloud.natural.language.internal.configuration.GCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration;
import com.liferay.document.library.asset.auto.tagger.google.cloud.natural.language.internal.constants.GCloudNaturalLanguageAssetAutoTagProviderConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.repository.capabilities.TemporaryFileEntriesCapability;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;

import java.io.IOException;
import java.io.InputStream;

import java.nio.charset.StandardCharsets;

import java.util.Collection;
import java.util.Collections;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alicia García
 */
@Component(
	property = "model.class.name=com.liferay.document.library.kernel.model.DLFileEntry",
	service = AssetAutoTagProvider.class
)
public class GCloudNaturalLanguageDocumentAssetAutoTagProvider
	implements AssetAutoTagProvider<FileEntry> {

	@Override
	public Collection<String> getTagNames(FileEntry fileEntry) {
		try {
			if (fileEntry.isRepositoryCapabilityProvided(
					TemporaryFileEntriesCapability.class)) {

				return Collections.emptySet();
			}

			return _gCloudNaturalLanguageDocumentAssetAutoTagger.getTagNames(
				_getConfiguration(fileEntry), _getFileEntryContent(fileEntry),
				fileEntry.getMimeType());
		}
		catch (Exception e) {
			_log.error(e, e);

			return Collections.emptySet();
		}
	}

	private GCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration
			_getConfiguration(FileEntry fileEntry)
		throws ConfigurationException {

		return _configurationProvider.getConfiguration(
			GCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration.class,
			new CompanyServiceSettingsLocator(
				fileEntry.getCompanyId(),
				GCloudNaturalLanguageAssetAutoTagProviderConstants.
					SERVICE_NAME));
	}

	private String _getFileEntryContent(FileEntry fileEntry)
		throws IOException, PortalException {

		FileVersion fileVersion = fileEntry.getFileVersion();

		String mimeType = fileVersion.getMimeType();

		if (mimeType.equals(ContentTypes.TEXT_PLAIN) ||
			mimeType.equals(ContentTypes.TEXT_HTML)) {

			try (InputStream inputStream = fileVersion.getContentStream(
					false)) {

				return new String(
					FileUtil.getBytes(inputStream), StandardCharsets.UTF_8);
			}
		}

		try (InputStream inputStream = fileVersion.getContentStream(false)) {
			return FileUtil.extractText(inputStream, fileVersion.getFileName());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GCloudNaturalLanguageDocumentAssetAutoTagProvider.class);

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private GCloudNaturalLanguageDocumentAssetAutoTagger
		_gCloudNaturalLanguageDocumentAssetAutoTagger;

}