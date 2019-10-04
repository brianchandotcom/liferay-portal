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

package com.liferay.portal.security.auth;

import com.liferay.portal.kernel.security.auth.ScreenNameGenerator;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 * @author Peter Fellwock
 */
public class ScreenNameGeneratorFactory {

	public static ScreenNameGenerator getInstance() {
		return _screenNameGeneratorFactory._serviceTracker.getService();
	}

	private ScreenNameGeneratorFactory() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(ScreenNameGenerator.class);

		_serviceTracker.open();
	}

	private static final ScreenNameGeneratorFactory
		_screenNameGeneratorFactory = new ScreenNameGeneratorFactory();

	private final ServiceTracker<?, ScreenNameGenerator> _serviceTracker;

}