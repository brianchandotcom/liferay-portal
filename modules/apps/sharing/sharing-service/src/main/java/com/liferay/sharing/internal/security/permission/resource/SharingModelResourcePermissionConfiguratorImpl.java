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

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionLogic;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.security.permission.contributor.PermissionSQLContributor;
import com.liferay.sharing.configuration.SharingConfiguration;
import com.liferay.sharing.configuration.SharingConfigurationFactory;
import com.liferay.sharing.internal.configuration.SharingSystemConfiguration;
import com.liferay.sharing.internal.security.permission.contributor.SharingPermissionSQLContributor;
import com.liferay.sharing.security.permission.SharingEntryAction;
import com.liferay.sharing.security.permission.resource.SharingModelResourcePermissionConfigurator;
import com.liferay.sharing.service.SharingEntryLocalService;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 * @author Sergio González
 * @author Preston Crary
 */
@Component(
	configurationPid = "com.liferay.sharing.internal.configuration.SharingSystemConfiguration",
	immediate = true, service = SharingModelResourcePermissionConfigurator.class
)
public class SharingModelResourcePermissionConfiguratorImpl
	implements SharingModelResourcePermissionConfigurator {

	@Override
	public <T extends GroupedModel> void configure(
		ModelResourcePermission<T> modelResourcePermission,
		Consumer<ModelResourcePermissionLogic<T>> consumer) {

		if (_sharingSystemConfiguration.enabled()) {
			_modelClassNames.add(modelResourcePermission.getModelName());

			_sharingPermissionSQLContributorServiceRegistration.setProperties(
				new HashMapDictionary<String, Object>() {
					{
						put("model.class.name", _modelClassNames.toArray());
					}
				});

			consumer.accept(
				new SharingModelResourcePermissionLogic<>(
					_classNameLocalService.getClassNameId(
						modelResourcePermission.getModelName())));
		}
	}

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_sharingSystemConfiguration = ConfigurableUtil.createConfigurable(
			SharingSystemConfiguration.class, properties);

		_sharingPermissionSQLContributorServiceRegistration =
			bundleContext.registerService(
				PermissionSQLContributor.class,
				new SharingPermissionSQLContributor(
					_classNameLocalService, _groupLocalService,
					_sharingConfigurationFactory),
				new HashMapDictionary<>());
	}

	@Deactivate
	protected void deactivate() {
		_sharingPermissionSQLContributorServiceRegistration.unregister();
	}

	private static Map<String, SharingEntryAction> _getSharingEntryActions() {
		Map<String, SharingEntryAction> sharingEntryActions = new HashMap<>();

		for (SharingEntryAction sharingEntryAction :
				SharingEntryAction.values()) {

			sharingEntryActions.put(
				sharingEntryAction.getActionId(), sharingEntryAction);
		}

		return sharingEntryActions;
	}

	private static final Map<String, SharingEntryAction> _sharingEntryActions =
		_getSharingEntryActions();

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	private final Set<String> _modelClassNames = new HashSet<>();

	@Reference
	private SharingConfigurationFactory _sharingConfigurationFactory;

	@Reference
	private SharingEntryLocalService _sharingEntryLocalService;

	private ServiceRegistration<PermissionSQLContributor>
		_sharingPermissionSQLContributorServiceRegistration;
	private SharingSystemConfiguration _sharingSystemConfiguration;

	private class SharingModelResourcePermissionLogic<T extends GroupedModel>
		implements ModelResourcePermissionLogic<T> {

		@Override
		public Boolean contains(
				PermissionChecker permissionChecker, String name, T model,
				String actionId)
			throws PortalException {

			SharingEntryAction sharingEntryAction = _sharingEntryActions.get(
				actionId);

			if (sharingEntryAction == null) {
				return null;
			}

			long primaryKey = (Long)model.getPrimaryKeyObj();

			if (permissionChecker.hasOwnerPermission(
					model.getCompanyId(), name, primaryKey, model.getUserId(),
					actionId) ||
				permissionChecker.hasPermission(
					model.getGroupId(), name, primaryKey, actionId)) {

				return null;
			}

			if (!_sharingEntryLocalService.hasSharingPermission(
					permissionChecker.getUserId(), _classNameId, primaryKey,
					sharingEntryAction)) {

				return null;
			}

			SharingConfiguration sharingConfiguration =
				_sharingConfigurationFactory.getGroupSharingConfiguration(
					_groupLocalService.getGroup(model.getGroupId()));

			if (sharingConfiguration.isEnabled()) {
				return true;
			}

			return null;
		}

		private SharingModelResourcePermissionLogic(long classNameId) {
			_classNameId = classNameId;
		}

		private final long _classNameId;

	}

}