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

import com.liferay.layout.seo.exception.NoSuchSiteException;
import com.liferay.layout.seo.model.LayoutSEOSite;
import com.liferay.layout.seo.service.base.LayoutSEOSiteLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

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
	public void deleteLayoutSEOSite(String uuid, long groupId)
		throws NoSuchSiteException {

		layoutSEOSitePersistence.removeByUUID_G(uuid, groupId);
	}

	@Override
	public LayoutSEOSite fetchLayoutSEOSiteByGroupId(long groupId) {
		return layoutSEOSitePersistence.fetchByGroupId(groupId);
	}

	@Override
	public List<LayoutSEOSite> getLayoutSEOSitesByUuidAndCompanyId(
		String uuid, long companyId) {

		return layoutSEOSitePersistence.findByUuid_C(uuid, companyId);
	}

	@Override
	public LayoutSEOSite updateLayoutSEOSite(
			long userId, long groupId, boolean openGraphEnabled,
			Map<Locale, String> openGraphImageAltMap,
			long openGraphImageFileEntryId, ServiceContext serviceContext)
		throws PortalException {

		LayoutSEOSite layoutSEOSite = layoutSEOSitePersistence.fetchByGroupId(
			groupId);

		if (layoutSEOSite == null) {
			return _addLayoutSEOSite(
				userId, groupId, openGraphEnabled, openGraphImageAltMap,
				openGraphImageFileEntryId, serviceContext);
		}

		layoutSEOSite.setModifiedDate(new Date());
		layoutSEOSite.setOpenGraphEnabled(openGraphEnabled);

		if (openGraphImageFileEntryId != 0) {
			layoutSEOSite.setOpenGraphImageAltMap(openGraphImageAltMap);
		}
		else {
			layoutSEOSite.setOpenGraphImageAltMap(Collections.emptyMap());
		}

		layoutSEOSite.setOpenGraphImageFileEntryId(openGraphImageFileEntryId);

		return layoutSEOSitePersistence.update(layoutSEOSite);
	}

	private LayoutSEOSite _addLayoutSEOSite(
			long userId, long groupId, boolean openGraphEnabled,
			Map<Locale, String> openGraphImageAltMap,
			long openGraphImageFileEntryId, ServiceContext serviceContext)
		throws PortalException {

		LayoutSEOSite layoutSEOSite = layoutSEOSitePersistence.create(
			counterLocalService.increment());

		layoutSEOSite.setUuid(serviceContext.getUuid());
		layoutSEOSite.setGroupId(groupId);

		Group group = _groupLocalService.getGroup(groupId);

		layoutSEOSite.setCompanyId(group.getCompanyId());

		layoutSEOSite.setUserId(userId);
		layoutSEOSite.setCreateDate(new Date());
		layoutSEOSite.setModifiedDate(new Date());
		layoutSEOSite.setOpenGraphEnabled(openGraphEnabled);

		if (openGraphImageFileEntryId != 0) {
			layoutSEOSite.setOpenGraphImageAltMap(openGraphImageAltMap);
		}

		layoutSEOSite.setOpenGraphImageFileEntryId(openGraphImageFileEntryId);

		return layoutSEOSitePersistence.update(layoutSEOSite);
	}

	@Reference
	private GroupLocalService _groupLocalService;

}