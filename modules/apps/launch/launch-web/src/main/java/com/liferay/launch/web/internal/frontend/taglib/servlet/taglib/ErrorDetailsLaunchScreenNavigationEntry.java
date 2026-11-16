/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.launch.web.internal.frontend.taglib.servlet.taglib;

import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.launch.web.internal.constants.LaunchScreenNavigationConstants;
import com.liferay.launch.web.internal.model.LaunchSet;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.MapUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jose Luis Navarro
 */
@Component(
	property = "screen.navigation.entry.order:Integer=30",
	service = ScreenNavigationEntry.class
)
public class ErrorDetailsLaunchScreenNavigationEntry
	extends BaseLaunchScreenNavigationEntry {

	@Override
	public String getCategoryKey() {
		return LaunchScreenNavigationConstants.CATEGORY_KEY_ERROR_DETAILS;
	}

	@Override
	public String getJspPath() {
		return "/launch/error_details.jsp";
	}

	@Override
	public boolean isVisible(User user, LaunchSet launchSet) {
		ObjectEntry objectEntry = _objectEntryLocalService.fetchObjectEntry(
			launchSet.getLaunchSetId());

		if ((objectEntry == null) || !objectEntry.isDraft()) {
			return false;
		}

		if (MapUtil.getLong(objectEntry.getValues(), "publishErrorsCount") >
				0) {

			return true;
		}

		return false;
	}

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

}