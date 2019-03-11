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

package com.liferay.bulk.rest.internal.graphql.mutation.v1_0;

import com.liferay.bulk.rest.dto.v1_0.BulkActionResponse;
import com.liferay.bulk.rest.dto.v1_0.BulkAssetEntryAction;
import com.liferay.bulk.rest.dto.v1_0.BulkAssetEntryCommonCategories;
import com.liferay.bulk.rest.dto.v1_0.BulkAssetEntryCommonTags;
import com.liferay.bulk.rest.dto.v1_0.BulkAssetEntryUpdateCategoriesAction;
import com.liferay.bulk.rest.dto.v1_0.BulkAssetEntryUpdateTagsAction;
import com.liferay.bulk.rest.resource.v1_0.BulkActionResponseResource;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLInvokeDetached;
import graphql.annotations.annotationTypes.GraphQLName;

import javax.annotation.Generated;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Alejandro Tardín
 * @generated
 */
@Generated("")
public class Mutation {

	@GraphQLField
	@GraphQLInvokeDetached
	public BulkActionResponse postCategoryClassName(
			@GraphQLName("class-name-id") Long classNameId,
			@GraphQLName("BulkAssetEntryUpdateCategoriesAction")
				BulkAssetEntryUpdateCategoriesAction
					bulkAssetEntryUpdateCategoriesAction)
		throws Exception {

		BulkActionResponseResource bulkActionResponseResource =
			_createBulkActionResponseResource();

		return bulkActionResponseResource.postCategoryClassName(
			classNameId, bulkAssetEntryUpdateCategoriesAction);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public BulkAssetEntryCommonCategories
			postCategoryContentSpaceClassNameCommon(
				@GraphQLName("content-space-id") Long contentSpaceId,
				@GraphQLName("class-name-id") Long classNameId,
				@GraphQLName("BulkAssetEntryAction") BulkAssetEntryAction
					bulkAssetEntryAction)
		throws Exception {

		BulkActionResponseResource bulkActionResponseResource =
			_createBulkActionResponseResource();

		return bulkActionResponseResource.
			postCategoryContentSpaceClassNameCommon(
				contentSpaceId, classNameId, bulkAssetEntryAction);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public BulkActionResponse postTagClassName(
			@GraphQLName("class-name-id") Long classNameId,
			@GraphQLName("BulkAssetEntryUpdateTagsAction")
				BulkAssetEntryUpdateTagsAction bulkAssetEntryUpdateTagsAction)
		throws Exception {

		BulkActionResponseResource bulkActionResponseResource =
			_createBulkActionResponseResource();

		return bulkActionResponseResource.postTagClassName(
			classNameId, bulkAssetEntryUpdateTagsAction);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public BulkAssetEntryCommonTags postTagContentSpaceClassNameCommon(
			@GraphQLName("content-space-id") Long contentSpaceId,
			@GraphQLName("class-name-id") Long classNameId,
			@GraphQLName("BulkAssetEntryAction") BulkAssetEntryAction
				bulkAssetEntryAction)
		throws Exception {

		BulkActionResponseResource bulkActionResponseResource =
			_createBulkActionResponseResource();

		return bulkActionResponseResource.postTagContentSpaceClassNameCommon(
			contentSpaceId, classNameId, bulkAssetEntryAction);
	}

	private static BulkActionResponseResource
			_createBulkActionResponseResource()
		throws Exception {

		BulkActionResponseResource bulkActionResponseResource =
			_bulkActionResponseResourceServiceTracker.getService();

		bulkActionResponseResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		return bulkActionResponseResource;
	}

	private static final ServiceTracker
		<BulkActionResponseResource, BulkActionResponseResource>
			_bulkActionResponseResourceServiceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(Mutation.class);

		ServiceTracker<BulkActionResponseResource, BulkActionResponseResource>
			bulkActionResponseResourceServiceTracker = new ServiceTracker<>(
				bundle.getBundleContext(), BulkActionResponseResource.class,
				null);

		bulkActionResponseResourceServiceTracker.open();

		_bulkActionResponseResourceServiceTracker =
			bulkActionResponseResourceServiceTracker;
	}

}