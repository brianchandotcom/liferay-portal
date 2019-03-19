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
import com.liferay.portal.kernel.aop.ChainableMethodAdvice;
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

	public static synchronized AopInvocationHandler create(
		Object target, TransactionExecutor transactionExecutor) {

		AopInvocationHandler aopInvocationHandler = new AopInvocationHandler(
			target, _createChainableMethodAdvices(transactionExecutor));

		_aopInvocationHandlers.put(aopInvocationHandler, transactionExecutor);

		return aopInvocationHandler;
	}

	public static synchronized void destroy(
		AopInvocationHandler aopInvocationHandler) {

		_aopInvocationHandlers.remove(aopInvocationHandler);
	}

	private static ChainableMethodAdvice[] _createChainableMethodAdvices(
		TransactionExecutor transactionExecutor) {

		ChainableMethodAdvice[] chainableMethodAdvices =
			new ChainableMethodAdvice[_chainableMethodAdvices.size() + 1];

		_chainableMethodAdvices.toArray(chainableMethodAdvices);

		TransactionInterceptor transactionInterceptor =
			new TransactionInterceptor();

		transactionInterceptor.setTransactionExecutor(transactionExecutor);

		chainableMethodAdvices[_chainableMethodAdvices.size()] =
			transactionInterceptor;

		return chainableMethodAdvices;
	}

	private static List<ChainableMethodAdvice>
		_createStaticChainableMethodAdvices() {

		List<ChainableMethodAdvice> chainableMethodAdvices = new ArrayList<>();

		chainableMethodAdvices.add(new AccessControlAdvice());

		AsyncAdvice asyncAdvice = new AsyncAdvice();

		asyncAdvice.setDefaultDestinationName("liferay/async_service");

		chainableMethodAdvices.add(asyncAdvice);

		chainableMethodAdvices.add(new BufferedIncrementAdvice());

		if (PropsValues.CLUSTER_LINK_ENABLED) {
			chainableMethodAdvices.add(new ClusterableAdvice());
		}

		DynamicDataSourceTargetSource dynamicDataSourceTargetSource =
			InfrastructureUtil.getDynamicDataSourceTargetSource();

		if (dynamicDataSourceTargetSource != null) {
			DynamicDataSourceAdvice dynamicDataSourceAdvice =
				new DynamicDataSourceAdvice();

			dynamicDataSourceAdvice.setDynamicDataSourceTargetSource(
				dynamicDataSourceTargetSource);

			chainableMethodAdvices.add(dynamicDataSourceAdvice);
		}

		chainableMethodAdvices.add(new IndexableAdvice());

		if (PropsValues.PORTAL_RESILIENCY_ENABLED) {
			chainableMethodAdvices.add(new PortalResiliencyAdvice());
		}

		chainableMethodAdvices.add(new RetryAdvice());

		chainableMethodAdvices.add(new ServiceContextAdvice());

		if (SPIUtil.isSPI()) {
			chainableMethodAdvices.add(new SPIClusterableAdvice());
		}

		chainableMethodAdvices.add(new SystemEventAdvice());

		chainableMethodAdvices.add(new ThreadLocalCacheAdvice());

		chainableMethodAdvices.sort(_CHAINABLE_METHOD_ADVICE_COMPARATOR);

		return chainableMethodAdvices;
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
		_createStaticChainableMethodAdvices();

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

				_reset();
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

				_reset();
			}

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);
		}

		private static void _reset() {
			for (Map.Entry<AopInvocationHandler, TransactionExecutor> entry :
					_aopInvocationHandlers.entrySet()) {

				AopInvocationHandler aopInvocationHandler = entry.getKey();
				TransactionExecutor transactionExecutor = entry.getValue();

				aopInvocationHandler.setChainableMethodAdvices(
					_createChainableMethodAdvices(transactionExecutor));
			}
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