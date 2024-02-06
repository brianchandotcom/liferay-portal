/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.token.definition.internal;

import com.liferay.client.extension.type.ThemeCSSCET;
import com.liferay.osgi.service.tracker.collections.EagerServiceTrackerCustomizer;
import com.liferay.portal.kernel.util.Validator;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * @author Anderson Luiz
 * @author Thiago Buarque
 */
public class ThemeCSSClientExtensionServiceTrackerCustomizer
	implements EagerServiceTrackerCustomizer<ThemeCSSCET, ThemeCSSCET> {

	public ThemeCSSClientExtensionServiceTrackerCustomizer(
		BundleContext bundleContext,
		FrontendTokenDefinitionManager frontendTokenDefinitionManager) {

		_bundleContext = bundleContext;
		_frontendTokenDefinitionManager = frontendTokenDefinitionManager;
	}

	@Override
	public ThemeCSSCET addingService(
		ServiceReference<ThemeCSSCET> serviceReference) {

		ThemeCSSCET themeCSSCET = _bundleContext.getService(serviceReference);

		String frontendTokenDefinitionString =
			themeCSSCET.getFrontendTokenDefinition();

		if (!Validator.isBlank(frontendTokenDefinitionString)) {
			_frontendTokenDefinitionManager.addFrontendTokenDefinition(
				themeCSSCET.getCompanyId(),
				themeCSSCET.getExternalReferenceCode(),
				frontendTokenDefinitionString);
		}

		return themeCSSCET;
	}

	@Override
	public void modifiedService(
		ServiceReference<ThemeCSSCET> serviceReference,
		ThemeCSSCET themeCSSCET) {
	}

	@Override
	public void removedService(
		ServiceReference<ThemeCSSCET> serviceReference,
		ThemeCSSCET themeCSSCET) {

		_frontendTokenDefinitionManager.removeFrontendTokenDefinition(
			themeCSSCET.getCompanyId(), themeCSSCET.getExternalReferenceCode());
		_bundleContext.ungetService(serviceReference);
	}

	private final BundleContext _bundleContext;
	private final FrontendTokenDefinitionManager
		_frontendTokenDefinitionManager;

}