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

package com.liferay.arquillian.extension.junit.bridge.remote.manager;

import java.lang.annotation.Annotation;

/**
 * @author Matthew Tambara
 */
public class Instance<T> {

	public Instance(
		Class<T> type, Class<? extends Annotation> scope, Manager manager) {

		_type = type;
		_scope = scope;
		_manager = manager;
	}

	public T get() {
		return _manager.resolve(_type);
	}

	public void set(T value) {
		if (_scope == null) {
			throw new IllegalStateException(
				"Value can not be set, instance has no Scope defined: " +
					value);
		}

		_manager.bind(_scope, _type, value);

		_manager.fire(value);
	}

	private final Manager _manager;
	private final Class<? extends Annotation> _scope;
	private final Class<T> _type;

}