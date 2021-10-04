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

package com.liferay.portal.search.elasticsearch.monitoring.web.internal.servlet.filter;

import com.liferay.portal.search.elasticsearch.monitoring.web.internal.constants.MonitoringWebConstants;
import com.liferay.portal.servlet.filters.authverifier.AuthVerifierFilter;

import javax.servlet.Filter;

import org.osgi.service.component.annotations.Component;

/**
 * @author André de Oliveira
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"filter.init.auth.verifier.PortalSessionAuthVerifier.urls.includes=/" + MonitoringWebConstants.SERVLET_PATH + "/*",
		"osgi.http.whiteboard.filter.name=com.liferay.portal.search.elasticsearch.monitoring.web.internal.servlet.filter.MonitoringProxyAuthVerifierFilter",
		"osgi.http.whiteboard.filter.pattern=/" + MonitoringWebConstants.SERVLET_PATH + "/*"
	},
	service = Filter.class
)
public class MonitoringProxyAuthVerifierFilter extends AuthVerifierFilter {
}