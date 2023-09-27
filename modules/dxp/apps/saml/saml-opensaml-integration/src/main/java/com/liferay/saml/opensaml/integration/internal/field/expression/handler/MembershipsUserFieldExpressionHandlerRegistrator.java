/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.opensaml.integration.internal.field.expression.handler;

import com.liferay.portal.kernel.feature.flag.FeatureFlagManager;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.saml.opensaml.integration.field.expression.handler.UserFieldExpressionHandler;

import java.util.Dictionary;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Stian Sigvartsen
 */
@Component(service = {})
public class MembershipsUserFieldExpressionHandlerRegistrator {

	@Activate
	protected void activate(BundleContext bundleContext) {
		if (!_featureFlagManager.isEnabled("LPS-180198")) {
			return;
		}

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		for (String key : _serviceReference.getPropertyKeys()) {
			Object value = _serviceReference.getProperty(key);

			properties.put(key, value);
		}

		_serviceRegistration = bundleContext.registerService(
			UserFieldExpressionHandler.class,
			bundleContext.getService(_serviceReference), properties);
	}

	@Deactivate
	protected void deactivate() {
		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();
		}
	}

	@Reference
	private FeatureFlagManager _featureFlagManager;

	@Reference
	private ServiceReference<MembershipsUserFieldExpressionHandler>
		_serviceReference;

	private ServiceRegistration<?> _serviceRegistration;

}