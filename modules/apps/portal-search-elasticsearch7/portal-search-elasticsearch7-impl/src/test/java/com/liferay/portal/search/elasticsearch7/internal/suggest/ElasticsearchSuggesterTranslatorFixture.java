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

package com.liferay.portal.search.elasticsearch7.internal.suggest;

import com.liferay.portal.kernel.test.ReflectionTestUtil;

/**
 * @author Michael C. Han
 */
public class ElasticsearchSuggesterTranslatorFixture {

	public ElasticsearchSuggesterTranslatorFixture() {
		ReflectionTestUtil.setFieldValue(
			_elasticsearchSuggesterTranslator, "_completionSuggesterTranslator",
			new CompletionSuggesterTranslatorImpl());
		ReflectionTestUtil.setFieldValue(
			_elasticsearchSuggesterTranslator, "_phraseSuggesterTranslator",
			new PhraseSuggesterTranslatorImpl());
		ReflectionTestUtil.setFieldValue(
			_elasticsearchSuggesterTranslator, "_termSuggesterTranslator",
			new TermSuggesterTranslatorImpl());
	}

	public ElasticsearchSuggesterTranslator
		getElasticsearchSuggesterTranslator() {

		return _elasticsearchSuggesterTranslator;
	}

	private final ElasticsearchSuggesterTranslator
		_elasticsearchSuggesterTranslator =
			new ElasticsearchSuggesterTranslator();

}