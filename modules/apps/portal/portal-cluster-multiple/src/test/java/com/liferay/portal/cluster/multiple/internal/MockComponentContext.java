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

package com.liferay.portal.cluster.multiple.internal;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.PortalInetSocketAddressEventListener;

import java.io.File;
import java.io.InputStream;

import java.util.Collection;
import java.util.Dictionary;

import org.junit.Assert;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleListener;
import org.osgi.framework.Filter;
import org.osgi.framework.FrameworkListener;
import org.osgi.framework.ServiceFactory;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceObjects;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.ComponentInstance;

/**
 * @author Michael C. Han
 */
public class MockComponentContext implements ComponentContext {

	public MockComponentContext(Dictionary<String, Object> properties) {
		_properties = properties;
	}

	@Override
	public void disableComponent(String componentName) {
	}

	@Override
	public void enableComponent(String componentName) {
	}

	@Override
	public BundleContext getBundleContext() {
		return new MockBundleContext();
	}

	@Override
	public ComponentInstance getComponentInstance() {
		return null;
	}

	@Override
	public Dictionary<String, Object> getProperties() {
		return _properties;
	}

	@Override
	public ServiceReference<?> getServiceReference() {
		return null;
	}

	@Override
	public Bundle getUsingBundle() {
		return null;
	}

	@Override
	public Object locateService(String serviceName) {
		return null;
	}

	@Override
	public Object locateService(
		String serviceName,
		@SuppressWarnings("rawtypes") ServiceReference serviceReference) {

		return null;
	}

	@Override
	public Object[] locateServices(String serviceName) {
		return null;
	}

	private final Dictionary<String, Object> _properties;

	private class MockBundleContext implements BundleContext {

		@Override
		public void addBundleListener(BundleListener bundleListener) {
		}

		@Override
		public void addFrameworkListener(FrameworkListener frameworkListener) {
		}

		@Override
		public void addServiceListener(ServiceListener serviceListener) {
		}

		@Override
		public void addServiceListener(
			ServiceListener serviceListener, String serviceName) {
		}

		@Override
		public Filter createFilter(String filterString) {
			return null;
		}

		@Override
		public ServiceReference<?>[] getAllServiceReferences(
			String serviceName, String filterString) {

			return null;
		}

		@Override
		public Bundle getBundle() {
			return null;
		}

		@Override
		public Bundle getBundle(long bundleId) {
			return null;
		}

		@Override
		public Bundle getBundle(String bundleName) {
			return null;
		}

		@Override
		public Bundle[] getBundles() {
			return null;
		}

		@Override
		public File getDataFile(String fileName) {
			return null;
		}

		@Override
		public String getProperty(String key) {
			return StringPool.BLANK;
		}

		@Override
		public <S> S getService(ServiceReference<S> serviceReference) {
			return null;
		}

		@Override
		public <S> ServiceObjects<S> getServiceObjects(
			ServiceReference<S> serviceReference) {

			return null;
		}

		@Override
		public <S> ServiceReference<S> getServiceReference(Class<S> clazz) {
			return null;
		}

		@Override
		public ServiceReference<?> getServiceReference(String serviceName) {
			return null;
		}

		@Override
		public <S> Collection<ServiceReference<S>> getServiceReferences(
			Class<S> clazz, String serviceName) {

			return null;
		}

		@Override
		public ServiceReference<?>[] getServiceReferences(
			String serviceName, String filterString) {

			return null;
		}

		@Override
		public Bundle installBundle(String bundleName) {
			return null;
		}

		@Override
		public Bundle installBundle(
			String bundleName, InputStream inputStream) {

			return null;
		}

		@Override
		public <S> ServiceRegistration<S> registerService(
			Class<S> clazz, S object, Dictionary<String, ?> dictionary) {

			Assert.assertEquals(
				PortalInetSocketAddressEventListener.class, clazz);
			Assert.assertNotNull(object);
			Assert.assertTrue(dictionary.isEmpty());

			return new ServiceRegistration<S>() {

				@Override
				public ServiceReference<S> getReference() {
					return null;
				}

				@Override
				public void setProperties(Dictionary<String, ?> dictionary) {
				}

				@Override
				public void unregister() {
				}

			};
		}

		@Override
		public <S> ServiceRegistration<S> registerService(
			Class<S> clazz, ServiceFactory<S> serviceFactory,
			Dictionary<String, ?> dictionary) {

			return null;
		}

		@Override
		public ServiceRegistration<?> registerService(
			String classNames, Object object,
			Dictionary<String, ?> dictionary) {

			return null;
		}

		@Override
		public ServiceRegistration<?> registerService(
			String[] classNames, Object object,
			Dictionary<String, ?> dictionary) {

			return null;
		}

		@Override
		public void removeBundleListener(BundleListener bundleListener) {
		}

		@Override
		public void removeFrameworkListener(
			FrameworkListener frameworkListener) {
		}

		@Override
		public void removeServiceListener(ServiceListener serviceListener) {
		}

		@Override
		public boolean ungetService(ServiceReference<?> serviceReference) {
			return true;
		}

	}

}