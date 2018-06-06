/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.dashboard.web.internal.portlet;

import com.liferay.commerce.dashboard.web.internal.constants.CommerceDashboardPortletKeys;
import com.liferay.commerce.dashboard.web.internal.display.context.CommerceDashboardForecastsChartDisplayContext;
import com.liferay.commerce.forecast.service.CommerceForecastEntryLocalService;
import com.liferay.commerce.forecast.service.CommerceForecastValueLocalService;
import com.liferay.commerce.organization.service.CommerceOrganizationService;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.CompanyService;
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
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-commerce-dashboard-forecasts-chart",
		"com.liferay.portlet.display-category=commerce",
		"com.liferay.portlet.instanceable=true",
		"com.liferay.portlet.layout-cacheable=true",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"javax.portlet.display-name=Commerce Dashboard Forecasts Chart",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.view-template=/forecasts_chart/view.jsp",
		"javax.portlet.name=" + CommerceDashboardPortletKeys.COMMERCE_DASHBOARD_FORECASTS_CHART,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = {CommerceDashboardForecastsChartPortlet.class, Portlet.class}
)
public class CommerceDashboardForecastsChartPortlet extends MVCPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		try {
			CommerceDashboardForecastsChartDisplayContext
				commerceDashboardForecastsChartDisplayContext =
					new CommerceDashboardForecastsChartDisplayContext(
						_commerceForecastEntryLocalService,
						_commerceForecastValueLocalService,
						_commerceOrganizationService, _companyService,
						_configurationProvider, renderRequest);

			renderRequest.setAttribute(
				WebKeys.PORTLET_DISPLAY_CONTEXT,
				commerceDashboardForecastsChartDisplayContext);

			super.render(renderRequest, renderResponse);
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
	}

	@Reference
	private CommerceForecastEntryLocalService
		_commerceForecastEntryLocalService;

	@Reference
	private CommerceForecastValueLocalService
		_commerceForecastValueLocalService;

	@Reference
	private CommerceOrganizationService _commerceOrganizationService;

	@Reference
	private CompanyService _companyService;

	@Reference
	private ConfigurationProvider _configurationProvider;

}