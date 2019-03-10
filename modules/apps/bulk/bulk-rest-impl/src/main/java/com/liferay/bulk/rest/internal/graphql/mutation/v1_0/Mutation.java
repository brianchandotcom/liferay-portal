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
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class Mutation {

	@GraphQLField
	@GraphQLInvokeDetached
	public BulkActionResponse postCategoryCategoryClassName(
			@GraphQLName("category-class-name-id") Long categoryClassNameId,
			@GraphQLName("BulkAssetEntryUpdateCategoriesAction")
				BulkAssetEntryUpdateCategoriesAction
					bulkAssetEntryUpdateCategoriesAction)
		throws Exception {

		BulkActionResponseResource bulkActionResponseResource =
			_createBulkActionResponseResource();

		return bulkActionResponseResource.postCategoryCategoryClassName(
			categoryClassNameId, bulkAssetEntryUpdateCategoriesAction);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public BulkAssetEntryCommonCategories
			postCategoryCategoryGroupCategoryClassNameCommon(
				@GraphQLName("category-group-id") Long categoryGroupId,
				@GraphQLName("category-class-name-id") Long categoryClassNameId,
				@GraphQLName("BulkAssetEntryAction") BulkAssetEntryAction
					bulkAssetEntryAction)
		throws Exception {

		BulkActionResponseResource bulkActionResponseResource =
			_createBulkActionResponseResource();

		return bulkActionResponseResource.
			postCategoryCategoryGroupCategoryClassNameCommon(
				categoryGroupId, categoryClassNameId, bulkAssetEntryAction);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public BulkActionResponse postTagTagClassName(
			@GraphQLName("tag-class-name-id") Long tagClassNameId,
			@GraphQLName("BulkAssetEntryUpdateTagsAction")
				BulkAssetEntryUpdateTagsAction bulkAssetEntryUpdateTagsAction)
		throws Exception {

		BulkActionResponseResource bulkActionResponseResource =
			_createBulkActionResponseResource();

		return bulkActionResponseResource.postTagTagClassName(
			tagClassNameId, bulkAssetEntryUpdateTagsAction);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public BulkAssetEntryCommonTags postTagTagGroupTagClassNameCommon(
			@GraphQLName("tag-group-id") Long tagGroupId,
			@GraphQLName("tag-class-name-id") Long tagClassNameId,
			@GraphQLName("BulkAssetEntryAction") BulkAssetEntryAction
				bulkAssetEntryAction)
		throws Exception {

		BulkActionResponseResource bulkActionResponseResource =
			_createBulkActionResponseResource();

		return bulkActionResponseResource.postTagTagGroupTagClassNameCommon(
			tagGroupId, tagClassNameId, bulkAssetEntryAction);
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