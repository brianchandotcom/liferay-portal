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

package com.liferay.saml.opensaml.integration.internal.field.expression.resolver.registry;

import com.liferay.saml.opensaml.integration.field.expression.resolver.UserFieldExpressionResolver;
import com.liferay.saml.opensaml.integration.field.expression.resolver.registry.UserFieldExpressionResolverRegistry;
import com.liferay.saml.opensaml.integration.internal.service.tracker.collections.OrderedServiceTrackerMap;
import com.liferay.saml.opensaml.integration.internal.service.tracker.collections.OrderedServiceTrackerMapFactory;

import java.util.List;
import java.util.Map;

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
@Component(service = UserFieldExpressionResolverRegistry.class)
public class UserFieldExpressionResolverRegistryImpl
	implements UserFieldExpressionResolverRegistry {

	@Override
	public List<Map.Entry<String, UserFieldExpressionResolver>>
		getOrderedUserFieldExpressionResolvers() {

		return _orderedServiceTrackerMap.getOrderedServices();
	}

	@Override
	public UserFieldExpressionResolver getUserFieldExpressionResolver(
		String key) {

		return _orderedServiceTrackerMap.getService(key);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_orderedServiceTrackerMap = _orderedServiceTrackerMapFactory.create(
			bundleContext, UserFieldExpressionResolver.class, "key",
			() -> _defaultUserFieldExpressionResolver);
	}

	@Deactivate
	protected void deactivate() {
		_orderedServiceTrackerMap.close();
	}

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY, target = "(default=true)"
	)
	private volatile UserFieldExpressionResolver
		_defaultUserFieldExpressionResolver;

	private OrderedServiceTrackerMap<UserFieldExpressionResolver>
		_orderedServiceTrackerMap;

	@Reference
	private OrderedServiceTrackerMapFactory _orderedServiceTrackerMapFactory;

}