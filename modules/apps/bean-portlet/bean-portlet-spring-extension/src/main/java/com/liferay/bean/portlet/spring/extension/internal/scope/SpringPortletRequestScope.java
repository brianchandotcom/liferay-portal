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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import javax.portlet.annotations.PortletRequestScoped;

/**
 * @author Neil Griffin
 */
public class SpringPortletRequestScope extends BaseScope {

	@Override
	public String getScopeName() {
		return PortletRequestScoped.class.getSimpleName();
	}

	@Override
	protected SpringScopedBean getSpringScopedBean(String name) {
		SpringScopedBeanManager scopedBeanManager =
			SpringScopedBeanManagerThreadLocal.getCurrentScopedBeanManager();

		if (scopedBeanManager == null) {
			_log.error(
				"Attempted to get a @PortletRequestScoped bean name=" + name +
					" outside the scope of a portlet request.");

			return null;
		}

		return scopedBeanManager.getPortletRequestScopedBean(name);
	}

	@Override
	protected void setSpringScopedBean(
		String name, SpringScopedBean springScopedBean) {

		SpringScopedBeanManager scopedBeanManager =
			SpringScopedBeanManagerThreadLocal.getCurrentScopedBeanManager();

		scopedBeanManager.setPortletRequestScopedBean(name, springScopedBean);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SpringPortletRequestScope.class);

}