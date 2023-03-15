/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.faro.web.internal.application;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import com.liferay.osb.faro.web.internal.context.GroupInfoContextProvider;
import com.liferay.osb.faro.web.internal.controller.api.RecommendationController;
import com.liferay.osb.faro.web.internal.controller.api.ReportController;
import com.liferay.osb.faro.web.internal.util.JSONUtil;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

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

		singletons.add(_groupInfoContextProvider);
		singletons.add(new JacksonJsonProvider(JSONUtil.getObjectMapper()));
		singletons.add(_recommendationController);
		singletons.add(_reportController);

		return singletons;
	}

	public static class OAuth2ScopeAliases {

		public static final String RECOMMENDATIONS_EVERYTHING =
			"Liferay.Analytics.Cloud.REST.recommendations.everything";

		public static final String REPORTS_EVERYTHING =
			"Liferay.Analytics.Cloud.REST.reports.everything";

	}

	@Reference
	private GroupInfoContextProvider _groupInfoContextProvider;

	@Reference
	private RecommendationController _recommendationController;

	@Reference
	private ReportController _reportController;

}