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

import com.liferay.registry.ServiceReference;

import java.util.Comparator;
import java.util.List;

/**
 * @author Carlos Sierra Andrés
 */
public class ServiceTrackerMapFactory {

	public static <S> ServiceTrackerMap<String, List<S>>
		createListServiceTracker(Class<S> clazz, String propertyKey) {

		return new ServiceTrackerMapImpl<String, S, List<S>>(
			clazz,"(" + propertyKey + "=*)",
			new PropertyServiceReferenceMapper<String>(propertyKey),
			new ListServiceTrackerBucketFactory<S>(
				new PropertyServiceReferenceComparator<S>("service.ranking")));
	}

	public static <K, S> ServiceTrackerMap<K, List<S>>
		createListServiceTrackerMap(
			Class<S> clazz, String filterString,
			ServiceReferenceMapper<K> serviceReferenceMapper) {

		return new ServiceTrackerMapImpl<K, S, List<S>>(
			clazz, filterString, serviceReferenceMapper,
			new ListServiceTrackerBucketFactory<S>(
				new PropertyServiceReferenceComparator<S>("service.ranking")));
	}

	public static <K, S> ServiceTrackerMap<K, List<S>>
		createListServiceTrackerMap(
			Class<S> clazz, String filterString,
			ServiceReferenceMapper<K> serviceReferenceMapper,
			Comparator<ServiceReference<S>> comparator) {

		return new ServiceTrackerMapImpl<K, S, List<S>>(
			clazz, filterString, serviceReferenceMapper,
				new ListServiceTrackerBucketFactory<S>(comparator));
	}

	public static <S> ServiceTrackerMap<String, S>
		createObjectServiceTrackerMap(Class<S> clazz, String propertyKey) {

		return new ServiceTrackerMapImpl<String, S, S>(
			clazz, "(" + propertyKey + "=*)",
			new PropertyServiceReferenceMapper<String>(propertyKey),
			new ObjectServiceTrackerBucketFactory<S>(
				new PropertyServiceReferenceComparator<S>("service.ranking")));
	}

	public static <K, S> ServiceTrackerMap<K, S> createObjectServiceTrackerMap(
		Class<S> clazz, String filterString,
		ServiceReferenceMapper<K> serviceReferenceMapper) {

		return new ServiceTrackerMapImpl<K, S, S>(
			clazz, filterString, serviceReferenceMapper,
			new ObjectServiceTrackerBucketFactory<S>(
				new PropertyServiceReferenceComparator<S>("service.ranking")));
	}

	public static <K, S> ServiceTrackerMap<K, S> createObjectServiceTrackerMap(
		Class<S> clazz, String filterString,
		ServiceReferenceMapper<K> serviceReferenceMapper,
		Comparator<ServiceReference<S>> comparator) {

		return new ServiceTrackerMapImpl<K, S, S>(
			clazz, filterString, serviceReferenceMapper,
			new ObjectServiceTrackerBucketFactory<S>(comparator));
	}

	private static class PropertyServiceReferenceComparator <T>
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
				return 0;
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

	private static class PropertyServiceReferenceMapper <T>
		implements ServiceReferenceMapper<T> {

		public PropertyServiceReferenceMapper(String propertyKey) {
			_propertyKey = propertyKey;
		}

		@Override
		public void map(
			ServiceReference<?> serviceReference, Emitter<T> emitter) {

			T propertyValue = (T)serviceReference.getProperty(_propertyKey);

			if (propertyValue != null) {
				emitter.emit(propertyValue);
			}
		}

		private String _propertyKey;

	}

}