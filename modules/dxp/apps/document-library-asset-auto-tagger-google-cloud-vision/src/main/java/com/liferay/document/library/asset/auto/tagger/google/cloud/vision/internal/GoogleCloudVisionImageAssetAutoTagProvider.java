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
import com.liferay.portal.kernel.json.JSONUtil;
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

import java.net.HttpURLConnection;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
			_isTemporary(fileEntry) || !_isSupportedFormat(fileEntry)) {

			return Collections.emptyList();
		}

		try {
			JSONObject responseJSONObject = _queryGoogleCloudVisionJSONObject(
				_getPayloadJSON(fileEntry));

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

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_googleCloudVisionConfiguration = ConfigurableUtil.createConfigurable(
			GoogleCloudVisionAssetAutoTagProviderConfiguration.class,
			properties);
	}

	private String _getPayloadJSON(FileEntry fileEntry) throws Exception {
		FileVersion fileVersion = fileEntry.getFileVersion();

		JSONObject jsonObject = JSONUtil.put(
			"requests",
			JSONUtil.put(
				JSONUtil.put(
					"features",
					JSONUtil.put(
						JSONUtil.put("type", "LABEL_DETECTION")
					)
				).put(
					"image",
					JSONUtil.put(
						"content",
						Base64.encode(
							FileUtil.getBytes(
								fileVersion.getContentStream(false))))
				)
			)
		);

		return jsonObject.toString();
	}

	private boolean _isSupportedFormat(FileEntry fileEntry) {
		String extension = fileEntry.getExtension();

		return _supportedFormats.contains(StringUtil.toUpperCase(extension));
	}

	private boolean _isTemporary(FileEntry fileEntry) {
		return fileEntry.isRepositoryCapabilityProvided(
			TemporaryFileEntriesCapability.class);
	}

	private JSONObject _queryGoogleCloudVisionJSONObject(String payloadJSON)
		throws Exception {

		Http.Options options = new Http.Options();

		options.addHeader("Content-Type", ContentTypes.APPLICATION_JSON);
		options.setBody(
			payloadJSON, ContentTypes.APPLICATION_JSON, StringPool.UTF8);
		options.setLocation(
			"https://vision.googleapis.com/v1/images:annotate?key=" +
				_googleCloudVisionConfiguration.apiKey());
		options.setPost(true);

		String responseJSON = _http.URLtoString(options);

		Http.Response response = options.getResponse();

		if (response.getResponseCode() != HttpURLConnection.HTTP_OK) {
			throw new PortalException(
				"Response code " + response.getResponseCode() + ": " +
					responseJSON);
		}

		return JSONFactoryUtil.createJSONObject(responseJSON);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GoogleCloudVisionImageAssetAutoTagProvider.class);

	private static final Set<String> _supportedFormats = new HashSet<>(
		Arrays.asList(
			"BMP", "GIF", "ICO", "JPEG", "JPG", "PNG", "RAW", "WEBP"));

	private volatile GoogleCloudVisionAssetAutoTagProviderConfiguration
		_googleCloudVisionConfiguration;

	@Reference
	private Http _http;

}