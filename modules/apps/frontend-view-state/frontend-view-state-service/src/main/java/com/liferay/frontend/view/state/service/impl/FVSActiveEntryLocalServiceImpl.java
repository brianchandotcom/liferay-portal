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

import com.liferay.frontend.view.state.model.FVSActiveEntry;
import com.liferay.frontend.view.state.service.base.FVSActiveEntryLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.frontend.view.state.model.FVSActiveEntry",
	service = AopService.class
)
public class FVSActiveEntryLocalServiceImpl
	extends FVSActiveEntryLocalServiceBaseImpl {

	public FVSActiveEntry createFVSActiveEntry(
		long userId, long fvsEntryId, String clayDataSetDisplayId, long plid,
		String portletId) {

		FVSActiveEntry fvsActiveEntry =
			fvsActiveEntryLocalService.createFVSActiveEntry(
				counterLocalService.increment());

		fvsActiveEntry.setUserId(userId);
		fvsActiveEntry.setFvsEntryId(fvsEntryId);
		fvsActiveEntry.setClayDataSetDisplayId(clayDataSetDisplayId);
		fvsActiveEntry.setPlid(plid);
		fvsActiveEntry.setPortletId(portletId);

		return fvsActiveEntry;
	}

	public FVSActiveEntry fetchFVSActiveEntry(
		long userId, String clayDatasetDisplayId, long plid, String portletId) {

		return fvsActiveEntryPersistence.fetchByU_CDSDI_P_P(
			userId, clayDatasetDisplayId, plid, portletId);
	}

}