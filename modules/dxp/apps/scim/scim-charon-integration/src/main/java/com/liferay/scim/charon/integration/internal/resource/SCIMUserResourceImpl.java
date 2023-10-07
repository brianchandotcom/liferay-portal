/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.scim.charon.integration.internal.resource;

import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.scim.charon.integration.internal.user.manager.UserManagerImpl;
import com.liferay.scim.resource.SCIMUserResource;
import com.liferay.scim.user.manager.SCIMUser;
import com.liferay.scim.user.manager.SCIMUserManager;

import java.io.File;

import java.util.Collections;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import org.wso2.charon3.core.config.SCIMUserSchemaExtensionBuilder;
import org.wso2.charon3.core.extensions.UserManager;
import org.wso2.charon3.core.protocol.SCIMResponse;
import org.wso2.charon3.core.protocol.endpoints.AbstractResourceManager;
import org.wso2.charon3.core.protocol.endpoints.UserResourceManager;
import org.wso2.charon3.core.schema.SCIMConstants;

/**
 * @author Rafael Praxedes
 */
@Component(service = SCIMUserResource.class)
public class SCIMUserResourceImpl implements SCIMUserResource {

	@Override
	public Response addSCIMUser(String resourceString) {
		return _buildResponse(
			_userResourceManager.create(
				resourceString, _userManager, null, null));
	}

	@Override
	public Response deleteSCIMUser(String id) {
		return _buildResponse(_userResourceManager.delete(id, _userManager));
	}

	@Override
	public Response getSCIMUser(String id) {
		return _buildResponse(
			_userResourceManager.get(id, _userManager, null, null));
	}

	@Override
	public Response getSCIMUsers(int count, int startIndex) {
		return _buildResponse(
			_userResourceManager.listWithGET(
				_userManager, null, startIndex, count, null, null, null, null,
				null));
	}

	@Override
	public Response getSCIMUsers(String resourceString) {
		return _buildResponse(
			_userResourceManager.listWithPOST(resourceString, _userManager));
	}

	@Override
	public Response updateSCIMUser(String id, String resourceString) {
		SCIMUser scimUser = _scimUserManager.fetchSCIMUser(
			CompanyThreadLocal.getCompanyId(), GetterUtil.getLong(id));

		if (scimUser != null) {
			return _buildResponse(
				_userResourceManager.updateWithPUT(
					id, resourceString, _userManager, null, null));
		}

		return addSCIMUser(resourceString);
	}

	@Activate
	protected void activate() throws Exception {
		AbstractResourceManager.setEndpointURLMap(
			Collections.singletonMap(
				SCIMConstants.USER_ENDPOINT, "/o/scim/Users"));

		_registerLiferayUserSchemaExtension();

		_userManager = new UserManagerImpl(
			_companyLocalService, _scimUserManager);
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
			SCIMUserResourceImpl.class.getResourceAsStream(
				"dependencies/liferay-user-schema-extension.json"));

		SCIMUserSchemaExtensionBuilder scimUserSchemaExtensionBuilder =
			SCIMUserSchemaExtensionBuilder.getInstance();

		scimUserSchemaExtensionBuilder.buildUserSchemaExtension(file.getPath());
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private com.liferay.portal.kernel.util.File _file;

	@Reference
	private SCIMUserManager _scimUserManager;

	private UserManager _userManager;
	private final UserResourceManager _userResourceManager =
		new UserResourceManager();

}