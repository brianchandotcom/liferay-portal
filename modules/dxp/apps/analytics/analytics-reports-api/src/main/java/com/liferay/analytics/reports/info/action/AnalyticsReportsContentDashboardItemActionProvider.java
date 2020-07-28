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

package com.liferay.analytics.reports.info.action;

import com.liferay.content.dashboard.item.action.ContentDashboardItemAction;
import com.liferay.content.dashboard.item.action.ContentDashboardItemActionException;
import com.liferay.portal.kernel.exception.PortalException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author David Arques
 */
public interface AnalyticsReportsContentDashboardItemActionProvider {

	public ContentDashboardItemAction getContentDashboardItemAction(
			String className, long classPK,
			HttpServletRequest httpServletRequest)
		throws ContentDashboardItemActionException;

	public boolean isShowContentDashboardItemAction(
			String className, long classPK,
			HttpServletRequest httpServletRequest)
		throws PortalException;

}