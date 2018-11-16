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
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.security.permission.SharingEntryAction;
import com.liferay.sharing.security.permission.SharingPermission;
import com.liferay.sharing.service.SharingEntryLocalService;
import com.liferay.sharing.web.internal.display.SharingEntryPermissionDisplay;
import com.liferay.sharing.web.internal.display.SharingEntryPermissionDisplayAction;

import java.util.ArrayList;
import java.util.Arrays;
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

		if (_sharingEntryLocalService.hasSharingPermission(
				sharingEntry, SharingEntryAction.UPDATE)) {

			return SharingEntryPermissionDisplayAction.UPDATE;
		}

		if (_sharingEntryLocalService.hasSharingPermission(
				sharingEntry, SharingEntryAction.ADD_DISCUSSION)) {

			return SharingEntryPermissionDisplayAction.COMMENTS;
		}

		if (_sharingEntryLocalService.hasSharingPermission(
				sharingEntry, SharingEntryAction.VIEW)) {

			return SharingEntryPermissionDisplayAction.VIEW;
		}

		return null;
	}

	public List<SharingEntryPermissionDisplay>
		getSharingEntryPermissionDisplays(
			PermissionChecker permissionChecker, long classNameId, long classPK,
			long groupId, Locale locale) {

		List<SharingEntryAction> sharingEntryActions = new ArrayList<>();

		try {
			if (_sharingPermission.contains(
					permissionChecker, classNameId, classPK, groupId,
					Arrays.asList(SharingEntryAction.VIEW))) {

				sharingEntryActions.add(SharingEntryAction.VIEW);
			}
		}
		catch (PortalException pe) {
			_log.error(pe, pe);
		}

		try {
			if (_sharingPermission.contains(
					permissionChecker, classNameId, classPK, groupId,
					Arrays.asList(SharingEntryAction.UPDATE))) {

				sharingEntryActions.add(SharingEntryAction.UPDATE);
			}
		}
		catch (PortalException pe) {
			_log.error(pe, pe);
		}

		try {
			if (_sharingPermission.contains(
					permissionChecker, classNameId, classPK, groupId,
					Arrays.asList(SharingEntryAction.ADD_DISCUSSION))) {

				sharingEntryActions.add(SharingEntryAction.ADD_DISCUSSION);
			}
		}
		catch (PortalException pe) {
			_log.error(pe, pe);
		}

		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(locale);

		return SharingEntryPermissionDisplay.getSharingEntryPermissionDisplays(
			sharingEntryActions, resourceBundle);
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	public void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	private static final Log _log = LogFactoryUtil.getLog(SharingUtil.class);

	@Reference(target = "(bundle.symbolic.name=com.liferay.sharing.web)")
	private ResourceBundleLoader _resourceBundleLoader;

	@Reference
	private SharingEntryLocalService _sharingEntryLocalService;

	@Reference
	private SharingPermission _sharingPermission;

}