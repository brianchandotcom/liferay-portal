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

package com.liferay.layout.admin.web.internal.frontend.taglib.clay.servlet.taglib;

import com.liferay.frontend.taglib.clay.servlet.taglib.BaseVerticalCard;
import com.liferay.layout.utility.page.model.LayoutUtilityPageEntry;
import com.liferay.portal.kernel.util.HtmlUtil;

import javax.portlet.RenderRequest;

/**
 * @author Eudaldo Alonso
 */
public class LayoutUtilityPageEntryVerticalCard extends BaseVerticalCard {

	public LayoutUtilityPageEntryVerticalCard(
		LayoutUtilityPageEntry layoutUtilityPageEntry,
		RenderRequest renderRequest) {

		super(null, renderRequest, null);

		_layoutUtilityPageEntry = layoutUtilityPageEntry;
	}

	@Override
	public String getIcon() {
		return "list";
	}

	@Override
	public String getTitle() {
		return HtmlUtil.escape(_layoutUtilityPageEntry.getName());
	}

	private final LayoutUtilityPageEntry _layoutUtilityPageEntry;

}