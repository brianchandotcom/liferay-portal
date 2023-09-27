/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.teams.item.selector.web.internal;

import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Team;

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
		return _team.getName();
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