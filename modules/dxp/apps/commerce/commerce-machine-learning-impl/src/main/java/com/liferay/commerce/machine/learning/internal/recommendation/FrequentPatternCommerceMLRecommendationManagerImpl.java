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

package com.liferay.commerce.machine.learning.internal.recommendation;

import com.liferay.commerce.machine.learning.internal.recommendation.constants.CommerceMLRecommendationField;
import com.liferay.commerce.machine.learning.internal.search.api.CommerceMLIndexer;
import com.liferay.commerce.machine.learning.recommendation.FrequentPatternCommerceMLRecommendation;
import com.liferay.commerce.machine.learning.recommendation.FrequentPatternCommerceMLRecommendationManager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.FunctionScoreQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.query.TermQuery;
import com.liferay.portal.search.query.function.CombineFunction;
import com.liferay.portal.search.query.function.score.ScoreFunctions;
import com.liferay.portal.search.query.function.score.ScriptScoreFunction;
import com.liferay.portal.search.script.Script;
import com.liferay.portal.search.script.ScriptBuilder;
import com.liferay.portal.search.script.ScriptType;
import com.liferay.portal.search.script.Scripts;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(
	enabled = false, immediate = true,
	service = FrequentPatternCommerceMLRecommendationManager.class
)
public class FrequentPatternCommerceMLRecommendationManagerImpl
	extends BaseCommerceMLRecommendationServiceImpl
		<FrequentPatternCommerceMLRecommendation>
	implements FrequentPatternCommerceMLRecommendationManager {

	@Override
	public FrequentPatternCommerceMLRecommendation
			addFrequentPatternCommerceMLRecommendation(
				FrequentPatternCommerceMLRecommendation
					frequentPatternCommerceMLRecommendation)
		throws PortalException {

		return addCommerceMLRecommendation(
			frequentPatternCommerceMLRecommendation,
			_commerceMLIndexer.getIndexName(
				frequentPatternCommerceMLRecommendation.getCompanyId()),
			_commerceMLIndexer.getDocumentType());
	}

	@Override
	public FrequentPatternCommerceMLRecommendation create() {
		return new FrequentPatternCommerceMLRecommendationImpl();
	}

	@Override
	public List<FrequentPatternCommerceMLRecommendation>
			getFrequentPatternCommerceMLRecommendations(
				long companyId, long[] cpDefinitionIds)
		throws PortalException {

		long startTimeMillis = System.currentTimeMillis();

		SearchSearchRequest searchSearchRequest = _getSearchSearchRequest(
			companyId, cpDefinitionIds);

		int start = 0;

		Map<String, Document> documentMap = new LinkedHashMap<>(
			DEFAULT_RESULT_SIZE, 1.0F);

		while (documentMap.size() < DEFAULT_RESULT_SIZE) {
			searchSearchRequest.setStart(start);

			SearchSearchResponse searchSearchResponse =
				searchEngineAdapter.execute(searchSearchRequest);

			Hits hits = searchSearchResponse.getHits();

			for (Document doc : hits.getDocs()) {
				String recommendedEntryClassPK = doc.get(
					CommerceMLRecommendationField.RECOMMENDED_ENTRY_CLASS_PK);

				if (documentMap.get(recommendedEntryClassPK) != null) {
					continue;
				}

				documentMap.put(recommendedEntryClassPK, doc);

				if (documentMap.size() == DEFAULT_RESULT_SIZE) {
					break;
				}
			}

			start += DEFAULT_FETCH_SIZE;

			if (start > searchSearchResponse.getCount()) {
				break;
			}
		}

		if (_log.isTraceEnabled()) {
			_log.trace(
				String.format(
					"Query execution time: %s",
					System.currentTimeMillis() - startTimeMillis));
		}

		return toModelList(new ArrayList<>(documentMap.values()));
	}

	@Override
	protected Document toDocument(
		FrequentPatternCommerceMLRecommendation model) {

		Document document = getBaseDocument(model);

		long hash = getHash(
			model.getAntecedentIds(), model.getRecommendedEntryClassPK());

		document.addKeyword(Field.UID, String.valueOf(hash));

		document.addKeyword(
			CommerceMLRecommendationField.ANTECEDENT_IDS,
			model.getAntecedentIds());

		document.addNumber(
			CommerceMLRecommendationField.ANTECEDENT_IDS_LENGTH,
			model.getAntecedentIdsLength());

		return document;
	}

	@Override
	protected FrequentPatternCommerceMLRecommendation toModel(
		Document document) {

		FrequentPatternCommerceMLRecommendation
			frequentPatternCommerceMLRecommendation =
				getBaseCommerceMLRecommendationModel(
					new FrequentPatternCommerceMLRecommendationImpl(),
					document);

		frequentPatternCommerceMLRecommendation.setAntecedentIds(
			GetterUtil.getLongValues(
				document.getValues(
					CommerceMLRecommendationField.ANTECEDENT_IDS)));

		frequentPatternCommerceMLRecommendation.setAntecedentIdsLength(
			GetterUtil.getLong(
				document.get(
					CommerceMLRecommendationField.ANTECEDENT_IDS_LENGTH)));

		return frequentPatternCommerceMLRecommendation;
	}

	protected static final int DEFAULT_FETCH_SIZE = 300;

	protected static final int DEFAULT_RESULT_SIZE = 10;

	private BooleanQuery _getConstantScoreQuery(long[] cpInstanceIds) {
		BooleanQuery booleanQuery = _queries.booleanQuery();

		for (long cpInstanceId : cpInstanceIds) {
			TermQuery termQuery = _queries.term(
				CommerceMLRecommendationField.ANTECEDENT_IDS, cpInstanceId);

			booleanQuery.addShouldQueryClauses(
				_queries.constantScore(termQuery));
		}

		return booleanQuery;
	}

	private BooleanQuery _getExcludeRecommendations(long[] cpInstanceIds) {
		BooleanQuery booleanQuery = _queries.booleanQuery();

		for (long cpInstanceId : cpInstanceIds) {
			booleanQuery.addMustNotQueryClauses(
				_queries.term(
					CommerceMLRecommendationField.RECOMMENDED_ENTRY_CLASS_PK,
					cpInstanceId));
		}

		return booleanQuery;
	}

	private Script _getScript(long[] cpInstanceIds) {
		ScriptBuilder builder = _scripts.builder();

		return builder.idOrCode(
			StringUtil.read(
				getClass(),
				"/com/liferay/commerce/machine/learning/internal/dependencies" +
					"/frequent-pattern-commerce-ml-recommendation-script." +
						"painless")
		).language(
			"painless"
		).putParameter(
			"cpInstanceIds", cpInstanceIds
		).scriptType(
			ScriptType.INLINE
		).build();
	}

	private SearchSearchRequest _getSearchSearchRequest(
		long companyId, long[] cpDefinitionIds) {

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setIndexNames(
			_commerceMLIndexer.getIndexName(companyId));

		searchSearchRequest.setSize(DEFAULT_FETCH_SIZE);

		BooleanQuery excludeRecommendationsBooleanQuery =
			_getExcludeRecommendations(cpDefinitionIds);

		BooleanQuery booleanQuery = _getConstantScoreQuery(cpDefinitionIds);

		ScriptScoreFunction scriptScoreFunction = _scoreFunctions.script(
			_getScript(cpDefinitionIds));

		FunctionScoreQuery functionScoreQuery = _queries.functionScore(
			booleanQuery);

		functionScoreQuery.addFilterQueryScoreFunctionHolder(
			excludeRecommendationsBooleanQuery, scriptScoreFunction);

		functionScoreQuery.setCombineFunction(CombineFunction.REPLACE);
		functionScoreQuery.setScoreMode(FunctionScoreQuery.ScoreMode.SUM);
		functionScoreQuery.setMinScore(1.1F);

		searchSearchRequest.setQuery(functionScoreQuery);

		return searchSearchRequest;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FrequentPatternCommerceMLRecommendationManagerImpl.class);

	@Reference(
		target = "(component.name=com.liferay.commerce.machine.learning.internal.recommendation.search.index.FrequentPatternCommerceMLRecommendationIndexer)"
	)
	private CommerceMLIndexer _commerceMLIndexer;

	@Reference
	private Queries _queries;

	@Reference
	private ScoreFunctions _scoreFunctions;

	@Reference
	private Scripts _scripts;

}