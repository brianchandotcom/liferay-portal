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

package com.liferay.portal.aop.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.monitoring.ServiceMonitoringControl;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.spring.transaction.TransactionExecutor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Preston Crary
 */
@Component(immediate = true, service = {})
public class AopServiceManager {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_aopServiceServiceTracker = new ServiceTracker<>(
			bundleContext, AopService.class,
			new AopServiceServiceTrackerCustomizer());

		_aopServiceServiceTracker.open();

		_transactionExecutorServiceTracker = new ServiceTracker<>(
			bundleContext, TransactionExecutor.class,
			new TransactionExecutorServiceTrackerCustomizer());

		_transactionExecutorServiceTracker.open(true);
	}

	@Deactivate
	protected void deactivate() {
		_aopServiceServiceTracker.close();

		_transactionExecutorServiceTracker.close();
	}

	private final Map<Object, AopServiceResolver> _aopDependencyResolvers =
		new ConcurrentHashMap<>();
	private ServiceTracker<AopService, AopServiceRegistrar>
		_aopServiceServiceTracker;
	private BundleContext _bundleContext;

	@Reference(target = "(original.bean=true)")
	private TransactionExecutor _portalTransactionExecutor;

	@Reference
	private ServiceMonitoringControl _serviceMonitoringControl;

	private ServiceTracker<TransactionExecutor, TransactionExecutorHolder>
		_transactionExecutorServiceTracker;

	private class AopServiceServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<AopService, AopServiceRegistrar> {

		@Override
		public AopServiceRegistrar addingService(
			ServiceReference<AopService> serviceReference) {

			AopService aopService = _bundleContext.getService(serviceReference);

			Class<?>[] aopInterfaces = _getAopInterfaces(aopService);

			if (aopInterfaces.length == 0) {
				throw new IllegalArgumentException(
					StringBundler.concat(
						"Cannot register ", aopService.getClass(),
						" without a service interface"));
			}

			AopServiceRegistrar aopServiceRegistrar = new AopServiceRegistrar(
				serviceReference, aopService, aopInterfaces);

			if (aopServiceRegistrar.isLiferayService()) {
				_aopDependencyResolvers.compute(
					serviceReference.getProperty(Constants.SERVICE_BUNDLEID),
					(bundleId, aopServiceResolver) -> {
						if (aopServiceResolver == null) {
							aopServiceResolver = new AopServiceResolver(
								_serviceMonitoringControl);
						}

						aopServiceResolver.addAopAopServiceRegistrar(
							aopServiceRegistrar);

						return aopServiceResolver;
					});
			}
			else {
				aopServiceRegistrar.register(
					_portalTransactionExecutor, _serviceMonitoringControl);
			}

			return aopServiceRegistrar;
		}

		@Override
		public void modifiedService(
			ServiceReference<AopService> serviceReference,
			AopServiceRegistrar aopServiceRegistrar) {

			if (aopServiceRegistrar.isLiferayService()) {
				_aopDependencyResolvers.compute(
					serviceReference.getProperty(Constants.SERVICE_BUNDLEID),
					(bundleId, aopServiceResolver) -> {
						aopServiceRegistrar.updateProperties();

						return aopServiceResolver;
					});
			}
			else {
				aopServiceRegistrar.updateProperties();
			}
		}

		@Override
		public void removedService(
			ServiceReference<AopService> serviceReference,
			AopServiceRegistrar aopServiceRegistrar) {

			_aopDependencyResolvers.computeIfPresent(
				serviceReference.getProperty(Constants.SERVICE_BUNDLEID),
				(bundleId, aopServiceResolver) -> {
					aopServiceResolver.removeAopServiceRegistrar(
						aopServiceRegistrar);

					if (aopServiceResolver.isEmpty()) {
						return null;
					}

					return aopServiceResolver;
				});

			_bundleContext.ungetService(serviceReference);
		}

		private Class<?>[] _getAopInterfaces(AopService aopService) {
			Class<?>[] aopInterfaces = aopService.getAopInterfaces();

			Class<? extends AopService> aopServiceClass = aopService.getClass();

			if (ArrayUtil.isEmpty(aopInterfaces)) {
				return ArrayUtil.remove(
					aopServiceClass.getInterfaces(), AopService.class);
			}

			for (Class<?> aopInterface : aopInterfaces) {
				if (!aopInterface.isInterface()) {
					throw new IllegalArgumentException(
						StringBundler.concat(
							"Cannot proxy ", aopServiceClass, " because ",
							aopInterface, " is not an interface"));
				}

				if (!aopInterface.isAssignableFrom(aopServiceClass)) {
					throw new IllegalArgumentException(
						StringBundler.concat(
							"Cannot proxy ", aopServiceClass, " because ",
							aopInterface, " is not implemented"));
				}

				if (aopInterface == AopService.class) {
					throw new IllegalArgumentException(
						"Do not include AopService in service interfaces");
				}
			}

			return aopInterfaces;
		}

	}

	private class TransactionExecutorServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<TransactionExecutor, TransactionExecutorHolder> {

		@Override
		public TransactionExecutorHolder addingService(
			ServiceReference<TransactionExecutor> serviceReference) {

			TransactionExecutor transactionExecutor = _bundleContext.getService(
				serviceReference);

			TransactionExecutorHolder transactionExecutorHolder =
				new TransactionExecutorHolder(
					serviceReference, transactionExecutor);

			_aopDependencyResolvers.compute(
				serviceReference.getProperty(Constants.SERVICE_BUNDLEID),
				(bundleId, aopServiceResolver) -> {
					if (aopServiceResolver == null) {
						aopServiceResolver = new AopServiceResolver(
							_serviceMonitoringControl);
					}

					aopServiceResolver.addTransactionExecutorHolder(
						transactionExecutorHolder);

					return aopServiceResolver;
				});

			return transactionExecutorHolder;
		}

		@Override
		public void modifiedService(
			ServiceReference<TransactionExecutor> serviceReference,
			TransactionExecutorHolder transactionExecutorHolder) {
		}

		@Override
		public void removedService(
			ServiceReference<TransactionExecutor> serviceReference,
			TransactionExecutorHolder transactionExecutorHolder) {

			_aopDependencyResolvers.computeIfPresent(
				serviceReference.getProperty(Constants.SERVICE_BUNDLEID),
				(bundleId, aopServiceResolver) -> {
					aopServiceResolver.removeTransactionExecutorHolder(
						transactionExecutorHolder);

					if (aopServiceResolver.isEmpty()) {
						return null;
					}

					return aopServiceResolver;
				});

			_bundleContext.ungetService(serviceReference);
		}

	}

}