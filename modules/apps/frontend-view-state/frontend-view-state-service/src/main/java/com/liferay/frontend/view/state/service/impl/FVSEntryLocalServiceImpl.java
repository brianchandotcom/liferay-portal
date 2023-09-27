/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.view.state.service.impl;

import com.liferay.frontend.view.state.model.FVSEntry;
import com.liferay.frontend.view.state.service.base.FVSEntryLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.frontend.view.state.model.FVSEntry",
	service = AopService.class
)
public class FVSEntryLocalServiceImpl extends FVSEntryLocalServiceBaseImpl {

	@Override
	public FVSEntry addFVSEntry(long userId, String viewState)
		throws PortalException {

		FVSEntry fvsEntry = fvsEntryPersistence.create(
			counterLocalService.increment());

		User user = _userLocalService.getUserById(userId);

		fvsEntry.setCompanyId(user.getCompanyId());
		fvsEntry.setUserId(user.getUserId());
		fvsEntry.setUserName(user.getFullName());

		fvsEntry.setViewState(viewState);

		return fvsEntryPersistence.update(fvsEntry);
	}

	@Reference
	private UserLocalService _userLocalService;

}