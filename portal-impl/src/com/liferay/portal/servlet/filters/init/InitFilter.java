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

package com.liferay.portal.servlet.filters.init;

import com.liferay.portal.servlet.filters.BasePortalFilter;
import com.liferay.registry.ServiceRegistration;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Matthew Tambara
 */
public class InitFilter extends BasePortalFilter {

	public void setServiceRegistration(
		ServiceRegistration<InitFilter> serviceRegistration) {

		_serviceRegistration = serviceRegistration;
	}

	@Override
	protected void processFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws Exception {

		if (_setup.compareAndSet(false, true)) {
			try {
				processFilter(
					InitFilter.class.getName(), request, response, filterChain);
			}
			finally {
				_serviceRegistration.unregister();

				_countDownLatch.countDown();
			}
		}
		else {
			_countDownLatch.await();

			processFilter(
				InitFilter.class.getName(), request, response, filterChain);
		}
	}

	private final CountDownLatch _countDownLatch = new CountDownLatch(1);
	private ServiceRegistration<InitFilter> _serviceRegistration;
	private final AtomicBoolean _setup = new AtomicBoolean();

}