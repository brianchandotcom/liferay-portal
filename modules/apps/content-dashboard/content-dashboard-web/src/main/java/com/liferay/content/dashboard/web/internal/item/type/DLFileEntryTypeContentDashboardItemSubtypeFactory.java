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

package com.liferay.content.dashboard.web.internal.item.type;

import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.GroupLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = ContentDashboardItemSubtypeFactory.class)
public class DLFileEntryTypeContentDashboardItemSubtypeFactory
	implements ContentDashboardItemSubtypeFactory<DLFileEntryType> {

	@Override
	public ContentDashboardItemSubtype<DLFileEntryType> create(long classPK)
		throws PortalException {

		DLFileEntryType dlFileEntryType =
			_dlFileEntryTypeLocalService.getFileEntryType(classPK);

		return new DLFileEntryTypeContentDashboardItemSubtype(
			dlFileEntryType,
			_groupLocalService.fetchGroup(dlFileEntryType.getGroupId()));
	}

	@Reference
	private DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

}