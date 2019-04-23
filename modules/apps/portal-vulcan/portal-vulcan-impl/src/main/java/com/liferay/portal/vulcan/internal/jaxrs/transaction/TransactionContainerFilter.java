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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.spring.transaction.TransactionAttributeAdapter;
import com.liferay.portal.spring.transaction.TransactionAttributeBuilder;
import com.liferay.portal.spring.transaction.TransactionHandler;
import com.liferay.portal.spring.transaction.TransactionStatusAdapter;

import java.io.IOException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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
@Transactional(rollbackFor = Exception.class)
public class TransactionContainerFilter
	implements ContainerRequestFilter, ContainerResponseFilter {

	@Override
	public void filter(ContainerRequestContext containerRequestContext)
		throws IOException {

		if (_transactionVerbs.contains(containerRequestContext.getMethod())) {
			containerRequestContext.setProperty(
				_TRANSACTION_STATUS_ADAPTER,
				_transactionHandler.start(_transactionAttributeAdapter));
		}
	}

	@Override
	public void filter(
			ContainerRequestContext containerRequestContext,
			ContainerResponseContext containerResponseContext)
		throws IOException {

		TransactionStatusAdapter transactionStatusAdapter =
			(TransactionStatusAdapter)containerRequestContext.getProperty(
				_TRANSACTION_STATUS_ADAPTER);

		if (transactionStatusAdapter == null) {
			return;
		}

		Response.Status.Family family = Response.Status.Family.familyOf(
			containerResponseContext.getStatus());

		if (family.equals(Response.Status.Family.SUCCESSFUL)) {
			_transactionHandler.commit(
				_transactionAttributeAdapter, transactionStatusAdapter);
		}
		else {
			try {
				_transactionHandler.rollback(
					new RuntimeException(), _transactionAttributeAdapter,
					transactionStatusAdapter);
			}
			catch (Throwable throwable) {
				if (_log.isDebugEnabled()) {
					_log.debug("Could not rollback the transation", throwable);
				}
			}
		}
	}

	private static final String _TRANSACTION_STATUS_ADAPTER =
		TransactionContainerFilter.class.getName() +
			".TRANSACTION_STATUS_ADAPTER";

	private static final Log _log = LogFactoryUtil.getLog(
		TransactionContainerFilter.class);

	private static final TransactionAttributeAdapter
		_transactionAttributeAdapter = new TransactionAttributeAdapter(
			TransactionAttributeBuilder.build(
				TransactionContainerFilter.class.getAnnotation(
					Transactional.class)));
	private static final TransactionHandler _transactionHandler =
		(TransactionHandler)PortalBeanLocatorUtil.locate("transactionExecutor");
	private static final Set<String> _transactionVerbs = new HashSet<>(
		Arrays.asList("DELETE", "PATCH", "POST", "PUT"));

}