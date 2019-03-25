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

package com.liferay.layout.page.template.service.impl;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntryVersion;
import com.liferay.layout.page.template.service.base.LayoutPageTemplateEntryVersionLocalServiceBaseImpl;
import com.liferay.layout.page.template.util.comparator.LayoutPageTemplateEntryVersionComparator;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Pavel Savinov
 * @see LayoutPageTemplateEntryVersionLocalServiceBaseImpl
 */
public class LayoutPageTemplateEntryVersionLocalServiceImpl
	extends LayoutPageTemplateEntryVersionLocalServiceBaseImpl {

	public LayoutPageTemplateEntryVersion addLayoutPageTemplateEntryVersion(
			long userId, long groupId, long layoutPageTemplateEntryId,
			double version, String name, long classNameId, long classTypeId,
			int type, ServiceContext serviceContext)
		throws PortalException {

		// Layout page template version

		User user = userLocalService.getUser(userId);

		long layoutPageTemplateEntryVersionId = counterLocalService.increment();

		LayoutPageTemplateEntryVersion layoutPageTemplateEntryVersion =
			layoutPageTemplateEntryVersionPersistence.create(
				layoutPageTemplateEntryVersionId);

		Date now = new Date();

		layoutPageTemplateEntryVersion.setGroupId(groupId);
		layoutPageTemplateEntryVersion.setCompanyId(user.getCompanyId());
		layoutPageTemplateEntryVersion.setUserId(user.getUserId());
		layoutPageTemplateEntryVersion.setUserName(user.getFullName());
		layoutPageTemplateEntryVersion.setCreateDate(
			serviceContext.getCreateDate(now));
		layoutPageTemplateEntryVersion.setModifiedDate(
			serviceContext.getModifiedDate(now));
		layoutPageTemplateEntryVersion.setLayoutPageTemplateEntryId(
			layoutPageTemplateEntryId);

		// Layout

		Layout layout = _addLayout(
			userId, groupId, name, classNameId, classTypeId, type,
			serviceContext);

		layoutPageTemplateEntryVersion.setPlid(layout.getPlid());

		layoutPageTemplateEntryVersion.setVersion(version);

		return layoutPageTemplateEntryVersionPersistence.update(
			layoutPageTemplateEntryVersion);
	}

	public void deleteLayoutPageTemplateEntryVersions(
		long layoutPageTemplateEntryId) {

		layoutPageTemplateEntryVersionPersistence.
			removeBylayoutPageTemplateEntryId(layoutPageTemplateEntryId);
	}

	public LayoutPageTemplateEntryVersion
		fetchLatestLayoutPageTemplateEntryVersion(
			long layoutPageTemplateEntryId) {

		return layoutPageTemplateEntryVersionPersistence.
			fetchBylayoutPageTemplateEntryId_Last(
				layoutPageTemplateEntryId,
				new LayoutPageTemplateEntryVersionComparator());
	}

	private Layout _addLayout(
			long userId, long groupId, String name, long classNameId,
			long classTypeId, int type, ServiceContext serviceContext)
		throws PortalException {

		Map<Locale, String> titleMap = Collections.singletonMap(
			LocaleUtil.getSiteDefault(), name);

		if (classNameId > 0) {
			AssetRendererFactory assetRendererFactory =
				AssetRendererFactoryRegistryUtil.
					getAssetRendererFactoryByClassNameId(classNameId);

			titleMap = Collections.singletonMap(
				LocaleUtil.getSiteDefault(),
				assetRendererFactory.getTypeName(
					LocaleUtil.getSiteDefault(), classTypeId));
		}

		String layoutType = LayoutConstants.TYPE_ASSET_DISPLAY;

		if (type == LayoutPageTemplateEntryTypeConstants.TYPE_BASIC) {
			layoutType = LayoutConstants.TYPE_CONTENT;
		}

		serviceContext.setAttribute(
			"layout.instanceable.allowed", Boolean.TRUE);

		return _layoutLocalService.addLayout(
			userId, groupId, false, 0, titleMap, titleMap, null, null, null,
			layoutType, StringPool.BLANK, true, true, new HashMap<>(),
			serviceContext);
	}

	@ServiceReference(type = LayoutLocalService.class)
	private LayoutLocalService _layoutLocalService;

}