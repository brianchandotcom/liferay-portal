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

import com.liferay.portal.kernel.security.auth.EmailAddressGenerator;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

/**
 * @author Amos Fong
 * @author Shuyang Zhou
 * @author Peter Fellwock
 */
public class EmailAddressGeneratorFactory {

	public static EmailAddressGenerator getInstance() {
		return _emailAddressGeneratorFactory._serviceTracker.getService();
	}

	private EmailAddressGeneratorFactory() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(EmailAddressGenerator.class);

		_serviceTracker.open();
	}

	private static final EmailAddressGeneratorFactory
		_emailAddressGeneratorFactory = new EmailAddressGeneratorFactory();

	private final ServiceTracker<?, EmailAddressGenerator> _serviceTracker;

}