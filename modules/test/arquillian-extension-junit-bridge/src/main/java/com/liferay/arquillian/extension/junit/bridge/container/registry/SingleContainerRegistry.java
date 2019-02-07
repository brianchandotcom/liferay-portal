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
public class SingleContainerRegistry implements ContainerRegistry {

	public SingleContainerRegistry(Container container) {
		_container = container;
	}

	@Override
	public Container create(
		ContainerDef containerDef, ServiceLoader serviceLoader) {

		throw new UnsupportedOperationException();
	}

	@Override
	public Container getContainer(String name) {
		if (name.equals(_container.getName())) {
			return _container;
		}

		return null;
	}

	@Override
	public Container getContainer(TargetDescription targetDescription) {
		return _container;
	}

	@Override
	public List<Container> getContainers() {
		return Collections.singletonList(_container);
	}

	private final Container _container;

}