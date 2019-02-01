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

import org.jboss.arquillian.config.descriptor.api.ContainerDef;
import org.jboss.arquillian.container.spi.Container;
import org.jboss.arquillian.container.spi.ContainerRegistry;
import org.jboss.arquillian.container.spi.event.KillContainer;
import org.jboss.arquillian.container.spi.event.SetupContainer;
import org.jboss.arquillian.container.spi.event.SetupContainers;
import org.jboss.arquillian.container.spi.event.StartSuiteContainers;
import org.jboss.arquillian.container.spi.event.StopSuiteContainers;
import org.jboss.arquillian.core.api.Event;
import org.jboss.arquillian.core.api.Injector;
import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;

/**
 * @author Matthew Tambara
 */
public class ContainerLifecycleController {

	public void killContainer(@Observes KillContainer killContainer)
		throws Exception {

		_forContainer(killContainer.getContainer(), Container::kill);
	}

	public void setupContainer(@Observes SetupContainer setupContainer)
		throws Exception {

		_forContainer(setupContainer.getContainer(), Container::setup);
	}

	public void setupContainers(@Observes SetupContainers setupContainers)
		throws Exception {

		_forEachContainer(
			new Operation<Container>() {

				@Override
				public void perform(Container container) throws Exception {
					_setupContainerEvent.fire(new SetupContainer(container));
				}

				@Inject
				private Event<SetupContainer> _setupContainerEvent;

			});
	}

	public void startSuiteContainers(
			@Observes StartSuiteContainers startSuiteContainers)
		throws Exception {

		_forEachSuiteContainer(Container::start);
	}

	public void stopSuiteContainers(
			@Observes StopSuiteContainers stopSuiteContainers)
		throws Exception {

		_forEachSuiteContainer(Container::stop);
	}

	public interface Operation<T> {

		public void perform(T container) throws Exception;

	}

	private void _forContainer(
			Container container, Operation<Container> operation)
		throws Exception {

		Injector injector = _injectorInstance.get();

		injector.inject(operation);

		operation.perform(container);
	}

	private void _forEachContainer(Operation<Container> operation)
		throws Exception {

		Injector injector = _injectorInstance.get();

		injector.inject(operation);

		ContainerRegistry containerRegistry = _containerRegistryInstance.get();

		if (containerRegistry == null) {
			return;
		}

		for (Container container : containerRegistry.getContainers()) {
			operation.perform(container);
		}
	}

	private void _forEachSuiteContainer(Operation<Container> operation)
		throws Exception {

		Injector injector = _injectorInstance.get();

		injector.inject(operation);

		ContainerRegistry containerRegistry = _containerRegistryInstance.get();

		for (Container container : containerRegistry.getContainers()) {
			ContainerDef containerDef = container.getContainerConfiguration();

			if ("suite".equals(containerDef.getMode())) {
				operation.perform(container);
			}
		}
	}

	@Inject
	private Instance<ContainerRegistry> _containerRegistryInstance;

	@Inject
	private Instance<Injector> _injectorInstance;

}