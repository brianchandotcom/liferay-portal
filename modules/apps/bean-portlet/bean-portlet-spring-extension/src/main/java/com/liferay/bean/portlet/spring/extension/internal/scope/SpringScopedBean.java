/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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