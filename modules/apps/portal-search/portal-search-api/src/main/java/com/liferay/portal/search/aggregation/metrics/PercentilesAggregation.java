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

import com.liferay.portal.search.aggregation.FieldAggregation;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 */
@ProviderType
public interface PercentilesAggregation extends FieldAggregation {

	public Integer getCompression();

	public Integer getHdrSignificantValueDigits();

	public Boolean getKeyed();

	public PercentilesMethod getPercentilesMethod();

	public double[] getPercents();

	public void setCompression(Integer compression);

	public void setHdrSignificantValueDigits(Integer hdrSignificantValueDigits);

	public void setKeyed(Boolean keyed);

	public void setPercentilesMethod(PercentilesMethod percentilesMethod);

	public void setPercents(double... percents);

}