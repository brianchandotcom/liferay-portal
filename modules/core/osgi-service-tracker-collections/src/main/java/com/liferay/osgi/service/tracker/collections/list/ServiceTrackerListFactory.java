/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public static <S, T> ServiceTrackerList<T> open(
		BundleContext bundleContext, Class<S> clazz, String filterString,
		ServiceTrackerCustomizer<S, T> serviceTrackerCustomizer) {

		return new ServiceTrackerListImpl<>(
			bundleContext, clazz, filterString, serviceTrackerCustomizer, null);
	}

	public static <S, T> ServiceTrackerList<T> open(
		BundleContext bundleContext, Class<S> clazz, String filterString,
		ServiceTrackerCustomizer<S, T> serviceTrackerCustomizer,
		Comparator<ServiceReference<S>> comparator) {

		return new ServiceTrackerListImpl<>(
			bundleContext, clazz, filterString, serviceTrackerCustomizer,
			comparator);
	}

	public static <T> ServiceTrackerList<T> open(
		BundleContext bundleContext, Class<T> clazz) {

		ServiceTrackerCustomizer<T, T> serviceTrackerCustomizer =
			new DefaultServiceTrackerCustomizer<>(bundleContext);

		return new ServiceTrackerListImpl<>(
			bundleContext, clazz, null, serviceTrackerCustomizer, null);
	}

	public static <T> ServiceTrackerList<T> open(
		BundleContext bundleContext, Class<T> clazz,
		Comparator<ServiceReference<T>> comparator) {

		ServiceTrackerCustomizer<T, T> serviceTrackerCustomizer =
			new DefaultServiceTrackerCustomizer<>(bundleContext);

		return new ServiceTrackerListImpl<>(
			bundleContext, clazz, null, serviceTrackerCustomizer, comparator);
	}

	public static <T> ServiceTrackerList<T> open(
		BundleContext bundleContext, Class<T> clazz, String filterString) {

		ServiceTrackerCustomizer<T, T> serviceTrackerCustomizer =
			new DefaultServiceTrackerCustomizer<>(bundleContext);

		return new ServiceTrackerListImpl<>(
			bundleContext, clazz, filterString, serviceTrackerCustomizer, null);
	}

	public static <T> ServiceTrackerList<T> open(
		BundleContext bundleContext, Class<T> clazz, String filterString,
		Comparator<ServiceReference<T>> comparator) {

		ServiceTrackerCustomizer<T, T> serviceTrackerCustomizer =
			new DefaultServiceTrackerCustomizer<>(bundleContext);

		return new ServiceTrackerListImpl<>(
			bundleContext, clazz, filterString, serviceTrackerCustomizer,
			comparator);
	}

}