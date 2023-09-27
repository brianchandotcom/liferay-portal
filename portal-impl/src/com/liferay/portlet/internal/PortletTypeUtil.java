/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		catch (NoSuchMethodException noSuchMethodException) {
			_log.error(noSuchMethodException);
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortletTypeUtil.class);

}