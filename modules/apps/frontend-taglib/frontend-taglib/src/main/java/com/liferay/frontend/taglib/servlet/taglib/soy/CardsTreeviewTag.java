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

package com.liferay.frontend.taglib.servlet.taglib.soy;

import com.liferay.frontend.taglib.soy.servlet.taglib.ComponentRendererTag;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

/**
 * @author Chema Balsas
 */
public class CardsTreeviewTag extends ComponentRendererTag {

	@Override
	public int doStartTag() {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		putValue("pathThemeImages", themeDisplay.getPathThemeImages());

		setTemplateNamespace("liferay.frontend.CardsTreeview.render");

		return super.doStartTag();
	}

	@Override
	public String getModule() {
		return "frontend-taglib/cards_treeview/CardsTreeview.es";
	}

	public void setNodes(Object nodes) {
		putValue("nodes", nodes);
	}

	public void setViewType(String viewType) {
		putValue("viewType", viewType);
	}

}