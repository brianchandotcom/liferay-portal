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

import com.liferay.bulk.rest.dto.v1_0.BulkActionResponseModel;
import com.liferay.bulk.rest.dto.v1_0.BulkAssetEntryActionModel;
import com.liferay.bulk.rest.dto.v1_0.BulkAssetEntryCommonCategoriesModel;
import com.liferay.bulk.rest.dto.v1_0.BulkAssetEntryCommonTagsModel;
import com.liferay.bulk.rest.dto.v1_0.BulkAssetEntryUpdateCategoriesActionModel;
import com.liferay.bulk.rest.dto.v1_0.BulkAssetEntryUpdateTagsActionModel;
import com.liferay.bulk.rest.internal.resource.v1_0.BulkActionResponseModelResourceImpl;
import com.liferay.bulk.rest.resource.v1_0.BulkActionResponseModelResource;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLInvokeDetached;
import graphql.annotations.annotationTypes.GraphQLName;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class Mutation {

	@GraphQLField
	@GraphQLInvokeDetached
	public BulkActionResponseModel postClassNameId(
			@GraphQLName("classNameId") Long classNameId,
			@GraphQLName("BulkAssetEntryUpdateCategoriesActionModel")
				BulkAssetEntryUpdateCategoriesActionModel
					bulkAssetEntryUpdateCategoriesActionModel)
		throws Exception {

		BulkActionResponseModelResource bulkActionResponseModelResource =
			_createBulkActionResponseModelResource();

		return bulkActionResponseModelResource.postClassNameId(
			classNameId, bulkAssetEntryUpdateCategoriesActionModel);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public BulkActionResponseModel postClassNameId(
			@GraphQLName("classNameId") Long classNameId,
			@GraphQLName("BulkAssetEntryUpdateTagsActionModel")
				BulkAssetEntryUpdateTagsActionModel
					bulkAssetEntryUpdateTagsActionModel)
		throws Exception {

		BulkActionResponseModelResource bulkActionResponseModelResource =
			_createBulkActionResponseModelResource();

		return bulkActionResponseModelResource.postClassNameId(
			classNameId, bulkAssetEntryUpdateTagsActionModel);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public BulkAssetEntryCommonCategoriesModel postGroupIdClassNameId(
			@GraphQLName("groupId") Long groupId,
			@GraphQLName("classNameId") Long classNameId,
			@GraphQLName("BulkAssetEntryActionModel") BulkAssetEntryActionModel
				bulkAssetEntryActionModel)
		throws Exception {

		BulkActionResponseModelResource bulkActionResponseModelResource =
			_createBulkActionResponseModelResource();

		return bulkActionResponseModelResource.postGroupIdClassNameId(
			groupId, classNameId, bulkAssetEntryActionModel);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public BulkAssetEntryCommonTagsModel postGroupIdClassNameId(
			@GraphQLName("groupId") Long groupId,
			@GraphQLName("classNameId") Long classNameId,
			@GraphQLName("BulkAssetEntryActionModel") BulkAssetEntryActionModel
				bulkAssetEntryActionModel)
		throws Exception {

		BulkActionResponseModelResource bulkActionResponseModelResource =
			_createBulkActionResponseModelResource();

		return bulkActionResponseModelResource.postGroupIdClassNameId(
			groupId, classNameId, bulkAssetEntryActionModel);
	}

	private static BulkActionResponseModelResource
		_createBulkActionResponseModelResource() {

		return new BulkActionResponseModelResourceImpl();
	}

}