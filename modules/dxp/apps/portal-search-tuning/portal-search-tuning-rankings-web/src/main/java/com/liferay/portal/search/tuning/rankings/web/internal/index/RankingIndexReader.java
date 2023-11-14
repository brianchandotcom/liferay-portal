/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.index;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.search.tuning.rankings.web.internal.index.name.RankingIndexName;
import com.liferay.search.experiences.model.SXPBlueprint;

import java.util.List;

/**
 * @author André de Oliveira
 */
public interface RankingIndexReader {

	public List<Ranking> fetch(Group group) throws PortalException;

	public Ranking fetch(String id, RankingIndexName rankingIndexName);

	public List<Ranking> fetch(
		String groupExternalReferenceCode, String queryString,
		RankingIndexName rankingIndexName,
		String sxpBlueprintExternalReferenceCode);

	public List<Ranking> fetch(SXPBlueprint sxpBlueprint)
		throws PortalException;

	public boolean isExists(RankingIndexName rankingIndexName);

}