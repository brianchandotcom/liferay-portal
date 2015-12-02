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
import com.liferay.journal.web.portlet.action.ActionUtil;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;

import javax.portlet.PortletRequest;

/**
 * @author Eudaldo Alonso
 */
public class PreviewPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	public PreviewPortletConfigurationIcon(PortletRequest portletRequest) {
		super(portletRequest);
	}

	@Override
	public String getMessage() {
		return "preview";
	}

	@Override
	public String getURL() {
		return "javascript:;";
	}

	@Override
	public boolean isShow() {
		try {
			JournalArticle article = ActionUtil.getArticle(portletRequest);

			if ((article != null) && !article.isNew()) {
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

}