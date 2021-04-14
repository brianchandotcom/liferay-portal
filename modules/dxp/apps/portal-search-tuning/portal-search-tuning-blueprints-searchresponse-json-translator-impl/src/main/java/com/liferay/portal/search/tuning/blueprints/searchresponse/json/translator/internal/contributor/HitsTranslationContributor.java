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

package com.liferay.portal.search.tuning.blueprints.searchresponse.json.translator.internal.contributor;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.highlight.HighlightField;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.tuning.blueprints.attributes.BlueprintsAttributes;
import com.liferay.portal.search.tuning.blueprints.message.Messages;
import com.liferay.portal.search.tuning.blueprints.model.Blueprint;
import com.liferay.portal.search.tuning.blueprints.searchresponse.json.translator.constants.ResponseAttributeKeys;
import com.liferay.portal.search.tuning.blueprints.searchresponse.json.translator.internal.result.ResultBuilderFactory;
import com.liferay.portal.search.tuning.blueprints.searchresponse.json.translator.internal.util.ResultUtil;
import com.liferay.portal.search.tuning.blueprints.searchresponse.json.translator.spi.contributor.JSONTranslationContributor;
import com.liferay.portal.search.tuning.blueprints.searchresponse.json.translator.spi.hit.HitContributor;
import com.liferay.portal.search.tuning.blueprints.searchresponse.json.translator.spi.result.ResultBuilder;
import com.liferay.portal.search.tuning.blueprints.util.component.ServiceComponentReference;
import com.liferay.portal.search.tuning.blueprints.util.component.ServiceComponentReferenceUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true, property = "name=hits",
	service = JSONTranslationContributor.class
)
public class HitsTranslationContributor implements JSONTranslationContributor {

	@Override
	public void contribute(
		JSONObject responseJSONObject, SearchResponse searchResponse,
		Blueprint blueprint, BlueprintsAttributes blueprintsAttributes,
		ResourceBundle resourceBundle, Messages messages) {

		responseJSONObject.put(
			"hits",
			_getHitsJSONArray(
				searchResponse, blueprintsAttributes, resourceBundle));
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void registerHitContributor(
		HitContributor hitContributor, Map<String, Object> properties) {

		ServiceComponentReferenceUtil.addToMapByName(
			_hitContributors, hitContributor, properties);
	}

	protected void unregisterHitContributor(
		HitContributor hitContributor, Map<String, Object> properties) {

		ServiceComponentReferenceUtil.removeFromMapByName(
			_hitContributors, hitContributor, properties);
	}

	private void _addDefaultResultFields(
			JSONObject hitJSONObject, ResultBuilder resultBuilder,
			Document document, BlueprintsAttributes blueprintsAttributes,
			ResourceBundle resourceBundle)
		throws Exception {

		hitJSONObject.put(
			"b_author", resultBuilder.getAuthor(document, blueprintsAttributes)
		).put(
			"b_created",
			resultBuilder.getCreateDate(document, blueprintsAttributes)
		).put(
			"b_modified",
			resultBuilder.getModificationDate(document, blueprintsAttributes)
		).put(
			"b_summary",
			resultBuilder.getSummary(document, blueprintsAttributes)
		).put(
			"b_title", resultBuilder.getTitle(document, blueprintsAttributes)
		).put(
			"b_type", _getType(resultBuilder, document, resourceBundle)
		);
	}

	private void _addResultFields(
			JSONObject hitJSONObject, SearchHit searchHit,
			List<String> resultFields,
			BlueprintsAttributes blueprintsAttributes,
			ResourceBundle resourceBundle)
		throws Exception {

		Document document = searchHit.getDocument();

		ResultBuilder resultBuilder = _resultBuilderFactory.getBuilder(
			document.getString("entryClassName"));

		if (resultFields.isEmpty()) {
			_addDefaultResultFields(
				hitJSONObject, resultBuilder, document, blueprintsAttributes,
				resourceBundle);
		}
		else {
			for (String fieldName : resultFields) {
				if (fieldName.equalsIgnoreCase("b_assetEntryId")) {
					hitJSONObject.put(
						"b_assetEntryId", _getAssetEntryId(document));
				}
				else if (fieldName.equalsIgnoreCase("b_author")) {
					hitJSONObject.put(
						"b_author",
						resultBuilder.getAuthor(
							document, blueprintsAttributes));
				}
				else if (fieldName.equalsIgnoreCase("b_created")) {
					hitJSONObject.put(
						"b_created",
						resultBuilder.getCreateDate(
							document, blueprintsAttributes));
				}
				else if (fieldName.equalsIgnoreCase("b_modified")) {
					hitJSONObject.put(
						"b_modified",
						resultBuilder.getModificationDate(
							document, blueprintsAttributes));
				}
				else if (fieldName.equalsIgnoreCase("b_summary")) {
					hitJSONObject.put(
						"b_summary",
						resultBuilder.getSummary(
							document, blueprintsAttributes));
				}
				else if (fieldName.equalsIgnoreCase("b_title")) {
					hitJSONObject.put(
						"b_title",
						resultBuilder.getTitle(document, blueprintsAttributes));
				}
				else if (fieldName.equalsIgnoreCase("b_type")) {
					hitJSONObject.put(
						"b_type",
						_getType(resultBuilder, document, resourceBundle));
				}
				else if (fieldName.equalsIgnoreCase("b_authorPortraitUrl") ||
						 fieldName.equalsIgnoreCase("b_authorInitials")) {

					_setUserPortrait(hitJSONObject, document);
				}
				else if (fieldName.equalsIgnoreCase("b_thumbnailSrc")) {
					hitJSONObject.put(
						"b_thumbnailSrc",
						resultBuilder.getThumbnail(
							document, blueprintsAttributes));
				}
				else if (fieldName.equalsIgnoreCase("id")) {
					hitJSONObject.put("id", searchHit.getId());
				}
				else if (fieldName.equalsIgnoreCase("score")) {
					hitJSONObject.put("score", searchHit.getScore());
				}
				else {
					hitJSONObject.put(fieldName, document.getValue(fieldName));
				}
			}
		}

		_setHightlightFields(hitJSONObject, searchHit, blueprintsAttributes);

		_executeHitContributors(
			hitJSONObject, document, resultBuilder, blueprintsAttributes,
			resourceBundle);
	}

	private void _executeHitContributors(
		JSONObject hitJSONObject, Document document,
		ResultBuilder resultBuilder, BlueprintsAttributes blueprintsAttributes,
		ResourceBundle resourceBundle) {

		for (Map.Entry<String, ServiceComponentReference<HitContributor>>
				entry : _hitContributors.entrySet()) {

			ServiceComponentReference<HitContributor> value = entry.getValue();

			HitContributor hitContributor = value.getServiceComponent();

			hitContributor.contribute(
				hitJSONObject, document, resultBuilder, blueprintsAttributes,
				resourceBundle);
		}
	}

	private Map<String, List<Object>> _formatDocument(Document document) {
		Map<String, com.liferay.portal.search.document.Field> fields =
			document.getFields();

		Set<Map.Entry<String, com.liferay.portal.search.document.Field>>
			entrySet = fields.entrySet();

		Stream<Map.Entry<String, com.liferay.portal.search.document.Field>>
			stream = entrySet.stream();

		return stream.collect(
			HashMap::new,
			(map, entry) -> {
				com.liferay.portal.search.document.Field field =
					entry.getValue();

				map.put(entry.getKey(), field.getValues());
			},
			HashMap::putAll);
	}

	private Long _getAssetEntryId(Document document) {
		try {
			AssetEntry assetEntry = _assetEntryLocalService.getEntry(
				document.getString(Field.ENTRY_CLASS_NAME),
				document.getLong(Field.ENTRY_CLASS_PK));

			return assetEntry.getEntryId();
		}
		catch (PortalException portalException) {
			_log.error(portalException.getMessage(), portalException);
		}

		return null;
	}

	private JSONArray _getHitsJSONArray(
		SearchResponse searchResponse,
		BlueprintsAttributes blueprintsAttributes,
		ResourceBundle resourceBundle) {

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		SearchHits searchHits = searchResponse.getSearchHits();

		List<SearchHit> searchHitsList = searchHits.getSearchHits();

		if (searchHitsList.isEmpty()) {
			return jsonArray;
		}

		List<String> resultFields = _getResultFields(blueprintsAttributes);

		for (SearchHit searchHit : searchHitsList) {
			try {
				JSONObject hitJSONObject = _jsonFactory.createJSONObject();

				if (!Validator.isBlank(searchHit.getExplanation())) {
					hitJSONObject.put(
						"explanation", searchHit.getExplanation());
				}

				if (_includeDocument(blueprintsAttributes)) {
					hitJSONObject.put(
						"document", _formatDocument(searchHit.getDocument()));
				}

				if (_includeHighlightFieldsMap(blueprintsAttributes)) {
					hitJSONObject.put(
						"highlightFieldsMap",
						searchHit.getHighlightFieldsMap());
				}

				if (_includeResult(blueprintsAttributes)) {
					_addResultFields(
						hitJSONObject, searchHit, resultFields,
						blueprintsAttributes, resourceBundle);
				}

				jsonArray.put(hitJSONObject);
			}
			catch (JSONException jsonException) {
				_log.error(jsonException.getMessage(), jsonException);
			}
			catch (Exception exception) {
				_log.error(exception.getMessage(), exception);
			}
		}

		return jsonArray;
	}

	private List<String> _getResultFields(
		BlueprintsAttributes blueprintsAttributes) {

		Optional<Object> optional = blueprintsAttributes.getAttributeOptional(
			ResponseAttributeKeys.RESULT_FIELDS);

		if (!optional.isPresent()) {
			return new ArrayList<>();
		}

		return (List<String>)optional.get();
	}

	private String _getType(
			ResultBuilder resultBuilder, Document document,
			ResourceBundle resourceBundle)
		throws Exception {

		return _language.get(
			resourceBundle,
			StringUtil.toLowerCase(resultBuilder.getType(document)));
	}

	private boolean _includeDocument(
		BlueprintsAttributes blueprintsAttributes) {

		Optional<Object> includeOptional =
			blueprintsAttributes.getAttributeOptional(
				ResponseAttributeKeys.INCLUDE_DOCUMENT);

		if (!includeOptional.isPresent()) {
			return false;
		}

		return GetterUtil.getBoolean(includeOptional.get());
	}

	private boolean _includeHighlightFieldsMap(
		BlueprintsAttributes blueprintsAttributes) {

		Optional<Object> includeOptional =
			blueprintsAttributes.getAttributeOptional(
				ResponseAttributeKeys.INCLUDE_HIGHLIGHT_FIELDS_MAP);

		if (!includeOptional.isPresent()) {
			return false;
		}

		return GetterUtil.getBoolean(includeOptional.get());
	}

	private boolean _includeResult(BlueprintsAttributes blueprintsAttributes) {
		Optional<Object> includeOptional =
			blueprintsAttributes.getAttributeOptional(
				ResponseAttributeKeys.INCLUDE_RESULT);

		if (!includeOptional.isPresent()) {
			return false;
		}

		return GetterUtil.getBoolean(includeOptional.get());
	}

	private void _setHightlightFields(
		JSONObject hitJSONObject, SearchHit searchHit,
		BlueprintsAttributes blueprintsAttributes) {

		Map<String, HighlightField> highlightFieldsMap =
			searchHit.getHighlightFieldsMap();

		if (highlightFieldsMap.isEmpty()) {
			return;
		}

		Optional<Object> descriptionMaxLengthOptional =
			blueprintsAttributes.getAttributeOptional(
				ResponseAttributeKeys.SUMMARY_MAX_LENGTH);

		int descriptionMaxLength = GetterUtil.getInteger(
			descriptionMaxLengthOptional.orElse(700));

		Map<String, HighlightField> map = searchHit.getHighlightFieldsMap();

		for (Map.Entry<String, HighlightField> entry : map.entrySet()) {
			try {
				StringBundler sb = new StringBundler();

				int i = 0;

				HighlightField highlightField = entry.getValue();

				for (String s : highlightField.getFragments()) {
					if (i > 0) {
						sb.append("...");
					}

					sb.append(s);
					i++;
				}

				String key = entry.getKey();

				if (key.contains("_")) {
					key = key.substring(0, key.indexOf("_"));
				}

				String cleanedText = ResultUtil.stripHTML(
					sb.toString(), descriptionMaxLength);

				hitJSONObject.put(key + "_highlight", cleanedText);
			}
			catch (IllegalStateException illegalStateException) {
				_log.error(
					illegalStateException.getMessage(), illegalStateException);
			}
			catch (IndexOutOfBoundsException indexOutOfBoundsException) {
				_log.error(
					indexOutOfBoundsException.getMessage(),
					indexOutOfBoundsException);
			}
			catch (Exception exception) {
				_log.error(exception.getMessage(), exception);
			}
		}
	}

	private void _setUserPortrait(JSONObject hitJSONObject, Document document)
		throws Exception {

		try {
			long userId = document.getLong(Field.USER_ID);

			User user = _userLocalService.getUser(userId);

			if (user.getPortraitId() != 0) {
				String userPortraitUrl = UserConstants.getPortraitURL(
					"/image", user.isMale(), user.getPortraitId(),
					user.getUserUuid());

				if (userPortraitUrl != null) {
					hitJSONObject.put("b_authorPortraitUrl", userPortraitUrl);
				}
			}

			String firstName = user.getFirstName();

			String firstNameInitials = firstName.substring(0, 1);

			String lastName = user.getLastName();

			String lastNameInitials = lastName.substring(0, 1);

			hitJSONObject.put(
				"b_authorInitials",
				StringUtil.toUpperCase(firstNameInitials + lastNameInitials));
		}
		catch (PortalException portalException) {
			String name = document.getString(Field.USER_NAME);

			String[] nameParts = name.split(" ");

			String firstNameInitials = nameParts[0].substring(0, 1);

			String lastNameInitials = nameParts[1].substring(0, 1);

			hitJSONObject.put(
				"b_userInitials",
				StringUtil.toUpperCase(firstNameInitials + lastNameInitials));

			if (_log.isWarnEnabled()) {
				_log.warn(portalException.getMessage(), portalException);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		HitsTranslationContributor.class);

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	private volatile Map<String, ServiceComponentReference<HitContributor>>
		_hitContributors = new ConcurrentHashMap<>();

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	@Reference
	private ResultBuilderFactory _resultBuilderFactory;

	@Reference
	private UserLocalService _userLocalService;

}