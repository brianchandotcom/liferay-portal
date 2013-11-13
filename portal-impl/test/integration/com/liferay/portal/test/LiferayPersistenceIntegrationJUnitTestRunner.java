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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.tools.DBUpgrader;

import java.util.Arrays;
import java.util.List;

import org.junit.runners.model.InitializationError;

/**
 * @author Miguel Pastor
 */
public class LiferayPersistenceIntegrationJUnitTestRunner
	extends CustomizableSpringContextJUnitTestRunner {

	public LiferayPersistenceIntegrationJUnitTestRunner(Class<?> clazz)
		throws InitializationError {

		super(clazz);
	}

	@Override
	public void afterApplicationContextInit() {
		try {
			DBUpgrader.upgrade();
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	@Override
	public List<String> getExtraConfigLocations() {
		return Arrays.asList("META-INF/test-persistence-spring.xml");
	}

	private static Log _log = LogFactoryUtil.getLog(
		LiferayPersistenceIntegrationJUnitTestRunner.class);

}
