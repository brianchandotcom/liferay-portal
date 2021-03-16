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
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.tuning.blueprints.attributes.BlueprintsAttributes;
import com.liferay.portal.search.tuning.blueprints.engine.component.ServiceComponentReference;
import com.liferay.portal.search.tuning.blueprints.message.Messages;
import com.liferay.portal.search.tuning.blueprints.model.Blueprint;
import com.liferay.portal.search.tuning.blueprints.searchresponse.json.translator.SearchResponseJSONTranslator;
import com.liferay.portal.search.tuning.blueprints.searchresponse.json.translator.spi.contributor.JSONTranslationContributor;
import com.liferay.portal.search.tuning.blueprints.service.BlueprintService;

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

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void registerJSONTranslationContributor(
		JSONTranslationContributor jsonTranslationContributor,
		Map<String, Object> properties) {

		String name = (String)properties.get("name");

		if (Validator.isBlank(name)) {
			if (_log.isWarnEnabled()) {
				Class<?> clazz = jsonTranslationContributor.getClass();

				_log.warn(
					"Unable to add response contributor " + clazz.getName() +
						". Name property empty.");
			}

			return;
		}

		int serviceRanking = GetterUtil.get(
			properties.get("service.ranking"), 0);

		ServiceComponentReference<JSONTranslationContributor>
			serviceComponentReference = new ServiceComponentReference<>(
				jsonTranslationContributor, serviceRanking);

		if (_jsonTranslationContributors.containsKey(name)) {
			ServiceComponentReference<JSONTranslationContributor>
				previousReference = _jsonTranslationContributors.get(name);

			if (previousReference.compareTo(serviceComponentReference) < 0) {
				_jsonTranslationContributors.put(
					name, serviceComponentReference);
			}
		}
		else {
			_jsonTranslationContributors.put(name, serviceComponentReference);
		}
	}

	protected void unregisterJSONTranslationContributor(
		JSONTranslationContributor responseContributor,
		Map<String, Object> properties) {

		String name = (String)properties.get("name");

		if (Validator.isBlank(name)) {
			return;
		}

		_jsonTranslationContributors.remove(name);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SearchResponseJSONTranslatorImpl.class);

	@Reference
	private BlueprintService _blueprintService;

	@Reference
	private JSONFactory _jsonFactory;

	private volatile Map
		<String, ServiceComponentReference<JSONTranslationContributor>>
			_jsonTranslationContributors = new ConcurrentHashMap<>();

}