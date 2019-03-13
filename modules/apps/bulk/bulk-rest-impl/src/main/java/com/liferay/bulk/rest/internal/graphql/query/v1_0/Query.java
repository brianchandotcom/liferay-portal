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

package com.liferay.bulk.rest.internal.graphql.query.v1_0;

import com.liferay.bulk.rest.dto.v1_0.Status;
import com.liferay.bulk.rest.resource.v1_0.StatusResource;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLInvokeDetached;

import javax.annotation.Generated;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Alejandro Tardín
 * @generated
 */
@Generated("")
public class Query {

	@GraphQLField
	@GraphQLInvokeDetached
	public Status getStatu() throws Exception {
		StatusResource statusResource = _createStatusResource();

		return statusResource.getStatu();
	}

	private static StatusResource _createStatusResource() throws Exception {
		StatusResource statusResource =
			_statusResourceServiceTracker.getService();

		statusResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		return statusResource;
	}

	private static final ServiceTracker<StatusResource, StatusResource>
		_statusResourceServiceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(Query.class);

		ServiceTracker<StatusResource, StatusResource>
			statusResourceServiceTracker = new ServiceTracker<>(
				bundle.getBundleContext(), StatusResource.class, null);

		statusResourceServiceTracker.open();

		_statusResourceServiceTracker = statusResourceServiceTracker;
	}

}