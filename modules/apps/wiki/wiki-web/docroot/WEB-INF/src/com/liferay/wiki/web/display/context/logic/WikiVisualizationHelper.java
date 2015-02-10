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

package com.liferay.wiki.web.display.context.logic;

import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.web.display.context.util.WikiRequestHelper;

import java.util.Collection;

/**
 * @author Adolfo Pérez
 */
public class WikiVisualizationHelper {

	public WikiVisualizationHelper(WikiRequestHelper wikiRequestHelper) {
		_wikiRequestHelper = wikiRequestHelper;
	}

	public boolean isNodeNameVisible() {
		String portletId = _wikiRequestHelper.getPortletId();

		return portletId.equals(WikiPortletKeys.WIKI_ADMIN);
	}

	public boolean isNodeNavigationVisible(Collection<WikiNode> nodes) {
		String portletId = _wikiRequestHelper.getPortletId();

		return (nodes.size() > 1) && portletId.equals(WikiPortletKeys.WIKI);
	}

	private final WikiRequestHelper _wikiRequestHelper;

}