/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.reports.journal.internal.content.dashboard.item.action.provider;

import com.liferay.analytics.reports.info.action.provider.AnalyticsReportsContentDashboardItemActionProvider;
import com.liferay.content.dashboard.item.action.ContentDashboardItemAction;
import com.liferay.content.dashboard.item.action.exception.ContentDashboardItemActionException;
import com.liferay.content.dashboard.item.action.provider.ContentDashboardItemActionProvider;
import com.liferay.info.item.InfoItemReference;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(service = ContentDashboardItemActionProvider.class)
public class ViewInPanelJournalArticleContentDashboardItemActionProvider
	implements ContentDashboardItemActionProvider<JournalArticle> {

	@Override
	public ContentDashboardItemAction getContentDashboardItemAction(
			JournalArticle journalArticle,
			HttpServletRequest httpServletRequest)
		throws ContentDashboardItemActionException {

		if (!isShow(journalArticle, httpServletRequest)) {
			return null;
		}

		return _analyticsReportsContentDashboardItemActionProvider.
			getContentDashboardItemAction(
				httpServletRequest,
				new InfoItemReference(
					JournalArticle.class.getName(),
					journalArticle.getResourcePrimKey()));
	}

	@Override
	public String getKey() {
		return "showMetrics";
	}

	@Override
	public ContentDashboardItemAction.Type getType() {
		return ContentDashboardItemAction.Type.VIEW_IN_PANEL;
	}

	@Override
	public boolean isShow(
		JournalArticle journalArticle, HttpServletRequest httpServletRequest) {

		if (!journalArticle.hasApprovedVersion()) {
			return false;
		}

		try {
			return _analyticsReportsContentDashboardItemActionProvider.
				isShowContentDashboardItemAction(
					httpServletRequest,
					new InfoItemReference(
						JournalArticle.class.getName(),
						journalArticle.getResourcePrimKey()));
		}
		catch (PortalException portalException) {
			_log.error(portalException);

			return false;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ViewInPanelJournalArticleContentDashboardItemActionProvider.class);

	@Reference
	private AnalyticsReportsContentDashboardItemActionProvider
		_analyticsReportsContentDashboardItemActionProvider;

}