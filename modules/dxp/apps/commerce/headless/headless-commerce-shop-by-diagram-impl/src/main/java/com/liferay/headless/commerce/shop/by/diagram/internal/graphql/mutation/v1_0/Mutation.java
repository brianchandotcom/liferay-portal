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

package com.liferay.headless.commerce.shop.by.diagram.internal.graphql.mutation.v1_0;

import com.liferay.headless.commerce.shop.by.diagram.dto.v1_0.Diagram;
import com.liferay.headless.commerce.shop.by.diagram.dto.v1_0.DiagramEntry;
import com.liferay.headless.commerce.shop.by.diagram.dto.v1_0.Pin;
import com.liferay.headless.commerce.shop.by.diagram.resource.v1_0.DiagramEntryResource;
import com.liferay.headless.commerce.shop.by.diagram.resource.v1_0.DiagramResource;
import com.liferay.headless.commerce.shop.by.diagram.resource.v1_0.PinResource;
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

	public static void setDiagramResourceComponentServiceObjects(
		ComponentServiceObjects<DiagramResource>
			diagramResourceComponentServiceObjects) {

		_diagramResourceComponentServiceObjects =
			diagramResourceComponentServiceObjects;
	}

	public static void setDiagramEntryResourceComponentServiceObjects(
		ComponentServiceObjects<DiagramEntryResource>
			diagramEntryResourceComponentServiceObjects) {

		_diagramEntryResourceComponentServiceObjects =
			diagramEntryResourceComponentServiceObjects;
	}

	public static void setPinResourceComponentServiceObjects(
		ComponentServiceObjects<PinResource>
			pinResourceComponentServiceObjects) {

		_pinResourceComponentServiceObjects =
			pinResourceComponentServiceObjects;
	}

	@GraphQLField
	public Diagram updateProductByExternalReferenceCodeDiagram(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("diagram") Diagram diagram)
		throws Exception {

		return _applyComponentServiceObjects(
			_diagramResourceComponentServiceObjects,
			this::_populateResourceContext,
			diagramResource ->
				diagramResource.putProductByExternalReferenceCodeDiagram(
					externalReferenceCode, diagram));
	}

	@GraphQLField
	public Diagram updateProductIdDiagram(
			@GraphQLName("id") Long id, @GraphQLName("diagram") Diagram diagram)
		throws Exception {

		return _applyComponentServiceObjects(
			_diagramResourceComponentServiceObjects,
			this::_populateResourceContext,
			diagramResource -> diagramResource.putProductIdDiagram(
				id, diagram));
	}

	@GraphQLField
	public Response deleteDiagramEntry(@GraphQLName("id") Long id)
		throws Exception {

		return _applyComponentServiceObjects(
			_diagramEntryResourceComponentServiceObjects,
			this::_populateResourceContext,
			diagramEntryResource -> diagramEntryResource.deleteDiagramEntry(
				id));
	}

	@GraphQLField
	public Response deleteDiagramEntryBatch(
			@GraphQLName("id") Long id,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_diagramEntryResourceComponentServiceObjects,
			this::_populateResourceContext,
			diagramEntryResource ->
				diagramEntryResource.deleteDiagramEntryBatch(
					id, callbackURL, object));
	}

	@GraphQLField
	public Response patchDiagramEntry(
			@GraphQLName("id") Long id,
			@GraphQLName("diagramEntry") DiagramEntry diagramEntry)
		throws Exception {

		return _applyComponentServiceObjects(
			_diagramEntryResourceComponentServiceObjects,
			this::_populateResourceContext,
			diagramEntryResource -> diagramEntryResource.patchDiagramEntry(
				id, diagramEntry));
	}

	@GraphQLField
	public DiagramEntry createProductByExternalReferenceCodeDiagramEntry(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("diagramEntry") DiagramEntry diagramEntry)
		throws Exception {

		return _applyComponentServiceObjects(
			_diagramEntryResourceComponentServiceObjects,
			this::_populateResourceContext,
			diagramEntryResource ->
				diagramEntryResource.
					postProductByExternalReferenceCodeDiagramEntry(
						externalReferenceCode, diagramEntry));
	}

	@GraphQLField
	public DiagramEntry createProductIdDiagramEntry(
			@GraphQLName("id") Long id,
			@GraphQLName("diagramEntry") DiagramEntry diagramEntry)
		throws Exception {

		return _applyComponentServiceObjects(
			_diagramEntryResourceComponentServiceObjects,
			this::_populateResourceContext,
			diagramEntryResource ->
				diagramEntryResource.postProductIdDiagramEntry(
					id, diagramEntry));
	}

	@GraphQLField
	public Response deletePin(@GraphQLName("id") Long id) throws Exception {
		return _applyComponentServiceObjects(
			_pinResourceComponentServiceObjects, this::_populateResourceContext,
			pinResource -> pinResource.deletePin(id));
	}

	@GraphQLField
	public Response deletePinBatch(
			@GraphQLName("id") Long id,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_pinResourceComponentServiceObjects, this::_populateResourceContext,
			pinResource -> pinResource.deletePinBatch(id, callbackURL, object));
	}

	@GraphQLField
	public Response patchPin(
			@GraphQLName("id") Long id, @GraphQLName("pin") Pin pin)
		throws Exception {

		return _applyComponentServiceObjects(
			_pinResourceComponentServiceObjects, this::_populateResourceContext,
			pinResource -> pinResource.patchPin(id, pin));
	}

	@GraphQLField
	public Pin createProductByExternalReferenceCodePin(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("pin") Pin pin)
		throws Exception {

		return _applyComponentServiceObjects(
			_pinResourceComponentServiceObjects, this::_populateResourceContext,
			pinResource -> pinResource.postProductByExternalReferenceCodePin(
				externalReferenceCode, pin));
	}

	@GraphQLField
	public Pin createProductIdPin(
			@GraphQLName("id") Long id, @GraphQLName("pin") Pin pin)
		throws Exception {

		return _applyComponentServiceObjects(
			_pinResourceComponentServiceObjects, this::_populateResourceContext,
			pinResource -> pinResource.postProductIdPin(id, pin));
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

	private void _populateResourceContext(DiagramResource diagramResource)
		throws Exception {

		diagramResource.setContextAcceptLanguage(_acceptLanguage);
		diagramResource.setContextCompany(_company);
		diagramResource.setContextHttpServletRequest(_httpServletRequest);
		diagramResource.setContextHttpServletResponse(_httpServletResponse);
		diagramResource.setContextUriInfo(_uriInfo);
		diagramResource.setContextUser(_user);
		diagramResource.setGroupLocalService(_groupLocalService);
		diagramResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			DiagramEntryResource diagramEntryResource)
		throws Exception {

		diagramEntryResource.setContextAcceptLanguage(_acceptLanguage);
		diagramEntryResource.setContextCompany(_company);
		diagramEntryResource.setContextHttpServletRequest(_httpServletRequest);
		diagramEntryResource.setContextHttpServletResponse(
			_httpServletResponse);
		diagramEntryResource.setContextUriInfo(_uriInfo);
		diagramEntryResource.setContextUser(_user);
		diagramEntryResource.setGroupLocalService(_groupLocalService);
		diagramEntryResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(PinResource pinResource)
		throws Exception {

		pinResource.setContextAcceptLanguage(_acceptLanguage);
		pinResource.setContextCompany(_company);
		pinResource.setContextHttpServletRequest(_httpServletRequest);
		pinResource.setContextHttpServletResponse(_httpServletResponse);
		pinResource.setContextUriInfo(_uriInfo);
		pinResource.setContextUser(_user);
		pinResource.setGroupLocalService(_groupLocalService);
		pinResource.setRoleLocalService(_roleLocalService);
	}

	private static ComponentServiceObjects<DiagramResource>
		_diagramResourceComponentServiceObjects;
	private static ComponentServiceObjects<DiagramEntryResource>
		_diagramEntryResourceComponentServiceObjects;
	private static ComponentServiceObjects<PinResource>
		_pinResourceComponentServiceObjects;

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