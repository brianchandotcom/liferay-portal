/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.view;

import com.liferay.frontend.data.set.view.FDSView;
import com.liferay.frontend.data.set.view.FDSViewRegistry;
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
@Component(service = FDSViewRegistry.class)
public class FDSViewRegistryImpl implements FDSViewRegistry {

	public FDSViewRegistryImpl() {
	}

	public FDSViewRegistryImpl(
		ServiceTrackerMap<String, List<ServiceWrapper<FDSView>>>
			serviceTrackerMap) {

		_serviceTrackerMap = serviceTrackerMap;
	}

	@Override
	public List<FDSView> getFDSViews(String fdsName) {
		List<ServiceWrapper<FDSView>> fdsViewServiceWrappers =
			_serviceTrackerMap.getService(fdsName);

		if (fdsViewServiceWrappers == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No frontend data set view is associated with " + fdsName);
			}

			return Collections.emptyList();
		}

		return TransformUtil.transform(
			fdsViewServiceWrappers,
			fdsViewServiceWrapper -> fdsViewServiceWrapper.getService());
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, FDSView.class, "frontend.data.set.name",
			ServiceTrackerCustomizerFactory.<FDSView>serviceWrapper(
				bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FDSViewRegistryImpl.class);

	private ServiceTrackerMap<String, List<ServiceWrapper<FDSView>>>
		_serviceTrackerMap;

}