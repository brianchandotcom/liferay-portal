/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.staging.internal.instance.lifecycle;

import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.EveryNodeEveryStartup;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.feature.flag.FeatureFlagListener;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.staging.StagingGroupHelper;
import com.liferay.staging.internal.constants.CompanyGroupConstants;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = PortalInstanceLifecycleListener.class)
public class AddCompanyGroupPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener
	implements EveryNodeEveryStartup {

	@Override
	public void portalInstanceRegistered(Company company) {
		_serviceRegistration = _bundleContext.registerService(
			FeatureFlagListener.class,
			(companyId, featureFlagKey, enabled) -> {
				try {
					Group group = _groupLocalService.fetchFriendlyURLGroup(
						company.getCompanyId(),
						CompanyGroupConstants.FRIENDLY_URL);

					if (group != null) {
						_groupLocalService.deleteGroup(group);
					}

					if (!enabled) {
						return;
					}

					_groupLocalService.addGroup(
						_userLocalService.getGuestUserId(
							company.getCompanyId()),
						GroupConstants.DEFAULT_PARENT_GROUP_ID,
						StagingGroupHelper.class.getName(), companyId,
						GroupConstants.DEFAULT_LIVE_GROUP_ID, null, null,
						GroupConstants.TYPE_SITE_RESTRICTED, true,
						GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION,
						CompanyGroupConstants.FRIENDLY_URL, false, true, null);
				}
				catch (Exception exception) {
					_log.error(exception);
				}
			},
			MapUtil.singletonDictionary("feature.flag.key", "LPD-35914"));
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	@Deactivate
	protected void deactivate() {
		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AddCompanyGroupPortalInstanceLifecycleListener.class);

	private BundleContext _bundleContext;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED)
	private ModuleServiceLifecycle _moduleServiceLifecycle;

	private ServiceRegistration<FeatureFlagListener> _serviceRegistration;

	@Reference
	private UserLocalService _userLocalService;

}