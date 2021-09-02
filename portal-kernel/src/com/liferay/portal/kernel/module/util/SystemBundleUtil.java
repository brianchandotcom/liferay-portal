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

package com.liferay.portal.kernel.module.util;

import com.liferay.petra.function.UnsafeFunction;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

/**
 * @author Shuyang Zhou
 */
public class SystemBundleUtil {

	public static <S, R, E extends Throwable> R callService(
			Class<S> serviceClass, UnsafeFunction<S, R, E> unsafeFunction)
		throws E {

		BundleContext bundleContext = getBundleContext();

		ServiceReference<S> serviceReference =
			bundleContext.getServiceReference(serviceClass);

		if (serviceReference == null) {
			return unsafeFunction.apply(null);
		}

		S service = bundleContext.getService(serviceReference);

		try {
			return unsafeFunction.apply(service);
		}
		finally {
			bundleContext.ungetService(serviceReference);
		}
	}

	public static <S, R, E extends Throwable> R callService(
			String serviceClassName, UnsafeFunction<S, R, E> unsafeFunction)
		throws E {

		BundleContext bundleContext = getBundleContext();

		ServiceReference<?> serviceReference =
			bundleContext.getServiceReference(serviceClassName);

		if (serviceReference == null) {
			return unsafeFunction.apply(null);
		}

		S service = (S)bundleContext.getService(serviceReference);

		try {
			return unsafeFunction.apply(service);
		}
		finally {
			bundleContext.ungetService(serviceReference);
		}
	}

	public static Filter createFilter(String filterString) {
		BundleContext bundleContext = getBundleContext();

		try {
			return bundleContext.createFilter(filterString);
		}
		catch (InvalidSyntaxException invalidSyntaxException) {
			throw new RuntimeException(invalidSyntaxException);
		}
	}

	public static BundleContext getBundleContext() {
		Bundle systemBundle = _systemBundleProvider.getSystemBundle();

		if (systemBundle == null) {
			throw new IllegalStateException("System bundle is not initialized");
		}

		return systemBundle.getBundleContext();
	}

	public static ServiceLatch newServiceLatch() {
		return new ServiceLatch(getBundleContext());
	}

	private static final SystemBundleProvider _systemBundleProvider;

	static {
		ServiceLoader<SystemBundleProvider> serviceLoader = ServiceLoader.load(
			SystemBundleProvider.class,
			SystemBundleUtil.class.getClassLoader());

		Iterator<SystemBundleProvider> iterator = serviceLoader.iterator();

		List<SystemBundleProvider> systemBundleProviders = new ArrayList<>();

		iterator.forEachRemaining(systemBundleProviders::add);

		if (systemBundleProviders.isEmpty()) {
			throw new ExceptionInInitializerError(
				"Unable to locate module framework implementation");
		}

		systemBundleProviders.sort(Comparator.reverseOrder());

		_systemBundleProvider = systemBundleProviders.get(0);
	}

}