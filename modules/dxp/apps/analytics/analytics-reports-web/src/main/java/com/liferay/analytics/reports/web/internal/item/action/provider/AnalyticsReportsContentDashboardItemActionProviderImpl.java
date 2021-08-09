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

package com.liferay.analytics.reports.web.internal.item.action.provider;

import com.liferay.analytics.reports.info.action.provider.AnalyticsReportsContentDashboardItemActionProvider;
import com.liferay.analytics.reports.info.item.AnalyticsReportsInfoItem;
import com.liferay.analytics.reports.info.item.AnalyticsReportsInfoItemTracker;
import com.liferay.analytics.reports.info.item.provider.AnalyticsReportsInfoItemObjectProvider;
import com.liferay.analytics.reports.web.internal.info.item.provider.AnalyticsReportsInfoItemObjectProviderTracker;
import com.liferay.analytics.reports.web.internal.item.action.AnalyticsReportsContentDashboardItemAction;
import com.liferay.analytics.reports.web.internal.util.AnalyticsReportsUtil;
import com.liferay.content.dashboard.item.action.ContentDashboardItemAction;
import com.liferay.content.dashboard.item.action.exception.ContentDashboardItemActionException;
import com.liferay.info.item.InfoItemReference;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.PortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Arques
 */
@Component(service = AnalyticsReportsContentDashboardItemActionProvider.class)
public class AnalyticsReportsContentDashboardItemActionProviderImpl
	implements AnalyticsReportsContentDashboardItemActionProvider {

	@Override
	public ContentDashboardItemAction getContentDashboardItemAction(
			HttpServletRequest httpServletRequest,
			InfoItemReference infoItemReference)
		throws ContentDashboardItemActionException {

		try {
			if (!isShowContentDashboardItemAction(
					httpServletRequest, infoItemReference)) {

				return null;
			}

			return new AnalyticsReportsContentDashboardItemAction(
				AnalyticsReportsUtil.getAnalyticsReportsPanelURL(
					infoItemReference, httpServletRequest, _portal,
					_portletURLFactory));
		}
		catch (PortalException | WindowStateException exception) {
			throw new ContentDashboardItemActionException(exception);
		}
	}

	@Override
	public ContentDashboardItemAction getContentDashboardItemAction(
			String className, long classPK,
			HttpServletRequest httpServletRequest)
		throws ContentDashboardItemActionException {

		try {
			return getContentDashboardItemAction(
				httpServletRequest, new InfoItemReference(className, classPK));
		}
		catch (PortalException portalException) {
			throw new ContentDashboardItemActionException(portalException);
		}
	}

	@Override
	public boolean isShowContentDashboardItemAction(
			HttpServletRequest httpServletRequest,
			InfoItemReference infoItemReference)
		throws PortalException {

		AnalyticsReportsInfoItemObjectProvider<Object>
			analyticsReportsInfoItemObjectProvider =
				(AnalyticsReportsInfoItemObjectProvider<Object>)
					_analyticsReportsInfoItemObjectProviderTracker.
						getAnalyticsReportsInfoItemObjectProvider(
							infoItemReference.getClassName());

		if (analyticsReportsInfoItemObjectProvider == null) {
			return false;
		}

		Object analyticsReportsInfoItemObject =
			analyticsReportsInfoItemObjectProvider.
				getAnalyticsReportsInfoItemObject(infoItemReference);

		if (analyticsReportsInfoItemObject == null) {
			return false;
		}

		AnalyticsReportsInfoItem<Object> analyticsReportsInfoItem =
			(AnalyticsReportsInfoItem<Object>)
				_analyticsReportsInfoItemTracker.getAnalyticsReportsInfoItem(
					infoItemReference.getClassName());

		if ((analyticsReportsInfoItem == null) ||
			!analyticsReportsInfoItem.isShow(analyticsReportsInfoItemObject)) {

			return false;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (AnalyticsReportsUtil.isShowAnalyticsReportsPanel(
				themeDisplay.getCompanyId(), httpServletRequest)) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isShowContentDashboardItemAction(
			String className, long classPK,
			HttpServletRequest httpServletRequest)
		throws PortalException {

		return isShowContentDashboardItemAction(
			httpServletRequest, new InfoItemReference(className, classPK));
	}

	@Reference
	private AnalyticsReportsInfoItemObjectProviderTracker
		_analyticsReportsInfoItemObjectProviderTracker;

	@Reference
	private AnalyticsReportsInfoItemTracker _analyticsReportsInfoItemTracker;

	@Reference
	private Portal _portal;

	@Reference
	private PortletURLFactory _portletURLFactory;

}