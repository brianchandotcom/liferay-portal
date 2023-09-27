/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.suggestions;

import com.liferay.portal.search.suggestions.Suggestion;
import com.liferay.portal.search.suggestions.SuggestionBuilder;
import com.liferay.portal.search.suggestions.SuggestionBuilderFactory;

import org.osgi.service.component.annotations.Component;

/**
 * @author Petteri Karttunen
 */
@Component(service = SuggestionBuilderFactory.class)
public class SuggestionBuilderFactoryImpl implements SuggestionBuilderFactory {

	@Override
	public SuggestionBuilder builder() {
		return new SuggestionBuilderImpl();
	}

	@Override
	public SuggestionBuilder builder(Suggestion suggestion) {
		return new SuggestionBuilderImpl(suggestion);
	}

}