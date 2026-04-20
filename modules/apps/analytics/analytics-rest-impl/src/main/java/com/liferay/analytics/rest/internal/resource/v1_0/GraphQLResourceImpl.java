/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.rest.internal.resource.v1_0;

import com.liferay.analytics.rest.internal.client.AnalyticsCloudClient;
import com.liferay.analytics.rest.resource.v1_0.GraphQLResource;
import com.liferay.analytics.settings.rest.manager.AnalyticsSettingsManager;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Http;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Ivica Cardic
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/graph-ql.properties",
	scope = ServiceScope.PROTOTYPE, service = GraphQLResource.class
)
public class GraphQLResourceImpl extends BaseGraphQLResourceImpl {

	@Override
	public Response postGraphQL(String body) throws Exception {
		try {
			AnalyticsCloudClient.Response response =
				_analyticsCloudClient.executeGraphQl(
					_analyticsSettingsManager.getAnalyticsConfiguration(
						contextCompany.getCompanyId()),
					body);

			return Response.status(
				response.getStatusCode()
			).entity(
				response.getBody()
			).type(
				MediaType.APPLICATION_JSON
			).build();
		}
		catch (Exception exception) {
			_log.error(
				"Unable to proxy GraphQL request to Analytics Cloud",
				exception);

			JSONObject errorJSONObject = _jsonFactory.createJSONObject();

			errorJSONObject.put(
				"error", "Unable to reach Analytics Cloud"
			).put(
				"message", exception.getMessage()
			);

			return Response.status(
				Response.Status.BAD_GATEWAY
			).entity(
				errorJSONObject.toString()
			).type(
				MediaType.APPLICATION_JSON
			).build();
		}
	}

	@Activate
	protected void activate() {
		_analyticsCloudClient = new AnalyticsCloudClient(_http);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GraphQLResourceImpl.class);

	private AnalyticsCloudClient _analyticsCloudClient;

	@Reference
	private AnalyticsSettingsManager _analyticsSettingsManager;

	@Reference
	private Http _http;

	@Reference
	private JSONFactory _jsonFactory;

}