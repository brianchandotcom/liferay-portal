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

package com.liferay.document.library.asset.auto.tagger.opennlp.internal;

import com.liferay.asset.auto.tagger.AssetAutoTagProvider;
import com.liferay.asset.auto.tagger.opennlp.api.OpenNLPDocumentAssetAutoTagger;
import com.liferay.document.library.asset.auto.tagger.opennlp.internal.configuration.OpenNPLDocumentAssetAutoTagProviderCompanyConfiguration;
import com.liferay.document.library.asset.auto.tagger.opennlp.internal.constants.OpenNLPDocumentAssetAutoTagProviderConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.repository.capabilities.TemporaryFileEntriesCapability;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.util.FileUtil;

import java.io.IOException;
import java.io.InputStream;

import java.util.Collection;
import java.util.Collections;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(
	property = "model.class.name=com.liferay.document.library.kernel.model.DLFileEntry",
	service = AssetAutoTagProvider.class
)
public class OpenNLPDocumentAssetAutoTagProvider
	implements AssetAutoTagProvider<FileEntry> {

	@Override
	public Collection<String> getTagNames(FileEntry fileEntry) {
		try {
			return _getTagNames(fileEntry);
		}
		catch (Exception e) {
			_log.error(e, e);

			return Collections.emptySet();
		}
	}

	private OpenNPLDocumentAssetAutoTagProviderCompanyConfiguration
			_getConfiguration(long companyId)
		throws ConfigurationException {

		return _configurationProvider.getConfiguration(
			OpenNPLDocumentAssetAutoTagProviderCompanyConfiguration.class,
			new CompanyServiceSettingsLocator(
				companyId,
				OpenNLPDocumentAssetAutoTagProviderConstants.SERVICE_NAME));
	}

	private String _getFileEntryContent(FileEntry fileEntry)
		throws IOException, PortalException {

		FileVersion fileVersion = fileEntry.getFileVersion();

		try (InputStream inputStream = fileVersion.getContentStream(false)) {
			return FileUtil.extractText(inputStream, fileVersion.getFileName());
		}
	}

	private Collection<String> _getTagNames(FileEntry fileEntry)
		throws Exception {

		if (fileEntry.isRepositoryCapabilityProvided(
				TemporaryFileEntriesCapability.class)) {

			return Collections.emptyList();
		}

		OpenNPLDocumentAssetAutoTagProviderCompanyConfiguration
			openNPLDocumentAssetAutoTagProviderCompanyConfiguration =
				_getConfiguration(fileEntry.getCompanyId());

		if (!openNPLDocumentAssetAutoTagProviderCompanyConfiguration.
				enabled()) {

			return Collections.emptyList();
		}

		return _openNLPDocumentAssetAutoTagger.getTagNames(
			_getFileEntryContent(fileEntry),
			openNPLDocumentAssetAutoTagProviderCompanyConfiguration.
				confidenceThreshold(),
			fileEntry.getMimeType());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OpenNLPDocumentAssetAutoTagProvider.class);

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private OpenNLPDocumentAssetAutoTagger _openNLPDocumentAssetAutoTagger;

}