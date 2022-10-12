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

package com.liferay.commerce.dashboard.web.internal.portlet.action;

import com.liferay.commerce.account.permission.CommerceAccountPermission;
import com.liferay.commerce.dashboard.web.internal.constants.CommerceDashboardPortletKeys;
import com.liferay.commerce.dashboard.web.internal.display.context.CommerceDashboardForecastDisplayContext;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + CommerceDashboardPortletKeys.COMMERCE_DASHBOARD_FORECASTS_CHART,
	service = ConfigurationAction.class
)
public class CommerceDashboardForecastConfigurationAction
	extends DefaultConfigurationAction {

	@Override
	public void include(
			PortletConfig portletConfig, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		try {
			CommerceDashboardForecastDisplayContext
				commerceDashboardForecastDisplayContext =
					new CommerceDashboardForecastDisplayContext(
						_commerceAccountPermission, httpServletRequest);

			httpServletRequest.setAttribute(
				WebKeys.PORTLET_DISPLAY_CONTEXT,
				commerceDashboardForecastDisplayContext);
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		super.include(portletConfig, httpServletRequest, httpServletResponse);
	}

	@Override
	public void processAction(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		String[] assetCategoryIds = ArrayUtil.toStringArray(
			serviceContext.getAssetCategoryIds());

		setPreference(
			actionRequest, "assetCategoryIds",
			StringUtil.merge(assetCategoryIds));

		super.processAction(portletConfig, actionRequest, actionResponse);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceDashboardForecastConfigurationAction.class);

	@Reference
	private CommerceAccountPermission _commerceAccountPermission;

}