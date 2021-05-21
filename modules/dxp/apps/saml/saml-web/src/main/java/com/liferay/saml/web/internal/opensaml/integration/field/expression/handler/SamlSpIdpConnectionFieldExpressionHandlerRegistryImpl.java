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

package com.liferay.saml.web.internal.opensaml.integration.field.expression.handler;

import com.liferay.saml.opensaml.integration.internal.OrderedServiceTrackerMap;
import com.liferay.saml.opensaml.integration.internal.OrderedServiceTrackerMapFactory;
import com.liferay.saml.opensaml.integration.field.expression.handler.SamlSpIdpConnectionFieldExpressionHandler;
import com.liferay.saml.opensaml.integration.field.expression.handler.registry.SamlSpIdpConnectionFieldExpressionHandlerRegistry;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Stian Sigvartsen
 */
@Component(service = SamlSpIdpConnectionFieldExpressionHandlerRegistry.class)
public class SamlSpIdpConnectionFieldExpressionHandlerRegistryImpl
	implements SamlSpIdpConnectionFieldExpressionHandlerRegistry {

	@Override
	public SamlSpIdpConnectionFieldExpressionHandler getFieldExpressionHandler(
		String prefix) {

		return _samlSpIdpConnectionFieldExpressionHandlers.getService(prefix);
	}

	@Override
	public Set<String> getFieldExpressionHandlerPrefixes() {
		return _samlSpIdpConnectionFieldExpressionHandlers.getServicesKeys();
	}

	@Override
	public List<String> getOrderedFieldExpressionHandlerPrefixes() {
		return _samlSpIdpConnectionFieldExpressionHandlers.
			getOrderedServicesKeys();
	}

	@Override
	public List<Map.Entry<String, SamlSpIdpConnectionFieldExpressionHandler>>
		getOrderedFieldExpressionHandlers() {

		return _samlSpIdpConnectionFieldExpressionHandlers.getOrderedServices();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_samlSpIdpConnectionFieldExpressionHandlers =
			_orderedServiceTrackerMapFactory.create(
				bundleContext, SamlSpIdpConnectionFieldExpressionHandler.class,
				"prefix",
				() -> _defaultSamlSpIdpConnectionFieldExpressionHandler);
	}

	@Deactivate
	protected void deactivate() {
		_samlSpIdpConnectionFieldExpressionHandlers.close();
	}

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY, target = "(default=true)"
	)
	private volatile SamlSpIdpConnectionFieldExpressionHandler
		_defaultSamlSpIdpConnectionFieldExpressionHandler;

	@Reference
	private OrderedServiceTrackerMapFactory _orderedServiceTrackerMapFactory;

	private OrderedServiceTrackerMap<SamlSpIdpConnectionFieldExpressionHandler>
		_samlSpIdpConnectionFieldExpressionHandlers;

}