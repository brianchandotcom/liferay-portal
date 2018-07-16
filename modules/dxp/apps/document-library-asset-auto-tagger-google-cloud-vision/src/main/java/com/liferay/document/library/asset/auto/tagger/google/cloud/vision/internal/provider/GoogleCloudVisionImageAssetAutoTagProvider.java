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

package com.liferay.document.library.asset.auto.tagger.google.cloud.vision.internal.provider;

import com.liferay.asset.auto.tagger.AssetAutoTagProvider;
import com.liferay.document.library.asset.auto.tagger.google.cloud.vision.internal.configuration.GoogleCloudVisionAssetAutoTagProviderConfiguration;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.capabilities.TemporaryFileEntriesCapability;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Alejandro Tardín
 */
@Component(
	configurationPid = "com.liferay.document.library.asset.auto.tagger.google.cloud.vision.internal.configuration.GoogleCloudVisionAssetAutoTagProviderConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE,
	property = "model.class.name=com.liferay.document.library.kernel.model.DLFileEntry",
	service = AssetAutoTagProvider.class
)
public class GoogleCloudVisionImageAssetAutoTagProvider
	implements AssetAutoTagProvider<FileEntry> {

	@Override
	public List<String> getTagNames(FileEntry fileEntry) {
		if (!_googleCloudVisionConfiguration.enabled() ||
			_isTemporary(fileEntry) || !_isFormatSupported(fileEntry)) {

			return Collections.emptyList();
		}

		try {
			JSONObject responseObject = _queryGoogleVision(
				_getRequestJSON(fileEntry));

			JSONArray responsesArray = responseObject.getJSONArray("responses");

			List<String> tags = new ArrayList<>();

			if ((responsesArray != null) && (responsesArray.length() > 0)) {
				JSONObject firstResponse = responsesArray.getJSONObject(0);

				JSONArray labelAnnotations = firstResponse.getJSONArray(
					"labelAnnotations");

				if (labelAnnotations != null) {
					for (int i = 0; i < labelAnnotations.length(); i++) {
						JSONObject labelAnnotation =
							labelAnnotations.getJSONObject(i);

						tags.add(labelAnnotation.getString("description"));
					}
				}
			}

			return tags;
		}
		catch (IOException | PortalException e) {
			_log.error(e, e);

			return Collections.emptyList();
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_googleCloudVisionConfiguration = ConfigurableUtil.createConfigurable(
			GoogleCloudVisionAssetAutoTagProviderConfiguration.class,
			properties);
	}

	private String _getRequestJSON(FileEntry fileEntry)
		throws IOException, PortalException {

		JSONObject requestObject = JSONFactoryUtil.createJSONObject();

		JSONObject imageObject = JSONFactoryUtil.createJSONObject();

		FileVersion fileVersion = fileEntry.getFileVersion();

		imageObject.put(
			"content",
			Base64.encode(
				FileUtil.getBytes(fileVersion.getContentStream(false))));

		requestObject.put("image", imageObject);

		JSONObject featureObject = JSONFactoryUtil.createJSONObject();

		featureObject.put("type", "LABEL_DETECTION");

		JSONArray featuresArray = JSONFactoryUtil.createJSONArray();

		featuresArray.put(featureObject);

		requestObject.put("features", featuresArray);

		JSONArray requestsArray = JSONFactoryUtil.createJSONArray();

		requestsArray.put(requestObject);

		JSONObject payload = JSONFactoryUtil.createJSONObject();

		payload.put("requests", requestsArray);

		return payload.toString();
	}

	private boolean _isFormatSupported(FileEntry fileEntry) {
		String extension = fileEntry.getExtension();

		return _supportedFormats.contains(StringUtil.toUpperCase(extension));
	}

	private boolean _isTemporary(FileEntry fileEntry) {
		return fileEntry.isRepositoryCapabilityProvided(
			TemporaryFileEntriesCapability.class);
	}

	private JSONObject _queryGoogleVision(String jsonPayload)
		throws IOException, JSONException {

		URL url = new URL(
			"https://vision.googleapis.com/v1/images:annotate?key=" +
				_googleCloudVisionConfiguration.apiKey());

		URLConnection connection = url.openConnection();

		HttpURLConnection http = (HttpURLConnection)connection;

		http.setRequestMethod("POST");
		http.setDoOutput(true);

		http.setRequestProperty("Content-Type", "application/json");

		try (OutputStream os = http.getOutputStream()) {
			os.write(jsonPayload.getBytes());
		}

		http.getResponseMessage();

		try (InputStream is = http.getInputStream()) {
			return JSONFactoryUtil.createJSONObject(StringUtil.read(is));
		}
		catch (Exception e) {
			try (InputStream is = http.getErrorStream()) {
				_log.error(StringUtil.read(is));

				throw e;
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GoogleCloudVisionImageAssetAutoTagProvider.class);

	private static final List<String> _supportedFormats = Arrays.asList(
		"JPEG", "JPG", "PNG", "GIF", "BMP", "WEBP", "RAW", "ICO");

	private volatile GoogleCloudVisionAssetAutoTagProviderConfiguration
		_googleCloudVisionConfiguration;

}