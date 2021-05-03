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

package com.liferay.portal.search.tuning.blueprints.engine.internal.suggester.translator;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.generic.MatchQuery;
import com.liferay.portal.kernel.search.suggest.PhraseSuggester;
import com.liferay.portal.kernel.search.suggest.Suggester;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.tuning.blueprints.constants.json.keys.suggester.PhraseSuggesterConfigurationKeys;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.ParameterData;
import com.liferay.portal.search.tuning.blueprints.engine.spi.suggester.SuggesterTranslator;
import com.liferay.portal.search.tuning.blueprints.message.Message;
import com.liferay.portal.search.tuning.blueprints.message.Messages;
import com.liferay.portal.search.tuning.blueprints.message.Severity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true, property = "type=phrase",
	service = SuggesterTranslator.class
)
public class PhraseSuggesterTranslator
	extends BaseSuggesterTranslator implements SuggesterTranslator {

	@Override
	public Optional<Suggester> translate(
		String suggesterName, JSONObject configurationJSONObject,
		ParameterData parameterData, Messages messages) {

		if (!validateSuggesterConfiguration(
				messages, configurationJSONObject, suggesterName)) {

			return Optional.empty();
		}

		String field = configurationJSONObject.getString(
			PhraseSuggesterConfigurationKeys.FIELD.getJsonKey());

		PhraseSuggester phraseSuggester = new PhraseSuggester(
			suggesterName, field,
			getValue(configurationJSONObject, parameterData));

		if (!configurationJSONObject.isNull(
				PhraseSuggesterConfigurationKeys.ANALYZER.getJsonKey())) {

			phraseSuggester.setAnalyzer(
				configurationJSONObject.getString(
					PhraseSuggesterConfigurationKeys.ANALYZER.getJsonKey()));
		}

		if (!configurationJSONObject.isNull(
				PhraseSuggesterConfigurationKeys.COLLATE.getJsonKey())) {

			_setCollate(phraseSuggester, configurationJSONObject, messages);
		}

		if (!configurationJSONObject.isNull(
				PhraseSuggesterConfigurationKeys.CONFIDENCE.getJsonKey())) {

			phraseSuggester.setConfidence(
				GetterUtil.getFloat(
					configurationJSONObject.get(
						PhraseSuggesterConfigurationKeys.CONFIDENCE.
							getJsonKey())));
		}

		if (!configurationJSONObject.isNull(
				PhraseSuggesterConfigurationKeys.DIRECT_GENERATOR.
					getJsonKey())) {

			_setCandidateGenerators(
				phraseSuggester, configurationJSONObject, messages);
		}

		if (!configurationJSONObject.isNull(
				PhraseSuggesterConfigurationKeys.FORCE_UNIGRAMS.getJsonKey())) {

			phraseSuggester.setForceUnigrams(
				configurationJSONObject.getBoolean(
					PhraseSuggesterConfigurationKeys.FORCE_UNIGRAMS.
						getJsonKey()));
		}

		if (!configurationJSONObject.isNull(
				PhraseSuggesterConfigurationKeys.GRAM_SIZE.getJsonKey())) {

			phraseSuggester.setGramSize(
				configurationJSONObject.getInt(
					PhraseSuggesterConfigurationKeys.GRAM_SIZE.getJsonKey()));
		}

		if (!configurationJSONObject.isNull(
				PhraseSuggesterConfigurationKeys.MAX_ERRORS.getJsonKey())) {

			phraseSuggester.setMaxErrors(
				GetterUtil.getFloat(
					configurationJSONObject.get(
						PhraseSuggesterConfigurationKeys.MAX_ERRORS.
							getJsonKey())));
		}

		if (!configurationJSONObject.isNull(
				PhraseSuggesterConfigurationKeys.REAL_WORLD_ERROR_LIKELIHOOD.
					getJsonKey())) {

			phraseSuggester.setRealWordErrorLikelihood(
				GetterUtil.getFloat(
					configurationJSONObject.get(
						PhraseSuggesterConfigurationKeys.
							REAL_WORLD_ERROR_LIKELIHOOD.getJsonKey())));
		}

		if (!configurationJSONObject.isNull(
				PhraseSuggesterConfigurationKeys.SEPARATOR.getJsonKey())) {

			phraseSuggester.setSeparator(
				configurationJSONObject.getString(
					PhraseSuggesterConfigurationKeys.SEPARATOR.getJsonKey()));
		}

		if (!configurationJSONObject.isNull(
				PhraseSuggesterConfigurationKeys.SHARD_SIZE.getJsonKey())) {

			phraseSuggester.setShardSize(
				configurationJSONObject.getInt(
					PhraseSuggesterConfigurationKeys.SHARD_SIZE.getJsonKey()));
		}

		if (!configurationJSONObject.isNull(
				PhraseSuggesterConfigurationKeys.SIZE.getJsonKey())) {

			phraseSuggester.setSize(
				configurationJSONObject.getInt(
					PhraseSuggesterConfigurationKeys.SIZE.getJsonKey()));
		}

		if (!configurationJSONObject.isNull(
				PhraseSuggesterConfigurationKeys.PRE_HIGHLIGHT_TAG.
					getJsonKey())) {

			phraseSuggester.setPreHighlightFilter(
				configurationJSONObject.getString(
					PhraseSuggesterConfigurationKeys.PRE_HIGHLIGHT_TAG.
						getJsonKey()));
		}

		if (!configurationJSONObject.isNull(
				PhraseSuggesterConfigurationKeys.POST_HIGHLIGHT_TAG.
					getJsonKey())) {

			phraseSuggester.setPostHighlightFilter(
				configurationJSONObject.getString(
					PhraseSuggesterConfigurationKeys.POST_HIGHLIGHT_TAG.
						getJsonKey()));
		}

		return Optional.of(phraseSuggester);
	}

	protected String getValue(
		JSONObject configurationJSONObject, ParameterData parameterData) {

		String text = configurationJSONObject.getString(
			PhraseSuggesterConfigurationKeys.TEXT.getJsonKey());

		if (Validator.isBlank(text)) {
			text = parameterData.getKeywords();
		}

		return StringUtil.toLowerCase(text);
	}

	private Query _getCollateQuery(
		JSONObject collateJSONObject, Messages messages) {

		JSONObject queryJSONObject = collateJSONObject.getJSONObject("query");

		if (queryJSONObject == null) {
			return null;
		}

		JSONObject querySourceJSONObject = queryJSONObject.getJSONObject(
			"source");

		if (querySourceJSONObject == null) {
			messages.addMessage(
				new Message.Builder().className(
					getClass().getName()
				).localizationKey(
					"core.error.undefined-collate-query-source"
				).msg(
					"PhraseSuggester.Collate query source is not defined"
				).rootObject(
					queryJSONObject
				).rootProperty(
					"source"
				).severity(
					Severity.ERROR
				).build());

			return null;
		}

		JSONObject matchQueryJSONObject = querySourceJSONObject.getJSONObject(
			"match");

		if (matchQueryJSONObject == null) {
			messages.addMessage(
				new Message.Builder().className(
					getClass().getName()
				).localizationKey(
					"core.error.unsupported-collate-query-type"
				).msg(
					"PhraseSuggester.Collate query only support Match type"
				).rootObject(
					querySourceJSONObject
				).rootProperty(
					"source"
				).severity(
					Severity.ERROR
				).build());

			return null;
		}

		Iterator<String> iterator = matchQueryJSONObject.keys();

		String field = iterator.next();

		return new MatchQuery(field, matchQueryJSONObject.getString(field));
	}

	private void _setCandidateGenerators(
		PhraseSuggester phraseSuggester, JSONObject configurationJSONObject,
		Messages messages) {

		JSONArray directGeneratorJSONArray =
			configurationJSONObject.getJSONArray(
				PhraseSuggesterConfigurationKeys.DIRECT_GENERATOR.getJsonKey());

		if ((directGeneratorJSONArray == null) ||
			(directGeneratorJSONArray.length() == 0)) {

			return;
		}

		for (int i = 0; i < directGeneratorJSONArray.length(); i++) {
			JSONObject jsonObject = directGeneratorJSONArray.getJSONObject(i);

			String field = jsonObject.getString("field");

			if (Validator.isBlank(field)) {
				continue;
			}

			PhraseSuggester.CandidateGenerator candidateGenerator =
				new PhraseSuggester.CandidateGenerator(field);

			if (!jsonObject.isNull("max_edits")) {
				candidateGenerator.setMaxEdits(jsonObject.getInt("max_edits"));
			}

			if (!jsonObject.isNull("max_inspections")) {
				candidateGenerator.setMaxInspections(
					jsonObject.getInt("max_inspections"));
			}

			if (!jsonObject.isNull("max_term_freq")) {
				candidateGenerator.setMaxTermFreq(
					jsonObject.getInt("max_term_freq"));
			}

			if (!jsonObject.isNull("min_doc_freq")) {
				candidateGenerator.setMinDocFreq(
					jsonObject.getInt("min_doc_freq"));
			}

			if (!jsonObject.isNull("min_word_length")) {
				candidateGenerator.setMinWordLength(
					jsonObject.getInt("min_word_length"));
			}

			if (!jsonObject.isNull("post_filter")) {
				candidateGenerator.setPostFilterAnalyzer(
					jsonObject.getString("post_filter"));
			}

			if (!jsonObject.isNull("pre_filter")) {
				candidateGenerator.setPreFilterAnalyzer(
					jsonObject.getString("pre_filter"));
			}

			if (!jsonObject.isNull("prefix_length")) {
				candidateGenerator.setPrefixLength(
					jsonObject.getInt("prefix_length"));
			}

			if (!jsonObject.isNull("size")) {
				candidateGenerator.setSize(jsonObject.getInt("size"));
			}

			if (!jsonObject.isNull("size")) {
				candidateGenerator.setSize(jsonObject.getInt("size"));
			}

			if (!jsonObject.isNull("suggest_mode")) {
				try {
					candidateGenerator.setSuggestMode(
						getSuggestMode(jsonObject.getString("suggest_mode")));
				}
				catch (IllegalArgumentException illegalArgumentException) {
					messages.addMessage(
						new Message.Builder().className(
							getClass().getName()
						).localizationKey(
							"core.error.unknown-suggest-mode"
						).msg(
							"Unknown suggest mode"
						).rootObject(
							jsonObject
						).rootProperty(
							"suggest_mode"
						).severity(
							Severity.ERROR
						).build());

					_log.error(
						illegalArgumentException.getMessage(),
						illegalArgumentException);
				}
			}

			phraseSuggester.addCandidateGenerator(candidateGenerator);
		}
	}

	private void _setCollate(
		PhraseSuggester phraseSuggester, JSONObject configurationJSONObject,
		Messages messages) {

		if (!configurationJSONObject.has(
				PhraseSuggesterConfigurationKeys.COLLATE.getJsonKey())) {

			return;
		}

		JSONObject collateJSONObject = configurationJSONObject.getJSONObject(
			PhraseSuggesterConfigurationKeys.COLLATE.getJsonKey());

		Query query = _getCollateQuery(collateJSONObject, messages);

		if (query == null) {
			return;
		}

		PhraseSuggester.Collate collate = new PhraseSuggester.Collate(query);

		if (!collateJSONObject.isNull("prune")) {
			collate.setPrune(collateJSONObject.getBoolean("prune"));
		}

		if (!collateJSONObject.isNull("params")) {
			_setCollateParams(
				collate, collateJSONObject.getJSONObject("params"));
		}

		phraseSuggester.setCollate(collate);
	}

	private Map<String, Object> _setCollateParams(
		PhraseSuggester.Collate collate, JSONObject paramsJSONObject) {

		Map<String, Object> params = new HashMap<>();

		Stream<String> stream = paramsJSONObject.keySet(
		).stream();

		stream.forEach(
			key -> collate.addParams(key, paramsJSONObject.get(key)));

		return params;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PhraseSuggesterTranslator.class);

	@Reference
	private Queries _queries;

}