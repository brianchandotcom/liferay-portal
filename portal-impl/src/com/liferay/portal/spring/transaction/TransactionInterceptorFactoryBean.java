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

import com.liferay.portal.dao.jdbc.aop.DynamicDataSourceTargetSource;
import com.liferay.portal.dao.jdbc.aop.DynamicDataSourceTransactionInterceptor;
import com.liferay.portal.kernel.spring.util.FactoryBean;
import com.liferay.portal.kernel.util.InfrastructureUtil;

import org.aopalliance.intercept.MethodInterceptor;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.TransactionAttributeSource;

/**
 * @author Shuyang Zhou
 */
public class TransactionInterceptorFactoryBean
	implements FactoryBean<MethodInterceptor> {

	public MethodInterceptor create() {
		BaseTransactionInterceptor baseTransactionInterceptor;

		if (_platformTransactionManager instanceof
			CallbackPreferringTransactionInterceptor) {

			baseTransactionInterceptor =
				new CallbackPreferringTransactionInterceptor();
		}
		else {
			baseTransactionInterceptor = new TransactionInterceptor();
		}

		baseTransactionInterceptor.setPlatformTransactionManager(
			_platformTransactionManager);
		baseTransactionInterceptor.setTransactionAttributeSource(
			_transactionAttributeSource);

		DynamicDataSourceTargetSource dynamicDataSourceTargetSource =
			(DynamicDataSourceTargetSource)
				InfrastructureUtil.getDynamicDataSourceTargetSource();

		if (dynamicDataSourceTargetSource == null) {
			return baseTransactionInterceptor;
		}

		DynamicDataSourceTransactionInterceptor
			dynamicDataSourceTransactionInterceptor =
				new DynamicDataSourceTransactionInterceptor();

		dynamicDataSourceTransactionInterceptor.
			setDynamicDataSourceTargetSource(dynamicDataSourceTargetSource);
		dynamicDataSourceTransactionInterceptor.setTransactionAttributeSource(
			_transactionAttributeSource);
		dynamicDataSourceTransactionInterceptor.setTransactionInterceptor(
			baseTransactionInterceptor);

		return dynamicDataSourceTransactionInterceptor;
	}

	public void setPlatformTransactionManager(
		PlatformTransactionManager platformTransactionManager) {

		_platformTransactionManager = platformTransactionManager;
	}

	public void setTransactionAttributeSource(
		TransactionAttributeSource transactionAttributeSource) {

		_transactionAttributeSource = transactionAttributeSource;
	}

	private PlatformTransactionManager _platformTransactionManager;
	private TransactionAttributeSource _transactionAttributeSource;

}