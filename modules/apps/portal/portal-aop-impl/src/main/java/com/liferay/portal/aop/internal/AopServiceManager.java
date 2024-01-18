/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.aop.internal;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.osgi.util.osgi.commands.OSGiCommands;
import com.liferay.petra.io.unsync.UnsyncStringWriter;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.events.StartupHelperUtil;
import com.liferay.portal.kernel.concurrent.SystemExecutorServiceUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.osgi.debug.SystemChecker;
import com.liferay.portal.spring.transaction.TransactionExecutor;

import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;
import java.util.function.Supplier;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.Filter;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
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

		_parallel = StartupHelperUtil.isDBWarmed();

		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, TransactionExecutor.class, null,
			(serviceReference, emitter) -> {
				Bundle bundle = serviceReference.getBundle();

				emitter.emit(bundle.getBundleId());
			});

		_aopServiceServiceTracker = new ServiceTracker<>(
			bundleContext, AopService.class,
			new AopServiceServiceTrackerCustomizer());

		_aopServiceServiceTracker.open();

		_serviceRegistrations.add(
			bundleContext.registerService(
				SystemChecker.class, new AopServiceRegistrarSystemChecker(),
				MapUtil.singletonDictionary(Constants.SERVICE_RANKING, 1)));

		_serviceRegistrations.add(
			bundleContext.registerService(
				OSGiCommands.class, new AopServiceRegistrarOSGiCommands(),
				HashMapDictionaryBuilder.put(
					"osgi.command.function", "failures"
				).put(
					"osgi.command.scope", "aop"
				).build()));
	}

	@Deactivate
	protected void deactivate() {
		for (ServiceRegistration<?> serviceRegistration :
				_serviceRegistrations) {

			serviceRegistration.unregister();
		}

		_aopServiceServiceTracker.close();

		_serviceTrackerMap.close();
	}

	@Modified
	protected void modified(Map<String, Object> properties) {
		_parallel = GetterUtil.getBoolean(
			properties.get("parallel.enabled"), StartupHelperUtil.isDBWarmed());
	}

	private String _collectRegistrationFailures() {
		Throwable aggregatedThrowable = null;

		Map<?, Supplier<AopServiceRegistrar>> trackedMap =
			_aopServiceServiceTracker.getTracked();

		for (Supplier<AopServiceRegistrar> supplier : trackedMap.values()) {
			try {
				supplier.get();
			}
			catch (Throwable throwable) {
				if (aggregatedThrowable == null) {
					aggregatedThrowable = throwable;
				}
				else {
					aggregatedThrowable.addSuppressed(throwable);
				}
			}
		}

		if (aggregatedThrowable == null) {
			return null;
		}

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		aggregatedThrowable.printStackTrace(
			new PrintWriter(unsyncStringWriter));

		return unsyncStringWriter.toString();
	}

	private ServiceTracker<AopService, Supplier<AopServiceRegistrar>>
		_aopServiceServiceTracker;
	private BundleContext _bundleContext;
	private volatile boolean _parallel;

	@Reference(target = "(&(bean.id=transactionExecutor)(original.bean=true))")
	private TransactionExecutor _portalTransactionExecutor;

	private final List<ServiceRegistration<?>> _serviceRegistrations =
		new ArrayList<>();
	private ServiceTrackerMap<Long, TransactionExecutor> _serviceTrackerMap;

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

	private class AopServiceRegistrarOSGiCommands implements OSGiCommands {

		public String failures() {
			return _collectRegistrationFailures();
		}

	}

	private class AopServiceRegistrarSystemChecker implements SystemChecker {

		@Override
		public String check() {
			return _collectRegistrationFailures();
		}

		@Override
		public String getName() {
			return "AOP Service Registrar System Checker";
		}

		@Override
		public String getOSGiCommand() {
			return "aop:failures";
		}

		@Override
		public String toString() {
			return getName();
		}

	}

	private class AopServiceServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<AopService, Supplier<AopServiceRegistrar>> {

		@Override
		public Supplier<AopServiceRegistrar> addingService(
			ServiceReference<AopService> serviceReference) {

			AopService aopService = _bundleContext.getService(serviceReference);

			Class<?>[] aopInterfaces = _getAopInterfaces(aopService);

			if (aopInterfaces.length == 0) {
				throw new IllegalArgumentException(
					StringBundler.concat(
						"Unable to register ", aopService.getClass(),
						" without a service interface"));
			}

			FutureTask<AopServiceRegistrar> futureTask = new FutureTask<>(
				() -> {
					AopServiceRegistrar aopServiceRegistrar =
						new AopServiceRegistrar(
							serviceReference, aopService, aopInterfaces);

					if (aopServiceRegistrar.isLiferayService()) {
						Long bundleId = (Long)serviceReference.getProperty(
							Constants.SERVICE_BUNDLEID);

						TransactionExecutor transactionExecutor =
							_serviceTrackerMap.getService(bundleId);

						if (transactionExecutor == null) {
							ServiceTracker<?, ?> serviceTracker =
								new TransactionExecutorServiceTracker(
									_bundleContext, bundleId,
									aopServiceRegistrar);

							serviceTracker.open();
						}
						else {
							aopServiceRegistrar.register(transactionExecutor);
						}
					}
					else {
						aopServiceRegistrar.register(
							_portalTransactionExecutor);
					}

					return aopServiceRegistrar;
				});

			if (_parallel) {
				ExecutorService executorService =
					SystemExecutorServiceUtil.getExecutorService();

				executorService.submit(futureTask);
			}
			else {
				futureTask.run();
			}

			return () -> {
				try {
					return futureTask.get();
				}
				catch (Exception exception) {
					return ReflectionUtil.throwException(exception);
				}
			};
		}

		@Override
		public void modifiedService(
			ServiceReference<AopService> serviceReference,
			Supplier<AopServiceRegistrar> aopServiceRegistrarSupplier) {

			AopServiceRegistrar aopServiceRegistrar =
				aopServiceRegistrarSupplier.get();

			aopServiceRegistrar.updateProperties();
		}

		@Override
		public void removedService(
			ServiceReference<AopService> serviceReference,
			Supplier<AopServiceRegistrar> aopServiceRegistrarSupplier) {

			AopServiceRegistrar aopServiceRegistrar =
				aopServiceRegistrarSupplier.get();

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