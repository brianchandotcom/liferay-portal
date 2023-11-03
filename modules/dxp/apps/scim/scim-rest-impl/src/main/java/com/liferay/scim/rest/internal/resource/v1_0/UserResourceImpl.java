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
import com.liferay.scim.rest.dto.v1_0.User;
import com.liferay.scim.rest.internal.constants.ScimConstants;
import com.liferay.scim.rest.internal.manager.UserManagerImpl;
import com.liferay.scim.rest.resource.v1_0.UserResource;

import java.io.File;

import java.util.Collections;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

import org.wso2.charon3.core.config.SCIMUserSchemaExtensionBuilder;
import org.wso2.charon3.core.extensions.UserManager;
import org.wso2.charon3.core.protocol.SCIMResponse;
import org.wso2.charon3.core.protocol.endpoints.AbstractResourceManager;
import org.wso2.charon3.core.protocol.endpoints.UserResourceManager;

/**
 * @author Olivér Kecskeméty
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/user.properties",
	scope = ServiceScope.PROTOTYPE, service = UserResource.class
)
public class UserResourceImpl extends BaseUserResourceImpl {

	@Override
	public Object getV2User(Integer count, Integer startIndex)
		throws Exception {

		return _buildResponse(
			_userResourceManager.listWithGET(
				_userManager, null, startIndex, count, null, null, null, null,
				null));
	}

	@Override
	public Object getV2UserById(String id) throws Exception {
		return _buildResponse(
			_userResourceManager.get(id, _userManager, null, null));
	}

	@Override
	public Response postV2User(User user) throws Exception {
		return _buildResponse(
			_userResourceManager.create(
				user.toString(), _userManager, null, null));
	}

	@Override
	public Response putV2User(String id, User user) throws Exception {
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
			_classNameLocalService, _companyLocalService, _configurationAdmin,
			_expandoColumnLocalService, _expandoTableLocalService,
			_expandoValueLocalService, _userLocalService);
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