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

package com.liferay.knowledge.base.internal.security.permission.resource;

import com.liferay.knowledge.base.constants.KBConstants;
import com.liferay.knowledge.base.constants.KBFolderConstants;
import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.knowledge.base.service.KBFolderLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionLogic;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.util.PropsValues;

import java.util.Dictionary;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(immediate = true, service = {})
public class KBFolderModelResourcePermissionRegistrar {

	@Activate
	protected void activate(BundleContext bundleContext) {
		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("model.class.name", KBFolder.class.getName());

		_serviceRegistration = bundleContext.registerService(
			ModelResourcePermission.class,
			ModelResourcePermissionFactory.create(
				KBFolder.class, KBFolder::getKbFolderId,
				_kbFolderLocalService::getKBFolder, _portletResourcePermission,
				(modelResourcePermission, consumer) -> {
					if (PropsValues.PERMISSIONS_VIEW_DYNAMIC_INHERITANCE) {
						consumer.accept(
							new KBFolderDynamicInheritanceModelResourcePermissionLogic(
								modelResourcePermission));
					}
				}),
			properties);
	}

	@Deactivate
	protected void deactivate() {
		_serviceRegistration.unregister();
	}

	@Reference
	private KBFolderLocalService _kbFolderLocalService;

	@Reference(
		target = "(resource.name=" + KBConstants.RESOURCE_NAME_ADMIN + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

	private ServiceRegistration<ModelResourcePermission> _serviceRegistration;

	private class KBFolderDynamicInheritanceModelResourcePermissionLogic
		implements ModelResourcePermissionLogic<KBFolder> {

		@Override
		public Boolean contains(
				PermissionChecker permissionChecker, String name,
				KBFolder kbFolder, String actionId)
			throws PortalException {

			if (!ActionKeys.VIEW.equals(actionId)) {
				return null;
			}

			long parentKBFolderId = kbFolder.getParentKBFolderId();

			if (parentKBFolderId ==
					KBFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

				return null;
			}

			kbFolder = _kbFolderLocalService.fetchKBFolder(parentKBFolderId);

			return _kbFolderModelResourcePermission.contains(
				permissionChecker, kbFolder, actionId);
		}

		private KBFolderDynamicInheritanceModelResourcePermissionLogic(
			ModelResourcePermission<KBFolder> modelResourcePermission) {

			_kbFolderModelResourcePermission = modelResourcePermission;
		}

		private final ModelResourcePermission<KBFolder>
			_kbFolderModelResourcePermission;

	}

}