/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.web.internal.feature.flag;

import com.liferay.application.list.PanelApp;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.exportimport.constants.ExportImportPortletKeys;
import com.liferay.exportimport.web.internal.application.list.CompanyExportPanelApp;
import com.liferay.exportimport.web.internal.application.list.CompanyImportPanelApp;
import com.liferay.portal.kernel.feature.flag.FeatureFlagListener;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.staging.StagingGroupHelper;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = "featureFlagKey=LPD-35914", service = FeatureFlagListener.class
)
public class CompanyExportImportFeatureFlagListener
	implements FeatureFlagListener {

	@Override
	public void onValue(
		long companyId, String featureFlagKey, boolean enabled) {

		if (enabled) {
			if (!_serviceRegistrations.isEmpty()) {
				return;
			}

			_serviceRegistrations.add(
				_bundleContext.registerService(
					PanelApp.class,
					new CompanyExportPanelApp(
						_portal, _exportPortlet, _stagingGroupHelper),
					HashMapDictionaryBuilder.<String, Object>put(
						"panel.app.order", 1300
					).put(
						"panel.category.key",
						PanelCategoryKeys.
							APPLICATIONS_MENU_APPLICATIONS_BATCH_PLANNER
					).build()));
			_serviceRegistrations.add(
				_bundleContext.registerService(
					PanelApp.class,
					new CompanyImportPanelApp(
						_portal, _importPortlet, _stagingGroupHelper),
					HashMapDictionaryBuilder.<String, Object>put(
						"panel.app.order", 1400
					).put(
						"panel.category.key",
						PanelCategoryKeys.
							APPLICATIONS_MENU_APPLICATIONS_BATCH_PLANNER
					).build()));
		}
		else {
			_unregisterServiceRegistrations();
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	@Deactivate
	protected void deactivate() {
		_unregisterServiceRegistrations();
	}

	private void _unregisterServiceRegistrations() {
		_serviceRegistrations.forEach(ServiceRegistration::unregister);
		_serviceRegistrations.clear();
	}

	private BundleContext _bundleContext;

	@Reference(
		target = "(javax.portlet.name=" + ExportImportPortletKeys.EXPORT + ")"
	)
	private Portlet _exportPortlet;

	@Reference(
		target = "(javax.portlet.name=" + ExportImportPortletKeys.IMPORT + ")"
	)
	private Portlet _importPortlet;

	@Reference
	private Portal _portal;

	private final List<ServiceRegistration<PanelApp>> _serviceRegistrations =
		new ArrayList<>();

	@Reference
	private StagingGroupHelper _stagingGroupHelper;

}