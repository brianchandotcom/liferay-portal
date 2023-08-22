/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.messaging.internal.jmx;

import com.liferay.osgi.service.tracker.collections.EagerServiceTrackerCustomizer;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapperFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;

import java.util.Dictionary;
import java.util.Set;

import javax.management.DynamicMBean;
import javax.management.NotCompliantMBeanException;
import javax.management.StandardMBean;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
@Component(
	property = {
		"jmx.objectname=com.liferay.portal.messaging:classification=message_bus,name=MessageBusManager",
		"jmx.objectname.cache.key=MessageBusManager"
	},
	service = DynamicMBean.class
)
public class MessageBusManager
	extends StandardMBean implements MessageBusManagerMBean {

	public MessageBusManager() throws NotCompliantMBeanException {
		super(MessageBusManagerMBean.class);
	}

	@Override
	public int getDestinationCount() {
		Set<String> destinationNames = _serviceTrackerMap.keySet();

		return destinationNames.size();
	}

	@Override
	public int getMessageListenerCount(String destinationName) {
		Destination destination = _messageBus.getDestination(destinationName);

		if (destination == null) {
			return 0;
		}

		return destination.getMessageListenerCount();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, Destination.class, "(destination.name=*)",
			ServiceReferenceMapperFactory.create(
				bundleContext,
				(destination, emitter) -> emitter.emit(destination.getName())),
			new EagerServiceTrackerCustomizer
				<Destination, ServiceRegistration<DynamicMBean>>() {

				@Override
				public ServiceRegistration<DynamicMBean> addingService(
					ServiceReference<Destination> serviceReference) {

					ServiceRegistration<DynamicMBean> serviceRegistration =
						null;

					Destination destination = bundleContext.getService(
						serviceReference);

					try {
						DestinationStatisticsManager
							destinationStatisticsManager =
								new DestinationStatisticsManager(destination);

						Dictionary<String, Object> mBeanProperties =
							HashMapDictionaryBuilder.<String, Object>put(
								"jmx.objectname",
								destinationStatisticsManager.getObjectName()
							).put(
								"jmx.objectname.cache.key",
								destinationStatisticsManager.
									getObjectNameCacheKey()
							).build();

						serviceRegistration = bundleContext.registerService(
							DynamicMBean.class, destinationStatisticsManager,
							mBeanProperties);
					}
					catch (NotCompliantMBeanException
								notCompliantMBeanException) {

						if (_log.isInfoEnabled()) {
							_log.info(
								"Unable to register destination mbean",
								notCompliantMBeanException);
						}
					}

					return serviceRegistration;
				}

				@Override
				public void modifiedService(
					ServiceReference<Destination> serviceReference,
					ServiceRegistration<DynamicMBean> serviceRegistration) {
				}

				@Override
				public void removedService(
					ServiceReference<Destination> serviceReference,
					ServiceRegistration<DynamicMBean> serviceRegistration) {

					bundleContext.ungetService(serviceReference);

					serviceRegistration.unregister();
				}

			});
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MessageBusManager.class);

	@Reference
	private MessageBus _messageBus;

	private ServiceTrackerMap<String, ServiceRegistration<DynamicMBean>>
		_serviceTrackerMap;

}