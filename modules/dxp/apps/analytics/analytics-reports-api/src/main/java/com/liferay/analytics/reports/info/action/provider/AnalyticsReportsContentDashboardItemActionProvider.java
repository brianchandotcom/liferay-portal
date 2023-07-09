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

package com.liferay.analytics.reports.info.action.provider;

import com.liferay.content.dashboard.item.action.ContentDashboardItemAction;
import com.liferay.content.dashboard.item.action.exception.ContentDashboardItemActionException;
import com.liferay.info.item.InfoItemReference;
import com.liferay.portal.kernel.exception.PortalException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author David Arques
 */
public interface AnalyticsReportsContentDashboardItemActionProvider {

	public ContentDashboardItemAction getContentDashboardItemAction(
			HttpServletRequest httpServletRequest,
			InfoItemReference infoItemReference)
		throws ContentDashboardItemActionException;

	public boolean isShowContentDashboardItemAction(
			HttpServletRequest httpServletRequest,
			InfoItemReference infoItemReference)
		throws PortalException;

}