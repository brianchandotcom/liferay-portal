/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.headless.commerce.bom.internal.graphql.mutation.v1_0;

import com.liferay.headless.commerce.bom.dto.v1_0.Spot;
import com.liferay.headless.commerce.bom.resource.v1_0.SpotResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
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
 * @author Alessio Antonio Rendina
 * @generated
 */
@Generated("")
public class Mutation {

	public static void setSpotResourceComponentServiceObjects(
		ComponentServiceObjects<SpotResource>
			spotResourceComponentServiceObjects) {

		_spotResourceComponentServiceObjects =
			spotResourceComponentServiceObjects;
	}

	@GraphQLField
	public Spot createAreaIdSpot(
			@GraphQLName("id") Long id, @GraphQLName("spot") Spot spot)
		throws Exception {

		return _applyComponentServiceObjects(
			_spotResourceComponentServiceObjects,
			this::_populateResourceContext,
			spotResource -> spotResource.postAreaIdSpot(id, spot));
	}

	@GraphQLField
	public Response createAreaIdSpotBatch(
			@GraphQLName("id") Long id,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_spotResourceComponentServiceObjects,
			this::_populateResourceContext,
			spotResource -> spotResource.postAreaIdSpotBatch(
				id, callbackURL, object));
	}

	@GraphQLField
	public Response deleteAreaIdSpot(
			@GraphQLName("id") Long id, @GraphQLName("spotId") Long spotId)
		throws Exception {

		return _applyComponentServiceObjects(
			_spotResourceComponentServiceObjects,
			this::_populateResourceContext,
			spotResource -> spotResource.deleteAreaIdSpot(id, spotId));
	}

	@GraphQLField
	public Response updateAreaIdSpot(
			@GraphQLName("id") Long id, @GraphQLName("spotId") Long spotId,
			@GraphQLName("spot") Spot spot)
		throws Exception {

		return _applyComponentServiceObjects(
			_spotResourceComponentServiceObjects,
			this::_populateResourceContext,
			spotResource -> spotResource.putAreaIdSpot(id, spotId, spot));
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

	private void _populateResourceContext(SpotResource spotResource)
		throws Exception {

		spotResource.setContextAcceptLanguage(_acceptLanguage);
		spotResource.setContextCompany(_company);
		spotResource.setContextHttpServletRequest(_httpServletRequest);
		spotResource.setContextHttpServletResponse(_httpServletResponse);
		spotResource.setContextUriInfo(_uriInfo);
		spotResource.setContextUser(_user);
		spotResource.setGroupLocalService(_groupLocalService);
		spotResource.setRoleLocalService(_roleLocalService);
	}

	private static ComponentServiceObjects<SpotResource>
		_spotResourceComponentServiceObjects;

	private AcceptLanguage _acceptLanguage;
	private com.liferay.portal.kernel.model.Company _company;
	private GroupLocalService _groupLocalService;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private RoleLocalService _roleLocalService;
	private BiFunction<Object, String, Sort[]> _sortsBiFunction;
	private UriInfo _uriInfo;
	private com.liferay.portal.kernel.model.User _user;

}