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

package com.liferay.layout.internal.service;

import com.liferay.layout.constants.LayoutConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutLocalServiceWrapper;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.util.Portal;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(immediate = true, service = ServiceWrapper.class)
public class DraftLayoutLocalServiceWrapper extends LayoutLocalServiceWrapper {

	public DraftLayoutLocalServiceWrapper() {
		super(null);
	}

	public DraftLayoutLocalServiceWrapper(
		LayoutLocalService layoutLocalService) {

		super(layoutLocalService);
	}

	@Override
	public Layout addLayout(
			long userId, long groupId, boolean privateLayout,
			long parentLayoutId, Map<Locale, String> nameMap,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			Map<Locale, String> keywordsMap, Map<Locale, String> robotsMap,
			String type, String typeSettings, boolean hidden,
			Map<Locale, String> friendlyURLMap, ServiceContext serviceContext)
		throws PortalException {

		Layout layout = super.addLayout(
			userId, groupId, privateLayout, parentLayoutId, nameMap, titleMap,
			descriptionMap, keywordsMap, robotsMap, type, typeSettings, hidden,
			false, friendlyURLMap, serviceContext);

		if (!Objects.equals(type, LayoutConstants.LAYOUT_TYPE_CONTENT) &&
			!Objects.equals(type, LayoutConstants.LAYOUT_TYPE_ASSET_DISPLAY)) {

			return layout;
		}

		super.addLayout(
			userId, groupId, privateLayout, parentLayoutId,
			_portal.getClassNameId(Layout.class), layout.getPlid(), nameMap,
			titleMap, descriptionMap, keywordsMap, robotsMap, type,
			typeSettings, true, true, friendlyURLMap, serviceContext);

		return layout;
	}

	@Reference
	private Portal _portal;

}