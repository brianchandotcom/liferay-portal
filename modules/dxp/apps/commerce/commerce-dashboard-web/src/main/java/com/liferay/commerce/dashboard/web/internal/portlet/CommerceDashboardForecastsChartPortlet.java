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

package com.liferay.commerce.dashboard.web.internal.portlet;

import com.liferay.commerce.account.permission.CommerceAccountPermission;
import com.liferay.commerce.dashboard.web.internal.constants.CommerceDashboardPortletKeys;
import com.liferay.commerce.dashboard.web.internal.display.context.CommerceDashboardForecastDisplayContext;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-commerce-dashboard-forecasts-chart",
		"com.liferay.portlet.display-category=commerce",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"com.liferay.portlet.layout-cacheable=true",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"javax.portlet.display-name=Commerce Dashboard Forecasts Chart",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.config-jsp=/configuration/forecast.jsp",
		"javax.portlet.init-param.view-template=/forecast.jsp",
		"javax.portlet.name=" + CommerceDashboardPortletKeys.COMMERCE_DASHBOARD_FORECASTS_CHART,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user",
		"javax.portlet.version=3.0"
	},
	service = {CommerceDashboardForecastsChartPortlet.class, Portlet.class}
)
public class CommerceDashboardForecastsChartPortlet extends MVCPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		try {
			renderRequest.setAttribute(
				WebKeys.PORTLET_DISPLAY_CONTEXT,
				new CommerceDashboardForecastDisplayContext(
					_commerceAccountPermission,
					_portal.getHttpServletRequest(renderRequest)));
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}

		super.render(renderRequest, renderResponse);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceDashboardForecastsChartPortlet.class);

	@Reference
	private CommerceAccountPermission _commerceAccountPermission;

	@Reference
	private Portal _portal;

}