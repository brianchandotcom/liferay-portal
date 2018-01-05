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

package com.liferay.portal.search.suggest;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.analysis.Tokenizer;
import com.liferay.portal.kernel.search.suggest.Collator;
import com.liferay.portal.kernel.search.suggest.QuerySuggester;
import com.liferay.portal.kernel.search.suggest.Suggester;
import com.liferay.portal.kernel.search.suggest.SuggesterResults;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.search.analysis.SimpleTokenizer;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Michael C. Han
 */
public abstract class BaseQuerySuggester implements QuerySuggester {

	@Override
	public String spellCheckKeywords(SearchContext searchContext)
		throws SearchException {

		Map<String, List<String>> suggestions = spellCheckKeywords(
			searchContext, 1);

		// Must use LocalizationUtil instead of @Reference Localization,
		// otherwise it becomes an unsatisfied reference and the component
		// remains undeployed, with no stack trace.
		// Similar to:
		// https://issues.liferay.com/browse/LPS-72507
		//     ("Stop using HttpUtil in modules, use service reference directly
		//     instead")
		// https://github.com/liferay/liferay-portal/commit/
		//     cdadae7#diff-874fcf4d20d5892447db4ce340c2bc49
		// https://github.com/liferay/liferay-portal/commit/d1da078

		Localization localization = LocalizationUtil.getLocalization();

		String localizedFieldName = localization.getLocalizedName(
			searchContext.getLanguageId(), Field.SPELL_CHECK_WORD);

		Tokenizer tokenizer = getTokenizer();

		List<String> keywords = tokenizer.tokenize(
			localizedFieldName, searchContext.getKeywords(),
			searchContext.getLanguageId());

		return collator.collate(suggestions, keywords);
	}

	@Override
	public SuggesterResults suggest(
		SearchContext searchContext, Suggester suggester) {

		return new SuggesterResults();
	}

	protected Tokenizer getTokenizer() {
		if (tokenizer != null) {
			return tokenizer;
		}

		return _defaultTokenizer;
	}

	@Reference
	protected Collator collator;

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected volatile Tokenizer tokenizer;

	private static final Tokenizer _defaultTokenizer = new SimpleTokenizer();

}