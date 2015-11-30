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

package com.liferay.social.requests.web.portlet.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.GroupLocalService;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.service.permission.UserPermissionUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.social.model.SocialRequest;
import com.liferay.portlet.social.model.SocialRequestConstants;
import com.liferay.portlet.social.service.SocialRequestLocalService;
import com.liferay.social.requests.web.constants.SocialRequestsPortletKeys;
import com.liferay.social.requests.web.constants.SocialRequestsWebKeys;

import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + SocialRequestsPortletKeys.REQUESTS,
		"mvc.command.name=/", "mvc.command.name=/requests/view"
	},
	service = MVCRenderCommand.class
)
public class ViewMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			return doRender(renderRequest);
		}
		catch (PortalException pe) {
			throw new PortletException(pe);
		}
	}

	protected String doRender(RenderRequest renderRequest)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Group group = _groupLocalService.getGroup(
			themeDisplay.getScopeGroupId());

		User user = themeDisplay.getUser();

		if (group.isUser()) {
			user = _userLocalService.getUserById(group.getClassPK());
		}

		if (UserPermissionUtil.contains(
				themeDisplay.getPermissionChecker(), user.getUserId(),
				ActionKeys.UPDATE)) {

			List<SocialRequest> requests =
				_socialRequestLocalService.getReceiverUserRequests(
					user.getUserId(), SocialRequestConstants.STATUS_PENDING, 0,
					100);

			renderRequest.setAttribute(
				SocialRequestsWebKeys.SOCIAL_REQUESTS, requests);
		}

		return "/view.jsp";
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Reference(unbind = "-")
	protected void setSocialRequestLocalService(
		SocialRequestLocalService socialRequestLocalService) {

		_socialRequestLocalService = socialRequestLocalService;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private volatile GroupLocalService _groupLocalService;
	private volatile SocialRequestLocalService _socialRequestLocalService;
	private volatile UserLocalService _userLocalService;

}