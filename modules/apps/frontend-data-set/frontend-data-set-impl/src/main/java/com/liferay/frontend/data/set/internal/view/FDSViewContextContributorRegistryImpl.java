/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.view;

import com.liferay.frontend.data.set.view.FDSViewContextContributor;
import com.liferay.frontend.data.set.view.FDSViewContextContributorRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Marco Leo
 */
@Component(service = FDSViewContextContributorRegistry.class)
public class FDSViewContextContributorRegistryImpl
	implements FDSViewContextContributorRegistry {

	@Override
	public List<FDSViewContextContributor> getFDSViewContextContributors(
		String fdsViewName) {

		List<ServiceWrapper<FDSViewContextContributor>>
			fdsViewContextContributorServiceWrappers =
				_serviceTrackerMap.getService(fdsViewName);

		if (fdsViewContextContributorServiceWrappers == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No frontend data set view context contributor is " +
						"associated with " + fdsViewName);
			}

			return Collections.emptyList();
		}

		List<FDSViewContextContributor> fdsViewContextContributors =
			new ArrayList<>();

		for (ServiceWrapper<FDSViewContextContributor>
				fdsViewContextContributorServiceWrapper :
					fdsViewContextContributorServiceWrappers) {

			fdsViewContextContributors.add(
				fdsViewContextContributorServiceWrapper.getService());
		}

		return fdsViewContextContributors;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, FDSViewContextContributor.class,
			"frontend.data.set.view.name",
			ServiceTrackerCustomizerFactory.
				<FDSViewContextContributor>serviceWrapper(bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FDSViewContextContributorRegistryImpl.class);

	private ServiceTrackerMap
		<String, List<ServiceWrapper<FDSViewContextContributor>>>
			_serviceTrackerMap;

}