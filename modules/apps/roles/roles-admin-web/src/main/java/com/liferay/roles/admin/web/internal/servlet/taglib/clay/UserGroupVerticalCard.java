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

package com.liferay.roles.admin.web.internal.servlet.taglib.clay;

import com.liferay.frontend.taglib.clay.servlet.taglib.BaseVerticalCard;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.UserGroup;

import javax.portlet.RenderRequest;

/**
 * @author Eudaldo Alonso
 */
public class UserGroupVerticalCard extends BaseVerticalCard {

	public UserGroupVerticalCard(
		UserGroup userGroup, RenderRequest renderRequest,
		RowChecker rowChecker) {

		super(userGroup, renderRequest, rowChecker);

		_userGroup = userGroup;
	}

	@Override
	public String getIcon() {
		return "users";
	}

	@Override
	public String getInputValue() {
		try {
			return String.valueOf(_userGroup.getGroupId());
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}
		}

		return StringPool.BLANK;
	}

	@Override
	public String getSubtitle() {
		return _userGroup.getDescription();
	}

	@Override
	public String getTitle() {
		return _userGroup.getName();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UserGroupVerticalCard.class);

	private final UserGroup _userGroup;

}