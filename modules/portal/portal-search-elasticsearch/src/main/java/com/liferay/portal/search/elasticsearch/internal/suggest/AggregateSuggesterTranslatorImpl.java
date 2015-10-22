/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.search.elasticsearch.internal.suggest;

import com.liferay.portal.kernel.search.suggest.AggregateSuggester;
import com.liferay.portal.kernel.search.suggest.Suggester;
import com.liferay.portal.kernel.search.suggest.SuggesterTranslator;
import com.liferay.portal.search.elasticsearch.suggest.AggregateSuggesterTranslator;

import java.util.List;
import java.util.Map;

import org.elasticsearch.search.suggest.SuggestBuilder;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = AggregateSuggesterTranslator.class)
public class AggregateSuggesterTranslatorImpl
	implements AggregateSuggesterTranslator {

	@Override
	public SuggestBuilder translate(
		AggregateSuggester aggregateSuggester,
		SuggesterTranslator<SuggestBuilder> suggesterTranslator) {

		SuggestBuilder aggregateSuggestBuilder = new SuggestBuilder(
			aggregateSuggester.getName());

		aggregateSuggestBuilder.setText(aggregateSuggester.getValue());

		Map<String, Suggester> suggesters = aggregateSuggester.getSuggesters();

		for (Suggester suggester : suggesters.values()) {
			SuggestBuilder suggestBuilder = suggesterTranslator.translate(
				suggester, null);

			List<SuggestBuilder.SuggestionBuilder<?>> suggestionBuilders =
				suggestBuilder.getSuggestion();

			for (SuggestBuilder.SuggestionBuilder<?> suggestionBuilder :
					suggestionBuilders) {

				suggestionBuilder.text(null);

				aggregateSuggestBuilder.addSuggestion(suggestionBuilder);
			}
		}

		return aggregateSuggestBuilder;
	}

}