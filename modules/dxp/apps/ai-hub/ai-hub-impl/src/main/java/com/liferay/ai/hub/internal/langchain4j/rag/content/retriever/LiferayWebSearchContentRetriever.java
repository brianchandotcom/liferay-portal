/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.langchain4j.rag.content.retriever;

import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.service.OAuth2ApplicationLocalServiceUtil;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.InetAddressUtil;
import com.liferay.portal.kernel.util.PortalRunMode;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.rest.dto.v1_0.SearchResult;

import dev.langchain4j.rag.content.Content;
import dev.langchain4j.rag.query.Query;
import dev.langchain4j.web.search.WebSearchOrganicResult;

import java.net.URI;
import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Feliphe Marinho
 */
public class LiferayWebSearchContentRetriever extends BaseContentRetriever {

	public LiferayWebSearchContentRetriever(
			String blueprintExternalReferenceCode, long oAuth2ApplicationId,
			long userId, String userToken, long workflowInstanceId)
		throws PortalException {

		super(userId, workflowInstanceId);

		_blueprintExternalReferenceCode = blueprintExternalReferenceCode;

		if (oAuth2ApplicationId == 0L) {
			throw new IllegalArgumentException(
				"OAuth 2 application ID is required");
		}

		OAuth2Application oAuth2Application =
			OAuth2ApplicationLocalServiceUtil.getOAuth2Application(
				oAuth2ApplicationId);

		_homePageURL = oAuth2Application.getHomePageURL();

		_userToken = userToken;
	}

	@Override
	protected String getSearchTarget() {
		return _homePageURL;
	}

	@Override
	protected List<Content> search(Query query) {
		try {
			return _search(query);
		}
		catch (Exception exception) {
			return ReflectionUtil.throwException(exception);
		}
	}

	private List<Content> _search(Query query) throws Exception {
		Http.Options options = new Http.Options();

		options.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + _userToken);
		options.addHeader(
			HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_JSON);

		URL urlObject = new URL(_homePageURL);

		if (!PortalRunMode.isTestMode() &&
			InetAddressUtil.isLocalInetAddress(
				InetAddressUtil.getInetAddressByName(urlObject.getHost()))) {

			throw new SecurityException(
				"Local links are not allowed: " + urlObject);
		}

		String location = _homePageURL + "/o/search/v1.0/search";

		if (!Validator.isBlank(_blueprintExternalReferenceCode)) {
			location = HttpComponentsUtil.addParameter(
				location, "blueprintExternalReferenceCode",
				_blueprintExternalReferenceCode);
		}

		location = HttpComponentsUtil.addParameter(location, "page", 1);
		location = HttpComponentsUtil.addParameter(location, "pageSize", 5);
		location = HttpComponentsUtil.addParameter(
			location, "search", query.text());

		options.setLocation(location);

		options.setMethod(Http.Method.GET);

		String responseBody = HttpUtil.URLtoString(options);

		Http.Response response = options.getResponse();

		if ((response.getResponseCode() < 200) ||
			(response.getResponseCode() >= 300)) {

			throw new PortalException(
				StringBundler.concat(
					"Search request to ", location,
					" failed with response code ", response.getResponseCode()));
		}

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(responseBody);

		List<Content> contents = new ArrayList<>();

		for (JSONObject itemJSONObject :
				(Iterable<JSONObject>)jsonObject.getJSONArray("items")) {

			SearchResult searchResult = SearchResult.toDTO(
				itemJSONObject.toString());

			if (searchResult.getScore() < 5) {
				continue;
			}

			String itemURL = _homePageURL;

			if (searchResult.getItemURL() != null) {
				itemURL = searchResult.getItemURL();
			}

			WebSearchOrganicResult webSearchOrganicResult =
				WebSearchOrganicResult.from(
					searchResult.getTitle(), URI.create(itemURL), null,
					searchResult.getDescription(),
					Map.of("score", String.valueOf(searchResult.getScore())));

			contents.add(Content.from(webSearchOrganicResult.toTextSegment()));
		}

		return contents;
	}

	private final String _blueprintExternalReferenceCode;
	private final String _homePageURL;
	private final String _userToken;

}