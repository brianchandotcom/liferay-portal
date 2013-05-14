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

package com.liferay.portlet.wiki.asset;

import com.liferay.portal.kernel.trash.BaseTrashRenderer;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.trash.util.TrashUtil;
import com.liferay.portlet.wiki.model.WikiNode;

import java.util.Locale;

/**
 * @author Eudaldo Alonso
 */
public class WikiNodeTrashRenderer extends BaseTrashRenderer {

	public static final String TYPE = "wiki_node";

	public WikiNodeTrashRenderer(WikiNode node) {
		_node = node;
	}

	@Override
	public String getClassName() {
		return WikiNode.class.getName();
	}

	@Override
	public long getClassPK() {
		return _node.getPrimaryKey();
	}

	@Override
	public String getIconPath(ThemeDisplay themeDisplay) {
		return themeDisplay.getPathThemeImages() + "/common/all_pages.png";
	}

	@Override
	public String getPortletId() {
		return PortletKeys.WIKI;
	}

	@Override
	public String getSummary(Locale locale) {
		return HtmlUtil.stripHtml(_node.getDescription());
	}

	@Override
	public String getTitle(Locale locale) {
		if (!_node.isInTrash()) {
			return _node.getName();
		}

		return TrashUtil.getOriginalTitle(_node.getName());
	}

	@Override
	public String getType() {
		return TYPE;
	}

	private WikiNode _node;

}