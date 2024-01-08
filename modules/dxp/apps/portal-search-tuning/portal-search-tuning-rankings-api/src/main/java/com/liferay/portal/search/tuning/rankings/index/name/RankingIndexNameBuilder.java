/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.index.name;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Adam Brandizzi
 */
@ProviderType
public interface RankingIndexNameBuilder {

	public RankingIndexName getRankingIndexName(long companyId);

}