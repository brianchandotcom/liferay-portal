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

package com.liferay.sharing.web.internal.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.security.permission.SharingEntryAction;
import com.liferay.sharing.security.permission.SharingPermission;
import com.liferay.sharing.web.internal.display.SharingEntryPermissionDisplay;
import com.liferay.sharing.web.internal.display.SharingEntryPermissionDisplayAction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(immediate = true, service = SharingUtil.class)
public class SharingUtil {

	public SharingEntryPermissionDisplayAction
		getSharingEntryPermissionDisplayActionKey(SharingEntry sharingEntry) {

		if (sharingEntry.hasSharingPermission(SharingEntryAction.UPDATE)) {
			return SharingEntryPermissionDisplayAction.UPDATE;
		}

		if (sharingEntry.hasSharingPermission(
				SharingEntryAction.ADD_DISCUSSION)) {

			return SharingEntryPermissionDisplayAction.COMMENTS;
		}

		if (sharingEntry.hasSharingPermission(SharingEntryAction.VIEW)) {
			return SharingEntryPermissionDisplayAction.VIEW;
		}

		return null;
	}

	public List<SharingEntryPermissionDisplay>
		getSharingEntryPermissionDisplays(
			PermissionChecker permissionChecker, long classNameId, long classPK,
			long groupId, Locale locale) {

		List<SharingEntryAction> sharingEntryActions = new ArrayList<>();

		for (SharingEntryAction sharingEntryAction :
				SharingEntryAction.values()) {

			try {
				if (_sharingPermission.contains(
						permissionChecker, classNameId, classPK, groupId,
						Collections.singletonList(sharingEntryAction))) {

					sharingEntryActions.add(sharingEntryAction);
				}
			}
			catch (PortalException pe) {
				_log.error(pe, pe);
			}
		}

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			locale, SharingUtil.class);

		return SharingEntryPermissionDisplay.getSharingEntryPermissionDisplays(
			sharingEntryActions, resourceBundle);
	}

	private static final Log _log = LogFactoryUtil.getLog(SharingUtil.class);

	@Reference
	private SharingPermission _sharingPermission;

}