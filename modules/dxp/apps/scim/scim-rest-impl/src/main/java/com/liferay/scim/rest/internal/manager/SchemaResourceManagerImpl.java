/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.scim.rest.internal.manager;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.URLUtil;

import java.io.IOException;

import java.net.URL;

import java.util.Map;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.wso2.charon3.core.exceptions.AbstractCharonException;
import org.wso2.charon3.core.exceptions.ConflictException;
import org.wso2.charon3.core.exceptions.InternalErrorException;
import org.wso2.charon3.core.exceptions.NotFoundException;
import org.wso2.charon3.core.extensions.UserManager;
import org.wso2.charon3.core.protocol.ResponseCodeConstants;
import org.wso2.charon3.core.protocol.SCIMResponse;
import org.wso2.charon3.core.protocol.endpoints.AbstractResourceManager;
import org.wso2.charon3.core.protocol.endpoints.SchemaResourceManager;
import org.wso2.charon3.core.schema.SCIMConstants;

/**
 * @author Alvaro Saugar
 */
public class SchemaResourceManagerImpl extends SchemaResourceManager {

	@Override
	public SCIMResponse get(
		String id, UserManager userManager, String attributes,
		String excludeAttributes) {

		try {
			if (userManager != null) {
				userManager.getCoreSchema();

				return new SCIMResponse(
					ResponseCodeConstants.CODE_OK, _getSchemas(attributes),
					_getResponseHeaders());
			}

			String error = "Provided user manager handler is null.";

			throw new InternalErrorException(error);
		}
		catch (AbstractCharonException abstractCharonException) {
			return AbstractResourceManager.encodeSCIMException(
				abstractCharonException);
		}
		catch (Exception exception) {
			if (exception instanceof ConflictException) {
				return AbstractResourceManager.encodeSCIMException(
					(ConflictException)exception);
			}

			throw exception;
		}
	}

	private JSONObject _createSchema(String attribute, String jsonFile)
		throws IOException, JSONException {

		Bundle bundle = FrameworkUtil.getBundle(
			SchemaResourceManagerImpl.class);

		URL userSchemaURL = bundle.getResource(
			"META-INF/schemas/json/" + jsonFile);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			URLUtil.toString(userSchemaURL));

		JSONObject metaJSONObject = (JSONObject)jsonObject.get("meta");

		String locationString = (String)metaJSONObject.get("location");

		metaJSONObject.put("location", attribute + locationString);

		JSONArray schemasJSONArray = JSONUtil.put(
			"urn:ietf:params:scim:schemas:core:2.0:Schema");

		jsonObject.put("schemas", schemasJSONArray);

		return jsonObject;
	}

	private Map<String, String> _getResponseHeaders() throws NotFoundException {
		return HashMapBuilder.put(
			SCIMConstants.CONTENT_TYPE_HEADER, SCIMConstants.APPLICATION_JSON
		).put(
			SCIMConstants.LOCATION_HEADER,
			getResourceEndpointURL(SCIMConstants.SCHEMAS_ENDPOINT)
		).build();
	}

	private String _getSchemas(String attribute)
		throws AbstractCharonException {

		try {
			JSONArray schemasJSONArray = JSONFactoryUtil.createJSONArray(
				"[\"urn:ietf:params:scim:api:messages:2.0:ListResponse\"]");

			JSONObject rootJSONObject = JSONFactoryUtil.createJSONObject(
				HashMapBuilder.put(
					"itemsPerPage", 3
				).put(
					"startIndex", 1
				).put(
					"totalResults", 3
				).build());

			rootJSONObject.put("schemas", schemasJSONArray);

			JSONArray resourcesJSONArray = JSONUtil.putAll(
				_createSchema(attribute, "user-schema.json"),
				_createSchema(attribute, "user-extension-schema.json"),
				_createSchema(attribute, "group-schema.json"));

			rootJSONObject.put("Resources", resourcesJSONArray);

			return rootJSONObject.toString();
		}
		catch (IOException | JSONException exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			String error = "Error getting the schemas.";

			throw new InternalErrorException(error);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SchemaResourceManagerImpl.class);

}