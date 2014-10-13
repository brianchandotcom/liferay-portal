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

package com.liferay.portal.poller;

import com.liferay.portal.dao.shard.ShardPollerProcessorWrapper;
import com.liferay.portal.kernel.dao.shard.ShardUtil;
import com.liferay.portal.kernel.nio.intraband.RegistrationReference;
import com.liferay.portal.kernel.nio.intraband.proxy.TargetLocator;
import com.liferay.portal.kernel.poller.PollerProcessor;
import com.liferay.portal.nio.intraband.proxy.IntrabandProxyInstallationUtil;
import com.liferay.portal.nio.intraband.proxy.IntrabandProxyUtil;
import com.liferay.portal.nio.intraband.proxy.StubHolder.StubCreator;
import com.liferay.portal.nio.intraband.proxy.StubMap;
import com.liferay.portal.nio.intraband.proxy.WarnLogExceptionHandler;
import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;
import com.liferay.registry.collections.StringServiceRegistrationMap;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * @author Brian Wing Shun Chan
 */
public class PollerProcessorUtil {

	public static void addPollerProcessor(
		String portletId, PollerProcessor pollerProcessor) {

		_instance._addPollerProcessor(portletId, pollerProcessor);
	}

	public static void deletePollerProcessor(String portletId) {
		_instance._deletePollerProcessor(portletId);
	}

	public static PollerProcessor getPollerProcessor(String portletId) {
		return _instance._getPollerProcessor(portletId);
	}

	private PollerProcessorUtil() {
		Registry registry = RegistryUtil.getRegistry();

		Filter filter = registry.getFilter(
			"(&(javax.portlet.name=*)(objectClass=" +
				PollerProcessor.class.getName() + "))");

		_serviceTracker = registry.trackServices(
			filter, new PollerProcessorServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	private void _addPollerProcessor(
		String portletId, PollerProcessor pollerProcessor) {

		Registry registry = RegistryUtil.getRegistry();

		Map<String, Object> properties = new HashMap<String, Object>();

		properties.put("javax.portlet.name", portletId);

		ServiceRegistration<PollerProcessor> serviceRegistration =
			registry.registerService(
				PollerProcessor.class, pollerProcessor, properties);

		_serviceRegistrations.put(portletId, serviceRegistration);
	}

	private void _deletePollerProcessor(String portletId) {
		ServiceRegistration<PollerProcessor> serviceRegistration =
			_serviceRegistrations.remove(portletId);

		if (serviceRegistration != null) {
			serviceRegistration.unregister();
		}
	}

	private PollerProcessor _getPollerProcessor(String portletId) {
		return _pollerPorcessors.get(portletId);
	}

	private static final PollerProcessorUtil _instance =
		new PollerProcessorUtil();

	private final StubMap<PollerProcessor> _pollerPorcessors =
		new StubMap<PollerProcessor>(
			new StubCreator<PollerProcessor>() {

				@Override
				public PollerProcessor createStub(
						String portletId, PollerProcessor pollerProcessor,
						RegistrationReference registrationReference)
					throws Exception {

					Future<String[]> skeletonProxyMethodSignaturesFuture =
						IntrabandProxyInstallationUtil.installSkeleton(
							registrationReference, PollerProcessor.class,
							new PollerProcessorTargetLocator());

					String[] skeletonProxyMethodSignatures =
						skeletonProxyMethodSignaturesFuture.get();

					Class<? extends PollerProcessor>
						stubPollerClass =
							(Class<? extends PollerProcessor>)
								IntrabandProxyUtil.getStubClass(
									PollerProcessor.class,
									PollerProcessor.class.getName());

					IntrabandProxyInstallationUtil.checkProxyMethodSignatures(
						skeletonProxyMethodSignatures,
						IntrabandProxyUtil.getProxyMethodSignatures(
							stubPollerClass));

					return IntrabandProxyUtil.newStubInstance(
						stubPollerClass, portletId, registrationReference,
						WarnLogExceptionHandler.INSTANCE);
				}

				@Override
				public PollerProcessor onCreationFailure(
					String portletId, PollerProcessor pollerProcessor,
					Exception e) {

					return pollerProcessor;
				}

				@Override
				public PollerProcessor onInvalidation(
					String portletId, PollerProcessor pollerProcessor,
					PollerProcessor stubPollerProcessor) {

					_pollerPorcessors.removeStubHolder(
						portletId, stubPollerProcessor);

					return pollerProcessor;
				}

			});

	private final StringServiceRegistrationMap<PollerProcessor>
		_serviceRegistrations =
			new StringServiceRegistrationMap<PollerProcessor>();
	private final ServiceTracker<PollerProcessor, PollerProcessor>
		_serviceTracker;

	private static class PollerProcessorTargetLocator implements TargetLocator {

		@Override
		public Object getTarget(String portletId) {
			PollerProcessor pollerProcessor =
				PollerProcessorUtil.getPollerProcessor(portletId);

			if (pollerProcessor == null) {
				throw new IllegalStateException(
					"Unable to get poller processor for portlet " + portletId);
			}

			return pollerProcessor;
		}

	}

	private class PollerProcessorServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<PollerProcessor, PollerProcessor> {

		@Override
		public PollerProcessor addingService(
			ServiceReference<PollerProcessor> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			PollerProcessor pollerProcessor = registry.getService(
				serviceReference);

			if (ShardUtil.isEnabled()) {
				pollerProcessor = new ShardPollerProcessorWrapper(
					pollerProcessor);
			}

			String portletId = (String)serviceReference.getProperty(
				"javax.portlet.name");

			_pollerPorcessors.put(portletId, pollerProcessor);

			return pollerProcessor;
		}

		@Override
		public void modifiedService(
			ServiceReference<PollerProcessor> serviceReference,
			PollerProcessor pollerProcessor) {
		}

		@Override
		public void removedService(
			ServiceReference<PollerProcessor> serviceReference,
			PollerProcessor pollerProcessor) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			String portletId = (String)serviceReference.getProperty(
				"javax.portlet.name");

			_pollerPorcessors.remove(portletId);
		}

	}

}