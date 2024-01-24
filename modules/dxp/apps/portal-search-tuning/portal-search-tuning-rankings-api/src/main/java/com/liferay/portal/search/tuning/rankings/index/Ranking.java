/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.index;

import java.util.Collection;
import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Bryan Engler
 */
@ProviderType
public interface Ranking {

	public List<String> getAliases();

	public String getGroupExternalReferenceCode();

	public List<String> getHiddenDocumentIds();

	public String getIndexName();

	public String getName();

	public String getNameForDisplay();

	public List<Ranking.Pin> getPins();

	public String getQueryString();

	public Collection<String> getQueryStrings();

	public String getRankingDocumentId();

	public String getStatus();

	public String getSXPBlueprintExternalReferenceCode();

	public boolean isPinned(String documentId);
	
	public interface Pin {

		public String getDocumentId();
	
		public int getPosition();
		
		public interface Builder {
			
			public Pin build();

			public Builder documentId(String documentId);

			public Builder position(int position);
			
		}
	
	}
	
	public interface RankingBuilder {

		public RankingBuilder aliases(List<String> aliases);
	
		public Ranking build();
	
		public RankingBuilder groupExternalReferenceCode(
			String groupExternalReferenceCode);
	
		public RankingBuilder hiddenDocumentIds(
			List<String> hiddenDocumentIds);
	
		public RankingBuilder indexName(String indexName);
	
		public RankingBuilder name(String name);
	
		public RankingBuilder pins(List<Ranking.Pin> pins);
	
		public RankingBuilder queryString(String queryString);
	
		public RankingBuilder rankingDocumentId(String rankingDocumentId);
	
		public RankingBuilder status(String status);
	
		public RankingBuilder sxpBlueprintExternalReferenceCode(
			String sxpBlueprintExternalReferenceCode);

	}

}
