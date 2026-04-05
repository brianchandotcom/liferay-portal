/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.web.search;

import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.model.OAuth2Authorization;
import com.liferay.oauth2.provider.service.OAuth2ApplicationLocalServiceUtil;
import com.liferay.oauth2.provider.service.OAuth2AuthorizationLocalServiceUtil;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.rest.dto.v1_0.SearchResult;

import dev.langchain4j.web.search.WebSearchEngine;
import dev.langchain4j.web.search.WebSearchInformationResult;
import dev.langchain4j.web.search.WebSearchOrganicResult;
import dev.langchain4j.web.search.WebSearchRequest;
import dev.langchain4j.web.search.WebSearchResults;

import java.net.URI;
import java.net.URLEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Feliphe Marinho
 */
public class LiferayWebSearchEngine implements WebSearchEngine {

	public LiferayWebSearchEngine(
		String accessToken, String blueprintExternalReferenceCode,
		long companyId, String userToken) {

		_accessToken = accessToken;
		_blueprintExternalReferenceCode = blueprintExternalReferenceCode;
		_companyId = companyId;
		_userToken = userToken;
	}

	@Override
	public WebSearchResults search(WebSearchRequest webSearchRequest) {
		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setCompanyIdWithSafeCloseable(_companyId)) {

			return _search(webSearchRequest);
		}
		catch (Exception exception) {
			return ReflectionUtil.throwException(exception);
		}
	}

	private WebSearchResults _search(WebSearchRequest webSearchRequest)
		throws Exception {

		List<WebSearchOrganicResult> webSearchOrganicResults =
			new ArrayList<>();

		Http.Options options = new Http.Options();

		options.addHeader(
			HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_JSON);
		options.addHeader("Liferay-AI-Hub-Cell-On-Behalf-Of", _userToken);

		OAuth2Authorization oAuth2Authorization =
			OAuth2AuthorizationLocalServiceUtil.
				getOAuth2AuthorizationByAccessTokenContent(
					_accessToken.substring(7));

		OAuth2Application oAuth2Application =
			OAuth2ApplicationLocalServiceUtil.getOAuth2Application(
				oAuth2Authorization.getOAuth2ApplicationId());

		String location =
			oAuth2Application.getHomePageURL() + "/o/search/v1.0/search";

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

			String url = "";

			if (searchResult.getItemURL() != null) {
				url = searchResult.getItemURL();
			}

			webSearchOrganicResults.add(
				WebSearchOrganicResult.from(
					searchResult.getTitle(),
					URI.create(URLEncoder.encode(url, "UTF-8")), null,
					searchResult.getDescription(),
					Map.of("score", String.valueOf(score))));
		}

		return WebSearchResults.from(
			WebSearchInformationResult.from(jsonObject.getLong("totalCount")),
			webSearchOrganicResults);
	}

	private final String _accessToken;
	private final String _blueprintExternalReferenceCode;
	private final long _companyId;
	private final String _userToken;

}