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

import com.liferay.layout.seo.model.LayoutSEOSite;
import com.liferay.layout.seo.service.base.LayoutSEOSiteLocalServiceBaseImpl;
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
	property = "model.class.name=com.liferay.layout.seo.model.LayoutSEOSite",
	service = AopService.class
)
public class LayoutSEOSiteLocalServiceImpl
	extends LayoutSEOSiteLocalServiceBaseImpl {

	@Override
	public LayoutSEOSite fetchLayoutSEOSiteByGroupId(long groupId) {
		return layoutSEOSitePersistence.fetchByGroupId(groupId);
	}

	@Override
	public LayoutSEOSite updateLayoutSEOSite(
			long userId, long groupId, boolean openGraphEnabled,
			long openGraphImageFileEntryId, ServiceContext serviceContext)
		throws PortalException {

		LayoutSEOSite layoutSEOSite = layoutSEOSitePersistence.fetchByGroupId(
			groupId);

		if (layoutSEOSite == null) {
			return _addLayoutSEOSite(
				userId, groupId, openGraphImageFileEntryId, openGraphEnabled,
				serviceContext);
		}

		layoutSEOSite.setModifiedDate(DateUtil.newDate());
		layoutSEOSite.setOpenGraphEnabled(openGraphEnabled);
		layoutSEOSite.setOpenGraphImageFileEntryId(openGraphImageFileEntryId);

		return layoutSEOSitePersistence.update(layoutSEOSite);
	}

	private LayoutSEOSite _addLayoutSEOSite(
			long userId, long groupId, long openGraphImageFileEntryId,
			boolean openGraphEnabled, ServiceContext serviceContext)
		throws PortalException {

		LayoutSEOSite layoutSEOSite = layoutSEOSitePersistence.create(
			counterLocalService.increment());

		layoutSEOSite.setUuid(serviceContext.getUuid());
		layoutSEOSite.setGroupId(groupId);

		Group group = groupLocalService.getGroup(groupId);

		layoutSEOSite.setCompanyId(group.getCompanyId());

		layoutSEOSite.setUserId(userId);

		Date now = DateUtil.newDate();

		layoutSEOSite.setCreateDate(now);
		layoutSEOSite.setModifiedDate(now);

		layoutSEOSite.setOpenGraphEnabled(openGraphEnabled);
		layoutSEOSite.setOpenGraphImageFileEntryId(openGraphImageFileEntryId);

		return layoutSEOSitePersistence.update(layoutSEOSite);
	}

}