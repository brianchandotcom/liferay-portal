/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osgi.util.service;

import com.liferay.petra.concurrent.DCLSingleton;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Supplier;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.Constants;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.SynchronousBundleListener;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Shuyang Zhou
 */
public class Snapshot<T> {

	@SuppressWarnings("unchecked")
	public static <T> Class<T> cast(Class<?> clazz) {
		return (Class<T>)clazz;
	}

	public Snapshot(Class<?> holderClass, Class<T> serviceClass) {
		this(holderClass, serviceClass, null);
	}

	public Snapshot(
		Class<?> holderClass, Class<T> serviceClass, String filterString) {

		this(holderClass, serviceClass, filterString, false);
	}

	public Snapshot(
		Class<?> holderClass, Class<T> serviceClass, String filterString,
		boolean dynamic) {

		if (dynamic) {
			DCLSingleton<ServiceTracker<T, T>> serviceTrackerDCLSingleton =
				new DCLSingleton<>();

			Supplier<ServiceTracker<T, T>> serviceTrackerSupplier = () -> {
				ServiceTracker<T, T> serviceTracker = null;

				Bundle bundle = FrameworkUtil.getBundle(holderClass);

				BundleContext bundleContext = bundle.getBundleContext();

				if (filterString == null) {
					serviceTracker = new ServiceTracker<>(
						bundleContext, serviceClass, null);
				}
				else {
					try {
						serviceTracker = new ServiceTracker<>(
							bundleContext,
							bundleContext.createFilter(
								StringBundler.concat(
									"(&(objectClass=", serviceClass.getName(),
									")", filterString, ")")),
							null);
					}
					catch (InvalidSyntaxException invalidSyntaxException) {
						return ReflectionUtil.throwException(
							invalidSyntaxException);
					}
				}

				serviceTracker.open();

				_dclSingletonBundleListener.addDCLSingleton(
					bundle.getBundleId(), serviceTrackerDCLSingleton);

				return serviceTracker;
			};

			_serivceSupplier = () -> {
				ServiceTracker<T, T> serviceTracker =
					serviceTrackerDCLSingleton.getSingleton(
						serviceTrackerSupplier);

				return serviceTracker.getService();
			};
		}
		else {
			DCLSingleton<T> serviceDCLSingleton = new DCLSingleton<>();

			_serivceSupplier = () -> serviceDCLSingleton.getSingleton(
				() -> {
					Bundle bundle = FrameworkUtil.getBundle(holderClass);

					BundleContext bundleContext = bundle.getBundleContext();

					ServiceReference<T> serviceReference = _getServiceReference(
						bundleContext, serviceClass, filterString);

					if (serviceReference == null) {
						return null;
					}

					try {
						bundleContext.addServiceListener(
							new ServiceListener() {

								@Override
								public void serviceChanged(
									ServiceEvent serviceEvent) {

									if (serviceEvent.getType() ==
											ServiceEvent.UNREGISTERING) {

										serviceDCLSingleton.destroy(null);

										bundleContext.removeServiceListener(
											this);
									}
								}

							},
							StringBundler.concat(
								"(", Constants.SERVICE_ID, "=",
								serviceReference.getProperty(
									Constants.SERVICE_ID),
								")"));
					}
					catch (InvalidSyntaxException invalidSyntaxException) {
						return ReflectionUtil.throwException(
							invalidSyntaxException);
					}

					return bundleContext.getService(serviceReference);
				});
		}
	}

	public T get() {
		return _serivceSupplier.get();
	}

	private ServiceReference<T> _getServiceReference(
		BundleContext bundleContext, Class<T> serviceClass,
		String filterString) {

		if (filterString == null) {
			return bundleContext.getServiceReference(serviceClass);
		}

		try {
			Collection<ServiceReference<T>> serviceReferences =
				bundleContext.getServiceReferences(serviceClass, filterString);

			if (serviceReferences.isEmpty()) {
				return null;
			}

			Iterator<ServiceReference<T>> iterator =
				serviceReferences.iterator();

			return iterator.next();
		}
		catch (InvalidSyntaxException invalidSyntaxException) {
			return ReflectionUtil.throwException(invalidSyntaxException);
		}
	}

	private static final DCLSingletonBundleListener
		_dclSingletonBundleListener = new DCLSingletonBundleListener();

	static {
		Bundle bundle = FrameworkUtil.getBundle(Snapshot.class);

		BundleContext bundleContext = bundle.getBundleContext();

		bundleContext.addBundleListener(_dclSingletonBundleListener);
	}

	private final Supplier<T> _serivceSupplier;

	private static class DCLSingletonBundleListener
		implements SynchronousBundleListener {

		public void addDCLSingleton(
			long bundleId, DCLSingleton<?> dclSingleton) {

			Queue<DCLSingleton<?>> dclSingletons = _dclSingletons.get(bundleId);

			if (dclSingletons == null) {
				dclSingletons = new ConcurrentLinkedQueue<>();

				Queue<DCLSingleton<?>> previousDCLSingletons =
					_dclSingletons.putIfAbsent(bundleId, dclSingletons);

				if (previousDCLSingletons != null) {
					dclSingletons = previousDCLSingletons;
				}
			}

			dclSingletons.add(dclSingleton);
		}

		@Override
		public void bundleChanged(BundleEvent bundleEvent) {
			if (bundleEvent.getType() != BundleEvent.STOPPING) {
				return;
			}

			Bundle bundle = bundleEvent.getBundle();

			Queue<DCLSingleton<?>> dclSingletons = _dclSingletons.remove(
				bundle.getBundleId());

			if (dclSingletons == null) {
				return;
			}

			for (DCLSingleton<?> dclSingleton : dclSingletons) {
				dclSingleton.destroy(null);
			}
		}

		private Map<Long, Queue<DCLSingleton<?>>> _dclSingletons =
			new ConcurrentHashMap<>();

	}

}