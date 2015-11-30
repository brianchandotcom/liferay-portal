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

package com.liferay.wiki.service.permission;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.permission.PermissionUpdateHandler;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiPageLocalService;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gergely Mathe
 */
@Component(
	property = {"model.class.name=com.liferay.wiki.model.WikiPage"},
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

	@Reference(unbind = "-")
	protected void setWikiPageLocalService(
		WikiPageLocalService wikiPageLocalService) {

		_wikiPageLocalService = wikiPageLocalService;
	}

	private volatile WikiPageLocalService _wikiPageLocalService;

}