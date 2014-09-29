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
		Class<? extends CallbackFactory> callbackFactoryClass,
		Properties properties) {

		if (callbackFactoryClass == null) {
			throw new NullPointerException("Callback Factory class is null");
		}

		if (properties == null) {
			throw new NullPointerException("Properties is null");
		}

		_callbackFactoryClass = callbackFactoryClass;
		_properties = properties;
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

		return _callbackFactoryClass.equals(
				callbackConfiguration._callbackFactoryClass) &&
			_properties.equals(callbackConfiguration._properties);
	}

	public Class<? extends CallbackFactory> getCallbackFactoryClass() {
		return _callbackFactoryClass;
	}

	public Properties getProperties() {
		return _properties;
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _callbackFactoryClass);

		return HashUtil.hash(hash, _properties);
	}

	private final Class<? extends CallbackFactory> _callbackFactoryClass;
	private final Properties _properties;

}