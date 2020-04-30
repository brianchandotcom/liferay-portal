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

package com.liferay.bean.portlet.spring.extension.internal.scope;

import com.liferay.bean.portlet.extension.ScopedBean;

import java.io.Serializable;

/**
 * @author Neil Griffin
 */
public class SpringScopedBean implements ScopedBean<Object>, Serializable {

	public SpringScopedBean(
		Object containerCreatedInstance, Runnable destructionCallback,
		String scopeName) {

		_containerCreatedInstance = containerCreatedInstance;
		_destructionCallback = destructionCallback;
		_scopeName = scopeName;
	}

	@Override
	public void destroy() {
		if (_destructionCallback != null) {
			_destructionCallback.run();
		}
	}

	@Override
	public Object getContainerCreatedInstance() {
		return _containerCreatedInstance;
	}

	public String getScopeName() {
		return _scopeName;
	}

	private static final long serialVersionUID = 2356583366611553322L;

	private final Object _containerCreatedInstance;
	private final Runnable _destructionCallback;
	private final String _scopeName;

}