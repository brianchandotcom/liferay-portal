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

package com.liferay.mail.util;

import com.liferay.mail.kernel.util.Hook;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 * @author Peter Fellwock
 */
public class HookFactory {

	public static Hook getInstance() {
		return _hookFactory._serviceTracker.getService();
	}

	private HookFactory() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(Hook.class);

		_serviceTracker.open();
	}

	private static final HookFactory _hookFactory = new HookFactory();

	private final ServiceTracker<Hook, Hook> _serviceTracker;

}