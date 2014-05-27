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

package com.liferay.portlet.wiki.context;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.wiki.WikiPortletInstanceSettings;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.service.WikiNodeServiceUtil;
import com.liferay.portlet.wiki.util.WikiUtil;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Iván Zaera
 */
public class WikiConfigurationDisplayContext {

	public WikiConfigurationDisplayContext(
		HttpServletRequest request,
		WikiPortletInstanceSettings wikiPortletInstanceSettings) {

		_wikiPortletInstanceSettings = wikiPortletInstanceSettings;

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		_scopeGroupId = themeDisplay.getScopeGroupId();
	}

	public List<String> getAllNodeNames()
		throws PortalException, SystemException {

		if (_allNodeNames == null) {
			_populateNodes();
		}

		return _allNodeNames;
	}

	public List<WikiNode> getAllNodes()
		throws PortalException, SystemException {

		if (_allNodes == null) {
			_populateNodes();
		}

		return _allNodes;
	}

	public String[] getVisibleNodeNames()
		throws PortalException, SystemException {

		if (_visibleNodeNames == null) {
			_populateNodes();
		}

		return _visibleNodeNames;
	}

	private void _populateNodes() throws PortalException, SystemException {
		_allNodes = WikiNodeServiceUtil.getNodes(_scopeGroupId);
		_allNodeNames = WikiUtil.getNodeNames(_allNodes);

		_visibleNodeNames = _wikiPortletInstanceSettings.getVisibleNodes();

		if (ArrayUtil.isNotEmpty(_visibleNodeNames)) {
			_allNodes = WikiUtil.orderNodes(_allNodes, _visibleNodeNames);
		}
		else {
			_visibleNodeNames = _allNodeNames.toArray(
				new String[_allNodeNames.size()]);
		}
	}

	private List<String> _allNodeNames;
	private List<WikiNode> _allNodes;
	private long _scopeGroupId;
	private String[] _visibleNodeNames;
	private WikiPortletInstanceSettings _wikiPortletInstanceSettings;

}