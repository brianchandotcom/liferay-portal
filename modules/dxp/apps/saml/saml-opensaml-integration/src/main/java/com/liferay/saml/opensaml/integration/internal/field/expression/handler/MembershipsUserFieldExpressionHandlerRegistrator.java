/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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