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

package com.liferay.sharing.document.library.internal.servlet.taglib;

import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.frontend.taglib.dynamic.section.DynamicSection;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.model.SharingEntryModel;
import com.liferay.sharing.service.SharingEntryLocalService;
import com.liferay.taglib.servlet.PipingServletResponse;

import java.io.ByteArrayOutputStream;

import java.util.List;
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
	property = "name=com.liferay.document.library.web#/document_library/info_panel_file_entry.jsp#fileEntryOwner"
)
public class DLInfoPanelFileEntryOwnerDynamicSection implements DynamicSection {

	@Override
	public StringBundler modify(StringBundler sb, PageContext pageContext) {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		FileEntry fileEntry = (FileEntry)request.getAttribute(
			"info_panel.jsp-fileEntry");

		long classNameId = _classNameLocalService.getClassNameId(
			DLFileEntryConstants.getClassName());

		int countSharingEntryToUserIds =
			_sharingEntryLocalService.countFromUserSharingEntries(
				themeDisplay.getUserId(), classNameId,
				fileEntry.getFileEntryId());

		if (countSharingEntryToUserIds == 0) {
			return sb;
		}

		request.setAttribute(
			"info_panel_file_entry.jsp-countSharingEntryToUserIds",
			countSharingEntryToUserIds);

		List<SharingEntry> fromUserSharingEntries =
			_sharingEntryLocalService.getFromUserSharingEntries(
				themeDisplay.getUserId(), classNameId,
				fileEntry.getFileEntryId(), 0, 4);

		Stream<SharingEntry> fromUserSharingEntriesStream =
			fromUserSharingEntries.stream();

		List<User> sharingEntryToUsers = fromUserSharingEntriesStream.map(
			SharingEntryModel::getFromUserId
		).map(
			_userLocalService::fetchUserById
		).filter(
			user -> user != null
		).collect(
			Collectors.toList()
		);

		request.setAttribute(
			"info_panel_file_entry.jsp-sharingEntryToUsers",
			sharingEntryToUsers);

		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher(
				"/META-INF/resources/dynamic_section" +
					"/info_panel_file_entry.jsp");

		String result = null;

		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
			HttpServletResponse httpServletResponse = new PipingServletResponse(
				(HttpServletResponse)pageContext.getResponse(), outputStream);

			requestDispatcher.include(
				pageContext.getRequest(), httpServletResponse);

			result = new String(outputStream.toByteArray());
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}

		return new StringBundler(result);
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.sharing.document.library)"
	)
	private ServletContext _servletContext;

	@Reference
	private SharingEntryLocalService _sharingEntryLocalService;

	@Reference
	private UserLocalService _userLocalService;

}