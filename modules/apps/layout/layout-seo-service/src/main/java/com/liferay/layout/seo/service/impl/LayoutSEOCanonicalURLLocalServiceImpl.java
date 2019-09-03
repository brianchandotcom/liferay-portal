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

import com.liferay.layout.seo.exception.NoSuchCanonicalURLException;
import com.liferay.layout.seo.model.LayoutSEOCanonicalURL;
import com.liferay.layout.seo.service.base.LayoutSEOCanonicalURLLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.DateUtil;

import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = "model.class.name=com.liferay.layout.seo.model.LayoutSEOCanonicalURL",
	service = AopService.class
)
public class LayoutSEOCanonicalURLLocalServiceImpl
	extends LayoutSEOCanonicalURLLocalServiceBaseImpl {

	@Override
	public void deleteLayoutSEOCanonicalURL(
			long groupId, boolean privateLayout, long layoutId)
		throws NoSuchCanonicalURLException {

		layoutSEOCanonicalURLPersistence.removeByG_P_L(
			groupId, privateLayout, layoutId);
	}

	@Override
	public void deleteLayoutSEOCanonicalURL(String uuid, long groupId)
		throws NoSuchCanonicalURLException {

		layoutSEOCanonicalURLPersistence.removeByUUID_G(uuid, groupId);
	}

	@Override
	public LayoutSEOCanonicalURL fetchLayoutSEOCanonicalURL(
		long groupId, boolean privateLayout, long layoutId) {

		return layoutSEOCanonicalURLPersistence.fetchByG_P_L(
			groupId, privateLayout, layoutId);
	}

	@Override
	public LayoutSEOCanonicalURL updateLayoutSEOCanonicalURL(
			long userId, long groupId, boolean privateLayout, long layoutId,
			boolean enabled, Map<Locale, String> canonicalURLMap,
			ServiceContext serviceContext)
		throws PortalException {

		LayoutSEOCanonicalURL layoutSEOCanonicalURL =
			layoutSEOCanonicalURLPersistence.fetchByG_P_L(
				groupId, privateLayout, layoutId);

		if (layoutSEOCanonicalURL == null) {
			return _addLayoutSEOCanonicalURL(
				userId, groupId, privateLayout, layoutId, enabled,
				canonicalURLMap, serviceContext);
		}

		layoutSEOCanonicalURL.setModifiedDate(DateUtil.newDate());
		layoutSEOCanonicalURL.setEnabled(enabled);
		layoutSEOCanonicalURL.setCanonicalURLMap(canonicalURLMap);

		return layoutSEOCanonicalURLPersistence.update(layoutSEOCanonicalURL);
	}

	private LayoutSEOCanonicalURL _addLayoutSEOCanonicalURL(
			long userId, long groupId, boolean privateLayout, long layoutId,
			boolean enabled, Map<Locale, String> canonicalURLMap,
			ServiceContext serviceContext)
		throws PortalException {

		LayoutSEOCanonicalURL layoutSEOCanonicalURL =
			layoutSEOCanonicalURLPersistence.create(
				counterLocalService.increment());

		layoutSEOCanonicalURL.setUuid(serviceContext.getUuid());
		layoutSEOCanonicalURL.setGroupId(groupId);

		Group group = groupLocalService.getGroup(groupId);

		layoutSEOCanonicalURL.setCompanyId(group.getCompanyId());

		layoutSEOCanonicalURL.setUserId(userId);

		Date now = DateUtil.newDate();

		layoutSEOCanonicalURL.setCreateDate(now);
		layoutSEOCanonicalURL.setModifiedDate(now);

		layoutSEOCanonicalURL.setEnabled(enabled);
		layoutSEOCanonicalURL.setCanonicalURLMap(canonicalURLMap);
		layoutSEOCanonicalURL.setPrivateLayout(privateLayout);
		layoutSEOCanonicalURL.setLayoutId(layoutId);

		return layoutSEOCanonicalURLPersistence.update(layoutSEOCanonicalURL);
	}

}