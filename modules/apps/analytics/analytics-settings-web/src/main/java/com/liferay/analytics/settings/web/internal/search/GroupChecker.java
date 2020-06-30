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

package com.liferay.analytics.settings.web.internal.search;

import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Objects;
import java.util.Set;

import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author André Miranda
 */
public class GroupChecker extends EmptyOnClickRowChecker {

	public GroupChecker(
		RenderResponse renderResponse, String channelId, Set<String> ids,
		String mvcRenderCommandName) {

		super(renderResponse);

		_channelId = channelId;
		_ids = ids;
		_mvcRenderCommandName = mvcRenderCommandName;
	}

	@Override
	public boolean isChecked(Object obj) {
		Group group = (Group)obj;

		if (StringUtil.equalsIgnoreCase(
				_mvcRenderCommandName, "/analytics/edit_synced_sites")) {

			return _ids.contains(String.valueOf(group.getGroupId()));
		}

		return Objects.equals(
			group.getTypeSettingsProperty("analyticsChannelId"), _channelId);
	}

	@Override
	protected String getRowCheckBox(
		HttpServletRequest httpServletRequest, boolean checked,
		boolean disabled, String name, String value, String checkBoxRowIds,
		String checkBoxAllRowIds, String checkBoxPostOnClick) {

		if (StringUtil.equalsIgnoreCase(
				_mvcRenderCommandName, "/analytics/edit_synced_sites")) {

			return super.getRowCheckBox(
				httpServletRequest, checked, disabled, name, value,
				checkBoxRowIds, checkBoxAllRowIds, checkBoxPostOnClick);
		}

		if (!checked && _ids.contains(value)) {
			disabled = true;
		}

		return super.getRowCheckBox(
			httpServletRequest, checked, disabled, name, value, checkBoxRowIds,
			checkBoxAllRowIds, checkBoxPostOnClick);
	}

	private final String _channelId;
	private final Set<String> _ids;
	private final String _mvcRenderCommandName;

}