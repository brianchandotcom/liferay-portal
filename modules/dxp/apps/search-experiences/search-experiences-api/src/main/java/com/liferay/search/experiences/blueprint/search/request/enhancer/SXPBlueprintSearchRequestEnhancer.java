/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.blueprint.search.request.enhancer;

import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.search.experiences.model.SXPBlueprint;

/**
 * @author André de Oliveira
 */
public interface SXPBlueprintSearchRequestEnhancer {

	public void enhance(
		SearchRequestBuilder searchRequestBuilder, String sxpBlueprintJSON);

	public void enhance(
		SearchRequestBuilder searchRequestBuilder, SXPBlueprint sxpBlueprint);

}