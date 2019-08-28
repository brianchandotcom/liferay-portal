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

import com.liferay.layout.seo.model.LayoutCanonicalURL;
import com.liferay.layout.seo.service.base.LayoutCanonicalURLLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.util.DateUtil;

import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = "model.class.name=com.liferay.layout.seo.model.LayoutCanonicalURL",
	service = AopService.class
)
public class LayoutCanonicalURLLocalServiceImpl
	extends LayoutCanonicalURLLocalServiceBaseImpl {

	@Override
	public LayoutCanonicalURL fetchLayoutCanonicalURL(
		long groupId, boolean privateLayout, long layoutId) {

		return layoutCanonicalURLPersistence.fetchByG_P_L(
			groupId, privateLayout, layoutId);
	}

	@Override
	public LayoutCanonicalURL updateLayoutCanonicalURL(
			long userId, long groupId, boolean privateLayout, long layoutId,
			boolean enabled, Map<Locale, String> canonicalURLMap)
		throws PortalException {

		LayoutCanonicalURL layoutCanonicalURL =
			layoutCanonicalURLPersistence.fetchByG_P_L(
				groupId, privateLayout, layoutId);

		if (layoutCanonicalURL == null) {
			return _addLayoutCanonicalURL(
				userId, groupId, privateLayout, layoutId, enabled,
				canonicalURLMap);
		}

		layoutCanonicalURL.setModifiedDate(DateUtil.newDate());
		layoutCanonicalURL.setEnabled(enabled);
		layoutCanonicalURL.setCanonicalURLMap(canonicalURLMap);

		return layoutCanonicalURLPersistence.update(layoutCanonicalURL);
	}

	private LayoutCanonicalURL _addLayoutCanonicalURL(
			long userId, long groupId, boolean privateLayout, long layoutId,
			boolean enabled, Map<Locale, String> canonicalURLMap)
		throws PortalException {

		LayoutCanonicalURL layoutCanonicalURL =
			layoutCanonicalURLPersistence.create(
				counterLocalService.increment());

		layoutCanonicalURL.setGroupId(groupId);

		Group group = groupLocalService.getGroup(groupId);

		layoutCanonicalURL.setCompanyId(group.getCompanyId());

		layoutCanonicalURL.setUserId(userId);

		Date now = DateUtil.newDate();

		layoutCanonicalURL.setCreateDate(now);
		layoutCanonicalURL.setModifiedDate(now);

		layoutCanonicalURL.setEnabled(enabled);
		layoutCanonicalURL.setCanonicalURLMap(canonicalURLMap);
		layoutCanonicalURL.setPrivateLayout(privateLayout);
		layoutCanonicalURL.setLayoutId(layoutId);

		return layoutCanonicalURLPersistence.update(layoutCanonicalURL);
	}

}