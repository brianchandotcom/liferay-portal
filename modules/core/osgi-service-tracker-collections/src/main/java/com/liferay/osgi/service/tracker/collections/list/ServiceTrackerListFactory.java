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

package com.liferay.osgi.service.tracker.collections.list;

import com.liferay.osgi.service.tracker.collections.internal.DefaultServiceTrackerCustomizer;
import com.liferay.osgi.service.tracker.collections.internal.list.ServiceTrackerListImpl;

import java.util.Comparator;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Adolfo Pérez
 */
public class ServiceTrackerListFactory {

	public static <S, T> ServiceTrackerList<S, T> open(
		BundleContext bundleContext, Class<S> clazz, String filterString,
		ServiceTrackerCustomizer<S, T> serviceTrackerCustomizer) {

		return new ServiceTrackerListImpl<>(
			bundleContext, clazz, filterString, serviceTrackerCustomizer, null);
	}

	public static <S, T> ServiceTrackerList<S, T> open(
		BundleContext bundleContext, Class<S> clazz, String filterString,
		ServiceTrackerCustomizer<S, T> serviceTrackerCustomizer,
		Comparator<ServiceReference<S>> comparator) {

		return new ServiceTrackerListImpl<>(
			bundleContext, clazz, filterString, serviceTrackerCustomizer,
			comparator);
	}

	public static <T> ServiceTrackerList<T, T> open(
		BundleContext bundleContext, Class<T> clazz) {

		ServiceTrackerCustomizer<T, T> serviceTrackerCustomizer =
			new DefaultServiceTrackerCustomizer<>(bundleContext);

		return new ServiceTrackerListImpl<>(
			bundleContext, clazz, null, serviceTrackerCustomizer, null);
	}

	public static <T> ServiceTrackerList<T, T> open(
		BundleContext bundleContext, Class<T> clazz,
		Comparator<ServiceReference<T>> comparator) {

		ServiceTrackerCustomizer<T, T> serviceTrackerCustomizer =
			new DefaultServiceTrackerCustomizer<>(bundleContext);

		return new ServiceTrackerListImpl<>(
			bundleContext, clazz, null, serviceTrackerCustomizer, comparator);
	}

	public static <T> ServiceTrackerList<T, T> open(
		BundleContext bundleContext, Class<T> clazz, String filterString) {

		ServiceTrackerCustomizer<T, T> serviceTrackerCustomizer =
			new DefaultServiceTrackerCustomizer<>(bundleContext);

		return new ServiceTrackerListImpl<>(
			bundleContext, clazz, filterString, serviceTrackerCustomizer, null);
	}

	public static <T> ServiceTrackerList<T, T> open(
		BundleContext bundleContext, Class<T> clazz, String filterString,
		Comparator<ServiceReference<T>> comparator) {

		ServiceTrackerCustomizer<T, T> serviceTrackerCustomizer =
			new DefaultServiceTrackerCustomizer<>(bundleContext);

		return new ServiceTrackerListImpl<>(
			bundleContext, clazz, filterString, serviceTrackerCustomizer,
			comparator);
	}

}