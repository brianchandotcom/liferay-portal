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

package com.liferay.staging.taglib.servlet.taglib;

import aQute.bnd.annotation.ProviderType;

import com.liferay.exportimport.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.staging.taglib.internal.servlet.ServletContextUtil;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Levente Hudák
 */
@ProviderType
public class DefineObjectsTag extends IncludeTag {

	@Override
	public int doStartTag() {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = ParamUtil.getLong(request, "groupId");

		Group group = GroupLocalServiceUtil.fetchGroup(groupId);

		if (group == null) {
			group = (Group)request.getAttribute(WebKeys.GROUP);
		}

		if (group == null) {
			group = themeDisplay.getScopeGroup();
		}

		if (group == null) {
			return SKIP_BODY;
		}

		pageContext.setAttribute("group", group);
		pageContext.setAttribute("groupId", group.getGroupId());
		pageContext.setAttribute("liveGroup", null);
		pageContext.setAttribute("liveGroupId", 0L);

		Layout layout = themeDisplay.getLayout();

		String privateLayoutString = request.getParameter("privateLayout");

		if (Validator.isNull(privateLayoutString)) {
			privateLayoutString = GetterUtil.getString(
				request.getAttribute(WebKeys.PRIVATE_LAYOUT), null);
		}

		boolean privateLayout = GetterUtil.getBoolean(
			privateLayoutString, layout.isPrivateLayout());

		pageContext.setAttribute("privateLayout", privateLayout);

		pageContext.setAttribute("stagingGroup", null);
		pageContext.setAttribute("stagingGroupId", 0L);

		if (!group.isStaged() && !group.isStagedRemotely() &&
			!group.hasLocalOrRemoteStagingGroup()) {

			return SKIP_BODY;
		}

		Group liveGroup = StagingUtil.getLiveGroup(group.getGroupId());

		pageContext.setAttribute("liveGroup", liveGroup);
		pageContext.setAttribute("liveGroupId", liveGroup.getGroupId());

		Group stagingGroup = null;

		if (!group.hasRemoteStagingGroup() || group.isStagedRemotely()) {
			stagingGroup = StagingUtil.getStagingGroup(group.getGroupId());
		}

		if (stagingGroup != null) {
			pageContext.setAttribute("stagingGroup", stagingGroup);
			pageContext.setAttribute(
				"stagingGroupId", stagingGroup.getGroupId());
		}

		if (Validator.isNotNull(_portletId)) {
			boolean stagedPortlet = liveGroup.isStagedPortlet(_portletId);

			if (group.isStagedRemotely()) {
				stagedPortlet = stagingGroup.isStagedPortlet(_portletId);
			}

			if (stagedPortlet) {
				pageContext.setAttribute("group", stagingGroup);
				pageContext.setAttribute("groupId", stagingGroup.getGroupId());
				pageContext.setAttribute("scopeGroup", stagingGroup);
				pageContext.setAttribute(
					"scopeGroupId", stagingGroup.getGroupId());
			}
		}

		return SKIP_BODY;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	@Override
	public void setPortletId(String portletId) {
		_portletId = portletId;
	}

	@Override
	protected void cleanUp() {
		_portletId = null;
	}

	private String _portletId;

}