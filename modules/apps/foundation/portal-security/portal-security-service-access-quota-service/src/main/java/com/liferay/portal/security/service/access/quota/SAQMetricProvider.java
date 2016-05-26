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

package com.liferay.portal.security.service.access.quota;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.security.auth.AccessControlContext;

import java.lang.reflect.Method;

/**
 * @author Stian Sigvartsen
 */
@ProviderType
public interface SAQMetricProvider {

	public String getMetricName();

	public String getMetricValue(
		AccessControlContext accessControlContext, Method method);

	public boolean matches(String metricValue, String metricFilter);

}