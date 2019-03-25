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
import com.liferay.document.library.asset.auto.tagger.google.cloud.natural.language.internal.configuration.GoogleCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration;
import com.liferay.document.library.asset.auto.tagger.google.cloud.natural.language.internal.constants.GoogleCloudNaturalLanguageAssetAutoTagProviderConstants;
import com.liferay.document.library.asset.auto.tagger.google.cloud.natural.language.internal.util.GoogleCloudNaturalLanguageUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
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
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;

import java.net.HttpURLConnection;

import java.nio.charset.StandardCharsets;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alicia García
 */
@Component(
	property = "model.class.name=com.liferay.document.library.kernel.model.DLFileEntry",
	service = AssetAutoTagProvider.class
)
public class GoogleCloudNaturalLanguageDocumentAssetAutoTagProvider
	implements AssetAutoTagProvider<FileEntry> {

	@Override
	public Collection<String> getTagNames(FileEntry fileEntry) {
		try {
			if (fileEntry.isRepositoryCapabilityProvided(
					TemporaryFileEntriesCapability.class) ||
				!_supportedContentTypes.contains(fileEntry.getMimeType())) {

				return Collections.emptyList();
			}

			GoogleCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration
				googleCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration =
					_getConfiguration(fileEntry);

			if (!googleCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration.
					classificationEndpointEnabled() &&
				!googleCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration.
					entityEndpointEnabled()) {

				return Collections.emptyList();
			}

			Set<String> tagNames = new HashSet<>();

			String apiKey =
				googleCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration.
					apiKey();

			String type = _getFileEntryType(fileEntry);

			int size =
				GoogleCloudNaturalLanguageAssetAutoTagProviderConstants.
					MAX_CHARACTERS_SERVICE - _MINIMUM_PAYLOAD_SIZE -
						type.length();

			String truncatedContent =
				GoogleCloudNaturalLanguageUtil.truncateToSize(
					_getFileEntryContent(fileEntry), size);

			String documentPayload =
				GoogleCloudNaturalLanguageUtil.getDocumentPayload(
					truncatedContent, type);

			if (googleCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration.
					classificationEndpointEnabled()) {

				JSONObject responseJSONObject = _post(
					_getServiceURL(apiKey, "classifyText"), documentPayload);

				JSONArray jsonArray = responseJSONObject.getJSONArray(
					"categories");

				float confidence =
					googleCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration.
						confidence();

				_processTagNames(
					jsonArray,
					jsonObject ->
						jsonObject.getDouble("confidence") > confidence,
					tagNames::add);
			}

			if (googleCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration.
					entityEndpointEnabled()) {

				JSONObject responseJSONObject = _post(
					_getServiceURL(apiKey, "analyzeEntities"), documentPayload);

				JSONArray jsonArray = responseJSONObject.getJSONArray(
					"entities");

				float salience =
					googleCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration.
						salience();

				_processTagNames(
					jsonArray,
					jsonObject -> jsonObject.getDouble("salience") > salience,
					tagNames::add);
			}

			return tagNames;
		}
		catch (Exception e) {
			_log.error(e, e);

			return Collections.emptyList();
		}
	}

	private static <T> Predicate<T> _negate(Predicate<T> predicate) {
		return predicate.negate();
	}

	private GoogleCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration
			_getConfiguration(FileEntry fileEntry)
		throws ConfigurationException {

		return _configurationProvider.getConfiguration(
			GoogleCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration.
				class,
			new CompanyServiceSettingsLocator(
				fileEntry.getCompanyId(),
				GoogleCloudNaturalLanguageAssetAutoTagProviderConstants.
					SERVICE_NAME));
	}

	private String _getFileEntryContent(FileEntry fileEntry)
		throws IOException, PortalException {

		FileVersion fileVersion = fileEntry.getFileVersion();

		String mimeType = fileVersion.getMimeType();

		if (mimeType.equals(ContentTypes.TEXT_PLAIN) ||
			mimeType.equals(ContentTypes.TEXT_HTML)) {

			try (InputStream is = fileVersion.getContentStream(false)) {
				return new String(
					FileUtil.getBytes(is), StandardCharsets.UTF_8);
			}
		}

		try (InputStream is = fileVersion.getContentStream(false)) {
			return FileUtil.extractText(is, fileVersion.getFileName());
		}
	}

	private String _getFileEntryType(FileEntry fileEntry) {
		if (ContentTypes.TEXT_HTML.equals(fileEntry.getMimeType())) {
			return "HTML";
		}

		return "PLAIN_TEXT";
	}

	private String _getServiceURL(String apiKey, String endpoint) {
		return StringBundler.concat(
			"https://language.googleapis.com/v1/documents:", endpoint, "?key=",
			apiKey);
	}

	private JSONObject _post(String serviceURL, String body) throws Exception {
		Http.Options options = new Http.Options();

		options.setBody(body, ContentTypes.APPLICATION_JSON, StringPool.UTF8);
		options.addHeader("Content-Type", ContentTypes.APPLICATION_JSON);
		options.setLocation(serviceURL);
		options.setPost(true);

		String responseJSON = _http.URLtoString(options);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(responseJSON);

		Http.Response response = options.getResponse();

		if (response.getResponseCode() == HttpURLConnection.HTTP_OK) {
			return jsonObject;
		}

		JSONObject errorJSONObject = jsonObject.getJSONObject("error");

		String errorMessage = responseJSON;

		if (errorJSONObject != null) {
			errorMessage = errorJSONObject.getString("message");
		}

		throw new PortalException(
			StringBundler.concat(
				"Cannot generate tags with Google Natural Language service; ",
				"Response code ", response.getResponseCode(), ": ",
				errorMessage));
	}

	private void _processTagNames(
		JSONArray jsonArray, Predicate<JSONObject> predicate,
		Consumer<String> consumer) {

		if (jsonArray == null) {
			return;
		}

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			if (predicate.test(jsonObject)) {
				String tagName = StringUtil.removeChars(
					jsonObject.getString("name"), CharPool.APOSTROPHE,
					CharPool.DASH);

				Stream.of(
					StringUtil.split(tagName, CharPool.AMPERSAND)
				).flatMap(
					tagNamePart -> Stream.of(
						StringUtil.split(tagNamePart, CharPool.FORWARD_SLASH))
				).map(
					String::trim
				).filter(
					_negate(String::isEmpty)
				).forEach(
					consumer
				);
			}
		}
	}

	private static final int _MINIMUM_PAYLOAD_SIZE;

	private static final Log _log = LogFactoryUtil.getLog(
		GoogleCloudNaturalLanguageDocumentAssetAutoTagProvider.class);

	private static final Set<String> _supportedContentTypes = new HashSet<>(
		Arrays.asList(
			"application/epub+zip", "application/vnd.apple.pages.13",
			"application/vnd.google-apps.document",
			"application/vnd.openxmlformats-officedocument.wordprocessingml." +
				"document",
			ContentTypes.APPLICATION_MSWORD, ContentTypes.APPLICATION_PDF,
			ContentTypes.APPLICATION_TEXT, ContentTypes.TEXT_HTML,
			ContentTypes.TEXT_PLAIN));

	static {
		String payload = GoogleCloudNaturalLanguageUtil.getDocumentPayload(
			StringPool.BLANK, StringPool.BLANK);

		_MINIMUM_PAYLOAD_SIZE = payload.length();
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private Http _http;

}