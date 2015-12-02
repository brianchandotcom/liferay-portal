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

package com.liferay.journal.web.portlet.configuration.icon;

import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.permission.JournalArticlePermission;
import com.liferay.journal.web.portlet.action.ActionUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.taglib.security.PermissionsURLTag;

import javax.portlet.PortletRequest;

/**
 * @author Eudaldo Alonso
 */
public class PermissionsPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	public PermissionsPortletConfigurationIcon(PortletRequest portletRequest) {
		super(portletRequest);
	}

	@Override
	public String getMessage() {
		return "permissions";
	}

	@Override
	public String getURL() {
		String url = StringPool.BLANK;

		try {
			JournalArticle article = getArticle();

			url = PermissionsURLTag.doTag(
				StringPool.BLANK, JournalArticle.class.getName(),
				HtmlUtil.escape(article.getTitle(themeDisplay.getLocale())),
				String.valueOf(article.getGroupId()),
				String.valueOf(article.getResourcePrimKey()),
				LiferayWindowState.POP_UP.toString(), null,
				themeDisplay.getRequest());
		}
		catch (Exception e) {
		}

		return url;
	}

	@Override
	public boolean isShow() {
		try {
			JournalArticle article = getArticle();

			if ((article == null) || article.isNew()) {
				return false;
			}

			if (JournalArticlePermission.contains(
					themeDisplay.getPermissionChecker(), article,
					ActionKeys.PERMISSIONS)) {

				return true;
			}
		}
		catch (Exception e) {
		}

		return false;
	}

	@Override
	public boolean isToolTip() {
		return false;
	}

	@Override
	public boolean isUseDialog() {
		return true;
	}

	protected JournalArticle getArticle() throws Exception {
		return ActionUtil.getArticle(portletRequest);
	}

}