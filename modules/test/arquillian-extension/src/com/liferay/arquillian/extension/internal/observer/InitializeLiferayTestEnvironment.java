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

package com.liferay.arquillian.extension.internal.observer;

import com.liferay.arquillian.extension.internal.descriptor.SpringDescriptor;
import com.liferay.arquillian.extension.internal.event.LiferayContextCreatedEvent;
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

	public void beforeClass(@Observes BeforeClass beforeClass) {
		System.setProperty("catalina.base", ".");

		ResetDatabaseUtilDataSource.initialize();

		List<String> configLocations = getConfigLocations();

		InitUtil.initWithSpring(configLocations, true);

		if (System.getProperty("external-properties") == null) {
			System.setProperty("external-properties", "portal-test.properties");
		}

		_event.fire(new LiferayContextCreatedEvent(beforeClass.getTestClass()));
	}

	protected List<String> getConfigLocations() {
		List<String> configLocations = new ArrayList<String>();

		ServiceLoader serviceLoader = _instance.get();

		List<SpringDescriptor> springDescriptors =
			(List<SpringDescriptor>)serviceLoader.all(SpringDescriptor.class);

		for (SpringDescriptor springDescriptor : springDescriptors) {
			configLocations.addAll(springDescriptor.getConfigLocations());
		}

		return configLocations;
	}

	@Inject
	private final Event<LiferayContextCreatedEvent> _event;

	@Inject
	private final Instance<ServiceLoader> _instance;

}