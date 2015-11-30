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

package com.liferay.osgi.service.tracker.collections.internal.map;

import java.io.File;
import java.io.InputStream;

import java.util.Collection;
import java.util.Dictionary;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.BundleListener;
import org.osgi.framework.Filter;
import org.osgi.framework.FrameworkListener;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Carlos Sierra Andrés
 */
public class BundleContextWrapper implements BundleContext {

	public BundleContextWrapper(BundleContext bundleContext) {
		if (bundleContext == null) {
			throw new IllegalArgumentException();
		}

		_bundleContext = bundleContext;
	}

	@Override
	public void addBundleListener(BundleListener bundleListener) {
		_bundleContext.addBundleListener(bundleListener);
	}

	@Override
	public void addFrameworkListener(FrameworkListener frameworkListener) {
		_bundleContext.addFrameworkListener(frameworkListener);
	}

	@Override
	public void addServiceListener(ServiceListener serviceListener) {
		_bundleContext.addServiceListener(serviceListener);
	}

	@Override
	public void addServiceListener(
			ServiceListener serviceListener, String filterString)
		throws InvalidSyntaxException {

		_bundleContext.addServiceListener(serviceListener, filterString);
	}

	@Override
	public Filter createFilter(String s) throws InvalidSyntaxException {
		return _bundleContext.createFilter(s);
	}

	@Override
	public ServiceReference<?>[] getAllServiceReferences(
			String clazz, String filterString)
		throws InvalidSyntaxException {

		return _bundleContext.getAllServiceReferences(clazz, filterString);
	}

	@Override
	public Bundle getBundle() {
		return _bundleContext.getBundle();
	}

	@Override
	public Bundle getBundle(long id) {
		return _bundleContext.getBundle(id);
	}

	@Override
	public Bundle getBundle(String location) {
		return _bundleContext.getBundle(location);
	}

	@Override
	public Bundle[] getBundles() {
		return _bundleContext.getBundles();
	}

	@Override
	public File getDataFile(String fileName) {
		return _bundleContext.getDataFile(fileName);
	}

	@Override
	public String getProperty(String key) {
		return _bundleContext.getProperty(key);
	}

	@Override
	public <S> S getService(ServiceReference<S> serviceReference) {
		AtomicInteger serviceReferenceCount = _serviceReferenceCountsMap.get(
			serviceReference);

		if (serviceReferenceCount == null) {
			serviceReferenceCount = new AtomicInteger(0);

			AtomicInteger previousServiceReferenceCount =
				_serviceReferenceCountsMap.putIfAbsent(
					serviceReference, serviceReferenceCount);

			if (previousServiceReferenceCount != null) {
				serviceReferenceCount = previousServiceReferenceCount;
			}
		}

		serviceReferenceCount.incrementAndGet();

		return _bundleContext.getService(serviceReference);
	}

	@Override
	public <S> ServiceReference<S> getServiceReference(Class<S> clazz) {
		return _bundleContext.getServiceReference(clazz);
	}

	@Override
	public ServiceReference<?> getServiceReference(String clazz) {
		return _bundleContext.getServiceReference(clazz);
	}

	public Map<ServiceReference<?>, AtomicInteger>
		getServiceReferenceCountsMap() {

			return _serviceReferenceCountsMap;
	}

	@Override
	public <S> Collection<ServiceReference<S>> getServiceReferences(
			Class<S> clazz, String filterString)
		throws InvalidSyntaxException {

		return _bundleContext.getServiceReferences(clazz, filterString);
	}

	@Override
	public ServiceReference<?>[] getServiceReferences(
			String clazz, String filterString)
		throws InvalidSyntaxException {

		return _bundleContext.getServiceReferences(clazz, filterString);
	}

	@Override
	public Bundle installBundle(String location) throws BundleException {
		return _bundleContext.installBundle(location);
	}

	@Override
	public Bundle installBundle(String location, InputStream inputStream)
		throws BundleException {

		return _bundleContext.installBundle(location, inputStream);
	}

	@Override
	public <S> ServiceRegistration<S> registerService(
		Class<S> clazz, S service, Dictionary<String, ?> properties) {

		return _bundleContext.registerService(clazz, service, properties);
	}

	@Override
	public ServiceRegistration<?> registerService(
		String clazz, Object service, Dictionary<String, ?> properties) {

		return _bundleContext.registerService(clazz, service, properties);
	}

	@Override
	public ServiceRegistration<?> registerService(
		String[] classes, Object service, Dictionary<String, ?> properties) {

		return _bundleContext.registerService(classes, service, properties);
	}

	@Override
	public void removeBundleListener(BundleListener bundleListener) {
		_bundleContext.removeBundleListener(bundleListener);
	}

	@Override
	public void removeFrameworkListener(FrameworkListener frameworkListener) {
		_bundleContext.removeFrameworkListener(frameworkListener);
	}

	@Override
	public void removeServiceListener(ServiceListener serviceListener) {
		_bundleContext.removeServiceListener(serviceListener);
	}

	@Override
	public boolean ungetService(ServiceReference<?> serviceReference) {
		AtomicInteger serviceReferenceCount = _serviceReferenceCountsMap.get(
			serviceReference);

		if (serviceReferenceCount != null) {
			serviceReferenceCount.decrementAndGet();
		}

		return _bundleContext.ungetService(serviceReference);
	}

	private final BundleContext _bundleContext;
	private final ConcurrentMap<ServiceReference<?>, AtomicInteger>
		_serviceReferenceCountsMap = new ConcurrentHashMap<>();

}