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

package com.liferay.arquillian.extension.junit.bridge.container.registry;

import com.liferay.arquillian.extension.junit.bridge.container.impl.ContainerImpl;
import com.liferay.arquillian.extension.junit.bridge.container.remote.LiferayRemoteDeployableContainer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jboss.arquillian.config.descriptor.api.ContainerDef;
import org.jboss.arquillian.container.spi.Container;
import org.jboss.arquillian.container.spi.ContainerRegistry;
import org.jboss.arquillian.container.spi.client.deployment.TargetDescription;
import org.jboss.arquillian.core.spi.ServiceLoader;

/**
 * @author Matthew Tambara
 */
public class LocalContainerRegistry implements ContainerRegistry {

	@Override
	public Container create(
		ContainerDef containerDef, ServiceLoader serviceLoader) {

		Container container = new ContainerImpl(
			containerDef.getContainerName(),
			new LiferayRemoteDeployableContainer(), containerDef);

		_containers.add(container);

		return container;
	}

	@Override
	public Container getContainer(String name) {
		for (Container container : _containers) {
			if (name.equals(container.getName())) {
				return container;
			}
		}

		return null;
	}

	@Override
	public Container getContainer(TargetDescription targetDescription) {
		if (_containers.size() == 1) {
			return _containers.get(0);
		}

		for (Container container : _containers) {
			ContainerDef containerDef = container.getContainerConfiguration();

			if (containerDef.isDefault()) {
				return container;
			}
		}

		return null;
	}

	@Override
	public List<Container> getContainers() {
		return Collections.unmodifiableList(_containers);
	}

	private final List<Container> _containers = new ArrayList<>();

}