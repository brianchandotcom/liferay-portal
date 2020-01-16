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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.index.IndexNameBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.tuning.rankings.web.internal.constants.ResultRankingsPortletKeys;
import com.liferay.portal.search.tuning.rankings.web.internal.index.DuplicateQueryStringsDetector;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Kevin Tan
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ResultRankingsPortletKeys.RESULT_RANKINGS,
		"mvc.command.name=/results_ranking/validate"
	},
	service = MVCResourceCommand.class
)
public class ValidateRankingMVCResourceCommand implements MVCResourceCommand {

	@Override
	public boolean serveResource(
		ResourceRequest resourceRequest, ResourceResponse resourceResponse) {

		try {
			writeJSONPortletResponse(
				resourceRequest, resourceResponse,
				getJSONObject(resourceRequest));

			return false;
		}
		catch (RuntimeException runtimeException) {
			runtimeException.printStackTrace();

			throw runtimeException;
		}
	}

	protected JSONObject getJSONObject(ResourceRequest resourceRequest) {
		ValidateRankingMVCResourceRequest validateRankingMVCResourceRequest =
			new ValidateRankingMVCResourceRequest(resourceRequest);

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		List<String> duplicateQueryStrings = _getDuplicateAliases(
			resourceRequest, validateRankingMVCResourceRequest);

		if (ListUtil.isNotEmpty(duplicateQueryStrings)) {
			jsonArray.put(
				LanguageUtil.format(
					portal.getHttpServletRequest(resourceRequest),
					"aliases-must-be-unique-across-all-rankings.-the-" +
						"following-aliases-already-exist-x",
					StringUtil.merge(
						duplicateQueryStrings, StringPool.COMMA_AND_SPACE),
					false));
		}

		return JSONUtil.put("errors", jsonArray);
	}

	protected void writeJSONPortletResponse(
		ResourceRequest resourceRequest, ResourceResponse resourceResponse,
		JSONObject jsonObject) {

		if (jsonObject == null) {
			return;
		}

		try {
			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse, jsonObject);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	@Reference
	protected DuplicateQueryStringsDetector duplicateQueryStringsDetector;

	@Reference
	protected IndexNameBuilder indexNameBuilder;

	@Reference
	protected Portal portal;

	@Reference
	protected SearchRequestBuilderFactory searchRequestBuilderFactory;

	private List<String> _getAliases(
		ValidateRankingMVCResourceRequest validateRankingMVCResourceRequest) {

		List<String> strings = new ArrayList<>(
			validateRankingMVCResourceRequest.getAliases());

		strings.remove(_ACTIVATE_SPECIAL);
		strings.remove(_DEACTIVATE_SPECIAL);

		Stream<String> stream = strings.stream();

		Predicate<String> predicate = this::_isUpdateSpecial;

		return stream.filter(
			predicate.negate()
		).collect(
			Collectors.toList()
		);
	}

	private List<String> _getDuplicateAliases(
		ResourceRequest resourceRequest,
		ValidateRankingMVCResourceRequest validateRankingMVCResourceRequest) {

		String index = _getIndexName(
			resourceRequest, validateRankingMVCResourceRequest);

		List<String> aliases = _getAliases(validateRankingMVCResourceRequest);

		return duplicateQueryStringsDetector.detect(
			duplicateQueryStringsDetector.builder(
			).index(
				index
			).queryStrings(
				aliases
			).unlessRankingId(
				validateRankingMVCResourceRequest.getResultsRankingUid()
			).build());
	}

	private String _getIndexName(
		ResourceRequest resourceRequest,
		ValidateRankingMVCResourceRequest validateRankingMVCResourceRequest) {

		String index = validateRankingMVCResourceRequest.getIndexName();

		if (Validator.isBlank(index)) {
			index = indexNameBuilder.getIndexName(
				portal.getCompanyId(resourceRequest));
		}

		return index;
	}

	private boolean _isUpdateSpecial(String string) {
		return string.startsWith(_UPDATE_SPECIAL);
	}

	private static final String _ACTIVATE_SPECIAL = StringPool.PLUS;

	private static final String _DEACTIVATE_SPECIAL = StringPool.MINUS;

	private static final String _UPDATE_SPECIAL = StringPool.GREATER_THAN;

	private class ValidateRankingMVCResourceRequest {

		public static final String PARAM_ALIASES = "aliases";

		public static final String PARAM_KEYWORDS = "keywords";

		public ValidateRankingMVCResourceRequest(
			ResourceRequest resourceRequest) {

			_indexName = ParamUtil.getString(resourceRequest, "index-name");
			_resultsRankingUid = ParamUtil.getString(
				resourceRequest, "resultsRankingUid");

			_aliases = Arrays.asList(
				ParamUtil.getStringValues(resourceRequest, PARAM_ALIASES));
		}

		public List<String> getAliases() {
			return Collections.unmodifiableList(_aliases);
		}

		public String getIndexName() {
			return _indexName;
		}

		public String getResultsRankingUid() {
			return _resultsRankingUid;
		}

		private final List<String> _aliases;
		private final String _indexName;
		private final String _resultsRankingUid;

	}

}