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

package com.liferay.apio.architect.wiring.osgi.internal.manager.representable;

import com.liferay.apio.architect.representor.Representable;
import com.liferay.apio.architect.wiring.osgi.internal.manager.base.BaseManager;
import com.liferay.apio.architect.wiring.osgi.manager.representable.NameManager;

import java.util.Optional;

import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class NameManagerImpl
	extends BaseManager<Representable, String> implements NameManager {

	public NameManagerImpl() {
		super(Representable.class);
	}

	@Override
	public Optional<String> getNameOptional(String className) {
		return getServiceOptional(className);
	}

	@Override
	protected String map(
		Representable representable,
		ServiceReference<Representable> serviceReference, Class<?> clazz) {

		return representable.getName();
	}

}