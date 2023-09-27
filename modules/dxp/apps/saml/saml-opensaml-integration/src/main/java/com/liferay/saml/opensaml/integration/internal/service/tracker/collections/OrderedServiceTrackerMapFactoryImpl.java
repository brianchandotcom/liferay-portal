/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.opensaml.integration.internal.service.tracker.collections;

import com.liferay.osgi.service.tracker.collections.ServiceReferenceServiceTuple;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Component;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Stian Sigvartsen
 */
@Component(service = OrderedServiceTrackerMapFactory.class)
public class OrderedServiceTrackerMapFactoryImpl
	implements OrderedServiceTrackerMapFactory {

	@Override
	public <T> OrderedServiceTrackerMap<T> create(
		BundleContext bundleContext, Class<T> clazz, String propertyKey) {

		return new OrderedServiceTrackerMapImpl<>(
			bundleContext, clazz, "(default=true)", propertyKey);
	}

	private static class OrderedServiceTrackerMapImpl<T>
		implements OrderedServiceTrackerMap<T> {

		@Override
		public void close() {
			_serviceTrackerMap.close();
		}

		@Override
		public String getDefaultServiceKey() {
			ServiceReference<T> serviceReference =
				_defaultServiceTracker.getServiceReference();

			if (serviceReference == null) {
				return StringPool.BLANK;
			}

			return GetterUtil.getString(
				serviceReference.getProperty(_propertyKey));
		}

		@Override
		public List<Map.Entry<String, T>> getOrderedServices() {
			Set<String> prefixes = _serviceTrackerMap.keySet();

			List<ServiceReferenceServiceTuple<T, T>>
				serviceReferenceServiceTuples = TransformUtil.transform(
					prefixes, _serviceTrackerMap::getService);

			Collections.sort(
				serviceReferenceServiceTuples,
				Comparator.comparing(
					serviceReferenceServiceTuple -> {
						ServiceReference<?> serviceReference =
							serviceReferenceServiceTuple.getServiceReference();

						return GetterUtil.getInteger(
							serviceReference.getProperty("display.index"));
					}));

			return TransformUtil.transform(
				serviceReferenceServiceTuples,
				serviceReferenceServiceTuple ->
					new AbstractMap.SimpleEntry<String, T>(
						(String)
							serviceReferenceServiceTuple.getServiceReference(
							).getProperty(
								_propertyKey
							),
						serviceReferenceServiceTuple.getService()));
		}

		@Override
		public List<String> getOrderedServicesKeys() {
			Set<String> prefixes = _serviceTrackerMap.keySet();

			List<ServiceReference<T>> serviceReferences = new ArrayList<>();

			for (String prefix : prefixes) {
				ServiceReferenceServiceTuple<T, T>
					curServiceReferenceServiceTuple =
						_serviceTrackerMap.getService(prefix);

				serviceReferences.add(
					curServiceReferenceServiceTuple.getServiceReference());
			}

			Collections.sort(
				serviceReferences,
				Comparator.comparing(
					serviceReference -> GetterUtil.getInteger(
						serviceReference.getProperty("display.index"))));

			return TransformUtil.transform(
				serviceReferences,
				serviceReference -> GetterUtil.getString(
					serviceReference.getProperty(_propertyKey)));
		}

		@Override
		public T getService(String key) {
			ServiceReferenceServiceTuple<T, T> service =
				_serviceTrackerMap.getService(key);

			if (service != null) {
				return service.getService();
			}

			return _defaultServiceTracker.getService();
		}

		@Override
		public Set<String> getServicesKeys() {
			return _serviceTrackerMap.keySet();
		}

		private OrderedServiceTrackerMapImpl(
			BundleContext bundleContext, Class<T> clazz,
			String defaultServiceFilter, String propertyKey) {

			_propertyKey = propertyKey;

			_defaultServiceTracker = ServiceTrackerFactory.open(
				bundleContext,
				StringBundler.concat(
					"(&(objectClass=", clazz.getName(), ")",
					defaultServiceFilter, ")"));
			_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, clazz, propertyKey,
				ServiceTrackerCustomizerFactory.serviceReferenceServiceTuple(
					bundleContext));
		}

		private final ServiceTracker<T, T> _defaultServiceTracker;
		private final String _propertyKey;
		private final ServiceTrackerMap
			<String, ServiceReferenceServiceTuple<T, T>> _serviceTrackerMap;

	}

}