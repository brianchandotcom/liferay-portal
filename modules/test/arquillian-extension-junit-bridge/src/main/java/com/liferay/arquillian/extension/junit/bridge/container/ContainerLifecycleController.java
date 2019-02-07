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

package com.liferay.arquillian.extension.junit.bridge.container;

import org.jboss.arquillian.container.spi.Container;
import org.jboss.arquillian.container.spi.client.container.LifecycleException;
import org.jboss.arquillian.container.spi.event.KillContainer;
import org.jboss.arquillian.container.spi.event.SetupContainer;
import org.jboss.arquillian.container.spi.event.SetupContainers;
import org.jboss.arquillian.container.spi.event.StartSuiteContainers;
import org.jboss.arquillian.container.spi.event.StopSuiteContainers;
import org.jboss.arquillian.core.api.Event;
import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;

/**
 * @author Matthew Tambara
 */
public class ContainerLifecycleController {

	public void killContainer(@Observes KillContainer killContainer)
		throws Exception {

		Container container = killContainer.getContainer();

		container.kill();
	}

	public void setupContainer(@Observes SetupContainer setupContainer)
		throws Exception {

		Container container = setupContainer.getContainer();

		container.setup();
	}

	public void setupContainers(@Observes SetupContainers setupContainers) {
		Container container = _containerInstance.get();

		_setupContainerEvent.fire(new SetupContainer(container));
	}

	public void startSuiteContainers(
			@Observes StartSuiteContainers startSuiteContainers)
		throws LifecycleException {

		Container container = _containerInstance.get();

		container.start();
	}

	public void stopSuiteContainers(
			@Observes StopSuiteContainers stopSuiteContainers)
		throws LifecycleException {

		Container container = _containerInstance.get();

		container.stop();
	}

	@Inject
	private Instance<Container> _containerInstance;

	@Inject
	private Event<SetupContainer> _setupContainerEvent;

}