/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.view.state.service.impl;

import com.liferay.frontend.view.state.model.FVSActiveEntry;
import com.liferay.frontend.view.state.service.base.FVSActiveEntryLocalServiceBaseImpl;
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
	property = "model.class.name=com.liferay.frontend.view.state.model.FVSActiveEntry",
	service = AopService.class
)
public class FVSActiveEntryLocalServiceImpl
	extends FVSActiveEntryLocalServiceBaseImpl {

	@Override
	public FVSActiveEntry addFVSActiveEntry(
			long userId, long fvsEntryId, String clayDataSetDisplayId,
			long plid, String portletId)
		throws PortalException {

		FVSActiveEntry fvsActiveEntry = fvsActiveEntryPersistence.create(
			counterLocalService.increment());

		User user = _userLocalService.getUserById(userId);

		fvsActiveEntry.setCompanyId(user.getCompanyId());
		fvsActiveEntry.setUserId(user.getUserId());
		fvsActiveEntry.setUserName(user.getFullName());

		fvsActiveEntry.setFvsEntryId(fvsEntryId);
		fvsActiveEntry.setClayDataSetDisplayId(clayDataSetDisplayId);
		fvsActiveEntry.setPlid(plid);
		fvsActiveEntry.setPortletId(portletId);

		return fvsActiveEntryPersistence.update(fvsActiveEntry);
	}

	@Override
	public FVSActiveEntry fetchFVSActiveEntry(
		long userId, String clayDataSetDisplayId, long plid, String portletId) {

		return fvsActiveEntryPersistence.fetchByU_CDSDI_P_P(
			userId, clayDataSetDisplayId, plid, portletId);
	}

	@Reference
	private UserLocalService _userLocalService;

}