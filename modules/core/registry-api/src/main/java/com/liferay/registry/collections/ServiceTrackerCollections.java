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

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * @author Raymond Augé
 */
public class ServiceTrackerCollections {

	public static <S> ServiceTrackerList<S> list(Class<S> clazz) {
		return new ServiceTrackerCollectionImpl<>(
			clazz, null, null, Collections.<String, Object>emptyMap());
	}

	public static <S> ServiceTrackerList<S> list(
		Class<S> clazz, Filter filter) {

		return new ServiceTrackerCollectionImpl<>(
			clazz, filter, null, Collections.<String, Object>emptyMap());
	}

	public static <S> ServiceTrackerList<S> list(
		Class<S> clazz, Filter filter, Map<String, Object> properties) {

		return new ServiceTrackerCollectionImpl<>(
			clazz, filter, null, properties);
	}

	public static <S> ServiceTrackerList<S> list(
		Class<S> clazz, Filter filter,
		ServiceTrackerCustomizer<S, S> serviceTrackerCustomizer) {

		return new ServiceTrackerCollectionImpl<>(
			clazz, filter, serviceTrackerCustomizer,
			Collections.<String, Object>emptyMap());
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
			clazz, null, null, properties);
	}

	public static <S> ServiceTrackerList<S> list(
		Class<S> clazz,
		ServiceTrackerCustomizer<S, S> serviceTrackerCustomizer) {

		return new ServiceTrackerCollectionImpl<>(
			clazz, null, serviceTrackerCustomizer,
			Collections.<String, Object>emptyMap());
	}

	public static <S> ServiceTrackerList<S> list(
		Class<S> clazz, ServiceTrackerCustomizer<S, S> serviceTrackerCustomizer,
		Map<String, Object> properties) {

		return new ServiceTrackerCollectionImpl<>(
			clazz, null, serviceTrackerCustomizer, properties);
	}

	public static <S> ServiceTrackerList<S> list(
		Class<S> clazz, String filterString) {

		return list(clazz, _getFilter(filterString));
	}

	public static <S> ServiceTrackerList<S> list(
		Class<S> clazz, String filterString, Map<String, Object> properties) {

		return list(clazz, _getFilter(filterString), properties);
	}

	public static <S> ServiceTrackerList<S> list(
		Class<S> clazz, String filterString,
		ServiceTrackerCustomizer<S, S> serviceTrackerCustomizer) {

		return list(clazz, _getFilter(filterString), serviceTrackerCustomizer);
	}

	public static <S> ServiceTrackerList<S> list(
		Class<S> clazz, String filterString,
		ServiceTrackerCustomizer<S, S> serviceTrackerCustomizer,
		Map<String, Object> properties) {

		return list(
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

	public static <S> ServiceTrackerList<S> openList(Class<S> clazz) {
		ServiceTrackerList<S> serviceTrackerList = list(clazz);

		serviceTrackerList.open();

		return serviceTrackerList;
	}

	public static <S> ServiceTrackerList<S> openList(
		Class<S> clazz, Filter filter) {

		ServiceTrackerList<S> serviceTrackerList = list(clazz, filter);

		serviceTrackerList.open();

		return serviceTrackerList;
	}

	public static <S> ServiceTrackerList<S> openList(
		Class<S> clazz, Filter filter, Map<String, Object> properties) {

		ServiceTrackerList<S> serviceTrackerList = list(
			clazz, filter, properties);

		serviceTrackerList.open();

		return serviceTrackerList;
	}

	public static <S> ServiceTrackerList<S> openList(
		Class<S> clazz, Filter filter,
		ServiceTrackerCustomizer<S, S> serviceTrackerCustomizer) {

		ServiceTrackerList<S> serviceTrackerList = list(
			clazz, filter, serviceTrackerCustomizer);

		serviceTrackerList.open();

		return serviceTrackerList;
	}

	public static <S> ServiceTrackerList<S> openList(
		Class<S> clazz, Filter filter,
		ServiceTrackerCustomizer<S, S> serviceTrackerCustomizer,
		Map<String, Object> properties) {

		ServiceTrackerList<S> serviceTrackerList = list(
			clazz, filter, serviceTrackerCustomizer, properties);

		serviceTrackerList.open();

		return serviceTrackerList;
	}

	public static <S> ServiceTrackerList<S> openList(
		Class<S> clazz, Map<String, Object> properties) {

		ServiceTrackerList<S> serviceTrackerList = list(clazz, properties);

		serviceTrackerList.open();

		return serviceTrackerList;
	}

	public static <S> ServiceTrackerList<S> openList(
		Class<S> clazz,
		ServiceTrackerCustomizer<S, S> serviceTrackerCustomizer) {

		ServiceTrackerList<S> serviceTrackerList = list(
			clazz, serviceTrackerCustomizer);

		serviceTrackerList.open();

		return serviceTrackerList;
	}

	public static <S> ServiceTrackerList<S> openList(
		Class<S> clazz, ServiceTrackerCustomizer<S, S> serviceTrackerCustomizer,
		Map<String, Object> properties) {

		ServiceTrackerList<S> serviceTrackerList = list(
			clazz, serviceTrackerCustomizer, properties);

		serviceTrackerList.open();

		return serviceTrackerList;
	}

	public static <S> ServiceTrackerList<S> openList(
		Class<S> clazz, String filterString) {

		ServiceTrackerList<S> serviceTrackerList = list(clazz, filterString);

		serviceTrackerList.open();

		return serviceTrackerList;
	}

	public static <S> ServiceTrackerList<S> openList(
		Class<S> clazz, String filterString, Map<String, Object> properties) {

		ServiceTrackerList<S> serviceTrackerList = list(
			clazz, filterString, properties);

		serviceTrackerList.open();

		return serviceTrackerList;
	}

	public static <S> ServiceTrackerList<S> openList(
		Class<S> clazz, String filterString,
		ServiceTrackerCustomizer<S, S> serviceTrackerCustomizer) {

		ServiceTrackerList<S> serviceTrackerList = list(
			clazz, filterString, serviceTrackerCustomizer);

		serviceTrackerList.open();

		return serviceTrackerList;
	}

	public static <S> ServiceTrackerList<S> openList(
		Class<S> clazz, String filterString,
		ServiceTrackerCustomizer<S, S> serviceTrackerCustomizer,
		Map<String, Object> properties) {

		ServiceTrackerList<S> serviceTrackerList = list(
			clazz, filterString, serviceTrackerCustomizer, properties);

		serviceTrackerList.open();

		return serviceTrackerList;
	}

	public static <S> ServiceTrackerMap<String, List<S>> openMultiValueMap(
		Class<S> clazz, String propertyKey) {

		ServiceTrackerMap<String, List<S>> serviceTrackerMap = multiValueMap(
			clazz, propertyKey);

		serviceTrackerMap.open();

		return serviceTrackerMap;
	}

	public static <K, S> ServiceTrackerMap<K, List<S>> openMultiValueMap(
		Class<S> clazz, String filterString,
		ServiceReferenceMapper<K, ? super S> serviceReferenceMapper) {

		ServiceTrackerMap<K, List<S>> serviceTrackerMap = multiValueMap(
			clazz, filterString, serviceReferenceMapper);

		serviceTrackerMap.open();

		return serviceTrackerMap;
	}

	public static <K, S> ServiceTrackerMap<K, List<S>> openMultiValueMap(
		Class<S> clazz, String filterString,
		ServiceReferenceMapper<K, ? super S> serviceReferenceMapper,
		Comparator<ServiceReference<S>> comparator) {

		ServiceTrackerMap<K, List<S>> serviceTrackerMap = multiValueMap(
			clazz, filterString, serviceReferenceMapper, comparator);

		serviceTrackerMap.open();

		return serviceTrackerMap;
	}

	public static <K, SR, S> ServiceTrackerMap<K, List<S>> openMultiValueMap(
		Class<SR> clazz, String filterString,
		ServiceReferenceMapper<K, ? super SR> serviceReferenceMapper,
		ServiceTrackerCustomizer<SR, S> serviceTrackerCustomizer) {

		ServiceTrackerMap<K, List<S>> serviceTrackerMap = multiValueMap(
			clazz, filterString, serviceReferenceMapper,
			serviceTrackerCustomizer);

		serviceTrackerMap.open();

		return serviceTrackerMap;
	}

	public static <K, SR, S> ServiceTrackerMap<K, List<S>> openMultiValueMap(
		Class<SR> clazz, String filterString,
		ServiceReferenceMapper<K, ? super SR> serviceReferenceMapper,
		ServiceTrackerCustomizer<SR, S> serviceTrackerCustomizer,
		Comparator<ServiceReference<SR>> comparator) {

		ServiceTrackerMap<K, List<S>> serviceTrackerMap = multiValueMap(
			clazz, filterString, serviceReferenceMapper,
			serviceTrackerCustomizer, comparator);

		serviceTrackerMap.open();

		return serviceTrackerMap;
	}

	public static <SR, S> ServiceTrackerMap<String, List<S>> openMultiValueMap(
		Class<SR> clazz, String propertyKey,
		ServiceTrackerCustomizer<SR, S> serviceTrackerCustomizer) {

		ServiceTrackerMap<String, List<S>> serviceTrackerMap = multiValueMap(
			clazz, propertyKey, serviceTrackerCustomizer);

		serviceTrackerMap.open();

		return serviceTrackerMap;
	}

	public static <S> ServiceTrackerMap<String, S> openSingleValueMap(
		Class<S> clazz, String propertyKey) {

		ServiceTrackerMap<String, S> serviceTrackerMap = singleValueMap(
			clazz, propertyKey);

		serviceTrackerMap.open();

		return serviceTrackerMap;
	}

	public static <K, S> ServiceTrackerMap<K, S> openSingleValueMap(
		Class<S> clazz, String filterString,
		ServiceReferenceMapper<K, ? super S> serviceReferenceMapper) {

		ServiceTrackerMap<K, S> serviceTrackerMap = singleValueMap(
			clazz, filterString, serviceReferenceMapper);

		serviceTrackerMap.open();

		return serviceTrackerMap;
	}

	public static <K, S> ServiceTrackerMap<K, S> openSingleValueMap(
		Class<S> clazz, String filterString,
		ServiceReferenceMapper<K, ? super S> serviceReferenceMapper,
		Comparator<ServiceReference<S>> comparator) {

		ServiceTrackerMap<K, S> serviceTrackerMap = singleValueMap(
			clazz, filterString, serviceReferenceMapper, comparator);

		serviceTrackerMap.open();

		return serviceTrackerMap;
	}

	public static <K, SR, S> ServiceTrackerMap<K, S> openSingleValueMap(
		Class<SR> clazz, String filterString,
		ServiceReferenceMapper<K, ? super SR> serviceReferenceMapper,
		ServiceTrackerCustomizer<SR, S> serviceTrackerCustomizer) {

		ServiceTrackerMap<K, S> serviceTrackerMap = singleValueMap(
			clazz, filterString, serviceReferenceMapper,
			serviceTrackerCustomizer);

		serviceTrackerMap.open();

		return serviceTrackerMap;
	}

	public static <K, SR, S> ServiceTrackerMap<K, S> openSingleValueMap(
		Class<SR> clazz, String filterString,
		ServiceReferenceMapper<K, ? super SR> serviceReferenceMapper,
		ServiceTrackerCustomizer<SR, S> serviceTrackerCustomizer,
		Comparator<ServiceReference<SR>> comparator) {

		ServiceTrackerMap<K, S> serviceTrackerMap = singleValueMap(
			clazz, filterString, serviceReferenceMapper,
			serviceTrackerCustomizer, comparator);

		serviceTrackerMap.open();

		return serviceTrackerMap;
	}

	public static <SR, S> ServiceTrackerMap<String, S> openSingleValueMap(
		Class<SR> clazz, String propertyKey,
		ServiceTrackerCustomizer<SR, S> serviceTrackerCustomizer) {

		ServiceTrackerMap<String, S> serviceTrackerMap = singleValueMap(
			clazz, propertyKey, serviceTrackerCustomizer);

		serviceTrackerMap.open();

		return serviceTrackerMap;
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