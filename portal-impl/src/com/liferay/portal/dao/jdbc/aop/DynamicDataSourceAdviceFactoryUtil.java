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

package com.liferay.portal.dao.jdbc.aop;

import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.spring.transaction.TransactionInterceptor;

import org.aopalliance.intercept.MethodInterceptor;

/**
 * @author Shuyang Zhou
 */
public class DynamicDataSourceAdviceFactoryUtil {

	public static MethodInterceptor createDynamicDataSourceAdvice(
		TransactionInterceptor transactionInterceptor) {

		DynamicDataSourceTargetSource dynamicDataSourceTargetSource =
			(DynamicDataSourceTargetSource)
				InfrastructureUtil.getDynamicDataSourceTargetSource();

		if (dynamicDataSourceTargetSource == null) {
			return transactionInterceptor;
		}

		DynamicDataSourceAdvice dynamicDataSourceAdvice =
			new DynamicDataSourceAdvice();

		dynamicDataSourceAdvice.setDynamicDataSourceTargetSource(
			dynamicDataSourceTargetSource);
		dynamicDataSourceAdvice.setNextMethodInterceptor(
			transactionInterceptor);
		dynamicDataSourceAdvice.setTransactionAttributeSource(
			transactionInterceptor.getTransactionAttributeSource());

		return dynamicDataSourceAdvice;
	}

}