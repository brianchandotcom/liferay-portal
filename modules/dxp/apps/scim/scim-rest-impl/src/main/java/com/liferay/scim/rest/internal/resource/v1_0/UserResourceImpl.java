/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.scim.rest.internal.resource.v1_0;

import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.expando.kernel.service.ExpandoValueLocalService;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.scim.rest.dto.v1_0.User;
import com.liferay.scim.rest.internal.manager.UserManagerImpl;
import com.liferay.scim.rest.internal.util.ScimUserUtil;
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
import org.wso2.charon3.core.schema.SCIMConstants;

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
		synchronized (_userResourceManager) {
			if (_userManager != null) {
				return;
			}

			AbstractResourceManager.setEndpointURLMap(
				Collections.singletonMap(
					SCIMConstants.USER_ENDPOINT, "/o/scim/Users"));

			_registerLiferayUserSchemaExtension();

			_userManager = new UserManagerImpl(
				_classNameLocalService, _companyLocalService,
				_configurationAdmin, _expandoColumnLocalService,
				_expandoTableLocalService, _expandoValueLocalService,
				_userLocalService);
		}
	}

	private Response _buildResponse(SCIMResponse scimResponse) {
		Response.ResponseBuilder responseBuilder = Response.status(
			scimResponse.getResponseStatus());

		Map<String, String> map = scimResponse.getHeaderParamMap();

		if (MapUtil.isNotEmpty(map)) {
			for (Map.Entry<String, String> entry : map.entrySet()) {
				responseBuilder.header(entry.getKey(), entry.getValue());
			}
		}

		if (scimResponse.getResponseMessage() != null) {
			responseBuilder.entity(scimResponse.getResponseMessage());
		}

		return responseBuilder.build();
	}

	private void _registerLiferayUserSchemaExtension() throws Exception {
		SCIMUserSchemaExtensionBuilder scimUserSchemaExtensionBuilder =
			SCIMUserSchemaExtensionBuilder.getInstance();

		String json = JSONUtil.putAll(
			JSONUtil.put(
				"attributeName", "birthday"
			).put(
				"attributeURI",
				ScimUserUtil.LIFERAY_USER_SCHEMA_EXTENSION_URI + ":birthday"
			).put(
				"canonicalValues", _jsonFactory.createJSONArray()
			).put(
				"caseExact", "false"
			).put(
				"dataType", "string"
			).put(
				"description", "User's birthday"
			).put(
				"multiValued", "false"
			).put(
				"mutability", "readWrite"
			).put(
				"referenceTypes", _jsonFactory.createJSONArray()
			).put(
				"required", "false"
			).put(
				"returned", "default"
			).put(
				"subAttributes", "null"
			).put(
				"uniqueness", "none"
			),
			JSONUtil.put(
				"attributeName", "male"
			).put(
				"attributeURI",
				ScimUserUtil.LIFERAY_USER_SCHEMA_EXTENSION_URI + ":male"
			).put(
				"canonicalValues", _jsonFactory.createJSONArray()
			).put(
				"caseExact", "false"
			).put(
				"dataType", "boolean"
			).put(
				"description", "User's gender"
			).put(
				"multiValued", "false"
			).put(
				"mutability", "readWrite"
			).put(
				"referenceTypes", _jsonFactory.createJSONArray()
			).put(
				"required", "false"
			).put(
				"returned", "default"
			).put(
				"subAttributes", "null"
			).put(
				"uniqueness", "none"
			),
			JSONUtil.put(
				"attributeName", ScimUserUtil.LIFERAY_USER_SCHEMA_EXTENSION_URI
			).put(
				"attributeURI", ScimUserUtil.LIFERAY_USER_SCHEMA_EXTENSION_URI
			).put(
				"canonicalValues", _jsonFactory.createJSONArray()
			).put(
				"caseExact", "false"
			).put(
				"dataType", "complex"
			).put(
				"description", "Liferay's User Schema Extension"
			).put(
				"multiValued", "false"
			).put(
				"mutability", "readWrite"
			).put(
				"referenceTypes", JSONUtil.put("external")
			).put(
				"required", "false"
			).put(
				"returned", "default"
			).put(
				"subAttributes", "birthday male"
			).put(
				"uniqueness", "none"
			)
		).toString();

		File file = _file.createTempFile(json.getBytes());

		scimUserSchemaExtensionBuilder.buildUserSchemaExtension(file.getPath());
	}

	private static UserManager _userManager;
	private static final UserResourceManager _userResourceManager =
		new UserResourceManager();

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
	private JSONFactory _jsonFactory;

	@Reference
	private UserLocalService _userLocalService;

}