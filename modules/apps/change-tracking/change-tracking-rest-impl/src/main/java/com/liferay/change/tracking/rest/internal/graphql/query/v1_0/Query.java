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

package com.liferay.change.tracking.rest.internal.graphql.query.v1_0;

import com.liferay.change.tracking.rest.dto.v1_0.Collection;
import com.liferay.change.tracking.rest.dto.v1_0.Settings;
import com.liferay.change.tracking.rest.resource.v1_0.CollectionResource;
import com.liferay.change.tracking.rest.resource.v1_0.SettingsResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLInvokeDetached;
import graphql.annotations.annotationTypes.GraphQLName;

import javax.annotation.Generated;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Mate Thurzo
 * @generated
 */
@Generated("")
public class Query {

	public static void setCollectionResourceComponentServiceObjects(
		ComponentServiceObjects<CollectionResource>
			collectionResourceComponentServiceObjects) {

		_collectionResourceComponentServiceObjects =
			collectionResourceComponentServiceObjects;
	}

	public static void setSettingsResourceComponentServiceObjects(
		ComponentServiceObjects<SettingsResource>
			settingsResourceComponentServiceObjects) {

		_settingsResourceComponentServiceObjects =
			settingsResourceComponentServiceObjects;
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public java.util.Collection<Collection> getCollectionsPage(
			@GraphQLName("companyId") Long companyId,
			@GraphQLName("type") String type,
			@GraphQLName("userId") Long userId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_collectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			collectionResource -> {
				Page paginationPage = collectionResource.getCollectionsPage(
					companyId, type, userId, Pagination.of(pageSize, page),
					sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection getCollection(
			@GraphQLName("collectionId") Long collectionId)
		throws Exception {

		return _applyComponentServiceObjects(
			_collectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			collectionResource -> collectionResource.getCollection(
				collectionId));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public java.util.Collection<Settings> getSettingsPage(
			@GraphQLName("companyId") Long companyId,
			@GraphQLName("userId") Long userId)
		throws Exception {

		return _applyComponentServiceObjects(
			_settingsResourceComponentServiceObjects,
			this::_populateResourceContext,
			settingsResource -> {
				Page paginationPage = settingsResource.getSettingsPage(
					companyId, userId);

				return paginationPage.getItems();
			});
	}

	private <T, R, E1 extends Throwable, E2 extends Throwable> R
			_applyComponentServiceObjects(
				ComponentServiceObjects<T> componentServiceObjects,
				UnsafeConsumer<T, E1> unsafeConsumer,
				UnsafeFunction<T, R, E2> unsafeFunction)
		throws E1, E2 {

		T resource = componentServiceObjects.getService();

		try {
			unsafeConsumer.accept(resource);

			return unsafeFunction.apply(resource);
		}
		finally {
			componentServiceObjects.ungetService(resource);
		}
	}

	private void _populateResourceContext(CollectionResource collectionResource)
		throws Exception {

		collectionResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private void _populateResourceContext(SettingsResource settingsResource)
		throws Exception {

		settingsResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private static ComponentServiceObjects<CollectionResource>
		_collectionResourceComponentServiceObjects;
	private static ComponentServiceObjects<SettingsResource>
		_settingsResourceComponentServiceObjects;

}