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

package com.liferay.apio.architect.wiring.osgi.internal.service.tracker.customizer;

import static com.liferay.apio.architect.wiring.osgi.internal.manager.TypeArgumentProperties.PRINCIPAL_TYPE_ARGUMENT;
import static com.liferay.apio.architect.wiring.osgi.internal.manager.util.ManagerUtil.getGenericClassFromPropertyOrElse;
import static com.liferay.apio.architect.wiring.osgi.internal.manager.util.ManagerUtil.getTypeParamOrFail;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * Instances of this class represent a {@code ServiceTrackerCustomizer} that
 * transforms the service to another type.
 *
 * @author Alejandro Hernández
 * @review
 */
public abstract class TransformServiceTrackerCustomizer<T, U>
	implements ServiceTrackerCustomizer<T, U> {

	public TransformServiceTrackerCustomizer(Class<T> managedClass) {
		_managedClass = managedClass;
	}

	@Override
	public U addingService(ServiceReference<T> serviceReference) {
		Bundle bundle = FrameworkUtil.getBundle(
			TransformServiceTrackerCustomizer.class);

		BundleContext bundleContext = bundle.getBundleContext();

		T t = bundleContext.getService(serviceReference);

		Class<?> clazz = getGenericClassFromPropertyOrElse(
			serviceReference, PRINCIPAL_TYPE_ARGUMENT,
			() -> getTypeParamOrFail(
				t, _managedClass, getPrincipalTypeParamPosition()));

		return map(t, serviceReference, clazz);
	}

	/**
	 * Returns the position of the principal type param.
	 *
	 * @return the position
	 * @review
	 */
	public Integer getPrincipalTypeParamPosition() {
		return 2;
	}

	@Override
	public void modifiedService(ServiceReference<T> serviceReference, U u) {
	}

	@Override
	public void removedService(ServiceReference<T> serviceReference, U u) {
		Bundle bundle = FrameworkUtil.getBundle(
			TransformServiceTrackerCustomizer.class);

		BundleContext bundleContext = bundle.getBundleContext();

		bundleContext.ungetService(serviceReference);

		onRemovedService(serviceReference, u);
	}

	/**
	 * Returns the managed class.
	 *
	 * @return the managed class
	 * @review
	 */
	protected Class<T> getManagedClass() {
		return _managedClass;
	}

	/**
	 * Transforms a service of type {@link T} into a {@link U}.
	 *
	 * @param  t the service to transform
	 * @param  serviceReference the service reference
	 * @param  clazz the generic class
	 * @return the service transformed to a {@link U}
	 * @review
	 */
	protected abstract U map(
		T t, ServiceReference<T> serviceReference, Class<?> clazz);

	/**
	 * Called when the service is being removed.
	 *
	 * @param  serviceReference the service reference being removed
	 * @param  u the service being removed
	 * @review
	 */
	protected void onRemovedService(ServiceReference<T> serviceReference, U u) {
	}

	private final Class<T> _managedClass;

}