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

package com.liferay.commerce.product.internal.type;

import com.liferay.commerce.product.internal.type.comparator.CPTypeServiceWrapperDisplayOrderComparator;
import com.liferay.commerce.product.type.CPType;
import com.liferay.commerce.product.type.CPTypeRenderer;
import com.liferay.commerce.product.type.CPTypeServicesTracker;
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
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Marco Leo
 */
@Component(immediate = true)
public class CPTypeServicesTrackerImpl implements CPTypeServicesTracker {

	@Override
	public CPType getCPType(String name) {
		if (Validator.isNull(name)) {
			return null;
		}

		ServiceWrapper<CPType> cpTypeServiceWrapper =
			_cpTypeServiceTrackerMap.getService(name);

		if (cpTypeServiceWrapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No commerce product type registered with name " + name);
			}

			return null;
		}

		return cpTypeServiceWrapper.getService();
	}

	@Override
	public Set<String> getCPTypeNames() {
		return _cpTypeServiceTrackerMap.keySet();
	}

	@Override
	public CPTypeRenderer getCPTypeRenderer(String name) {
		return _cpTypeRendererServiceTrackerMap.getService(name);
	}

	@Override
	public List<CPType> getCPTypes() {
		List<CPType> cpTypes = new ArrayList<>();

		List<ServiceWrapper<CPType>> cpTypeServiceWrappers =
			ListUtil.fromCollection(_cpTypeServiceTrackerMap.values());

		Collections.sort(
			cpTypeServiceWrappers, _cpTypeServiceWrapperDisplayOrderComparator);

		for (ServiceWrapper<CPType> cpTypeServiceWrapper :
				cpTypeServiceWrappers) {

			cpTypes.add(cpTypeServiceWrapper.getService());
		}

		return Collections.unmodifiableList(cpTypes);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_cpTypeRendererServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, CPTypeRenderer.class,
				"commerce.product.type.name");
		_cpTypeServiceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, CPType.class, "commerce.product.type.name",
			ServiceTrackerCustomizerFactory.<CPType>serviceWrapper(
				bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_cpTypeRendererServiceTrackerMap.close();
		_cpTypeServiceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPTypeServicesTrackerImpl.class);

	private ServiceTrackerMap<String, CPTypeRenderer>
		_cpTypeRendererServiceTrackerMap;
	private ServiceTrackerMap<String, ServiceWrapper<CPType>>
		_cpTypeServiceTrackerMap;
	private final Comparator<ServiceWrapper<CPType>>
		_cpTypeServiceWrapperDisplayOrderComparator =
			new CPTypeServiceWrapperDisplayOrderComparator();

}