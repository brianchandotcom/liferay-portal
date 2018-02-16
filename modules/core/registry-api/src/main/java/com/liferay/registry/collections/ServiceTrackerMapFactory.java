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
import com.liferay.registry.ServiceTrackerCustomizer;

import java.util.Comparator;
import java.util.List;

/**
 * @author Carlos Sierra Andrés
 */
public interface ServiceTrackerMapFactory {

	public <S> ServiceTrackerMap<String, List<S>> openMultiValueMap(
		Class<S> clazz, String propertyKey);

	public <K, S> ServiceTrackerMap<K, List<S>> openMultiValueMap(
		Class<S> clazz, String filterString,
		ServiceReferenceMapper<K, ? super S> serviceReferenceMapper);

	public <K, S> ServiceTrackerMap<K, List<S>> openMultiValueMap(
		Class<S> clazz, String filterString,
		ServiceReferenceMapper<K, ? super S> serviceReferenceMapper,
		Comparator<ServiceReference<S>> comparator);

	public <K, S> ServiceTrackerMap<K, List<S>> openMultiValueMap(
		Class<S> clazz, String filterString,
		ServiceReferenceMapper<K, ? super S> serviceReferenceMapper,
		ServiceTrackerMapListener<K, ? super S, List<S>>
			serviceTrackerMapListener);

	public <K, SR, S> ServiceTrackerMap<K, List<S>> openMultiValueMap(
		Class<SR> clazz, String filterString,
		ServiceReferenceMapper<K, ? super SR> serviceReferenceMapper,
		ServiceTrackerCustomizer<SR, S> serviceTrackerCustomizer);

	public <K, SR, S> ServiceTrackerMap<K, List<S>> openMultiValueMap(
		Class<SR> clazz, String filterString,
		ServiceReferenceMapper<K, ? super SR> serviceReferenceMapper,
		ServiceTrackerCustomizer<SR, S> serviceTrackerCustomizer,
		Comparator<ServiceReference<SR>> comparator);

	public <SR, S> ServiceTrackerMap<String, List<S>> openMultiValueMap(
		Class<SR> clazz, String propertyKey,
		ServiceTrackerCustomizer<SR, S> serviceTrackerCustomizer);

	public <S> ServiceTrackerMap<String, S> openSingleValueMap(
		Class<S> clazz, String propertyKey);

	public <K, S> ServiceTrackerMap<K, S> openSingleValueMap(
		Class<S> clazz, String filterString,
		ServiceReferenceMapper<K, ? super S> serviceReferenceMapper);

	public <K, S> ServiceTrackerMap<K, S> openSingleValueMap(
		Class<S> clazz, String filterString,
		ServiceReferenceMapper<K, ? super S> serviceReferenceMapper,
		Comparator<ServiceReference<S>> comparator);

	public <K, SR, S> ServiceTrackerMap<K, S> openSingleValueMap(
		Class<SR> clazz, String filterString,
		ServiceReferenceMapper<K, ? super SR> serviceReferenceMapper,
		ServiceTrackerCustomizer<SR, S> serviceTrackerCustomizer);

	public <K, SR, S> ServiceTrackerMap<K, S> openSingleValueMap(
		Class<SR> clazz, String filterString,
		ServiceReferenceMapper<K, ? super SR> serviceReferenceMapper,
		ServiceTrackerCustomizer<SR, S> serviceTrackerCustomizer,
		Comparator<ServiceReference<SR>> comparator);

	public <SR, S> ServiceTrackerMap<String, S> openSingleValueMap(
		Class<SR> clazz, String propertyKey,
		ServiceTrackerCustomizer<SR, S> serviceTrackerCustomizer);

}