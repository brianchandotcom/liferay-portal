/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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