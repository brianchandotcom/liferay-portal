/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.service;

import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.layout.friendly.url.LayoutFriendlyURLEntryHelper;
import com.liferay.portal.kernel.exception.NoSuchLayoutException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.ServiceWrapper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = ServiceWrapper.class)
public class LayoutLocalServiceWrapper
	extends com.liferay.portal.kernel.service.LayoutLocalServiceWrapper {

	@Override
	public Layout fetchLayoutByFriendlyURL(
		long groupId, boolean privateLayout, String friendlyURL) {

		Layout layout = super.fetchLayoutByFriendlyURL(
			groupId, privateLayout, friendlyURL);

		if (layout != null) {
			return layout;
		}

		return _fetchLayoutByFriendlyURL(groupId, privateLayout, friendlyURL);
	}

	@Override
	public Layout getFriendlyURLLayout(
			long groupId, boolean privateLayout, String friendlyURL)
		throws PortalException {

		try {
			return super.getFriendlyURLLayout(
				groupId, privateLayout, friendlyURL);
		}
		catch (NoSuchLayoutException noSuchLayoutException) {
			Layout layout = _fetchLayoutByFriendlyURL(
				groupId, privateLayout, friendlyURL);

			if (layout != null) {
				return layout;
			}

			throw noSuchLayoutException;
		}
	}

	private Layout _fetchLayoutByFriendlyURL(
		long groupId, boolean privateLayout, String friendlyURL) {

		FriendlyURLEntry friendlyURLEntry =
			_friendlyURLEntryLocalService.fetchFriendlyURLEntry(
				groupId,
				_layoutFriendlyURLEntryHelper.getClassNameId(privateLayout),
				friendlyURL);

		if (friendlyURLEntry != null) {
			Layout layout = fetchLayout(friendlyURLEntry.getClassPK());

			if (layout != null) {
				return layout;
			}
		}

		return null;
	}

	@Reference
	private FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

	@Reference
	private LayoutFriendlyURLEntryHelper _layoutFriendlyURLEntryHelper;

}