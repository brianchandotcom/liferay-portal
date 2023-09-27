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
 */
@ProviderType
public interface HistogramAggregation extends FieldAggregation {

	public void addOrders(Order... orders);

	public Double getInterval();

	public Boolean getKeyed();

	public Double getMaxBound();

	public Double getMinBound();

	public Long getMinDocCount();

	public Double getOffset();

	public List<Order> getOrders();

	public void setBounds(Double minBound, Double maxBound);

	public void setInterval(Double interval);

	public void setKeyed(Boolean keyed);

	public void setMinDocCount(Long minDocCount);

	public void setOffset(Double offset);

}