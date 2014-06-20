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

/**
 * @author Carlos Sierra Andrés
 */
public class ServiceTrackerMapFactory {

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
			final String propertyName) {

			return new ServiceReferenceMapper<T>() {

				@Override
				public void map(
					ServiceReference<?> serviceReference, Emitter<T> emitter) {

					T property = (T)serviceReference.getProperty(propertyName);

					if (property != null) {
						emitter.emit(property);
					}
				}
			};
		}

	}

}