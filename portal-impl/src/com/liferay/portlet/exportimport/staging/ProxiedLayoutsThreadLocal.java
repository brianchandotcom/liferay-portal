/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.exportimport.staging;

import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.security.auth.CompanyCentralizedThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ObjectValuePair;

import java.util.Map;

/**
 * @author Sergio Sánchez
 */
public class ProxiedLayoutsThreadLocal {

	public static void clearProxiedLayouts() {
		_proxiedLayouts.remove();
	}

	public static ObjectValuePair<ServiceContext, Map<Layout, Object>>
		getProxiedLayouts() {

		return _proxiedLayouts.get();
	}

	public static void setProxiedLayouts(
		ObjectValuePair<ServiceContext, Map<Layout, Object>> objectValuePair) {

		_proxiedLayouts.set(objectValuePair);
	}

	private static final ThreadLocal
		<ObjectValuePair<ServiceContext, Map<Layout, Object>>> _proxiedLayouts =
			new CompanyCentralizedThreadLocal<>(
				ProxiedLayoutsThreadLocal.class + "._proxiedLayouts");

}