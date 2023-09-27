/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.aggregation.bucket;

import com.liferay.portal.search.aggregation.FieldAggregation;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.significance.SignificanceHeuristic;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 */
@ProviderType
public interface SignificantTextAggregation extends FieldAggregation {

	public Query getBackgroundFilterQuery();

	public BucketCountThresholds getBucketCountThresholds();

	public String getExecutionHint();

	public Boolean getFilterDuplicateText();

	public IncludeExcludeClause getIncludeExcludeClause();

	public Long getMinDocCount();

	public Long getShardMinDocCount();

	public Integer getShardSize();

	public SignificanceHeuristic getSignificanceHeuristic();

	public Integer getSize();

	public List<String> getSourceFields();

	public void setBackgroundFilterQuery(Query backgroundFilterQuery);

	public void setBucketCountThresholds(
		BucketCountThresholds bucketCountThresholds);

	public void setExecutionHint(String executionHint);

	public void setFilterDuplicateText(Boolean filterDuplicateText);

	public void setIncludeExcludeClause(
		IncludeExcludeClause includeExcludeClause);

	public void setMinDocCount(Long minDocCount);

	public void setShardMinDocCount(Long shardMinDocCount);

	public void setShardSize(Integer shardSize);

	public void setSignificanceHeuristic(
		SignificanceHeuristic significanceHeuristic);

	public void setSize(Integer size);

}