/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.engine.adapter.search;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 */
@ProviderType
public interface SearchRequestExecutor {

	public ClearScrollResponse executeSearchRequest(
		ClearScrollRequest clearScrollRequest);

	public ClosePointInTimeResponse executeSearchRequest(
		ClosePointInTimeRequest closePointInTimeRequest);

	public CountSearchResponse executeSearchRequest(
		CountSearchRequest countSearchRequest);

	public MultisearchSearchResponse executeSearchRequest(
		MultisearchSearchRequest multisearchSearchRequest);

	public OpenPointInTimeResponse executeSearchRequest(
		OpenPointInTimeRequest openPointInTimeRequest);

	public SearchSearchResponse executeSearchRequest(
		SearchSearchRequest searchSearchRequest);

	public SuggestSearchResponse executeSearchRequest(
		SuggestSearchRequest suggestSearchRequest);

}