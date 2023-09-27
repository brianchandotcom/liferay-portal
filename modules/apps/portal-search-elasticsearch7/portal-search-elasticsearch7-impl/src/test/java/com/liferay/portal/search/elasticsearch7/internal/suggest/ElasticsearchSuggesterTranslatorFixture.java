/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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