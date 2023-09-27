/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.suggestions;

import com.liferay.portal.search.suggestions.SuggestionsContributorResultsBuilder;
import com.liferay.portal.search.suggestions.SuggestionsContributorResultsBuilderFactory;

import org.osgi.service.component.annotations.Component;

/**
 * @author Petteri Karttunen
 */
@Component(service = SuggestionsContributorResultsBuilderFactory.class)
public class SuggestionsContributorResultsBuilderFactoryImpl
	implements SuggestionsContributorResultsBuilderFactory {

	@Override
	public SuggestionsContributorResultsBuilder builder() {
		return new SuggestionsContributorResultsBuilderImpl();
	}

}