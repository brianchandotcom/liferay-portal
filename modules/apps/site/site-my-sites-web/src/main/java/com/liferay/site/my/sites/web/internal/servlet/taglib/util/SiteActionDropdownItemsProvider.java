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

package com.liferay.site.my.sites.web.internal.servlet.taglib.util;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.MembershipRequestConstants;
import com.liferay.portal.kernel.security.membershippolicy.SiteMembershipPolicyUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutServiceUtil;
import com.liferay.portal.kernel.service.MembershipRequestLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class SiteActionDropdownItemsProvider {

	public SiteActionDropdownItemsProvider(
		Group group, RenderRequest renderRequest, RenderResponse renderResponse,
		String tabs1) {

		_group = group;
		_renderResponse = renderResponse;
		_tabs1 = tabs1;

		_request = PortalUtil.getHttpServletRequest(renderRequest);

		_themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<DropdownItem> getActionDropdownItems() throws Exception {
		return new DropdownItemList() {
			{
				if (Objects.equals(_tabs1, "my-sites")) {
					if (LayoutServiceUtil.getLayoutsCount(
							_group.getGroupId(), false) > 0) {

						add(_getViewSitePublicPagesActionConsumer());
					}

					if (LayoutServiceUtil.getLayoutsCount(
							_group.getGroupId(), true) > 0) {

						add(_getViewSitePrivatePagesActionConsumer());
					}

					if (_isShowLeaveAction()) {
						add(_getLeaveSiteActionConsumer());
					}
				}
				else if (_group.isManualMembership()) {
					if (!GroupLocalServiceUtil.hasUserGroup(
							_themeDisplay.getUserId(), _group.getGroupId()) &&
						SiteMembershipPolicyUtil.isMembershipAllowed(
							_themeDisplay.getUserId(), _group.getGroupId())) {

						if (_group.getType() == GroupConstants.TYPE_SITE_OPEN) {
							add(_getJoinSiteActionConsumer());
						}

						if (_isShowMembershipRequestAction()) {
							add(_getSiteMembershipRequestActionConsumer());
						}

						if (_isShowMembershipRequestedAction()) {
							add(_getSiteMembershipRequestedActionConsumer());
						}
					}
					else if (_isShowLeaveAction()) {
						add(_getLeaveSiteActionConsumer());
					}
				}
			}
		};
	}

	private Consumer<DropdownItem> _getJoinSiteActionConsumer() {
		PortletURL joinSiteURL = _renderResponse.createActionURL();

		joinSiteURL.setParameter(ActionRequest.ACTION_NAME, "updateGroupUsers");

		joinSiteURL.setParameter("redirect", _themeDisplay.getURLCurrent());
		joinSiteURL.setParameter(
			"groupId", String.valueOf(_group.getGroupId()));
		joinSiteURL.setParameter(
			"addUserIds", String.valueOf(_themeDisplay.getUserId()));

		return dropdownItem -> {
			dropdownItem.putData("action", "joinSite");
			dropdownItem.putData("joinSiteURL", joinSiteURL.toString());
			dropdownItem.setLabel(LanguageUtil.get(_request, "join"));
		};
	}

	private Consumer<DropdownItem> _getLeaveSiteActionConsumer() {
		PortletURL leaveSiteURL = _renderResponse.createActionURL();

		leaveSiteURL.setParameter(
			ActionRequest.ACTION_NAME, "updateGroupUsers");

		leaveSiteURL.setParameter("redirect", _themeDisplay.getURLCurrent());
		leaveSiteURL.setParameter(
			"groupId", String.valueOf(_group.getGroupId()));
		leaveSiteURL.setParameter(
			"removeUserIds", String.valueOf(_themeDisplay.getUserId()));

		return dropdownItem -> {
			dropdownItem.putData("action", "leaveSite");
			dropdownItem.putData("leaveSiteURL", leaveSiteURL.toString());
			dropdownItem.setLabel(LanguageUtil.get(_request, "leave"));
		};
	}

	private Consumer<DropdownItem> _getSiteMembershipRequestActionConsumer() {
		return dropdownItem -> {
			dropdownItem.setHref(
				_renderResponse.createRenderURL(), "mvcPath",
				"/post_membership_request.jsp", "groupId", _group.getGroupId());
			dropdownItem.setLabel(
				LanguageUtil.get(_request, "request-membership"));
		};
	}

	private Consumer<DropdownItem> _getSiteMembershipRequestedActionConsumer() {
		return dropdownItem -> {
			dropdownItem.putData("action", "membershipRequested");
			dropdownItem.setLabel(
				LanguageUtil.get(_request, "membership-requested"));
		};
	}

	private Consumer<DropdownItem> _getViewSitePrivatePagesActionConsumer() {
		return dropdownItem -> {
			dropdownItem.setHref(_group.getDisplayURL(_themeDisplay, true));
			dropdownItem.setTarget("_blank");
			dropdownItem.setLabel(
				LanguageUtil.get(_request, "go-to-private-pages"));
		};
	}

	private Consumer<DropdownItem> _getViewSitePublicPagesActionConsumer() {
		return dropdownItem -> {
			dropdownItem.setHref(_group.getDisplayURL(_themeDisplay, false));
			dropdownItem.setTarget("_blank");
			dropdownItem.setLabel(
				LanguageUtil.get(_request, "go-to-public-pages"));
		};
	}

	private boolean _isShowLeaveAction() throws PortalException {
		if ((_group.getType() != GroupConstants.TYPE_SITE_OPEN) &&
			(_group.getType() != GroupConstants.TYPE_SITE_RESTRICTED)) {

			return false;
		}

		if (!GroupLocalServiceUtil.hasUserGroup(
				_themeDisplay.getUserId(), _group.getGroupId(), false)) {

			return false;
		}

		if (SiteMembershipPolicyUtil.isMembershipRequired(
				_themeDisplay.getUserId(), _group.getGroupId())) {

			return false;
		}

		return true;
	}

	private boolean _isShowMembershipRequestAction() throws PortalException {
		if (_group.getType() != GroupConstants.TYPE_SITE_RESTRICTED) {
			return false;
		}

		if (MembershipRequestLocalServiceUtil.hasMembershipRequest(
				_themeDisplay.getUserId(), _group.getGroupId(),
				MembershipRequestConstants.STATUS_PENDING)) {

			return false;
		}

		if (!SiteMembershipPolicyUtil.isMembershipAllowed(
				_themeDisplay.getUserId(), _group.getGroupId())) {

			return false;
		}

		return true;
	}

	private boolean _isShowMembershipRequestedAction() {
		if (MembershipRequestLocalServiceUtil.hasMembershipRequest(
				_themeDisplay.getUserId(), _group.getGroupId(),
				MembershipRequestConstants.STATUS_PENDING)) {

			return true;
		}

		return false;
	}

	private final Group _group;
	private final RenderResponse _renderResponse;
	private final HttpServletRequest _request;
	private final String _tabs1;
	private final ThemeDisplay _themeDisplay;

}