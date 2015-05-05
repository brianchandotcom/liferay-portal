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

package com.liferay.osgi.service.tracker.map;

import com.liferay.osgi.service.tracker.map.internal.DefaultServiceTrackerCustomizer;
import com.liferay.osgi.service.tracker.map.internal.MultiValueServiceTrackerBucketFactory;
import com.liferay.osgi.service.tracker.map.internal.ServiceTrackerMapImpl;
import com.liferay.osgi.service.tracker.map.internal.SingleValueServiceTrackerBucketFactory;

import java.util.Comparator;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Carlos Sierra Andrés
 */
public class ServiceTrackerMapFactory {

	public static <S> ServiceTrackerMap<String, List<S>> multiValueMap(
			BundleContext bundleContext, Class<S> clazz, String propertyKey)
		throws InvalidSyntaxException {

		return new ServiceTrackerMapImpl<>(
			bundleContext, clazz, "(" + propertyKey + "=*)",
			new PropertyServiceReferenceMapper<String, S>(propertyKey),
			new DefaultServiceTrackerCustomizer<S>(bundleContext),
			new MultiValueServiceTrackerBucketFactory<S, S>());
	}

	public static <K, S> ServiceTrackerMap<K, List<S>> multiValueMap(
			BundleContext bundleContext, Class<S> clazz, String filterString,
			ServiceReferenceMapper<K, ? super S> serviceReferenceMapper)
		throws InvalidSyntaxException {

		return new ServiceTrackerMapImpl<>(
			bundleContext, clazz, filterString, serviceReferenceMapper,
			new DefaultServiceTrackerCustomizer<S>(bundleContext),
			new MultiValueServiceTrackerBucketFactory<S, S>());
	}

	public static <K, S> ServiceTrackerMap<K, List<S>> multiValueMap(
			BundleContext bundleContext, Class<S> clazz, String filterString,
			ServiceReferenceMapper<K, ? super S> serviceReferenceMapper,
			Comparator<ServiceReference<S>> comparator)
		throws InvalidSyntaxException {

		return new ServiceTrackerMapImpl<>(
			bundleContext, clazz, filterString, serviceReferenceMapper,
			new DefaultServiceTrackerCustomizer<S>(bundleContext),
			new MultiValueServiceTrackerBucketFactory<S, S>(comparator));
	}

	public static <K, SR, S> ServiceTrackerMap<K, List<S>> multiValueMap(
			BundleContext bundleContext, Class<SR> clazz, String filterString,
			ServiceReferenceMapper<K, ? super SR> serviceReferenceMapper,
			ServiceTrackerCustomizer<SR, S> serviceTrackerCustomizer)
		throws InvalidSyntaxException {

		return new ServiceTrackerMapImpl<>(
			bundleContext, clazz, filterString, serviceReferenceMapper,
			serviceTrackerCustomizer,
			new MultiValueServiceTrackerBucketFactory<SR, S>());
	}

	public static <K, SR, S> ServiceTrackerMap<K, List<S>> multiValueMap(
			BundleContext bundleContext, Class<SR> clazz, String filterString,
			ServiceReferenceMapper<K, ? super SR> serviceReferenceMapper,
			ServiceTrackerCustomizer<SR, S> serviceTrackerCustomizer,
			Comparator<ServiceReference<SR>> comparator)
		throws InvalidSyntaxException {

		return new ServiceTrackerMapImpl<>(
			bundleContext, clazz, filterString, serviceReferenceMapper,
			serviceTrackerCustomizer,
			new MultiValueServiceTrackerBucketFactory<SR, S>(comparator));
	}

	public static <SR, S> ServiceTrackerMap<String, List<S>> multiValueMap(
			BundleContext bundleContext, Class<SR> clazz, String propertyKey,
			ServiceTrackerCustomizer<SR, S> serviceTrackerCustomizer)
		throws InvalidSyntaxException {

		return new ServiceTrackerMapImpl<>(
			bundleContext, clazz, "(" + propertyKey + "=*)",
			new PropertyServiceReferenceMapper<String, SR>(propertyKey),
			serviceTrackerCustomizer,
			new MultiValueServiceTrackerBucketFactory<SR, S>());
	}

	public static <S> ServiceTrackerMap<String, S> singleValueMap(
			BundleContext bundleContext, Class<S> clazz, String propertyKey)
		throws InvalidSyntaxException {

		return new ServiceTrackerMapImpl<>(
			bundleContext, clazz, "(" + propertyKey + "=*)",
			new PropertyServiceReferenceMapper<String, S>(propertyKey),
			new DefaultServiceTrackerCustomizer<S>(bundleContext),
			new SingleValueServiceTrackerBucketFactory<S, S>());
	}

	public static <K, S> ServiceTrackerMap<K, S> singleValueMap(
			BundleContext bundleContext, Class<S> clazz, String filterString,
			ServiceReferenceMapper<K, ? super S> serviceReferenceMapper)
		throws InvalidSyntaxException {

		return new ServiceTrackerMapImpl<>(
			bundleContext, clazz, filterString, serviceReferenceMapper,
			new DefaultServiceTrackerCustomizer<S>(bundleContext),
			new SingleValueServiceTrackerBucketFactory<S, S>());
	}

	public static <K, S> ServiceTrackerMap<K, S> singleValueMap(
			BundleContext bundleContext, Class<S> clazz, String filterString,
			ServiceReferenceMapper<K, ? super S> serviceReferenceMapper,
			Comparator<ServiceReference<S>> comparator)
		throws InvalidSyntaxException {

		return new ServiceTrackerMapImpl<>(
			bundleContext, clazz, filterString, serviceReferenceMapper,
			new DefaultServiceTrackerCustomizer<S>(bundleContext),
			new SingleValueServiceTrackerBucketFactory<S, S>(comparator));
	}

	public static <K, SR, S> ServiceTrackerMap<K, S> singleValueMap(
			BundleContext bundleContext, Class<SR> clazz, String filterString,
			ServiceReferenceMapper<K, ? super SR> serviceReferenceMapper,
			ServiceTrackerCustomizer<SR, S> serviceTrackerCustomizer)
		throws InvalidSyntaxException {

		return new ServiceTrackerMapImpl<>(
			bundleContext, clazz, filterString, serviceReferenceMapper,
			serviceTrackerCustomizer,
			new SingleValueServiceTrackerBucketFactory<SR, S>());
	}

	public static <K, SR, S> ServiceTrackerMap<K, S> singleValueMap(
			BundleContext bundleContext, Class<SR> clazz, String filterString,
			ServiceReferenceMapper<K, ? super SR> serviceReferenceMapper,
			ServiceTrackerCustomizer<SR, S> serviceTrackerCustomizer,
			Comparator<ServiceReference<SR>> comparator)
		throws InvalidSyntaxException {

		return new ServiceTrackerMapImpl<>(
			bundleContext, clazz, filterString, serviceReferenceMapper,
			serviceTrackerCustomizer,
			new SingleValueServiceTrackerBucketFactory<SR, S>(comparator));
	}

	public static <SR, S> ServiceTrackerMap<String, S> singleValueMap(
			BundleContext bundleContext, Class<SR> clazz, String propertyKey,
			ServiceTrackerCustomizer<SR, S> serviceTrackerCustomizer)
		throws InvalidSyntaxException {

		return new ServiceTrackerMapImpl<>(
			bundleContext, clazz, "(" + propertyKey + "=*)",
			new PropertyServiceReferenceMapper<String, SR>(propertyKey),
			serviceTrackerCustomizer,
			new SingleValueServiceTrackerBucketFactory<SR, S>());
	}

	public static class PropertyServiceReferenceComparator <T>
		implements Comparator<ServiceReference<T>> {

		public PropertyServiceReferenceComparator(String propertyKey) {
			_propertyKey = propertyKey;
		}

		@Override
		public int compare(
			ServiceReference<T> serviceReference1,
			ServiceReference<T> serviceReference2) {

			if (serviceReference1 == null) {
				if (serviceReference2 == null) {
					return 0;
				}
				else {
					return -1;
				}
			}
			else if (serviceReference2 == null) {
				return 1;
			}

			Object propertyValue1 = serviceReference1.getProperty(_propertyKey);

			if (!(propertyValue1 instanceof Comparable)) {
				return -(serviceReference1.compareTo(serviceReference2));
			}

			Comparable<Object> propertyValueComparable1 =
				(Comparable<Object>)propertyValue1;

			Object propertyValue2 = serviceReference2.getProperty(_propertyKey);

			if (propertyValue1 == null) {
				if (propertyValue2 != null) {
					return -1;
				}

				return -(serviceReference1.compareTo(serviceReference2));
			}

			return -(propertyValueComparable1.compareTo(propertyValue2));
		}

		private String _propertyKey;

	}

	public static class PropertyServiceReferenceMapper<T, S>
		implements ServiceReferenceMapper<T, S> {

		public PropertyServiceReferenceMapper(String propertyKey) {
			_propertyKey = propertyKey;
		}

		@Override
		public void map(
			ServiceReference<S> serviceReference, Emitter<T> emitter) {

			Object propertyValue = serviceReference.getProperty(_propertyKey);

			if (propertyValue != null) {
				if (propertyValue instanceof Object[]) {
					for (T t : (T[])propertyValue) {
						emitter.emit(t);
					}
				}
				else {
					emitter.emit((T)propertyValue);
				}
			}
		}

		private String _propertyKey;

	}

}