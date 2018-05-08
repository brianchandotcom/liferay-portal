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

import com.liferay.portal.kernel.search.suggest.Suggester;

/**
 * @author Michael C. Han
 */
public abstract class BaseSuggesterTranslatorImpl {

	protected String translate(Suggester.Sort sort) {
		if (sort == Suggester.Sort.FREQUENCY) {
			return "frequency";
		}
		else {
			return "score";
		}
	}

	protected String translate(Suggester.StringDistance stringDistance) {
		if (stringDistance == Suggester.StringDistance.DAMERAU_LEVENSHTEIN) {
			return "damerau_levnshtein";
		}
		else if (stringDistance == Suggester.StringDistance.JAROWINKLER) {
			return "jarowinkler";
		}
		else if (stringDistance == Suggester.StringDistance.LEVENSTEIN) {
			return "levenstein";
		}
		else if (stringDistance == Suggester.StringDistance.NGRAM) {
			return "ngram";
		}
		else {
			return "internal";
		}
	}

	protected String translate(Suggester.SuggestMode suggestMode) {
		if (suggestMode == Suggester.SuggestMode.ALWAYS) {
			return "always";
		}
		else if (suggestMode == Suggester.SuggestMode.POPULAR) {
			return "popular";
		}
		else {
			return "missing";
		}
	}

}