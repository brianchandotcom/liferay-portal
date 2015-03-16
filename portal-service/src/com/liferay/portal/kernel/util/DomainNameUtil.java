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

import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

/**
 * @author Yuxing Wu
 */
public class DomainNameUtil {

	public static String getCookieDomain(String domain) {
		return _getInstance().getCookieDomain(domain);
	}

	private static DomainName _getInstance() {
		return _instance._serviceTracker.getService();
	}

	private DomainNameUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(DomainName.class);

		_serviceTracker.open();
	}

	private static final DomainNameUtil _instance = new DomainNameUtil();

	private final ServiceTracker<DomainName, DomainName> _serviceTracker;

}