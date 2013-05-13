/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal;

import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.expando.model.BaseCustomAttributesDisplay;
import com.liferay.portlet.journal.model.JournalArticle;

/**
 * @author Jorge Ferrer
 */
public class JournalArticleCustomAttributesDisplay
	extends BaseCustomAttributesDisplay {

	@Override
	public String getClassName() {
		return JournalArticle.class.getName();
	}

	@Override
	public String getIconPath(ThemeDisplay themeDisplay) {
		return themeDisplay.getPathThemeImages() + "/common/history.png";
	}

}