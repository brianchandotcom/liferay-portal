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

import javax.portlet.annotations.PortletSessionScoped;

/**
 * @author Neil Griffin
 */
public class SpringPortletSessionScope extends BaseScope {

	public SpringPortletSessionScope(int subscope) {
		_subscope = subscope;
	}

	@Override
	public String getScopeName() {
		return PortletSessionScoped.class.getSimpleName();
	}

	@Override
	protected SpringScopedBean getSpringScopedBean(String name) {
		SpringScopedBeanManager scopedBeanManager =
			SpringScopedBeanManagerThreadLocal.getCurrentScopedBeanManager();

		return scopedBeanManager.getPortletSessionScopedBean(_subscope, name);
	}

	@Override
	protected void setSpringScopedBean(
		String name, SpringScopedBean springScopedBean) {

		SpringScopedBeanManager scopedBeanManager =
			SpringScopedBeanManagerThreadLocal.getCurrentScopedBeanManager();

		scopedBeanManager.setPortletSessionScopedBean(
			_subscope, name, springScopedBean);
	}

	private final int _subscope;

}