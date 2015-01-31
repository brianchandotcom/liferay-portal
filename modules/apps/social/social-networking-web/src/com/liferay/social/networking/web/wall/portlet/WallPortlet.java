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

package com.liferay.social.networking.web.wall.portlet;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.GroupLocalService;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.service.permission.UserPermissionUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.social.model.SocialRelationConstants;
import com.liferay.portlet.social.service.SocialRelationLocalService;
import com.liferay.social.networking.model.WallEntry;
import com.liferay.social.networking.service.WallEntryLocalService;
import com.liferay.social.networking.service.configuration.configurator.SocialNetworkingServiceConfigurator;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.social",
		"com.liferay.portlet.icon=/icons/wall.png",
		"com.liferay.portlet.friendly-url-routes=com/liferay/social/networking/web/wall/portlet/route/wall-friendly-url-routes.xml",
		"com.liferay.portlet.css-class-wrapper=social-networking-portlet-wall",
		"javax.portlet.display-name=Wall",
		"javax.portlet.init-param.clear-request-parameters=true",
		"javax.portlet.init-param.view-template=/wall/view.jsp",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.supports.mime-type=text/html",
		"javax.portlet.resource-bundle=content.Language.properties",
		"javax.portlet.info.title=Wall", "javax.portlet.info.short-title=Wall",
		"javax.portlet.keywords=Wall",
		"javax.portlet.security-role-ref=administrator,guest,power-user,user"
	},
	service = Portlet.class
)
public class WallPortlet extends MVCPortlet {

	public void addWallEntry(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (!themeDisplay.isSignedIn()) {
			return;
		}

		Group group = _groupLocalService.getGroup(
			themeDisplay.getScopeGroupId());

		User user = null;

		if (group.isUser()) {
			user = _userLocalService.getUserById(group.getClassPK());
		}
		else {
			return;
		}

		if ((themeDisplay.getUserId() != user.getUserId()) &&
			!_socialRelationLocalService.hasRelation(
				themeDisplay.getUserId(), user.getUserId(),
				SocialRelationConstants.TYPE_BI_FRIEND)) {

			return;
		}

		String comments = ParamUtil.getString(actionRequest, "comments");

		_wallEntryLocalService.addWallEntry(
			themeDisplay.getScopeGroupId(), themeDisplay.getUserId(), comments,
			themeDisplay);
	}

	public void deleteWallEntry(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (!themeDisplay.isSignedIn()) {
			return;
		}

		long wallEntryId = ParamUtil.getLong(actionRequest, "wallEntryId");

		WallEntry wallEntry = null;

		try {
			wallEntry = _wallEntryLocalService.getWallEntry(wallEntryId);
		}
		catch (Exception e) {
			return;
		}

		if (wallEntry.getGroupId() != themeDisplay.getScopeGroupId()) {
			return;
		}

		Group group = _groupLocalService.getGroup(
			themeDisplay.getScopeGroupId());

		User user = null;

		if (group.isUser()) {
			user = _userLocalService.getUserById(group.getClassPK());
		}
		else {
			return;
		}

		if (!UserPermissionUtil.contains(
				themeDisplay.getPermissionChecker(), user.getUserId(),
				ActionKeys.UPDATE)) {

			return;
		}

		try {
			_wallEntryLocalService.deleteWallEntry(wallEntryId);
		}
		catch (Exception e) {
		}
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Reference(policy = ReferencePolicy.DYNAMIC)
	protected void setSocialNetworkingServiceConfigurator(
		SocialNetworkingServiceConfigurator
			socialNetworkingServiceConfigurator) {
	}

	@Reference(unbind = "-")
	protected void setSocialRelationLocalService(
		SocialRelationLocalService socialRelationLocalService) {

		_socialRelationLocalService = socialRelationLocalService;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	@Reference(unbind = "-")
	protected void setWallEntryLocalService(
		WallEntryLocalService wallEntryLocalService) {

		_wallEntryLocalService = wallEntryLocalService;
	}

	protected void unsetSocialNetworkingServiceConfigurator(
		SocialNetworkingServiceConfigurator
			socialNetworkingServiceConfigurator) {
	}

	private GroupLocalService _groupLocalService;
	private SocialRelationLocalService _socialRelationLocalService;
	private UserLocalService _userLocalService;
	private WallEntryLocalService _wallEntryLocalService;

}