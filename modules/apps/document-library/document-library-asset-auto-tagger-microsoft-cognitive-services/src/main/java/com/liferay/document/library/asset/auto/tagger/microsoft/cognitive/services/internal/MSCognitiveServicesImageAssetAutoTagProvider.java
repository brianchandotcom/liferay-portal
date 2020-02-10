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

package com.liferay.document.library.asset.auto.tagger.microsoft.cognitive.services.internal;

import com.liferay.asset.auto.tagger.AssetAutoTagProvider;
import com.liferay.document.library.asset.auto.tagger.microsoft.cognitive.services.internal.configuration.MSCognitiveServicesAssetAutoTagProviderCompanyConfiguration;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.repository.capabilities.TemporaryFileEntriesCapability;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.InetAddressUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.InputStream;
import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = "model.class.name=com.liferay.document.library.kernel.model.DLFileEntry",
	service = AssetAutoTagProvider.class
)
public class MSCognitiveServicesImageAssetAutoTagProvider
	implements AssetAutoTagProvider<FileEntry> {

	@Override
	public Collection<String> getTagNames(FileEntry fileEntry) {
		try {
			MSCognitiveServicesAssetAutoTagProviderCompanyConfiguration
				msCognitiveServicesAssetAutoTagProviderCompanyConfiguration =
					_getConfiguration(fileEntry);

			if (!msCognitiveServicesAssetAutoTagProviderCompanyConfiguration.
					enabled() ||
				_isTemporary(fileEntry) || (fileEntry.getSize() > _MAX_SIZE) ||
				!_isSupportedMimeType(fileEntry.getMimeType())) {

				return Collections.emptyList();
			}

			checkAPIEndpoint(
				msCognitiveServicesAssetAutoTagProviderCompanyConfiguration.
					apiEndpoint());

			JSONObject responseJSONObject = _queryComputerVisionJSONObject(
				msCognitiveServicesAssetAutoTagProviderCompanyConfiguration.
					apiEndpoint(),
				msCognitiveServicesAssetAutoTagProviderCompanyConfiguration.
					apiKey(),
				fileEntry.getFileVersion());

			JSONArray tagsJSONArray = responseJSONObject.getJSONArray("tags");

			return JSONUtil.toStringList(tagsJSONArray, "name");
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception, exception);
			}

			return Collections.emptyList();
		}
	}

	protected void checkAPIEndpoint(String apiEndpoint)
		throws MalformedURLException, UnknownHostException {

		URL url = new URL(apiEndpoint);

		if (InetAddressUtil.isLocalInetAddress(
				InetAddressUtil.getInetAddressByName(url.getHost()))) {

			throw new SecurityException(
				"Microsoft Cognitive Services Image Auto Tagging API " +
					"endpoint is a local address");
		}
	}

	private MSCognitiveServicesAssetAutoTagProviderCompanyConfiguration
			_getConfiguration(FileEntry fileEntry)
		throws ConfigurationException {

		return _configurationProvider.getCompanyConfiguration(
			MSCognitiveServicesAssetAutoTagProviderCompanyConfiguration.class,
			fileEntry.getCompanyId());
	}

	private boolean _isSupportedMimeType(String mimeType) {
		return _supportedMimeTypes.contains(mimeType);
	}

	private boolean _isTemporary(FileEntry fileEntry) {
		return fileEntry.isRepositoryCapabilityProvided(
			TemporaryFileEntriesCapability.class);
	}

	private JSONObject _queryComputerVisionJSONObject(
			String apiEnpoint, String apiKey, FileVersion fileVersion)
		throws Exception {

		URL url = new URL(apiEnpoint + "/tag");

		HttpURLConnection httpURLConnection =
			(HttpURLConnection)url.openConnection();

		httpURLConnection.setDoOutput(true);
		httpURLConnection.setRequestMethod("POST");
		httpURLConnection.setRequestProperty(
			"Content-Type", "application/octet-stream");
		httpURLConnection.setRequestProperty(
			"Ocp-Apim-Subscription-Key", apiKey);

		try (OutputStream outputStream = httpURLConnection.getOutputStream()) {
			outputStream.write(
				FileUtil.getBytes(fileVersion.getContentStream(false)));
		}

		httpURLConnection.getResponseMessage();

		try (InputStream inputStream = httpURLConnection.getInputStream()) {
			return JSONFactoryUtil.createJSONObject(
				StringUtil.read(inputStream));
		}
		catch (Exception exception) {
			try (InputStream inputStream = httpURLConnection.getErrorStream()) {
				throw new PortalException(
					StringBundler.concat(
						"Response code ", httpURLConnection.getResponseCode(),
						":", StringUtil.read(inputStream)),
					exception);
			}
		}
	}

	private static final int _MAX_SIZE = 4 * 1024 * 1024;

	private static final Log _log = LogFactoryUtil.getLog(
		MSCognitiveServicesImageAssetAutoTagProvider.class);

	private static final List<String> _supportedMimeTypes = Arrays.asList(
		ContentTypes.IMAGE_BMP, ContentTypes.IMAGE_GIF, ContentTypes.IMAGE_JPEG,
		ContentTypes.IMAGE_PNG);

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private Http _http;

}