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

package com.liferay.portal.vulcan.internal.jaxrs.transaction;

import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.spring.transaction.DefaultTransactionExecutor;
import com.liferay.portal.spring.transaction.TransactionAttributeAdapter;
import com.liferay.portal.spring.transaction.TransactionAttributeBuilder;
import com.liferay.portal.spring.transaction.TransactionExecutor;
import com.liferay.portal.spring.transaction.TransactionInvokerImpl;
import com.liferay.portal.spring.transaction.TransactionStatusAdapter;

import java.io.IOException;

import java.lang.reflect.Field;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.ServerErrorException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/**
 * @author Javier Gamarra
 */
@Provider
public class TransactionContainerFilter
	implements ContainerRequestFilter, ContainerResponseFilter {

	@Override
	public void filter(ContainerRequestContext containerRequestContext)
		throws IOException {

		if (_transactionVerbs.contains(containerRequestContext.getMethod())) {
			DefaultTransactionExecutor defaultTransactionExecutor =
				_getDefaultTransactionExecutor();

			TransactionAttributeAdapter transactionAttributeAdapter =
				_getTransactionAttributeAdapter();

			TransactionStatusThreadLocal.setTransactionStatusAdapter(
				defaultTransactionExecutor.start(transactionAttributeAdapter));
		}
	}

	@Override
	public void filter(
			ContainerRequestContext containerRequestContext,
			ContainerResponseContext containerResponseContext)
		throws IOException {

		if (_transactionVerbs.contains(containerRequestContext.getMethod())) {
			DefaultTransactionExecutor defaultTransactionExecutor =
				_getDefaultTransactionExecutor();

			TransactionStatusAdapter transactionStatusAdapter =
				TransactionStatusThreadLocal.getTransactionStatusAdapter();

			if (transactionStatusAdapter != null) {
				TransactionAttributeAdapter transactionAttributeAdapter =
					_getTransactionAttributeAdapter();

				if (Response.Status.Family.familyOf(
						containerResponseContext.getStatus()).equals(
							Response.Status.Family.SUCCESSFUL)) {

					defaultTransactionExecutor.commit(
						transactionAttributeAdapter, transactionStatusAdapter);
				}
				else {
					try {
						defaultTransactionExecutor.rollback(
							new RuntimeException(), transactionAttributeAdapter,
							transactionStatusAdapter);
					}
					catch (Throwable throwable) {

						// We are crashing either way and we don't want
						// to swallow the real exception and create another
						// request

					}
				}
			}
		}
	}

	private DefaultTransactionExecutor _getDefaultTransactionExecutor() {
		try {
			Field field = TransactionInvokerImpl.class.getDeclaredField(
				"_transactionExecutor");

			field.setAccessible(true);

			TransactionExecutor transactionExecutor =
				(TransactionExecutor)field.get(null);

			return new DefaultTransactionExecutor(
				transactionExecutor.getPlatformTransactionManager());
		}
		catch (IllegalAccessException | NoSuchFieldException e) {
			throw new ServerErrorException(
				Response.Status.INTERNAL_SERVER_ERROR, e);
		}
	}

	private TransactionAttributeAdapter _getTransactionAttributeAdapter() {
		TransactionConfig transactionConfig = TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

		return new TransactionAttributeAdapter(
			TransactionAttributeBuilder.build(
				true, transactionConfig.getIsolation(),
				transactionConfig.getPropagation(),
				transactionConfig.isReadOnly(), transactionConfig.getTimeout(),
				transactionConfig.getRollbackForClasses(),
				transactionConfig.getRollbackForClassNames(),
				transactionConfig.getNoRollbackForClasses(),
				transactionConfig.getNoRollbackForClassNames()));
	}

	private static final List<String> _transactionVerbs = Arrays.asList(
		"DELETE", "PATCH", "POST", "PUT");

}