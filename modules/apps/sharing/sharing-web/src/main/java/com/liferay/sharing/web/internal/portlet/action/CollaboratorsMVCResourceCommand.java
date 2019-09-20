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
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.sharing.configuration.SharingConfiguration;
import com.liferay.sharing.configuration.SharingConfigurationFactory;
import com.liferay.sharing.constants.SharingPortletKeys;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.model.SharingEntryModel;
import com.liferay.sharing.security.permission.SharingPermission;
import com.liferay.sharing.service.SharingEntryLocalService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.PortletURL;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + SharingPortletKeys.SHARING,
		"mvc.command.name=/sharing/collaborators"
	},
	service = MVCResourceCommand.class
)
public class CollaboratorsMVCResourceCommand extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			resourceRequest);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		SharingConfiguration sharingConfiguration =
			_sharingConfigurationFactory.getGroupSharingConfiguration(
				themeDisplay.getSiteGroup());

		if (!sharingConfiguration.isEnabled()) {
			return;
		}

		if (!themeDisplay.isSignedIn()) {
			throw new PrincipalException.MustBeAuthenticated(
				themeDisplay.getUserId());
		}

		long classNameId = _classNameLocalService.getClassNameId(
			ParamUtil.getString(httpServletRequest, "className"));

		long classPK = ParamUtil.getLong(httpServletRequest, "classPK");

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse,
			JSONUtil.put(
				"collaborators", _getSharingEntryToUsers(classPK, classNameId)
			).put(
				"manageCollaboratorsURL",
				_getManageCollaboratorsURL(
					classNameId, classPK, themeDisplay, httpServletRequest,
					resourceResponse)
			).put(
				"total",
				_sharingEntryLocalService.getSharingEntriesCount(
					classNameId, classPK)
			));
	}

	private PortletURL _getManageCollaboratorsURL(
			long classNameId, long classPK, ThemeDisplay themeDisplay,
			HttpServletRequest httpServletRequest,
			ResourceResponse resourceResponse)
		throws PortalException, WindowStateException {

		boolean canManageCollaborators = false;

		try {
			canManageCollaborators =
				_sharingPermission.containsManageCollaboratorsPermission(
					themeDisplay.getPermissionChecker(), classNameId, classPK,
					themeDisplay.getScopeGroupId());
		}
		catch (PortalException pe) {
			_log.error(pe, pe);
		}

		if (canManageCollaborators) {
			PortletURL manageCollaboratorsRenderURL =
				PortletProviderUtil.getPortletURL(
					httpServletRequest, SharingEntry.class.getName(),
					PortletProvider.Action.MANAGE);

			manageCollaboratorsRenderURL.setParameter(
				"classNameId", String.valueOf(classNameId));
			manageCollaboratorsRenderURL.setParameter(
				"classPK", String.valueOf(classPK));
			manageCollaboratorsRenderURL.setParameter(
				"dialogId",
				resourceResponse.getNamespace() + "manageCollaboratorsDialog");
			manageCollaboratorsRenderURL.setWindowState(
				LiferayWindowState.POP_UP);

			return manageCollaboratorsRenderURL;
		}

		return null;
	}

	private List<User> _getSharingEntryToUsers(long classPK, long classNameId) {
		List<SharingEntry> sharingEntries =
			_sharingEntryLocalService.getSharingEntries(
				classNameId, classPK, 0, 4);

		Stream<SharingEntry> stream = sharingEntries.stream();

		return stream.map(
			SharingEntryModel::getToUserId
		).map(
			_userLocalService::fetchUserById
		).filter(
			Objects::nonNull
		).collect(
			Collectors.toList()
		);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CollaboratorsMVCResourceCommand.class);

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private SharingConfigurationFactory _sharingConfigurationFactory;

	@Reference
	private SharingEntryLocalService _sharingEntryLocalService;

	@Reference
	private SharingPermission _sharingPermission;

	@Reference
	private UserLocalService _userLocalService;

}