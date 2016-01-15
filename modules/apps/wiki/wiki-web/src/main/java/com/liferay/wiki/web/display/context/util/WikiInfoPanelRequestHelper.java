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

package com.liferay.wiki.web.display.context.util;

import com.liferay.portal.kernel.display.context.util.BaseRequestHelper;
import com.liferay.wiki.constants.WikiWebKeys;
import com.liferay.wiki.model.WikiNode;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 */
public class WikiInfoPanelRequestHelper extends BaseRequestHelper {

	public WikiInfoPanelRequestHelper(HttpServletRequest request) {
		super(request);
	}

	public List<WikiNode> getNodes() {
		if (_nodes != null) {
			return _nodes;
		}

		HttpServletRequest request = getRequest();

		_nodes = (List<WikiNode>)request.getAttribute(WikiWebKeys.WIKI_NODES);

		if (_nodes == null) {
			_nodes = Collections.emptyList();
		}

		return _nodes;
	}

	private List<WikiNode> _nodes;

}