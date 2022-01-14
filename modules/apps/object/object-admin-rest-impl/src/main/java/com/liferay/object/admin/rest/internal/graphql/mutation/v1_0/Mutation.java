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

package com.liferay.object.admin.rest.internal.graphql.mutation.v1_0;

import com.liferay.object.admin.rest.dto.v1_0.ObjectAction;
import com.liferay.object.admin.rest.dto.v1_0.ObjectDefinition;
import com.liferay.object.admin.rest.dto.v1_0.ObjectField;
import com.liferay.object.admin.rest.dto.v1_0.ObjectLayout;
import com.liferay.object.admin.rest.dto.v1_0.ObjectRelationship;
import com.liferay.object.admin.rest.dto.v1_0.ObjectView;
import com.liferay.object.admin.rest.resource.v1_0.ObjectActionResource;
import com.liferay.object.admin.rest.resource.v1_0.ObjectDefinitionResource;
import com.liferay.object.admin.rest.resource.v1_0.ObjectFieldResource;
import com.liferay.object.admin.rest.resource.v1_0.ObjectLayoutResource;
import com.liferay.object.admin.rest.resource.v1_0.ObjectRelationshipResource;
import com.liferay.object.admin.rest.resource.v1_0.ObjectViewResource;
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
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class Mutation {

	public static void setObjectActionResourceComponentServiceObjects(
		ComponentServiceObjects<ObjectActionResource>
			objectActionResourceComponentServiceObjects) {

		_objectActionResourceComponentServiceObjects =
			objectActionResourceComponentServiceObjects;
	}

	public static void setObjectDefinitionResourceComponentServiceObjects(
		ComponentServiceObjects<ObjectDefinitionResource>
			objectDefinitionResourceComponentServiceObjects) {

		_objectDefinitionResourceComponentServiceObjects =
			objectDefinitionResourceComponentServiceObjects;
	}

	public static void setObjectFieldResourceComponentServiceObjects(
		ComponentServiceObjects<ObjectFieldResource>
			objectFieldResourceComponentServiceObjects) {

		_objectFieldResourceComponentServiceObjects =
			objectFieldResourceComponentServiceObjects;
	}

	public static void setObjectLayoutResourceComponentServiceObjects(
		ComponentServiceObjects<ObjectLayoutResource>
			objectLayoutResourceComponentServiceObjects) {

		_objectLayoutResourceComponentServiceObjects =
			objectLayoutResourceComponentServiceObjects;
	}

	public static void setObjectRelationshipResourceComponentServiceObjects(
		ComponentServiceObjects<ObjectRelationshipResource>
			objectRelationshipResourceComponentServiceObjects) {

		_objectRelationshipResourceComponentServiceObjects =
			objectRelationshipResourceComponentServiceObjects;
	}

	public static void setObjectViewResourceComponentServiceObjects(
		ComponentServiceObjects<ObjectViewResource>
			objectViewResourceComponentServiceObjects) {

		_objectViewResourceComponentServiceObjects =
			objectViewResourceComponentServiceObjects;
	}

	@GraphQLField
	public boolean deleteObjectAction(
			@GraphQLName("objectActionId") Long objectActionId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_objectActionResourceComponentServiceObjects,
			this::_populateResourceContext,
			objectActionResource -> objectActionResource.deleteObjectAction(
				objectActionId));

		return true;
	}

	@GraphQLField
	public Response deleteObjectActionBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_objectActionResourceComponentServiceObjects,
			this::_populateResourceContext,
			objectActionResource ->
				objectActionResource.deleteObjectActionBatch(
					callbackURL, object));
	}

	@GraphQLField
	public ObjectAction patchObjectAction(
			@GraphQLName("objectActionId") Long objectActionId,
			@GraphQLName("objectAction") ObjectAction objectAction)
		throws Exception {

		return _applyComponentServiceObjects(
			_objectActionResourceComponentServiceObjects,
			this::_populateResourceContext,
			objectActionResource -> objectActionResource.patchObjectAction(
				objectActionId, objectAction));
	}

	@GraphQLField
	public ObjectAction updateObjectAction(
			@GraphQLName("objectActionId") Long objectActionId,
			@GraphQLName("objectAction") ObjectAction objectAction)
		throws Exception {

		return _applyComponentServiceObjects(
			_objectActionResourceComponentServiceObjects,
			this::_populateResourceContext,
			objectActionResource -> objectActionResource.putObjectAction(
				objectActionId, objectAction));
	}

	@GraphQLField
	public Response updateObjectActionBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_objectActionResourceComponentServiceObjects,
			this::_populateResourceContext,
			objectActionResource -> objectActionResource.putObjectActionBatch(
				callbackURL, object));
	}

	@GraphQLField
	public ObjectAction createObjectDefinitionObjectAction(
			@GraphQLName("objectDefinitionId") Long objectDefinitionId,
			@GraphQLName("objectAction") ObjectAction objectAction)
		throws Exception {

		return _applyComponentServiceObjects(
			_objectActionResourceComponentServiceObjects,
			this::_populateResourceContext,
			objectActionResource ->
				objectActionResource.postObjectDefinitionObjectAction(
					objectDefinitionId, objectAction));
	}

	@GraphQLField
	public Response createObjectDefinitionObjectActionBatch(
			@GraphQLName("objectDefinitionId") Long objectDefinitionId,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_objectActionResourceComponentServiceObjects,
			this::_populateResourceContext,
			objectActionResource ->
				objectActionResource.postObjectDefinitionObjectActionBatch(
					objectDefinitionId, callbackURL, object));
	}

	@GraphQLField
	public ObjectDefinition createObjectDefinition(
			@GraphQLName("objectDefinition") ObjectDefinition objectDefinition)
		throws Exception {

		return _applyComponentServiceObjects(
			_objectDefinitionResourceComponentServiceObjects,
			this::_populateResourceContext,
			objectDefinitionResource ->
				objectDefinitionResource.postObjectDefinition(
					objectDefinition));
	}

	@GraphQLField
	public Response createObjectDefinitionBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_objectDefinitionResourceComponentServiceObjects,
			this::_populateResourceContext,
			objectDefinitionResource ->
				objectDefinitionResource.postObjectDefinitionBatch(
					callbackURL, object));
	}

	@GraphQLField
	public boolean deleteObjectDefinition(
			@GraphQLName("objectDefinitionId") Long objectDefinitionId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_objectDefinitionResourceComponentServiceObjects,
			this::_populateResourceContext,
			objectDefinitionResource ->
				objectDefinitionResource.deleteObjectDefinition(
					objectDefinitionId));

		return true;
	}

	@GraphQLField
	public Response deleteObjectDefinitionBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_objectDefinitionResourceComponentServiceObjects,
			this::_populateResourceContext,
			objectDefinitionResource ->
				objectDefinitionResource.deleteObjectDefinitionBatch(
					callbackURL, object));
	}

	@GraphQLField
	public ObjectDefinition patchObjectDefinition(
			@GraphQLName("objectDefinitionId") Long objectDefinitionId,
			@GraphQLName("objectDefinition") ObjectDefinition objectDefinition)
		throws Exception {

		return _applyComponentServiceObjects(
			_objectDefinitionResourceComponentServiceObjects,
			this::_populateResourceContext,
			objectDefinitionResource ->
				objectDefinitionResource.patchObjectDefinition(
					objectDefinitionId, objectDefinition));
	}

	@GraphQLField
	public ObjectDefinition updateObjectDefinition(
			@GraphQLName("objectDefinitionId") Long objectDefinitionId,
			@GraphQLName("objectDefinition") ObjectDefinition objectDefinition)
		throws Exception {

		return _applyComponentServiceObjects(
			_objectDefinitionResourceComponentServiceObjects,
			this::_populateResourceContext,
			objectDefinitionResource ->
				objectDefinitionResource.putObjectDefinition(
					objectDefinitionId, objectDefinition));
	}

	@GraphQLField
	public Response updateObjectDefinitionBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_objectDefinitionResourceComponentServiceObjects,
			this::_populateResourceContext,
			objectDefinitionResource ->
				objectDefinitionResource.putObjectDefinitionBatch(
					callbackURL, object));
	}

	@GraphQLField
	public boolean createObjectDefinitionPublish(
			@GraphQLName("objectDefinitionId") Long objectDefinitionId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_objectDefinitionResourceComponentServiceObjects,
			this::_populateResourceContext,
			objectDefinitionResource ->
				objectDefinitionResource.postObjectDefinitionPublish(
					objectDefinitionId));

		return true;
	}

	@GraphQLField
	public ObjectField createObjectDefinitionObjectField(
			@GraphQLName("objectDefinitionId") Long objectDefinitionId,
			@GraphQLName("objectField") ObjectField objectField)
		throws Exception {

		return _applyComponentServiceObjects(
			_objectFieldResourceComponentServiceObjects,
			this::_populateResourceContext,
			objectFieldResource ->
				objectFieldResource.postObjectDefinitionObjectField(
					objectDefinitionId, objectField));
	}

	@GraphQLField
	public Response createObjectDefinitionObjectFieldBatch(
			@GraphQLName("objectDefinitionId") Long objectDefinitionId,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_objectFieldResourceComponentServiceObjects,
			this::_populateResourceContext,
			objectFieldResource ->
				objectFieldResource.postObjectDefinitionObjectFieldBatch(
					objectDefinitionId, callbackURL, object));
	}

	@GraphQLField
	public boolean deleteObjectField(
			@GraphQLName("objectFieldId") Long objectFieldId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_objectFieldResourceComponentServiceObjects,
			this::_populateResourceContext,
			objectFieldResource -> objectFieldResource.deleteObjectField(
				objectFieldId));

		return true;
	}

	@GraphQLField
	public Response deleteObjectFieldBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_objectFieldResourceComponentServiceObjects,
			this::_populateResourceContext,
			objectFieldResource -> objectFieldResource.deleteObjectFieldBatch(
				callbackURL, object));
	}

	@GraphQLField
	public ObjectField patchObjectField(
			@GraphQLName("objectFieldId") Long objectFieldId,
			@GraphQLName("objectField") ObjectField objectField)
		throws Exception {

		return _applyComponentServiceObjects(
			_objectFieldResourceComponentServiceObjects,
			this::_populateResourceContext,
			objectFieldResource -> objectFieldResource.patchObjectField(
				objectFieldId, objectField));
	}

	@GraphQLField
	public ObjectField updateObjectField(
			@GraphQLName("objectFieldId") Long objectFieldId,
			@GraphQLName("objectField") ObjectField objectField)
		throws Exception {

		return _applyComponentServiceObjects(
			_objectFieldResourceComponentServiceObjects,
			this::_populateResourceContext,
			objectFieldResource -> objectFieldResource.putObjectField(
				objectFieldId, objectField));
	}

	@GraphQLField
	public Response updateObjectFieldBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_objectFieldResourceComponentServiceObjects,
			this::_populateResourceContext,
			objectFieldResource -> objectFieldResource.putObjectFieldBatch(
				callbackURL, object));
	}

	@GraphQLField
	public ObjectLayout createObjectDefinitionObjectLayout(
			@GraphQLName("objectDefinitionId") Long objectDefinitionId,
			@GraphQLName("objectLayout") ObjectLayout objectLayout)
		throws Exception {

		return _applyComponentServiceObjects(
			_objectLayoutResourceComponentServiceObjects,
			this::_populateResourceContext,
			objectLayoutResource ->
				objectLayoutResource.postObjectDefinitionObjectLayout(
					objectDefinitionId, objectLayout));
	}

	@GraphQLField
	public Response createObjectDefinitionObjectLayoutBatch(
			@GraphQLName("objectDefinitionId") Long objectDefinitionId,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_objectLayoutResourceComponentServiceObjects,
			this::_populateResourceContext,
			objectLayoutResource ->
				objectLayoutResource.postObjectDefinitionObjectLayoutBatch(
					objectDefinitionId, callbackURL, object));
	}

	@GraphQLField
	public boolean deleteObjectLayout(
			@GraphQLName("objectLayoutId") Long objectLayoutId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_objectLayoutResourceComponentServiceObjects,
			this::_populateResourceContext,
			objectLayoutResource -> objectLayoutResource.deleteObjectLayout(
				objectLayoutId));

		return true;
	}

	@GraphQLField
	public Response deleteObjectLayoutBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_objectLayoutResourceComponentServiceObjects,
			this::_populateResourceContext,
			objectLayoutResource ->
				objectLayoutResource.deleteObjectLayoutBatch(
					callbackURL, object));
	}

	@GraphQLField
	public ObjectLayout updateObjectLayout(
			@GraphQLName("objectLayoutId") Long objectLayoutId,
			@GraphQLName("objectLayout") ObjectLayout objectLayout)
		throws Exception {

		return _applyComponentServiceObjects(
			_objectLayoutResourceComponentServiceObjects,
			this::_populateResourceContext,
			objectLayoutResource -> objectLayoutResource.putObjectLayout(
				objectLayoutId, objectLayout));
	}

	@GraphQLField
	public Response updateObjectLayoutBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_objectLayoutResourceComponentServiceObjects,
			this::_populateResourceContext,
			objectLayoutResource -> objectLayoutResource.putObjectLayoutBatch(
				callbackURL, object));
	}

	@GraphQLField
	public ObjectRelationship createObjectDefinitionObjectRelationship(
			@GraphQLName("objectDefinitionId") Long objectDefinitionId,
			@GraphQLName("objectRelationship") ObjectRelationship
				objectRelationship)
		throws Exception {

		return _applyComponentServiceObjects(
			_objectRelationshipResourceComponentServiceObjects,
			this::_populateResourceContext,
			objectRelationshipResource ->
				objectRelationshipResource.
					postObjectDefinitionObjectRelationship(
						objectDefinitionId, objectRelationship));
	}

	@GraphQLField
	public Response createObjectDefinitionObjectRelationshipBatch(
			@GraphQLName("objectDefinitionId") Long objectDefinitionId,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_objectRelationshipResourceComponentServiceObjects,
			this::_populateResourceContext,
			objectRelationshipResource ->
				objectRelationshipResource.
					postObjectDefinitionObjectRelationshipBatch(
						objectDefinitionId, callbackURL, object));
	}

	@GraphQLField
	public boolean deleteObjectRelationship(
			@GraphQLName("objectRelationshipId") Long objectRelationshipId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_objectRelationshipResourceComponentServiceObjects,
			this::_populateResourceContext,
			objectRelationshipResource ->
				objectRelationshipResource.deleteObjectRelationship(
					objectRelationshipId));

		return true;
	}

	@GraphQLField
	public Response deleteObjectRelationshipBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_objectRelationshipResourceComponentServiceObjects,
			this::_populateResourceContext,
			objectRelationshipResource ->
				objectRelationshipResource.deleteObjectRelationshipBatch(
					callbackURL, object));
	}

	@GraphQLField
	public ObjectRelationship updateObjectRelationship(
			@GraphQLName("objectRelationshipId") Long objectRelationshipId,
			@GraphQLName("objectRelationship") ObjectRelationship
				objectRelationship)
		throws Exception {

		return _applyComponentServiceObjects(
			_objectRelationshipResourceComponentServiceObjects,
			this::_populateResourceContext,
			objectRelationshipResource ->
				objectRelationshipResource.putObjectRelationship(
					objectRelationshipId, objectRelationship));
	}

	@GraphQLField
	public Response updateObjectRelationshipBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_objectRelationshipResourceComponentServiceObjects,
			this::_populateResourceContext,
			objectRelationshipResource ->
				objectRelationshipResource.putObjectRelationshipBatch(
					callbackURL, object));
	}

	@GraphQLField
	public ObjectView createObjectDefinitionObjectView(
			@GraphQLName("objectDefinitionId") Long objectDefinitionId,
			@GraphQLName("objectView") ObjectView objectView)
		throws Exception {

		return _applyComponentServiceObjects(
			_objectViewResourceComponentServiceObjects,
			this::_populateResourceContext,
			objectViewResource ->
				objectViewResource.postObjectDefinitionObjectView(
					objectDefinitionId, objectView));
	}

	@GraphQLField
	public Response createObjectDefinitionObjectViewBatch(
			@GraphQLName("objectDefinitionId") Long objectDefinitionId,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_objectViewResourceComponentServiceObjects,
			this::_populateResourceContext,
			objectViewResource ->
				objectViewResource.postObjectDefinitionObjectViewBatch(
					objectDefinitionId, callbackURL, object));
	}

	@GraphQLField
	public boolean deleteObjectView(
			@GraphQLName("objectViewId") Long objectViewId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_objectViewResourceComponentServiceObjects,
			this::_populateResourceContext,
			objectViewResource -> objectViewResource.deleteObjectView(
				objectViewId));

		return true;
	}

	@GraphQLField
	public Response deleteObjectViewBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_objectViewResourceComponentServiceObjects,
			this::_populateResourceContext,
			objectViewResource -> objectViewResource.deleteObjectViewBatch(
				callbackURL, object));
	}

	@GraphQLField
	public ObjectView updateObjectView(
			@GraphQLName("objectViewId") Long objectViewId,
			@GraphQLName("objectView") ObjectView objectView)
		throws Exception {

		return _applyComponentServiceObjects(
			_objectViewResourceComponentServiceObjects,
			this::_populateResourceContext,
			objectViewResource -> objectViewResource.putObjectView(
				objectViewId, objectView));
	}

	@GraphQLField
	public Response updateObjectViewBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_objectViewResourceComponentServiceObjects,
			this::_populateResourceContext,
			objectViewResource -> objectViewResource.putObjectViewBatch(
				callbackURL, object));
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
			ObjectActionResource objectActionResource)
		throws Exception {

		objectActionResource.setContextAcceptLanguage(_acceptLanguage);
		objectActionResource.setContextCompany(_company);
		objectActionResource.setContextHttpServletRequest(_httpServletRequest);
		objectActionResource.setContextHttpServletResponse(
			_httpServletResponse);
		objectActionResource.setContextUriInfo(_uriInfo);
		objectActionResource.setContextUser(_user);
		objectActionResource.setGroupLocalService(_groupLocalService);
		objectActionResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			ObjectDefinitionResource objectDefinitionResource)
		throws Exception {

		objectDefinitionResource.setContextAcceptLanguage(_acceptLanguage);
		objectDefinitionResource.setContextCompany(_company);
		objectDefinitionResource.setContextHttpServletRequest(
			_httpServletRequest);
		objectDefinitionResource.setContextHttpServletResponse(
			_httpServletResponse);
		objectDefinitionResource.setContextUriInfo(_uriInfo);
		objectDefinitionResource.setContextUser(_user);
		objectDefinitionResource.setGroupLocalService(_groupLocalService);
		objectDefinitionResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			ObjectFieldResource objectFieldResource)
		throws Exception {

		objectFieldResource.setContextAcceptLanguage(_acceptLanguage);
		objectFieldResource.setContextCompany(_company);
		objectFieldResource.setContextHttpServletRequest(_httpServletRequest);
		objectFieldResource.setContextHttpServletResponse(_httpServletResponse);
		objectFieldResource.setContextUriInfo(_uriInfo);
		objectFieldResource.setContextUser(_user);
		objectFieldResource.setGroupLocalService(_groupLocalService);
		objectFieldResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			ObjectLayoutResource objectLayoutResource)
		throws Exception {

		objectLayoutResource.setContextAcceptLanguage(_acceptLanguage);
		objectLayoutResource.setContextCompany(_company);
		objectLayoutResource.setContextHttpServletRequest(_httpServletRequest);
		objectLayoutResource.setContextHttpServletResponse(
			_httpServletResponse);
		objectLayoutResource.setContextUriInfo(_uriInfo);
		objectLayoutResource.setContextUser(_user);
		objectLayoutResource.setGroupLocalService(_groupLocalService);
		objectLayoutResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			ObjectRelationshipResource objectRelationshipResource)
		throws Exception {

		objectRelationshipResource.setContextAcceptLanguage(_acceptLanguage);
		objectRelationshipResource.setContextCompany(_company);
		objectRelationshipResource.setContextHttpServletRequest(
			_httpServletRequest);
		objectRelationshipResource.setContextHttpServletResponse(
			_httpServletResponse);
		objectRelationshipResource.setContextUriInfo(_uriInfo);
		objectRelationshipResource.setContextUser(_user);
		objectRelationshipResource.setGroupLocalService(_groupLocalService);
		objectRelationshipResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(ObjectViewResource objectViewResource)
		throws Exception {

		objectViewResource.setContextAcceptLanguage(_acceptLanguage);
		objectViewResource.setContextCompany(_company);
		objectViewResource.setContextHttpServletRequest(_httpServletRequest);
		objectViewResource.setContextHttpServletResponse(_httpServletResponse);
		objectViewResource.setContextUriInfo(_uriInfo);
		objectViewResource.setContextUser(_user);
		objectViewResource.setGroupLocalService(_groupLocalService);
		objectViewResource.setRoleLocalService(_roleLocalService);
	}

	private static ComponentServiceObjects<ObjectActionResource>
		_objectActionResourceComponentServiceObjects;
	private static ComponentServiceObjects<ObjectDefinitionResource>
		_objectDefinitionResourceComponentServiceObjects;
	private static ComponentServiceObjects<ObjectFieldResource>
		_objectFieldResourceComponentServiceObjects;
	private static ComponentServiceObjects<ObjectLayoutResource>
		_objectLayoutResourceComponentServiceObjects;
	private static ComponentServiceObjects<ObjectRelationshipResource>
		_objectRelationshipResourceComponentServiceObjects;
	private static ComponentServiceObjects<ObjectViewResource>
		_objectViewResourceComponentServiceObjects;

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