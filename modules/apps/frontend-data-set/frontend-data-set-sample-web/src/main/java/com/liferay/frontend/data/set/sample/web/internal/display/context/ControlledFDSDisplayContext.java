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

package com.liferay.frontend.data.set.sample.web.internal.display.context;

import com.liferay.frontend.data.set.sample.web.internal.constants.FDSSampleFDSNames;
import com.liferay.frontend.data.set.sample.web.internal.model.UserEntry;
import com.liferay.frontend.data.set.sample.web.internal.servlet.ServletContextUtil;
import com.liferay.frontend.data.set.view.FDSViewSerializer;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marko Cikos
 */
public class ControlledFDSDisplayContext {

	public ControlledFDSDisplayContext(HttpServletRequest httpServletRequest) {
		_httpServletRequest = httpServletRequest;
	}

	public Object getItems() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return TransformUtil.transform(
			UserLocalServiceUtil.getUsers(
				themeDisplay.getCompanyId(), false,
				WorkflowConstants.STATUS_APPROVED, 0, 20, null),
			user -> new UserEntry(
				user.getEmailAddress(), user.getFirstName(), user.getUserId(),
				user.getLastName()));
	}

	public Object getViews() {
		FDSViewSerializer fdsViewSerializer =
			ServletContextUtil.getFDSViewSerializer();

		return fdsViewSerializer.serialize(
			FDSSampleFDSNames.CONTROLLED,
			PortalUtil.getLocale(_httpServletRequest));
	}

	private final HttpServletRequest _httpServletRequest;

}