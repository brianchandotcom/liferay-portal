/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.keymanager.internal.configuration.category;

import com.liferay.configuration.admin.category.ConfigurationCategory;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.CompanyConstants;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Tomas Polesovsky
 * @author Christopher Kian
 */
@Component(service = {})
public class KeyManagerConfigurationCategory implements ConfigurationCategory {

	@Override
	public String getBundleSymbolicName() {
		return "com.liferay.keymanager";
	}

	@Override
	public String getCategoryIcon() {
		return "key";
	}

	@Override
	public String getCategoryKey() {
		return "keymanager";
	}

	@Override
	public String getCategorySection() {
		return "security";
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		if (!FeatureFlagManagerUtil.isEnabled(
				CompanyConstants.SYSTEM, "LPD-88411")) {

			return;
		}

		_serviceRegistration = bundleContext.registerService(
			ConfigurationCategory.class, this, null);
	}

	@Deactivate
	protected void deactivate() {
		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();
		}
	}

	private ServiceRegistration<ConfigurationCategory> _serviceRegistration;

}