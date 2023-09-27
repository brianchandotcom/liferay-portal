/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = CPContentContributorRegistry.class)
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

	private ServiceTrackerMap<String, ServiceWrapper<CPContentContributor>>
		_serviceTrackerMap;

}