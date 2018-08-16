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

package com.liferay.sharing.document.library.internal.security.permission.resource;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionLogic;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portlet.documentlibrary.constants.DLConstants;
import com.liferay.sharing.constants.SharingEntryActionKey;
import com.liferay.sharing.service.SharingEntryLocalService;

import java.util.Dictionary;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(immediate = true)
public class SharingEntryDLFileEntryModelResourcePermissionRegistrar {

	public static final String COMPONENT_NAME =
		"com.liferay.sharing.document.library.internal.security.permission." +
			"resource.SharingEntryDLFileEntryModelResourcePermission";

	@Activate
	public void activate(BundleContext bundleContext) {
		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("component.name", COMPONENT_NAME);
		properties.put("model.class.name", DLFileEntry.class.getName());
		properties.put("service.ranking", 100);

		_serviceRegistration = bundleContext.registerService(
			ModelResourcePermission.class,
			ModelResourcePermissionFactory.create(
				DLFileEntry.class, DLFileEntry::getFileEntryId,
				_dlFileEntryLocalService::getDLFileEntry,
				_portletResourcePermission,
				(modelResourcePermission, consumer) -> {
					consumer.accept(
						new SharingDLFileEntryModelPermissionLogic(
							_dlFileEntryModelResourcePermission));
				}),
			properties);
	}

	@Deactivate
	public void deactivate() {
		_serviceRegistration.unregister();
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Reference(
		target = "(&(model.class.name=com.liferay.document.library.kernel.model.DLFileEntry)(!(component.name=" + COMPONENT_NAME + ")))"
	)
	private ModelResourcePermission<DLFileEntry>
		_dlFileEntryModelResourcePermission;

	@Reference(target = "(resource.name=" + DLConstants.RESOURCE_NAME + ")")
	private PortletResourcePermission _portletResourcePermission;

	private ServiceRegistration<ModelResourcePermission> _serviceRegistration;

	@Reference
	private SharingEntryLocalService _sharingEntryLocalService;

	private class SharingDLFileEntryModelPermissionLogic
		implements ModelResourcePermissionLogic<DLFileEntry> {

		@Override
		public Boolean contains(
				PermissionChecker permissionChecker, String name,
				DLFileEntry dlFileEntry, String actionId)
			throws PortalException {

			if (_dlFileEntryModelResourcePermission.contains(
					permissionChecker, dlFileEntry, actionId)) {

				return true;
			}

			if (SharingEntryActionKey.isSupportedActionId(actionId)) {
				SharingEntryActionKey sharingEntryActionKey =
					SharingEntryActionKey.parseFromActionId(actionId);

				long classNameId = _classNameLocalService.getClassNameId(name);

				if (_sharingEntryLocalService.hasSharingPermission(
						permissionChecker.getUserId(), classNameId,
						dlFileEntry.getFileEntryId(), sharingEntryActionKey)) {

					return true;
				}
			}

			return false;
		}

		private SharingDLFileEntryModelPermissionLogic(
			ModelResourcePermission<DLFileEntry>
				dlFileEntryModelResourcePermission) {

			_dlFileEntryModelResourcePermission =
				dlFileEntryModelResourcePermission;
		}

		private final ModelResourcePermission<DLFileEntry>
			_dlFileEntryModelResourcePermission;

	}

}