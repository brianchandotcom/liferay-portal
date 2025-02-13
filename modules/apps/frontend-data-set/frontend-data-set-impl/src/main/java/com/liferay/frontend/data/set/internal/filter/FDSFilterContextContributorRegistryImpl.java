/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.filter;

import com.liferay.frontend.data.set.filter.FDSFilterContextContributor;
import com.liferay.frontend.data.set.filter.FDSFilterContextContributorRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Collections;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Marco Leo
 */
@Component(service = FDSFilterContextContributorRegistry.class)
public class FDSFilterContextContributorRegistryImpl
	implements FDSFilterContextContributorRegistry {

	public FDSFilterContextContributorRegistryImpl(
		ServiceTrackerMap
			<String, List<ServiceWrapper<FDSFilterContextContributor>>>
				serviceTrackerMap) {

		_serviceTrackerMap = serviceTrackerMap;
	}

	@Override
	public List<FDSFilterContextContributor> getFDSFilterContextContributors(
		String fdsFilterType) {

		List<ServiceWrapper<FDSFilterContextContributor>>
			fdsFilterContextContributorServiceWrappers =
				_serviceTrackerMap.getService(fdsFilterType);

		if (fdsFilterContextContributorServiceWrappers == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No frontend data set filter context contributor is " +
						"associated with " + fdsFilterType);
			}

			return Collections.emptyList();
		}

		return TransformUtil.transform(
			fdsFilterContextContributorServiceWrappers,
			fdsFilterContextContributorServiceWrapper ->
				fdsFilterContextContributorServiceWrapper.getService());
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, FDSFilterContextContributor.class,
			"frontend.data.set.filter.type",
			ServiceTrackerCustomizerFactory.
				<FDSFilterContextContributor>serviceWrapper(bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FDSFilterContextContributorRegistryImpl.class);

	private ServiceTrackerMap
		<String, List<ServiceWrapper<FDSFilterContextContributor>>>
			_serviceTrackerMap;

}