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
import org.jboss.arquillian.container.spi.event.StartClassContainers;
import org.jboss.arquillian.container.spi.event.StartContainer;
import org.jboss.arquillian.container.spi.event.StartSuiteContainers;
import org.jboss.arquillian.container.spi.event.StopClassContainers;
import org.jboss.arquillian.container.spi.event.StopContainer;
import org.jboss.arquillian.container.spi.event.StopManualContainers;
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

		_forContainer(
			killContainer.getContainer(),
			new Operation<Container>() {

				@Override
				public void perform(Container container) throws Exception {
					if (container.getState().equals(Container.State.STARTED)) {
						container.kill();
					}
				}

			});
	}

	public void setupContainer(@Observes SetupContainer setupContainer)
		throws Exception {

		_forContainer(
			setupContainer.getContainer(),
			new Operation<Container>() {

				@Override
				public void perform(Container container) throws Exception {
					container.setup();
				}

			});
	}

	public void setupContainers(@Observes SetupContainers setupContainers)
		throws Exception {

		_forEachContainer(
			new Operation<Container>() {

				@Override
				public void perform(Container container) {
					_setupContainerEvent.fire(new SetupContainer(container));
				}

				@Inject
				private Event<SetupContainer> _setupContainerEvent;

			});
	}

	public void startClassContainers(
			@Observes StartClassContainers startClassContainers)
		throws Exception {

		_forEachClassContainer(
			new Operation<Container>() {

				@Override
				public void perform(Container container) {
					_startContainerEvent.fire(new StartContainer(container));
				}

				@Inject
				private Event<StartContainer> _startContainerEvent;

			});
	}

	public void startContainer(@Observes StartContainer startContainer)
		throws Exception {

		_forContainer(
			startContainer.getContainer(),
			new Operation<Container>() {

				@Override
				public void perform(Container container) throws Exception {
					if (!container.getState().equals(Container.State.STARTED)) {
						container.start();
					}
				}

			});
	}

	public void startSuiteContainers(
			@Observes StartSuiteContainers startSuiteContainers)
		throws Exception {

		_forEachSuiteContainer(
			new Operation<Container>() {

				@Override
				public void perform(Container container) {
					_startContainerEvent.fire(new StartContainer(container));
				}

				@Inject
				private Event<StartContainer> _startContainerEvent;

			});
	}

	public void stopClassContainers(
			@Observes StopClassContainers stopClassContainers)
		throws Exception {

		_forEachClassContainer(
			new Operation<Container>() {

				@Override
				public void perform(Container container) {
					_stopContainerEvent.fire(new StopContainer(container));
				}

				@Inject
				private Event<StopContainer> _stopContainerEvent;

			});
	}

	public void stopContainer(@Observes StopContainer stopContainer)
		throws Exception {

		_forContainer(
			stopContainer.getContainer(),
			new Operation<Container>() {

				@Override
				public void perform(Container container) throws Exception {
					if (container.getState().equals(Container.State.STARTED)) {
						container.stop();
					}
				}

			});
	}

	public void stopManualContainers(
			@Observes StopManualContainers stopManualContainers)
		throws Exception {

		_forEachManualContainer(
			new Operation<Container>() {

				@Override
				public void perform(Container container) {
					_stopContainerEvent.fire(new StopContainer(container));
				}

				@Inject
				private Event<StopContainer> _stopContainerEvent;

			});
	}

	public void stopSuiteContainers(
			@Observes StopSuiteContainers stopSuiteContainers)
		throws Exception {

		_forEachSuiteContainer(
			new Operation<Container>() {

				@Override
				public void perform(Container container) {
					_stopContainerEvent.fire(new StopContainer(container));
				}

				@Inject
				private Event<StopContainer> _stopContainerEvent;

			});
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

	private void _forEachClassContainer(Operation<Container> operation)
		throws Exception {

		Injector injector = _injectorInstance.get();

		injector.inject(operation);

		ContainerRegistry containerRegistry = _containerRegistryInstance.get();

		for (Container container : containerRegistry.getContainers()) {
			ContainerDef containerDef = container.getContainerConfiguration();

			if ("class".equals(containerDef.getMode())) {
				operation.perform(container);
			}
		}
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

	private void _forEachManualContainer(Operation<Container> operation)
		throws Exception {

		Injector injector = _injectorInstance.get();

		injector.inject(operation);

		ContainerRegistry containerRegistry = _containerRegistryInstance.get();

		for (Container container : containerRegistry.getContainers()) {
			ContainerDef containerDef = container.getContainerConfiguration();

			if ("manual".equals(containerDef.getMode())) {
				operation.perform(container);
			}
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