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
	immediate = true,
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

		Map<String, Document> documents = new LinkedHashMap<>(
			_DOCUMENTS_SIZE, 1.0F);

		while (documents.size() < _DOCUMENTS_SIZE) {
			searchSearchRequest.setStart(start);

			SearchSearchResponse searchSearchResponse =
				searchEngineAdapter.execute(searchSearchRequest);

			Hits hits = searchSearchResponse.getHits();

			for (Document document : hits.getDocs()) {
				String recommendedEntryClassPK = document.get(
					CommerceMLRecommendationField.RECOMMENDED_ENTRY_CLASS_PK);

				if (documents.get(recommendedEntryClassPK) != null) {
					continue;
				}

				documents.put(recommendedEntryClassPK, document);

				if (documents.size() == _DOCUMENTS_SIZE) {
					break;
				}
			}

			start += _SEARCH_SEARCH_REQUEST_SIZE;

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

		return toList(new ArrayList<>(documents.values()));
	}

	@Override
	protected Document toDocument(
		FrequentPatternCommerceMLRecommendation model) {

		Document document = getDocument(model);

		document.addKeyword(
			Field.UID,
			String.valueOf(
				getHash(
					model.getAntecedentIds(),
					model.getRecommendedEntryClassPK())));
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
				getCommerceMLRecommendation(
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
		ScriptBuilder scriptBuilder = _scripts.builder();

		return scriptBuilder.idOrCode(
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

		FunctionScoreQuery functionScoreQuery = _queries.functionScore(
			_getConstantScoreQuery(cpDefinitionIds));

		functionScoreQuery.addFilterQueryScoreFunctionHolder(
			_getExcludeRecommendations(cpDefinitionIds),
			_scoreFunctions.script(_getScript(cpDefinitionIds)));
		functionScoreQuery.setCombineFunction(CombineFunction.REPLACE);
		functionScoreQuery.setScoreMode(FunctionScoreQuery.ScoreMode.SUM);
		functionScoreQuery.setMinScore(1.1F);

		return new SearchSearchRequest() {
			{
				setIndexNames(_commerceMLIndexer.getIndexName(companyId));
				setQuery(functionScoreQuery);
				setSize(_SEARCH_SEARCH_REQUEST_SIZE);
			}
		};
	}

	private static final int _DOCUMENTS_SIZE = 10;

	private static final int _SEARCH_SEARCH_REQUEST_SIZE = 300;

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