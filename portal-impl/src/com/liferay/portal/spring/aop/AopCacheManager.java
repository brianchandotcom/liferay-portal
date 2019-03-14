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

package com.liferay.portal.spring.aop;

import com.liferay.portal.cache.thread.local.ThreadLocalCacheAdvice;
import com.liferay.portal.dao.jdbc.aop.DynamicDataSourceAdvice;
import com.liferay.portal.increment.BufferedIncrementAdvice;
import com.liferay.portal.internal.cluster.ClusterableAdvice;
import com.liferay.portal.internal.cluster.SPIClusterableAdvice;
import com.liferay.portal.kernel.dao.jdbc.aop.DynamicDataSourceTargetSource;
import com.liferay.portal.kernel.resiliency.spi.SPIUtil;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.messaging.async.AsyncAdvice;
import com.liferay.portal.resiliency.service.PortalResiliencyAdvice;
import com.liferay.portal.search.IndexableAdvice;
import com.liferay.portal.security.access.control.AccessControlAdvice;
import com.liferay.portal.service.ServiceContextAdvice;
import com.liferay.portal.spring.transaction.TransactionExecutor;
import com.liferay.portal.spring.transaction.TransactionInterceptor;
import com.liferay.portal.systemevent.SystemEventAdvice;
import com.liferay.portal.util.PropsValues;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Preston Crary
 */
public class AopCacheManager {

	public static AopInvocationHandler create(
		Object target, TransactionExecutor transactionExecutor) {

		synchronized (AopCacheManager.class) {
			AopInvocationHandler aopInvocationHandler =
				new AopInvocationHandler(
					target, _createChainableMethodAdvices(transactionExecutor));

			_aopInvocationHandlers.put(
				aopInvocationHandler, transactionExecutor);

			return aopInvocationHandler;
		}
	}

	public static synchronized void destroy(
		AopInvocationHandler aopInvocationHandler) {

		_aopInvocationHandlers.remove(aopInvocationHandler);
	}

	public static synchronized void reset() {
		for (Map.Entry<AopInvocationHandler, TransactionExecutor> entry :
				_aopInvocationHandlers.entrySet()) {

			AopInvocationHandler aopInvocationHandler = entry.getKey();
			TransactionExecutor transactionExecutor = entry.getValue();

			aopInvocationHandler.setChainableMethodAdvices(
				_createChainableMethodAdvices(transactionExecutor));
		}
	}

	private static ChainableMethodAdvice[] _createChainableMethodAdvices(
		TransactionExecutor transactionExecutor) {

		List<ChainableMethodAdvice> chainableMethodAdvices = new ArrayList<>(
			_chainableMethodAdvices);

		TransactionInterceptor transactionInterceptor =
			new TransactionInterceptor();

		transactionInterceptor.setTransactionExecutor(transactionExecutor);

		chainableMethodAdvices.add(transactionInterceptor);

		return chainableMethodAdvices.toArray(
			new ChainableMethodAdvice[chainableMethodAdvices.size()]);
	}

	private AopCacheManager() {
	}

	private static final Comparator<ChainableMethodAdvice>
		_CHAINABLE_METHOD_ADVICE_COMPARATOR = Comparator.comparing(
			chainableMethodAdvice -> {
				Class<? extends ChainableMethodAdvice> clazz =
					chainableMethodAdvice.getClass();

				return clazz.getName();
			});

	private static final Map<AopInvocationHandler, TransactionExecutor>
		_aopInvocationHandlers = new HashMap<>();

	private static final List<ChainableMethodAdvice> _chainableMethodAdvices =
		new ArrayList<ChainableMethodAdvice>() {
			{
				if (SPIUtil.isSPI()) {
					add(new SPIClusterableAdvice());
				}

				if (PropsValues.CLUSTER_LINK_ENABLED) {
					add(new ClusterableAdvice());
				}

				add(new AccessControlAdvice());

				if (PropsValues.PORTAL_RESILIENCY_ENABLED) {
					add(new PortalResiliencyAdvice());
				}

				AsyncAdvice asyncAdvice = new AsyncAdvice();

				asyncAdvice.setDefaultDestinationName("liferay/async_service");

				add(asyncAdvice);

				add(new ThreadLocalCacheAdvice());

				add(new BufferedIncrementAdvice());

				add(new IndexableAdvice());

				add(new SystemEventAdvice());

				add(new ServiceContextAdvice());

				add(new RetryAdvice());

				DynamicDataSourceTargetSource dynamicDataSourceTargetSource =
					InfrastructureUtil.getDynamicDataSourceTargetSource();

				if (dynamicDataSourceTargetSource != null) {
					DynamicDataSourceAdvice dynamicDataSourceAdvice =
						new DynamicDataSourceAdvice();

					dynamicDataSourceAdvice.setDynamicDataSourceTargetSource(
						dynamicDataSourceTargetSource);

					add(dynamicDataSourceAdvice);
				}

				sort(_CHAINABLE_METHOD_ADVICE_COMPARATOR);
			}
		};

	private static class ChainableMethodAdviceServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<ChainableMethodAdvice, ChainableMethodAdvice> {

		@Override
		public ChainableMethodAdvice addingService(
			ServiceReference<ChainableMethodAdvice> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			ChainableMethodAdvice chainableMethodAdvice = registry.getService(
				serviceReference);

			synchronized (AopCacheManager.class) {
				int index = Collections.binarySearch(
					_chainableMethodAdvices, chainableMethodAdvice,
					_CHAINABLE_METHOD_ADVICE_COMPARATOR);

				if (index < 0) {
					index = -index - 1;
				}

				_chainableMethodAdvices.add(index, chainableMethodAdvice);

				reset();
			}

			return chainableMethodAdvice;
		}

		@Override
		public void modifiedService(
			ServiceReference<ChainableMethodAdvice> serviceReference,
			ChainableMethodAdvice chainableMethodAdvice) {
		}

		@Override
		public void removedService(
			ServiceReference<ChainableMethodAdvice> serviceReference,
			ChainableMethodAdvice chainableMethodAdvice) {

			synchronized (AopCacheManager.class) {
				_chainableMethodAdvices.remove(chainableMethodAdvice);

				reset();
			}

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);
		}

	}

	static {
		Registry registry = RegistryUtil.getRegistry();

		ServiceTracker<?, ?> serviceTracker = registry.trackServices(
			ChainableMethodAdvice.class,
			new ChainableMethodAdviceServiceTrackerCustomizer());

		serviceTracker.open();
	}

}