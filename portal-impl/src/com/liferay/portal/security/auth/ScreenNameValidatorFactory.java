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

import com.liferay.portal.kernel.security.auth.ScreenNameValidator;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 * @author Peter Fellwock
 */
public class ScreenNameValidatorFactory {

	public static ScreenNameValidator getInstance() {
		return _screenNameValidatorFactory._serviceTracker.getService();
	}

	private ScreenNameValidatorFactory() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(ScreenNameValidator.class);

		_serviceTracker.open();
	}

	private static final ScreenNameValidatorFactory
		_screenNameValidatorFactory = new ScreenNameValidatorFactory();

	private final ServiceTracker<?, ScreenNameValidator> _serviceTracker;

}