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

package com.liferay.commerce.internal.order;

import com.liferay.commerce.internal.order.comparator.CommerceOrderValidatorServiceWrapperPriorityComparator;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.order.CommerceOrderValidator;
import com.liferay.commerce.order.CommerceOrderValidatorRegistry;
import com.liferay.commerce.order.CommerceOrderValidatorResult;
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
					"No commerce order validator registered with key " + key);
			}

			return null;
		}

		return commerceOrderValidatorServiceWrapper.getService();
	}

	@Override
	public Map<Long, List<CommerceOrderValidatorResult>>
			getCommerceOrderValidatorResults(CommerceOrder commerceOrder)
		throws PortalException {

		if (commerceOrder == null) {
			return Collections.emptyMap();
		}

		Map<Long, List<CommerceOrderValidatorResult>>
			commerceOrderValidatorResultMap = new HashMap<>();

		List<CommerceOrderItem> commerceOrderItems =
			commerceOrder.getCommerceOrderItems();

		for (CommerceOrderItem commerceOrderItem : commerceOrderItems) {
			List<CommerceOrderValidatorResult>
				filteredCommerceOrderValidatorResults = new ArrayList<>();

			List<CommerceOrderValidatorResult> commerceOrderValidatorResults =
				validate(commerceOrderItem);

			for (CommerceOrderValidatorResult commerceOrderValidatorResult :
					commerceOrderValidatorResults) {

				if ((commerceOrderValidatorResult.getCommerceOrderItemId() >
						0) &&
					(commerceOrderItem.getCommerceOrderItemId() ==
						commerceOrderValidatorResult.
							getCommerceOrderItemId())) {

					filteredCommerceOrderValidatorResults.add(
						commerceOrderValidatorResult);
				}
			}

			commerceOrderValidatorResultMap.put(
				commerceOrderItem.getCommerceOrderItemId(),
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
	public boolean isValid(CommerceOrder commerceOrder) throws PortalException {
		if (commerceOrder == null) {
			return false;
		}

		List<CommerceOrderValidatorResult> commerceOrderValidatorResults =
			new ArrayList<>();

		List<CommerceOrderItem> commerceOrderItems =
			commerceOrder.getCommerceOrderItems();

		for (CommerceOrderItem commerceOrderItem : commerceOrderItems) {
			List<CommerceOrderValidatorResult>
				itemCommerceOrderValidatorResults = validate(commerceOrderItem);

			for (CommerceOrderValidatorResult commerceOrderValidatorResult :
					itemCommerceOrderValidatorResults) {

				commerceOrderValidatorResults.add(commerceOrderValidatorResult);
			}
		}

		return commerceOrderValidatorResults.isEmpty();
	}

	@Override
	public List<CommerceOrderValidatorResult> validate(
			CommerceOrderItem commerceOrderItem)
		throws PortalException {

		List<CommerceOrderValidatorResult> commerceOrderValidatorResults =
			new ArrayList<>();

		List<CommerceOrderValidator> commerceOrderValidators =
			getCommerceOrderValidators();

		for (CommerceOrderValidator commerceOrderValidator :
				commerceOrderValidators) {

			CommerceOrderValidatorResult commerceOrderValidatorResult =
				commerceOrderValidator.validate(commerceOrderItem);

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