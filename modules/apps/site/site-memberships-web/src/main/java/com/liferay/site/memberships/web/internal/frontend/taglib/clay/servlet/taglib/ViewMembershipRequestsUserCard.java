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

package com.liferay.site.memberships.web.internal.frontend.taglib.clay.servlet.taglib;

import com.liferay.frontend.taglib.clay.servlet.taglib.BaseUserCard;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.MembershipRequest;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Date;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class ViewMembershipRequestsUserCard extends BaseUserCard {

	public ViewMembershipRequestsUserCard(
		MembershipRequest membershipRequest, User user,
		RenderRequest renderRequest, RenderResponse renderResponse) {

		super(user, renderRequest, null);

		_membershipRequest = membershipRequest;
		_renderResponse = renderResponse;

		_httpServletRequest = PortalUtil.getHttpServletRequest(renderRequest);
	}

	@Override
	public String getHref() {
		return PortletURLBuilder.createRenderURL(
			_renderResponse
		).setMVCPath(
			"/preview_membership_request.jsp"
		).setRedirect(
			themeDisplay.getURLCurrent()
		).setParameter(
			"membershipRequestId", _membershipRequest.getMembershipRequestId()
		).buildString();
	}

	@Override
	public String getSubtitle() {
		String replier = _getReplier();

		Date replyDate = _membershipRequest.getReplyDate();

		String replyDateDescription = LanguageUtil.getTimeDescription(
			_httpServletRequest,
			System.currentTimeMillis() - replyDate.getTime(), true);

		if (Validator.isNull(replier)) {
			return LanguageUtil.format(
				_httpServletRequest, "replied-x-ago", replyDateDescription);
		}

		return LanguageUtil.format(
			_httpServletRequest, "replied-by-x-x-ago",
			new String[] {replier, replyDateDescription});
	}

	private String _getReplier() {
		User membershipRequestReplierUser = UserLocalServiceUtil.fetchUserById(
			_membershipRequest.getReplierUserId());

		if (membershipRequestReplierUser == null) {
			return StringPool.BLANK;
		}

		if (!membershipRequestReplierUser.isDefaultUser()) {
			return HtmlUtil.escape(membershipRequestReplierUser.getFullName());
		}

		try {
			Company membershipRequestReplierCompany =
				CompanyLocalServiceUtil.getCompanyById(
					membershipRequestReplierUser.getCompanyId());

			return HtmlUtil.escape(membershipRequestReplierCompany.getName());
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return StringPool.BLANK;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ViewMembershipRequestsUserCard.class);

	private final HttpServletRequest _httpServletRequest;
	private final MembershipRequest _membershipRequest;
	private final RenderResponse _renderResponse;

}