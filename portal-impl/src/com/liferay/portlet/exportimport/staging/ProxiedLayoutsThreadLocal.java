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

package com.liferay.portlet.exportimport.staging;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.util.AutoResetThreadLocal;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Sergio Sánchez
 */
@ProviderType
public class ProxiedLayoutsThreadLocal {

	public static void clearProxiedLayouts() {
		_proxiedLayouts.remove();
	}

	public static Map<Layout, Object> getProxiedLayouts() {
		return _proxiedLayouts.get();
	}

	private static final ThreadLocal<Map<Layout, Object>> _proxiedLayouts =
		new AutoResetThreadLocal<Map<Layout, Object>>(
			ProxiedLayoutsThreadLocal.class + "._proxiedLayouts",
			new HashMap<Layout, Object>());

}