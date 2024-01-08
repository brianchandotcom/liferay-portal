/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.index;

import com.liferay.portal.search.tuning.rankings.index.name.RankingIndexName;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author André de Oliveira
 */
@ProviderType
public interface RankingIndexReader {

	public List<Ranking> fetch(
		boolean excludeInactiveStatus, String groupExternalReferenceCode,
		String queryString, RankingIndexName rankingIndexName,
		String sxpBlueprintExternalReferenceCode);

	public Ranking fetch(String id, RankingIndexName rankingIndexName);

	public List<Ranking> fetch(
		String groupExternalReferenceCode, String queryString,
		RankingIndexName rankingIndexName,
		String sxpBlueprintExternalReferenceCode);

	public List<Ranking> fetchByGroupExternalReferenceCode(
		String groupExternalReferenceCode, RankingIndexName rankingIndexName);

	public List<Ranking> fetchBySXPBlueprintExternalReferenceCode(
		RankingIndexName rankingIndexName,
		String sxpBlueprintExternalReferenceCode);

	public boolean isExists(RankingIndexName rankingIndexName);

}