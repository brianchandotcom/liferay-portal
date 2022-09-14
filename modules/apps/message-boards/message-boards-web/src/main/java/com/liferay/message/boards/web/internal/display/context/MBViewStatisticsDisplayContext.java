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

package com.liferay.message.boards.web.internal.display.context;

import com.liferay.message.boards.constants.MBPortletKeys;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.web.internal.display.MBCategoryDisplay;
import com.liferay.message.boards.web.internal.util.MBUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Objects;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Adolfo Pérez
 */
public class MBViewStatisticsDisplayContext {

	public MBViewStatisticsDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public MBCategoryDisplay getMBCategoryDisplay() {
		return new MBCategoryDisplay(
			_themeDisplay.getScopeGroupId(), _getCategoryId());
	}

	public PortletURL getPortletURL() {
		return PortletURLBuilder.createRenderURL(
			_renderResponse
		).setMVCRenderCommandName(
			"/message_boards/view_statistics"
		).setParameter(
			"mbCategoryId", _getCategoryId()
		).buildPortletURL();
	}

	public boolean isMBAdmin() {
		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		if (Objects.equals(
				portletDisplay.getPortletName(),
				MBPortletKeys.MESSAGE_BOARDS_ADMIN)) {

			return true;
		}

		return false;
	}

	private long _getCategoryId() {
		if (_categoryId != null) {
			return _categoryId;
		}

		if (!isMBAdmin()) {
			MBCategory category = (MBCategory)_renderRequest.getAttribute(
				WebKeys.MESSAGE_BOARDS_CATEGORY);

			_categoryId = MBUtil.getCategoryId(_renderRequest, category);
		}
		else {
			_categoryId = GetterUtil.getLong(
				_renderRequest.getAttribute("view.jsp-categoryId"));
		}

		return _categoryId;
	}

	private Long _categoryId;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;

}