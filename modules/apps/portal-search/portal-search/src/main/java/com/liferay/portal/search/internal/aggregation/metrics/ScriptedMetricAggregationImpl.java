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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.search.aggregation.AggregationVisitor;
import com.liferay.portal.search.aggregation.metrics.ScriptedMetricAggregation;
import com.liferay.portal.search.internal.aggregation.BaseAggregation;
import com.liferay.portal.search.script.Script;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Michael C. Han
 */
@ProviderType
public class ScriptedMetricAggregationImpl
	extends BaseAggregation implements ScriptedMetricAggregation {

	public ScriptedMetricAggregationImpl(String name) {
		super(name);
	}

	@Override
	public <T> T accept(AggregationVisitor<T> aggregationVisitor) {
		return aggregationVisitor.visit(this);
	}

	public void clearParameter(String paramName) {
		_parameters.remove(paramName);
	}

	public void clearParameters() {
		_parameters.clear();
	}

	@Override
	public Script getCombineScript() {
		return _combineScript;
	}

	@Override
	public Script getInitScript() {
		return _initScript;
	}

	@Override
	public Script getMapScript() {
		return _mapScript;
	}

	@Override
	public Map<String, Object> getParameters() {
		return Collections.unmodifiableMap(_parameters);
	}

	@Override
	public Script getReduceScript() {
		return _reduceScript;
	}

	public void putParameter(String paramName, Object paramValue) {
		_parameters.put(paramName, paramValue);
	}

	public void setCombineScript(Script combineScript) {
		_combineScript = combineScript;
	}

	public void setInitScript(Script initScript) {
		_initScript = initScript;
	}

	public void setMapScript(Script mapScript) {
		_mapScript = mapScript;
	}

	public void setParameters(Map<String, Object> parameters) {
		_parameters = parameters;
	}

	public void setReduceScript(Script reduceScript) {
		_reduceScript = reduceScript;
	}

	private Script _combineScript;
	private Script _initScript;
	private Script _mapScript;
	private Map<String, Object> _parameters = new HashMap<>();
	private Script _reduceScript;

}