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
 */
@ProviderType
public interface DateHistogramAggregation extends FieldAggregation {

	public void addOrders(Order... orders);

	public String getDateHistogramInterval();

	public Long getInterval();

	public Boolean getKeyed();

	public Long getMaxBound();

	public Long getMinBound();

	public Long getMinDocCount();

	public Long getOffset();

	public List<Order> getOrders();

	public void setBounds(Long minBound, Long maxBound);

	public void setDateHistogramInterval(String dateHistogramInterval);

	public void setInterval(Long interval);

	public void setKeyed(Boolean keyed);

	public void setMinDocCount(Long minDocCount);

	public void setOffset(Long offset);

}