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

package com.liferay.portal.template;

import com.liferay.portal.bean.BeanLocatorImpl;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * @author Raymond Augé
 */
public class UtilLocator {

	public static UtilLocator getInstance() {
		return _utilLocator;
	}

	public Object findUtil(String utilName) {
		Object bean = null;

		try {
			bean = PortalBeanLocatorUtil.locate(_getUtilName(utilName));
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return bean;
	}

	public Object findUtil(String servletContextName, String utilName) {
		Object bean = null;

		try {
			bean = PortletBeanLocatorUtil.locate(
				servletContextName, _getUtilName(utilName));
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return bean;
	}

	private UtilLocator() {
	}

	private String _getUtilName(String utilName) {
		if (!utilName.endsWith(BeanLocatorImpl.VELOCITY_SUFFIX)) {
			utilName += BeanLocatorImpl.VELOCITY_SUFFIX;
		}

		return utilName;
	}

	private static final Log _log = LogFactoryUtil.getLog(UtilLocator.class);

	private static final UtilLocator _utilLocator = new UtilLocator();

}