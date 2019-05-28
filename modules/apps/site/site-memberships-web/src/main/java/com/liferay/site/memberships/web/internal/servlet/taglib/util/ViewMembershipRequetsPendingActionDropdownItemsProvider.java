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

package com.liferay.site.memberships.web.internal.servlet.taglib.util;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.MembershipRequest;
import com.liferay.portal.kernel.model.MembershipRequestConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class ViewMembershipRequetsPendingActionDropdownItemsProvider {

	public ViewMembershipRequetsPendingActionDropdownItemsProvider(
		MembershipRequest membershipRequest, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_membershipRequest = membershipRequest;
		_renderResponse = renderResponse;

		_httpServletRequest = PortalUtil.getHttpServletRequest(renderRequest);

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<DropdownItem> getActionDropdownItems() throws Exception {
		return new DropdownItemList() {
			{
				if ((_membershipRequest.getStatusId() ==
						MembershipRequestConstants.STATUS_PENDING) &&
					GroupPermissionUtil.contains(
						_themeDisplay.getPermissionChecker(),
						_themeDisplay.getSiteGroupIdOrLiveGroupId(),
						ActionKeys.ASSIGN_MEMBERS)) {

					add(_getReplyRequestActionUnsafeConsumer());
				}
			}
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getReplyRequestActionUnsafeConsumer() {

		return dropdownItem -> {
			dropdownItem.setHref(
				_renderResponse.createRenderURL(), "mvcPath",
				"/reply_membership_request.jsp", "p_u_i_d",
				_membershipRequest.getUserId(), "groupId",
				_themeDisplay.getSiteGroupIdOrLiveGroupId(),
				"membershipRequestId",
				_membershipRequest.getMembershipRequestId());
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "reply"));
		};
	}

	private final HttpServletRequest _httpServletRequest;
	private final MembershipRequest _membershipRequest;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;

}