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
import com.liferay.portal.kernel.monitoring.ServiceMonitoringControl;
import com.liferay.portal.kernel.resiliency.spi.SPIUtil;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.messaging.async.AsyncAdvice;
import com.liferay.portal.monitoring.statistics.service.ServiceMonitorAdvice;
import com.liferay.portal.resiliency.service.PortalResiliencyAdvice;
import com.liferay.portal.search.IndexableAdvice;
import com.liferay.portal.security.access.control.AccessControlAdvice;
import com.liferay.portal.service.ServiceContextAdvice;
import com.liferay.portal.spring.transaction.TransactionExecutor;
import com.liferay.portal.spring.transaction.TransactionInterceptor;
import com.liferay.portal.systemevent.SystemEventAdvice;
import com.liferay.portal.util.PropsValues;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Preston Crary
 */
public class AopCacheManager {

	public static AopInvocationHandler create(
		Object target, ChainableMethodAdvice[] chainableMethodAdvices) {

		AopInvocationHandler aopInvocationHandler = new AopInvocationHandler(
			target, chainableMethodAdvices);

		_aopInvocationHandlers.add(aopInvocationHandler);

		return aopInvocationHandler;
	}

	public static ChainableMethodAdvice[] createChainableMethodAdvices(
		TransactionExecutor transactionExecutor,
		ServiceMonitoringControl serviceMonitoringControl) {

		List<ChainableMethodAdvice> chainableMethodAdvices = new ArrayList<>(
			14);

		if (SPIUtil.isSPI()) {
			chainableMethodAdvices.add(new SPIClusterableAdvice());
		}

		if (PropsValues.CLUSTER_LINK_ENABLED) {
			chainableMethodAdvices.add(new ClusterableAdvice());
		}

		chainableMethodAdvices.add(new AccessControlAdvice());

		if (PropsValues.PORTAL_RESILIENCY_ENABLED) {
			chainableMethodAdvices.add(new PortalResiliencyAdvice());
		}

		chainableMethodAdvices.add(
			new ServiceMonitorAdvice(serviceMonitoringControl));

		AsyncAdvice asyncAdvice = new AsyncAdvice();

		asyncAdvice.setDefaultDestinationName("liferay/async_service");

		chainableMethodAdvices.add(asyncAdvice);

		chainableMethodAdvices.add(new ThreadLocalCacheAdvice());

		chainableMethodAdvices.add(new BufferedIncrementAdvice());

		chainableMethodAdvices.add(new IndexableAdvice());

		chainableMethodAdvices.add(new SystemEventAdvice());

		chainableMethodAdvices.add(new ServiceContextAdvice());

		chainableMethodAdvices.add(new RetryAdvice());

		TransactionInterceptor transactionInterceptor =
			new TransactionInterceptor();

		transactionInterceptor.setTransactionExecutor(transactionExecutor);

		DynamicDataSourceTargetSource dynamicDataSourceTargetSource =
			InfrastructureUtil.getDynamicDataSourceTargetSource();

		if (dynamicDataSourceTargetSource != null) {
			DynamicDataSourceAdvice dynamicDataSourceAdvice =
				new DynamicDataSourceAdvice();

			dynamicDataSourceAdvice.setDynamicDataSourceTargetSource(
				dynamicDataSourceTargetSource);

			chainableMethodAdvices.add(dynamicDataSourceAdvice);
		}

		chainableMethodAdvices.add(transactionInterceptor);

		return chainableMethodAdvices.toArray(
			new ChainableMethodAdvice[chainableMethodAdvices.size()]);
	}

	public static void destroy(AopInvocationHandler aopInvocationHandler) {
		_aopInvocationHandlers.remove(aopInvocationHandler);
	}

	public static void reset() {
		for (AopInvocationHandler aopInvocationHandler :
				_aopInvocationHandlers) {

			aopInvocationHandler.reset();
		}
	}

	private AopCacheManager() {
	}

	private static final Set<AopInvocationHandler> _aopInvocationHandlers =
		Collections.newSetFromMap(new ConcurrentHashMap<>());

}