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

package com.liferay.data.engine.rest.internal.graphql.query.v1_0;

import com.liferay.data.engine.rest.dto.v1_0.DataDefinition;
import com.liferay.data.engine.rest.dto.v1_0.DataRecordCollection;
import com.liferay.data.engine.rest.resource.v1_0.DataDefinitionResource;
import com.liferay.data.engine.rest.resource.v1_0.DataRecordCollectionResource;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLInvokeDetached;
import graphql.annotations.annotationTypes.GraphQLName;

import java.util.Collection;

import javax.annotation.Generated;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Jeyvison Nascimento
 * @generated
 */
@Generated("")
public class Query {

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<DataDefinition> getDataDefinitionsPage(
			@GraphQLName("groupId") Long groupId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		DataDefinitionResource dataDefinitionResource =
			_createDataDefinitionResource();

		Page paginationPage = dataDefinitionResource.getDataDefinitionsPage(
			groupId, Pagination.of(pageSize, page));

		return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<DataDefinition> getDataDefinitionSearchPage(
			@GraphQLName("groupId") Long groupId,
			@GraphQLName("keywords") String keywords,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		DataDefinitionResource dataDefinitionResource =
			_createDataDefinitionResource();

		Page paginationPage =
			dataDefinitionResource.getDataDefinitionSearchPage(
				groupId, keywords, Pagination.of(pageSize, page));

		return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public DataDefinition getDataDefinition(
			@GraphQLName("data-definition-id") Long dataDefinitionId)
		throws Exception {

		DataDefinitionResource dataDefinitionResource =
			_createDataDefinitionResource();

		return dataDefinitionResource.getDataDefinition(dataDefinitionId);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<DataRecordCollection> getDataRecordCollectionsPage(
			@GraphQLName("groupId") Long groupId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		DataRecordCollectionResource dataRecordCollectionResource =
			_createDataRecordCollectionResource();

		Page paginationPage =
			dataRecordCollectionResource.getDataRecordCollectionsPage(
				groupId, Pagination.of(pageSize, page));

		return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public DataRecordCollection getDataRecordCollection(
			@GraphQLName("data-record-collection-id") Long
				dataRecordCollectionId)
		throws Exception {

		DataRecordCollectionResource dataRecordCollectionResource =
			_createDataRecordCollectionResource();

		return dataRecordCollectionResource.getDataRecordCollection(
			dataRecordCollectionId);
	}

	private static DataDefinitionResource _createDataDefinitionResource()
		throws Exception {

		DataDefinitionResource dataDefinitionResource =
			_dataDefinitionResourceServiceTracker.getService();

		dataDefinitionResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		return dataDefinitionResource;
	}

	private static final ServiceTracker
		<DataDefinitionResource, DataDefinitionResource>
			_dataDefinitionResourceServiceTracker;

	private static DataRecordCollectionResource
			_createDataRecordCollectionResource()
		throws Exception {

		DataRecordCollectionResource dataRecordCollectionResource =
			_dataRecordCollectionResourceServiceTracker.getService();

		dataRecordCollectionResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		return dataRecordCollectionResource;
	}

	private static final ServiceTracker
		<DataRecordCollectionResource, DataRecordCollectionResource>
			_dataRecordCollectionResourceServiceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(Query.class);

		ServiceTracker<DataDefinitionResource, DataDefinitionResource>
			dataDefinitionResourceServiceTracker = new ServiceTracker<>(
				bundle.getBundleContext(), DataDefinitionResource.class, null);

		dataDefinitionResourceServiceTracker.open();

		_dataDefinitionResourceServiceTracker =
			dataDefinitionResourceServiceTracker;
		ServiceTracker
			<DataRecordCollectionResource, DataRecordCollectionResource>
				dataRecordCollectionResourceServiceTracker =
					new ServiceTracker<>(
						bundle.getBundleContext(),
						DataRecordCollectionResource.class, null);

		dataRecordCollectionResourceServiceTracker.open();

		_dataRecordCollectionResourceServiceTracker =
			dataRecordCollectionResourceServiceTracker;
	}

}