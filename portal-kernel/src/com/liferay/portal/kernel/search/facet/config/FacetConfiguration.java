/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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