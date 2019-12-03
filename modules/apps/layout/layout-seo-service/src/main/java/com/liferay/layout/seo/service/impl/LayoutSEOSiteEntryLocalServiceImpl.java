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

package com.liferay.layout.seo.service.impl;

import com.liferay.layout.seo.model.LayoutSEOSiteEntry;
import com.liferay.layout.seo.service.base.LayoutSEOSiteEntryLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.DateUtil;

import java.util.Date;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alicia Garcia
 * @author Adolfo Pérez
 */
@Component(
	property = "model.class.name=com.liferay.layout.seo.model.LayoutSEOSiteEntry",
	service = AopService.class
)
public class LayoutSEOSiteEntryLocalServiceImpl
	extends LayoutSEOSiteEntryLocalServiceBaseImpl {

	@Override
	public LayoutSEOSiteEntry fetchLayoutSEOSiteEntryByGroupId(long groupId) {
		return layoutSEOSiteEntryPersistence.fetchByGroupId(groupId);
	}

	@Override
	public LayoutSEOSiteEntry updateLayoutSEOSiteEntry(
			long userId, long groupId, boolean openGraphEnabled,
			long openGraphImageFileEntryId, ServiceContext serviceContext)
		throws PortalException {

		LayoutSEOSiteEntry layoutSEOSiteEntry =
			layoutSEOSiteEntryPersistence.fetchByGroupId(groupId);

		if (layoutSEOSiteEntry == null) {
			return _addLayoutSEOSiteEntry(
				userId, groupId, openGraphImageFileEntryId, openGraphEnabled,
				serviceContext);
		}

		layoutSEOSiteEntry.setModifiedDate(DateUtil.newDate());
		layoutSEOSiteEntry.setOpenGraphEnabled(openGraphEnabled);
		layoutSEOSiteEntry.setOpenGraphImageFileEntryId(
			openGraphImageFileEntryId);

		return layoutSEOSiteEntryPersistence.update(layoutSEOSiteEntry);
	}

	private LayoutSEOSiteEntry _addLayoutSEOSiteEntry(
			long userId, long groupId, long openGraphImageFileEntryId,
			boolean openGraphEnabled, ServiceContext serviceContext)
		throws PortalException {

		LayoutSEOSiteEntry layoutSEOSiteEntry =
			layoutSEOSiteEntryPersistence.create(
				counterLocalService.increment());

		layoutSEOSiteEntry.setUuid(serviceContext.getUuid());
		layoutSEOSiteEntry.setGroupId(groupId);

		Group group = groupLocalService.getGroup(groupId);

		layoutSEOSiteEntry.setCompanyId(group.getCompanyId());

		layoutSEOSiteEntry.setUserId(userId);

		Date now = DateUtil.newDate();

		layoutSEOSiteEntry.setCreateDate(now);
		layoutSEOSiteEntry.setModifiedDate(now);

		layoutSEOSiteEntry.setOpenGraphEnabled(openGraphEnabled);
		layoutSEOSiteEntry.setOpenGraphImageFileEntryId(
			openGraphImageFileEntryId);

		return layoutSEOSiteEntryPersistence.update(layoutSEOSiteEntry);
	}

}