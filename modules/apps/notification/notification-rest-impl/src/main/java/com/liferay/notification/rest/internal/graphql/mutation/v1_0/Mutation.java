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

package com.liferay.notification.rest.internal.graphql.mutation.v1_0;

import com.liferay.notification.rest.dto.v1_0.NotificationTemplate;
import com.liferay.notification.rest.resource.v1_0.NotificationQueueEntryResource;
import com.liferay.notification.rest.resource.v1_0.NotificationTemplateResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
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
 * @author Gabriel Albuquerque
 * @generated
 */
@Generated("")
public class Mutation {

	public static void setNotificationQueueEntryResourceComponentServiceObjects(
		ComponentServiceObjects<NotificationQueueEntryResource>
			notificationQueueEntryResourceComponentServiceObjects) {

		_notificationQueueEntryResourceComponentServiceObjects =
			notificationQueueEntryResourceComponentServiceObjects;
	}

	public static void setNotificationTemplateResourceComponentServiceObjects(
		ComponentServiceObjects<NotificationTemplateResource>
			notificationTemplateResourceComponentServiceObjects) {

		_notificationTemplateResourceComponentServiceObjects =
			notificationTemplateResourceComponentServiceObjects;
	}

	@GraphQLField
	public boolean deleteNotificationQueueEntry(
			@GraphQLName("notificationQueueEntryId") Long
				notificationQueueEntryId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_notificationQueueEntryResourceComponentServiceObjects,
			this::_populateResourceContext,
			notificationQueueEntryResource ->
				notificationQueueEntryResource.deleteNotificationQueueEntry(
					notificationQueueEntryId));

		return true;
	}

	@GraphQLField
	public Response deleteNotificationQueueEntryBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_notificationQueueEntryResourceComponentServiceObjects,
			this::_populateResourceContext,
			notificationQueueEntryResource ->
				notificationQueueEntryResource.
					deleteNotificationQueueEntryBatch(callbackURL, object));
	}

	@GraphQLField
	public boolean updateNotificationQueueEntryResend(
			@GraphQLName("notificationQueueEntryId") Long
				notificationQueueEntryId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_notificationQueueEntryResourceComponentServiceObjects,
			this::_populateResourceContext,
			notificationQueueEntryResource ->
				notificationQueueEntryResource.putNotificationQueueEntryResend(
					notificationQueueEntryId));

		return true;
	}

	@GraphQLField
	public NotificationTemplate createNotificationTemplate(
			@GraphQLName("notificationTemplate") NotificationTemplate
				notificationTemplate)
		throws Exception {

		return _applyComponentServiceObjects(
			_notificationTemplateResourceComponentServiceObjects,
			this::_populateResourceContext,
			notificationTemplateResource ->
				notificationTemplateResource.postNotificationTemplate(
					notificationTemplate));
	}

	@GraphQLField
	public Response createNotificationTemplateBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_notificationTemplateResourceComponentServiceObjects,
			this::_populateResourceContext,
			notificationTemplateResource ->
				notificationTemplateResource.postNotificationTemplateBatch(
					callbackURL, object));
	}

	@GraphQLField
	public boolean deleteNotificationTemplate(
			@GraphQLName("notificationTemplateId") Long notificationTemplateId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_notificationTemplateResourceComponentServiceObjects,
			this::_populateResourceContext,
			notificationTemplateResource ->
				notificationTemplateResource.deleteNotificationTemplate(
					notificationTemplateId));

		return true;
	}

	@GraphQLField
	public Response deleteNotificationTemplateBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_notificationTemplateResourceComponentServiceObjects,
			this::_populateResourceContext,
			notificationTemplateResource ->
				notificationTemplateResource.deleteNotificationTemplateBatch(
					callbackURL, object));
	}

	@GraphQLField
	public NotificationTemplate patchNotificationTemplate(
			@GraphQLName("notificationTemplateId") Long notificationTemplateId,
			@GraphQLName("notificationTemplate") NotificationTemplate
				notificationTemplate)
		throws Exception {

		return _applyComponentServiceObjects(
			_notificationTemplateResourceComponentServiceObjects,
			this::_populateResourceContext,
			notificationTemplateResource ->
				notificationTemplateResource.patchNotificationTemplate(
					notificationTemplateId, notificationTemplate));
	}

	@GraphQLField
	public NotificationTemplate updateNotificationTemplate(
			@GraphQLName("notificationTemplateId") Long notificationTemplateId,
			@GraphQLName("notificationTemplate") NotificationTemplate
				notificationTemplate)
		throws Exception {

		return _applyComponentServiceObjects(
			_notificationTemplateResourceComponentServiceObjects,
			this::_populateResourceContext,
			notificationTemplateResource ->
				notificationTemplateResource.putNotificationTemplate(
					notificationTemplateId, notificationTemplate));
	}

	@GraphQLField
	public Response updateNotificationTemplateBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_notificationTemplateResourceComponentServiceObjects,
			this::_populateResourceContext,
			notificationTemplateResource ->
				notificationTemplateResource.putNotificationTemplateBatch(
					callbackURL, object));
	}

	@GraphQLField
	public NotificationTemplate createNotificationTemplateCopy(
			@GraphQLName("notificationTemplateId") Long notificationTemplateId)
		throws Exception {

		return _applyComponentServiceObjects(
			_notificationTemplateResourceComponentServiceObjects,
			this::_populateResourceContext,
			notificationTemplateResource ->
				notificationTemplateResource.postNotificationTemplateCopy(
					notificationTemplateId));
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
			NotificationQueueEntryResource notificationQueueEntryResource)
		throws Exception {

		notificationQueueEntryResource.setContextAcceptLanguage(
			_acceptLanguage);
		notificationQueueEntryResource.setContextCompany(_company);
		notificationQueueEntryResource.setContextHttpServletRequest(
			_httpServletRequest);
		notificationQueueEntryResource.setContextHttpServletResponse(
			_httpServletResponse);
		notificationQueueEntryResource.setContextUriInfo(_uriInfo);
		notificationQueueEntryResource.setContextUser(_user);
		notificationQueueEntryResource.setGroupLocalService(_groupLocalService);
		notificationQueueEntryResource.setRoleLocalService(_roleLocalService);

		notificationQueueEntryResource.setVulcanBatchEngineImportTaskResource(
			_vulcanBatchEngineImportTaskResource);
	}

	private void _populateResourceContext(
			NotificationTemplateResource notificationTemplateResource)
		throws Exception {

		notificationTemplateResource.setContextAcceptLanguage(_acceptLanguage);
		notificationTemplateResource.setContextCompany(_company);
		notificationTemplateResource.setContextHttpServletRequest(
			_httpServletRequest);
		notificationTemplateResource.setContextHttpServletResponse(
			_httpServletResponse);
		notificationTemplateResource.setContextUriInfo(_uriInfo);
		notificationTemplateResource.setContextUser(_user);
		notificationTemplateResource.setGroupLocalService(_groupLocalService);
		notificationTemplateResource.setRoleLocalService(_roleLocalService);

		notificationTemplateResource.setVulcanBatchEngineImportTaskResource(
			_vulcanBatchEngineImportTaskResource);
	}

	private static ComponentServiceObjects<NotificationQueueEntryResource>
		_notificationQueueEntryResourceComponentServiceObjects;
	private static ComponentServiceObjects<NotificationTemplateResource>
		_notificationTemplateResourceComponentServiceObjects;

	private AcceptLanguage _acceptLanguage;
	private com.liferay.portal.kernel.model.Company _company;
	private GroupLocalService _groupLocalService;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private RoleLocalService _roleLocalService;
	private BiFunction<Object, String, Sort[]> _sortsBiFunction;
	private UriInfo _uriInfo;
	private com.liferay.portal.kernel.model.User _user;
	private VulcanBatchEngineImportTaskResource
		_vulcanBatchEngineImportTaskResource;

}