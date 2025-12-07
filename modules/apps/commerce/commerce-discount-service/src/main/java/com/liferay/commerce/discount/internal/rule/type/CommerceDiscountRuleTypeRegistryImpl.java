/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.discount.internal.rule.type;

import com.liferay.commerce.discount.internal.rule.type.comparator.CommerceDiscountRuleTypeOrderComparator;
import com.liferay.commerce.discount.rule.type.CommerceDiscountRuleType;
import com.liferay.commerce.discount.rule.type.CommerceDiscountRuleTypeRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;

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
@Component(service = CommerceDiscountRuleTypeRegistry.class)
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
		List<ServiceWrapper<CommerceDiscountRuleType>>
			commerceDiscountRuleTypeServiceWrappers = ListUtil.fromCollection(
				_serviceTrackerMap.values());

		Collections.sort(
			commerceDiscountRuleTypeServiceWrappers,
			_commerceDiscountRuleTypeServiceWrapperOrderComparator);

		return Collections.unmodifiableList(
			TransformUtil.transform(
				commerceDiscountRuleTypeServiceWrappers,
				commerceDiscountRuleTypeServiceWrapper ->
					commerceDiscountRuleTypeServiceWrapper.getService()));
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
	private ServiceTrackerMap<String, ServiceWrapper<CommerceDiscountRuleType>>
		_serviceTrackerMap;

}