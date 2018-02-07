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

import com.liferay.apio.architect.identifier.Identifier;
import com.liferay.apio.architect.representor.Representable;
import com.liferay.apio.architect.unsafe.Unsafe;
import com.liferay.apio.architect.wiring.osgi.internal.manager.base.BaseManager;
import com.liferay.apio.architect.wiring.osgi.manager.representable.IdentifierClassManager;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapper.Emitter;

import java.util.Optional;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class IdentifierClassManagerImpl
	extends BaseManager<Representable, Class>
	implements IdentifierClassManager {

	public IdentifierClassManagerImpl() {
		super(Representable.class);
	}

	@Override
	public <T extends Identifier> Optional<Class<T>> getIdentifierClassOptional(
		String name) {

		Optional<Class> optional = getServiceOptional(name);

		return optional.map(Unsafe::unsafeCast);
	}

	@Override
	protected void emit(
		ServiceReference<Representable> serviceReference,
		Emitter<String> emitter) {

		Bundle bundle = FrameworkUtil.getBundle(
			IdentifierClassManagerImpl.class);

		BundleContext bundleContext = bundle.getBundleContext();

		Representable representable = bundleContext.getService(
			serviceReference);

		emitter.emit(representable.getName());
	}

	@Override
	protected Class map(
		Representable representable,
		ServiceReference<Representable> serviceReference, Class<?> clazz) {

		return clazz;
	}

}