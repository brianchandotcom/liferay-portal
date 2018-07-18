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

package com.liferay.portlet.internal;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.lang.reflect.Method;

import javax.portlet.GenericPortlet;
import javax.portlet.HeaderPortlet;
import javax.portlet.HeaderRequest;
import javax.portlet.HeaderResponse;
import javax.portlet.Portlet;

/**
 * @author Brian Wing Shun Chan
 * @author Brian Myunghun Kim
 * @author Raymond Augé
 * @author Neil Griffin
 */
public class PortletTypeUtil {

	public static boolean isHeaderPortlet(Portlet portlet) {
		if (!(portlet instanceof HeaderPortlet)) {
			return false;
		}

		Class<?> portletClass = portlet.getClass();

		try {
			Method renderHeadersMethod = portletClass.getMethod(
				"renderHeaders", HeaderRequest.class, HeaderResponse.class);

			if (GenericPortlet.class !=
					renderHeadersMethod.getDeclaringClass()) {

				return true;
			}
		}
		catch (NoSuchMethodException nsme) {
			_log.error(nsme, nsme);
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortletTypeUtil.class);

}