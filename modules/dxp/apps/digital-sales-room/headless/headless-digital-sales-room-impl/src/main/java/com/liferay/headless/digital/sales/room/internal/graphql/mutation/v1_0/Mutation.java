/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.digital.sales.room.internal.graphql.mutation.v1_0;

import com.liferay.headless.digital.sales.room.dto.v1_0.DigitalSalesRoom;
import com.liferay.headless.digital.sales.room.resource.v1_0.DigitalSalesRoomResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;

import jakarta.annotation.Generated;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.ws.rs.core.UriInfo;

import java.util.function.BiFunction;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Stefano Motta
 * @generated
 */
@Generated("")
public class Mutation {

	public static void setDigitalSalesRoomResourceComponentServiceObjects(
		ComponentServiceObjects<DigitalSalesRoomResource>
			digitalSalesRoomResourceComponentServiceObjects) {

		_digitalSalesRoomResourceComponentServiceObjects =
			digitalSalesRoomResourceComponentServiceObjects;
	}

	@GraphQLField
	public DigitalSalesRoom patchDigitalSalesRoom(
			@GraphQLName("digitalSalesRoomId") Long digitalSalesRoomId,
			@GraphQLName("digitalSalesRoom") DigitalSalesRoom digitalSalesRoom)
		throws Exception {

		return _applyComponentServiceObjects(
			_digitalSalesRoomResourceComponentServiceObjects,
			this::_populateResourceContext,
			digitalSalesRoomResource ->
				digitalSalesRoomResource.patchDigitalSalesRoom(
					digitalSalesRoomId, digitalSalesRoom));
	}

	@GraphQLField
	public DigitalSalesRoom createDigitalSalesRoom(
			@GraphQLName("digitalSalesRoom") DigitalSalesRoom digitalSalesRoom)
		throws Exception {

		return _applyComponentServiceObjects(
			_digitalSalesRoomResourceComponentServiceObjects,
			this::_populateResourceContext,
			digitalSalesRoomResource ->
				digitalSalesRoomResource.postDigitalSalesRoom(
					digitalSalesRoom));
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
		digitalSalesRoomResource.setRoleLocalService(_roleLocalService);
	}

	private static ComponentServiceObjects<DigitalSalesRoomResource>
		_digitalSalesRoomResourceComponentServiceObjects;

	private AcceptLanguage _acceptLanguage;
	private com.liferay.portal.kernel.model.Company _company;
	private GroupLocalService _groupLocalService;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private RoleLocalService _roleLocalService;
	private BiFunction<Object, String, com.liferay.portal.kernel.search.Sort[]>
		_sortsBiFunction;
	private UriInfo _uriInfo;
	private com.liferay.portal.kernel.model.User _user;

}