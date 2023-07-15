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

package com.liferay.user.groups.admin.item.selector.web.internal.frontend.taglib.clay.servlet.taglib;

import com.liferay.frontend.taglib.clay.servlet.taglib.BaseVerticalCard;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import javax.portlet.RenderRequest;

/**
 * @author Eudaldo Alonso
 */
public class UserGroupVerticalCard extends BaseVerticalCard {

	public UserGroupVerticalCard(
		RenderRequest renderRequest, RowChecker rowChecker, boolean selectable,
		UserGroup userGroup) {

		super(userGroup, renderRequest, rowChecker);

		_selectable = selectable;
		_userGroup = userGroup;
	}

	@Override
	public String getIcon() {
		return "users";
	}

	@Override
	public String getStickerIcon() {
		return StringPool.BLANK;
	}

	@Override
	public String getStickerImageSrc() {
		return StringPool.BLANK;
	}

	@Override
	public String getSubtitle() {
		int usersCount = UserLocalServiceUtil.searchCount(
			_userGroup.getCompanyId(), StringPool.BLANK,
			WorkflowConstants.STATUS_ANY,
			LinkedHashMapBuilder.<String, Object>put(
				"usersUserGroups", _userGroup.getUserGroupId()
			).build());

		return LanguageUtil.format(
			themeDisplay.getLocale(), "x-users", usersCount);
	}

	@Override
	public String getTitle() {
		return _userGroup.getName();
	}

	@Override
	public boolean isSelectable() {
		return _selectable;
	}

	private final boolean _selectable;
	private final UserGroup _userGroup;

}