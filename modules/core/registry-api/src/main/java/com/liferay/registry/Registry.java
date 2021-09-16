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

package com.liferay.registry;

import java.util.Collection;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Raymond Augé
 */
@ProviderType
public interface Registry {

	public <T> ServiceReference<T>[] getAllServiceReferences(
			String className, String filterString)
		throws Exception;

	public <T> T getService(ServiceReference<T> serviceReference);

	public <T> ServiceReference<T> getServiceReference(Class<T> clazz);

	public <T> ServiceReference<T> getServiceReference(String className);

	public <T> Collection<ServiceReference<T>> getServiceReferences(
			Class<T> clazz, String filterString)
		throws Exception;

	public <T> ServiceReference<T>[] getServiceReferences(
			String className, String filterString)
		throws Exception;

	public <T> Collection<T> getServices(Class<T> clazz, String filterString)
		throws Exception;

	public <T> T[] getServices(String className, String filterString)
		throws Exception;

	public String getSymbolicName(ClassLoader classLoader);

	public <T> ServiceRegistration<T> registerService(
		Class<T> clazz, T service);

	public <T> ServiceRegistration<T> registerService(
		Class<T> clazz, T service, Map<String, Object> properties);

	public <T> ServiceRegistration<T> registerService(
		String className, T service);

	public <T> ServiceRegistration<T> registerService(
		String className, T service, Map<String, Object> properties);

	public <T> ServiceRegistration<T> registerService(
		String[] classNames, T service);

	public <T> ServiceRegistration<T> registerService(
		String[] classNames, T service, Map<String, Object> properties);

	public <T> boolean ungetService(ServiceReference<T> serviceReference);

}