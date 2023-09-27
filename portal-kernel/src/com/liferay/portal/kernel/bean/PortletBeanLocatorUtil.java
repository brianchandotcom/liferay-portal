/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.bean;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletBeanLocatorUtil {

	public static BeanLocator getBeanLocator(String servletContextName) {
		return _beanLocators.get(servletContextName);
	}

	public static Object locate(String servletContextName, String name)
		throws BeanLocatorException {

		BeanLocator beanLocator = getBeanLocator(servletContextName);

		if (beanLocator == null) {
			_log.error(
				"BeanLocator is null for servlet context " +
					servletContextName);

			throw new BeanLocatorException(
				"BeanLocator is not set for servlet context " +
					servletContextName);
		}

		return beanLocator.locate(name);
	}

	public static void setBeanLocator(
		String servletContextName, BeanLocator beanLocator) {

		if (_log.isDebugEnabled()) {
			if (beanLocator != null) {
				_log.debug(
					StringBundler.concat(
						"Setting BeanLocator ", beanLocator.hashCode(),
						" for servlet context ", servletContextName));
			}
			else {
				_log.debug(
					"Removing BeanLocator for servlet context " +
						servletContextName);
			}
		}

		_beanLocators.put(servletContextName, beanLocator);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortletBeanLocatorUtil.class);

	private static final Map<String, BeanLocator> _beanLocators =
		new HashMap<>();

}