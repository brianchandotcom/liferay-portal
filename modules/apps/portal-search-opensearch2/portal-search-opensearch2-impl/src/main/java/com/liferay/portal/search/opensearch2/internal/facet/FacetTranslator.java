/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.facet;

import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.facet.Facet;

import java.util.Map;

import org.opensearch.client.opensearch.core.SearchRequest;

/**
 * @author Michael C. Han
 * @author Petteri Karttunen
 */
public interface FacetTranslator {

	public void translate(
		boolean basicFacetSelection, Map<String, Facet> facetsMap, Query query,
		SearchRequest.Builder searchRequestBuilder);

}