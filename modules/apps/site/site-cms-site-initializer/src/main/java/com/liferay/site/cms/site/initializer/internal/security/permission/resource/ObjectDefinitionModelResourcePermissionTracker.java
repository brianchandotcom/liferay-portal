/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.security.permission.resource;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.site.cms.site.initializer.internal.util.CMSRoleUtil;

import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Jürgen Kappler
 */
@Component(service = {})
public class ObjectDefinitionModelResourcePermissionTracker {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTracker = ServiceTrackerFactory.open(
			bundleContext,
			(Class<ModelResourcePermission<?>>)
				(Class<?>)ModelResourcePermission.class,
			new ModelResourcePermissionServiceTrackerCustomizer(bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectDefinitionModelResourcePermissionTracker.class);

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ResourceActionLocalService _resourceActionLocalService;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	private ServiceTracker
		<ModelResourcePermission<?>, ModelResourcePermission<?>>
			_serviceTracker;

	private class ModelResourcePermissionServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<ModelResourcePermission<?>, ModelResourcePermission<?>> {

		public ModelResourcePermissionServiceTrackerCustomizer(
			BundleContext bundleContext) {

			_bundleContext = bundleContext;
		}

		@Override
		public ModelResourcePermission<?> addingService(
			ServiceReference<ModelResourcePermission<?>> serviceReference) {

			_addResourcePermissions(serviceReference);

			return _bundleContext.getService(serviceReference);
		}

		@Override
		public void modifiedService(
			ServiceReference<ModelResourcePermission<?>> serviceReference,
			ModelResourcePermission<?> modelResourcePermission) {
		}

		@Override
		public void removedService(
			ServiceReference<ModelResourcePermission<?>> serviceReference,
			ModelResourcePermission<?> infoItemCapabilitiesProvider) {

			_bundleContext.ungetService(serviceReference);
		}

		private void _addResourcePermissions(
			ServiceReference<ModelResourcePermission<?>> serviceReference) {

			if (!GetterUtil.getBoolean(
					serviceReference.getProperty("com.liferay.object"))) {

				return;
			}

			long companyId = GetterUtil.getLong(
				serviceReference.getProperty("companyId"));

			if (!FeatureFlagManagerUtil.isEnabled(companyId, "LPD-17564")) {
				return;
			}

			String className = GetterUtil.getString(
				serviceReference.getProperty("model.class.name"));

			try {
				ObjectDefinition objectDefinition =
					_objectDefinitionLocalService.
						fetchObjectDefinitionByClassName(companyId, className);

				if (objectDefinition == null) {
					return;
				}

				Role role =
					CMSRoleUtil.getOrAddCMSAdministratorRoleAndPermissions(
						companyId);

				_addResourcePermissions(
					objectDefinition.getResourceName(), companyId, role);
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception);
				}
			}
		}

		private void _addResourcePermissions(
				String name, long companyId, Role role)
			throws PortalException {

			List<ResourceAction> resourceActions =
				_resourceActionLocalService.getResourceActions(name);

			for (ResourceAction resourceAction : resourceActions) {
				_resourcePermissionLocalService.addResourcePermission(
					companyId, name, ResourceConstants.SCOPE_COMPANY,
					String.valueOf(companyId), role.getRoleId(),
					resourceAction.getActionId());
			}
		}

		private final BundleContext _bundleContext;

	}

}