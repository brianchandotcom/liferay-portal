/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.user.taglib.servlet.taglib;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.taglib.util.PortalIncludeUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author Brian Wing Shun Chan
 */
public class UserDisplayTag extends TagSupport {

	@Override
	public int doEndTag() throws JspException {
		try {
			PortalIncludeUtil.include(pageContext, getEndPage());

			HttpServletRequest httpServletRequest =
				(HttpServletRequest)pageContext.getRequest();

			httpServletRequest.removeAttribute("liferay-user:user-display:url");

			return EVAL_PAGE;
		}
		catch (Exception exception) {
			throw new JspException(exception);
		}
	}

	@Override
	public int doStartTag() throws JspException {
		try {
			HttpServletRequest httpServletRequest =
				(HttpServletRequest)pageContext.getRequest();

			httpServletRequest.setAttribute(
				"liferay-user:user-display:userId", String.valueOf(_userId));
			httpServletRequest.setAttribute(
				"liferay-user:user-display:userName", _userName);

			User user = UserLocalServiceUtil.fetchUserById(_userId);

			if (user != null) {
				if (user.isGuestUser()) {
					user = null;
				}

				httpServletRequest.setAttribute(
					"liferay-user:user-display:user", user);

				pageContext.setAttribute("userDisplay", user);
			}
			else {
				httpServletRequest.removeAttribute(
					"liferay-user:user-display:user");

				pageContext.removeAttribute("userDisplay");
			}

			httpServletRequest.setAttribute(
				"liferay-user:user-display:url", _url);

			PortalIncludeUtil.include(pageContext, getStartPage());

			if (user != null) {
				return EVAL_BODY_INCLUDE;
			}

			return SKIP_BODY;
		}
		catch (Exception exception) {
			throw new JspException(exception);
		}
	}

	public void setUrl(String url) {
		_url = url;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	protected String getEndPage() {
		return "/user_display/end.jsp";
	}

	protected String getStartPage() {
		return "/user_display/start.jsp";
	}

	private String _url;
	private long _userId;
	private String _userName;

}