/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.teams.web.internal.frontend.taglib.clay.servlet.taglib;

import com.liferay.frontend.taglib.clay.servlet.taglib.BaseUserCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.site.teams.web.internal.servlet.taglib.util.UserActionDropdownItemsProvider;
import com.liferay.taglib.util.LexiconUtil;

import jakarta.portlet.RenderRequest;
import jakarta.portlet.RenderResponse;

import java.util.List;

/**
 * @author Eudaldo Alonso
 */
public class UserUserCard extends BaseUserCard {

	public UserUserCard(
		boolean inherited, RenderRequest renderRequest,
		RenderResponse renderResponse, RowChecker rowChecker, long teamId,
		User user) {

		super(user, renderRequest, rowChecker);

		_inherited = inherited;
		_renderResponse = renderResponse;
		_teamId = teamId;
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		UserActionDropdownItemsProvider userActionDropdownItemsProvider =
			new UserActionDropdownItemsProvider(
				user, _teamId, renderRequest, _renderResponse);

		return userActionDropdownItemsProvider.getActionDropdownItems();
	}

	@Override
	public String getSubtitle() {
		String subtitle = super.getSubtitle();

		if (!_inherited) {
			return subtitle;
		}

		return StringBundler.concat(
			subtitle, " (",
			LanguageUtil.get(
				PortalUtil.getHttpServletRequest(renderRequest), "inherited"),
			StringPool.CLOSE_PARENTHESIS);
	}

	@Override
	public String getUserColorClass() {
		return "sticker-user-icon " + LexiconUtil.getUserColorCssClass(user);
	}

	@Override
	public boolean isDisabled() {
		return rowChecker.isDisabled(user);
	}

	@Override
	public boolean isSelectable() {
		return !isDisabled();
	}

	private final boolean _inherited;
	private final RenderResponse _renderResponse;
	private final long _teamId;

}