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

package com.liferay.portal.search.internal.aggregation.metrics;

import com.liferay.portal.search.aggregation.AggregationVisitor;
import com.liferay.portal.search.aggregation.ValueType;
import com.liferay.portal.search.aggregation.metrics.WeightedAvgAggregation;
import com.liferay.portal.search.internal.aggregation.BaseAggregation;
import com.liferay.portal.search.script.Script;

/**
 * @author Michael C. Han
 */
public class WeightedAvgAggregationImpl
	extends BaseAggregation implements WeightedAvgAggregation {

	public WeightedAvgAggregationImpl(
		String name, String valueField, String weightField) {

		super(name);

		_valueField = valueField;
		_weightField = weightField;
	}

	@Override
	public <T> T accept(AggregationVisitor<T> aggregationVisitor) {
		return aggregationVisitor.visit(this);
	}

	@Override
	public String getFormat() {
		return _format;
	}

	@Override
	public String getValueField() {
		return _valueField;
	}

	@Override
	public Object getValueMissing() {
		return _valueMissing;
	}

	@Override
	public Script getValueScript() {
		return _valueScript;
	}

	@Override
	public ValueType getValueType() {
		return _valueType;
	}

	@Override
	public String getWeightField() {
		return _weightField;
	}

	@Override
	public Object getWeightMissing() {
		return _weightMissing;
	}

	@Override
	public Script getWeightScript() {
		return _weightScript;
	}

	@Override
	public void setFormat(String format) {
		_format = format;
	}

	@Override
	public void setValueMissing(Object valueMissing) {
		_valueMissing = valueMissing;
	}

	@Override
	public void setValueScript(Script valueScript) {
		_valueScript = valueScript;
	}

	@Override
	public void setValueType(ValueType valueType) {
		_valueType = valueType;
	}

	@Override
	public void setWeightMissing(Object weightMissing) {
		_weightMissing = weightMissing;
	}

	@Override
	public void setWeightScript(Script weightScript) {
		_weightScript = weightScript;
	}

	private String _format;
	private final String _valueField;
	private Object _valueMissing;
	private Script _valueScript;
	private ValueType _valueType;
	private final String _weightField;
	private Object _weightMissing;
	private Script _weightScript;

}