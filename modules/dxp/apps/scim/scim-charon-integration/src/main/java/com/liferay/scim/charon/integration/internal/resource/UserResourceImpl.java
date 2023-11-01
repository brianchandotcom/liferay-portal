/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.scim.charon.integration.internal.resource;

import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.expando.kernel.service.ExpandoValueLocalService;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.scim.charon.integration.internal.ScimUserManager;
import com.liferay.scim.charon.integration.internal.constants.ScimConstants;
import com.liferay.scim.charon.integration.internal.user.manager.UserManagerImpl;
import com.liferay.scim.resource.UserResource;

import java.io.File;

import java.util.Collections;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import org.wso2.charon3.core.config.SCIMUserSchemaExtensionBuilder;
import org.wso2.charon3.core.extensions.UserManager;
import org.wso2.charon3.core.protocol.SCIMResponse;
import org.wso2.charon3.core.protocol.endpoints.AbstractResourceManager;
import org.wso2.charon3.core.protocol.endpoints.UserResourceManager;

/**
 * @author Rafael Praxedes
 */
@Component(service = UserResource.class)
public class UserResourceImpl implements UserResource {

	@Override
	public Response createUser(String resourceString) {
		return _buildResponse(
			_userResourceManager.create(
				resourceString, _userManager, null, null));
	}

	@Override
	public Response deleteUser(String id) {
		return _buildResponse(_userResourceManager.delete(id, _userManager));
	}

	@Override
	public Response getUser(String id) {
		return _buildResponse(
			_userResourceManager.get(id, _userManager, null, null));
	}

	@Override
	public Response listUsers(int count, int startIndex) {
		return _buildResponse(
			_userResourceManager.listWithGET(
				_userManager, null, startIndex, count, null, null, null, null,
				null));
	}

	@Override
	public Response searchUser(String resourceString) {
		return _buildResponse(
			_userResourceManager.listWithPOST(resourceString, _userManager));
	}

	@Override
	public Response updateUser(String id, String resourceString) {
		return _buildResponse(
			_userResourceManager.updateWithPUT(
				id, resourceString, _userManager, null, null));
	}

	@Activate
	protected void activate() throws Exception {
		AbstractResourceManager.setEndpointURLMap(
			Collections.singletonMap(
				ScimConstants.USER_ENDPOINT, "/o/scim/Users"));

		_registerLiferayUserSchemaExtension();

		_userManager = new UserManagerImpl(
			_companyLocalService,
			new ScimUserManager(
				_classNameLocalService, _companyLocalService,
				_configurationAdmin, _expandoColumnLocalService,
				_expandoTableLocalService, _expandoValueLocalService,
				_userLocalService));
	}

	private Response _buildResponse(SCIMResponse scimResponse) {
		Response.ResponseBuilder responseBuilder = Response.status(
			scimResponse.getResponseStatus());

		Map<String, String> httpHeaders = scimResponse.getHeaderParamMap();

		if (MapUtil.isNotEmpty(httpHeaders)) {
			for (Map.Entry<String, String> entry : httpHeaders.entrySet()) {
				responseBuilder.header(entry.getKey(), entry.getValue());
			}
		}

		if (scimResponse.getResponseMessage() != null) {
			responseBuilder.entity(scimResponse.getResponseMessage());
		}

		return responseBuilder.build();
	}

	private void _registerLiferayUserSchemaExtension() throws Exception {
		File file = _file.createTempFile(
			UserResourceImpl.class.getResourceAsStream(
				"dependencies/liferay-user-schema-extension.json"));

		SCIMUserSchemaExtensionBuilder scimUserSchemaExtensionBuilder =
			SCIMUserSchemaExtensionBuilder.getInstance();

		scimUserSchemaExtensionBuilder.buildUserSchemaExtension(file.getPath());
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	@Reference
	private ExpandoColumnLocalService _expandoColumnLocalService;

	@Reference
	private ExpandoTableLocalService _expandoTableLocalService;

	@Reference
	private ExpandoValueLocalService _expandoValueLocalService;

	@Reference
	private com.liferay.portal.kernel.util.File _file;

	@Reference
	private UserLocalService _userLocalService;

	private UserManager _userManager;
	private final UserResourceManager _userResourceManager =
		new UserResourceManager();

}