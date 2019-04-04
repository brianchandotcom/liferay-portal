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

package com.liferay.asset.auto.tagger.google.cloud.natural.language.internal;

import com.liferay.asset.auto.tagger.google.cloud.natural.language.api.GCloudNaturalLanguageDocumentAssetAutoTagger;
import com.liferay.asset.auto.tagger.google.cloud.natural.language.api.configuration.GCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration;
import com.liferay.asset.auto.tagger.google.cloud.natural.language.api.constants.GCloudNaturalLanguageAssetAutoTagProviderConstants;
import com.liferay.asset.auto.tagger.google.cloud.natural.language.internal.util.GCloudNaturalLanguageUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.net.HttpURLConnection;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(service = GCloudNaturalLanguageDocumentAssetAutoTagger.class)
public class GCloudNaturalLanguageDocumentAssetAutoTaggerImpl
	implements GCloudNaturalLanguageDocumentAssetAutoTagger {

	@Override
	public Collection<String> getTagNames(
			GCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration
				gCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration,
			String content, Locale locale, String mimeType)
		throws Exception {

		if (!_supportedContentTypes.contains(mimeType)) {
			return Collections.emptySet();
		}

		if (!gCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration.
				classificationEndpointEnabled() &&
			!gCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration.
				entityEndpointEnabled()) {

			return Collections.emptySet();
		}

		String documentPayload = _getDocumentPayload(content, mimeType);

		Collection<String> classificationTagNames = _getClassificationTagNames(
			gCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration,
			documentPayload, locale);

		Collection<String> entitiesTagNames = _getEntitiesTagNames(
			gCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration,
			documentPayload, locale);

		return Stream.concat(
			classificationTagNames.stream(), entitiesTagNames.stream()
		).collect(
			Collectors.toSet()
		);
	}

	@Override
	public Collection<String> getTagNames(
			GCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration
				gCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration,
			String content, String mimeType)
		throws Exception {

		return getTagNames(
			gCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration,
			content, null, mimeType);
	}

	private static <T> Predicate<T> _negate(Predicate<T> predicate) {
		return predicate.negate();
	}

	private Collection<String> _getClassificationTagNames(
			GCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration
				gCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration,
			String documentPayload, Locale locale)
		throws Exception {

		if (!gCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration.
				classificationEndpointEnabled()) {

			return Collections.emptySet();
		}

		if (Objects.nonNull(locale) &&
			!Objects.equals(
				locale.getLanguage(), Locale.ENGLISH.getLanguage())) {

			return Collections.emptySet();
		}

		JSONObject responseJSONObject = _post(
			_getServiceURL(
				gCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration.
					apiKey(),
				"classifyText"),
			documentPayload);
		float confidence =
			gCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration.
				confidence();

		return _toTagNames(
			responseJSONObject.getJSONArray("categories"),
			jsonObject -> jsonObject.getDouble("confidence") > confidence);
	}

	private String _getDocumentPayload(String content, String mimeType) {
		String type = GCloudNaturalLanguageUtil.getType(mimeType);

		int size =
			GCloudNaturalLanguageAssetAutoTagProviderConstants.
				MAX_CHARACTERS_SERVICE - _MINIMUM_PAYLOAD_SIZE - type.length();

		String truncatedContent = GCloudNaturalLanguageUtil.truncateToSize(
			content, size);

		return GCloudNaturalLanguageUtil.getDocumentPayload(
			truncatedContent, type);
	}

	private Collection<String> _getEntitiesTagNames(
			GCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration
				gCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration,
			String documentPayload, Locale locale)
		throws Exception {

		if (!gCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration.
				entityEndpointEnabled()) {

			return Collections.emptySet();
		}

		if (Objects.nonNull(locale) &&
			_supportedEntityLanguages.contains(locale.getLanguage())) {

			return Collections.emptySet();
		}

		JSONObject responseJSONObject = _post(
			_getServiceURL(
				gCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration.
					apiKey(),
				"analyzeEntities"),
			documentPayload);
		float salience =
			gCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration.
				salience();

		return _toTagNames(
			responseJSONObject.getJSONArray("entities"),
			jsonObject -> jsonObject.getDouble("salience") > salience);
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
				"Unable to generate tags with the Google Natural Language ",
				"service. Response code ", response.getResponseCode(), ": ",
				errorMessage));
	}

	private Set<String> _toTagNames(
		JSONArray jsonArray, Predicate<JSONObject> predicate) {

		if (jsonArray == null) {
			return Collections.emptySet();
		}

		return StreamSupport.stream(
			(Spliterator<JSONObject>)jsonArray.spliterator(), false
		).filter(
			predicate
		).map(
			jsonObject -> StringUtil.removeChars(
				jsonObject.getString("name"), CharPool.APOSTROPHE,
				CharPool.DASH)
		).map(
			tagName -> StringUtil.split(tagName, CharPool.AMPERSAND)
		).flatMap(
			Stream::of
		).map(
			tagNamePart -> StringUtil.split(tagNamePart, CharPool.FORWARD_SLASH)
		).flatMap(
			Stream::of
		).map(
			String::trim
		).filter(
			_negate(String::isEmpty)
		).collect(
			Collectors.toSet()
		);
	}

	private static final int _MINIMUM_PAYLOAD_SIZE;

	private static final Set<String> _supportedContentTypes = new HashSet<>(
		Arrays.asList(
			"application/epub+zip", "application/vnd.apple.pages.13",
			"application/vnd.google-apps.document",
			"application/vnd.openxmlformats-officedocument.wordprocessingml." +
				"document",
			ContentTypes.APPLICATION_MSWORD, ContentTypes.APPLICATION_PDF,
			ContentTypes.APPLICATION_TEXT, ContentTypes.TEXT_HTML,
			ContentTypes.TEXT_PLAIN));
	private static final Set<String> _supportedEntityLanguages = new HashSet<>(
		Arrays.asList(
			Locale.CHINESE.getLanguage(), Locale.ENGLISH.getLanguage(),
			Locale.FRENCH.getLanguage(), Locale.GERMAN.getLanguage(),
			Locale.ITALIAN.getLanguage(), Locale.JAPAN.getLanguage(),
			Locale.KOREAN.getLanguage(), LocaleUtil.PORTUGAL.getLanguage(),
			LocaleUtil.SPAIN.getLanguage()));

	static {
		String payload = GCloudNaturalLanguageUtil.getDocumentPayload(
			StringPool.BLANK, StringPool.BLANK);

		_MINIMUM_PAYLOAD_SIZE = payload.length();
	}

	@Reference
	private Http _http;

}