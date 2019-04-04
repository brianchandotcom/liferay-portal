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

package com.liferay.document.library.asset.auto.tagger.google.cloud.vision.internal;

import com.liferay.asset.auto.tagger.AssetAutoTagProvider;
import com.liferay.document.library.asset.auto.tagger.google.cloud.vision.internal.configuration.GCloudVisionAssetAutoTagProviderCompanyConfiguration;
import com.liferay.document.library.asset.auto.tagger.google.cloud.vision.internal.util.GCloudVisionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
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
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringUtil;

import java.net.HttpURLConnection;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = "model.class.name=com.liferay.document.library.kernel.model.DLFileEntry",
	service = AssetAutoTagProvider.class
)
public class GCloudVisionImageAssetAutoTagProvider
	implements AssetAutoTagProvider<FileEntry> {

	@Override
	public Collection<String> getTagNames(FileEntry fileEntry) {
		try {
			GCloudVisionAssetAutoTagProviderCompanyConfiguration
				gCloudVisionAssetAutoTagProviderCompanyConfiguration =
					_getConfiguration(fileEntry);

			if (!gCloudVisionAssetAutoTagProviderCompanyConfiguration.
					enabled() ||
				_isTemporary(fileEntry) || !_isSupportedFormat(fileEntry)) {

				return Collections.emptyList();
			}

			JSONObject responseJSONObject = _queryGCloudVisionJSONObject(
				gCloudVisionAssetAutoTagProviderCompanyConfiguration.apiKey(),
				GCloudVisionUtil.getAnnotateImagePayload(fileEntry));

			JSONArray responsesJSONArray = responseJSONObject.getJSONArray(
				"responses");

			if ((responsesJSONArray != null) &&
				(responsesJSONArray.length() > 0)) {

				JSONObject firstResponseJSONObject =
					responsesJSONArray.getJSONObject(0);

				JSONArray labelAnnotationsJSONArray =
					firstResponseJSONObject.getJSONArray("labelAnnotations");

				return JSONUtil.toStringList(
					labelAnnotationsJSONArray, "description");
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return Collections.emptyList();
	}

	private GCloudVisionAssetAutoTagProviderCompanyConfiguration
			_getConfiguration(FileEntry fileEntry)
		throws ConfigurationException {

		return _configurationProvider.getCompanyConfiguration(
			GCloudVisionAssetAutoTagProviderCompanyConfiguration.class,
			fileEntry.getCompanyId());
	}

	private boolean _isSupportedFormat(FileEntry fileEntry) {
		String extension = fileEntry.getExtension();

		return _supportedFormats.contains(StringUtil.toUpperCase(extension));
	}

	private boolean _isTemporary(FileEntry fileEntry) {
		return fileEntry.isRepositoryCapabilityProvided(
			TemporaryFileEntriesCapability.class);
	}

	private JSONObject _queryGCloudVisionJSONObject(
			String apiKey, String payloadJSON)
		throws Exception {

		Http.Options options = new Http.Options();

		options.addHeader("Content-Type", ContentTypes.APPLICATION_JSON);
		options.setBody(
			payloadJSON, ContentTypes.APPLICATION_JSON, StringPool.UTF8);
		options.setLocation(
			"https://vision.googleapis.com/v1/images:annotate?key=" + apiKey);
		options.setPost(true);

		String responseJSON = _http.URLtoString(options);

		Http.Response response = options.getResponse();

		if (response.getResponseCode() != HttpURLConnection.HTTP_OK) {
			throw new PortalException(
				StringBundler.concat(
					"Response code ", response.getResponseCode(), ": ",
					responseJSON));
		}

		return JSONFactoryUtil.createJSONObject(responseJSON);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GCloudVisionImageAssetAutoTagProvider.class);

	private static final Set<String> _supportedFormats = new HashSet<>(
		Arrays.asList(
			"BMP", "GIF", "ICO", "JPEG", "JPG", "PNG", "RAW", "WEBP"));

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private Http _http;

}