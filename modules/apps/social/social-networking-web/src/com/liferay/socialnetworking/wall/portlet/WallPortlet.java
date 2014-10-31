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

package com.liferay.socialnetworking.wall.portlet;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.permission.UserPermissionUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.social.model.SocialRelationConstants;
import com.liferay.portlet.social.service.SocialRelationLocalServiceUtil;
import com.liferay.socialnetworking.model.WallEntry;
import com.liferay.socialnetworking.service.WallEntryLocalServiceUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = {
		"com.liferay.portlet.icon=/icons/wall.png",
		"com.liferay.portlet.friendly-url-routes=" +
			"com/liferay/socialnetworking/wall/portlet/" +
				"wall-friendly-url-routes.xml",
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

	public static final String JAVAX_PORTLET_NAME =
		"com_liferay_socialnetworking_wall_portlet_WallPortlet";

	public void addWallEntry(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (!themeDisplay.isSignedIn()) {
			return;
		}

		Group group = GroupLocalServiceUtil.getGroup(
			themeDisplay.getScopeGroupId());

		User user = null;

		if (group.isUser()) {
			user = UserLocalServiceUtil.getUserById(group.getClassPK());
		}
		else {
			return;
		}

		if ((themeDisplay.getUserId() != user.getUserId()) &&
			!SocialRelationLocalServiceUtil.hasRelation(
				themeDisplay.getUserId(), user.getUserId(),
				SocialRelationConstants.TYPE_BI_FRIEND)) {

			return;
		}

		String comments = ParamUtil.getString(actionRequest, "comments");

		WallEntryLocalServiceUtil.addWallEntry(
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
			wallEntry = WallEntryLocalServiceUtil.getWallEntry(wallEntryId);
		}
		catch (Exception e) {
			return;
		}

		if (wallEntry.getGroupId() != themeDisplay.getScopeGroupId()) {
			return;
		}

		Group group = GroupLocalServiceUtil.getGroup(
			themeDisplay.getScopeGroupId());

		User user = null;

		if (group.isUser()) {
			user = UserLocalServiceUtil.getUserById(group.getClassPK());
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
			WallEntryLocalServiceUtil.deleteWallEntry(wallEntryId);
		}
		catch (Exception e) {
		}
	}

}