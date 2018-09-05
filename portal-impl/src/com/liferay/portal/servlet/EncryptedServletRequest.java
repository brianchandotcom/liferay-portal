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

package com.liferay.portal.servlet;

import com.liferay.petra.encryptor.Encryptor;
import com.liferay.petra.encryptor.EncryptorException;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Validator;

import java.security.Key;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @author Brian Wing Shun Chan
 */
public class EncryptedServletRequest extends HttpServletRequestWrapper {

	public EncryptedServletRequest(HttpServletRequest request, Key key) {
		super(request);

		_params = new HashMap<>();
		_key = key;

		Map<String, String[]> parameters = request.getParameterMap();

		for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
			String name = entry.getKey();
			String[] values = entry.getValue();

			for (int i = 0; i < values.length; i++) {
				if (Validator.isNotNull(values[i])) {
					try {
						values[i] = Encryptor.decrypt(_key, values[i]);
					}
					catch (EncryptorException ee) {
						values[i] = StringPool.BLANK;
					}
				}
			}

			_params.put(name, values);
		}
	}

	@Override
	public String getParameter(String name) {
		String[] values = _params.get(name);

		if (ArrayUtil.isNotEmpty(values)) {
			return values[0];
		}

		return null;
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		return Collections.unmodifiableMap(_params);
	}

	@Override
	public String[] getParameterValues(String name) {
		return _params.get(name);
	}

	private final Key _key;
	private final Map<String, String[]> _params;

}