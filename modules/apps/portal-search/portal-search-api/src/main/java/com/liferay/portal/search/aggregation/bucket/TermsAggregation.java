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