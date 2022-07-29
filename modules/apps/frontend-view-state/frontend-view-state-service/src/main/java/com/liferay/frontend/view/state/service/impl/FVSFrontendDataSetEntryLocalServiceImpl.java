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

package com.liferay.frontend.view.state.service.impl;

import com.liferay.frontend.view.state.model.FVSFrontendDataSetEntry;
import com.liferay.frontend.view.state.service.base.FVSFrontendDataSetEntryLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.frontend.view.state.model.FVSFrontendDataSetEntry",
	service = AopService.class
)
public class FVSFrontendDataSetEntryLocalServiceImpl
	extends FVSFrontendDataSetEntryLocalServiceBaseImpl {

	@Override
	public FVSFrontendDataSetEntry addFVSFrontendDataSetEntry(
			long fvsEntryId, String fdsName, String name, long plid,
			String portletId, long userId)
		throws PortalException {

		FVSFrontendDataSetEntry fvsFrontendDataSetEntry =
			fvsFrontendDataSetEntryPersistence.create(
				counterLocalService.increment());

		User user = _userLocalService.getUserById(userId);

		fvsFrontendDataSetEntry.setCompanyId(user.getCompanyId());
		fvsFrontendDataSetEntry.setUserId(user.getUserId());
		fvsFrontendDataSetEntry.setUserName(user.getFullName());

		fvsFrontendDataSetEntry.setFvsEntryId(fvsEntryId);
		fvsFrontendDataSetEntry.setFdsName(fdsName);
		fvsFrontendDataSetEntry.setName(name);
		fvsFrontendDataSetEntry.setPlid(plid);
		fvsFrontendDataSetEntry.setPortletId(portletId);

		return fvsFrontendDataSetEntryPersistence.update(
			fvsFrontendDataSetEntry);
	}

	@Override
	public List<FVSFrontendDataSetEntry> getFVSFrontendDataSetEntries(
		String fdsName, long plid, String portletId, long userId) {

		return fvsFrontendDataSetEntryPersistence.findByU_F_P_P(
			userId, fdsName, plid, portletId);
	}

	@Reference
	private UserLocalService _userLocalService;

}