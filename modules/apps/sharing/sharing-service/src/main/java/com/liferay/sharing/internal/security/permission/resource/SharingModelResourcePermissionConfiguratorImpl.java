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

package com.liferay.sharing.internal.security.permission.resource;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionLogic;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.sharing.configuration.SharingConfiguration;
import com.liferay.sharing.configuration.SharingConfigurationFactory;
import com.liferay.sharing.security.permission.SharingEntryAction;
import com.liferay.sharing.security.permission.resource.SharingModelResourcePermissionConfigurator;
import com.liferay.sharing.service.SharingEntryLocalService;

import java.util.function.Consumer;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 * @author Sergio González
 * @author Preston Crary
 */
@Component(
	immediate = true, service = SharingModelResourcePermissionConfigurator.class
)
public class SharingModelResourcePermissionConfiguratorImpl
	implements SharingModelResourcePermissionConfigurator {

	@Override
	public <T extends GroupedModel> void configure(
		ModelResourcePermission<T> modelResourcePermission,
		Consumer<ModelResourcePermissionLogic<T>> consumer) {

		consumer.accept(new SharingModelResourcePermissionLogicImpl<>());
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private SharingConfigurationFactory _sharingConfigurationFactory;

	@Reference
	private SharingEntryLocalService _sharingEntryLocalService;

	private class SharingModelResourcePermissionLogicImpl
		<T extends GroupedModel>
			implements ModelResourcePermissionLogic<T> {

		@Override
		public Boolean contains(
				PermissionChecker permissionChecker, String name, T model,
				String actionId)
			throws PortalException {

			SharingConfiguration sharingConfiguration =
				_sharingConfigurationFactory.getGroupSharingConfiguration(
					_groupLocalService.getGroup(model.getGroupId()));

			if (!sharingConfiguration.isEnabled()) {
				return null;
			}

			if (SharingEntryAction.isSupportedActionId(actionId)) {
				SharingEntryAction sharingEntryAction =
					SharingEntryAction.parseFromActionId(actionId);

				long classNameId = _classNameLocalService.getClassNameId(name);

				if (_sharingEntryLocalService.hasSharingPermission(
						permissionChecker.getUserId(), classNameId,
						(Long)model.getPrimaryKeyObj(), sharingEntryAction)) {

					return true;
				}
			}

			return null;
		}

	}

}