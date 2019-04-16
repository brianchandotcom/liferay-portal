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

package com.liferay.wiki.web.internal.asset;

import com.liferay.portal.kernel.trash.BaseTrashRenderer;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.trash.TrashHelper;
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.model.WikiNode;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Eudaldo Alonso
 */
public class WikiNodeTrashRenderer extends BaseTrashRenderer {

	public static final String TYPE = "wiki_node";

	public WikiNodeTrashRenderer(WikiNode node, TrashHelper trashHelper) {
		_node = node;
		_trashHelper = trashHelper;
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
	public String getIconCssClass() {
		return "folder";
	}

	@Override
	public String getPortletId() {
		return WikiPortletKeys.WIKI;
	}

	@Override
	public String getSummary(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		return HtmlUtil.stripHtml(_node.getDescription());
	}

	@Override
	public String getTitle(Locale locale) {
		if (!_node.isInTrash()) {
			return _node.getName();
		}

		if (_trashHelper == null) {
			return _node.getName();
		}

		return _trashHelper.getOriginalTitle(_node.getName());
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public boolean include(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, String template) {

		return false;
	}

	private final WikiNode _node;
	private final TrashHelper _trashHelper;

}