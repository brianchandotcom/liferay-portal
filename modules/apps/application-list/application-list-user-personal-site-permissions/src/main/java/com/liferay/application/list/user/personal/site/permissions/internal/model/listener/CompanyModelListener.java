/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.application.list.user.personal.site.permissions.internal.model.listener;

import com.liferay.application.list.PanelApp;
import com.liferay.application.list.PanelAppRegistry;
import com.liferay.application.list.PanelCategoryRegistry;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.application.list.display.context.logic.PanelCategoryHelper;
import com.liferay.osgi.service.tracker.collections.EagerServiceTrackerCustomizer;
import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tomas Polesovsky
 */
@Component(service = ModelListener.class)
public class CompanyModelListener extends BaseModelListener<Company> {

	@Override
	public void onAfterCreate(Company company) {
		TransactionCommitCallbackUtil.registerCallback(
			() -> {
				PanelCategoryHelper panelCategoryHelper =
					new PanelCategoryHelper(
						_panelAppRegistry, _panelCategoryRegistry);

				List<PanelApp> panelApps = panelCategoryHelper.getAllPanelApps(
					PanelCategoryKeys.SITE_ADMINISTRATION);

				List<Portlet> portlets = new ArrayList<>(panelApps.size());

				for (PanelApp panelApp : panelApps) {
					Portlet portlet = _portletLocalService.getPortletById(
						panelApp.getPortletId());

					portlets.add(portlet);
				}

				_initPermissions(company.getCompanyId(), portlets);

				return null;
			});
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, PanelApp.class,
			"(panel.category.key=" + PanelCategoryKeys.SITE_ADMINISTRATION +
				"*)",
			new PropertyServiceReferenceMapper<>("panel.category.key"),
			new PanelAppServiceTrackerCustomizer(bundleContext));
	}

	private Role _getPowerUserRole(long companyId) {
		try {
			return _roleLocalService.getRole(
				companyId, RoleConstants.POWER_USER);
		}
		catch (PortalException portalException) {
			_log.error(
				"Unable to get power user role in company " + companyId,
				portalException);
		}

		return null;
	}

	private Group _getUserPersonalSiteGroup(long companyId) {
		try {
			return _groupLocalService.getUserPersonalSiteGroup(companyId);
		}
		catch (PortalException portalException) {
			_log.error(
				"Unable to get user personal site group in company " +
					companyId,
				portalException);
		}

		return null;
	}

	private void _initPermissions(Company company, Portlet portlet) {
		long companyId = company.getCompanyId();

		Role powerUserRole = _getPowerUserRole(companyId);

		if (powerUserRole == null) {
			return;
		}

		Group userPersonalSiteGroup = _getUserPersonalSiteGroup(companyId);

		if (userPersonalSiteGroup == null) {
			return;
		}

		try {
			if (companyId == portlet.getCompanyId()) {
				_initPermissions(
					companyId, powerUserRole.getRoleId(),
					portlet.getRootPortletId(),
					userPersonalSiteGroup.getGroupId());
			}
		}
		catch (PortalException portalException) {
			_log.error(
				StringBundler.concat(
					"Unable to initialize user personal site permissions for ",
					"portlet ", portlet.getPortletId(), " in company ",
					company.getCompanyId()),
				portalException);
		}
	}

	private void _initPermissions(long companyId, List<Portlet> portlets) {
		Role powerUserRole = _getPowerUserRole(companyId);

		if (powerUserRole == null) {
			return;
		}

		Group userPersonalSiteGroup = _getUserPersonalSiteGroup(companyId);

		if (userPersonalSiteGroup == null) {
			return;
		}

		for (Portlet portlet : portlets) {
			try {
				if (companyId == portlet.getCompanyId()) {
					_initPermissions(
						companyId, powerUserRole.getRoleId(),
						portlet.getRootPortletId(),
						userPersonalSiteGroup.getGroupId());
				}
			}
			catch (PortalException portalException) {
				_log.error(
					StringBundler.concat(
						"Unable to initialize user personal site permissions ",
						"for portlet ", portlet.getPortletId(), " in company ",
						companyId),
					portalException);
			}
		}
	}

	private void _initPermissions(
			long companyId, long powerUserRoleId, String rootPortletId,
			long userPersonalSiteGroupId)
		throws PortalException {

		String primaryKey = String.valueOf(userPersonalSiteGroupId);

		int count = _resourcePermissionLocalService.getResourcePermissionsCount(
			companyId, rootPortletId, ResourceConstants.SCOPE_GROUP,
			primaryKey);

		if (count == 0) {
			List<String> portletActionIds =
				ResourceActionsUtil.getPortletResourceActions(rootPortletId);

			_resourcePermissionLocalService.setResourcePermissions(
				companyId, rootPortletId, ResourceConstants.SCOPE_GROUP,
				String.valueOf(userPersonalSiteGroupId), powerUserRoleId,
				portletActionIds.toArray(new String[0]));
		}

		String modelName = ResourceActionsUtil.getPortletRootModelResource(
			rootPortletId);

		if (Validator.isBlank(modelName)) {
			return;
		}

		count = _resourcePermissionLocalService.getResourcePermissionsCount(
			companyId, modelName, ResourceConstants.SCOPE_GROUP, primaryKey);

		if (count == 0) {
			List<String> modelActionIds =
				ResourceActionsUtil.getModelResourceActions(modelName);

			_resourcePermissionLocalService.setResourcePermissions(
				companyId, modelName, ResourceConstants.SCOPE_GROUP,
				String.valueOf(userPersonalSiteGroupId), powerUserRoleId,
				modelActionIds.toArray(new String[0]));
		}
	}

	private void _initPermissions(Portlet portlet) {
		_companyLocalService.forEachCompany(
			company -> _initPermissions(company, portlet));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CompanyModelListener.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private PanelAppRegistry _panelAppRegistry;

	@Reference
	private PanelCategoryRegistry _panelCategoryRegistry;

	@Reference
	private PortletLocalService _portletLocalService;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	private ServiceTrackerMap<String, PanelApp> _serviceTrackerMap;

	private class PanelAppServiceTrackerCustomizer
		implements EagerServiceTrackerCustomizer<PanelApp, PanelApp> {

		public PanelAppServiceTrackerCustomizer(BundleContext bundleContext) {
			_bundleContext = bundleContext;
		}

		@Override
		public PanelApp addingService(
			ServiceReference<PanelApp> serviceReference) {

			PanelApp panelApp = _bundleContext.getService(serviceReference);

			try {
				Portlet portlet = panelApp.getPortlet();

				if (portlet == null) {
					portlet = _portletLocalService.getPortletById(
						panelApp.getPortletId());
				}

				if (portlet == null) {
					Class<?> panelAppClass = panelApp.getClass();

					_log.error(
						StringBundler.concat(
							"Unable to get portlet ", panelApp.getPortletId(),
							" for panel app ", panelAppClass.getName()));

					return panelApp;
				}

				_initPermissions(portlet);

				return panelApp;
			}
			catch (Throwable throwable) {
				_bundleContext.ungetService(serviceReference);

				throw throwable;
			}
		}

		@Override
		public void modifiedService(
			ServiceReference<PanelApp> serviceReference, PanelApp panelApp) {
		}

		@Override
		public void removedService(
			ServiceReference<PanelApp> serviceReference, PanelApp panelApp) {

			_bundleContext.ungetService(serviceReference);
		}

		private final BundleContext _bundleContext;

	}

}