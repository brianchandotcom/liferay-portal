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

package com.liferay.headless.builder.internal.jaxrs.context.provider;

import com.liferay.headless.builder.exception.NoSuchOperationException;
import com.liferay.headless.builder.operation.Method;
import com.liferay.headless.builder.operation.Operation;
import com.liferay.headless.builder.operation.provider.OperationProvider;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.util.MessageUtil;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.ext.Provider;

import org.apache.cxf.jaxrs.ext.ContextProvider;
import org.apache.cxf.message.Message;

/**
 * @author Carlos Correa
 */
@Provider
public class OperationContextProvider implements ContextProvider<Operation> {

	public OperationContextProvider(
		OperationProvider operationProvider, Portal portal) {

		_operationProvider = operationProvider;
		_portal = portal;
	}

	@Override
	public Operation createContext(Message message) {
		HttpServletRequest httpServletRequest =
			MessageUtil.getHttpServletRequest(message);

		try {
			return _operationProvider.getOperation(
				_portal.getCompanyId(httpServletRequest),
				Method.valueOf(httpServletRequest.getMethod()),
				httpServletRequest.getRequestURI());
		}
		catch (NoSuchOperationException noSuchOperationException) {
			if (_log.isWarnEnabled()) {
				_log.warn(noSuchOperationException);
			}

			throw new RuntimeException(noSuchOperationException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OperationContextProvider.class);

	private final OperationProvider _operationProvider;
	private final Portal _portal;

}