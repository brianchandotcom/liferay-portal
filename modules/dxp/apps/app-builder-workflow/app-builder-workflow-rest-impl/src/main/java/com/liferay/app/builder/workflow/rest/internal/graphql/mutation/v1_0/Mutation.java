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

package com.liferay.app.builder.workflow.rest.internal.graphql.mutation.v1_0;

import com.liferay.app.builder.workflow.rest.dto.v1_0.AppWorkflow;
import com.liferay.app.builder.workflow.rest.resource.v1_0.AppWorkflowResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;

import java.util.function.BiFunction;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
public class Mutation {

	public static void setAppWorkflowResourceComponentServiceObjects(
		ComponentServiceObjects<AppWorkflowResource>
			appWorkflowResourceComponentServiceObjects) {

		_appWorkflowResourceComponentServiceObjects =
			appWorkflowResourceComponentServiceObjects;
	}

	@GraphQLField
	public AppWorkflow createAppWorkflow(
			@GraphQLName("appId") Long appId,
			@GraphQLName("appWorkflow") AppWorkflow appWorkflow)
		throws Exception {

		return _applyComponentServiceObjects(
			_appWorkflowResourceComponentServiceObjects,
			this::_populateResourceContext,
			appWorkflowResource -> appWorkflowResource.postAppWorkflow(
				appId, appWorkflow));
	}

	@GraphQLField
	public AppWorkflow updateAppWorkflow(
			@GraphQLName("appId") Long appId,
			@GraphQLName("appWorkflow") AppWorkflow appWorkflow)
		throws Exception {

		return _applyComponentServiceObjects(
			_appWorkflowResourceComponentServiceObjects,
			this::_populateResourceContext,
			appWorkflowResource -> appWorkflowResource.putAppWorkflow(
				appId, appWorkflow));
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
			AppWorkflowResource appWorkflowResource)
		throws Exception {

		appWorkflowResource.setContextAcceptLanguage(_acceptLanguage);
		appWorkflowResource.setContextCompany(_company);
		appWorkflowResource.setContextHttpServletRequest(_httpServletRequest);
		appWorkflowResource.setContextHttpServletResponse(_httpServletResponse);
		appWorkflowResource.setContextUriInfo(_uriInfo);
		appWorkflowResource.setContextUser(_user);
	}

	private static ComponentServiceObjects<AppWorkflowResource>
		_appWorkflowResourceComponentServiceObjects;

	private AcceptLanguage _acceptLanguage;
	private com.liferay.portal.kernel.model.Company _company;
	private BiFunction<Object, String, Sort[]> _sortsBiFunction;
	private com.liferay.portal.kernel.model.User _user;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private UriInfo _uriInfo;

}