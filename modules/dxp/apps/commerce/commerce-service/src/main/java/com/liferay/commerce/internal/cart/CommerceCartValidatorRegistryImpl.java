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

import com.liferay.commerce.cart.CommerceCartValidator;
import com.liferay.commerce.cart.CommerceCartValidatorRegistry;
import com.liferay.commerce.cart.CommerceCartValidatorResult;
import com.liferay.commerce.internal.cart.comparator.CommerceCartValidatorServiceWrapperPriorityComparator;
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
public class CommerceCartValidatorRegistryImpl
	implements CommerceCartValidatorRegistry {

	@Override
	public CommerceCartValidator getCommerceCartValidator(String key) {
		if (Validator.isNull(key)) {
			return null;
		}

		ServiceWrapper<CommerceCartValidator>
			commerceCartValidatorServiceWrapper = _serviceTrackerMap.getService(
				key);

		if (commerceCartValidatorServiceWrapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No commerce cart validator registered with key " + key);
			}

			return null;
		}

		return commerceCartValidatorServiceWrapper.getService();
	}

	@Override
	public Map<Long, List<CommerceCartValidatorResult>>
			getCommerceCartValidatorResults(CommerceCart commerceCart)
		throws PortalException {

		if (commerceCart == null) {
			return Collections.emptyMap();
		}

		Map<Long, List<CommerceCartValidatorResult>>
			commerceCartValidatorResultMap = new HashMap<>();

		List<CommerceCartItem> commerceCartItems =
			commerceCart.getCommerceCartItems();

		for (CommerceCartItem commerceCartItem : commerceCartItems) {
			List<CommerceCartValidatorResult>
				filteredCommerceCartValidatorResults = new ArrayList<>();

			List<CommerceCartValidatorResult> commerceCartValidatorResults =
				validate(commerceCartItem);

			for (CommerceCartValidatorResult commerceCartValidatorResult :
					commerceCartValidatorResults) {

				if ((commerceCartValidatorResult.getCommerceCartItemId() > 0) &&
					(commerceCartItem.getCommerceCartItemId() ==
						commerceCartValidatorResult.getCommerceCartItemId())) {

					filteredCommerceCartValidatorResults.add(
						commerceCartValidatorResult);
				}
			}

			commerceCartValidatorResultMap.put(
				commerceCartItem.getCommerceCartItemId(),
				filteredCommerceCartValidatorResults);
		}

		return commerceCartValidatorResultMap;
	}

	@Override
	public List<CommerceCartValidator> getCommerceCartValidators() {
		List<CommerceCartValidator> commerceCartValidators = new ArrayList<>();

		List<ServiceWrapper<CommerceCartValidator>>
			commerceCartValidatorServiceWrappers = ListUtil.fromCollection(
				_serviceTrackerMap.values());

		Collections.sort(
			commerceCartValidatorServiceWrappers,
			_commerceCartValidatorServiceWrapperPriorityComparator);

		for (ServiceWrapper<CommerceCartValidator>
				commerceCartValidatorServiceWrapper :
					commerceCartValidatorServiceWrappers) {

			commerceCartValidators.add(
				commerceCartValidatorServiceWrapper.getService());
		}

		return Collections.unmodifiableList(commerceCartValidators);
	}

	@Override
	public boolean isValid(CommerceCart commerceCart) throws PortalException {
		if (commerceCart == null) {
			return false;
		}

		List<CommerceCartValidatorResult> commerceCartValidatorResults =
			new ArrayList<>();

		List<CommerceCartItem> commerceCartItems =
			commerceCart.getCommerceCartItems();

		for (CommerceCartItem commerceCartItem : commerceCartItems) {
			List<CommerceCartValidatorResult> itemCommerceCartValidatorResults =
				validate(commerceCartItem);

			for (CommerceCartValidatorResult commerceCartValidatorResult :
					itemCommerceCartValidatorResults) {

				commerceCartValidatorResults.add(commerceCartValidatorResult);
			}
		}

		return commerceCartValidatorResults.isEmpty();
	}

	@Override
	public List<CommerceCartValidatorResult> validate(
			CommerceCartItem commerceCartItem)
		throws PortalException {

		List<CommerceCartValidatorResult> commerceCartValidatorResults =
			new ArrayList<>();

		List<CommerceCartValidator> commerceCartValidators =
			getCommerceCartValidators();

		for (CommerceCartValidator commerceCartValidator :
				commerceCartValidators) {

			CommerceCartValidatorResult commerceCartValidatorResult =
				commerceCartValidator.validate(commerceCartItem);

			if (!commerceCartValidatorResult.isValid()) {
				commerceCartValidatorResults.add(commerceCartValidatorResult);
			}
		}

		return commerceCartValidatorResults;
	}

	@Override
	public List<CommerceCartValidatorResult> validate(
			CPInstance cpInstance, CommerceCart commerceCart, int quantity)
		throws PortalException {

		List<CommerceCartValidatorResult> commerceCartValidatorResults =
			new ArrayList<>();

		List<CommerceCartValidator> commerceCartValidators =
			getCommerceCartValidators();

		for (CommerceCartValidator commerceCartValidator :
				commerceCartValidators) {

			CommerceCartValidatorResult commerceCartValidatorResult =
				commerceCartValidator.validate(cpInstance, quantity);

			if (!commerceCartValidatorResult.isValid() &&
				commerceCartValidatorResult.hasMessageResult()) {

				commerceCartValidatorResults.add(commerceCartValidatorResult);
			}
		}

		return commerceCartValidatorResults;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, CommerceCartValidator.class,
			"commerce.cart.validator.key",
			ServiceTrackerCustomizerFactory.
				<CommerceCartValidator>serviceWrapper(bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceCartValidatorRegistryImpl.class);

	private static final Comparator<ServiceWrapper<CommerceCartValidator>>
		_commerceCartValidatorServiceWrapperPriorityComparator =
			new CommerceCartValidatorServiceWrapperPriorityComparator();

	private ServiceTrackerMap<String, ServiceWrapper<CommerceCartValidator>>
		_serviceTrackerMap;

}