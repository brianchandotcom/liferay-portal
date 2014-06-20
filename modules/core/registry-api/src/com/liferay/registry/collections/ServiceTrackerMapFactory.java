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

	public static <K, S> ServiceTrackerMap<K, List<S>> createListMap(
		Class<S> clazz, String filter,
		ServiceReferenceMapper<K> serviceReferenceMapper) {

		return new ServiceTrackerMapImpl<K, S, List<S>>(
			clazz, filter, serviceReferenceMapper,
			new ListServiceTrackerBucketFactory<S>(
				Comparators.<S>fromProperty("service.ranking")));
	}

	public static <K, S> ServiceTrackerMap<K, List<S>> createListMap(
		Class<S> clazz, String filter,
		ServiceReferenceMapper<K> serviceReferenceMapper,
		Comparator<ServiceReference<S>> comparator) {

		return new ServiceTrackerMapImpl<K, S, List<S>>(
			clazz, filter, serviceReferenceMapper,
				new ListServiceTrackerBucketFactory<S>(comparator));
	}

	public static <S> ServiceTrackerMap<String, List<S>>
		createListMapFromProperty(Class<S> clazz, String property) {

		return new ServiceTrackerMapImpl<String, S, List<S>>(
			clazz,"("+property+"=*)", Mappers.<String>fromProperty(property),
			new ListServiceTrackerBucketFactory<S>(
				Comparators.<S>fromProperty("service.ranking")));
	}

	public static <K, S> ServiceTrackerMap<K, S> createObjectMap(
		Class<S> clazz, String filter,
		ServiceReferenceMapper<K> serviceReferenceMapper) {

		return new ServiceTrackerMapImpl<K, S, S>(
			clazz, filter, serviceReferenceMapper,
			new ObjectServiceTrackerBucketFactory<S>(
				Comparators.<S>fromProperty("service.ranking")));
	}

	public static <K, S> ServiceTrackerMap<K, S> createObjectMap(
		Class<S> clazz, String filter,
		ServiceReferenceMapper<K> serviceReferenceMapper,
		Comparator<ServiceReference<S>> comparator) {

		return new ServiceTrackerMapImpl<K, S, S>(
			clazz, filter, serviceReferenceMapper,
			new ObjectServiceTrackerBucketFactory<S>(comparator));
	}

	public static <S> ServiceTrackerMap<String, S> createObjectMapFromProperty(
		Class<S> clazz, String property) {

		return new ServiceTrackerMapImpl<String, S, S>(
			clazz, "("+property+"=*)", Mappers.<String>fromProperty(property),
			new ObjectServiceTrackerBucketFactory<S>(
				Comparators.<S>fromProperty("service.ranking")));
	}

	public static class Comparators {

		public static <T> Comparator<ServiceReference<T>> fromProperty(
			final String property) {

			return new Comparator<ServiceReference<T>>() {

				@Override
				public int compare(
					ServiceReference<T> sr1, ServiceReference<T> sr2) {

					if (sr1 == null) {
						if (sr2 == null) {
							return 0;
						}
						else {
							return -1;
						}
					}
					else if (sr2 == null) {
						return 1;
					}

					try {
						Comparable property1 = (Comparable)sr1.getProperty(
							property);

						Object property2 = sr2.getProperty(property);

						if (property1 == null) {
							if (property2 != null) {
								return -1;
							}

							return -(sr1.compareTo(sr2));
						}

						return -(property1.compareTo(property2));
					}
					catch (ClassCastException e) {
						return 0;
					}
				}
			};
		}
	}

	public static class Mappers {

		public static <T> ServiceReferenceMapper<T> fromProperty(
			final String property) {

			return new ServiceReferenceMapper<T>() {

				@Override
				public void map(
					ServiceReference<?> serviceReference, Emitter<T> emitter) {

					T value = (T)serviceReference.getProperty(property);

					if (value != null) {
						emitter.emit(value);
					}
				}
			};
		}

	}

}