/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.storage;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.search.tuning.rankings.index.Ranking;
import com.liferay.portal.search.tuning.rankings.index.name.RankingIndexName;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Almir Ferreira
 */
@ProviderType
public interface RankingStorageAdapter {

	public String create(Ranking ranking, RankingIndexName rankingIndexName);

	public void delete(
			String rankingDocumentId, RankingIndexName rankingIndexName)
		throws PortalException;

	public void update(Ranking ranking, RankingIndexName rankingIndexName)
		throws PortalException;

}