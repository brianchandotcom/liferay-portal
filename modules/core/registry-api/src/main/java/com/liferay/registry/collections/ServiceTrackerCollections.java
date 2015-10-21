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
import com.liferay.registry.collections.internal.ServiceTrackerCollectionImpl;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Raymond Augé
 */
public class ServiceTrackerCollections {

	public static <S> ServiceTrackerList<S> list(Class<S> clazz) {
		return new ServiceTrackerCollectionImpl<>(
			clazz, (Filter)null, null, new HashMap<String, Object>());
	}

	public static <S> ServiceTrackerList<S> list(
		Class<S> clazz, Filter filter) {

		return new ServiceTrackerCollectionImpl<>(
			clazz, filter, null, new HashMap<String, Object>());
	}

	public static <S> ServiceTrackerList<S> list(
		Class<S> clazz, Filter filter, Map<String, Object> properties) {

		return new ServiceTrackerCollectionImpl<>(
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

		return new ServiceTrackerCollectionImpl<>(
			clazz, filter, serviceTrackerCustomizer, properties);
	}

	public static <S> ServiceTrackerList<S> list(
		Class<S> clazz, Map<String, Object> properties) {

		return new ServiceTrackerCollectionImpl<>(
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

		return new ServiceTrackerCollectionImpl<>(
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

		return new ServiceTrackerCollectionImpl<>(
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

		ServiceTrackerMapFactory serviceTrackerMapFactory =
			ServiceTrackerMapFactoryUtil.getServiceTrackerMapFactory();

		return serviceTrackerMapFactory.multiValueMap(clazz, propertyKey);
	}

	public static <K, S> ServiceTrackerMap<K, List<S>> multiValueMap(
		Class<S> clazz, String filterString,
		ServiceReferenceMapper<K, ? super S> serviceReferenceMapper) {

		ServiceTrackerMapFactory serviceTrackerMapFactory =
			ServiceTrackerMapFactoryUtil.getServiceTrackerMapFactory();

		return serviceTrackerMapFactory.multiValueMap(
			clazz, filterString, serviceReferenceMapper);
	}

	public static <K, S> ServiceTrackerMap<K, List<S>> multiValueMap(
		Class<S> clazz, String filterString,
		ServiceReferenceMapper<K, ? super S> serviceReferenceMapper,
		Comparator<ServiceReference<S>> comparator) {

		ServiceTrackerMapFactory serviceTrackerMapFactory =
			ServiceTrackerMapFactoryUtil.getServiceTrackerMapFactory();

		return serviceTrackerMapFactory.multiValueMap(
			clazz, filterString, serviceReferenceMapper, comparator);
	}

	public static <K, SR, S> ServiceTrackerMap<K, List<S>> multiValueMap(
		Class<SR> clazz, String filterString,
		ServiceReferenceMapper<K, ? super SR> serviceReferenceMapper,
		ServiceTrackerCustomizer<SR, S> serviceTrackerCustomizer) {

		ServiceTrackerMapFactory serviceTrackerMapFactory =
			ServiceTrackerMapFactoryUtil.getServiceTrackerMapFactory();

		return serviceTrackerMapFactory.multiValueMap(
			clazz, filterString, serviceReferenceMapper,
			serviceTrackerCustomizer);
	}

	public static <K, SR, S> ServiceTrackerMap<K, List<S>> multiValueMap(
		Class<SR> clazz, String filterString,
		ServiceReferenceMapper<K, ? super SR> serviceReferenceMapper,
		ServiceTrackerCustomizer<SR, S> serviceTrackerCustomizer,
		Comparator<ServiceReference<SR>> comparator) {

		ServiceTrackerMapFactory serviceTrackerMapFactory =
			ServiceTrackerMapFactoryUtil.getServiceTrackerMapFactory();

		return serviceTrackerMapFactory.multiValueMap(
			clazz, filterString, serviceReferenceMapper,
			serviceTrackerCustomizer, comparator);
	}

	public static <SR, S> ServiceTrackerMap<String, List<S>> multiValueMap(
		Class<SR> clazz, String propertyKey,
		ServiceTrackerCustomizer<SR, S> serviceTrackerCustomizer) {

		ServiceTrackerMapFactory serviceTrackerMapFactory =
			ServiceTrackerMapFactoryUtil.getServiceTrackerMapFactory();

		return serviceTrackerMapFactory.multiValueMap(
			clazz, propertyKey, serviceTrackerCustomizer);
	}

	public static <S> ServiceTrackerMap<String, S> singleValueMap(
		Class<S> clazz, String propertyKey) {

		ServiceTrackerMapFactory serviceTrackerMapFactory =
			ServiceTrackerMapFactoryUtil.getServiceTrackerMapFactory();

		return serviceTrackerMapFactory.singleValueMap(clazz, propertyKey);
	}

	public static <K, S> ServiceTrackerMap<K, S> singleValueMap(
		Class<S> clazz, String filterString,
		ServiceReferenceMapper<K, ? super S> serviceReferenceMapper) {

		ServiceTrackerMapFactory serviceTrackerMapFactory =
			ServiceTrackerMapFactoryUtil.getServiceTrackerMapFactory();

		return serviceTrackerMapFactory.singleValueMap(
			clazz, filterString, serviceReferenceMapper);
	}

	public static <K, S> ServiceTrackerMap<K, S> singleValueMap(
		Class<S> clazz, String filterString,
		ServiceReferenceMapper<K, ? super S> serviceReferenceMapper,
		Comparator<ServiceReference<S>> comparator) {

		ServiceTrackerMapFactory serviceTrackerMapFactory =
			ServiceTrackerMapFactoryUtil.getServiceTrackerMapFactory();

		return serviceTrackerMapFactory.singleValueMap(
			clazz, filterString, serviceReferenceMapper, comparator);
	}

	public static <K, SR, S> ServiceTrackerMap<K, S> singleValueMap(
		Class<SR> clazz, String filterString,
		ServiceReferenceMapper<K, ? super SR> serviceReferenceMapper,
		ServiceTrackerCustomizer<SR, S> serviceTrackerCustomizer) {

		ServiceTrackerMapFactory serviceTrackerMapFactory =
			ServiceTrackerMapFactoryUtil.getServiceTrackerMapFactory();

		return serviceTrackerMapFactory.singleValueMap(
			clazz, filterString, serviceReferenceMapper,
			serviceTrackerCustomizer);
	}

	public static <K, SR, S> ServiceTrackerMap<K, S> singleValueMap(
		Class<SR> clazz, String filterString,
		ServiceReferenceMapper<K, ? super SR> serviceReferenceMapper,
		ServiceTrackerCustomizer<SR, S> serviceTrackerCustomizer,
		Comparator<ServiceReference<SR>> comparator) {

		ServiceTrackerMapFactory serviceTrackerMapFactory =
			ServiceTrackerMapFactoryUtil.getServiceTrackerMapFactory();

		return serviceTrackerMapFactory.singleValueMap(
			clazz, filterString, serviceReferenceMapper,
			serviceTrackerCustomizer, comparator);
	}

	public static <SR, S> ServiceTrackerMap<String, S> singleValueMap(
		Class<SR> clazz, String propertyKey,
		ServiceTrackerCustomizer<SR, S> serviceTrackerCustomizer) {

		ServiceTrackerMapFactory serviceTrackerMapFactory =
			ServiceTrackerMapFactoryUtil.getServiceTrackerMapFactory();

		return serviceTrackerMapFactory.singleValueMap(
			clazz, propertyKey, serviceTrackerCustomizer);
	}

	private static Filter _getFilter(String filterString) {
		Registry registry = RegistryUtil.getRegistry();

		return registry.getFilter(filterString);
	}

	private ServiceTrackerCollections() {
	}

}