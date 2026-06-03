/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.web.search;

import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.service.OAuth2ApplicationLocalServiceUtil;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.security.auth.CompanyInheritableThreadLocalCallable;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.InetAddressUtil;
import com.liferay.portal.kernel.util.PortalRunMode;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.rest.dto.v1_0.SearchResult;

import dev.langchain4j.web.search.WebSearchEngine;
import dev.langchain4j.web.search.WebSearchInformationResult;
import dev.langchain4j.web.search.WebSearchOrganicResult;
import dev.langchain4j.web.search.WebSearchRequest;
import dev.langchain4j.web.search.WebSearchResults;

import java.net.URI;
import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author Feliphe Marinho
 */
public class LiferayWebSearchEngine implements WebSearchEngine {

	public LiferayWebSearchEngine(
		String blueprintExternalReferenceCode, long oAuth2ApplicationId,
		String userToken) {

		_blueprintExternalReferenceCode = blueprintExternalReferenceCode;
		_oAuth2ApplicationId = oAuth2ApplicationId;
		_userToken = userToken;

		_searchCallable = new CompanyInheritableThreadLocalCallable<>(
			() -> _search(_webSearchRequest));
	}

	@Override
	public WebSearchResults search(WebSearchRequest webSearchRequest) {
		_webSearchRequest = webSearchRequest;

		try {
			return _searchCallable.call();
		}
		catch (Exception exception) {
			return ReflectionUtil.throwException(exception);
		}
	}

	private WebSearchResults _search(WebSearchRequest webSearchRequest)
		throws Exception {

		if (_oAuth2ApplicationId == 0L) {
			throw new IllegalArgumentException(
				"OAuth 2 application ID is required");
		}

		Http.Options options = new Http.Options();

		options.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + _userToken);
		options.addHeader(
			HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_JSON);

		OAuth2Application oAuth2Application =
			OAuth2ApplicationLocalServiceUtil.getOAuth2Application(
				_oAuth2ApplicationId);

		String homePageURL = oAuth2Application.getHomePageURL();

		URL urlObject = new URL(homePageURL);

		if (!PortalRunMode.isTestMode() &&
			InetAddressUtil.isLocalInetAddress(
				InetAddressUtil.getInetAddressByName(urlObject.getHost()))) {

			throw new SecurityException(
				"Local links are not allowed: " + urlObject);
		}

		List<WebSearchOrganicResult> webSearchOrganicResults =
			new ArrayList<>();

		String location = homePageURL + "/o/search/v1.0/search";

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

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			HttpUtil.URLtoString(options));

		for (JSONObject itemJSONObject :
				(Iterable<JSONObject>)jsonObject.getJSONArray("items")) {

			SearchResult searchResult = SearchResult.toDTO(
				itemJSONObject.toString());

			float score = searchResult.getScore();

			if (score < 5) {
				continue;
			}

			String itemURL = homePageURL;

			if (searchResult.getItemURL() != null) {
				itemURL = searchResult.getItemURL();
			}

			webSearchOrganicResults.add(
				WebSearchOrganicResult.from(
					searchResult.getTitle(), URI.create(itemURL), null,
					searchResult.getDescription(),
					Map.of("score", String.valueOf(score))));
		}

		return WebSearchResults.from(
			WebSearchInformationResult.from(jsonObject.getLong("totalCount")),
			webSearchOrganicResults);
	}

	private final String _blueprintExternalReferenceCode;
	private final long _oAuth2ApplicationId;
	private final Callable<WebSearchResults> _searchCallable;
	private final String _userToken;
	private WebSearchRequest _webSearchRequest;

}