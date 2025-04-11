/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.importmaps.extender.internal.osgi.util.tracker;

import com.liferay.frontend.js.importmaps.extender.DynamicJSImportMapsContributor;
import com.liferay.frontend.js.importmaps.extender.internal.servlet.taglib.JSImportMapsCache;
import com.liferay.frontend.js.importmaps.extender.internal.servlet.taglib.JSImportMapsRegistration;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Iván Zaera Avellón
 */
public class DynamicJSImportMapsContributorServiceTrackerCustomizer
	implements ServiceTrackerCustomizer
		<DynamicJSImportMapsContributor, JSImportMapsRegistration> {

	public DynamicJSImportMapsContributorServiceTrackerCustomizer(
		BundleContext bundleContext, JSImportMapsCache jsImportMapsCache) {

		_bundleContext = bundleContext;
		_jsImportMapsCache = jsImportMapsCache;
	}

	@Override
	public JSImportMapsRegistration addingService(
		ServiceReference<DynamicJSImportMapsContributor> serviceReference) {

		Long companyId = (Long)serviceReference.getProperty(
			"com.liferay.frontend.js.importmaps.company.id");

		if (companyId == null) {
			companyId = Long.valueOf(JSImportMapsCache.COMPANY_ID_ALL);
		}

		DynamicJSImportMapsContributor dynamicJSImportMapsContributor =
			_bundleContext.getService(serviceReference);

		return _jsImportMapsCache.register(
			companyId, dynamicJSImportMapsContributor);
	}

	@Override
	public void modifiedService(
		ServiceReference serviceReference,
		JSImportMapsRegistration jsImportMapsRegistration) {
	}

	@Override
	public void removedService(
		ServiceReference serviceReference,
		JSImportMapsRegistration jsImportMapsRegistration) {

		jsImportMapsRegistration.unregister();

		_bundleContext.ungetService(serviceReference);
	}

	private final BundleContext _bundleContext;
	private final JSImportMapsCache _jsImportMapsCache;

}