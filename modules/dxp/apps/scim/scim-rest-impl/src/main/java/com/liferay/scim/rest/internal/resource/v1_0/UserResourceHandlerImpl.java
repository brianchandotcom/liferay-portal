/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.scim.rest.internal.resource.v1_0;

import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.expando.kernel.service.ExpandoValueLocalService;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.scim.rest.dto.v1_0.QueryAttributes;
import com.liferay.scim.rest.dto.v1_0.User;
import com.liferay.scim.rest.internal.ScimUserManager;
import com.liferay.scim.rest.internal.constants.ScimConstants;
import com.liferay.scim.rest.internal.manager.UserManagerImpl;
import com.liferay.scim.rest.resource.handler.UserResourceHandler;

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
@Component(service = UserResourceHandler.class)
public class UserResourceHandlerImpl implements UserResourceHandler {

	public Response deleteV2User(String id) {
		return _buildResponse(_userResourceManager.delete(id, _userManager));
	}

	public Response getV2User(Integer count, Integer startIndex) {
		return _buildResponse(
			_userResourceManager.listWithGET(
				_userManager, null, startIndex, count, null, null, null, null,
				null));
	}

	public Response getV2UserById(String id) {
		return _buildResponse(
			_userResourceManager.get(id, _userManager, null, null));
	}

	public Response postV2User(User user) {
		return _buildResponse(
			_userResourceManager.create(
				user.toString(), _userManager, null, null));
	}

	public Response postV2UserSearch(QueryAttributes queryAttributes)
		throws Exception {

		return _buildResponse(
			_userResourceManager.listWithPOST(
				queryAttributes.toString(), _userManager));
	}

	public Response putV2User(String id, User user) {
		return _buildResponse(
			_userResourceManager.updateWithPUT(
				id, user.toString(), _userManager, null, null));
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
			UserResourceHandlerImpl.class.getResourceAsStream(
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