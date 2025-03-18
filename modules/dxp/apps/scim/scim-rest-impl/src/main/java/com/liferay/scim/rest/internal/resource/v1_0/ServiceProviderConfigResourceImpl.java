/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.scim.rest.internal.resource.v1_0;

import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.URLUtil;
import com.liferay.scim.rest.internal.util.ScimUtil;
import com.liferay.scim.rest.resource.v1_0.ServiceProviderConfigResource;

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
 * @author Jorge García Jiménez
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/service-provider-config.properties",
	scope = ServiceScope.PROTOTYPE,
	service = ServiceProviderConfigResource.class
)
public class ServiceProviderConfigResourceImpl
	extends BaseServiceProviderConfigResourceImpl {

	@Override
	public Object getV2ServiceProviderConfig() throws Exception {
		return _buildResponse(_getSCIMResponse());
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

	private Map<String, String> _getResponseHeaders() throws NotFoundException {
		return HashMapBuilder.put(
			SCIMConstants.CONTENT_TYPE_HEADER, SCIMConstants.APPLICATION_JSON
		).put(
			SCIMConstants.LOCATION_HEADER,
			AbstractResourceManager.getResourceEndpointURL(
				SCIMConstants.SERVICE_PROVIDER_CONFIG_ENDPOINT)
		).build();
	}

	private SCIMResponse _getSCIMResponse() throws Exception {
		try {
			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			ScimUtil.getScimClientOAuth2ApplicationConfiguration(
				serviceContext.getCompanyId(), _configurationAdmin);

			return new SCIMResponse(
				ResponseCodeConstants.CODE_OK, _read(), _getResponseHeaders());
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

	private String _read() throws InternalErrorException {
		try {
			Bundle bundle = FrameworkUtil.getBundle(
				ServiceProviderConfigResourceImpl.class);

			JSONObject serviceProviderConfigJSONObject =
				_jsonFactory.createJSONObject(
					URLUtil.toString(
						bundle.getResource(
							"META-INF/service-provider-config" +
								"/service-provider-config.json")));

			JSONObject metaJSONObject =
				serviceProviderConfigJSONObject.getJSONObject("meta");

			metaJSONObject.put(
				"location",
				AbstractResourceManager.getResourceEndpointURL(
					SCIMConstants.SERVICE_PROVIDER_CONFIG_ENDPOINT));

			return serviceProviderConfigJSONObject.toString();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			throw new InternalErrorException(
				"Error reading service-provider-config.json file");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ServiceProviderConfigResourceImpl.class);

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	@Reference
	private JSONFactory _jsonFactory;

}