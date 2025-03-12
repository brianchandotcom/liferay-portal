/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.scim.rest.internal.resource.v1_0;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.URLUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.scim.rest.internal.util.ScimUtil;
import com.liferay.scim.rest.resource.v1_0.SchemaResource;

import java.util.Map;

import javax.ws.rs.core.Response;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

import org.wso2.charon3.core.exceptions.AbstractCharonException;
import org.wso2.charon3.core.exceptions.ConflictException;
import org.wso2.charon3.core.exceptions.InternalErrorException;
import org.wso2.charon3.core.exceptions.NotFoundException;
import org.wso2.charon3.core.protocol.ResponseCodeConstants;
import org.wso2.charon3.core.protocol.SCIMResponse;
import org.wso2.charon3.core.protocol.endpoints.AbstractResourceManager;
import org.wso2.charon3.core.schema.SCIMConstants;

/**
 * @author Alvaro Saugar
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/schema.properties",
	scope = ServiceScope.PROTOTYPE, service = SchemaResource.class
)
public class SchemaResourceImpl extends BaseSchemaResourceImpl {

	@Override
	public Object getV2SchemaById(String id) throws Exception {
		return _buildResponse(_getSCIMResponse(id));
	}

	@Override
	public Object getV2Schemas() throws Exception {
		return getV2SchemaById(null);
	}

	private Response _buildResponse(SCIMResponse scimResponse) {
		Response.ResponseBuilder responseBuilder = Response.status(
			scimResponse.getResponseStatus());

		if (scimResponse.getResponseMessage() != null) {
			responseBuilder.entity(scimResponse.getResponseMessage());
		}

		Map<String, String> map = scimResponse.getHeaderParamMap();

		if (MapUtil.isNotEmpty(map)) {
			for (Map.Entry<String, String> entry : map.entrySet()) {
				responseBuilder.header(entry.getKey(), entry.getValue());
			}
		}

		return responseBuilder.build();
	}

	private Map<String, String> _getHeaders() throws NotFoundException {
		return HashMapBuilder.put(
			SCIMConstants.CONTENT_TYPE_HEADER, SCIMConstants.APPLICATION_JSON
		).put(
			SCIMConstants.LOCATION_HEADER,
			AbstractResourceManager.getResourceEndpointURL(
				SCIMConstants.SCHEMAS_ENDPOINT)
		).build();
	}

	private String _getSchemaJSON(String id) throws AbstractCharonException {
		if (_schemasMap.containsKey(id)) {
			JSONObject schemaJSONObject = _read(_schemasMap.get(id));

			return schemaJSONObject.toString();
		}

		throw new NotFoundException("No schema found with schema ID " + id);
	}

	private String _getSchemas() throws AbstractCharonException {
		return JSONUtil.put(
			"itemsPerPage", 3
		).put(
			"Resources",
			() -> {
				JSONArray resourcesJSONArray = _jsonFactory.createJSONArray();

				for (Map.Entry<String, String> entry : _schemasMap.entrySet()) {
					resourcesJSONArray.put(_read(entry.getValue()));
				}

				return resourcesJSONArray;
			}
		).put(
			"schemas",
			JSONUtil.put("urn:ietf:params:scim:api:messages:2.0:ListResponse")
		).put(
			"startIndex", 1
		).put(
			"totalResults", _schemasMap.size()
		).toString();
	}

	private SCIMResponse _getSCIMResponse(String id) {
		try {
			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			ScimUtil.getScimClientOAuth2ApplicationConfiguration(
				serviceContext.getCompanyId(), _configurationAdmin);

			if (Validator.isNull(id)) {
				return new SCIMResponse(
					ResponseCodeConstants.CODE_OK, _getSchemas(),
					_getHeaders());
			}

			String schemaJSON = _getSchemaJSON(id);

			if (Validator.isNull(schemaJSON)) {
				throw new NotFoundException(
					"No schema found with schema ID " + id);
			}

			return new SCIMResponse(
				ResponseCodeConstants.CODE_OK, schemaJSON, _getHeaders());
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

	private JSONObject _read(String fileName) throws InternalErrorException {
		try {
			Bundle bundle = FrameworkUtil.getBundle(SchemaResourceImpl.class);

			JSONObject schemaJSONObject = _jsonFactory.createJSONObject(
				URLUtil.toString(
					bundle.getResource("META-INF/schemas/" + fileName)));

			JSONObject metaJSONObject = schemaJSONObject.getJSONObject("meta");

			String resourceEndpointURL =
				AbstractResourceManager.getResourceEndpointURL(
					SCIMConstants.SCHEMAS_ENDPOINT);

			metaJSONObject.put(
				"location",
				resourceEndpointURL + metaJSONObject.getString("location"));

			schemaJSONObject.put(
				"schemas",
				JSONUtil.put("urn:ietf:params:scim:schemas:core:2.0:Schema"));

			return schemaJSONObject;
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			throw new InternalErrorException(
				"Error reading schema file " + fileName);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SchemaResourceImpl.class);

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	@Reference
	private JSONFactory _jsonFactory;

	private final Map<String, String> _schemasMap = Map.of(
		"urn:ietf:params:scim:schemas:core:2.0:Group", "group-schema.json",
		"urn:ietf:params:scim:schemas:core:2.0:User", "user-schema.json",
		"urn:ietf:params:scim:schemas:extension:liferay:2.0:User",
		"user-extension-schema.json");

}