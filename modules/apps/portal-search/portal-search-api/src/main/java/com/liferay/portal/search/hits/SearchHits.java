/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.hits;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 * @author André de Oliveira
 */
@ProviderType
public interface SearchHits {

	public float getMaxScore();

	public List<SearchHit> getSearchHits();

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             com.liferay.portal.search.searcher.SearchResponse#getSearchTimeValue()}
	 */
	@Deprecated
	public long getSearchTime();

	public long getTotalHits();

}