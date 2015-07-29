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

package com.liferay.portal.kernel.cache.configuration;

import com.liferay.portal.kernel.cache.CallbackFactory;
import com.liferay.portal.kernel.util.HashUtil;

import java.util.Properties;

/**
 * @author Tina Tian
 */
public class CallbackConfiguration {

	public CallbackConfiguration(
		CallbackFactory<?> callbackFactory, Properties properties) {

		if (callbackFactory == null) {
			throw new NullPointerException("Callback factory is null");
		}

		if (properties == null) {
			throw new NullPointerException("Properties is null");
		}

		_callbackFactory = callbackFactory;
		_properties = (Properties)properties.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CallbackConfiguration)) {
			return false;
		}

		CallbackConfiguration callbackConfiguration =
			(CallbackConfiguration)object;

		if ((_callbackFactory == callbackConfiguration._callbackFactory) &&
			_properties.equals(callbackConfiguration._properties)) {

			return true;
		}

		return false;
	}

	public CallbackFactory<?> getCallbackFactory() {
		return _callbackFactory;
	}

	public Properties getProperties() {
		return (Properties)_properties.clone();
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _callbackFactory);

		return HashUtil.hash(hash, _properties);
	}

	private final CallbackFactory<?> _callbackFactory;
	private final Properties _properties;

}