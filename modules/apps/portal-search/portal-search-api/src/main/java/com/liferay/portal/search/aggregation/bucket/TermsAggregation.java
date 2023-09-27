/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.aggregation.bucket;

import com.liferay.portal.search.aggregation.FieldAggregation;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 * @author André de Oliveira
 */
@ProviderType
public interface TermsAggregation extends FieldAggregation {

	public void addOrders(Order... orders);

	public CollectionMode getCollectionMode();

	public String getExecutionHint();

	public IncludeExcludeClause getIncludeExcludeClause();

	public Integer getMinDocCount();

	public List<Order> getOrders();

	public Integer getShardMinDocCount();

	public Integer getShardSize();

	public Boolean getShowTermDocCountError();

	public Integer getSize();

	public void setCollectionMode(CollectionMode collectionMode);

	public void setExecutionHint(String executionHint);

	public void setIncludeExcludeClause(
		IncludeExcludeClause includeExcludeClause);

	public void setMinDocCount(Integer minDocCount);

	public void setShardMinDocCount(Integer shardMinDocCount);

	public void setShardSize(Integer shardSize);

	public void setShowTermDocCountError(Boolean showTermDocCountError);

	public void setSize(Integer size);

}