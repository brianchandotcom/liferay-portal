/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.discount.internal.validator;

import com.liferay.commerce.discount.validator.CommerceDiscountValidator;
import com.liferay.commerce.discount.validator.CommerceDiscountValidatorRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Riccardo Alberti
 */
@Component(
	enabled = false, immediate = true,
	service = CommerceDiscountValidatorRegistry.class
)
public class CommerceDiscountValidatorRegistryImpl
	implements CommerceDiscountValidatorRegistry {

	@Override
	public CommerceDiscountValidator getCommerceDiscountValidator(String key) {
		if (Validator.isNull(key)) {
			return null;
		}

		ServiceWrapper<CommerceDiscountValidator>
			commerceDiscountValidatorServiceWrapper =
				_serviceTrackerMap.getService(key);

		if (commerceDiscountValidatorServiceWrapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No commerce discount validator registered with key " +
						key);
			}

			return null;
		}

		return commerceDiscountValidatorServiceWrapper.getService();
	}

	@Override
	public List<CommerceDiscountValidator> getCommerceDiscountValidators() {
		return getCommerceDiscountValidators(null);
	}

	@Override
	public List<CommerceDiscountValidator> getCommerceDiscountValidators(
		String... types) {

		List<CommerceDiscountValidator> commerceDiscountValidators =
			new ArrayList<>();

		List<ServiceWrapper<CommerceDiscountValidator>>
			commerceDiscountValidatorServiceWrappers = ListUtil.fromCollection(
				_serviceTrackerMap.values());

		Collections.sort(
			commerceDiscountValidatorServiceWrappers,
			_commerceDiscountValidatorServiceWrapperPriorityComparator);

		for (ServiceWrapper<CommerceDiscountValidator>
				commerceDiscountValidatorServiceWrapper :
					commerceDiscountValidatorServiceWrappers) {

			if ((types == null) || (types.length == 0)) {
				commerceDiscountValidators.add(
					commerceDiscountValidatorServiceWrapper.getService());

				continue;
			}

			Map<String, Object>
				commerceDiscountValidatorServiceWrapperProperties =
					commerceDiscountValidatorServiceWrapper.getProperties();

			Object valueObject =
				commerceDiscountValidatorServiceWrapperProperties.get(
					"commerce.discount.validator.type");

			String value = GetterUtil.getString(valueObject);

			if (ArrayUtil.contains(types, value)) {
				commerceDiscountValidators.add(
					commerceDiscountValidatorServiceWrapper.getService());
			}
		}

		return Collections.unmodifiableList(commerceDiscountValidators);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, CommerceDiscountValidator.class,
			"commerce.discount.validator.key",
			ServiceTrackerCustomizerFactory.
				<CommerceDiscountValidator>serviceWrapper(bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceDiscountValidatorRegistryImpl.class);

	private static final Comparator<ServiceWrapper<CommerceDiscountValidator>>
		_commerceDiscountValidatorServiceWrapperPriorityComparator =
			new CommerceDiscountValidatorServiceWrapperPriorityComparator();

	private ServiceTrackerMap<String, ServiceWrapper<CommerceDiscountValidator>>
		_serviceTrackerMap;

}