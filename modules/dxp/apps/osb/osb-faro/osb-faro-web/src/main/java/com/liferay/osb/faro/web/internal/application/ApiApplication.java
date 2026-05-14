/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.application;

import com.fasterxml.jackson.jakarta.rs.json.JacksonJsonProvider;

import com.liferay.osb.faro.web.internal.context.GroupInfoContextProvider;
import com.liferay.osb.faro.web.internal.controller.api.DemandbaseAccountController;
import com.liferay.osb.faro.web.internal.controller.api.GraphQLController;
import com.liferay.osb.faro.web.internal.controller.api.HubSpotWebhookController;
import com.liferay.osb.faro.web.internal.controller.api.MarketoWebhookController;
import com.liferay.osb.faro.web.internal.controller.api.RecommendationController;
import com.liferay.osb.faro.web.internal.controller.api.ReportController;
import com.liferay.osb.faro.web.internal.util.JSONUtil;

import jakarta.ws.rs.core.Application;

import java.util.HashSet;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(
	property = {
		"oauth2.scope.checker.type=annotations",
		"osgi.http.whiteboard.filter.dispatcher=FORWARD",
		"osgi.http.whiteboard.filter.dispatcher=REQUEST",
		"osgi.jaxrs.application.base=/analytics-cloud-api",
		"osgi.jaxrs.name=Liferay.Analytics.Cloud.REST"
	},
	service = Application.class
)
public class ApiApplication extends Application {

	@Override
	public Set<Object> getSingletons() {
		Set<Object> singletons = new HashSet<>();

		singletons.add(_demandbaseAccountController);
		singletons.add(_graphQLController);
		singletons.add(_groupInfoContextProvider);
		singletons.add(_hubSpotWebhookController);
		singletons.add(new JacksonJsonProvider(JSONUtil.getObjectMapper()));
		singletons.add(_marketoWebhookController);
		singletons.add(_recommendationController);
		singletons.add(_reportController);

		return singletons;
	}

	public static class OAuth2ScopeAliases {

		public static final String ACCOUNTS_WRITE =
			"Liferay.Analytics.Cloud.REST.accounts.write";

		public static final String HUBSPOT_WRITE =
			"Liferay.Analytics.Cloud.REST.hubspot.write";

		public static final String MARKETO_WRITE =
			"Liferay.Analytics.Cloud.REST.marketo.write";

		public static final String RECOMMENDATIONS_EVERYTHING =
			"Liferay.Analytics.Cloud.REST.recommendations.everything";

		public static final String REPORTS_EVERYTHING =
			"Liferay.Analytics.Cloud.REST.reports.everything";

	}

	@Reference
	private DemandbaseAccountController _demandbaseAccountController;

	@Reference
	private GraphQLController _graphQLController;

	@Reference
	private GroupInfoContextProvider _groupInfoContextProvider;

	@Reference
	private HubSpotWebhookController _hubSpotWebhookController;

	@Reference
	private MarketoWebhookController _marketoWebhookController;

	@Reference
	private RecommendationController _recommendationController;

	@Reference
	private ReportController _reportController;

}