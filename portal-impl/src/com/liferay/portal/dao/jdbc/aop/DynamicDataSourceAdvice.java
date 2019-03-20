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

package com.liferay.portal.dao.jdbc.aop;

import com.liferay.portal.kernel.aop.AopMethodInvocation;
import com.liferay.portal.kernel.aop.ChainableMethodAdvice;
import com.liferay.portal.kernel.dao.jdbc.aop.DynamicDataSourceTargetSource;
import com.liferay.portal.kernel.dao.jdbc.aop.MasterDataSource;
import com.liferay.portal.kernel.dao.jdbc.aop.Operation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.spring.transaction.TransactionAttributeBuilder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import java.util.Map;

import org.springframework.transaction.interceptor.TransactionAttribute;

/**
 * @author Shuyang Zhou
 * @author László Csontos
 */
public class DynamicDataSourceAdvice extends ChainableMethodAdvice {

	public DynamicDataSourceAdvice(
		DynamicDataSourceTargetSource dynamicDataSourceTargetSource) {

		_dynamicDataSourceTargetSource = dynamicDataSourceTargetSource;
	}

	@Override
	public Object createMethodContext(
		Class<?> targetClass, Method method,
		Map<Class<? extends Annotation>, Annotation> annotations) {

		Transactional transactional = (Transactional)annotations.get(
			Transactional.class);

		TransactionAttribute transactionAttribute =
			TransactionAttributeBuilder.build(transactional);

		if (transactionAttribute == null) {
			return null;
		}

		Operation operation = Operation.WRITE;

		MasterDataSource masterDataSource = (MasterDataSource)annotations.get(
			MasterDataSource.class);

		if ((masterDataSource == null) && transactional.readOnly()) {
			operation = Operation.READ;
		}

		return operation;
	}

	@Override
	protected Object before(
		AopMethodInvocation aopMethodInvocation, Object[] arguments) {

		Operation operation = aopMethodInvocation.getAdviceMethodContext();

		_dynamicDataSourceTargetSource.pushOperation(operation);

		return null;
	}

	@Override
	protected void duringFinally(
		AopMethodInvocation aopMethodInvocation, Object[] arguments) {

		_dynamicDataSourceTargetSource.popOperation();
	}

	private final DynamicDataSourceTargetSource _dynamicDataSourceTargetSource;

}