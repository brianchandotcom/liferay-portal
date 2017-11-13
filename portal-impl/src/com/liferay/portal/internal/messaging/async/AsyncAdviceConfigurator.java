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

package com.liferay.portal.internal.messaging.async;

import com.liferay.portal.messaging.async.AsyncAdvice;

import java.util.Map;

/**
 * @author Shuyang Zhou
 */
public class AsyncAdviceConfigurator {

	public void afterPropertiesSet() {
		if (_asyncAdvice == null) {
			throw new IllegalArgumentException("Async advice is null");
		}

		if (_defaultDestinationName == null) {
			throw new IllegalArgumentException(
				"Default destination name is null");
		}

		_asyncAdvice.setDefaultDestinationName(_defaultDestinationName);

		if (_destinationNames != null) {
			_asyncAdvice.setDestinationNames(_destinationNames);
		}
	}

	public void setAsyncAdvice(AsyncAdvice asyncAdvice) {
		_asyncAdvice = asyncAdvice;
	}

	public void setDefaultDestinationName(String defaultDestinationName) {
		_defaultDestinationName = defaultDestinationName;
	}

	public void setDestinationNames(Map<Class<?>, String> destinationNames) {
		_destinationNames = destinationNames;
	}

	private AsyncAdvice _asyncAdvice;
	private String _defaultDestinationName;
	private Map<Class<?>, String> _destinationNames;

}