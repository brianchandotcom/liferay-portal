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

package com.liferay.portal.vulcan.internal.jaxrs.message.exchange;

import com.liferay.portal.vulcan.jaxrs.constants.JaxRsConstants;

import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.ExchangeImpl;

/**
 * @author Luis Miguel Barcos
 */
public class ExchangeWrapper extends ExchangeImpl {

	public ExchangeWrapper(Exchange exchange, Object resource) {
		super((ExchangeImpl)exchange);

		_exchange = exchange;
		_resource = resource;
	}

	@Override
	public Object get(Object key) {
		if (key.equals(JaxRsConstants.LAST_SERVICE_OBJECT)) {
			return _resource;
		}

		return super.get(key);
	}

	public Exchange getExchange() {
		return _exchange;
	}

	public Object getResource() {
		return _resource;
	}

	private final Exchange _exchange;
	private final Object _resource;

}