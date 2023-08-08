/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.builder.internal.jaxrs.context.provider;

import com.liferay.portal.odata.filter.expression.Expression;

import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Providers;

import org.apache.cxf.jaxrs.ext.ContextProvider;
import org.apache.cxf.message.Message;

/**
 * @author Luis Miguel Barcos
 */
public class APIApplicationFilterContextProvider
	implements ContextProvider<Expression> {

	public APIApplicationFilterContextProvider(
		APIApplicationProvider apiApplicationProvider) {

		_apiApplicationProvider = apiApplicationProvider;
	}

	@Override
	public Expression createContext(Message message) {
		return _apiApplicationProvider.provide(
			Expression.class, message, _providers);
	}

	private final APIApplicationProvider _apiApplicationProvider;

	@Context
	private Providers _providers;

}