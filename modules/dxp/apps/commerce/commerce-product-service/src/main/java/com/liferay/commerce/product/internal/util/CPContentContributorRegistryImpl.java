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

package com.liferay.commerce.product.internal.util;

import com.liferay.commerce.product.util.CPContentContributor;
import com.liferay.commerce.product.util.CPContentContributorRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(immediate = true)
public class CPContentContributorRegistryImpl
	implements CPContentContributorRegistry {

	@Override
	public CPContentContributor getCPContentContributor(String key) {
		if (Validator.isNull(key)) {
			return null;
		}

		ServiceWrapper<CPContentContributor>
			cpContentContributorServiceWrapper = _serviceTrackerMap.getService(
				key);

		if (cpContentContributorServiceWrapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No commerce product content contributor registered with " +
						"key " + key);
			}

			return null;
		}

		return cpContentContributorServiceWrapper.getService();
	}

	@Override
	public List<CPContentContributor> getCPContentContributors() {
		List<CPContentContributor> cpContentContributors = new ArrayList<>();

		List<ServiceWrapper<CPContentContributor>>
			cpContentContributorServiceWrappers = ListUtil.fromCollection(
				_serviceTrackerMap.values());

		for (ServiceWrapper<CPContentContributor>
				cpContentContributorServiceWrapper :
					cpContentContributorServiceWrappers) {

			cpContentContributors.add(
				cpContentContributorServiceWrapper.getService());
		}

		return Collections.unmodifiableList(cpContentContributors);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, CPContentContributor.class,
			"commerce.product.content.contributor.name",
			ServiceTrackerCustomizerFactory.
				<CPContentContributor>serviceWrapper(bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPContentContributorRegistryImpl.class);

	@Reference
	private Portal _portal;

	private ServiceTrackerMap<String, ServiceWrapper<CPContentContributor>>
		_serviceTrackerMap;

}