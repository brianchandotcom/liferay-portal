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

package com.liferay.commerce.internal.cart;

import com.liferay.commerce.cart.CommerceOrderValidator;
import com.liferay.commerce.cart.CommerceOrderValidatorRegistry;
import com.liferay.commerce.cart.CommerceOrderValidatorResult;
import com.liferay.commerce.internal.cart.comparator.CommerceOrderValidatorServiceWrapperPriorityComparator;
import com.liferay.commerce.model.CommerceCart;
import com.liferay.commerce.model.CommerceCartItem;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Alessio Antonio Rendina
 */
@Component(immediate = true)
public class CommerceOrderValidatorRegistryImpl
	implements CommerceOrderValidatorRegistry {

	@Override
	public CommerceOrderValidator getCommerceOrderValidator(String key) {
		if (Validator.isNull(key)) {
			return null;
		}

		ServiceWrapper<CommerceOrderValidator>
			commerceOrderValidatorServiceWrapper =
				_serviceTrackerMap.getService(key);

		if (commerceOrderValidatorServiceWrapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No commerce cart validator registered with key " + key);
			}

			return null;
		}

		return commerceOrderValidatorServiceWrapper.getService();
	}

	@Override
	public Map<Long, List<CommerceOrderValidatorResult>>
			getCommerceOrderValidatorResults(CommerceCart commerceCart)
		throws PortalException {

		if (commerceCart == null) {
			return Collections.emptyMap();
		}

		Map<Long, List<CommerceOrderValidatorResult>>
			commerceOrderValidatorResultMap = new HashMap<>();

		List<CommerceCartItem> commerceCartItems =
			commerceCart.getCommerceCartItems();

		for (CommerceCartItem commerceCartItem : commerceCartItems) {
			List<CommerceOrderValidatorResult>
				filteredCommerceOrderValidatorResults = new ArrayList<>();

			List<CommerceOrderValidatorResult> commerceOrderValidatorResults =
				validate(commerceCartItem);

			for (CommerceOrderValidatorResult commerceOrderValidatorResult :
					commerceOrderValidatorResults) {

				if ((commerceOrderValidatorResult.getCommerceCartItemId() >
						0) &&
					(commerceCartItem.getCommerceCartItemId() ==
						commerceOrderValidatorResult.getCommerceCartItemId())) {

					filteredCommerceOrderValidatorResults.add(
						commerceOrderValidatorResult);
				}
			}

			commerceOrderValidatorResultMap.put(
				commerceCartItem.getCommerceCartItemId(),
				filteredCommerceOrderValidatorResults);
		}

		return commerceOrderValidatorResultMap;
	}

	@Override
	public List<CommerceOrderValidator> getCommerceOrderValidators() {
		List<CommerceOrderValidator> commerceOrderValidators =
			new ArrayList<>();

		List<ServiceWrapper<CommerceOrderValidator>>
			commerceOrderValidatorServiceWrappers = ListUtil.fromCollection(
				_serviceTrackerMap.values());

		Collections.sort(
			commerceOrderValidatorServiceWrappers,
			_commerceOrderValidatorServiceWrapperPriorityComparator);

		for (ServiceWrapper<CommerceOrderValidator>
				commerceOrderValidatorServiceWrapper :
					commerceOrderValidatorServiceWrappers) {

			commerceOrderValidators.add(
				commerceOrderValidatorServiceWrapper.getService());
		}

		return Collections.unmodifiableList(commerceOrderValidators);
	}

	@Override
	public boolean isValid(CommerceCart commerceCart) throws PortalException {
		if (commerceCart == null) {
			return false;
		}

		List<CommerceOrderValidatorResult> commerceOrderValidatorResults =
			new ArrayList<>();

		List<CommerceCartItem> commerceCartItems =
			commerceCart.getCommerceCartItems();

		for (CommerceCartItem commerceCartItem : commerceCartItems) {
			List<CommerceOrderValidatorResult>
				itemCommerceOrderValidatorResults = validate(commerceCartItem);

			for (CommerceOrderValidatorResult commerceOrderValidatorResult :
					itemCommerceOrderValidatorResults) {

				commerceOrderValidatorResults.add(commerceOrderValidatorResult);
			}
		}

		return commerceOrderValidatorResults.isEmpty();
	}

	@Override
	public List<CommerceOrderValidatorResult> validate(
			CommerceCartItem commerceCartItem)
		throws PortalException {

		List<CommerceOrderValidatorResult> commerceOrderValidatorResults =
			new ArrayList<>();

		List<CommerceOrderValidator> commerceOrderValidators =
			getCommerceOrderValidators();

		for (CommerceOrderValidator commerceOrderValidator :
				commerceOrderValidators) {

			CommerceOrderValidatorResult commerceOrderValidatorResult =
				commerceOrderValidator.validate(commerceCartItem);

			if (!commerceOrderValidatorResult.isValid()) {
				commerceOrderValidatorResults.add(commerceOrderValidatorResult);
			}
		}

		return commerceOrderValidatorResults;
	}

	@Override
	public List<CommerceOrderValidatorResult> validate(
			CPInstance cpInstance, int quantity)
		throws PortalException {

		List<CommerceOrderValidatorResult> commerceOrderValidatorResults =
			new ArrayList<>();

		List<CommerceOrderValidator> commerceOrderValidators =
			getCommerceOrderValidators();

		for (CommerceOrderValidator commerceOrderValidator :
				commerceOrderValidators) {

			CommerceOrderValidatorResult commerceOrderValidatorResult =
				commerceOrderValidator.validate(cpInstance, quantity);

			if (!commerceOrderValidatorResult.isValid() &&
				commerceOrderValidatorResult.hasMessageResult()) {

				commerceOrderValidatorResults.add(commerceOrderValidatorResult);
			}
		}

		return commerceOrderValidatorResults;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, CommerceOrderValidator.class,
			"commerce.order.validator.key",
			ServiceTrackerCustomizerFactory.
				<CommerceOrderValidator>serviceWrapper(bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceOrderValidatorRegistryImpl.class);

	private static final Comparator<ServiceWrapper<CommerceOrderValidator>>
		_commerceOrderValidatorServiceWrapperPriorityComparator =
			new CommerceOrderValidatorServiceWrapperPriorityComparator();

	private ServiceTrackerMap<String, ServiceWrapper<CommerceOrderValidator>>
		_serviceTrackerMap;

}