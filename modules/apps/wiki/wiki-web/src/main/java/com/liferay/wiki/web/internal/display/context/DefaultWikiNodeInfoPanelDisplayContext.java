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

package com.liferay.wiki.web.internal.display.context;

import com.liferay.wiki.display.context.WikiNodeInfoPanelDisplayContext;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.wiki.web.internal.display.context.util.WikiNodeInfoPanelRequestHelper;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Roberto Díaz
 */
public class DefaultWikiNodeInfoPanelDisplayContext
	implements WikiNodeInfoPanelDisplayContext {

	public DefaultWikiNodeInfoPanelDisplayContext(
		HttpServletRequest request, HttpServletResponse response) {

		_wikiNodeInfoPanelRequestHelper = new WikiNodeInfoPanelRequestHelper(
			request);
	}

	@Override
	public WikiNode getFirstNode() {
		List<WikiNode> nodes = _wikiNodeInfoPanelRequestHelper.getNodes();

		if (nodes.isEmpty()) {
			return null;
		}

		return nodes.get(0);
	}

	@Override
	public int getNodesCount() {
		return WikiNodeLocalServiceUtil.getNodesCount(
			_wikiNodeInfoPanelRequestHelper.getScopeGroupId());
	}

	@Override
	public int getSelectedNodesCount() {
		List<?> items = getSelectedNodes();

		return items.size();
	}

	@Override
	public UUID getUuid() {
		return _UUID;
	}

	@Override
	public boolean isMultipleNodeSelection() {
		List<?> items = getSelectedNodes();

		if (items.size() > 1) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isSingleNodeSelection() {
		List<WikiNode> nodes = _wikiNodeInfoPanelRequestHelper.getNodes();

		if (nodes.size() == 1) {
			return true;
		}

		return false;
	}

	protected List<?> getSelectedNodes() {
		return _wikiNodeInfoPanelRequestHelper.getNodes();
	}

	private static final UUID _UUID = UUID.fromString(
		"A91E44F1-686A-4FC5-8877-43C2532543D3");

	private final WikiNodeInfoPanelRequestHelper
		_wikiNodeInfoPanelRequestHelper;

}