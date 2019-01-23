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
import com.liferay.document.library.asset.auto.tagger.google.cloud.vision.internal.configuration.GoogleCloudVisionAssetAutoTagProviderCompanyConfiguration;
import com.liferay.document.library.asset.auto.tagger.google.cloud.vision.internal.constants.GoogleCloudVisionAssetAutoTagProviderConstants;
import com.liferay.document.library.asset.auto.tagger.google.cloud.vision.internal.util.GoogleCloudVisionUtil;
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
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringUtil;

import java.net.HttpURLConnection;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
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
public class GoogleCloudVisionImageAssetAutoTagProvider
	implements AssetAutoTagProvider<FileEntry> {

	@Override
	public List<String> getTagNames(FileEntry fileEntry) {
		try {
			GoogleCloudVisionAssetAutoTagProviderCompanyConfiguration
				googleCloudVisionAssetAutoTagProviderCompanyConfiguration =
					_getConfiguration(fileEntry);

			if (!googleCloudVisionAssetAutoTagProviderCompanyConfiguration.
					enabled() ||
				_isTemporary(fileEntry) || !_isSupportedFormat(fileEntry)) {

				return Collections.emptyList();
			}

			JSONObject responseJSONObject = _queryGoogleCloudVisionJSONObject(
				googleCloudVisionAssetAutoTagProviderCompanyConfiguration.
					apiKey(),
				GoogleCloudVisionUtil.getAnnotateImagePayload(fileEntry));

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

	private GoogleCloudVisionAssetAutoTagProviderCompanyConfiguration
			_getConfiguration(FileEntry fileEntry)
		throws ConfigurationException {

		return _configurationProvider.getConfiguration(
			GoogleCloudVisionAssetAutoTagProviderCompanyConfiguration.class,
			new CompanyServiceSettingsLocator(
				fileEntry.getCompanyId(),
				GoogleCloudVisionAssetAutoTagProviderConstants.SERVICE_NAME));
	}

	private boolean _isSupportedFormat(FileEntry fileEntry) {
		String extension = fileEntry.getExtension();

		return _supportedFormats.contains(StringUtil.toUpperCase(extension));
	}

	private boolean _isTemporary(FileEntry fileEntry) {
		return fileEntry.isRepositoryCapabilityProvided(
			TemporaryFileEntriesCapability.class);
	}

	private JSONObject _queryGoogleCloudVisionJSONObject(
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

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private Http _http;

}