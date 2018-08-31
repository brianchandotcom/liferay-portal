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

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.sharing.constants.SharingEntryActionKey;
import com.liferay.sharing.security.permission.SharingPermissionChecker;
import com.liferay.sharing.web.internal.display.SharingEntryPermissionDisplay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(immediate = true, service = SharingUtil.class)
public class SharingUtil {

	public List<SharingEntryPermissionDisplay>
		getSharingEntryPermissionDisplays(
			PermissionChecker permissionChecker, long classNameId, long classPK,
			long groupId, Locale locale) {

		List<SharingEntryActionKey> sharingEntryActionKeys = new ArrayList<>();

		SharingPermissionChecker sharingPermissionChecker =
			_serviceTrackerMap.getService(classNameId);

		if (sharingPermissionChecker == null) {
			return Collections.emptyList();
		}

		try {
			if (sharingPermissionChecker.hasPermission(
					permissionChecker, classPK, groupId,
					Arrays.asList(SharingEntryActionKey.VIEW))) {

				sharingEntryActionKeys.add(SharingEntryActionKey.VIEW);
			}
		}
		catch (PortalException pe) {
			_log.error(pe, pe);
		}

		try {
			if (sharingPermissionChecker.hasPermission(
					permissionChecker, classPK, groupId,
					Arrays.asList(SharingEntryActionKey.UPDATE))) {

				sharingEntryActionKeys.add(SharingEntryActionKey.UPDATE);
			}
		}
		catch (PortalException pe) {
			_log.error(pe, pe);
		}

		try {
			if (sharingPermissionChecker.hasPermission(
					permissionChecker, classPK, groupId,
					Arrays.asList(SharingEntryActionKey.ADD_DISCUSSION))) {

				sharingEntryActionKeys.add(
					SharingEntryActionKey.ADD_DISCUSSION);
			}
		}
		catch (PortalException pe) {
			_log.error(pe, pe);
		}

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return SharingEntryPermissionDisplay.getSharingEntryPermissionDisplays(
			sharingEntryActionKeys, resourceBundle);
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	public void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, SharingPermissionChecker.class,
			"(model.class.name=*)",
			(serviceReference, emitter) -> {
				emitter.emit(
					_classNameLocalService.getClassNameId(
						(String)serviceReference.getProperty(
							"model.class.name")));
			});
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(SharingUtil.class);

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference(target = "(bundle.symbolic.name=com.liferay.sharing.web)")
	private ResourceBundleLoader _resourceBundleLoader;

	private ServiceTrackerMap<Long, SharingPermissionChecker>
		_serviceTrackerMap;

}