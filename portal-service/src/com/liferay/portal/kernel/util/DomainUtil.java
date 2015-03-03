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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

/**
 * @author Yuxing Wu
 */
public class DomainUtil {

	public static Domain getDomain() {
		PortalRuntimePermission.checkGetBeanProperty(DomainUtil.class);

		return _domain;
	}

	public static String getDomain(String host) {
		return getDomain().getDomain(host);
	}

	public void setDomain(Domain domain) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_domain = domain;
	}

	private static Domain _domain;

}