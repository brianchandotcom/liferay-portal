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

package com.liferay.registry;

import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author Shuyang Zhou
 */
public class ServiceRankingUtil {

	public static int compare(
		ServiceReference<?> serviceReference1,
		ServiceReference<?> serviceReference2) {

		int value = Integer.compare(
			_getServiceRanking(serviceReference1),
			_getServiceRanking(serviceReference2));

		if (value != 0) {
			return value;
		}

		return -Long.compare(
			(Long)serviceReference1.getProperty("service.id"),
			(Long)serviceReference2.getProperty("service.id"));
	}

	public static <S, T> Optional<Entry<ServiceReference<S>, T>>
		getHighestRankingEntry(Map<ServiceReference<S>, T> services) {

		Set<Entry<ServiceReference<S>, T>> entrySet = services.entrySet();

		Stream<Entry<ServiceReference<S>, T>> stream = entrySet.stream();

		return stream.max(
			Comparator.comparing(Entry::getKey, ServiceRankingUtil::compare));
	}

	private static int _getServiceRanking(
		ServiceReference<?> serviceReference) {

		Object serviceRanking = serviceReference.getProperty("service.ranking");

		if (serviceRanking instanceof Integer) {
			return (Integer)serviceRanking;
		}

		return 0;
	}

}