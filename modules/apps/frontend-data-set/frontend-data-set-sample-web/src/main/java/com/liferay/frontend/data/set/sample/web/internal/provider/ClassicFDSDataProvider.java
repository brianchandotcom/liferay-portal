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

package com.liferay.frontend.data.set.sample.web.internal.provider;

import com.liferay.frontend.data.set.provider.FDSDataProvider;
import com.liferay.frontend.data.set.provider.search.FDSKeywords;
import com.liferay.frontend.data.set.provider.search.FDSPagination;
import com.liferay.frontend.data.set.sample.web.internal.constants.FDSSampleFDSNames;
import com.liferay.frontend.data.set.sample.web.internal.model.UserEntry;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.users.admin.kernel.util.UsersAdmin;

import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marko Cikos
 */
@Component(
	immediate = true,
	property = "fds.data.provider.key=" + FDSSampleFDSNames.CLASSIC,
	service = FDSDataProvider.class
)
public class ClassicFDSDataProvider implements FDSDataProvider<UserEntry> {

	@Override
	public List<UserEntry> getItems(
			FDSKeywords fdsKeywords, FDSPagination fdsPagination,
			HttpServletRequest httpServletRequest, Sort sort)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return TransformUtil.transform(
			_usersAdmin.getUsers(
				_userLocalService.search(
					themeDisplay.getCompanyId(), fdsKeywords.getKeywords(),
					WorkflowConstants.STATUS_APPROVED,
					new LinkedHashMap<String, Object>(),
					fdsPagination.getStartPosition(),
					fdsPagination.getEndPosition(), sort)),
			user -> new UserEntry(
				user.getEmailAddress(), user.getFirstName(), user.getUserId(),
				user.getLastName()));
	}

	@Override
	public int getItemsCount(
			FDSKeywords fdsKeywords, HttpServletRequest httpServletRequest)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return _userLocalService.searchCount(
			themeDisplay.getCompanyId(), fdsKeywords.getKeywords(),
			WorkflowConstants.STATUS_APPROVED, null);
	}

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private UsersAdmin _usersAdmin;

}