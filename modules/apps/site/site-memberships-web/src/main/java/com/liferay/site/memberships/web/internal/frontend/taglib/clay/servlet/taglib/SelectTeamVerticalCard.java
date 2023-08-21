/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.memberships.web.internal.frontend.taglib.clay.servlet.taglib;

import com.liferay.frontend.taglib.clay.servlet.taglib.VerticalCard;
import com.liferay.portal.kernel.model.Team;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Map;

/**
 * @author Christopher Kian
 */
public class SelectTeamVerticalCard implements VerticalCard {

	public SelectTeamVerticalCard(Team team) {
		_team = team;
	}

	@Override
	public String getCssClass() {
		return "selector-button";
	}

	@Override
	public Map<String, String> getDynamicAttributes() {
		return HashMapBuilder.put(
			"data-id", String.valueOf(_team.getTeamId())
		).build();
	}

	@Override
	public String getIcon() {
		return "users";
	}

	@Override
	public String getTitle() {
		return _team.getName();
	}

	@Override
	public boolean isSelectable() {
		return false;
	}

	private final Team _team;

}