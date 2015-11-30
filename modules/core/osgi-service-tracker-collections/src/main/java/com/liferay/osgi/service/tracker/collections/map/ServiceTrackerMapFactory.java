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

package com.liferay.osgi.service.tracker.collections.map;

import com.liferay.osgi.service.tracker.collections.internal.DefaultServiceTrackerCustomizer;
import com.liferay.osgi.service.tracker.collections.internal.map.MultiValueServiceTrackerBucketFactory;
import com.liferay.osgi.service.tracker.collections.internal.map.ServiceTrackerMapImpl;
import com.liferay.osgi.service.tracker.collections.internal.map.SingleValueServiceTrackerBucketFactory;

import java.util.Comparator;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Carlos Sierra Andrés
 */
public class ServiceTrackerMapFactory {

	public static <S> ServiceTrackerMap<String, List<S>> multiValueMap(
			BundleContext bundleContext, Class<S> clazz, String propertyKey)
		throws InvalidSyntaxException {

		return new ServiceTrackerMapImpl<>(
			bundleContext, clazz, "(" + propertyKey + "=*)",
			new PropertyServiceReferenceMapper<String, S>(propertyKey),
			new DefaultServiceTrackerCustomizer<S>(bundleContext),
			new MultiValueServiceTrackerBucketFactory<S, S>(), null);
	}

	public static <K, S> ServiceTrackerMap<K, List<S>> multiValueMap(
			BundleContext bundleContext, Class<S> clazz, String filterString,
			ServiceReferenceMapper<K, ? super S> serviceReferenceMapper)
		throws InvalidSyntaxException {

		return new ServiceTrackerMapImpl<>(
			bundleContext, clazz, filterString, serviceReferenceMapper,
			new DefaultServiceTrackerCustomizer<S>(bundleContext),
			new MultiValueServiceTrackerBucketFactory<S, S>(), null);
	}

	public static <K, S> ServiceTrackerMap<K, List<S>> multiValueMap(
			BundleContext bundleContext, Class<S> clazz, String filterString,
			ServiceReferenceMapper<K, ? super S> serviceReferenceMapper,
			Comparator<ServiceReference<S>> comparator)
		throws InvalidSyntaxException {

		return new ServiceTrackerMapImpl<>(
			bundleContext, clazz, filterString, serviceReferenceMapper,
			new DefaultServiceTrackerCustomizer<S>(bundleContext),
			new MultiValueServiceTrackerBucketFactory<S, S>(comparator), null);
	}

	public static <K, S> ServiceTrackerMap<K, List<S>> multiValueMap(
			BundleContext bundleContext, Class<S> clazz, String filterString,
			ServiceReferenceMapper<K, ? super S> serviceReferenceMapper,
			Comparator<ServiceReference<S>> comparator,
			ServiceTrackerMapListener<K, S, List<S>> serviceTrackerMapListener)
		throws InvalidSyntaxException {

		return new ServiceTrackerMapImpl<>(
			bundleContext, clazz, filterString, serviceReferenceMapper,
			new DefaultServiceTrackerCustomizer<S>(bundleContext),
			new MultiValueServiceTrackerBucketFactory<S, S>(comparator),
			serviceTrackerMapListener);
	}

	public static <K, SR, S> ServiceTrackerMap<K, List<S>> multiValueMap(
			BundleContext bundleContext, Class<SR> clazz, String filterString,
			ServiceReferenceMapper<K, ? super SR> serviceReferenceMapper,
			ServiceTrackerCustomizer<SR, S> serviceTrackerCustomizer)
		throws InvalidSyntaxException {

		return new ServiceTrackerMapImpl<>(
			bundleContext, clazz, filterString, serviceReferenceMapper,
			serviceTrackerCustomizer,
			new MultiValueServiceTrackerBucketFactory<SR, S>(), null);
	}

	public static <K, SR, S> ServiceTrackerMap<K, List<S>> multiValueMap(
			BundleContext bundleContext, Class<SR> clazz, String filterString,
			ServiceReferenceMapper<K, ? super SR> serviceReferenceMapper,
			ServiceTrackerCustomizer<SR, S> serviceTrackerCustomizer,
			Comparator<ServiceReference<SR>> comparator)
		throws InvalidSyntaxException {

		return new ServiceTrackerMapImpl<>(
			bundleContext, clazz, filterString, serviceReferenceMapper,
			serviceTrackerCustomizer,
			new MultiValueServiceTrackerBucketFactory<SR, S>(comparator), null);
	}

	public static <K, SR, S> ServiceTrackerMap<K, List<S>> multiValueMap(
			BundleContext bundleContext, Class<SR> clazz, String filterString,
			ServiceReferenceMapper<K, ? super SR> serviceReferenceMapper,
			ServiceTrackerCustomizer<SR, S> serviceTrackerCustomizer,
			Comparator<ServiceReference<SR>> comparator,
			ServiceTrackerMapListener<K, S, List<S>> serviceTrackerMapListener)
		throws InvalidSyntaxException {

		return new ServiceTrackerMapImpl<>(
			bundleContext, clazz, filterString, serviceReferenceMapper,
			serviceTrackerCustomizer,
			new MultiValueServiceTrackerBucketFactory<SR, S>(comparator),
			serviceTrackerMapListener);
	}

	public static <SR, S> ServiceTrackerMap<String, List<S>> multiValueMap(
			BundleContext bundleContext, Class<SR> clazz, String propertyKey,
			ServiceTrackerCustomizer<SR, S> serviceTrackerCustomizer)
		throws InvalidSyntaxException {

		return new ServiceTrackerMapImpl<>(
			bundleContext, clazz, "(" + propertyKey + "=*)",
			new PropertyServiceReferenceMapper<String, SR>(propertyKey),
			serviceTrackerCustomizer,
			new MultiValueServiceTrackerBucketFactory<SR, S>(), null);
	}

	public static <S> ServiceTrackerMap<String, List<S>> openMultiValueMap(
			BundleContext bundleContext, Class<S> clazz, String propertyKey)
		throws InvalidSyntaxException {

		ServiceTrackerMap<String, List<S>> serviceTrackerMap = multiValueMap(
			bundleContext, clazz, propertyKey);

		serviceTrackerMap.open();

		return serviceTrackerMap;
	}

	public static <K, S> ServiceTrackerMap<K, List<S>> openMultiValueMap(
			BundleContext bundleContext, Class<S> clazz, String filterString,
			ServiceReferenceMapper<K, ? super S> serviceReferenceMapper)
		throws InvalidSyntaxException {

		ServiceTrackerMap<K, List<S>> serviceTrackerMap = multiValueMap(
			bundleContext, clazz, filterString, serviceReferenceMapper);

		serviceTrackerMap.open();

		return serviceTrackerMap;
	}

	public static <K, S> ServiceTrackerMap<K, List<S>> openMultiValueMap(
			BundleContext bundleContext, Class<S> clazz, String filterString,
			ServiceReferenceMapper<K, ? super S> serviceReferenceMapper,
			Comparator<ServiceReference<S>> comparator)
		throws InvalidSyntaxException {

		ServiceTrackerMap<K, List<S>> serviceTrackerMap = multiValueMap(
			bundleContext, clazz, filterString, serviceReferenceMapper,
			comparator);

		serviceTrackerMap.open();

		return serviceTrackerMap;
	}

	public static <K, S> ServiceTrackerMap<K, List<S>> openMultiValueMap(
			BundleContext bundleContext, Class<S> clazz, String filterString,
			ServiceReferenceMapper<K, ? super S> serviceReferenceMapper,
			Comparator<ServiceReference<S>> comparator,
			ServiceTrackerMapListener<K, S, List<S>> serviceTrackerMapListener)
		throws InvalidSyntaxException {

		ServiceTrackerMap<K, List<S>> serviceTrackerMap = multiValueMap(
			bundleContext, clazz, filterString, serviceReferenceMapper,
			comparator, serviceTrackerMapListener);

		serviceTrackerMap.open();

		return serviceTrackerMap;
	}

	public static <K, SR, S> ServiceTrackerMap<K, List<S>> openMultiValueMap(
			BundleContext bundleContext, Class<SR> clazz, String filterString,
			ServiceReferenceMapper<K, ? super SR> serviceReferenceMapper,
			ServiceTrackerCustomizer<SR, S> serviceTrackerCustomizer)
		throws InvalidSyntaxException {

		ServiceTrackerMap<K, List<S>> serviceTrackerMap = multiValueMap(
			bundleContext, clazz, filterString, serviceReferenceMapper,
			serviceTrackerCustomizer);

		serviceTrackerMap.open();

		return serviceTrackerMap;
	}

	public static <K, SR, S> ServiceTrackerMap<K, List<S>> openMultiValueMap(
			BundleContext bundleContext, Class<SR> clazz, String filterString,
			ServiceReferenceMapper<K, ? super SR> serviceReferenceMapper,
			ServiceTrackerCustomizer<SR, S> serviceTrackerCustomizer,
			Comparator<ServiceReference<SR>> comparator)
		throws InvalidSyntaxException {

		ServiceTrackerMap<K, List<S>> serviceTrackerMap = multiValueMap(
			bundleContext, clazz, filterString, serviceReferenceMapper,
			serviceTrackerCustomizer, comparator);

		serviceTrackerMap.open();

		return serviceTrackerMap;
	}

	public static <K, SR, S> ServiceTrackerMap<K, List<S>> openMultiValueMap(
			BundleContext bundleContext, Class<SR> clazz, String filterString,
			ServiceReferenceMapper<K, ? super SR> serviceReferenceMapper,
			ServiceTrackerCustomizer<SR, S> serviceTrackerCustomizer,
			Comparator<ServiceReference<SR>> comparator,
			ServiceTrackerMapListener<K, S, List<S>> serviceTrackerMapListener)
		throws InvalidSyntaxException {

		ServiceTrackerMap<K, List<S>> serviceTrackerMap = multiValueMap(
			bundleContext, clazz, filterString, serviceReferenceMapper,
			serviceTrackerCustomizer, comparator, serviceTrackerMapListener);

		serviceTrackerMap.open();

		return serviceTrackerMap;
	}

	public static <SR, S> ServiceTrackerMap<String, List<S>> openMultiValueMap(
			BundleContext bundleContext, Class<SR> clazz, String propertyKey,
			ServiceTrackerCustomizer<SR, S> serviceTrackerCustomizer)
		throws InvalidSyntaxException {

		ServiceTrackerMap<String, List<S>> serviceTrackerMap = multiValueMap(
			bundleContext, clazz, propertyKey, serviceTrackerCustomizer);

		serviceTrackerMap.open();

		return serviceTrackerMap;
	}

	public static <S> ServiceTrackerMap<String, S> openSingleValueMap(
			BundleContext bundleContext, Class<S> clazz, String propertyKey)
		throws InvalidSyntaxException {

		ServiceTrackerMap<String, S> serviceTrackerMap = singleValueMap(
			bundleContext, clazz, propertyKey);

		serviceTrackerMap.open();

		return serviceTrackerMap;
	}

	public static <K, S> ServiceTrackerMap<K, S> openSingleValueMap(
			BundleContext bundleContext, Class<S> clazz, String filterString,
			ServiceReferenceMapper<K, ? super S> serviceReferenceMapper)
		throws InvalidSyntaxException {

		ServiceTrackerMap<K, S> serviceTrackerMap = singleValueMap(
			bundleContext, clazz, filterString, serviceReferenceMapper);

		serviceTrackerMap.open();

		return serviceTrackerMap;
	}

	public static <K, S> ServiceTrackerMap<K, S> openSingleValueMap(
			BundleContext bundleContext, Class<S> clazz, String filterString,
			ServiceReferenceMapper<K, ? super S> serviceReferenceMapper,
			Comparator<ServiceReference<S>> comparator)
		throws InvalidSyntaxException {

		ServiceTrackerMap<K, S> serviceTrackerMap = singleValueMap(
			bundleContext, clazz, filterString, serviceReferenceMapper,
			comparator);

		serviceTrackerMap.open();

		return serviceTrackerMap;
	}

	public static <S> ServiceTrackerMap<String, S> openSingleValueMap(
			BundleContext bundleContext, Class<S> clazz, String propertyKey,
			ServiceTrackerMapListener<String, S, S> serviceTrackerMapListener)
		throws InvalidSyntaxException {

		ServiceTrackerMap<String, S> serviceTrackerMap = singleValueMap(
			bundleContext, clazz, propertyKey, serviceTrackerMapListener);

		serviceTrackerMap.open();

		return serviceTrackerMap;
	}

	public static <K, SR, S> ServiceTrackerMap<K, S> openSingleValueMap(
			BundleContext bundleContext, Class<SR> clazz, String filterString,
			ServiceReferenceMapper<K, ? super SR> serviceReferenceMapper,
			ServiceTrackerCustomizer<SR, S> serviceTrackerCustomizer)
		throws InvalidSyntaxException {

		ServiceTrackerMap<K, S> serviceTrackerMap = singleValueMap(
			bundleContext, clazz, filterString, serviceReferenceMapper,
			serviceTrackerCustomizer);

		serviceTrackerMap.open();

		return serviceTrackerMap;
	}

	public static <K, SR, S> ServiceTrackerMap<K, S> openSingleValueMap(
			BundleContext bundleContext, Class<SR> clazz, String filterString,
			ServiceReferenceMapper<K, ? super SR> serviceReferenceMapper,
			ServiceTrackerCustomizer<SR, S> serviceTrackerCustomizer,
			Comparator<ServiceReference<SR>> comparator)
		throws InvalidSyntaxException {

		ServiceTrackerMap<K, S> serviceTrackerMap = singleValueMap(
			bundleContext, clazz, filterString, serviceReferenceMapper,
			serviceTrackerCustomizer, comparator);

		serviceTrackerMap.open();

		return serviceTrackerMap;
	}

	public static <SR, S> ServiceTrackerMap<String, S> openSingleValueMap(
			BundleContext bundleContext, Class<SR> clazz, String propertyKey,
			ServiceTrackerCustomizer<SR, S> serviceTrackerCustomizer)
		throws InvalidSyntaxException {

		ServiceTrackerMap<String, S> serviceTrackerMap = singleValueMap(
			bundleContext, clazz, propertyKey, serviceTrackerCustomizer);

		serviceTrackerMap.open();

		return serviceTrackerMap;
	}

	public static <S> ServiceTrackerMap<String, S> singleValueMap(
			BundleContext bundleContext, Class<S> clazz, String propertyKey)
		throws InvalidSyntaxException {

		return new ServiceTrackerMapImpl<>(
			bundleContext, clazz, "(" + propertyKey + "=*)",
			new PropertyServiceReferenceMapper<String, S>(propertyKey),
			new DefaultServiceTrackerCustomizer<S>(bundleContext),
			new SingleValueServiceTrackerBucketFactory<S, S>(), null);
	}

	public static <K, S> ServiceTrackerMap<K, S> singleValueMap(
			BundleContext bundleContext, Class<S> clazz, String filterString,
			ServiceReferenceMapper<K, ? super S> serviceReferenceMapper)
		throws InvalidSyntaxException {

		return new ServiceTrackerMapImpl<>(
			bundleContext, clazz, filterString, serviceReferenceMapper,
			new DefaultServiceTrackerCustomizer<S>(bundleContext),
			new SingleValueServiceTrackerBucketFactory<S, S>(), null);
	}

	public static <K, S> ServiceTrackerMap<K, S> singleValueMap(
			BundleContext bundleContext, Class<S> clazz, String filterString,
			ServiceReferenceMapper<K, ? super S> serviceReferenceMapper,
			Comparator<ServiceReference<S>> comparator)
		throws InvalidSyntaxException {

		return new ServiceTrackerMapImpl<>(
			bundleContext, clazz, filterString, serviceReferenceMapper,
			new DefaultServiceTrackerCustomizer<S>(bundleContext),
			new SingleValueServiceTrackerBucketFactory<S, S>(comparator), null);
	}

	public static <S> ServiceTrackerMap<String, S> singleValueMap(
			BundleContext bundleContext, Class<S> clazz, String propertyKey,
			ServiceTrackerMapListener<String, S, S> serviceTrackerMapListener)
		throws InvalidSyntaxException {

		return new ServiceTrackerMapImpl<>(
			bundleContext, clazz, "(" + propertyKey + "=*)",
			new PropertyServiceReferenceMapper<String, S>(propertyKey),
			new DefaultServiceTrackerCustomizer<S>(bundleContext),
			new SingleValueServiceTrackerBucketFactory<S, S>(),
			serviceTrackerMapListener);
	}

	public static <K, SR, S> ServiceTrackerMap<K, S> singleValueMap(
			BundleContext bundleContext, Class<SR> clazz, String filterString,
			ServiceReferenceMapper<K, ? super SR> serviceReferenceMapper,
			ServiceTrackerCustomizer<SR, S> serviceTrackerCustomizer)
		throws InvalidSyntaxException {

		return new ServiceTrackerMapImpl<>(
			bundleContext, clazz, filterString, serviceReferenceMapper,
			serviceTrackerCustomizer,
			new SingleValueServiceTrackerBucketFactory<SR, S>(), null);
	}

	public static <K, SR, S> ServiceTrackerMap<K, S> singleValueMap(
			BundleContext bundleContext, Class<SR> clazz, String filterString,
			ServiceReferenceMapper<K, ? super SR> serviceReferenceMapper,
			ServiceTrackerCustomizer<SR, S> serviceTrackerCustomizer,
			Comparator<ServiceReference<SR>> comparator)
		throws InvalidSyntaxException {

		return new ServiceTrackerMapImpl<>(
			bundleContext, clazz, filterString, serviceReferenceMapper,
			serviceTrackerCustomizer,
			new SingleValueServiceTrackerBucketFactory<SR, S>(comparator),
			null);
	}

	public static <SR, S> ServiceTrackerMap<String, S> singleValueMap(
			BundleContext bundleContext, Class<SR> clazz, String propertyKey,
			ServiceTrackerCustomizer<SR, S> serviceTrackerCustomizer)
		throws InvalidSyntaxException {

		return new ServiceTrackerMapImpl<>(
			bundleContext, clazz, "(" + propertyKey + "=*)",
			new PropertyServiceReferenceMapper<String, SR>(propertyKey),
			serviceTrackerCustomizer,
			new SingleValueServiceTrackerBucketFactory<SR, S>(), null);
	}

}