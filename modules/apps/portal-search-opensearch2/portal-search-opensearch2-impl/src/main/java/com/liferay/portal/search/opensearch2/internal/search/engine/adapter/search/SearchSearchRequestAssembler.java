/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.search.engine.adapter.search;

import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;

import org.opensearch.client.opensearch.core.SearchRequest;

/**
 * @author Michael C. Han
 * @author Petteri Karttunen
 */
public interface SearchSearchRequestAssembler {

	public void assemble(
		SearchRequest.Builder searchRequestBuilder,
		SearchSearchRequest searchSearchRequest);

}