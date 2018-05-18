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

package com.liferay.commerce.discount.internal.rule.type;

import com.liferay.commerce.discount.rule.type.CommerceDiscountRuleTypeJSPContributor;
import com.liferay.commerce.discount.rule.type.CommerceDiscountRuleTypeJSPContributorRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	service = CommerceDiscountRuleTypeJSPContributorRegistry.class
)
public class CommerceDiscountRuleTypeJSPContributorRegistryImpl
	implements CommerceDiscountRuleTypeJSPContributorRegistry {

	@Override
	public CommerceDiscountRuleTypeJSPContributor
		getCommerceDiscountRuleTypeJSPContributor(String key) {

		return _serviceTrackerMap.getService(key);
	}

	@Override
	public List<CommerceDiscountRuleTypeJSPContributor>
		getCommerceDiscountRuleTypeJSPContributors() {

		List<CommerceDiscountRuleTypeJSPContributor>
			commerceDiscountRuleTypeJSPContributors = new ArrayList<>();

		for (String key : _serviceTrackerMap.keySet()) {
			commerceDiscountRuleTypeJSPContributors.add(
				_serviceTrackerMap.getService(key));
		}

		return Collections.unmodifiableList(
			commerceDiscountRuleTypeJSPContributors);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, CommerceDiscountRuleTypeJSPContributor.class,
			"commerce.discount.rule.type.jsp.contributor.key");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap<String, CommerceDiscountRuleTypeJSPContributor>
		_serviceTrackerMap;

}