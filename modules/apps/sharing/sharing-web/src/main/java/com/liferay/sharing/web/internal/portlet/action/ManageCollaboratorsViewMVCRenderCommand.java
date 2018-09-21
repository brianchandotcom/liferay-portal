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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.service.SharingEntryLocalService;
import com.liferay.sharing.web.internal.constants.SharingPortletKeys;
import com.liferay.sharing.web.internal.display.SharingEntryPermissionDisplay;
import com.liferay.sharing.web.internal.util.SharingUtil;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + SharingPortletKeys.MANAGE_COLLABORATORS,
		"mvc.command.name=/", "mvc.command.name=/sharing/manage_collaborators"
	},
	service = MVCRenderCommand.class
)
public class ManageCollaboratorsViewMVCRenderCommand
	implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		Template template = getTemplate(renderRequest);

		template.put(
			"collaborators", _getCollaboratorsJSONArray(renderRequest));
		template.put(
			"sharingEntryPermissionDisplaySelectOptions",
			_getSharingEntryPermissionDisplaySelectOptions(renderRequest));
		template.put("spritemap", _getSpritemap(renderRequest));
		template.put("uri", _getManageCollaboratorsActionURL(renderResponse));

		return "ManageCollaborators";
	}

	protected Template getTemplate(RenderRequest renderRequest) {
		return (Template)renderRequest.getAttribute(WebKeys.TEMPLATE);
	}

	private JSONArray _getCollaboratorsJSONArray(RenderRequest renderRequest)
		throws PortletException {

		try {
			long classNameId = ParamUtil.getLong(renderRequest, "classNameId");
			long classPK = ParamUtil.getLong(renderRequest, "classPK");

			ThemeDisplay themeDisplay =
				(ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);

			int countSharingEntryToUserIds =
				_sharingEntryLocalService.countFromUserSharingEntries(
					themeDisplay.getUserId(), classNameId, classPK);

			if (countSharingEntryToUserIds == 0) {
				return JSONFactoryUtil.createJSONArray();
			}

			List<SharingEntry> fromUserSharingEntries =
				_sharingEntryLocalService.getFromUserSharingEntries(
					themeDisplay.getUserId(), classNameId, classPK, 0,
					countSharingEntryToUserIds);

			List<ObjectValuePair<SharingEntry, User>> sharingEntryToUserOVPs =
				new ArrayList<>();

			for (SharingEntry sharingEntry : fromUserSharingEntries) {
				User toUser = _userLocalService.fetchUser(
					sharingEntry.getToUserId());

				if (toUser != null) {
					sharingEntryToUserOVPs.add(
						new ObjectValuePair<>(sharingEntry, toUser));
				}
			}

			JSONArray collaboratorsJSONArray =
				JSONFactoryUtil.createJSONArray();

			for (ObjectValuePair<SharingEntry, User> sharingEntryToUserOVP :
					sharingEntryToUserOVPs) {

				SharingEntry sharingEntry = sharingEntryToUserOVP.getKey();
				User sharingEntryToUser = sharingEntryToUserOVP.getValue();

				JSONObject userJSONObject = JSONFactoryUtil.createJSONObject();

				userJSONObject.put("id", sharingEntryToUser.getUserId());
				userJSONObject.put(
					"imageSrc",
					sharingEntryToUser.getPortraitURL(themeDisplay));
				userJSONObject.put("name", sharingEntryToUser.getFullName());
				userJSONObject.put(
					"sharingEntryId", sharingEntry.getSharingEntryId());

				collaboratorsJSONArray.put(userJSONObject);
			}

			return collaboratorsJSONArray;
		}
		catch (PortalException pe) {
			throw new PortletException(pe);
		}
	}

	private String _getManageCollaboratorsActionURL(
		RenderResponse renderResponse) {

		LiferayPortletResponse liferayPortletResponse =
			_portal.getLiferayPortletResponse(renderResponse);

		PortletURL sharingEntryEditURL =
			liferayPortletResponse.createLiferayPortletURL(
				PortletProviderUtil.getPortletId(
					SharingEntry.class.getName(),
					PortletProvider.Action.MANAGE),
				PortletRequest.ACTION_PHASE);

		sharingEntryEditURL.setParameter(
			ActionRequest.ACTION_NAME, "/sharing/manage_collaborators");

		return sharingEntryEditURL.toString();
	}

	private JSONArray _getSharingEntryPermissionDisplaySelectOptions(
		RenderRequest renderRequest) {

		long classNameId = ParamUtil.getLong(renderRequest, "classNameId");
		long classPK = ParamUtil.getLong(renderRequest, "classPK");

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		List<SharingEntryPermissionDisplay> sharingEntryPermissionDisplays =
			_sharingUtil.getSharingEntryPermissionDisplays(
				themeDisplay.getPermissionChecker(), classNameId, classPK,
				themeDisplay.getScopeGroupId(), themeDisplay.getLocale());

		JSONArray sharingEntryPermissionDisplaySelectOptionsJSONArray =
			JSONFactoryUtil.createJSONArray();

		for (SharingEntryPermissionDisplay sharingEntryPermissionDisplay :
				sharingEntryPermissionDisplays) {

			JSONObject sharingEntryPermissionDisplaySelectOptionJSONObject =
				JSONFactoryUtil.createJSONObject();

			sharingEntryPermissionDisplaySelectOptionJSONObject.put(
				"label", sharingEntryPermissionDisplay.getTitle());
			sharingEntryPermissionDisplaySelectOptionJSONObject.put(
				"value",
				sharingEntryPermissionDisplay.
					getSharingEntryPermissionDisplayActionKeyActionId());

			sharingEntryPermissionDisplaySelectOptionsJSONArray.put(
				sharingEntryPermissionDisplaySelectOptionJSONObject);
		}

		return sharingEntryPermissionDisplaySelectOptionsJSONArray;
	}

	private String _getSpritemap(RenderRequest renderRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return themeDisplay.getPathThemeImages() + "/lexicon/icons.svg";
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private SharingEntryLocalService _sharingEntryLocalService;

	@Reference
	private SharingUtil _sharingUtil;

	@Reference
	private UserLocalService _userLocalService;

}