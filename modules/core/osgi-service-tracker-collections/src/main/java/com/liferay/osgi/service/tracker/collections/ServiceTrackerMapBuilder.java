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

package com.liferay.osgi.service.tracker.collections;

import com.liferay.osgi.service.tracker.collections.internal.DefaultServiceTrackerCustomizer;
import com.liferay.osgi.service.tracker.collections.internal.map.MultiValueServiceTrackerBucketFactory;
import com.liferay.osgi.service.tracker.collections.internal.map.ServiceTrackerMapImpl;
import com.liferay.osgi.service.tracker.collections.internal.map.SingleValueServiceTrackerBucketFactory;
import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerBucketFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapListener;

import java.util.Comparator;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Carlos Sierra Andrés
 */
public class ServiceTrackerMapBuilder {

	public interface Collector<K, SR, NR, R> {

		public ServiceTrackerMap<K, R> build();

		public Collector<K, SR, NR, R> newCollector(
			ServiceTrackerMapListener<K, NR, R> serviceTrackerMapListener);

	}

	public interface Mapper<K, SR, NR, R> {

		public <R> Collector<K, SR, NR, R> collect(
			ServiceTrackerBucketFactory<SR, NR, R> serviceTrackerBucketFactory);

		public Collector<K, SR, NR, List<NR>> collectMultiValue();

		public Collector<K, SR, NR, List<NR>> collectMultiValue(
			Comparator<ServiceReference<SR>> comparator);

		public Collector<K, SR, NR, NR> collectSingleValue();

		public Collector<K, SR, NR, NR> collectSingleValue(
			Comparator<ServiceReference<SR>> comparator);

	}

	public interface Selector<SR, NR> {

		public <K> Mapper<K, SR, NR, ?> map(
			ServiceReferenceMapper<K, SR> serviceReferenceMapper);

		public Mapper<String, SR, NR, NR> map(String propertyKey);

		public <NR> Selector<SR, NR> newSelector(
			ServiceTrackerCustomizer<SR, NR> serviceTrackerCustomizer);

		public Selector<SR, NR> newSelector(String filterString);

	}

	public interface SelectorFactory {

		public static <T> Selector<T, T> newSelector(
			BundleContext bundleContext, Class<T> clazz) {

			return new SelectorImpl<>(bundleContext, clazz, null, null);
		}

		public static <T> Selector<T, T> newSelector(
			BundleContext bundleContext, Class<T> clazz, String filterString) {

			return new SelectorImpl<>(bundleContext, clazz, filterString, null);
		}

		public static Selector<Object, Object> newSelector(
			BundleContext bundleContext, String className) {

			return new SelectorImpl<>(
				bundleContext, Object.class, "(objectClass=" + className + ")",
				null);
		}

		public static Selector<?, ?> newSelectorWithFilter(
			BundleContext bundleContext, String filterString) {

			return new SelectorImpl<>(
				bundleContext, Object.class, filterString, null);
		}

	}

	private static class CollectorImpl<K, SR, NR, R>
		implements Collector<K, SR, NR, R> {

		@Override
		public ServiceTrackerMap<K, R> build() {
			return new ServiceTrackerMapImpl<>(
				_bundleContext, _clazz, _filterString, _serviceReferenceMapper,
				_serviceTrackerCustomizer, _serviceTrackerBucketFactory,
				_serviceTrackerMapListener);
		}

		@Override
		public Collector<K, SR, NR, R> newCollector(
			ServiceTrackerMapListener<K, NR, R> serviceTrackerMapListener) {

			return new CollectorImpl<>(
				_bundleContext, _clazz, _serviceTrackerCustomizer,
				_filterString, _serviceReferenceMapper,
				_serviceTrackerBucketFactory, serviceTrackerMapListener);
		}

		private CollectorImpl(
			BundleContext bundleContext, Class<SR> clazz,
			ServiceTrackerCustomizer<SR, NR> serviceTrackerCustomizer,
			String filterString,
			ServiceReferenceMapper<K, SR> serviceReferenceMapper,
			ServiceTrackerBucketFactory<SR, NR, R> serviceTrackerBucketFactory,
			ServiceTrackerMapListener<K, NR, R> serviceTrackerMapListener) {

			_bundleContext = bundleContext;
			_clazz = clazz;

			if (serviceTrackerCustomizer == null) {
				_serviceTrackerCustomizer = new DefaultServiceTrackerCustomizer(
					_bundleContext);
			}
			else {
				_serviceTrackerCustomizer = serviceTrackerCustomizer;
			}

			_filterString = filterString;
			_serviceReferenceMapper = serviceReferenceMapper;
			_serviceTrackerBucketFactory = serviceTrackerBucketFactory;
			_serviceTrackerMapListener = serviceTrackerMapListener;
		}

		private final BundleContext _bundleContext;
		private final Class<SR> _clazz;
		private final String _filterString;
		private final ServiceReferenceMapper<K, SR> _serviceReferenceMapper;
		private final ServiceTrackerBucketFactory<SR, NR, R>
			_serviceTrackerBucketFactory;
		private final ServiceTrackerCustomizer<SR, NR>
			_serviceTrackerCustomizer;
		private final ServiceTrackerMapListener<K, NR, R>
			_serviceTrackerMapListener;

	}

	private static class MapperImpl<K, SR, NR, R>
		implements Mapper<K, SR, NR, R> {

		@Override
		public <R> Collector<K, SR, NR, R> collect(
			ServiceTrackerBucketFactory<SR, NR, R>
				serviceTrackerBucketFactory) {

			return new CollectorImpl<>(
				_bundleContext, _clazz, _serviceTrackerCustomizer,
				_filterString, _serviceReferenceMapper,
				serviceTrackerBucketFactory, null);
		}

		@Override
		public Collector<K, SR, NR, List<NR>> collectMultiValue() {
			return new CollectorImpl<>(
				_bundleContext, _clazz, _serviceTrackerCustomizer,
				_filterString, _serviceReferenceMapper,
				new MultiValueServiceTrackerBucketFactory<>(), null);
		}

		@Override
		public Collector<K, SR, NR, List<NR>> collectMultiValue(
			Comparator<ServiceReference<SR>> comparator) {

			return new CollectorImpl<>(
				_bundleContext, _clazz, _serviceTrackerCustomizer,
				_filterString, _serviceReferenceMapper,
				new MultiValueServiceTrackerBucketFactory<>(comparator), null);
		}

		@Override
		public Collector<K, SR, NR, NR> collectSingleValue() {
			return new CollectorImpl<>(
				_bundleContext, _clazz, _serviceTrackerCustomizer,
				_filterString, _serviceReferenceMapper,
				new SingleValueServiceTrackerBucketFactory<>(), null);
		}

		@Override
		public Collector<K, SR, NR, NR> collectSingleValue(
			Comparator<ServiceReference<SR>> comparator) {

			return new CollectorImpl<>(
				_bundleContext, _clazz, _serviceTrackerCustomizer,
				_filterString, _serviceReferenceMapper,
				new SingleValueServiceTrackerBucketFactory<>(comparator), null);
		}

		private MapperImpl(
			BundleContext bundleContext, Class<SR> clazz,
			ServiceTrackerCustomizer<SR, NR> serviceTrackerCustomizer,
			ServiceReferenceMapper<K, SR> serviceReferenceMapper,
			String filterString) {

			_bundleContext = bundleContext;
			_clazz = clazz;
			_serviceTrackerCustomizer = serviceTrackerCustomizer;
			_serviceReferenceMapper = serviceReferenceMapper;
			_filterString = filterString;
		}

		private final BundleContext _bundleContext;
		private final Class<SR> _clazz;
		private final String _filterString;
		private final ServiceReferenceMapper<K, SR> _serviceReferenceMapper;
		private final ServiceTrackerCustomizer<SR, NR>
			_serviceTrackerCustomizer;

	}

	private static class SelectorImpl<T, NR> implements Selector<T, NR> {

		@Override
		public <K> Mapper<K, T, NR, ?> map(
			ServiceReferenceMapper<K, T> serviceReferenceMapper) {

			return new MapperImpl<>(
				_bundleContext, _clazz, _serviceTrackerCustomizer,
				serviceReferenceMapper, null);
		}

		@Override
		public Mapper<String, T, NR, NR> map(String propertyKey) {
			String filterString = _filterString;

			if (filterString == null) {
				filterString = "(" + propertyKey + "=*)";
			}

			return new MapperImpl<>(
				_bundleContext, _clazz, _serviceTrackerCustomizer,
				new PropertyServiceReferenceMapper<>(propertyKey),
				filterString);
		}

		@Override
		public <NR> Selector<T, NR> newSelector(
			ServiceTrackerCustomizer<T, NR> serviceTrackerCustomizer) {

			return new SelectorImpl<>(
				_bundleContext, _clazz, _filterString,
				serviceTrackerCustomizer);
		}

		@Override
		public Selector<T, NR> newSelector(String filterString) {
			return new SelectorImpl<>(
				_bundleContext, _clazz, filterString,
				_serviceTrackerCustomizer);
		}

		private SelectorImpl(
			BundleContext bundleContext, Class<T> clazz, String filterString,
			ServiceTrackerCustomizer<T, NR> serviceTrackerCustomizer) {

			_bundleContext = bundleContext;
			_clazz = clazz;
			_filterString = filterString;
			_serviceTrackerCustomizer = serviceTrackerCustomizer;
		}

		private final BundleContext _bundleContext;
		private final Class<T> _clazz;
		private final String _filterString;
		private final ServiceTrackerCustomizer<T, NR> _serviceTrackerCustomizer;

	}

}