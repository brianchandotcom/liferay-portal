/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.delivery.order.internal.graphql.mutation.v1_0;

import com.liferay.headless.commerce.delivery.order.resource.v1_0.PlacedOrderCommentResource;
import com.liferay.headless.commerce.delivery.order.resource.v1_0.PlacedOrderItemResource;
import com.liferay.headless.commerce.delivery.order.resource.v1_0.PlacedOrderItemShipmentResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.batch.engine.resource.VulcanBatchEngineExportTaskResource;
import com.liferay.portal.vulcan.batch.engine.resource.VulcanBatchEngineImportTaskResource;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;

import java.util.function.BiFunction;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Andrea Sbarra
 * @generated
 */
@Generated("")
public class Mutation {

	public static void setPlacedOrderCommentResourceComponentServiceObjects(
		ComponentServiceObjects<PlacedOrderCommentResource>
			placedOrderCommentResourceComponentServiceObjects) {

		_placedOrderCommentResourceComponentServiceObjects =
			placedOrderCommentResourceComponentServiceObjects;
	}

	public static void setPlacedOrderItemResourceComponentServiceObjects(
		ComponentServiceObjects<PlacedOrderItemResource>
			placedOrderItemResourceComponentServiceObjects) {

		_placedOrderItemResourceComponentServiceObjects =
			placedOrderItemResourceComponentServiceObjects;
	}

	public static void
		setPlacedOrderItemShipmentResourceComponentServiceObjects(
			ComponentServiceObjects<PlacedOrderItemShipmentResource>
				placedOrderItemShipmentResourceComponentServiceObjects) {

		_placedOrderItemShipmentResourceComponentServiceObjects =
			placedOrderItemShipmentResourceComponentServiceObjects;
	}

	@GraphQLField
	public Response createPlacedOrderPlacedOrderCommentsPageExportBatch(
			@GraphQLName("placedOrderId") Long placedOrderId,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("contentType") String contentType,
			@GraphQLName("fieldNames") String fieldNames)
		throws Exception {

		return _applyComponentServiceObjects(
			_placedOrderCommentResourceComponentServiceObjects,
			this::_populateResourceContext,
			placedOrderCommentResource ->
				placedOrderCommentResource.
					postPlacedOrderPlacedOrderCommentsPageExportBatch(
						placedOrderId, callbackURL, contentType, fieldNames));
	}

	@GraphQLField
	public Response createPlacedOrderPlacedOrderItemsPageExportBatch(
			@GraphQLName("placedOrderId") Long placedOrderId,
			@GraphQLName("skuId") Long skuId,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("contentType") String contentType,
			@GraphQLName("fieldNames") String fieldNames)
		throws Exception {

		return _applyComponentServiceObjects(
			_placedOrderItemResourceComponentServiceObjects,
			this::_populateResourceContext,
			placedOrderItemResource ->
				placedOrderItemResource.
					postPlacedOrderPlacedOrderItemsPageExportBatch(
						placedOrderId, skuId, callbackURL, contentType,
						fieldNames));
	}

	@GraphQLField
	public Response
			createPlacedOrderItemPlacedOrderItemShipmentsPageExportBatch(
				@GraphQLName("placedOrderItemId") Long placedOrderItemId,
				@GraphQLName("callbackURL") String callbackURL,
				@GraphQLName("contentType") String contentType,
				@GraphQLName("fieldNames") String fieldNames)
		throws Exception {

		return _applyComponentServiceObjects(
			_placedOrderItemShipmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			placedOrderItemShipmentResource ->
				placedOrderItemShipmentResource.
					postPlacedOrderItemPlacedOrderItemShipmentsPageExportBatch(
						placedOrderItemId, callbackURL, contentType,
						fieldNames));
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

	private <T, E1 extends Throwable, E2 extends Throwable> void
			_applyVoidComponentServiceObjects(
				ComponentServiceObjects<T> componentServiceObjects,
				UnsafeConsumer<T, E1> unsafeConsumer,
				UnsafeConsumer<T, E2> unsafeFunction)
		throws E1, E2 {

		T resource = componentServiceObjects.getService();

		try {
			unsafeConsumer.accept(resource);

			unsafeFunction.accept(resource);
		}
		finally {
			componentServiceObjects.ungetService(resource);
		}
	}

	private void _populateResourceContext(
			PlacedOrderCommentResource placedOrderCommentResource)
		throws Exception {

		placedOrderCommentResource.setContextAcceptLanguage(_acceptLanguage);
		placedOrderCommentResource.setContextCompany(_company);
		placedOrderCommentResource.setContextHttpServletRequest(
			_httpServletRequest);
		placedOrderCommentResource.setContextHttpServletResponse(
			_httpServletResponse);
		placedOrderCommentResource.setContextUriInfo(_uriInfo);
		placedOrderCommentResource.setContextUser(_user);
		placedOrderCommentResource.setGroupLocalService(_groupLocalService);
		placedOrderCommentResource.setRoleLocalService(_roleLocalService);

		placedOrderCommentResource.setVulcanBatchEngineExportTaskResource(
			_vulcanBatchEngineExportTaskResource);

		placedOrderCommentResource.setVulcanBatchEngineImportTaskResource(
			_vulcanBatchEngineImportTaskResource);
	}

	private void _populateResourceContext(
			PlacedOrderItemResource placedOrderItemResource)
		throws Exception {

		placedOrderItemResource.setContextAcceptLanguage(_acceptLanguage);
		placedOrderItemResource.setContextCompany(_company);
		placedOrderItemResource.setContextHttpServletRequest(
			_httpServletRequest);
		placedOrderItemResource.setContextHttpServletResponse(
			_httpServletResponse);
		placedOrderItemResource.setContextUriInfo(_uriInfo);
		placedOrderItemResource.setContextUser(_user);
		placedOrderItemResource.setGroupLocalService(_groupLocalService);
		placedOrderItemResource.setRoleLocalService(_roleLocalService);

		placedOrderItemResource.setVulcanBatchEngineExportTaskResource(
			_vulcanBatchEngineExportTaskResource);

		placedOrderItemResource.setVulcanBatchEngineImportTaskResource(
			_vulcanBatchEngineImportTaskResource);
	}

	private void _populateResourceContext(
			PlacedOrderItemShipmentResource placedOrderItemShipmentResource)
		throws Exception {

		placedOrderItemShipmentResource.setContextAcceptLanguage(
			_acceptLanguage);
		placedOrderItemShipmentResource.setContextCompany(_company);
		placedOrderItemShipmentResource.setContextHttpServletRequest(
			_httpServletRequest);
		placedOrderItemShipmentResource.setContextHttpServletResponse(
			_httpServletResponse);
		placedOrderItemShipmentResource.setContextUriInfo(_uriInfo);
		placedOrderItemShipmentResource.setContextUser(_user);
		placedOrderItemShipmentResource.setGroupLocalService(
			_groupLocalService);
		placedOrderItemShipmentResource.setRoleLocalService(_roleLocalService);

		placedOrderItemShipmentResource.setVulcanBatchEngineExportTaskResource(
			_vulcanBatchEngineExportTaskResource);

		placedOrderItemShipmentResource.setVulcanBatchEngineImportTaskResource(
			_vulcanBatchEngineImportTaskResource);
	}

	private static ComponentServiceObjects<PlacedOrderCommentResource>
		_placedOrderCommentResourceComponentServiceObjects;
	private static ComponentServiceObjects<PlacedOrderItemResource>
		_placedOrderItemResourceComponentServiceObjects;
	private static ComponentServiceObjects<PlacedOrderItemShipmentResource>
		_placedOrderItemShipmentResourceComponentServiceObjects;

	private AcceptLanguage _acceptLanguage;
	private com.liferay.portal.kernel.model.Company _company;
	private GroupLocalService _groupLocalService;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private RoleLocalService _roleLocalService;
	private BiFunction<Object, String, Sort[]> _sortsBiFunction;
	private UriInfo _uriInfo;
	private com.liferay.portal.kernel.model.User _user;
	private VulcanBatchEngineExportTaskResource
		_vulcanBatchEngineExportTaskResource;
	private VulcanBatchEngineImportTaskResource
		_vulcanBatchEngineImportTaskResource;

}