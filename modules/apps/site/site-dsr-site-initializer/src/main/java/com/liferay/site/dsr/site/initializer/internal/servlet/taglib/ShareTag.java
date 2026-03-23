/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.dsr.site.initializer.internal.servlet.taglib;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.object.service.ObjectEntryServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.dsr.site.initializer.internal.servlet.ServletContextUtil;
import com.liferay.taglib.util.IncludeTag;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.PageContext;

import java.util.Objects;

/**
 * @author Balazs Breier
 */
public class ShareTag extends IncludeTag {

	@Override
	public int doEndTag() throws JspException {
		if (_hasUpdateModelResourcePermission) {
			return super.doEndTag();
		}

		return EVAL_PAGE;
	}

	@Override
	public int doStartTag() throws JspException {
		if (_roomId == 0) {
			return SKIP_BODY;
		}

		HttpServletRequest httpServletRequest = getRequest();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		try {
			_hasUpdateModelResourcePermission =
				ObjectEntryServiceUtil.hasModelResourcePermission(
					themeDisplay.getUser(), _roomId, ActionKeys.UPDATE);

			if (!_hasUpdateModelResourcePermission) {
				return SKIP_BODY;
			}
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			return SKIP_BODY;
		}

		return super.doStartTag();
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
		_roomId = 0L;

		if (_groupId == 0) {
			return;
		}

		Group group = GroupLocalServiceUtil.fetchGroup(_groupId);

		if (group == null) {
			return;
		}

		ObjectDefinition objectDefinition =
			ObjectDefinitionLocalServiceUtil.
				fetchObjectDefinitionByExternalReferenceCode(
					"L_DSR_ROOM", group.getCompanyId());

		if ((objectDefinition == null) ||
			!Objects.equals(
				group.getClassName(), objectDefinition.getClassName())) {

			return;
		}

		_roomId = group.getClassPK();
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		setServletContext(ServletContextUtil.getServletContext());
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_groupId = 0;
		_hasUpdateModelResourcePermission = false;
		_roomId = 0;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute(
			"liferay-site-dsr-site-initializer:share:roomId", _roomId);
	}

	private static final String _PAGE = "/share/page.jsp";

	private static final Log _log = LogFactoryUtil.getLog(ShareTag.class);

	private long _groupId;
	private boolean _hasUpdateModelResourcePermission;
	private long _roomId;

}