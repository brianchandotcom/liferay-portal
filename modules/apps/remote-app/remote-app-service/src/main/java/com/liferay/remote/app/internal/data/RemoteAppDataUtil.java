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

package com.liferay.remote.app.internal.data;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.remote.app.service.RemoteAppEntryLocalService;

import java.util.Collections;

/**
 * @author Shuyang Zhou
 */
public class RemoteAppDataUtil {

	public static void addRemoteApp(
			RemoteAppEntryLocalService remoteAppEntryLocalService,
			UserLocalService userLocalService, Company company)
		throws PortalException {

		remoteAppEntryLocalService.addCustomElementRemoteAppEntry(
			userLocalService.getDefaultUserId(company.getCompanyId()),
			StringPool.BLANK, "vanilla-counter",
			"https://liferay.github.io/liferay-frontend-projects" +
				"/vanilla-counter/index.js",
			"See how a vanilla counter works as a remote app.",
			"vanilla_counter", false,
			Collections.singletonMap(
				LocaleUtil.getDefault(), "Vanilla Counter"),
			"category.remote-apps", "friendly-url-mapping=vanilla_counter",
			"https://liferay.github.io/liferay-frontend-projects",
			WorkflowConstants.STATUS_APPROVED);
	}

}