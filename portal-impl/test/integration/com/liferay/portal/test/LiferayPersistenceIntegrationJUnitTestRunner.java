/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.test;

import com.liferay.portal.tools.DBUpgrader;
import com.liferay.portal.util.InitUtil;

import java.util.ArrayList;
import java.util.List;

import org.junit.runners.model.InitializationError;

/**
 * @author Miguel Pastor
 */
public class LiferayPersistenceIntegrationJUnitTestRunner
	extends LiferayIntegrationJUnitTestRunner {

	public LiferayPersistenceIntegrationJUnitTestRunner(Class<?> clazz)
		throws InitializationError {

		super(clazz);
	}

	@Override
	public void initApplicationContext() {
		List<String> extraConfigLocations = new ArrayList<String>(1);

		extraConfigLocations.add("META-INF/test-persistence-spring.xml");

		InitUtil.initWithSpring(extraConfigLocations);

		try {
			DBUpgrader.upgrade();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}