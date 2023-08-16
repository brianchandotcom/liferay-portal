/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.messaging.internal;

import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageBusInterceptor;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.messaging.internal.configuration.DestinationWorkerConfiguration;

import java.util.Dictionary;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedServiceFactory;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
@Component(service = MessageBus.class)
public class DefaultMessageBus implements MessageBus {

	@Override
	public Destination getDestination(String destinationName) {
		return _destinations.get(destinationName);
	}

	@Override
	public void sendMessage(String destinationName, Message message) {
		MessageBusThreadLocalUtil.populateMessageFromThreadLocals(message);

		for (MessageBusInterceptor messageBusInterceptor :
				_serviceTrackerList) {

			if (messageBusInterceptor.intercept(
					this, destinationName, message)) {

				return;
			}
		}

		Destination destination = _destinations.get(destinationName);

		if (destination == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Destination " + destinationName + " is not configured");
			}

			return;
		}

		message.setDestinationName(destinationName);

		if (message.get("companyId") == null) {
			Long[] companyIds = (Long[])message.get("companyIds");

			if (companyIds != null) {
				long orignalCompanyId = CompanyThreadLocal.getCompanyId();

				try {
					for (Long id : companyIds) {
						CompanyThreadLocal.setCompanyId(id);

						message.put("companyId", id);

						destination.send(message.clone());
					}
				}
				finally {
					CompanyThreadLocal.setCompanyId(orignalCompanyId);
				}

				return;
			}
		}

		destination.send(message);
	}

	@Override
	public void shutdown() {
		shutdown(false);
	}

	@Override
	public synchronized void shutdown(boolean force) {
		for (Destination destination : _destinations.values()) {
			destination.close(force);
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceRegistration = bundleContext.registerService(
			ManagedServiceFactory.class,
			new DefaultMessageBusManagedServiceFactory(),
			HashMapDictionaryBuilder.put(
				Constants.SERVICE_PID,
				"com.liferay.portal.messaging.internal.configuration." +
					"DestinationWorkerConfiguration"
			).build());

		_serviceTrackerList = ServiceTrackerListFactory.open(
			bundleContext, MessageBusInterceptor.class);
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerList.close();

		_serviceRegistration.unregister();

		shutdown(true);

		for (Destination destination : _destinations.values()) {
			destination.destroy();
		}

		_destinations.clear();
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(destination.name=*)"
	)
	protected synchronized void registerDestination(
		Destination destination, Map<String, Object> properties) {

		String destinationName = MapUtil.getString(
			properties, "destination.name");

		_addDestination(destination);

		DestinationWorkerConfiguration destinationWorkerConfiguration =
			_destinationWorkerConfigurations.get(destinationName);

		_updateDestination(destination, destinationWorkerConfiguration);
	}

	protected synchronized void unregisterDestination(
		Destination destination, Map<String, Object> properties) {

		_removeDestination(destination.getName());
	}

	private void _addDestination(Destination destination) {
		Destination oldDestination = _destinations.get(destination.getName());

		destination.open();

		_destinations.put(destination.getName(), destination);

		if (oldDestination != null) {
			oldDestination.destroy();
		}
	}

	private Destination _removeDestination(String destinationName) {
		Destination destination = _destinations.remove(destinationName);

		if (destination == null) {
			return null;
		}

		destination.destroy();

		return destination;
	}

	private void _updateDestination(
		Destination destination,
		DestinationWorkerConfiguration destinationWorkerConfiguration) {

		if ((destination == null) || (destinationWorkerConfiguration == null)) {
			return;
		}

		if (destination instanceof BaseAsyncDestination) {
			BaseAsyncDestination baseAsyncDestination =
				(BaseAsyncDestination)destination;

			baseAsyncDestination.setMaximumQueueSize(
				destinationWorkerConfiguration.maxQueueSize());
			baseAsyncDestination.setWorkersSize(
				destinationWorkerConfiguration.workerCoreSize(),
				destinationWorkerConfiguration.workerMaxSize());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DefaultMessageBus.class);

	private final Map<String, Destination> _destinations =
		new ConcurrentHashMap<>();
	private final Map<String, DestinationWorkerConfiguration>
		_destinationWorkerConfigurations = new ConcurrentHashMap<>();
	private final Map<String, String> _factoryPidsToDestinationNames =
		new ConcurrentHashMap<>();
	private ServiceRegistration<ManagedServiceFactory> _serviceRegistration;
	private ServiceTrackerList<MessageBusInterceptor> _serviceTrackerList;

	private class DefaultMessageBusManagedServiceFactory
		implements ManagedServiceFactory {

		@Override
		public void deleted(String factoryPid) {
			String destinationName = _factoryPidsToDestinationNames.remove(
				factoryPid);

			_destinationWorkerConfigurations.remove(destinationName);
		}

		@Override
		public String getName() {
			return "Default Message Bus";
		}

		@Override
		public void updated(String factoryPid, Dictionary<String, ?> dictionary)
			throws ConfigurationException {

			DestinationWorkerConfiguration destinationWorkerConfiguration =
				ConfigurableUtil.createConfigurable(
					DestinationWorkerConfiguration.class, dictionary);

			_factoryPidsToDestinationNames.put(
				factoryPid, destinationWorkerConfiguration.destinationName());

			_destinationWorkerConfigurations.put(
				destinationWorkerConfiguration.destinationName(),
				destinationWorkerConfiguration);

			Destination destination = _destinations.get(
				destinationWorkerConfiguration.destinationName());

			_updateDestination(destination, destinationWorkerConfiguration);
		}

	}

}