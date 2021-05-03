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

package com.liferay.portal.search.tuning.blueprints.searchresponse.json.translator.internal;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.tuning.blueprints.engine.attributes.BlueprintsAttributes;
import com.liferay.portal.search.tuning.blueprints.message.Message;
import com.liferay.portal.search.tuning.blueprints.message.Messages;
import com.liferay.portal.search.tuning.blueprints.model.Blueprint;
import com.liferay.portal.search.tuning.blueprints.searchresponse.json.translator.SearchResponseJSONTranslator;
import com.liferay.portal.search.tuning.blueprints.searchresponse.json.translator.constants.JSONKeys;
import com.liferay.portal.search.tuning.blueprints.searchresponse.json.translator.spi.contributor.JSONTranslationContributor;
import com.liferay.portal.search.tuning.blueprints.service.BlueprintService;
import com.liferay.portal.search.tuning.blueprints.util.component.ServiceComponentReference;
import com.liferay.portal.search.tuning.blueprints.util.component.ServiceComponentReferenceUtil;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Petteri Karttunen
 */
@Component(immediate = true, service = SearchResponseJSONTranslator.class)
public class SearchResponseJSONTranslatorImpl
	implements SearchResponseJSONTranslator {

	@Override
	public JSONObject translate(
			SearchResponse searchResponse, Blueprint blueprint,
			BlueprintsAttributes blueprintsAttributes,
			ResourceBundle resourceBundle, Messages messages)
		throws PortalException {

		JSONObject responseJSONObject = _jsonFactory.createJSONObject();

		for (Map.Entry
				<String, ServiceComponentReference<JSONTranslationContributor>>
					entry : _jsonTranslationContributors.entrySet()) {

			ServiceComponentReference<JSONTranslationContributor> value =
				entry.getValue();

			JSONTranslationContributor responseContributor =
				value.getServiceComponent();

			responseContributor.contribute(
				responseJSONObject, searchResponse, blueprint,
				blueprintsAttributes, resourceBundle, messages);
		}

		return responseJSONObject;
	}

	@Override
	public JSONObject translateErrorMessages(
		List<Message> errorMessages, ResourceBundle resourceBundle) {

		JSONObject responseJSONObject = _jsonFactory.createJSONObject();

		JSONArray errorMessagesJSONArray = _jsonFactory.createJSONArray();

		for (Message message : errorMessages) {
			JSONObject errorMessageJSONObject = _jsonFactory.createJSONObject();

			errorMessageJSONObject.put(
				"className", message.getClassName()
			).put(
				"elementId", message.getElementId()
			).put(
				"localizedMessage",
				_language.get(resourceBundle, message.getLocalizationKey())
			).put(
				"msg", message.getMsg()
			).put(
				"rootObject", message.getRootObject()
			).put(
				"rootProperty", message.getRootProperty()
			).put(
				"rootValue", message.getRootValue()
			).put(
				"severity", message.getSeverity()
			).put(
				"throwable", message.getThrowable()
			);

			errorMessagesJSONArray.put(errorMessageJSONObject);
		}

		responseJSONObject.put(JSONKeys.ERRORS, errorMessagesJSONArray);

		return responseJSONObject;
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void registerJSONTranslationContributor(
		JSONTranslationContributor jsonTranslationContributor,
		Map<String, Object> properties) {

		ServiceComponentReferenceUtil.addToMapByName(
			_jsonTranslationContributors, jsonTranslationContributor,
			properties);
	}

	protected void unregisterJSONTranslationContributor(
		JSONTranslationContributor responseContributor,
		Map<String, Object> properties) {

		ServiceComponentReferenceUtil.removeFromMapByName(
			_jsonTranslationContributors, responseContributor, properties);
	}

	@Reference
	private BlueprintService _blueprintService;

	@Reference
	private JSONFactory _jsonFactory;

	private volatile Map
		<String, ServiceComponentReference<JSONTranslationContributor>>
			_jsonTranslationContributors = new ConcurrentHashMap<>();

	@Reference
	private Language _language;

}