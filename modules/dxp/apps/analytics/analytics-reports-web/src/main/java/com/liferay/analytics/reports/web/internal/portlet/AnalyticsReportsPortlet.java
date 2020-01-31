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

package com.liferay.analytics.reports.web.internal.portlet;

import com.liferay.analytics.reports.info.item.AnalyticsReportsInfoItem;
import com.liferay.analytics.reports.info.item.AnalyticsReportsInfoItemTracker;
import com.liferay.analytics.reports.web.internal.constants.AnalyticsReportsPortletKeys;
import com.liferay.analytics.reports.web.internal.constants.AnalyticsReportsWebKeys;
import com.liferay.analytics.reports.web.internal.display.context.AnalyticsReportsDisplayContext;
import com.liferay.asset.display.page.constants.AssetDisplayPageWebKeys;
import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayContributorTracker;
import com.liferay.info.display.contributor.InfoDisplayObjectProvider;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Arques
 * @author Sarai Díaz
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=false",
		"com.liferay.portlet.layout-cacheable=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.system=true",
		"com.liferay.portlet.use-default-template=false",
		"javax.portlet.display-name=Content Performance",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + AnalyticsReportsPortletKeys.ANALYTICS_REPORTS,
		"javax.portlet.resource-bundle=content.Language"
	},
	service = {AnalyticsReportsPortlet.class, Portlet.class}
)
public class AnalyticsReportsPortlet extends MVCPortlet {

	@Override
	protected void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			renderRequest);

		HttpServletRequest originalHttpServletRequest =
			_portal.getOriginalServletRequest(httpServletRequest);

		String layoutMode = ParamUtil.getString(
			originalHttpServletRequest, "p_l_mode", Constants.VIEW);

		if (layoutMode.equals(Constants.PREVIEW)) {
			return;
		}

		InfoDisplayObjectProvider infoDisplayObjectProvider =
			_getInfoDisplayObjectProvider(httpServletRequest);

		AnalyticsReportsInfoItem analyticsReportsInfoItem = null;
		Object analyticsReportsInfoItemObject = null;

		if (infoDisplayObjectProvider != null) {
			analyticsReportsInfoItem =
				_analyticsReportsInfoItemTracker.getAnalyticsReportsInfoItem(
					_portal.getClassName(
						infoDisplayObjectProvider.getClassNameId()));
			analyticsReportsInfoItemObject =
				infoDisplayObjectProvider.getDisplayObject();
		}

		if ((analyticsReportsInfoItem == null) ||
			(analyticsReportsInfoItemObject == null)) {

			analyticsReportsInfoItem =
				_analyticsReportsInfoItemTracker.getAnalyticsReportsInfoItem(
					Layout.class.getName());

			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			analyticsReportsInfoItemObject = themeDisplay.getLayout();
		}

		renderRequest.setAttribute(
			AnalyticsReportsWebKeys.ANALYTICS_REPORTS_DISPLAY_CONTEXT,
			new AnalyticsReportsDisplayContext(
				analyticsReportsInfoItem, analyticsReportsInfoItemObject,
				httpServletRequest, _portal, renderResponse));

		super.doDispatch(renderRequest, renderResponse);
	}

	private InfoDisplayObjectProvider _getInfoDisplayObjectProvider(
		HttpServletRequest httpServletRequest) {

		InfoDisplayObjectProvider infoDisplayObjectProvider =
			(InfoDisplayObjectProvider)httpServletRequest.getAttribute(
				AssetDisplayPageWebKeys.INFO_DISPLAY_OBJECT_PROVIDER);

		if (infoDisplayObjectProvider != null) {
			return infoDisplayObjectProvider;
		}

		InfoDisplayContributor infoDisplayContributor =
			_infoDisplayContributorTracker.getInfoDisplayContributor(
				_portal.getClassName(
					ParamUtil.getLong(httpServletRequest, "classNameId")));

		try {
			infoDisplayObjectProvider =
				infoDisplayContributor.getInfoDisplayObjectProvider(
					ParamUtil.getLong(httpServletRequest, "classPK"));
		}
		catch (Exception exception) {
			_log.error("Unable to get info display object provider", exception);
		}

		return infoDisplayObjectProvider;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AnalyticsReportsPortlet.class);

	@Reference
	private AnalyticsReportsInfoItemTracker _analyticsReportsInfoItemTracker;

	@Reference
	private InfoDisplayContributorTracker _infoDisplayContributorTracker;

	@Reference
	private Portal _portal;

}