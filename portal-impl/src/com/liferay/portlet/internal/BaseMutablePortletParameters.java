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
import java.util.function.Function;

import javax.portlet.MutablePortletParameters;
import javax.portlet.PortletParameters;

/**
 * @author Neil Griffin
 */
public abstract class BaseMutablePortletParameters
	<T extends MutablePortletParameters>
		extends BasePortletParametersImpl<T>
		implements LiferayMutablePortletParameters {

	public BaseMutablePortletParameters(
		Map<String, String[]> parameterMap,
		Function<Map<String, String[]>, T> mutablePortletParametersCreator) {

		super(parameterMap, null, mutablePortletParametersCreator);
	}

	@Override
	public MutablePortletParameters add(PortletParameters portletParameters) {
		MutablePortletParameters oldMutablePortletParameters = clone();

		Map<String, String[]> parameterMap = getParameterMap();

		if (portletParameters instanceof BasePortletParametersImpl) {
			BasePortletParametersImpl<?> basePortletParametersImpl =
				(BasePortletParametersImpl<?>)portletParameters;

			Map<String, String[]> liferayPortletParametersMap =
				basePortletParametersImpl.getParameterMap();

			for (Map.Entry<String, String[]> entry :
					liferayPortletParametersMap.entrySet()) {

				String[] values = entry.getValue();

				String[] copiedValues = values.clone();

				parameterMap.put(entry.getKey(), copiedValues);
			}
		}
		else {
			for (String newParameterName : portletParameters.getNames()) {
				String[] values = portletParameters.getValues(newParameterName);

				String[] copiedValues = values.clone();

				parameterMap.put(newParameterName, copiedValues);
			}
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

		if (portletParameters instanceof BasePortletParametersImpl) {
			BasePortletParametersImpl<?> basePortletParametersImpl =
				(BasePortletParametersImpl<?>)portletParameters;

			Map<String, String[]> liferayPortletParametersMap =
				basePortletParametersImpl.getParameterMap();

			if (oldParameterNames.isEmpty() &&
				liferayPortletParametersMap.isEmpty()) {

				return oldMutablePortletParameters;
			}

			Map<String, String[]> parameterMap = getParameterMap();

			parameterMap.clear();

			for (Map.Entry<String, String[]> entry :
					liferayPortletParametersMap.entrySet()) {

				String[] values = entry.getValue();

				String[] copiedValues = values.clone();

				parameterMap.put(entry.getKey(), copiedValues);
			}
		}
		else {
			Set<String> newParameterNames = portletParameters.getNames();

			if (oldParameterNames.isEmpty() && newParameterNames.isEmpty()) {
				return oldMutablePortletParameters;
			}

			Map<String, String[]> parameterMap = getParameterMap();

			parameterMap.clear();

			for (String newParameterName : newParameterNames) {
				String[] values = portletParameters.getValues(newParameterName);

				String[] copiedValues = values.clone();

				parameterMap.put(newParameterName, copiedValues);
			}
		}

		_mutated = true;

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
		if (name == null) {
			throw new IllegalArgumentException();
		}

		String[] oldValues = getValues(name);

		if (value == null) {
			removeParameter(name);
		}
		else {
			Map<String, String[]> parameterMap = getParameterMap();

			if (append && (oldValues != null)) {
				String[] newValues = ArrayUtil.append(oldValues, value);

				parameterMap.put(name, newValues);
			}
			else {
				parameterMap.put(name, new String[] {value});
			}
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