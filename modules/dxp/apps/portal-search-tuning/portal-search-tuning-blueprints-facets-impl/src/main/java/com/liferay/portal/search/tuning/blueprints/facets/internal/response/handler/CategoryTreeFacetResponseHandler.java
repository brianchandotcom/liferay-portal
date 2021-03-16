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

package com.liferay.portal.search.tuning.blueprints.facets.internal.response.handler;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.aggregation.AggregationResult;
import com.liferay.portal.search.aggregation.bucket.Bucket;
import com.liferay.portal.search.aggregation.bucket.TermsAggregationResult;
import com.liferay.portal.search.tuning.blueprints.attributes.BlueprintsAttributes;
import com.liferay.portal.search.tuning.blueprints.facets.constants.FacetConfigurationKeys;
import com.liferay.portal.search.tuning.blueprints.facets.constants.FacetsJSONResponseKeys;
import com.liferay.portal.search.tuning.blueprints.facets.spi.response.FacetResponseHandler;
import com.liferay.portal.search.tuning.blueprints.message.Message;
import com.liferay.portal.search.tuning.blueprints.message.Messages;
import com.liferay.portal.search.tuning.blueprints.message.Severity;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true, property = "name=category_tree",
	service = FacetResponseHandler.class
)
public class CategoryTreeFacetResponseHandler
	extends BaseTermsFacetResponseHandler implements FacetResponseHandler {

	@Override
	public Optional<JSONObject> getResultOptional(
		AggregationResult aggregationResult,
		BlueprintsAttributes blueprintsAttributes,
		ResourceBundle resourceBundle, Messages messages,
		JSONObject configurationJSONObject) {

		JSONObject handlerParametersJSONObject =
			configurationJSONObject.getJSONObject(
				FacetConfigurationKeys.HANDLER_PARAMETERS.getJsonKey());

		if (handlerParametersJSONObject == null) {
			messages.addMessage(
				new Message.Builder().className(
					getClass().getName()
				).localizationKey(
					"facets.error.undefined-handler-parameters"
				).msg(
					"Facet handler parameters are not defined"
				).rootObject(
					handlerParametersJSONObject
				).rootProperty(
					FacetConfigurationKeys.HANDLER_PARAMETERS.getJsonKey()
				).severity(
					Severity.ERROR
				).build());

			return Optional.empty();
		}

		Long vocabularyId = handlerParametersJSONObject.getLong(
			"root_vocabulary_id", -1);

		if (vocabularyId < 0) {
			messages.addMessage(
				new Message.Builder().className(
					getClass().getName()
				).localizationKey(
					"facets.error.root-vocabulary-id-missing"
				).msg(
					"Root vocabulary id is not defined"
				).rootObject(
					handlerParametersJSONObject
				).rootProperty(
					"vocabulary_id"
				).rootValue(
					String.valueOf(vocabularyId)
				).severity(
					Severity.ERROR
				).build());

			return Optional.empty();
		}

		TermsAggregationResult termsAggregationResult =
			(TermsAggregationResult)aggregationResult;

		Collection<Bucket> buckets = termsAggregationResult.getBuckets();

		if (buckets.isEmpty()) {
			return Optional.empty();
		}

		long frequencyThreshold = configurationJSONObject.getLong(
			FacetConfigurationKeys.FREQUENCY_THRESHOLD.getJsonKey(), 1);

		JSONArray jsonArray = null;

		try {
			jsonArray = _getCategoriesJSONArray(
				buckets, vocabularyId, frequencyThreshold,
				blueprintsAttributes.getLocale(), messages);
		}
		catch (PortalException portalException) {
			messages.addMessage(
				new Message.Builder().className(
					getClass().getName()
				).localizationKey(
					"facets.error.asset-vocabulary-not-found"
				).msg(
					portalException.getMessage()
				).rootObject(
					handlerParametersJSONObject
				).rootProperty(
					"vocabulary_id"
				).rootValue(
					String.valueOf(vocabularyId)
				).severity(
					Severity.ERROR
				).throwable(
					portalException
				).build());

			if (_log.isWarnEnabled()) {
				_log.warn("Asset vocabulary " + vocabularyId + " not found.");
			}

			return Optional.empty();
		}

		return createResultObject(
			jsonArray, configurationJSONObject, resourceBundle);
	}

	private void _addChildNode(
		JSONArray jsonArray, List<AssetCategory> assetCategories,
		AssetCategory assetCategory, long frequency, Locale locale,
		Messages messages) {

		long categoryId = assetCategory.getCategoryId();

		JSONObject parentJSONObject = _getParentNodeJSONObject(
			jsonArray, assetCategories, assetCategory, frequency, locale);

		if (parentJSONObject == null) {
			messages.addMessage(
				new Message.Builder().className(
					getClass().getName()
				).localizationKey(
					"facets.error.asset-category-not-found"
				).msg(
					"Asset category not found"
				).rootValue(
					String.valueOf(categoryId)
				).severity(
					Severity.ERROR
				).build());

			if (_log.isWarnEnabled()) {
				_log.warn("Asset category " + categoryId + " not found");
			}

			return;
		}

		JSONObject childJSONObject = _createNode(
			categoryId, frequency, assetCategory.getTitle(locale));

		_addToChildren(parentJSONObject, childJSONObject);
	}

	private JSONObject _addRootNodeJSONObject(
		JSONArray jsonArray, AssetCategory assetCategory, long frequency,
		Locale locale) {

		long categoryId = assetCategory.getCategoryId();

		JSONObject rootNodeJSONObject = _findCategoryJSONObject(
			jsonArray, categoryId, true);

		if (rootNodeJSONObject != null) {
			_updateFrequency(rootNodeJSONObject, frequency);

			return rootNodeJSONObject;
		}

		rootNodeJSONObject = _createNode(
			categoryId, frequency, assetCategory.getTitle(locale));

		jsonArray.put(rootNodeJSONObject);

		return rootNodeJSONObject;
	}

	private void _addToChildren(
		JSONObject parentJSONObject, JSONObject childJSONObject) {

		JSONArray jsonArray = null;

		if (parentJSONObject.has("children")) {
			jsonArray = parentJSONObject.getJSONArray("children");
		}
		else {
			jsonArray = JSONFactoryUtil.createJSONArray();

			parentJSONObject.put("children", jsonArray);
		}

		jsonArray.put(childJSONObject);
	}

	private JSONObject _createNode(long value, long frequency, String name) {
		return JSONUtil.put(
			FacetsJSONResponseKeys.FREQUENCY, frequency
		).put(
			FacetsJSONResponseKeys.NAME, name
		).put(
			FacetsJSONResponseKeys.TEXT, getText(name, frequency, null)
		).put(
			FacetsJSONResponseKeys.VALUE, value
		);
	}

	private void _createTree(
		JSONArray jsonArray, List<AssetCategory> assetCategories,
		long categoryId, long frequency, Locale locale, Messages messages) {

		Stream<AssetCategory> stream = assetCategories.stream();

		Optional<AssetCategory> optional = stream.filter(
			a -> a.getCategoryId() == categoryId
		).findAny();

		if (!optional.isPresent()) {
			return;
		}

		AssetCategory assetCategory = optional.get();

		if (assetCategory.getParentCategoryId() == 0) {
			_addRootNodeJSONObject(jsonArray, assetCategory, frequency, locale);

			return;
		}

		_addChildNode(
			jsonArray, assetCategories, assetCategory, frequency, locale,
			messages);
	}

	private JSONObject _findCategoryJSONObject(
		JSONArray jsonArray, long categoryId, boolean root) {

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject1 = jsonArray.getJSONObject(i);

			if (jsonObject1.getLong(FacetsJSONResponseKeys.VALUE) ==
					categoryId) {

				return jsonObject1;
			}

			if (!root && jsonObject1.has("children")) {
				JSONObject jsonObject2 = _findCategoryJSONObject(
					jsonObject1.getJSONArray("children"), categoryId, false);

				if (jsonObject2 != null) {
					return jsonObject2;
				}
			}
		}

		return null;
	}

	private JSONArray _getCategoriesJSONArray(
			Collection<Bucket> buckets, long vocabularyId,
			long frequencyThreshold, Locale locale, Messages messages)
		throws PortalException {

		AssetVocabulary assetVocabulary =
			_assetVocabularyLocalService.getVocabulary(vocabularyId);

		List<AssetCategory> assetCategories = assetVocabulary.getCategories();

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (Bucket bucket : buckets) {
			long frequency = bucket.getDocCount();

			if (frequency < frequencyThreshold) {
				continue;
			}

			long categoryId = Long.valueOf(bucket.getKey());

			_createTree(
				jsonArray, assetCategories, categoryId, frequency, locale,
				messages);
		}

		return jsonArray;
	}

	private JSONObject _getParentNodeJSONObject(
		JSONArray jsonArray, List<AssetCategory> assetCategories,
		AssetCategory assetCategory, long frequency, Locale locale) {

		String[] ids = StringUtil.split(assetCategory.getTreePath(), "/");

		JSONObject parentJSONObject = null;

		for (int i = 1; i < (ids.length - 1); i++) {
			Stream<AssetCategory> stream = assetCategories.stream();

			long categoryId = Long.valueOf(ids[i]);

			Optional<AssetCategory> optional = stream.filter(
				a -> a.getCategoryId() == categoryId
			).findAny();

			if (!optional.isPresent()) {
				continue;
			}

			parentJSONObject = _getTreeNodeJSONObject(
				jsonArray, optional.get(), frequency, locale);
		}

		return parentJSONObject;
	}

	private JSONObject _getTreeNodeJSONObject(
		JSONArray jsonArray, AssetCategory assetCategory, long frequency,
		Locale locale) {

		long categoryId = assetCategory.getCategoryId();

		long parentCategoryId = assetCategory.getParentCategoryId();

		String title = assetCategory.getTitle(locale);

		if (parentCategoryId == 0) {
			return _addRootNodeJSONObject(
				jsonArray, assetCategory, frequency, locale);
		}

		JSONObject childNodeJSONObject = _findCategoryJSONObject(
			jsonArray, categoryId, false);

		if (childNodeJSONObject != null) {
			_updateFrequency(childNodeJSONObject, frequency);

			return childNodeJSONObject;
		}

		JSONObject parentJSONObject = _findCategoryJSONObject(
			jsonArray, parentCategoryId, false);

		childNodeJSONObject = _createNode(categoryId, frequency, title);

		_addToChildren(parentJSONObject, childNodeJSONObject);

		return childNodeJSONObject;
	}

	private void _updateFrequency(JSONObject jsonObject, long count) {
		long frequency = jsonObject.getLong(FacetsJSONResponseKeys.FREQUENCY);

		frequency += count;

		jsonObject.put(FacetsJSONResponseKeys.FREQUENCY, frequency);

		String name = jsonObject.getString(FacetsJSONResponseKeys.NAME);

		jsonObject.put(
			FacetsJSONResponseKeys.TEXT, getText(name, frequency, null));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CategoryTreeFacetResponseHandler.class);

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

}