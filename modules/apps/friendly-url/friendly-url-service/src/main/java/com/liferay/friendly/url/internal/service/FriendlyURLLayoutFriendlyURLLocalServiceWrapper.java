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

package com.liferay.friendly.url.internal.service;

import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutFriendlyURL;
import com.liferay.portal.kernel.service.LayoutFriendlyURLLocalService;
import com.liferay.portal.kernel.service.LayoutFriendlyURLLocalServiceWrapper;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.util.Portal;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(immediate = true, service = ServiceWrapper.class)
public class FriendlyURLLayoutFriendlyURLLocalServiceWrapper
	extends LayoutFriendlyURLLocalServiceWrapper {

	public FriendlyURLLayoutFriendlyURLLocalServiceWrapper() {
		super(null);
	}

	public FriendlyURLLayoutFriendlyURLLocalServiceWrapper(
		LayoutFriendlyURLLocalService layoutFriendlyURLLocalService) {

		super(layoutFriendlyURLLocalService);
	}

	@Override
	public LayoutFriendlyURL updateLayoutFriendlyURL(
			long userId, long companyId, long groupId, long plid,
			boolean privateLayout, String friendlyURL, String languageId,
			ServiceContext serviceContext)
		throws PortalException {

		LayoutFriendlyURL layoutFriendlyURL = super.updateLayoutFriendlyURL(
			userId, companyId, groupId, plid, privateLayout, friendlyURL,
			languageId, serviceContext);

		_addFriendlyURLEntry(groupId, layoutFriendlyURL, serviceContext);

		return layoutFriendlyURL;
	}

	@Override
	public List<LayoutFriendlyURL> updateLayoutFriendlyURLs(
			long userId, long companyId, long groupId, long plid,
			boolean privateLayout, Map<Locale, String> friendlyURLMap,
			ServiceContext serviceContext)
		throws PortalException {

		List<LayoutFriendlyURL> layoutFriendlyURLS =
			super.updateLayoutFriendlyURLs(
				userId, companyId, groupId, plid, privateLayout, friendlyURLMap,
				serviceContext);

		for (LayoutFriendlyURL layoutFriendlyURL : layoutFriendlyURLS) {
			_addFriendlyURLEntry(groupId, layoutFriendlyURL, serviceContext);
		}

		return layoutFriendlyURLS;
	}

	private void _addFriendlyURLEntry(
			long groupId, LayoutFriendlyURL layoutFriendlyURL,
			ServiceContext serviceContext)
		throws PortalException {

		_friendlyURLEntryLocalService.addFriendlyURLEntry(
			groupId, _portal.getClassNameId(Layout.class),
			layoutFriendlyURL.getPlid(),
			Collections.singletonMap(
				layoutFriendlyURL.getLanguageId(),
				layoutFriendlyURL.getFriendlyURL()),
			serviceContext);
	}

	@Reference
	private FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

	@Reference
	private Portal _portal;

}