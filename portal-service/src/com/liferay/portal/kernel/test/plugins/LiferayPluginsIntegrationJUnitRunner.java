/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.test.plugins;

import com.liferay.portal.kernel.test.AbstractIntegrationJUnitTestRunner;

import org.junit.runners.model.InitializationError;

/**
 * @author Manuel de la Peña
 */
public class LiferayPluginsIntegrationJUnitRunner
	extends AbstractIntegrationJUnitTestRunner {

	public LiferayPluginsIntegrationJUnitRunner(Class<?> clazz)
		throws InitializationError {

		super(clazz);
	}

	@Override
	public void initApplicationContext() {

		// It is not necessary to init application context in plugins because
		// a portal instance must be up and running

	}

}