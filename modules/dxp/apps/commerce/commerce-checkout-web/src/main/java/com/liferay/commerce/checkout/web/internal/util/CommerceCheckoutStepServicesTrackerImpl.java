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

package com.liferay.commerce.checkout.web.internal.util;

import com.liferay.commerce.checkout.web.internal.util.comparator.CommerceCheckoutStepServiceWrapperOrderComparator;
import com.liferay.commerce.checkout.web.util.CommerceCheckoutStep;
import com.liferay.commerce.checkout.web.util.CommerceCheckoutStepServicesTracker;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Marco Leo
 */
@Component(immediate = true)
public class CommerceCheckoutStepServicesTrackerImpl implements CommerceCheckoutStepServicesTracker {

	@Override
	public CommerceCheckoutStep getCommerceCheckoutStep(String name) {
		if (Validator.isNull(name)) {
			return null;
		}

		ServiceWrapper<CommerceCheckoutStep>
			commerceCheckoutStepServiceWrapper =
				_commerceCheckoutStepServiceTrackerMap.getService(name);

		if (commerceCheckoutStepServiceWrapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No commerce checkout step registered with name " + name);
			}

			return null;
		}

		return commerceCheckoutStepServiceWrapper.getService();
	}

	@Override
	public List<CommerceCheckoutStep> getCommerceCheckoutSteps() {
		List<CommerceCheckoutStep> commerceCheckoutSteps = new ArrayList<>();

		List<ServiceWrapper<CommerceCheckoutStep>>
			commerceCheckoutStepServiceWrappers =
				ListUtil.fromCollection(
					_commerceCheckoutStepServiceTrackerMap.values());

		Collections.sort(
			commerceCheckoutStepServiceWrappers,
			_commerceCheckoutStepServiceWrapperDisplayOrderComparator);

		for (ServiceWrapper<CommerceCheckoutStep>
				commerceCheckoutStepServiceWrapper :
					commerceCheckoutStepServiceWrappers) {

			commerceCheckoutSteps.add(
				commerceCheckoutStepServiceWrapper.getService());
		}

		return Collections.unmodifiableList(commerceCheckoutSteps);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_commerceCheckoutStepServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, CommerceCheckoutStep.class,
				"commerce.checkout.step.name",
				ServiceTrackerCustomizerFactory.
					<CommerceCheckoutStep>serviceWrapper(bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_commerceCheckoutStepServiceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceCheckoutStepServicesTrackerImpl.class);


	private ServiceTrackerMap<String, ServiceWrapper<CommerceCheckoutStep>>
		_commerceCheckoutStepServiceTrackerMap;

	private final Comparator<ServiceWrapper<CommerceCheckoutStep>>
		_commerceCheckoutStepServiceWrapperDisplayOrderComparator =
			new CommerceCheckoutStepServiceWrapperOrderComparator();

}