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

import static com.liferay.apio.architect.wiring.osgi.internal.manager.resource.ResourceClass.MODEL_CLASS;
import static com.liferay.apio.architect.wiring.osgi.internal.manager.util.ManagerUtil.getGenericClassFromPropertyOrElse;
import static com.liferay.apio.architect.wiring.osgi.internal.manager.util.ManagerUtil.getTypeParamOrFail;

import com.liferay.apio.architect.representor.Representable;
import com.liferay.apio.architect.wiring.osgi.internal.manager.base.BaseManager;
import com.liferay.apio.architect.wiring.osgi.internal.manager.router.NestedCollectionRouterManagerImpl;
import com.liferay.apio.architect.wiring.osgi.manager.representable.ModelClassManager;
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
public class ModelClassManagerImpl
	extends BaseManager<Representable, Class> implements ModelClassManager {

	public ModelClassManagerImpl() {
		super(Representable.class);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> Optional<Class<T>> getModelClassOptional(String name) {
		Optional<Class> optional = getServiceOptional(name);

		return optional.map(clazz -> (Class<T>)clazz);
	}

	@Override
	protected void emit(
		ServiceReference<Representable> serviceReference,
		Emitter<String> emitter) {

		Bundle bundle = FrameworkUtil.getBundle(
			NestedCollectionRouterManagerImpl.class);

		BundleContext bundleContext = bundle.getBundleContext();

		Representable representable = bundleContext.getService(
			serviceReference);

		emitter.emit(representable.getName());
	}

	@Override
	protected Class map(
		Representable representable,
		ServiceReference<Representable> serviceReference, Class<?> modelClass) {

		return getGenericClassFromPropertyOrElse(
			serviceReference, MODEL_CLASS,
			() -> getTypeParamOrFail(representable, Representable.class, 0));
	}

}