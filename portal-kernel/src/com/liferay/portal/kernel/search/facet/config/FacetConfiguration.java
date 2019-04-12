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

package com.liferay.portal.kernel.search.facet.config;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;

/**
 * @author Raymond Augé
 */
public class FacetConfiguration {

	public String getClassName() {
		return _className;
	}

	public JSONObject getData() {
		if (_dataJSONObject == null) {
			_dataJSONObject = JSONFactoryUtil.createJSONObject();
		}

		return _dataJSONObject;
	}

	public String getFieldName() {
		return _fieldName;
	}

	public String getLabel() {
		return _label;
	}

	public String getOrder() {
		if (_order == null) {
			return "OrderHitsDesc";
		}

		return _order;
	}

	public double getWeight() {
		return _weight;
	}

	public boolean isStatic() {
		return _static;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setDataJSONObject(JSONObject dataJSONObject) {
		_dataJSONObject = dataJSONObject;
	}

	public void setFieldName(String fieldName) {
		_fieldName = fieldName;
	}

	public void setLabel(String label) {
		_label = label;
	}

	public void setOrder(String order) {
		_order = order;
	}

	public void setStatic(boolean isStatic) {
		_static = isStatic;
	}

	public void setWeight(double weight) {
		_weight = weight;
	}

	private String _className;
	private JSONObject _dataJSONObject;
	private String _fieldName;
	private String _label;
	private String _order;
	private boolean _static;
	private double _weight;

}