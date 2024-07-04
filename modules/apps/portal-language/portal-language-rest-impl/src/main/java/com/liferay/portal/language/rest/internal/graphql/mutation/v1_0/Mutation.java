/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.language.rest.internal.graphql.mutation.v1_0;

import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.language.rest.dto.v1_0.Message;
import com.liferay.portal.language.rest.resource.v1_0.MessageResource;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.batch.engine.resource.VulcanBatchEngineExportTaskResource;
import com.liferay.portal.vulcan.batch.engine.resource.VulcanBatchEngineImportTaskResource;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.multipart.MultipartBody;

import java.util.function.BiFunction;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Thiago Buarque
 * @generated
 */
@Generated("")
public class Mutation {

	public static void setMessageResourceComponentServiceObjects(
		ComponentServiceObjects<MessageResource>
			messageResourceComponentServiceObjects) {

		_messageResourceComponentServiceObjects =
			messageResourceComponentServiceObjects;
	}

	@GraphQLField
	public Response createMessagesPageExportBatch(
			@GraphQLName("keys") String[] keys,
			@GraphQLName("languageId") String languageId,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("contentType") String contentType,
			@GraphQLName("fieldNames") String fieldNames)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageResource -> messageResource.postMessagesPageExportBatch(
				keys, languageId, callbackURL, contentType, fieldNames));
	}

	@GraphQLField
	@GraphQLName(
		description = "null", value = "postMessageLanguageIdMultipartBody"
	)
	public boolean createMessage(
			@GraphQLName("languageId") String languageId,
			@GraphQLName("multipartBody") MultipartBody multipartBody)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_messageResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageResource -> messageResource.postMessage(
				languageId, multipartBody));

		return true;
	}

	@GraphQLField
	public Response createMessageBatch(
			@GraphQLName("languageId") String languageId,
			@GraphQLName("multipartBody") MultipartBody multipartBody,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageResource -> messageResource.postMessageBatch(
				languageId, multipartBody, callbackURL, object));
	}

	@GraphQLField
	public Message updateMessage(@GraphQLName("message") Message message)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageResource -> messageResource.putMessage(message));
	}

	@GraphQLField
	public Response updateMessageBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageResource -> messageResource.putMessageBatch(
				callbackURL, object));
	}

	@GraphQLField
	public boolean deleteMessageByKey(
			@GraphQLName("key") String key,
			@GraphQLName("languageId") String languageId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_messageResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageResource -> messageResource.deleteMessageByKey(
				key, languageId));

		return true;
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

	private void _populateResourceContext(MessageResource messageResource)
		throws Exception {

		messageResource.setContextAcceptLanguage(_acceptLanguage);
		messageResource.setContextCompany(_company);
		messageResource.setContextHttpServletRequest(_httpServletRequest);
		messageResource.setContextHttpServletResponse(_httpServletResponse);
		messageResource.setContextUriInfo(_uriInfo);
		messageResource.setContextUser(_user);
		messageResource.setGroupLocalService(_groupLocalService);
		messageResource.setRoleLocalService(_roleLocalService);

		messageResource.setVulcanBatchEngineExportTaskResource(
			_vulcanBatchEngineExportTaskResource);

		messageResource.setVulcanBatchEngineImportTaskResource(
			_vulcanBatchEngineImportTaskResource);
	}

	private static ComponentServiceObjects<MessageResource>
		_messageResourceComponentServiceObjects;

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