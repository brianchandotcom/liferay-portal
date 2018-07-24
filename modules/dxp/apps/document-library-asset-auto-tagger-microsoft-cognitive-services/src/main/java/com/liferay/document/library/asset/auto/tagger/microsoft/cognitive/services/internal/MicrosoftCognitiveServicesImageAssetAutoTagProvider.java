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

package com.liferay.document.library.asset.auto.tagger.microsoft.cognitive.services.internal;

import com.liferay.asset.auto.tagger.AssetAutoTagProvider;
import com.liferay.document.library.asset.auto.tagger.microsoft.cognitive.services.internal.configuration.MicrosoftCognitiveServicesAssetAutoTagProviderConfiguration;
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
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.Http;
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
	configurationPid = "com.liferay.document.library.asset.auto.tagger.microsoft.cognitive.services.internal.configuration.MicrosoftCognitiveServicesAssetAutoTagProviderConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE,
	property = "model.class.name=com.liferay.document.library.kernel.model.DLFileEntry",
	service = AssetAutoTagProvider.class
)
public class MicrosoftCognitiveServicesImageAssetAutoTagProvider
	implements AssetAutoTagProvider<FileEntry> {

	@Override
	public List<String> getTagNames(FileEntry fileEntry) {
		if (!_microsoftCognitiveServicesConfiguration.enabled() ||
			_isTemporary(fileEntry) || (fileEntry.getSize() > _MAX_SIZE) ||
			!_isSupportedFormat(fileEntry)) {

			return Collections.emptyList();
		}

		try {
			FileVersion fileVersion = fileEntry.getFileVersion();

			JSONObject responseJSONObject = _queryComputerVisionJSONObject(
				fileVersion);

			JSONArray tagsJSONArray = responseJSONObject.getJSONArray("tags");

			return JSONUtil.toStringList(tagsJSONArray, "name");
		}
		catch (IOException | PortalException e) {
			_log.error(e, e);

			return Collections.emptyList();
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_microsoftCognitiveServicesConfiguration =
			ConfigurableUtil.createConfigurable(
				MicrosoftCognitiveServicesAssetAutoTagProviderConfiguration.
					class,
				properties);
	}

	private boolean _isSupportedFormat(FileEntry fileEntry) {
		String extension = fileEntry.getExtension();

		return _supportedFormats.contains(StringUtil.toUpperCase(extension));
	}

	private boolean _isTemporary(FileEntry fileEntry) {
		return fileEntry.isRepositoryCapabilityProvided(
			TemporaryFileEntriesCapability.class);
	}

	private JSONObject _queryComputerVisionJSONObject(FileVersion fileVersion)
		throws IOException, PortalException {

		URL url = new URL(
			_microsoftCognitiveServicesConfiguration.apiEndpoint() + "/tag");

		URLConnection connection = url.openConnection();

		HttpURLConnection http = (HttpURLConnection)connection;

		http.setDoOutput(true);
		http.setRequestMethod("POST");
		http.setRequestProperty("Content-Type", "application/octet-stream");
		http.setRequestProperty(
			"Ocp-Apim-Subscription-Key",
			_microsoftCognitiveServicesConfiguration.apiKey());

		try (OutputStream outputStream = http.getOutputStream()) {
			outputStream.write(
				FileUtil.getBytes(fileVersion.getContentStream(false)));
		}

		http.getResponseMessage();

		try (InputStream inputStream = http.getInputStream()) {
			return JSONFactoryUtil.createJSONObject(StringUtil.read(inputStream));
		}
		catch (Exception e) {
			try (InputStream inputStream = http.getErrorStream()) {
				throw new PortalException(
					"Response code " + http.getResponseCode()  + ":" +
						StringUtil.read(inputStream),
					e);
			}
		}
	}

	private static final int _MAX_SIZE = 4 * 1024 * 1024;

	private static final Log _log = LogFactoryUtil.getLog(
		MicrosoftCognitiveServicesImageAssetAutoTagProvider.class);

	private static final Set<String> _supportedFormats = new HashSet<>(
		Arrays.asList(
			"BMP", "GIF",  "JPEG", "JPG", "PNG"));

	@Reference
	private Http _http;

	private volatile MicrosoftCognitiveServicesAssetAutoTagProviderConfiguration
		_microsoftCognitiveServicesConfiguration;

}