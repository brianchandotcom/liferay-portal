/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.spring.transaction;

import com.liferay.portal.dao.jdbc.aop.DynamicDataSourceAdvice;
import com.liferay.portal.dao.jdbc.aop.DynamicDataSourceTargetSource;
import com.liferay.portal.kernel.spring.util.FactoryBean;
import com.liferay.portal.kernel.util.InfrastructureUtil;

import org.aopalliance.intercept.MethodInterceptor;

/**
 * @author Shuyang Zhou
 */
public class TransactionInterceptorFactoryBean
	implements FactoryBean<MethodInterceptor> {

	@Override
	public MethodInterceptor create() {
		return new TransactionInterceptor();
	}

	@Override
	public MethodInterceptor postProcessing(
		MethodInterceptor methodInterceptor) {

		TransactionInterceptor transactionInterceptor =
			(TransactionInterceptor)methodInterceptor;

		TransactionExecutor transactionExecutor =
			TransactionExecutorFactory.createTransactionExecutor(
				transactionInterceptor.platformTransactionManager, false);

		transactionInterceptor.setTransactionExecutor(transactionExecutor);

		DynamicDataSourceTargetSource dynamicDataSourceTargetSource =
			(DynamicDataSourceTargetSource)
				InfrastructureUtil.getDynamicDataSourceTargetSource();

		if (dynamicDataSourceTargetSource == null) {
			return methodInterceptor;
		}

		DynamicDataSourceAdvice dynamicDataSourceAdvice =
			new DynamicDataSourceAdvice();

		dynamicDataSourceAdvice.setDynamicDataSourceTargetSource(
			dynamicDataSourceTargetSource);
		dynamicDataSourceAdvice.setNextMethodInterceptor(methodInterceptor);

		dynamicDataSourceAdvice.setTransactionAttributeSource(
			transactionInterceptor.transactionAttributeSource);

		return dynamicDataSourceAdvice;
	}

}