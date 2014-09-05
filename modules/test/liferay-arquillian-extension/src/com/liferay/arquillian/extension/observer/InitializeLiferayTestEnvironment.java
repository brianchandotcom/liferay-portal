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

package com.liferay.arquillian.extension.observer;

import com.liferay.arquillian.extension.descriptor.SpringDescriptor;
import com.liferay.arquillian.extension.event.LiferayContextCreatedEvent;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.test.jdbc.ResetDatabaseUtilDataSource;
import com.liferay.portal.util.InitUtil;

import java.util.ArrayList;
import java.util.List;

import org.jboss.arquillian.core.api.Event;
import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.core.spi.ServiceLoader;
import org.jboss.arquillian.test.spi.event.suite.BeforeClass;

/**
 * @author Cristina González
 */
public class InitializeLiferayTestEnvironment {

	public void beforeClass(@Observes BeforeClass beforeClass)
		throws TemplateException {

		System.setProperty("catalina.base", ".");

		ResetDatabaseUtilDataSource.initialize();

		List<String> configLocalions = configLocations();

		InitUtil.initWithSpringAndModuleFramework(
			false, configLocalions, false);

		if (System.getProperty("external-properties") == null) {
			System.setProperty("external-properties", "portal-test.properties");
		}
	}

	private List<String> configLocations() {
		ServiceLoader serviceLoader = _serviceLoaderInstance.get();

		List<SpringDescriptor> springDescriptors =
			(List<SpringDescriptor>)serviceLoader.all(SpringDescriptor.class);

		List<String> configLocalions = new ArrayList<String>();

		for (SpringDescriptor springDescriptor : springDescriptors) {
			configLocalions.addAll(springDescriptor.getConfigLocations());
		}

		return configLocalions;
	}

	@Inject
	private Instance<ServiceLoader> _serviceLoaderInstance;

}