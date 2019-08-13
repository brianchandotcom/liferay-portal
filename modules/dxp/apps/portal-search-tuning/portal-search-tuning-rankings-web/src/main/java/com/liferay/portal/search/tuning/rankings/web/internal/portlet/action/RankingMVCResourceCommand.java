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

package com.liferay.portal.search.tuning.rankings.web.internal.portlet.action;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.document.GetDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.GetDocumentResponse;
import com.liferay.portal.search.filter.ComplexQueryPartBuilderFactory;
import com.liferay.portal.search.query.IdsQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.search.tuning.rankings.web.internal.constants.ResultRankingsPortletKeys;
import com.liferay.portal.search.tuning.rankings.web.internal.index.Ranking;
import com.liferay.portal.search.tuning.rankings.web.internal.index.RankingIndexReader;
import com.liferay.portal.search.tuning.rankings.web.internal.searcher.RankingSearchRequestHelper;

import java.io.IOException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ResultRankingsPortletKeys.RESULT_RANKINGS,
		"mvc.command.name=/results_ranking/get_results"
	},
	service = MVCResourceCommand.class
)
public class RankingMVCResourceCommand implements MVCResourceCommand {

	@Override
	public boolean serveResource(
		ResourceRequest resourceRequest, ResourceResponse resourceResponse) {

		try {
			String cmd = ParamUtil.getString(resourceRequest, Constants.CMD);

			if (cmd.equals("getVisibleResults")) {
				_writeVisibleDocumentsJSON(resourceRequest, resourceResponse);
			}
			else if (cmd.equals("getHiddenResults")) {
				_writeHiddenDocumentsJSON(resourceRequest, resourceResponse);
			}

			return false;
		}
		catch (RuntimeException re) {
			re.printStackTrace();

			throw re;
		}
	}

	protected SearchRequest buildSearchRequest(
		ResourceRequest resourceRequest, Ranking ranking) {

		SearchRequestBuilder searchRequestBuilder = getSearchRequestBuilder(
			resourceRequest, ranking);

		rankingSearchRequestHelper.contribute(searchRequestBuilder, ranking);

		return searchRequestBuilder.build();
	}

	protected Document getDocument(String index, String id, String type) {
		GetDocumentRequest getDocumentRequest = new GetDocumentRequest(
			index, id);

		getDocumentRequest.setType(type);
		getDocumentRequest.setFetchSourceInclude("*");

		GetDocumentResponse getDocumentResponse = searchEngineAdapter.execute(
			getDocumentRequest);

		if (!getDocumentResponse.isExists()) {
			return null;
		}

		return getDocumentResponse.getDocument();
	}

	protected Query getIdsQuery(String id) {
		IdsQuery idsQuery = queries.ids();

		idsQuery.addIds(id);

		return idsQuery;
	}

	protected SearchRequestBuilder getSearchRequestBuilder(
		ResourceRequest resourceRequest, Ranking ranking) {

		String queryString = _getQueryString(ranking, resourceRequest);

		return searchRequestBuilderFactory.builder(
		).addComplexQueryPart(
			complexQueryPartBuilderFactory.builder(
			).query(
				getIdsQuery(queryString)
			).occur(
				"should"
			).build()
		).from(
			ParamUtil.getInteger(resourceRequest, "from")
		).queryString(
			queryString
		).size(
			ParamUtil.getInteger(resourceRequest, "size", 10)
		).withSearchContext(
			searchContext -> searchContext.setCompanyId(
				ParamUtil.getLong(resourceRequest, "companyId"))
		);
	}

	protected String getTitle(Document document) {
		String title = document.getString(Field.TITLE + "_en_US");

		if (!Validator.isBlank(title)) {
			return title;
		}

		return document.getString(Field.TITLE);
	}

	protected void populateHiddenDocuments(
		JSONArray jsonArray, Ranking ranking) {

		List<String> ids = ranking.getBlockIds();

		Stream<String> stream = ids.stream();

		stream.map(
			id -> getDocument(
				ranking.getIndex(), id,
				LiferayTypeMappingsConstants.LIFERAY_DOCUMENT_TYPE)
		).filter(
			document -> document != null
		).map(
			document -> _withHiddenIcon(_translate(document))
		).forEach(
			jsonArray::put
		);
	}

	protected void populateVisibleDocuments(
		ResourceRequest resourceRequest, JSONArray jsonArray, Ranking ranking) {

		SearchRequest searchRequest = buildSearchRequest(
			resourceRequest, ranking);

		SearchResponse searchResponse = searcher.search(searchRequest);

		Stream<Document> stream = searchResponse.getDocumentsStream();

		stream.map(
			document -> _withPinnedIcon(
				ranking.isPinned(document.getString(Field.UID)),
				_translate(document))
		).forEach(
			jsonArray::put
		);
	}

	@Reference
	protected ComplexQueryPartBuilderFactory complexQueryPartBuilderFactory;

	@Reference
	protected Queries queries;

	@Reference
	protected RankingIndexReader rankingIndexReader;

	@Reference
	protected RankingSearchRequestHelper rankingSearchRequestHelper;

	@Reference
	protected SearchEngineAdapter searchEngineAdapter;

	@Reference
	protected Searcher searcher;

	@Reference
	protected SearchRequestBuilderFactory searchRequestBuilderFactory;

	private static boolean _isDocumentUid(String queryString) {
		return queryString.startsWith("com.liferay");
	}

	private String _getParamKeywords(ResourceRequest resourceRequest) {
		return ParamUtil.getString(resourceRequest, "keywords");
	}

	private String _getQueryString(Ranking ranking) {
		List<String> queryStrings = ranking.getQueryStrings();

		Stream<String> stream = queryStrings.stream();

		return stream.findFirst(
		).orElse(
			ranking.getName()
		);
	}

	private String _getQueryString(
		Ranking ranking, ResourceRequest resourceRequest) {

		String queryStringOfUrl = _getParamKeywords(resourceRequest);

		if (_isDocumentUid(queryStringOfUrl)) {
			return queryStringOfUrl;
		}

		return _getQueryString(ranking);
	}

	private String _getRankingId(ResourceRequest resourceRequest) {
		return ParamUtil.getString(
			resourceRequest, RankingMVCResourceCommand._PARAM_RANKING_ID);
	}

	private JSONObject _translate(Document document) {
		return JSONUtil.put(
			"author", document.getString(Field.USER_NAME)
		).put(
			"clicks", document.getString("clicks")
		).put(
			"description", document.getString(Field.DESCRIPTION)
		).put(
			"id", document.getString(Field.UID)
		).put(
			"title", getTitle(document)
		).put(
			"type", document.getString(Field.ENTRY_CLASS_NAME)
		);
	}

	private JSONObject _withHiddenIcon(JSONObject jsonObject) {
		return jsonObject.put("hidden", true);
	}

	private JSONObject _withPinnedIcon(boolean pinned, JSONObject jsonObject) {
		if (pinned) {
			jsonObject.put("pinned", true);
		}

		return jsonObject;
	}

	private void _writeHiddenDocumentsJSON(
		ResourceRequest resourceRequest, ResourceResponse resourceResponse) {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		String uid = _getRankingId(resourceRequest);

		Optional<Ranking> optional = rankingIndexReader.fetchOptional(uid);

		optional.ifPresent(
			ranking -> populateHiddenDocuments(jsonArray, optional.get()));

		JSONObject jsonObject = JSONUtil.put(
			"documents", jsonArray
		).put(
			"total", jsonArray.length()
		);

		_writeJSON(resourceRequest, resourceResponse, jsonObject);
	}

	private void _writeJSON(
		ResourceRequest resourceRequest, ResourceResponse resourceResponse,
		JSONObject jsonObject) {

		try {
			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse, jsonObject);
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	private void _writeVisibleDocumentsJSON(
		ResourceRequest resourceRequest, ResourceResponse resourceResponse) {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		Optional<Ranking> optional = rankingIndexReader.fetchOptional(
			_getRankingId(resourceRequest));

		optional.ifPresent(
			ranking -> populateVisibleDocuments(
				resourceRequest, jsonArray, ranking));

		JSONObject jsonObject = JSONUtil.put(
			"documents", jsonArray
		).put(
			"total", jsonArray.length()
		);

		_writeJSON(resourceRequest, resourceResponse, jsonObject);
	}

	private static final String _PARAM_RANKING_ID = "resultsRankingUid";

}