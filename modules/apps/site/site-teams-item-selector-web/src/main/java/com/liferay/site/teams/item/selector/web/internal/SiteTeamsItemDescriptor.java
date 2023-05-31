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

package com.liferay.site.teams.item.selector.web.internal;

import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Team;
import com.liferay.portal.kernel.util.HtmlUtil;

import java.util.Date;
import java.util.Locale;

/**
 * @author Eudaldo Alonso
 */
public class SiteTeamsItemDescriptor
	implements ItemSelectorViewDescriptor.ItemDescriptor {

	public SiteTeamsItemDescriptor(Team team) {
		_team = team;
	}

	@Override
	public String getIcon() {
		return null;
	}

	@Override
	public String getImageURL() {
		return null;
	}

	@Override
	public Date getModifiedDate() {
		return _team.getModifiedDate();
	}

	@Override
	public String getPayload() {
		return JSONUtil.put(
			"name", _team.getName()
		).put(
			"teamId", _team.getTeamId()
		).toString();
	}

	@Override
	public String getSubtitle(Locale locale) {
		return StringPool.BLANK;
	}

	@Override
	public String getTitle(Locale locale) {
		return HtmlUtil.escape(_team.getName());
	}

	@Override
	public long getUserId() {
		return _team.getUserId();
	}

	@Override
	public String getUserName() {
		return _team.getUserName();
	}

	private final Team _team;

}