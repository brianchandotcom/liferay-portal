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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.search.aggregation.Aggregation;
import com.liferay.portal.search.aggregation.ValueType;
import com.liferay.portal.search.script.Script;

/**
 * @author Michael C. Han
 */
@ProviderType
public interface WeightedAvgAggregation extends Aggregation {

	public String getFormat();

	public String getValueField();

	public Object getValueMissing();

	public Script getValueScript();

	public ValueType getValueType();

	public String getWeightField();

	public Object getWeightMissing();

	public Script getWeightScript();

	public void setFormat(String format);

	public void setValueMissing(Object valueMissing);

	public void setValueScript(Script valueScript);

	public void setValueType(ValueType valueType);

	public void setWeightMissing(Object weightMissing);

	public void setWeightScript(Script weightScript);

}