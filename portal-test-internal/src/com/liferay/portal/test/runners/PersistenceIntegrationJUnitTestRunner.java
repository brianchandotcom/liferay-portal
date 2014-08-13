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
package com.liferay.portal.test.runners;

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.test.jdbc.ResetDatabaseUtilDataSource;
import com.liferay.portal.util.InitUtil;
import com.liferay.portal.util.PropsUtil;

import java.util.List;

import org.junit.runners.model.InitializationError;

/**
 * @author Raymond Augé
 */
public class PersistenceIntegrationJUnitTestRunner
	extends LiferayIntegrationJUnitTestRunner {

	public PersistenceIntegrationJUnitTestRunner(Class<?> clazz)
		throws InitializationError {

		super(clazz);
	}

	@Override
	public void initApplicationContext() {
		System.setProperty("catalina.base", ".");

		ResetDatabaseUtilDataSource.initialize();

		List<String> configLocations = ListUtil.fromArray(
			PropsUtil.getArray(PropsKeys.SPRING_CONFIGS));

		configLocations.remove("META-INF/model-listener-spring.xml");

		InitUtil.initWithSpringAndModuleFramework(
			false, configLocations, false);

		afterApplicationContextInit();
	}

}