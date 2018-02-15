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
import java.util.function.Function;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Carlos Sierra Andrés
 */
public class ServiceTrackerMapBuilderFactory {

	public interface Filterable<K, SR, NR> {

		public Step2<K, SR, NR, ?> withFilter(String filter);

	}

	public interface Final<K, SR, NR, R> {

		public ServiceTrackerMap<K, R> build();

		public Final<K, SR, NR, R> open();

		public Final<K, SR, NR, R> withListener(
			ServiceTrackerMapListener<K, NR, R> serviceTrackerMapListener);

	}

	public interface Step2<K, SR, NR, R> {

		public Final<K, SR, NR, List<NR>> multiValue();

		public Final<K, SR, NR, List<NR>> multiValue(
			Comparator<ServiceReference<SR>> comparator);

		public Final<K, SR, NR, NR> singleValue();

		public Final<K, SR, NR, NR> singleValue(
			Comparator<ServiceReference<SR>> comparator);

		public <R> Final<K, SR, NR, R> withFactory(
			ServiceTrackerBucketFactory<SR, NR, R> bucketFactory);

	}

	public interface Tracking<SR, NR> {

		public Step2<String, SR, NR, NR> mapByProperty(String property);

		public <NR> Tracking<SR, NR> newTracking(
			Function<BundleContext, ServiceTrackerCustomizer<SR, NR>>
				customizer);

		public <NR> Tracking<SR, NR> newTracking(
			ServiceTrackerCustomizer<SR, NR> customizer);

		public <K> Filterable<K, SR, NR> withMapper(
			Function<BundleContext, ServiceReferenceMapper<K, SR>> customizer);

		public <K> Filterable<K, SR, NR> withMapper(
			ServiceReferenceMapper<K, SR> mapper);

	}

	public interface TrackingBuilder {

		public static <T> Tracking<T, T> clazz(
			BundleContext bundleContext, Class<T> clazz) {

			return new TrackingImpl<>(bundleContext, clazz, null, null, null);
		}

		public static <T> Tracking<T, T> clazz(
			BundleContext bundleContext, Class<T> clazz, String filter) {

			return new TrackingImpl<>(bundleContext, clazz, filter, null, null);
		}

		public static Tracking<Object, Object> clazz(
			BundleContext bundleContext, String className) {

			return new TrackingImpl<>(
				bundleContext, Object.class, "(objectClass=" + className + ")",
				null, null);
		}

		public static Tracking<?, ?> filter(
			BundleContext bundleContext, String filter) {

			return new TrackingImpl<>(
				bundleContext, Object.class, filter, null, null);
		}

	}

	private static class Step2Impl<K, SR, NR, R>
		implements Step2<K, SR, NR, R>, Filterable<K, SR, NR> {

		public Step2Impl(
			BundleContext bundleContext, TrackingImpl<SR, NR> trackingImpl,
			ServiceReferenceMapper<K, SR> mapper, String filter) {

			_bundleContext = bundleContext;
			_trackingImpl = trackingImpl;
			_mapper = mapper;
			_filter = filter;
		}

		@Override
		public Final<K, SR, NR, List<NR>> multiValue() {
			return new FinalImpl<>(
				new MultiValueServiceTrackerBucketFactory<>(), null, false);
		}

		@Override
		public Final<K, SR, NR, List<NR>> multiValue(
			Comparator<ServiceReference<SR>> comparator) {

			return new FinalImpl<>(
				new MultiValueServiceTrackerBucketFactory<>(comparator), null,
				false);
		}

		@Override
		public Final<K, SR, NR, NR> singleValue() {
			return new FinalImpl<>(
				new SingleValueServiceTrackerBucketFactory<>(), null, false);
		}

		@Override
		public Final<K, SR, NR, NR> singleValue(
			Comparator<ServiceReference<SR>> comparator) {

			return new FinalImpl<>(
				new SingleValueServiceTrackerBucketFactory<>(comparator), null,
				false);
		}

		@Override
		public <R> Final<K, SR, NR, R> withFactory(
			ServiceTrackerBucketFactory<SR, NR, R> bucketFactory) {

			return new FinalImpl<>(bucketFactory, null, false);
		}

		@Override
		public Step2<K, SR, NR, R> withFilter(String filter) {
			return new Step2Impl<>(
				_bundleContext, _trackingImpl, _mapper, filter);
		}

		private final BundleContext _bundleContext;
		private final String _filter;
		private final ServiceReferenceMapper<K, SR> _mapper;
		private final TrackingImpl<SR, NR> _trackingImpl;

		private class FinalImpl<K, SR, NR, R> implements Final<K, SR, NR, R> {

			public FinalImpl(
				ServiceTrackerBucketFactory<SR, NR, R> bucketFactory,
				ServiceTrackerMapListener<K, NR, R>
					serviceTrackerMapListener, boolean open) {

				_bucketFactory = bucketFactory;
				_serviceTrackerMapListener = serviceTrackerMapListener;
				_open = open;
			}

			@Override
			public ServiceTrackerMap<K, R> build() {
				ServiceReferenceMapper<K, SR> serviceReferenceMapper =
					getServiceReferenceMapper();
				ServiceTrackerCustomizer<SR, NR> serviceTrackerCustomizer =
					getServiceTrackerCustomizer();
				ServiceTrackerMapListener<K, NR, R> serviceTrackerMapListener =
					getServiceTrackerMapListener();
				Class<SR> trackingClass = getTrackingClass();
				String filter = getFilter();

				ServiceTrackerMap<K, R> serviceTrackerMap =
					new ServiceTrackerMapImpl<>(
						_bundleContext, trackingClass, filter,
						serviceReferenceMapper, serviceTrackerCustomizer,
						getBucketFactory(), serviceTrackerMapListener);

				if (_open) {
					serviceTrackerMap.open();
				}

				return serviceTrackerMap;
			}

			public ServiceTrackerBucketFactory<SR, NR, R> getBucketFactory() {
				return _bucketFactory;
			}

			public BundleContext getBundleContext() {
				return _bundleContext;
			}

			public String getFilter() {
				return _filter;
			}

			public ServiceReferenceMapper<K, SR> getServiceReferenceMapper() {
				return (ServiceReferenceMapper<K, SR>)_mapper;
			}

			public ServiceTrackerCustomizer<SR, NR>
				getServiceTrackerCustomizer() {

				ServiceTrackerCustomizer<SR, NR> customizer =
					(ServiceTrackerCustomizer<SR, NR>)
						_trackingImpl._customizer;

				if (customizer == null) {
					return new DefaultServiceTrackerCustomizer(_bundleContext);
				}

				return customizer;
			}

			public ServiceTrackerMapListener<K, NR, R>
				getServiceTrackerMapListener() {

				return _serviceTrackerMapListener;
			}

			public Class<SR> getTrackingClass() {
				return (Class<SR>)_trackingImpl._clazz;
			}

			@Override
			public Final<K, SR, NR, R> open() {
				return new FinalImpl<>(
					_bucketFactory, _serviceTrackerMapListener, true);
			}

			@Override
			public Final<K, SR, NR, R> withListener(
				ServiceTrackerMapListener<K, NR, R> serviceTrackerMapListener) {

				return new FinalImpl<>(
					_bucketFactory, serviceTrackerMapListener, _open);
			}

			private final ServiceTrackerBucketFactory<SR, NR, R> _bucketFactory;
			private final boolean _open;
			private final ServiceTrackerMapListener<K, NR, R>
				_serviceTrackerMapListener;

		}

	}

	private static class TrackingImpl<T, NR> implements Tracking<T, NR> {

		@Override
		public Step2<String, T, NR, NR> mapByProperty(String property) {
			if (_filter == null) {
				return new Step2Impl<>(
					_bundleContext, this,
					new PropertyServiceReferenceMapper<>(property),
					"(" + property + "=*)");
			}
			else {
				return new Step2Impl<>(
					_bundleContext, this,
					new PropertyServiceReferenceMapper<>(property), _filter);
			}
		}

		@Override
		public <NR> Tracking<T, NR> newTracking(
			Function<BundleContext, ServiceTrackerCustomizer<T, NR>> function) {

			return new TrackingImpl<>(
				_bundleContext, _clazz, _filter, null, function);
		}

		@Override
		public <NR> Tracking<T, NR> newTracking(
			ServiceTrackerCustomizer<T, NR> customizer) {

			return new TrackingImpl<>(
				_bundleContext, _clazz, _filter, customizer, null);
		}

		@Override
		public <K> Filterable<K, T, NR> withMapper(
			Function<BundleContext, ServiceReferenceMapper<K, T>> function) {

			return new Step2Impl<>(
				_bundleContext, this, function.apply(_bundleContext), null);
		}

		@Override
		public <K> Filterable<K, T, NR> withMapper(
			ServiceReferenceMapper<K, T> mapper) {

			return new Step2Impl<>(_bundleContext, this, mapper, null);
		}

		private TrackingImpl(
			BundleContext bundleContext, Class<T> clazz, String filter,
			ServiceTrackerCustomizer<T, NR> customizer,
			Function<BundleContext, ServiceTrackerCustomizer<T, NR>> function) {

			_bundleContext = bundleContext;
			_clazz = clazz;
			_filter = filter;

			if (function == null) {
				_customizer = customizer;
			}
			else {
				_customizer = function.apply(bundleContext);
			}
		}

		private final BundleContext _bundleContext;
		private final Class<T> _clazz;
		private final ServiceTrackerCustomizer<T, NR> _customizer;
		private final String _filter;

	}

}