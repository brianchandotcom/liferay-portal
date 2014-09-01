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

package com.liferay.registry.collections;

import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTrackerCustomizer;
import com.liferay.registry.collections.internal.MultiValueServiceTrackerBucketFactory;
import com.liferay.registry.collections.internal.RegistryServiceTrackerCustomizer;
import com.liferay.registry.collections.internal.ServiceTrackerCollectionImpl;
import com.liferay.registry.collections.internal.ServiceTrackerMapImpl;
import com.liferay.registry.collections.internal.SingleValueServiceTrackerBucketFactory;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Raymond Augé
 */
public class ServiceTrackerCollections {

	public static <S> ServiceTrackerList<S> list(Class<S> clazz) {
		return new ServiceTrackerCollectionImpl<S>(
			clazz, (Filter)null, null, new HashMap<String, Object>());
	}

	public static <S> ServiceTrackerList<S> list(
		Class<S> clazz, Filter filter) {

		return new ServiceTrackerCollectionImpl<S>(
			clazz, filter, null, new HashMap<String, Object>());
	}

	public static <S> ServiceTrackerList<S> list(
		Class<S> clazz, Filter filter, Map<String, Object> properties) {

		return new ServiceTrackerCollectionImpl<S>(
			clazz, filter, null, properties);
	}

	public static <S> ServiceTrackerList<S> list(
		Class<S> clazz, Filter filter,
		ServiceTrackerCustomizer<S, S> serviceTrackerCustomizer) {

		return new ServiceTrackerCollectionImpl<S>(
			clazz, filter, serviceTrackerCustomizer,
			new HashMap<String, Object>());
	}

	public static <S> ServiceTrackerList<S> list(
		Class<S> clazz, Filter filter,
		ServiceTrackerCustomizer<S, S> serviceTrackerCustomizer,
		Map<String, Object> properties) {

		return new ServiceTrackerCollectionImpl<S>(
			clazz, filter, serviceTrackerCustomizer, properties);
	}

	public static <S> ServiceTrackerList<S> list(
		Class<S> clazz, Map<String, Object> properties) {

		return new ServiceTrackerCollectionImpl<S>(
			clazz, (Filter)null, null, properties);
	}

	public static <S> ServiceTrackerList<S> list(
		Class<S> clazz,
		ServiceTrackerCustomizer<S, S> serviceTrackerCustomizer) {

		return new ServiceTrackerCollectionImpl<S>(
			clazz, (Filter)null, serviceTrackerCustomizer,
			new HashMap<String, Object>());
	}

	public static <S> ServiceTrackerList<S> list(
		Class<S> clazz, ServiceTrackerCustomizer<S, S> serviceTrackerCustomizer,
		Map<String, Object> properties) {

		return new ServiceTrackerCollectionImpl<S>(
			clazz, (Filter)null, serviceTrackerCustomizer, properties);
	}

	public static <S> ServiceTrackerList<S> list(
		Class<S> clazz, String filterString) {

		return new ServiceTrackerCollectionImpl<S>(
			clazz, _getFilter(filterString), null,
			new HashMap<String, Object>());
	}

	public static <S> ServiceTrackerList<S> list(
		Class<S> clazz, String filterString, Map<String, Object> properties) {

		return new ServiceTrackerCollectionImpl<S>(
			clazz, _getFilter(filterString), null, properties);
	}

	public static <S> ServiceTrackerList<S> list(
		Class<S> clazz, String filterString,
		ServiceTrackerCustomizer<S, S> serviceTrackerCustomizer) {

		return new ServiceTrackerCollectionImpl<S>(
			clazz, _getFilter(filterString), serviceTrackerCustomizer,
			new HashMap<String, Object>());
	}

	public static <S> ServiceTrackerList<S> list(
		Class<S> clazz, String filterString,
		ServiceTrackerCustomizer<S, S> serviceTrackerCustomizer,
		Map<String, Object> properties) {

		return new ServiceTrackerCollectionImpl<S>(
			clazz, _getFilter(filterString), serviceTrackerCustomizer,
			properties);
	}

	public static <S> ServiceTrackerMap<String, List<S>> multiValueMap(
		Class<S> clazz, String propertyKey) {

		return new ServiceTrackerMapImpl<String, S, S, List<S>>(
			clazz,"(" + propertyKey + "=*)",
			new PropertyServiceReferenceMapper<String, S>(propertyKey),
			new RegistryServiceTrackerCustomizer<S>(),
			new MultiValueServiceTrackerBucketFactory<S, S>());
	}

	public static <K, S> ServiceTrackerMap<K, List<S>> multiValueMap(
		Class<S> clazz, String filterString,
		ServiceReferenceMapper<K, S> serviceReferenceMapper) {

		return new ServiceTrackerMapImpl<K, S, S, List<S>>(
			clazz, filterString, serviceReferenceMapper,
			new RegistryServiceTrackerCustomizer<S>(),
			new MultiValueServiceTrackerBucketFactory<S, S>());
	}

	public static <K, S> ServiceTrackerMap<K, List<S>> multiValueMap(
		Class<S> clazz, String filterString,
		ServiceReferenceMapper<K, S> serviceReferenceMapper,
		Comparator<ServiceReference<S>> comparator) {

		return new ServiceTrackerMapImpl<K, S, S, List<S>>(
			clazz, filterString, serviceReferenceMapper,
			new RegistryServiceTrackerCustomizer<S>(),
			new MultiValueServiceTrackerBucketFactory<S, S>(comparator));
	}

	public static <S> ServiceTrackerMap<String, S> singleValueMap(
		Class<S> clazz, String propertyKey) {

		return new ServiceTrackerMapImpl<String, S, S, S>(
			clazz, "(" + propertyKey + "=*)",
			new PropertyServiceReferenceMapper<String, S>(propertyKey),
			new RegistryServiceTrackerCustomizer<S>(),
			new SingleValueServiceTrackerBucketFactory<S, S>());
	}

	public static <K, S> ServiceTrackerMap<K, S> singleValueMap(
		Class<S> clazz, String filterString,
		ServiceReferenceMapper<K, S> serviceReferenceMapper) {

		return new ServiceTrackerMapImpl<K, S, S, S>(
			clazz, filterString, serviceReferenceMapper,
			new RegistryServiceTrackerCustomizer<S>(),
			new SingleValueServiceTrackerBucketFactory<S, S>());
	}

	public static <K, S> ServiceTrackerMap<K, S> singleValueMap(
		Class<S> clazz, String filterString,
		ServiceReferenceMapper<K, S> serviceReferenceMapper,
		Comparator<ServiceReference<S>> comparator) {

		return new ServiceTrackerMapImpl<K, S, S, S>(
			clazz, filterString, serviceReferenceMapper,
			new RegistryServiceTrackerCustomizer<S>(),
			new SingleValueServiceTrackerBucketFactory<S, S>(comparator));
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

			T propertyValue = (T)serviceReference.getProperty(_propertyKey);

			if (propertyValue != null) {
				emitter.emit(propertyValue);
			}
		}

		private String _propertyKey;

	}

	private static Filter _getFilter(String filterString) {
		Registry registry = RegistryUtil.getRegistry();

		return registry.getFilter(filterString);
	}

	private ServiceTrackerCollections() {
	}

}