/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.facet;

import co.elastic.clients.elasticsearch.core.SearchRequest;

import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.facet.Facet;

import java.util.Map;

/**
 * @author Michael C. Han
 */
public interface FacetTranslator {

	public void translate(
		boolean basicFacetSelection, Map<String, Facet> facetsMap, Query query,
		SearchRequest.Builder searchRequestBuilder);

}