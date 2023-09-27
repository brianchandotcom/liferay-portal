/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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