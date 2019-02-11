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

package com.liferay.arquillian.extension.junit.bridge.observer;

import com.liferay.arquillian.extension.junit.bridge.container.LiferayRemoteDeployableContainer;
import com.liferay.arquillian.extension.junit.bridge.container.impl.ContainerImpl;

import org.jboss.arquillian.config.descriptor.api.ContainerDef;
import org.jboss.arquillian.config.descriptor.impl.ContainerDefImpl;
import org.jboss.arquillian.container.spi.Container;
import org.jboss.arquillian.core.api.Injector;
import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.InstanceProducer;
import org.jboss.arquillian.core.api.annotation.ApplicationScoped;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.core.api.event.ManagerStarted;

/**
 * @author Matthew Tambara
 */
public class ContainerRegistrar {

	public void createContainer(@Observes ManagerStarted managerStarted) {
		ContainerDef containerDef = new ContainerDefImpl("arquillian.xml");

		containerDef.setContainerName("default");

		Container container = new ContainerImpl(
			containerDef.getContainerName(),
			new LiferayRemoteDeployableContainer(), containerDef);

		Injector injector = _injectorInstance.get();

		injector.inject(container);

		_containerInstanceProducer.set(container);
	}

	@ApplicationScoped
	@Inject
	private InstanceProducer<Container> _containerInstanceProducer;

	@Inject
	private Instance<Injector> _injectorInstance;

}