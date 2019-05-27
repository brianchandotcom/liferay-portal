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

package com.liferay.portal.vulcan.internal.jaxrs.dynamic.feature;

import com.liferay.portal.vulcan.internal.jaxrs.container.response.filter.StatusContainerResponseFilter;
import com.liferay.portal.vulcan.status.Status;

import java.lang.reflect.Method;

import javax.ws.rs.Priorities;
import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

/**
 * @author Zoltán Takács
 */
@Provider
public class StatusDynamicFeature implements DynamicFeature {

	public void configure(ResourceInfo resourceInfo, FeatureContext context) {
		Method resourceMethod = resourceInfo.getResourceMethod();

		Status status = resourceMethod.getAnnotation(Status.class);

		if (status == null) {
			return;
		}

		StatusContainerResponseFilter statusContainerResponseFilter =
			new StatusContainerResponseFilter(status.value());

		context.register(statusContainerResponseFilter, Priorities.USER + 100);
	}

}