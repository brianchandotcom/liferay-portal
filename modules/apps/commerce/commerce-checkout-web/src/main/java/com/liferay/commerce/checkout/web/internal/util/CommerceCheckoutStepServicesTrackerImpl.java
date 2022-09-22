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
import com.liferay.commerce.util.CommerceCheckoutStep;
import com.liferay.commerce.util.CommerceCheckoutStepServicesTracker;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Marco Leo
 */
@Component(
	enabled = false, immediate = true,
	service = CommerceCheckoutStepServicesTracker.class
)
public class CommerceCheckoutStepServicesTrackerImpl
	implements CommerceCheckoutStepServicesTracker {

	@Override
	public CommerceCheckoutStep getCommerceCheckoutStep(
		String commerceCheckoutStepName) {

		if (Validator.isNull(commerceCheckoutStepName)) {
			return null;
		}

		ServiceTrackerMap<String, ServiceWrapper<CommerceCheckoutStep>>
			commerceCheckoutStepServiceTrackerMap =
				_getCommerceCheckoutStepServiceTrackerMap();

		ServiceWrapper<CommerceCheckoutStep>
			commerceCheckoutStepServiceWrapper =
				commerceCheckoutStepServiceTrackerMap.getService(
					commerceCheckoutStepName);

		if (commerceCheckoutStepServiceWrapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No commerce checkout step registered with name " +
						commerceCheckoutStepName);
			}

			return null;
		}

		return commerceCheckoutStepServiceWrapper.getService();
	}

	@Override
	public List<CommerceCheckoutStep> getCommerceCheckoutSteps(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, boolean onlyActive)
		throws Exception {

		List<CommerceCheckoutStep> commerceCheckoutSteps = new ArrayList<>();

		ServiceTrackerMap<String, ServiceWrapper<CommerceCheckoutStep>>
			commerceCheckoutStepServiceTrackerMap =
				_getCommerceCheckoutStepServiceTrackerMap();

		List<ServiceWrapper<CommerceCheckoutStep>>
			commerceCheckoutStepServiceWrappers = ListUtil.fromCollection(
				commerceCheckoutStepServiceTrackerMap.values());

		Collections.sort(
			commerceCheckoutStepServiceWrappers,
			_commerceCheckoutStepServiceWrapperDisplayOrderComparator);

		for (ServiceWrapper<CommerceCheckoutStep>
				commerceCheckoutStepServiceWrapper :
					commerceCheckoutStepServiceWrappers) {

			CommerceCheckoutStep commerceCheckoutStep =
				commerceCheckoutStepServiceWrapper.getService();

			if (!onlyActive ||
				commerceCheckoutStep.isActive(
					httpServletRequest, httpServletResponse)) {

				commerceCheckoutSteps.add(commerceCheckoutStep);
			}
		}

		return Collections.unmodifiableList(commerceCheckoutSteps);
	}

	@Override
	public CommerceCheckoutStep getNextCommerceCheckoutStep(
			String commerceCheckoutStepName,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		if (Validator.isNull(commerceCheckoutStepName)) {
			return null;
		}

		List<CommerceCheckoutStep> commerceCheckoutSteps =
			getCommerceCheckoutSteps(
				httpServletRequest, httpServletResponse, false);

		CommerceCheckoutStep currentCommerceCheckoutStep =
			getCommerceCheckoutStep(commerceCheckoutStepName);

		for (int commerceCheckoutStepIndex = commerceCheckoutSteps.indexOf(
				currentCommerceCheckoutStep);
			 commerceCheckoutStepIndex < commerceCheckoutSteps.size();
			 commerceCheckoutStepIndex++) {

			if ((commerceCheckoutStepIndex >= 0) &&
				(commerceCheckoutStepIndex <
					(commerceCheckoutSteps.size() - 1))) {

				CommerceCheckoutStep commerceCheckoutStep =
					commerceCheckoutSteps.get(commerceCheckoutStepIndex + 1);

				if (commerceCheckoutStep.isActive(
						httpServletRequest, httpServletResponse)) {

					return commerceCheckoutStep;
				}
			}
		}

		return null;
	}

	@Override
	public CommerceCheckoutStep getPreviousCommerceCheckoutStep(
			String commerceCheckoutStepName,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		if (Validator.isNull(commerceCheckoutStepName)) {
			return null;
		}

		List<CommerceCheckoutStep> commerceCheckoutSteps =
			getCommerceCheckoutSteps(
				httpServletRequest, httpServletResponse, true);

		int commerceCheckoutStepIndex = commerceCheckoutSteps.indexOf(
			getCommerceCheckoutStep(commerceCheckoutStepName));

		if (commerceCheckoutStepIndex > 0) {
			return commerceCheckoutSteps.get(commerceCheckoutStepIndex - 1);
		}

		return null;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	@Deactivate
	protected void deactivate() {
		if (_commerceCheckoutStepServiceTrackerMap != null) {
			_commerceCheckoutStepServiceTrackerMap.close();
		}
	}

	private ServiceTrackerMap<String, ServiceWrapper<CommerceCheckoutStep>>
		_getCommerceCheckoutStepServiceTrackerMap() {

		if (_commerceCheckoutStepServiceTrackerMap == null) {
			_commerceCheckoutStepServiceTrackerMap =
				ServiceTrackerMapFactory.openSingleValueMap(
					_bundleContext, CommerceCheckoutStep.class,
					"commerce.checkout.step.name",
					ServiceTrackerCustomizerFactory.
						<CommerceCheckoutStep>serviceWrapper(_bundleContext));
		}

		return _commerceCheckoutStepServiceTrackerMap;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceCheckoutStepServicesTrackerImpl.class);

	private BundleContext _bundleContext;
	private ServiceTrackerMap<String, ServiceWrapper<CommerceCheckoutStep>>
		_commerceCheckoutStepServiceTrackerMap;
	private final Comparator<ServiceWrapper<CommerceCheckoutStep>>
		_commerceCheckoutStepServiceWrapperDisplayOrderComparator =
			new CommerceCheckoutStepServiceWrapperOrderComparator();

}