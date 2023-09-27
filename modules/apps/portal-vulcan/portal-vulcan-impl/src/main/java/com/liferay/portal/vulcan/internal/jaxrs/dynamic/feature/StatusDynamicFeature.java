/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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