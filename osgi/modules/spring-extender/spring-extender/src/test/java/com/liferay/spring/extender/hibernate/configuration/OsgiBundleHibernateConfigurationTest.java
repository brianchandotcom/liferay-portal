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

package com.liferay.spring.extender.hibernate.configuration;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Miguel Pastor
 */
public class OsgiBundleHibernateConfigurationTest {

	@Test
	public void testConfigurationPaths() {
		String[] configurationResources =
			_configuration.getConfigurationResources();

		Assert.assertEquals(1, configurationResources.length);
		Assert.assertEquals(
			"META-INF/module-hbm.xml", configurationResources[0]);
	}

	private OsgiBundleHibernateConfiguration _configuration =
		new OsgiBundleHibernateConfiguration();

}