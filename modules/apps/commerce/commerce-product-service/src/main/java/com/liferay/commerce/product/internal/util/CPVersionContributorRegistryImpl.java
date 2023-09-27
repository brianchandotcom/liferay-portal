/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.util;

import com.liferay.commerce.product.util.CPVersionContributor;
import com.liferay.commerce.product.util.CPVersionContributorRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Ethan Bustad
 */
@Component(service = CPVersionContributorRegistry.class)
public class CPVersionContributorRegistryImpl
	implements CPVersionContributorRegistry {

	@Override
	public CPVersionContributor getCPVersionContributor(String key) {
		if (Validator.isNull(key)) {
			return null;
		}

		ServiceWrapper<CPVersionContributor>
			cpVersionContributorServiceWrapper = _serviceTrackerMap.getService(
				key);

		if (cpVersionContributorServiceWrapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No commerce product version contributor registered with " +
						"key " + key);
			}

			return null;
		}

		return cpVersionContributorServiceWrapper.getService();
	}

	@Override
	public List<CPVersionContributor> getCPVersionContributors() {
		Collection<ServiceWrapper<CPVersionContributor>>
			cpVersionContributorServiceWrappers = _serviceTrackerMap.values();

		List<CPVersionContributor> cpVersionContributors = new ArrayList<>(
			cpVersionContributorServiceWrappers.size());

		for (ServiceWrapper<CPVersionContributor>
				cpVersionContributorServiceWrapper :
					cpVersionContributorServiceWrappers) {

			cpVersionContributors.add(
				cpVersionContributorServiceWrapper.getService());
		}

		return Collections.unmodifiableList(cpVersionContributors);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, CPVersionContributor.class,
			"commerce.product.content.contributor.name",
			ServiceTrackerCustomizerFactory.
				<CPVersionContributor>serviceWrapper(bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPVersionContributorRegistryImpl.class);

	private ServiceTrackerMap<String, ServiceWrapper<CPVersionContributor>>
		_serviceTrackerMap;

}