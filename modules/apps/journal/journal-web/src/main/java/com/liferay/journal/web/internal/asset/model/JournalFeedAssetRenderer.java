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

package com.liferay.journal.web.internal.asset.model;

import com.liferay.asset.kernel.model.BaseAssetRenderer;
import com.liferay.journal.model.JournalFeed;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Georgel Pop
 */
public class JournalFeedAssetRenderer extends BaseAssetRenderer<JournalFeed> {

	public JournalFeedAssetRenderer(JournalFeed feed) {
		_feed = feed;
	}

	@Override
	public JournalFeed getAssetObject() {
		return _feed;
	}

	@Override
	public String getClassName() {
		return JournalFeed.class.getName();
	}

	@Override
	public long getClassPK() {
		return _feed.getId();
	}

	@Override
	public long getGroupId() {
		return _feed.getGroupId();
	}

	@Override
	public String getSummary(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		return _feed.getDescription();
	}

	@Override
	public String getTitle(Locale locale) {
		return _feed.getName();
	}

	@Override
	public long getUserId() {
		return _feed.getUserId();
	}

	@Override
	public String getUserName() {
		return _feed.getUserName();
	}

	@Override
	public String getUuid() {
		return _feed.getUuid();
	}

	@Override
	public boolean include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String template)
		throws Exception {

		return false;
	}

	private final JournalFeed _feed;

}