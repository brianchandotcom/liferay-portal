/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.digital.sales.room.internal.graphql.query.v1_0;

import com.liferay.headless.digital.sales.room.dto.v1_0.DigitalSalesRoom;
import com.liferay.headless.digital.sales.room.resource.v1_0.DigitalSalesRoomResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import jakarta.annotation.Generated;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.ws.rs.core.UriInfo;

import java.util.Map;
import java.util.function.BiFunction;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Stefano Motta
 * @generated
 */
@Generated("")
public class Query {

	public static void setDigitalSalesRoomResourceComponentServiceObjects(
		ComponentServiceObjects<DigitalSalesRoomResource>
			digitalSalesRoomResourceComponentServiceObjects) {

		_digitalSalesRoomResourceComponentServiceObjects =
			digitalSalesRoomResourceComponentServiceObjects;
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {digitalSalesRoom(digitalSalesRoomId: ___){accountId, accountName, banner, channelId, channelName, clientLogo, clientName, createDate, description, externalReferenceCode, friendlyUrlPath, id, modifiedDate, name, ownerId, ownerName, primaryColor, secondaryColor}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public DigitalSalesRoom digitalSalesRoom(
			@GraphQLName("digitalSalesRoomId") Long digitalSalesRoomId)
		throws Exception {

		return _applyComponentServiceObjects(
			_digitalSalesRoomResourceComponentServiceObjects,
			this::_populateResourceContext,
			digitalSalesRoomResource ->
				digitalSalesRoomResource.getDigitalSalesRoom(
					digitalSalesRoomId));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {digitalSalesRooms(page: ___, pageSize: ___, search: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public DigitalSalesRoomPage digitalSalesRooms(
			@GraphQLName("search") String search,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_digitalSalesRoomResourceComponentServiceObjects,
			this::_populateResourceContext,
			digitalSalesRoomResource -> new DigitalSalesRoomPage(
				digitalSalesRoomResource.getDigitalSalesRoomsPage(
					search, Pagination.of(page, pageSize))));
	}

	@GraphQLName("DigitalSalesRoomPage")
	public class DigitalSalesRoomPage {

		public DigitalSalesRoomPage(Page digitalSalesRoomPage) {
			actions = digitalSalesRoomPage.getActions();

			items = digitalSalesRoomPage.getItems();
			lastPage = digitalSalesRoomPage.getLastPage();
			page = digitalSalesRoomPage.getPage();
			pageSize = digitalSalesRoomPage.getPageSize();
			totalCount = digitalSalesRoomPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected java.util.Collection<DigitalSalesRoom> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

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

	private void _populateResourceContext(
			DigitalSalesRoomResource digitalSalesRoomResource)
		throws Exception {

		digitalSalesRoomResource.setContextAcceptLanguage(_acceptLanguage);
		digitalSalesRoomResource.setContextCompany(_company);
		digitalSalesRoomResource.setContextHttpServletRequest(
			_httpServletRequest);
		digitalSalesRoomResource.setContextHttpServletResponse(
			_httpServletResponse);
		digitalSalesRoomResource.setContextUriInfo(_uriInfo);
		digitalSalesRoomResource.setContextUser(_user);
		digitalSalesRoomResource.setGroupLocalService(_groupLocalService);
		digitalSalesRoomResource.setResourceActionLocalService(
			_resourceActionLocalService);
		digitalSalesRoomResource.setResourcePermissionLocalService(
			_resourcePermissionLocalService);
		digitalSalesRoomResource.setRoleLocalService(_roleLocalService);
	}

	private static ComponentServiceObjects<DigitalSalesRoomResource>
		_digitalSalesRoomResourceComponentServiceObjects;

	private AcceptLanguage _acceptLanguage;
	private com.liferay.portal.kernel.model.Company _company;
	private BiFunction
		<Object, String, com.liferay.portal.kernel.search.filter.Filter>
			_filterBiFunction;
	private GroupLocalService _groupLocalService;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private ResourceActionLocalService _resourceActionLocalService;
	private ResourcePermissionLocalService _resourcePermissionLocalService;
	private RoleLocalService _roleLocalService;
	private BiFunction<Object, String, com.liferay.portal.kernel.search.Sort[]>
		_sortsBiFunction;
	private UriInfo _uriInfo;
	private com.liferay.portal.kernel.model.User _user;

}