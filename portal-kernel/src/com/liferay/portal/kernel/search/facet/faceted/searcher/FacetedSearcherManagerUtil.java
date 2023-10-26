/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search.facet.faceted.searcher;

import com.liferay.portal.kernel.module.service.Snapshot;

/**
 * @author André de Oliveira
 */
public class FacetedSearcherManagerUtil {

	public static FacetedSearcher createFacetedSearcher() {
		FacetedSearcherManager facetedSearcherManager =
			_facetedSearcherManagerSnapshot.get();

		return facetedSearcherManager.createFacetedSearcher();
	}

	private static final Snapshot<FacetedSearcherManager>
		_facetedSearcherManagerSnapshot = new Snapshot<>(
			FacetedSearcherManagerUtil.class, FacetedSearcherManager.class);

}