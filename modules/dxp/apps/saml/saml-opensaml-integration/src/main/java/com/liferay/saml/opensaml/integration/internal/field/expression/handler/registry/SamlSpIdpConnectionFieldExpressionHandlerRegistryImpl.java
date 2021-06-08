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

package com.liferay.saml.opensaml.integration.internal.field.expression.handler.registry;

import com.liferay.saml.opensaml.integration.field.expression.handler.SamlSpIdpConnectionFieldExpressionHandler;
import com.liferay.saml.opensaml.integration.field.expression.handler.registry.SamlSpIdpConnectionFieldExpressionHandlerRegistry;
import com.liferay.saml.opensaml.integration.internal.service.tracker.collections.OrderedServiceTrackerMap;
import com.liferay.saml.opensaml.integration.internal.service.tracker.collections.OrderedServiceTrackerMapFactory;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Stian Sigvartsen
 */
@Component(service = SamlSpIdpConnectionFieldExpressionHandlerRegistry.class)
public class SamlSpIdpConnectionFieldExpressionHandlerRegistryImpl
	implements SamlSpIdpConnectionFieldExpressionHandlerRegistry {

	@Override
	public SamlSpIdpConnectionFieldExpressionHandler getFieldExpressionHandler(
		String prefix) {

		return _orderedServiceTrackerMap.getService(prefix);
	}

	@Override
	public Set<String> getFieldExpressionHandlerPrefixes() {
		return _orderedServiceTrackerMap.getServicesKeys();
	}

	@Override
	public List<String> getOrderedFieldExpressionHandlerPrefixes() {
		return _orderedServiceTrackerMap.getOrderedServicesKeys();
	}

	@Override
	public List<Map.Entry<String, SamlSpIdpConnectionFieldExpressionHandler>>
		getOrderedFieldExpressionHandlers() {

		return _orderedServiceTrackerMap.getOrderedServices();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_orderedServiceTrackerMap = _orderedServiceTrackerMapFactory.create(
			bundleContext, SamlSpIdpConnectionFieldExpressionHandler.class,
			"prefix");
	}

	@Deactivate
	protected void deactivate() {
		_orderedServiceTrackerMap.close();
	}

	private OrderedServiceTrackerMap<SamlSpIdpConnectionFieldExpressionHandler>
		_orderedServiceTrackerMap;

	@Reference
	private OrderedServiceTrackerMapFactory _orderedServiceTrackerMapFactory;

}