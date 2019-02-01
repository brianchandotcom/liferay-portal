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

package com.liferay.layout.admin.web.internal.servlet.taglib.clay;

import com.liferay.frontend.taglib.clay.servlet.taglib.soy.VerticalCard;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.util.HtmlUtil;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

/**
 * @author Eudaldo Alonso
 */
public class SelectGlobalTemplatesVerticalCard implements VerticalCard {

	public SelectGlobalTemplatesVerticalCard(
		LayoutPageTemplateEntry layoutPageTemplateEntry,
		RenderResponse renderResponse) {

		_layoutPageTemplateEntry = layoutPageTemplateEntry;
		_renderResponse = renderResponse;
	}

	@Override
	public Map<String, String> getData() {
		Map<String, String> data = new HashMap<>();

		try {
			PortletURL addLayoutURL = _renderResponse.createRenderURL();

			addLayoutURL.setParameter(
				"mvcRenderCommandName", "/layout/add_layout");
			addLayoutURL.setParameter(
				"layoutPageTemplateEntryId",
				String.valueOf(
					_layoutPageTemplateEntry.getLayoutPageTemplateEntryId()));
			addLayoutURL.setParameter(
				"layoutPrototypeId",
				String.valueOf(
					_layoutPageTemplateEntry.getLayoutPrototypeId()));

			addLayoutURL.setWindowState(LiferayWindowState.POP_UP);

			data.put("add-layout-url", addLayoutURL.toString());
		}
		catch (Exception e) {
		}

		return data;
	}

	@Override
	public String getElementClasses() {
		return "add-layout-action-option";
	}

	@Override
	public String getIcon() {
		return "page-template";
	}

	@Override
	public String getTitle() {
		return HtmlUtil.escape(_layoutPageTemplateEntry.getName());
	}

	@Override
	public boolean isSelectable() {
		return false;
	}

	private final LayoutPageTemplateEntry _layoutPageTemplateEntry;
	private final RenderResponse _renderResponse;

}