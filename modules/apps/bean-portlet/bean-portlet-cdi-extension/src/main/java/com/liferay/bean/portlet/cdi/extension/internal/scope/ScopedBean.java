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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.Serializable;

import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;

/**
 * @author Neil Griffin
 */
public class ScopedBean<T> implements Serializable {

	public ScopedBean(
		String name, Contextual<T> bean, CreationalContext<T> creationalContext,
		String scopeName) {

		_name = name;
		_bean = bean;
		_creationalContext = creationalContext;
		_scopeName = scopeName;

		_beanInstance = bean.create(creationalContext);
	}

	public void destroy() {
		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Destroying @", _scopeName, " bean name=", _name));
		}

		_creationalContext.release();

		_bean.destroy(_beanInstance, _creationalContext);
	}

	public T getBeanInstance() {
		return _beanInstance;
	}

	private static final Log _log = LogFactoryUtil.getLog(ScopedBean.class);

	private static final long serialVersionUID = 2388556996969921221L;

	private final Contextual<T> _bean;
	private final T _beanInstance;
	private final CreationalContext<T> _creationalContext;
	private final String _name;
	private final String _scopeName;

}