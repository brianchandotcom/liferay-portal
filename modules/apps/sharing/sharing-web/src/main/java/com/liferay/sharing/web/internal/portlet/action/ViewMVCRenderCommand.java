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

package com.liferay.sharing.web.internal.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.sharing.constants.SharingPortletKeys;
import com.liferay.sharing.web.internal.constants.SharingWebKeys;
import com.liferay.sharing.web.internal.display.SharingEntryPermissionDisplayAction;
import com.liferay.sharing.web.internal.util.SharingUtil;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + SharingPortletKeys.SHARING,
		"mvc.command.name=/", "mvc.command.name=/sharing/share"
	},
	service = MVCRenderCommand.class
)
public class ViewMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		renderRequest.setAttribute(
			SharingWebKeys.SHARING_REACT_DATA,
			HashMapBuilder.<String, Object>put(
				"classNameId", ParamUtil.getLong(renderRequest, "classNameId")
			).put(
				"classPK", ParamUtil.getLong(renderRequest, "classPK")
			).put(
				"dialogId",
				ParamUtil.getString(
					renderRequest, SharingWebKeys.SHARING_DIALOG_ID)
			).put(
				"portletNamespace", renderResponse.getNamespace()
			).put(
				"shareActionURL", _getShareActionURL(renderResponse)
			).put(
				"sharingEntryPermissionDisplayActionId",
				SharingEntryPermissionDisplayAction.VIEW.getActionId()
			).put(
				"sharingEntryPermissionDisplays",
				_sharingUtil.getSharingEntryPermissionDisplays(
					themeDisplay.getPermissionChecker(),
					ParamUtil.getLong(renderRequest, "classNameId"),
					ParamUtil.getLong(renderRequest, "classPK"),
					themeDisplay.getScopeGroupId(), themeDisplay.getLocale())
			).put(
				"sharingUserAutocompleteURL",
				_getSharingUserAutocompleteURL(renderResponse)
			).put(
				"sharingVerifyEmailAddressURL",
				_getSharingVerifyEmailAddressURL(renderResponse)
			).build());

		return "/sharing/view.jsp";
	}

	private String _getShareActionURL(RenderResponse renderResponse) {
		PortletURL shareActionURL = renderResponse.createActionURL();

		shareActionURL.setParameter(
			ActionRequest.ACTION_NAME, "/sharing/share");

		return shareActionURL.toString();
	}

	private String _getSharingUserAutocompleteURL(
		RenderResponse renderResponse) {

		ResourceURL sharingUserAutocompleteURL =
			renderResponse.createResourceURL();

		sharingUserAutocompleteURL.setResourceID("/sharing/users");

		return sharingUserAutocompleteURL.toString();
	}

	private String _getSharingVerifyEmailAddressURL(
		RenderResponse renderResponse) {

		ResourceURL sharingVerifyEmailAddressURL =
			renderResponse.createResourceURL();

		sharingVerifyEmailAddressURL.setResourceID(
			"/sharing/verify_email_address");

		return sharingVerifyEmailAddressURL.toString();
	}

	@Reference
	private SharingUtil _sharingUtil;

}