/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.blueprint.search.request.body.contributor;

import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.search.experiences.internal.blueprint.highlight.HighlightConverter;
import com.liferay.search.experiences.internal.blueprint.parameter.SXPParameterData;
import com.liferay.search.experiences.rest.dto.v1_0.Configuration;
import com.liferay.search.experiences.rest.dto.v1_0.HighlightConfiguration;

/**
 * @author Petteri Karttunen
 */
public class HighlightSXPSearchRequestBodyContributor
	implements SXPSearchRequestBodyContributor {

	public HighlightSXPSearchRequestBodyContributor(
		HighlightConverter highlightConverter) {

		_highlightConverter = highlightConverter;
	}

	@Override
	public void contribute(
		Configuration configuration, SearchRequestBuilder searchRequestBuilder,
		SXPParameterData sxpParameterData) {

		HighlightConfiguration highlightConfiguration =
			configuration.getHighlightConfiguration();

		if ((highlightConfiguration == null) ||
			(highlightConfiguration.getFields() == null)) {

			return;
		}

		searchRequestBuilder.highlight(
			_highlightConverter.toHighlight(highlightConfiguration));
	}

	@Override
	public String getName() {
		return "highlightConfiguration";
	}

	private final HighlightConverter _highlightConverter;

}