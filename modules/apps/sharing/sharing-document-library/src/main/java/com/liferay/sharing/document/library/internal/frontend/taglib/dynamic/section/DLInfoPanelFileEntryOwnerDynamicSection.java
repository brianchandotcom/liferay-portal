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

package com.liferay.sharing.document.library.internal.frontend.taglib.dynamic.section;

import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.frontend.taglib.dynamic.section.DynamicSection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.sharing.configuration.SharingConfiguration;
import com.liferay.sharing.configuration.SharingConfigurationFactory;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.model.SharingEntryModel;
import com.liferay.sharing.security.permission.SharingPermission;
import com.liferay.sharing.service.SharingEntryLocalService;
import com.liferay.taglib.servlet.PipingServletResponse;

import java.io.ByteArrayOutputStream;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(
	immediate = true,
	property = "name=com.liferay.document.library.web#/document_library/info_panel_file_entry.jsp#fileEntryOwner",
	service = DynamicSection.class
)
public class DLInfoPanelFileEntryOwnerDynamicSection implements DynamicSection {

	@Override
	public StringBundler modify(StringBundler sb, PageContext pageContext) {
		HttpServletRequest httpServletRequest =
			(HttpServletRequest)pageContext.getRequest();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		SharingConfiguration sharingConfiguration =
			_sharingConfigurationFactory.getGroupSharingConfiguration(
				themeDisplay.getSiteGroup());

		if (!sharingConfiguration.isEnabled()) {
			return sb;
		}

		long classNameId = _classNameLocalService.getClassNameId(
			DLFileEntryConstants.getClassName());
		FileEntry fileEntry = (FileEntry)httpServletRequest.getAttribute(
			"info_panel.jsp-fileEntry");

		int sharingEntriesCount =
			_sharingEntryLocalService.getSharingEntriesCount(
				classNameId, fileEntry.getFileEntryId());

		if (sharingEntriesCount == 0) {
			return sb;
		}

		httpServletRequest.setAttribute(
			"info_panel_file_entry.jsp-sharingEntriesCount",
			sharingEntriesCount);

		List<SharingEntry> sharingEntries =
			_sharingEntryLocalService.getSharingEntries(
				classNameId, fileEntry.getFileEntryId(), 0, 4);

		Stream<SharingEntry> stream = sharingEntries.stream();

		List<User> sharingEntryToUsers = stream.map(
			SharingEntryModel::getToUserId
		).map(
			_userLocalService::fetchUserById
		).filter(
			Objects::nonNull
		).collect(
			Collectors.toList()
		);

		httpServletRequest.setAttribute(
			"info_panel_file_entry.jsp-sharingEntryToUsers",
			sharingEntryToUsers);

		boolean showManageCollaborators = false;

		try {
			showManageCollaborators =
				_sharingPermission.containsManageCollaboratorsPermission(
					themeDisplay.getPermissionChecker(), classNameId,
					fileEntry.getFileEntryId(), themeDisplay.getScopeGroupId());
		}
		catch (PortalException pe) {
			_log.error(pe, pe);
		}

		httpServletRequest.setAttribute(
			"info_panel_file_entry.jsp-showManageCollaborators",
			showManageCollaborators);

		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher(
				"/META-INF/resources/dynamic_section" +
					"/info_panel_file_entry.jsp");

		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
			HttpServletResponse httpServletResponse = new PipingServletResponse(
				(HttpServletResponse)pageContext.getResponse(), outputStream);

			requestDispatcher.include(
				pageContext.getRequest(), httpServletResponse);

			return new StringBundler(new String(outputStream.toByteArray()));
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLInfoPanelFileEntryOwnerDynamicSection.class);

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.sharing.document.library)"
	)
	private ServletContext _servletContext;

	@Reference
	private SharingConfigurationFactory _sharingConfigurationFactory;

	@Reference
	private SharingEntryLocalService _sharingEntryLocalService;

	@Reference
	private SharingPermission _sharingPermission;

	@Reference
	private UserLocalService _userLocalService;

}