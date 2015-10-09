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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.service.WikiNodeServiceUtil;
import com.liferay.wiki.util.WikiUtil;
import com.liferay.wiki.web.configuration.WikiPortletInstanceOverriddenConfiguration;
import com.liferay.wiki.web.display.context.util.WikiRequestHelper;

import java.util.List;

/**
 * @author Iván Zaera
 */
public class WikiPortletInstanceSettingsHelper {

	public WikiPortletInstanceSettingsHelper(
		WikiRequestHelper wikiRequestHelper) {

		_wikiRequestHelper = wikiRequestHelper;

		_wikiPortletInstanceOverridenConfiguration =
			_wikiRequestHelper.getWikiPortletInstanceOverridenConfiguration();
	}

	public List<String> getAllNodeNames() throws PortalException {
		if (_allNodeNames == null) {
			_populateNodes();
		}

		return _allNodeNames;
	}

	public List<WikiNode> getAllNodes() throws PortalException {
		if (_allNodes == null) {
			_populateNodes();
		}

		return _allNodes;
	}

	public List<WikiNode> getAllPermittedNodes() throws PortalException {
		if (_allPermittedNodes == null) {
			_allPermittedNodes = WikiUtil.getNodes(
				getAllNodes(),
				_wikiPortletInstanceOverridenConfiguration.hiddenNodes(),
				_wikiRequestHelper.getPermissionChecker());
		}

		return _allPermittedNodes;
	}

	public String getDisplayStyle() {
		if (_displayStyle != null) {
			return _displayStyle;
		}

		_displayStyle =
			_wikiPortletInstanceOverridenConfiguration.displayStyle();

		return _displayStyle;
	}

	public long getDisplayStyleGroupId() {
		if (_displayStyleGroupId != 0) {
			return _displayStyleGroupId;
		}

		_displayStyleGroupId =
			_wikiPortletInstanceOverridenConfiguration.displayStyleGroupId();

		if (_displayStyleGroupId <= 0) {
			ThemeDisplay themeDisplay = _wikiRequestHelper.getThemeDisplay();

			_displayStyleGroupId = themeDisplay.getScopeGroupId();
		}

		return _displayStyleGroupId;
	}

	public String[] getHiddenNodes() {
		if (_hiddenNodes != null) {
			return _hiddenNodes;
		}

		_hiddenNodes = _wikiPortletInstanceOverridenConfiguration.hiddenNodes();

		return _hiddenNodes;
	}

	public String[] getVisibleNodeNames() throws PortalException {
		if (_visibleNodeNames == null) {
			_populateNodes();
		}

		return _visibleNodeNames;
	}

	public Boolean isEnableCommentRatings() {
		if (_enableCommentRatings != null) {
			return _enableCommentRatings;
		}

		_enableCommentRatings =
			_wikiPortletInstanceOverridenConfiguration.enableCommentRatings();

		return _enableCommentRatings;
	}

	public Boolean isEnableComments() {
		if (_enableComments != null) {
			return _enableComments;
		}

		_enableComments =
			_wikiPortletInstanceOverridenConfiguration.enableComments();

		return _enableComments;
	}

	public Boolean isEnablePageRatings() {
		if (_enablePageRatings != null) {
			return _enablePageRatings;
		}

		_enablePageRatings =
			_wikiPortletInstanceOverridenConfiguration.enablePageRatings();

		return _enablePageRatings;
	}

	public boolean isEnableRelatedAssets() {
		if (_enableRelatedAssets != null) {
			return _enableRelatedAssets;
		}

		_enableRelatedAssets =
			_wikiPortletInstanceOverridenConfiguration.enableRelatedAssets();

		return _enableRelatedAssets;
	}

	private void _populateNodes() throws PortalException {
		_allNodes = WikiNodeServiceUtil.getNodes(
			_wikiRequestHelper.getScopeGroupId());

		_allNodeNames = WikiUtil.getNodeNames(_allNodes);

		_visibleNodeNames =
			_wikiPortletInstanceOverridenConfiguration.visibleNodes();

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
	private List<WikiNode> _allPermittedNodes;
	private String _displayStyle;
	private long _displayStyleGroupId;
	private Boolean _enableCommentRatings;
	private Boolean _enableComments;
	private Boolean _enablePageRatings;
	private Boolean _enableRelatedAssets;
	private String[] _hiddenNodes;
	private String[] _visibleNodeNames;
	private final WikiPortletInstanceOverriddenConfiguration
		_wikiPortletInstanceOverridenConfiguration;
	private final WikiRequestHelper _wikiRequestHelper;

}