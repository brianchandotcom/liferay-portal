/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mcp.server.rest.internal.search.index;

import com.ibm.icu.text.RuleBasedNumberFormat;

import com.liferay.mcp.server.rest.dto.v1_0.Prerequisite;
import com.liferay.mcp.server.rest.dto.v1_0.ToolSearchResult;
import com.liferay.mcp.server.rest.internal.search.index.constants.MCPToolConstants;
import com.liferay.mcp.server.rest.internal.search.index.constants.MCPToolFields;
import com.liferay.mcp.server.rest.internal.search.index.util.IntentUtil;
import com.liferay.mcp.server.rest.internal.search.index.util.WordUtil;
import com.liferay.mcp.server.rest.internal.util.OpenAPIUtil;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.aggregation.AggregationResult;
import com.liferay.portal.search.aggregation.Aggregations;
import com.liferay.portal.search.aggregation.bucket.Bucket;
import com.liferay.portal.search.aggregation.bucket.TermsAggregation;
import com.liferay.portal.search.aggregation.bucket.TermsAggregationResult;
import com.liferay.portal.search.aggregation.metrics.TopHitsAggregation;
import com.liferay.portal.search.aggregation.metrics.TopHitsAggregationResult;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.FunctionScoreQuery;
import com.liferay.portal.search.query.MatchPhraseQuery;
import com.liferay.portal.search.query.MatchQuery;
import com.liferay.portal.search.query.MultiMatchQuery;
import com.liferay.portal.search.query.QueriesUtil;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.query.TermQuery;
import com.liferay.portal.search.query.TermsQuery;
import com.liferay.portal.search.query.function.CombineFunction;
import com.liferay.portal.search.query.function.score.ScoreFunctions;
import com.liferay.portal.search.script.Scripts;
import com.liferay.portal.search.sort.ScoreSort;
import com.liferay.portal.search.sort.SortOrder;
import com.liferay.portal.search.sort.Sorts;

import java.text.ParsePosition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(service = MCPToolIndexReader.class)
public class MCPToolIndexReader {

	public List<ToolSearchResult> search(
		Set<String> allowedToolKeys, long companyId,
		boolean includePrerequisites, String search, int size) {

		SearchSearchResponse searchSearchResponse =
			_searchEngineAdapter.execute(
				_getSearchSearchRequest(
					allowedToolKeys, companyId, search, size));

		SearchHits searchHits = searchSearchResponse.getSearchHits();

		List<SearchHit> searchHitsList = searchHits.getSearchHits();

		return _toToolSearchResults(
			allowedToolKeys, companyId, includePrerequisites, searchHitsList);
	}

	private void _demote(
		Query filterQuery, FunctionScoreQuery functionScoreQuery,
		float weight) {

		functionScoreQuery.addFilterQueryScoreFunctionHolder(
			filterQuery,
			_scoreFunctions.script(
				Scripts.INSTANCE.inline("painless", String.valueOf(weight))));
	}

	private Map<String, Document> _getCollectionToolDocuments(
		Set<String> allowedToolKeys, Set<String> collectionSegments,
		long companyId) {

		if (collectionSegments.isEmpty()) {
			return Collections.emptyMap();
		}

		SearchSearchResponse searchSearchResponse =
			_searchEngineAdapter.execute(
				_getCollectionToolsSearchSearchRequest(
					allowedToolKeys, collectionSegments, companyId));

		Map<String, AggregationResult> aggregationResultsMap =
			searchSearchResponse.getAggregationResultsMap();

		return _toCollectionToolDocuments(
			(TermsAggregationResult)aggregationResultsMap.get(
				MCPToolFields.COLLECTION_SEGMENT));
	}

	private SearchSearchRequest _getCollectionToolsSearchSearchRequest(
		Set<String> allowedToolKeys, Set<String> collectionSegments,
		long companyId) {

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		TermsAggregation termsAggregation = _aggregations.terms(
			MCPToolFields.COLLECTION_SEGMENT, MCPToolFields.COLLECTION_SEGMENT);

		TopHitsAggregation topHitsAggregation = _aggregations.topHits(
			_TOP_HITS_AGGREGATION_NAME);

		topHitsAggregation.addSortFields(
			_sorts.field(MCPToolFields.DEPRECATED, SortOrder.ASC),
			_sorts.field(MCPToolFields.PATH_PARAMETER_COUNT, SortOrder.ASC),
			_sorts.field(MCPToolFields.PATH_SEGMENT_COUNT, SortOrder.ASC),
			_sorts.field(MCPToolFields.TOOL_SET_SIZE, SortOrder.DESC),
			_sorts.field(MCPToolFields.PATH_LENGTH, SortOrder.ASC),
			_sorts.field(MCPToolFields.UID, SortOrder.ASC));
		topHitsAggregation.setFetchSource(true);
		topHitsAggregation.setSize(1);

		termsAggregation.addChildAggregation(topHitsAggregation);

		termsAggregation.setSize(collectionSegments.size());

		TermsQuery termsQuery = QueriesUtil.terms(
			MCPToolFields.COLLECTION_SEGMENT);

		termsQuery.addValues(collectionSegments.toArray());

		searchSearchRequest.addAggregation(termsAggregation);

		searchSearchRequest.setIndexNames(
			_mcpToolIndexCreator.getIndexName(companyId));
		searchSearchRequest.setQuery(
			_getFilteredQuery(allowedToolKeys, termsQuery));
		searchSearchRequest.setSize(0);

		return searchSearchRequest;
	}

	private String _getDescription(Document document) {
		String description = document.getString(MCPToolFields.DESCRIPTION);

		String toolName = document.getString(MCPToolFields.TOOL_NAME);

		if (Validator.isBlank(description)) {
			description = WordUtil.toWords(toolName);
		}

		String hint = _operationHints.get(toolName);

		if (Objects.equals(
				document.getString(MCPToolFields.OPERATION_VARIANT),
				MCPToolConstants.OPERATION_VARIANT_BATCH)) {

			hint = StringBundler.concat(
				"Takes many entities in one call, so use it rather than ",
				"calling the single entity operation repeatedly. It answers ",
				"202 with an import task, not a result: nothing is written ",
				"yet and 202 is not success. Before reporting the work done, ",
				"read executeStatus and failedItems by invoking getImportTask ",
				"in headless-batch-engine-v1.0, passing the id this returned.");
		}

		if (hint == null) {
			return description;
		}

		return description + StringPool.SPACE + hint;
	}

	private String _getEntityLiteralReplacement(List<String> intents) {
		if (intents.contains(IntentUtil.INTENT_CREATE)) {
			return _OBJECT_DEFINITION_PHRASE;
		}

		if (intents.contains(IntentUtil.INTENT_LIST) ||
			intents.contains(IntentUtil.INTENT_READ)) {

			return _OBJECT_ENTRY_PHRASE;
		}

		return _OBJECT_DEFINITION_PHRASE + StringPool.SPACE +
			_OBJECT_ENTRY_PHRASE;
	}

	private String _getEntityName(boolean association, String[] words) {
		if (association) {
			return null;
		}

		String entityName = null;

		List<String> entityWords = new ArrayList<>();

		for (String word : words) {
			if (_boundaryWords.contains(word)) {
				break;
			}

			if (word.equals("of") && !entityWords.isEmpty()) {
				entityName = StringUtil.merge(entityWords, StringPool.SPACE);

				entityWords.clear();
			}
			else if (!_ignoredWords.contains(word) &&
					 ListUtil.isEmpty(IntentUtil.getVerbIntents(word))) {

				entityWords.add(word);
			}
		}

		if (!entityWords.isEmpty()) {
			entityName = StringUtil.merge(entityWords, StringPool.SPACE);
		}

		return entityName;
	}

	private Query _getFilteredQuery(Set<String> allowedToolKeys, Query query) {
		if (allowedToolKeys == null) {
			return query;
		}

		BooleanQuery booleanQuery = QueriesUtil.booleanQuery();

		TermsQuery termsQuery = QueriesUtil.terms(MCPToolFields.UID);

		termsQuery.addValues(allowedToolKeys.toArray());

		booleanQuery.addFilterQueryClauses(termsQuery);

		booleanQuery.addMustQueryClauses(query);

		return booleanQuery;
	}

	private MultiMatchQuery _getMultiMatchQuery(String search) {
		MultiMatchQuery multiMatchQuery = QueriesUtil.multiMatch(
			search,
			LinkedHashMapBuilder.put(
				MCPToolFields.DESCRIPTION, _BOOST_FIELD_DESCRIPTION
			).put(
				MCPToolFields.ENTITY_NAME + ".split",
				_BOOST_FIELD_ENTITY_NAME_SPLIT
			).put(
				MCPToolFields.EXPANSION, _BOOST_FIELD_EXPANSION
			).put(
				MCPToolFields.PARAMETERS, _BOOST_FIELD_PARAMETERS
			).put(
				MCPToolFields.PATH, _BOOST_FIELD_PATH
			).put(
				MCPToolFields.SCHEMA_PROPERTIES, _BOOST_FIELD_SCHEMA_PROPERTIES
			).put(
				MCPToolFields.TOOL_NAME, _BOOST_FIELD_TOOL_NAME
			).put(
				MCPToolFields.TOOL_NAME + ".split", _BOOST_FIELD_TOOL_NAME_SPLIT
			).build());

		multiMatchQuery.setType(MultiMatchQuery.Type.CROSS_FIELDS);

		return multiMatchQuery;
	}

	private Query _getOperationVariantQuery(String... operationVariants) {
		TermsQuery termsQuery = QueriesUtil.terms(
			MCPToolFields.OPERATION_VARIANT);

		termsQuery.addValues((Object[])operationVariants);

		return termsQuery;
	}

	private Query _getOtherIntentsQuery(List<String> intents) {
		TermsQuery termsQuery = QueriesUtil.terms(MCPToolFields.INTENT);

		termsQuery.addValues(
			IntentUtil.getOtherIntents(
				intents
			).toArray());

		return termsQuery;
	}

	private Map<String, String> _getParameterCollectionSegments(
		Document document) {

		Map<String, String> parameterCollectionSegments = new LinkedHashMap<>();

		String[] segments = StringUtil.split(
			document.getString(MCPToolFields.PATH), CharPool.SLASH);

		for (int i = 1; i < (segments.length - 1); i++) {
			String parameter = OpenAPIUtil.getPathParameter(segments[i]);

			if (parameter == null) {
				continue;
			}

			parameterCollectionSegments.put(parameter, segments[i - 1]);
		}

		for (String requiredReference :
				document.getStrings(MCPToolFields.REQUIRED_REFERENCES)) {

			String[] parts = StringUtil.split(
				requiredReference, CharPool.POUND);

			if (parts.length != 2) {
				continue;
			}

			parameterCollectionSegments.put(parts[0], parts[1]);
		}

		return parameterCollectionSegments;
	}

	private String _getParameterNote(String parameter) {
		String note = _parameterHints.get(parameter);

		if (note != null) {
			return note;
		}

		if (StringUtil.endsWith(parameter, "ExternalReferenceCode")) {
			return "A generated code rather than a name. Read it from the " +
				"externalReferenceCode field of the listing named here.";
		}

		return null;
	}

	private List<Prerequisite> _getPrerequisites(
		Map<String, Document> collectionToolDocuments,
		Map<String, String> parameterCollectionSegments) {

		List<Prerequisite> prerequisites = new ArrayList<>();

		for (Map.Entry<String, String> entry :
				parameterCollectionSegments.entrySet()) {

			String parameter = entry.getKey();

			Prerequisite prerequisite = new Prerequisite();

			prerequisite.setNote(() -> _getParameterNote(parameter));
			prerequisite.setParameter(() -> parameter);

			Document document = collectionToolDocuments.get(entry.getValue());

			if (document != null) {
				prerequisite.setToolName(
					() -> document.getString(MCPToolFields.TOOL_NAME));
				prerequisite.setToolSetName(
					() -> document.getString(MCPToolFields.TOOL_SET_NAME));
			}

			prerequisites.add(prerequisite);
		}

		return prerequisites;
	}

	private int _getQuantity(String word) {
		ParsePosition parsePosition = new ParsePosition(0);

		Number number;

		synchronized (_ruleBasedNumberFormat) {
			number = _ruleBasedNumberFormat.parse(word, parsePosition);
		}

		if (parsePosition.getIndex() < word.length()) {
			return 0;
		}

		return number.intValue();
	}

	private Query _getQuery(String search) {
		String[] words = WordUtil.toSearchWords(search);

		boolean association = _hasAssociationWords(words);
		boolean batch = _hasBatchWords(words);

		List<String> intents = IntentUtil.getSearchIntents(words);

		FunctionScoreQuery functionScoreQuery = QueriesUtil.functionScore(
			_getRelevanceQuery(
				batch, _getEntityName(association, words),
				_replaceEntityLiteral(intents, search)));

		functionScoreQuery.setCombineFunction(CombineFunction.MULTIPLY);
		functionScoreQuery.setScoreMode(FunctionScoreQuery.ScoreMode.MULTIPLY);

		_demote(
			_getOperationVariantQuery(_OPERATION_VARIANTS_RARELY_WANTED),
			functionScoreQuery, _WEIGHT_RARELY_WANTED);
		_demote(
			_getOperationVariantQuery(_OPERATION_VARIANTS_SECONDARY_ACTION),
			functionScoreQuery, _WEIGHT_SECONDARY_ACTION);
		_demote(
			QueriesUtil.term(MCPToolFields.DEPRECATED, true),
			functionScoreQuery, _WEIGHT_DEPRECATED);

		if (!batch) {
			_demote(
				_getOperationVariantQuery(
					MCPToolConstants.OPERATION_VARIANT_BATCH),
				functionScoreQuery, _WEIGHT_BATCH);
		}

		if (association) {
			_demote(
				_getOperationVariantQuery(
					MCPToolConstants.OPERATION_VARIANT_TRAVERSAL),
				functionScoreQuery, _WEIGHT_TRAVERSAL);
		}

		if (!intents.isEmpty()) {
			_demote(
				_getOtherIntentsQuery(intents), functionScoreQuery,
				_WEIGHT_OTHER_INTENTS);
		}

		return functionScoreQuery;
	}

	private Query _getRelevanceQuery(
		boolean batch, String entityName, String search) {

		BooleanQuery booleanQuery = QueriesUtil.booleanQuery();

		booleanQuery.addShouldQueryClauses(
			_getMultiMatchQuery(search),
			new MatchPhraseQuery(MCPToolFields.EXPANSION + ".phrase", search));

		if (batch) {
			TermQuery termQuery = QueriesUtil.term(
				MCPToolFields.OPERATION_VARIANT,
				MCPToolConstants.OPERATION_VARIANT_BATCH);

			termQuery.setBoost(_BOOST_BATCH);

			booleanQuery.addShouldQueryClauses(termQuery);
		}

		if (entityName == null) {
			return booleanQuery;
		}

		MatchQuery matchQuery = QueriesUtil.match(
			MCPToolFields.ENTITY_NAME + ".split", entityName);

		matchQuery.setBoost(_BOOST_ENTITY_NAME_MATCH);

		return booleanQuery.addShouldQueryClauses(matchQuery);
	}

	private SearchSearchRequest _getSearchSearchRequest(
		Set<String> allowedToolKeys, long companyId, String search, int size) {

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setFetchSource(true);
		searchSearchRequest.setIndexNames(
			_mcpToolIndexCreator.getIndexName(companyId));
		searchSearchRequest.setQuery(
			_getFilteredQuery(allowedToolKeys, _getQuery(search)));
		searchSearchRequest.setSize(size);

		_setSorts(searchSearchRequest);

		return searchSearchRequest;
	}

	private boolean _hasAssociationWords(String[] words) {
		for (String word : words) {
			if (_associationWords.contains(word)) {
				return true;
			}

			String preposition = _associationVerbPrepositions.get(word);

			if ((preposition != null) &&
				ArrayUtil.contains(words, preposition)) {

				return true;
			}
		}

		return false;
	}

	private boolean _hasBatchWords(String[] words) {
		for (int i = 0; i < words.length; i++) {
			String word = words[i];

			if (_batchWords.contains(word) ||
				(_isQuantityExpression(i, words) &&
				 (_getQuantity(word) >= _BATCH_MIN_QUANTITY))) {

				return true;
			}
		}

		return false;
	}

	private boolean _isQuantityExpression(int index, String[] words) {
		if ((index == (words.length - 1)) ||
			!WordUtil.isPlural(words[index + 1])) {

			return false;
		}

		if (index == 0) {
			return true;
		}

		List<String> intents = IntentUtil.getVerbIntents(words[index - 1]);

		return !intents.isEmpty();
	}

	private String _replaceEntityLiteral(List<String> intents, String search) {
		Matcher matcher = _entityLiteralPattern.matcher(search);

		return matcher.replaceAll(_getEntityLiteralReplacement(intents));
	}

	private void _setSorts(SearchSearchRequest searchSearchRequest) {
		ScoreSort scoreSort = _sorts.score();

		scoreSort.setSortOrder(SortOrder.DESC);

		searchSearchRequest.addSorts(
			scoreSort,
			_sorts.field(MCPToolFields.PATH_SEGMENT_COUNT, SortOrder.ASC),
			_sorts.field(MCPToolFields.UID, SortOrder.ASC));
	}

	private Map<String, Document> _toCollectionToolDocuments(
		TermsAggregationResult termsAggregationResult) {

		Map<String, Document> collectionToolDocuments = new HashMap<>();

		for (Bucket bucket : termsAggregationResult.getBuckets()) {
			TopHitsAggregationResult topHitsAggregationResult =
				(TopHitsAggregationResult)bucket.getChildAggregationResult(
					_TOP_HITS_AGGREGATION_NAME);

			SearchHits searchHits = topHitsAggregationResult.getSearchHits();

			List<SearchHit> searchHitsList = searchHits.getSearchHits();

			if (searchHitsList.isEmpty()) {
				continue;
			}

			SearchHit searchHit = searchHitsList.get(0);

			collectionToolDocuments.put(
				bucket.getKey(), searchHit.getDocument());
		}

		return collectionToolDocuments;
	}

	private ToolSearchResult _toToolSearchResult(Document document) {
		return new ToolSearchResult() {
			{
				setDescription(() -> _getDescription(document));
				setToolName(() -> document.getString(MCPToolFields.TOOL_NAME));
				setToolSetName(
					() -> document.getString(MCPToolFields.TOOL_SET_NAME));
			}
		};
	}

	private List<ToolSearchResult> _toToolSearchResults(
		Set<String> allowedToolKeys, long companyId,
		boolean includePrerequisites, List<SearchHit> searchHitsList) {

		List<ToolSearchResult> toolSearchResults = TransformUtil.transform(
			searchHitsList,
			searchHit -> _toToolSearchResult(searchHit.getDocument()));

		if (!includePrerequisites || searchHitsList.isEmpty()) {
			return toolSearchResults;
		}

		SearchHit searchHit = searchHitsList.get(0);

		Document document = searchHit.getDocument();

		ToolSearchResult toolSearchResult = toolSearchResults.get(0);

		toolSearchResult.setPrerequisites(
			() -> {
				Map<String, String> parameterCollectionSegments =
					_getParameterCollectionSegments(document);

				List<Prerequisite> prerequisites = _getPrerequisites(
					_getCollectionToolDocuments(
						allowedToolKeys,
						new HashSet<>(parameterCollectionSegments.values()),
						companyId),
					parameterCollectionSegments);

				if (prerequisites.isEmpty()) {
					return null;
				}

				return prerequisites.toArray(new Prerequisite[0]);
			});

		return toolSearchResults;
	}

	private static final int _BATCH_MIN_QUANTITY = 10;

	private static final float _BOOST_BATCH = 3.0F;

	private static final float _BOOST_ENTITY_NAME_MATCH = 2.0F;

	private static final float _BOOST_FIELD_DESCRIPTION = 1.0F;

	private static final float _BOOST_FIELD_ENTITY_NAME_SPLIT = 4.0F;

	private static final float _BOOST_FIELD_EXPANSION = 1.5F;

	private static final float _BOOST_FIELD_PARAMETERS = 0.25F;

	private static final float _BOOST_FIELD_PATH = 2.0F;

	private static final float _BOOST_FIELD_SCHEMA_PROPERTIES = 0.75F;

	private static final float _BOOST_FIELD_TOOL_NAME = 5.0F;

	private static final float _BOOST_FIELD_TOOL_NAME_SPLIT = 2.0F;

	private static final String _OBJECT_DEFINITION_PHRASE = "object definition";

	private static final String _OBJECT_ENTRY_PHRASE = "object entry";

	private static final String[] _OPERATION_VARIANTS_RARELY_WANTED = {
		MCPToolConstants.OPERATION_VARIANT_HISTORY,
		MCPToolConstants.OPERATION_VARIANT_KEYED,
		MCPToolConstants.OPERATION_VARIANT_NESTED,
		MCPToolConstants.OPERATION_VARIANT_OPENAPI,
		MCPToolConstants.OPERATION_VARIANT_PERMISSIONS,
		MCPToolConstants.OPERATION_VARIANT_PREVIEW,
		MCPToolConstants.OPERATION_VARIANT_RATING,
		MCPToolConstants.OPERATION_VARIANT_SUBSCRIPTION
	};

	private static final String[] _OPERATION_VARIANTS_SECONDARY_ACTION = {
		MCPToolConstants.OPERATION_VARIANT_APPROVED,
		MCPToolConstants.OPERATION_VARIANT_COPY,
		MCPToolConstants.OPERATION_VARIANT_EXPIRE,
		MCPToolConstants.OPERATION_VARIANT_MOVE,
		MCPToolConstants.OPERATION_VARIANT_RESTORE,
		MCPToolConstants.OPERATION_VARIANT_TRANSLATION,
		MCPToolConstants.OPERATION_VARIANT_VALIDATE
	};

	private static final String _TOP_HITS_AGGREGATION_NAME = "topHits";

	private static final float _WEIGHT_BATCH = 0.3F;

	private static final float _WEIGHT_DEPRECATED = 0.8F;

	private static final float _WEIGHT_OTHER_INTENTS = 0.5F;

	private static final float _WEIGHT_RARELY_WANTED = 0.3F;

	private static final float _WEIGHT_SECONDARY_ACTION = 0.8F;

	private static final float _WEIGHT_TRAVERSAL = 0.7F;

	private static final Map<String, String> _associationVerbPrepositions =
		HashMapBuilder.put(
			"add", "to"
		).put(
			"added", "to"
		).put(
			"adds", "to"
		).put(
			"put", "to"
		).put(
			"remove", "from"
		).put(
			"removed", "from"
		).put(
			"removes", "from"
		).build();
	private static final Set<String> _associationWords = SetUtil.fromArray(
		"assign", "assigned", "assigning", "associate", "associated",
		"association", "attach", "attached", "detach", "disassociate", "link",
		"linked", "relate", "related", "unassign", "unlink");
	private static final Set<String> _batchWords = SetUtil.fromArray(
		"batch", "batches", "bulk", "many");
	private static final Set<String> _boundaryWords = SetUtil.fromArray(
		"and", "as", "at", "belonging", "by", "for", "from", "in", "inside",
		"into", "on", "onto", "that", "to", "under", "using", "via", "whose",
		"with", "within");
	private static final Pattern _entityLiteralPattern = Pattern.compile(
		"\\b(entities|entity)\\b", Pattern.CASE_INSENSITIVE);
	private static final Set<String> _ignoredWords = SetUtil.fromArray(
		"a", "an", "data", "detail", "details", "entities", "entity", "entries",
		"entry", "info", "information", "item", "items", "object", "objects",
		"of", "record", "records", "row", "rows", "the", "value", "values");
	private static final Map<String, String> _operationHints =
		HashMapBuilder.put(
			"postObjectDefinition",
			StringBundler.concat(
				"Once published, this object's entries get their own tools, ",
				"named after the object rather than after Liferay: to add ",
				"one, search for the object's own name, as in \"create a pet ",
				"store\", not \"create an object entry\".")
		).put(
			"postObjectDefinitionPublish",
			StringBundler.concat(
				"Publishing registers a tool set for this object's entries, ",
				"named after the object. To add one, search for the object's ",
				"own name, as in \"create a pet store\", not \"create an ",
				"object entry\".")
		).build();
	private static final Map<String, String> _parameterHints =
		HashMapBuilder.put(
			"assetLibraryId",
			StringBundler.concat(
				"The asset library's key, its numeric id or its external ",
				"reference code. The key is case sensitive.")
		).put(
			"siteId",
			StringBundler.concat(
				"The site's key, its numeric id or its external reference ",
				"code. The key is usually the site's name and is case ",
				"sensitive, so try it before listing sites.")
		).build();
	private static final RuleBasedNumberFormat _ruleBasedNumberFormat =
		new RuleBasedNumberFormat(
			LocaleUtil.ENGLISH, RuleBasedNumberFormat.SPELLOUT);

	@Reference
	private Aggregations _aggregations;

	@Reference
	private MCPToolIndexCreator _mcpToolIndexCreator;

	@Reference
	private ScoreFunctions _scoreFunctions;

	@Reference
	private SearchEngineAdapter _searchEngineAdapter;

	@Reference
	private Sorts _sorts;

}