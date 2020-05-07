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

package com.liferay.bean.portlet.cdi.extension.internal.scope;

import com.liferay.bean.portlet.extension.ScopedBean;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.Serializable;

import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;

/**
 * @author Neil Griffin
 */
public class CDIScopedBean<T> implements ScopedBean<T>, Serializable {

	public CDIScopedBean(
		Contextual<T> bean, CreationalContext<T> creationalContext, String name,
		String scopeName) {

		_bean = bean;
		_creationalContext = creationalContext;
		_name = name;
		_scopeName = scopeName;

		_containerCreatedInstance = bean.create(creationalContext);
	}

	@Override
	public void destroy() {
		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Destroying @", _scopeName, " bean named ", _name));
		}

		_creationalContext.release();

		_bean.destroy(_containerCreatedInstance, _creationalContext);
	}

	@Override
	public T getContainerCreatedInstance() {
		return _containerCreatedInstance;
	}

	public String getScopeName() {
		return _scopeName;
	}

	private static final Log _log = LogFactoryUtil.getLog(CDIScopedBean.class);

	private static final long serialVersionUID = 2388556996969921221L;

	private final Contextual<T> _bean;
	private final T _containerCreatedInstance;
	private final CreationalContext<T> _creationalContext;
	private final String _name;
	private final String _scopeName;

}