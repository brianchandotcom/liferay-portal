/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.aop.internal;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.spring.transaction.TransactionExecutor;

import java.util.Arrays;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.Filter;
import org.osgi.framework.InvalidSyntaxException;
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
@Component(service = {})
public class AopServiceManager {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_transactionExecutorServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, TransactionExecutor.class, null,
				(serviceReference, emitter) -> {
					Bundle bundle = serviceReference.getBundle();

					emitter.emit(bundle.getBundleId());
				});

		_aopServiceServiceTracker = new ServiceTracker<>(
			bundleContext, AopService.class,
			new AopServiceServiceTrackerCustomizer());

		_aopServiceServiceTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_aopServiceServiceTracker.close();

		_transactionExecutorServiceTrackerMap.close();
	}

	private ServiceTracker<AopService, AopServiceRegistrar>
		_aopServiceServiceTracker;
	private BundleContext _bundleContext;

	@Reference(target = "(&(bean.id=transactionExecutor)(original.bean=true))")
	private TransactionExecutor _portalTransactionExecutor;

	private ServiceTrackerMap<Long, TransactionExecutor>
		_transactionExecutorServiceTrackerMap;

	private static class TransactionExecutorServiceTracker
		extends ServiceTracker<TransactionExecutor, TransactionExecutor> {

		@Override
		public TransactionExecutor addingService(
			ServiceReference<TransactionExecutor>
				transactionExecutorServiceReference) {

			_aopServiceRegistrar.register(
				context.getService(transactionExecutorServiceReference));

			context.ungetService(transactionExecutorServiceReference);

			close();

			return null;
		}

		private static Filter _createFilter(
			BundleContext bundleContext, String filterString) {

			try {
				return bundleContext.createFilter(filterString);
			}
			catch (InvalidSyntaxException invalidSyntaxException) {
				throw new IllegalArgumentException(invalidSyntaxException);
			}
		}

		private TransactionExecutorServiceTracker(
			BundleContext bundleContext, Long bundleId,
			AopServiceRegistrar aopServiceRegistrar) {

			super(
				bundleContext,
				_createFilter(
					bundleContext,
					StringBundler.concat(
						"(&(objectClass=", TransactionExecutor.class.getName(),
						")(service.bundleid=", bundleId, "))")),
				null);

			_aopServiceRegistrar = aopServiceRegistrar;
		}

		private final AopServiceRegistrar _aopServiceRegistrar;

	}

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
						"Unable to register ", aopService.getClass(),
						" without a service interface"));
			}

			AopServiceRegistrar aopServiceRegistrar = new AopServiceRegistrar(
				serviceReference, aopService, aopInterfaces);

			if (aopServiceRegistrar.isLiferayService()) {
				Long bundleId = (Long)serviceReference.getProperty(
					Constants.SERVICE_BUNDLEID);

				TransactionExecutor transactionExecutor =
					_transactionExecutorServiceTrackerMap.getService(bundleId);

				if (transactionExecutor == null) {
					ServiceTracker<?, ?> serviceTracker =
						new TransactionExecutorServiceTracker(
							_bundleContext, bundleId, aopServiceRegistrar);

					serviceTracker.open();
				}
				else {
					aopServiceRegistrar.register(transactionExecutor);
				}
			}
			else {
				aopServiceRegistrar.register(_portalTransactionExecutor);
			}

			return aopServiceRegistrar;
		}

		@Override
		public void modifiedService(
			ServiceReference<AopService> serviceReference,
			AopServiceRegistrar aopServiceRegistrar) {

			aopServiceRegistrar.updateProperties();
		}

		@Override
		public void removedService(
			ServiceReference<AopService> serviceReference,
			AopServiceRegistrar aopServiceRegistrar) {

			aopServiceRegistrar.unregister();

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
							"Unable to proxy ", aopServiceClass, " because ",
							aopInterface, " is not an interface"));
				}

				if (!aopInterface.isAssignableFrom(aopServiceClass)) {
					throw new IllegalArgumentException(
						StringBundler.concat(
							"Unable to proxy ", aopServiceClass, " because ",
							aopInterface, " is not implemented"));
				}

				if (aopInterface == AopService.class) {
					throw new IllegalArgumentException(
						"Do not include AopService in service interfaces");
				}
			}

			return Arrays.copyOf(aopInterfaces, aopInterfaces.length);
		}

	}

}