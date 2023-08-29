/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.index;

import com.liferay.portal.search.tuning.rankings.web.internal.index.name.RankingIndexName;

import java.util.Collection;
import java.util.List;

/**
 * @author André de Oliveira
 */
public interface DuplicateQueryStringsDetector {

	public Criteria.Builder builder();

	public List<String> detect(Criteria criteria);

	public interface Criteria {

		public String getGroupExternalReferenceCode();

		public String getIndex();

		public Collection<String> getQueryStrings();

		public RankingIndexName getRankingIndexName();

		public String getSXPBlueprintExternalReferenceCode();

		public String getUnlessRankingDocumentId();

		public interface Builder {

			public Criteria build();

			public Builder groupExternalReferenceCode(
				String groupExternalReferenceCode);

			public Builder index(String index);

			public Builder queryStrings(Collection<String> queryStrings);

			public Builder rankingIndexName(RankingIndexName rankingIndexName);

			public Builder sxpBlueprintExternalReferenceCode(
				String sxpBlueprintExternalReferenceCode);

			public Builder unlessRankingDocumentId(
				String unlessRankingDocumentId);

		}

	}

}