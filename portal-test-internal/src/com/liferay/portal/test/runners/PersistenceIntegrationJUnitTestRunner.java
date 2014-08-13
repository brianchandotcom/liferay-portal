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
	protected List<String> processConfigurations(List<String> configLocations) {
		configLocations.remove("META-INF/model-listener-spring.xml");

		return configLocations;
	}

}