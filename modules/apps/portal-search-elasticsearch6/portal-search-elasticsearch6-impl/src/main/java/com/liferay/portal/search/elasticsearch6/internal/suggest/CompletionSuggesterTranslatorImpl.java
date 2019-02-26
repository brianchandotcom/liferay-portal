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

package com.liferay.portal.search.elasticsearch6.internal.suggest;

import com.liferay.portal.kernel.search.suggest.CompletionSuggester;
import com.liferay.portal.kernel.util.Validator;

import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.SuggestionBuilder;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = CompletionSuggesterTranslator.class)
public class CompletionSuggesterTranslatorImpl
	implements CompletionSuggesterTranslator {

	@Override
	public SuggestionBuilder translate(
		CompletionSuggester completionSuggester) {

		CompletionSuggestionBuilder completionSuggesterBuilder =
			SuggestBuilders.completionSuggestion(
				completionSuggester.getField());

		if (Validator.isNotNull(completionSuggester.getAnalyzer())) {
			completionSuggesterBuilder.analyzer(
				completionSuggester.getAnalyzer());
		}

		if (completionSuggester.getShardSize() != null) {
			completionSuggesterBuilder.shardSize(
				completionSuggester.getShardSize());
		}

		if (completionSuggester.getSize() != null) {
			completionSuggesterBuilder.size(completionSuggester.getSize());
		}

		completionSuggesterBuilder.text(completionSuggester.getValue());

		return completionSuggesterBuilder;
	}

}