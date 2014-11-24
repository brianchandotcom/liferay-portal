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

package com.liferay.portal.kernel.test;

import org.junit.runner.manipulation.Sorter;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

/**
 * @author Miguel Pastor
 */
public abstract class AbstractIntegrationJUnitTestRunner
	extends BlockJUnit4ClassRunner {

	public AbstractIntegrationJUnitTestRunner(Class<?> clazz)
		throws InitializationError {

		super(clazz);

		initApplicationContext();

		if (System.getProperty("external-properties") == null) {
			System.setProperty("external-properties", "portal-test.properties");
		}

		sort(new Sorter(new DescriptionComparator()));
	}

	public abstract void initApplicationContext();

}