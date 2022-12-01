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

import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.wiki.web.internal.display.context.helper.WikiNodeInfoPanelRequestHelper;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Roberto Díaz
 */
public class DefaultWikiNodeInfoPanelDisplayContext {

	public DefaultWikiNodeInfoPanelDisplayContext(
		HttpServletRequest httpServletRequest) {

		_wikiNodeInfoPanelRequestHelper = new WikiNodeInfoPanelRequestHelper(
			httpServletRequest);
	}

	public WikiNode getFirstNode() {
		List<WikiNode> nodes = _wikiNodeInfoPanelRequestHelper.getNodes();

		if (nodes.isEmpty()) {
			return null;
		}

		return nodes.get(0);
	}

	public int getNodesCount() {
		return WikiNodeLocalServiceUtil.getNodesCount(
			_wikiNodeInfoPanelRequestHelper.getScopeGroupId());
	}

	public int getSelectedNodesCount() {
		List<?> items = _getSelectedNodes();

		return items.size();
	}

	public boolean isMultipleNodeSelection() {
		List<?> items = _getSelectedNodes();

		if (items.size() > 1) {
			return true;
		}

		return false;
	}

	public boolean isSingleNodeSelection() {
		List<WikiNode> nodes = _wikiNodeInfoPanelRequestHelper.getNodes();

		if (nodes.size() == 1) {
			return true;
		}

		return false;
	}

	private List<?> _getSelectedNodes() {
		return _wikiNodeInfoPanelRequestHelper.getNodes();
	}

	private final WikiNodeInfoPanelRequestHelper
		_wikiNodeInfoPanelRequestHelper;

}