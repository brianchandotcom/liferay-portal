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

import com.liferay.commerce.discount.internal.rule.type.comparator.CommerceDiscountRuleTypeOrderComparator;
import com.liferay.commerce.discount.rule.type.CommerceDiscountRuleType;
import com.liferay.commerce.discount.rule.type.CommerceDiscountRuleTypeRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Alessio Antonio Rendina
 */
@Component(immediate = true)
public class CommerceDiscountRuleTypeRegistryImpl
	implements CommerceDiscountRuleTypeRegistry {

	@Override
	public CommerceDiscountRuleType getCommerceDiscountRuleType(String key) {
		ServiceWrapper<CommerceDiscountRuleType>
			commerceDiscountRuleTypeServiceWrapper =
				_serviceTrackerMap.getService(key);

		if (commerceDiscountRuleTypeServiceWrapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No CommerceDiscountRuleType registered with key " + key);
			}

			return null;
		}

		return commerceDiscountRuleTypeServiceWrapper.getService();
	}

	@Override
	public List<CommerceDiscountRuleType> getCommerceDiscountRuleTypes() {
		List<CommerceDiscountRuleType> commerceDiscountRuleTypes =
			new ArrayList<>();

		List<ServiceWrapper<CommerceDiscountRuleType>>
			commerceDiscountRuleTypeServiceWrappers = ListUtil.fromCollection(
				_serviceTrackerMap.values());

		Collections.sort(
			commerceDiscountRuleTypeServiceWrappers,
			_commerceDiscountRuleTypeServiceWrapperOrderComparator);

		for (ServiceWrapper<CommerceDiscountRuleType>
				commerceDiscountRuleTypeServiceWrapper :
					commerceDiscountRuleTypeServiceWrappers) {

			commerceDiscountRuleTypes.add(
				commerceDiscountRuleTypeServiceWrapper.getService());
		}

		return Collections.unmodifiableList(commerceDiscountRuleTypes);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, CommerceDiscountRuleType.class,
			"commerce.discount.rule.type.key",
			ServiceTrackerCustomizerFactory.
				<CommerceDiscountRuleType>serviceWrapper(bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceDiscountRuleTypeRegistryImpl.class);

	private final Comparator<ServiceWrapper<CommerceDiscountRuleType>>
		_commerceDiscountRuleTypeServiceWrapperOrderComparator =
			new CommerceDiscountRuleTypeOrderComparator();
	private ServiceTrackerMap<String,
		ServiceWrapper<CommerceDiscountRuleType>> _serviceTrackerMap;

}