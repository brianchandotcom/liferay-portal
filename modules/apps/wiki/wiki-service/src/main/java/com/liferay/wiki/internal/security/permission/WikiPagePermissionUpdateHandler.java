/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.wiki.internal.security.permission;

import com.liferay.portal.kernel.security.permission.PermissionUpdateHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiPageLocalService;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gergely Mathe
 */
@Component(
	property = "model.class.name=com.liferay.wiki.model.WikiPage",
	service = PermissionUpdateHandler.class
)
public class WikiPagePermissionUpdateHandler
	implements PermissionUpdateHandler {

	@Override
	public void updatedPermission(String primKey) {
		WikiPage wikiPage = _wikiPageLocalService.fetchLatestPage(
			GetterUtil.getLong(primKey), WorkflowConstants.STATUS_ANY, true);

		if (wikiPage == null) {
			return;
		}

		wikiPage.setModifiedDate(new Date());

		_wikiPageLocalService.updateWikiPage(wikiPage);
	}

	@Reference
	private WikiPageLocalService _wikiPageLocalService;

}