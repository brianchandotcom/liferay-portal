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
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

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
			!_isFormatSupported(fileEntry)) {

			return Collections.emptyList();
		}

		try {
			FileVersion fileVersion = fileEntry.getFileVersion();

			JSONObject responseJSONObject = _queryComputerVisionJSONObject(
				fileVersion);

			JSONArray tagsJSONArray = responseJSONObject.getJSONArray("tags");

			List<String> tagNames = new ArrayList<>();

			if (tagsJSONArray != null) {
				for (int i = 0; i < tagsJSONArray.length(); i++) {
					JSONObject tagJSONObject = tagsJSONArray.getJSONObject(i);

					tagNames.add(tagJSONObject.getString("name"));
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
		_microsoftCognitiveServicesConfiguration =
			ConfigurableUtil.createConfigurable(
				MicrosoftCognitiveServicesAssetAutoTagProviderConfiguration.
					class,
				properties);
	}

	private boolean _isFormatSupported(FileEntry fileEntry) {
		String extension = fileEntry.getExtension();

		return _supportedFormats.contains(StringUtil.toUpperCase(extension));
	}

	private boolean _isTemporary(FileEntry fileEntry) {
		return fileEntry.isRepositoryCapabilityProvided(
			TemporaryFileEntriesCapability.class);
	}

	private JSONObject _queryComputerVisionJSONObject(FileVersion fileVersion)
		throws IOException, PortalException {

		Http.Options options = new Http.Options();

		options.addHeader(
			"Ocp-Apim-Subscription-Key",
			_microsoftCognitiveServicesConfiguration.apiKey());
		options.addFilePart(
			HtmlUtil.escape(fileVersion.getTitle()),
			HtmlUtil.escape(fileVersion.getFileName()),
			FileUtil.getBytes(fileVersion.getContentStream(false)),
			fileVersion.getMimeType(), StringPool.UTF8);
		options.setLocation(
			_microsoftCognitiveServicesConfiguration.apiEndpoint() + "/tag");
		options.setPost(true);

		return JSONFactoryUtil.createJSONObject(_http.URLtoString(options));
	}

	private static final int _MAX_SIZE = 4 * 1024 * 1024;

	private static final Log _log = LogFactoryUtil.getLog(
		MicrosoftCognitiveServicesImageAssetAutoTagProvider.class);

	private static final List<String> _supportedFormats = Arrays.asList(
		"JPEG", "JPG", "PNG", "GIF", "BMP");

	@Reference
	private Http _http;

	private volatile MicrosoftCognitiveServicesAssetAutoTagProviderConfiguration
		_microsoftCognitiveServicesConfiguration;

}