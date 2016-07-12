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

package com.liferay.portal.init.servlet.filter.internal;

import com.liferay.portal.kernel.concurrent.CompeteLatch;
import com.liferay.portal.servlet.filters.BasePortalFilter;

import java.util.concurrent.CountDownLatch;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.ServiceRegistration;

/**
 * @author Matthew Tambara
 */
public class InitFilter extends BasePortalFilter {

	public void setServiceRegistration(
		ServiceRegistration<Filter> serviceRegistration) {

		_serviceRegistration = serviceRegistration;

		_countDownLatch.countDown();
	}

	@Override
	protected void processFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws Exception {

		_countDownLatch.await();

		if (_competeLatch.compete()) {
			try {
				processFilter(
					InitFilter.class.getName(), request, response, filterChain);
			}
			finally {
				_serviceRegistration.unregister();

				_competeLatch.done();
			}
		}
		else {
			_competeLatch.await();

			processFilter(
				InitFilter.class.getName(), request, response, filterChain);
		}
	}

	private final CompeteLatch _competeLatch = new CompeteLatch();
	private final CountDownLatch _countDownLatch = new CountDownLatch(1);
	private ServiceRegistration<Filter> _serviceRegistration;

}