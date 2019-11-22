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

import com.liferay.layout.seo.model.SiteSEOEntry;
import com.liferay.layout.seo.service.base.SiteSEOEntryLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.DateUtil;

import java.util.Date;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alicia Garcia
 */
@Component(
	property = "model.class.name=com.liferay.layout.seo.model.SiteSEOEntry",
	service = AopService.class
)
public class SiteSEOEntryLocalServiceImpl
	extends SiteSEOEntryLocalServiceBaseImpl {

	@Override
	public SiteSEOEntry fetchSiteSEOEntryByGroupId(long groupId) {
		return siteSEOEntryPersistence.fetchByGroupId(groupId);
	}

	public SiteSEOEntry updateSiteSEOEntry(
			long userId, long groupId, long openGraphImageFileEntryId,
			boolean openGraphEnabled, ServiceContext serviceContext)
		throws PortalException {

		SiteSEOEntry siteSEOEntry = siteSEOEntryPersistence.fetchByGroupId(
			groupId);

		if (siteSEOEntry == null) {
			siteSEOEntry = _addSiteSEOEntry(userId, groupId, serviceContext);
		}

		siteSEOEntry.setModifiedDate(DateUtil.newDate());

		siteSEOEntry.setOpenGraphEnabled(openGraphEnabled);

		if (openGraphEnabled) {
			siteSEOEntry.setOpenGraphImageFileEntryId(
				openGraphImageFileEntryId);
		}

		return siteSEOEntryPersistence.update(siteSEOEntry);
	}

	private SiteSEOEntry _addSiteSEOEntry(
			long userId, long groupId, ServiceContext serviceContext)
		throws PortalException {

		SiteSEOEntry siteSEOEntry;
		siteSEOEntry = siteSEOEntryPersistence.create(
			counterLocalService.increment());

		siteSEOEntry.setUuid(serviceContext.getUuid());
		siteSEOEntry.setGroupId(groupId);

		Group group = groupLocalService.getGroup(groupId);

		siteSEOEntry.setCompanyId(group.getCompanyId());

		siteSEOEntry.setUserId(userId);

		Date now = DateUtil.newDate();

		siteSEOEntry.setCreateDate(now);
		siteSEOEntry.setModifiedDate(now);

		return siteSEOEntry;
	}

}