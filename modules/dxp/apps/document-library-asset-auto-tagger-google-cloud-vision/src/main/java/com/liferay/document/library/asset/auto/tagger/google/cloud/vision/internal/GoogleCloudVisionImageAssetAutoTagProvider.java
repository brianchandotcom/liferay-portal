/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.document.library.asset.auto.tagger.google.cloud.vision.internal;

import com.liferay.asset.auto.tagger.AssetAutoTagProvider;
import com.liferay.document.library.asset.auto.tagger.google.cloud.vision.internal.configuration.GoogleCloudVisionAssetAutoTagProviderConfiguration;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.capabilities.TemporaryFileEntriesCapability;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

import java.net.HttpURLConnection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

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
			JSONObject responseJSONObject = _queryGoogleCloudVisionJSONObject(
				_getRequestJSONPayload(fileEntry));

			JSONArray responsesJSONArray = responseJSONObject.getJSONArray(
				"responses");

			List<String> tagNames = new ArrayList<>();

			if ((responsesJSONArray != null) &&
				(responsesJSONArray.length() > 0)) {

				JSONObject firstResponseJSONObject =
					responsesJSONArray.getJSONObject(0);

				JSONArray labelAnnotationsJSONArray =
					firstResponseJSONObject.getJSONArray("labelAnnotations");

				if (labelAnnotationsJSONArray != null) {
					for (int i = ; i < labelAnnotationsJSONArray.length();
							i++) {

						JSONObject labelAnnotationJSONObject =
							labelAnnotationsJSONArray.getJSONObject(i);

						tagNames.add(
							labelAnnotationJSONObject.getString("description"));
					}
				}
			}

			return tagNames;
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

	private String _getRequestJSONPayload(FileEntry fileEntry)
		throws IOException, PortalException {

		JSONObject payloadJSONObject = JSONFactoryUtil.createJSONObject();

		JSONArray requestsJSONArray = JSONFactoryUtil.createJSONArray();

		JSONObject requestJSONObject = JSONFactoryUtil.createJSONObject();

		JSONObject imageJSONObject = JSONFactoryUtil.createJSONObject();

		FileVersion fileVersion = fileEntry.getFileVersion();

		imageJSONObject.put(
			"content",
			Base64.encode(
				FileUtil.getBytes(fileVersion.getContentStream(false))));

		requestJSONObject.put("image", imageJSONObject);

		JSONArray featuresJSONArray = JSONFactoryUtil.createJSONArray();

		JSONObject featureJSONObject = JSONFactoryUtil.createJSONObject();

		featureJSONObject.put("type", "LABEL_DETECTION");

		featuresJSONArray.put(featureJSONObject);

		requestJSONObject.put("features", featuresJSONArray);

		requestsJSONArray.put(requestJSONObject);

		payloadJSONObject.put("requests", requestsJSONArray);

		return payloadJSONObject.toString();
	}

	private boolean _isFormatSupported(FileEntry fileEntry) {
		String extension = fileEntry.getExtension();

		return _supportedFormats.contains(StringUtil.toUpperCase(extension));
	}

	private boolean _isTemporary(FileEntry fileEntry) {
		return fileEntry.isRepositoryCapabilityProvided(
			TemporaryFileEntriesCapability.class);
	}

	private JSONObject _queryGoogleCloudVisionJSONObject(String jsonPayload)
		throws IOException, PortalException {

		Http.Options options = new Http.Options();

		options.setBody(
			jsonPayload, ContentTypes.APPLICATION_JSON, StringPool.UTF8);
		options.addHeader("Content-Type", ContentTypes.APPLICATION_JSON);
		options.setLocation(
			"https://vision.googleapis.com/v1/images:annotate?key=" +
				_googleCloudVisionConfiguration.apiKey());
		options.setPost(true);

		String responseContent = _http.URLtoString(options);

		Http.Response response = options.getResponse();

		if (response.getResponseCode() != HttpURLConnection.HTTP_OK) {
			throw new PortalException(
				String.format(
					"Request failed with status %d: %s",
					response.getResponseCode(), responseContent));
		}

		return JSONFactoryUtil.createJSONObject(responseContent);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GoogleCloudVisionImageAssetAutoTagProvider.class);

	private static final List<String> _supportedFormats = Arrays.asList(
		"JPEG", "JPG", "PNG", "GIF", "BMP", "WEBP", "RAW", "ICO");

	private volatile GoogleCloudVisionAssetAutoTagProviderConfiguration
		_googleCloudVisionConfiguration;

	@Reference
	private Http _http;

}