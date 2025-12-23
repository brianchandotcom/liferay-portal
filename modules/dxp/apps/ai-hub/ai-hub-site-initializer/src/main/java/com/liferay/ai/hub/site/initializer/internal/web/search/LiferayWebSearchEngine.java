/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.site.initializer.internal.web.search;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.rest.dto.v1_0.SearchResult;

import dev.langchain4j.web.search.WebSearchEngine;
import dev.langchain4j.web.search.WebSearchInformationResult;
import dev.langchain4j.web.search.WebSearchOrganicResult;
import dev.langchain4j.web.search.WebSearchRequest;
import dev.langchain4j.web.search.WebSearchResults;

import java.io.UnsupportedEncodingException;

import java.net.URI;
import java.net.URLEncoder;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Feliphe Marinho
 */
public class LiferayWebSearchEngine implements WebSearchEngine {

	public LiferayWebSearchEngine(String blueprintExternalReferenceCode) {
		_blueprintExternalReferenceCode = blueprintExternalReferenceCode;
	}

	@Override
	public WebSearchResults search(WebSearchRequest webSearchRequest) {
		JSONObject jsonObject = _search(webSearchRequest);

		JSONArray jsonArray = jsonObject.getJSONArray("items");

		Iterator<JSONObject> iterator = jsonArray.iterator();

		List<WebSearchOrganicResult> webSearchOrganicResults =
			new ArrayList<>();

		iterator.forEachRemaining(
			itemJSONObject -> webSearchOrganicResults.add(
				_toWebSearchOrganicResult(itemJSONObject)));

		return WebSearchResults.from(
			WebSearchInformationResult.from(jsonObject.getLong("totalCount")),
			webSearchOrganicResults);
	}

	private String _getAuthorization() {
		try {

			// TODO replace basic auth with token based authentication

			Base64.Encoder encoder = Base64.getEncoder();

			String userNameAndPassword =
				"test@liferay.com:" + PropsValues.DEFAULT_ADMIN_PASSWORD;

			return "Basic " +
				new String(
					encoder.encode(userNameAndPassword.getBytes("UTF-8")),
					"UTF-8");
		}
		catch (UnsupportedEncodingException unsupportedEncodingException) {
			throw new RuntimeException(unsupportedEncodingException);
		}
	}

	private JSONObject _search(WebSearchRequest webSearchRequest) {
		try {
			Http.Options options = new Http.Options();

			options.addHeader(HttpHeaders.AUTHORIZATION, _getAuthorization());
			options.addHeader(
				HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_JSON);

			// TODO replace http://localhost:8080 with origin's base URL

			String location = "http://localhost:8080/o/search/v1.0/search";

			if (!Validator.isBlank(_blueprintExternalReferenceCode)) {
				location = HttpComponentsUtil.addParameter(
					location, "blueprintExternalReferenceCode",
					_blueprintExternalReferenceCode);
			}

			location = HttpComponentsUtil.addParameter(
				location, "page", webSearchRequest.startPage());
			location = HttpComponentsUtil.addParameter(
				location, "pageSize", webSearchRequest.maxResults());
			location = HttpComponentsUtil.addParameter(
				location, "search", webSearchRequest.searchTerms());

			options.setLocation(location);

			options.setMethod(Http.Method.GET);

			return JSONFactoryUtil.createJSONObject(
				HttpUtil.URLtoString(options));
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		return null;
	}

	private WebSearchOrganicResult _toWebSearchOrganicResult(
		JSONObject jsonObject) {

		SearchResult searchResult = SearchResult.toDTO(jsonObject.toString());

		try {
			String url = "";

			if (searchResult.getItemURL() != null) {
				url = searchResult.getItemURL();
			}

			return WebSearchOrganicResult.from(
				searchResult.getTitle(),
				URI.create(URLEncoder.encode(url, "UTF-8")), null,
				searchResult.getDescription(),
				Map.of("score", String.valueOf(searchResult.getScore())));
		}
		catch (UnsupportedEncodingException unsupportedEncodingException) {
			throw new RuntimeException(unsupportedEncodingException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LiferayWebSearchEngine.class);

	private final String _blueprintExternalReferenceCode;

}