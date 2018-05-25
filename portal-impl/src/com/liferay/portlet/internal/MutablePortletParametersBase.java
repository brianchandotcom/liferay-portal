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

package com.liferay.portlet.internal;

import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.Map;
import java.util.Set;

import javax.portlet.MutablePortletParameters;
import javax.portlet.PortletParameters;

/**
 * @author Neil Griffin
 */
public abstract class MutablePortletParametersBase
	extends PortletParametersBase implements LiferayMutablePortletParameters {

	public MutablePortletParametersBase(Map<String, String[]> parameterMap) {
		super(parameterMap);
	}

	@Override
	public MutablePortletParameters add(PortletParameters portletParameters) {
		MutablePortletParameters oldMutablePortletParameters = clone();
		Set<String> newParameterNames = portletParameters.getNames();
		Map<String, String[]> parameterMap = getParameterMap();

		for (String newParameterName : newParameterNames) {
			String[] values = portletParameters.getValues(newParameterName);

			String[] copiedValues = values.clone();

			parameterMap.put(newParameterName, copiedValues);
		}

		_mutated = true;

		return oldMutablePortletParameters;
	}

	@Override
	public void clear() {
		Map<String, String[]> parameterMap = getParameterMap();

		parameterMap.clear();

		_mutated = true;
	}

	@Override
	public boolean isMutated() {
		return _mutated;
	}

	@Override
	public boolean removeParameter(String name) {
		if (name == null) {
			throw new IllegalArgumentException();
		}

		Map<String, String[]> parameterMap = getParameterMap();

		if (parameterMap.containsKey(name)) {
			parameterMap.remove(name);

			_mutated = true;

			return true;
		}

		return false;
	}

	@Override
	public MutablePortletParameters set(PortletParameters portletParameters) {
		MutablePortletParameters oldMutablePortletParameters = clone();

		Set<String> oldParameterNames = oldMutablePortletParameters.getNames();

		Set<String> newParameterNames = portletParameters.getNames();
		Map<String, String[]> parameterMap = getParameterMap();

		for (String oldParameterName : oldParameterNames) {
			if (!newParameterNames.contains(oldParameterName)) {
				parameterMap.remove(oldParameterName);

				_mutated = true;
			}
		}

		for (String newParameterName : newParameterNames) {
			String[] values = portletParameters.getValues(newParameterName);

			String[] copiedValues = values.clone();

			parameterMap.put(newParameterName, copiedValues);

			_mutated = true;
		}

		return oldMutablePortletParameters;
	}

	@Override
	public String setValue(String name, String value) {
		String[] oldValues = setValues(name, new String[] {value});

		_mutated = true;

		if ((oldValues != null) && (oldValues.length > 0)) {
			return oldValues[0];
		}

		return null;
	}

	@Override
	public String setValue(String name, String value, boolean append) {
		String[] oldValues;

		if (value == null) {
			oldValues = setValues(name, null, append);
		}
		else {
			oldValues = setValues(name, new String[] {value}, append);
		}

		_mutated = true;

		if ((oldValues != null) && (oldValues.length > 0)) {
			return oldValues[0];
		}

		return null;
	}

	@Override
	public String[] setValues(String name, String... values) {
		if (name == null) {
			throw new IllegalArgumentException();
		}

		if (values == null) {
			values = new String[0];
		}

		_mutated = true;

		Map<String, String[]> parameterMap = getParameterMap();

		return parameterMap.put(name, values);
	}

	@Override
	public String[] setValues(String name, String[] values, boolean append) {
		if (name == null) {
			throw new IllegalArgumentException();
		}

		String[] oldValues = getValues(name);

		if (values == null) {
			removeParameter(name);
		}
		else {
			Map<String, String[]> parameterMap = getParameterMap();

			if (append && (oldValues != null)) {
				String[] newValues = ArrayUtil.append(oldValues, values);

				parameterMap.put(name, newValues);
			}
			else {
				parameterMap.put(name, values);
			}
		}

		_mutated = true;

		return oldValues;
	}

	private boolean _mutated;

}