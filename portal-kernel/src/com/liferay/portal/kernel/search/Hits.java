/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public interface Hits extends Serializable {

	public void addGroupedHits(String groupValue, Hits hits);

	public void addStatsResults(StatsResults statsResults);

	public void copy(Hits hits);

	public Document doc(int n);

	public String getCollatedSpellCheckResult();

	public Document[] getDocs();

	public Map<String, Hits> getGroupedHits();

	public int getLength();

	public Query getQuery();

	public String[] getQuerySuggestions();

	public String[] getQueryTerms();

	public float[] getScores();

	public float getSearchTime();

	public String[] getSnippets();

	public Map<String, List<String>> getSpellCheckResults();

	public long getStart();

	public Map<String, StatsResults> getStatsResults();

	public boolean hasGroupedHits();

	public float score(int n);

	public void setCollatedSpellCheckResult(String collatedSpellCheckResult);

	public void setDocs(Document[] docs);

	public void setLength(int length);

	public void setQuery(Query query);

	public void setQuerySuggestions(String[] querySuggestions);

	public void setQueryTerms(String[] queryTerms);

	public void setScores(float[] scores);

	public void setSearchTime(float time);

	public void setSnippets(String[] snippets);

	public void setSpellCheckResults(
		Map<String, List<String>> spellCheckResults);

	public void setStart(long start);

	public String snippet(int n);

	public List<Document> toList();

}