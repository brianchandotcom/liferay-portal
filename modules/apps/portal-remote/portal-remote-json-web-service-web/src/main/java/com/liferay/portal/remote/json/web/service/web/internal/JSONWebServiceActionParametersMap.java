/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.remote.json.web.service.web.internal;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Igor Spasic
 */
public class JSONWebServiceActionParametersMap extends HashMap<String, Object> {

	public List<Entry<String, Object>> getInnerParameters(String baseName) {
		if (_innerParameters == null) {
			return null;
		}

		return _innerParameters.get(baseName);
	}

	public String getParameterTypeName(String name) {
		if (_parameterTypes == null) {
			return null;
		}

		return _parameterTypes.get(name);
	}

	public boolean includeDefaultParameters() {
		if (_defaultParameters == null) {
			return false;
		}

		for (DefaultParameter defaultParameter : _defaultParameters) {
			put(defaultParameter.getName(), defaultParameter.getValue());
		}

		return true;
	}

	@Override
	public Object put(String key, Object value) {
		int pos = key.indexOf(CharPool.COLON);

		if (key.startsWith(StringPool.DASH)) {
			key = key.substring(1);

			value = null;
		}
		else if (key.startsWith(StringPool.PLUS)) {
			key = key.substring(1);

			String typeName = null;

			if (pos != -1) {
				typeName = key.substring(pos);

				key = key.substring(0, pos - 1);
			}
			else {
				if (value != null) {
					typeName = value.toString();

					value = Void.TYPE;
				}
			}

			if (typeName != null) {
				if (_parameterTypes == null) {
					_parameterTypes = new HashMap<>();
				}

				_parameterTypes.put(key, typeName);
			}

			if (Validator.isNull(GetterUtil.getString(value))) {
				value = Void.TYPE;
			}
		}
		else if (pos != -1) {
			String typeName = key.substring(pos + 1);

			key = key.substring(0, pos);

			if (_parameterTypes == null) {
				_parameterTypes = new HashMap<>();
			}

			_parameterTypes.put(key, typeName);

			if (Validator.isNull(GetterUtil.getString(value))) {
				value = Void.TYPE;
			}
		}

		pos = key.indexOf(CharPool.PERIOD);

		if (pos != -1) {
			String baseName = key.substring(0, pos);

			String innerName = key.substring(pos + 1);

			if (_innerParameters == null) {
				_innerParameters = new HashMap<>();
			}

			List<Entry<String, Object>> values = _innerParameters.get(baseName);

			if (values == null) {
				values = new ArrayList<>();

				_innerParameters.put(baseName, values);
			}

			values.add(new SimpleImmutableEntry<>(innerName, value));

			return value;
		}

		return super.put(key, value);
	}

	public void putDefaultParameter(String key, Object value) {
		if (_defaultParameters == null) {
			_defaultParameters = new ArrayList<>();
		}

		_defaultParameters.add(new DefaultParameter(key, value));
	}

	public static class DefaultParameter {

		public DefaultParameter(String name, Object value) {
			_name = name;
			_value = value;
		}

		public String getName() {
			return _name;
		}

		public Object getValue() {
			return _value;
		}

		private final String _name;
		private final Object _value;

	}

	private List<DefaultParameter> _defaultParameters;
	private Map<String, List<Entry<String, Object>>> _innerParameters;
	private Map<String, String> _parameterTypes;

}