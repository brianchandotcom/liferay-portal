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

package com.liferay.portal.search.tuning.blueprints.suggestions.internal.typeahead;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.tuning.blueprints.engine.component.ServiceComponentReference;
import com.liferay.portal.search.tuning.blueprints.suggestions.attributes.SuggestionsAttributes;
import com.liferay.portal.search.tuning.blueprints.suggestions.constants.SuggestionsConstants;
import com.liferay.portal.search.tuning.blueprints.suggestions.spi.provider.TypeaheadDataProvider;
import com.liferay.portal.search.tuning.blueprints.suggestions.suggestion.Suggestion;
import com.liferay.portal.search.tuning.blueprints.suggestions.typeahead.TypeaheadService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Petteri Karttunen
 */
@Component(immediate = true, service = TypeaheadService.class)
public class TypeaheadServiceImpl implements TypeaheadService {

	public List<Suggestion> getSuggestions(
		SuggestionsAttributes suggestionsAttributes) {

		if (_typeaheadDataProviders.isEmpty()) {
			return new ArrayList<>();
		}

		return fetchSuggestions(suggestionsAttributes);
	}

	protected List<Suggestion> fetchSuggestions(
		SuggestionsAttributes suggestionsAttributes) {

		List<String> includedProviders = _getIncludedProviders(
			suggestionsAttributes);

		Map<String, Suggestion> suggestions = new HashMap<>();

		for (Map.Entry<String, ServiceComponentReference<TypeaheadDataProvider>>
				entry : _typeaheadDataProviders.entrySet()) {

			if (!includedProviders.isEmpty() &&
				!includedProviders.contains(entry.getKey())) {

				continue;
			}

			try {
				ServiceComponentReference<TypeaheadDataProvider> value =
					entry.getValue();

				TypeaheadDataProvider typeaheadDataProvider =
					value.getServiceComponent();

				List<Suggestion> providerSuggestions =
					typeaheadDataProvider.getSuggestions(suggestionsAttributes);

				if (providerSuggestions.isEmpty()) {
					continue;
				}

				_combineResults(
					suggestions, providerSuggestions,
					typeaheadDataProvider.getWeight());
			}
			catch (IllegalArgumentException illegalArgumentException) {
				_log.error(
					illegalArgumentException.getMessage(),
					illegalArgumentException);
			}
		}

		return _getResults(suggestions, suggestionsAttributes.getSize());
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void registerTypeaheadDataProvider(
		TypeaheadDataProvider typeaheadDataProvider,
		Map<String, Object> properties) {

		String name = (String)properties.get("name");

		if (Validator.isBlank(name)) {
			if (_log.isWarnEnabled()) {
				Class<?> clazz = typeaheadDataProvider.getClass();

				_log.warn(
					"Unable to register typeahead data provider " +
						clazz.getName() + ". Name property empty.");
			}

			return;
		}

		int serviceRanking = GetterUtil.get(
			properties.get("service.ranking"), 0);

		ServiceComponentReference<TypeaheadDataProvider>
			serviceComponentReference = new ServiceComponentReference<>(
				typeaheadDataProvider, serviceRanking);

		if (_typeaheadDataProviders.containsKey(name)) {
			ServiceComponentReference<TypeaheadDataProvider> previousReference =
				_typeaheadDataProviders.get(name);

			if (previousReference.compareTo(serviceComponentReference) < 0) {
				_typeaheadDataProviders.put(name, serviceComponentReference);
			}
		}
		else {
			_typeaheadDataProviders.put(name, serviceComponentReference);
		}
	}

	protected void unregisterTypeaheadDataProvider(
		TypeaheadDataProvider typeaheadDataProvider,
		Map<String, Object> properties) {

		String name = (String)properties.get("name");

		if (Validator.isBlank(name)) {
			return;
		}

		_typeaheadDataProviders.remove(name);
	}

	private void _combineResults(
		Map<String, Suggestion> suggestions,
		List<Suggestion> providerSuggestions, int weight) {

		Stream<Suggestion> stream = providerSuggestions.stream();

		stream.forEach(
			suggestion -> {
				float score = suggestion.getScore() * weight;
				String text = suggestion.getText();

				if (!suggestions.containsKey(text)) {
					suggestion.setScore(score);
					suggestion.setText(StringUtil.toLowerCase(text));

					suggestions.put(text, suggestion);
				}
				else {
					Suggestion existingSuggestion = suggestions.get(text);

					if (existingSuggestion.getScore() < score) {
						suggestions.put(text, suggestion);
					}
				}
			});
	}

	private List<String> _getIncludedProviders(
		SuggestionsAttributes suggestionsAttributes) {

		Optional<Object> attributeOptional =
			suggestionsAttributes.getAttributeOptional(
				SuggestionsConstants.INCLUDE_PROVIDERS);

		if (!attributeOptional.isPresent()) {
			return new ArrayList<>();
		}

		Object object = attributeOptional.get();

		if (!List.class.isAssignableFrom(object.getClass())) {
			return new ArrayList<>();
		}

		return (List<String>)object;
	}

	private List<Suggestion> _getResults(
		Map<String, Suggestion> suggestions, int size) {

		Collection<Suggestion> entrySet = suggestions.values();

		Stream<Suggestion> stream = entrySet.stream();

		return stream.sorted(
			Comparator.comparing(
				Suggestion::getScore, Comparator.reverseOrder())
		).limit(
			size
		).collect(
			Collectors.toList()
		);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TypeaheadServiceImpl.class);

	private volatile Map
		<String, ServiceComponentReference<TypeaheadDataProvider>>
			_typeaheadDataProviders = new ConcurrentHashMap<>();

}