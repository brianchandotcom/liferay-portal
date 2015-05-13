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

package com.liferay.portal.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Function;

import javax.portlet.PortletRequest;

/**
 * @author André de Oliveira
 */
public class ServiceContextFunction
	implements Function<String, ServiceContext> {

	public ServiceContextFunction(PortletRequest portletRequest) {
		_portletRequest = portletRequest;
	}

	@Override
	public ServiceContext apply(String className) {
		try {
			return ServiceContextFactory.getInstance(
				className, _portletRequest);
		}
		catch (PortalException pe) {
			throw new RuntimeException(pe);
		}
	}

	private final PortletRequest _portletRequest;

}