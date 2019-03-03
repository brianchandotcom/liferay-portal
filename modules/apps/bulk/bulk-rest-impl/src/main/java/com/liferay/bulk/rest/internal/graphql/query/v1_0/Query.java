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

import com.liferay.bulk.rest.dto.v1_0.BulkStatusModel;
import com.liferay.bulk.rest.internal.resource.v1_0.BulkStatusModelResourceImpl;
import com.liferay.bulk.rest.resource.v1_0.BulkStatusModelResource;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLInvokeDetached;
import graphql.annotations.annotationTypes.GraphQLName;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class Query {

	@GraphQLField
	@GraphQLInvokeDetached
	public BulkStatusModel getStatus(@GraphQLName("param") Long param)
		throws Exception {

		BulkStatusModelResource bulkStatusModelResource =
			_createBulkStatusModelResource();

		bulkStatusModelResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		return bulkStatusModelResource.getStatus(param);
	}

	private static BulkStatusModelResource _createBulkStatusModelResource() {
		return new BulkStatusModelResourceImpl();
	}

}