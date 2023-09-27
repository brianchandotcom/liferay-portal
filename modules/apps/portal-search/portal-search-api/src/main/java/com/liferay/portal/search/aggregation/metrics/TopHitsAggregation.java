/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.aggregation.metrics;

import com.liferay.portal.search.aggregation.Aggregation;
import com.liferay.portal.search.highlight.Highlight;
import com.liferay.portal.search.script.ScriptField;
import com.liferay.portal.search.sort.Sort;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 * @author André de Oliveira
 */
@ProviderType
public interface TopHitsAggregation extends Aggregation {

	public void addScriptFields(ScriptField... scriptFields);

	public void addSelectedFields(String... fields);

	public void addSortFields(Sort... sortFields);

	public Boolean getExplain();

	public Boolean getFetchSource();

	public String[] getFetchSourceExclude();

	public String[] getFetchSourceInclude();

	public Integer getFrom();

	public Highlight getHighlight();

	public List<ScriptField> getScriptFields();

	public List<String> getSelectedFields();

	public Integer getSize();

	public List<Sort> getSortFields();

	public Boolean getTrackScores();

	public Boolean getVersion();

	public void setExplain(Boolean explain);

	public void setFetchSource(Boolean fetchSource);

	public void setFetchSourceExclude(String[] fetchSourceExclude);

	public void setFetchSourceInclude(String[] fetchSourceInclude);

	public void setFetchSourceIncludeExclude(
		String[] fetchSourceInclude, String[] fetchSourceExclude);

	public void setFrom(Integer from);

	public void setHighlight(Highlight highlight);

	public void setScriptFields(List<ScriptField> scriptFields);

	public void setSelectedFields(List<String> selectedFields);

	public void setSize(Integer size);

	public void setTrackScores(Boolean trackScores);

	public void setVersion(Boolean version);

}