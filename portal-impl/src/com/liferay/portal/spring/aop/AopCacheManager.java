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
import com.liferay.portal.resiliency.service.PortalResiliencyAdvice;
import com.liferay.portal.search.IndexableAdvice;
import com.liferay.portal.security.access.control.AccessControlAdvice;
import com.liferay.portal.service.ServiceContextAdvice;
import com.liferay.portal.spring.transaction.TransactionHandler;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Preston Crary
 */
public class AopCacheManager {

	public static synchronized AopInvocationHandler create(
		Object target, TransactionHandler transactionHandler) {

		AopInvocationHandler aopInvocationHandler = new AopInvocationHandler(
			target,
			_chainableMethodAdvices.toArray(new ChainableMethodAdvice[0]),
			transactionHandler);

		_aopInvocationHandlers.add(aopInvocationHandler);

		return aopInvocationHandler;
	}

	public static synchronized void destroy(
		AopInvocationHandler aopInvocationHandler) {

		_aopInvocationHandlers.remove(aopInvocationHandler);
	}

	private static List<ChainableMethodAdvice>
		_createStaticChainableMethodAdvices() {

		List<ChainableMethodAdvice> chainableMethodAdvices = new ArrayList<>();

		chainableMethodAdvices.add(new AccessControlAdvice());

		chainableMethodAdvices.add(new BufferedIncrementAdvice());

		if (PropsValues.CLUSTER_LINK_ENABLED) {
			chainableMethodAdvices.add(new ClusterableAdvice());
		}

		DynamicDataSourceTargetSource dynamicDataSourceTargetSource =
			InfrastructureUtil.getDynamicDataSourceTargetSource();

		if (dynamicDataSourceTargetSource != null) {
			chainableMethodAdvices.add(
				new DynamicDataSourceAdvice(dynamicDataSourceTargetSource));
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

	private static final Set<AopInvocationHandler> _aopInvocationHandlers =
		new HashSet<>();
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

			synchronized (AopCacheManager.class) {
				for (AopInvocationHandler aopInvocationHandler :
						_aopInvocationHandlers) {

					aopInvocationHandler.reset();
				}
			}
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
			ChainableMethodAdvice[] chainableMethodAdvices =
				_chainableMethodAdvices.toArray(new ChainableMethodAdvice[0]);

			for (AopInvocationHandler aopInvocationHandler :
					_aopInvocationHandlers) {

				aopInvocationHandler.setChainableMethodAdvices(
					chainableMethodAdvices);
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