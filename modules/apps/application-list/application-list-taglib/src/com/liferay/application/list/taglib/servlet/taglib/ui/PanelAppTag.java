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

package com.liferay.application.list.taglib.servlet.taglib.ui;

import com.liferay.application.list.PanelApp;
import com.liferay.application.list.PanelCategory;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 */
public class PanelAppTag extends BasePanelTag {

	public void setPanelApp(PanelApp panelApp) {
		_panelApp = panelApp;
	}

	public void setPanelCategory(PanelCategory panelCategory) {
		_panelCategory = panelCategory;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_panelApp = null;
		_panelCategory = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"application-list-ui:panel-app:panelApp", _panelApp);
		request.setAttribute(
			"application-list-ui:panel-app:panelCategory", _panelCategory);
	}

	private static final String _PAGE = "/taglib/ui/panel_app/page.jsp";

	private PanelApp _panelApp;
	private PanelCategory _panelCategory;

}