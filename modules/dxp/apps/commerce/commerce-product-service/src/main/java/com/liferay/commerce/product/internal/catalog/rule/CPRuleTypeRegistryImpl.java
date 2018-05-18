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

package com.liferay.commerce.product.internal.catalog.rule;

import com.liferay.commerce.product.catalog.rule.CPRuleType;
import com.liferay.commerce.product.catalog.rule.CPRuleTypeRegistry;
import com.liferay.commerce.product.internal.catalog.rule.comparator.CPRuleTypeOrderComparator;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Alessio Antonio Rendina
 */
@Component(immediate = true)
public class CPRuleTypeRegistryImpl implements CPRuleTypeRegistry {

	@Override
	public CPRuleType getCPRuleType(String key) {
		ServiceWrapper<CPRuleType> cpRuleTypeServiceWrapper =
			_serviceTrackerMap.getService(key);

		if (cpRuleTypeServiceWrapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("No CPRuleType registered with key " + key);
			}

			return null;
		}

		return cpRuleTypeServiceWrapper.getService();
	}

	@Override
	public List<CPRuleType> getCPRuleTypes() {
		List<CPRuleType> cpRuleTypes = new ArrayList<>();

		List<ServiceWrapper<CPRuleType>> cpRuleTypeServiceWrappers =
			ListUtil.fromCollection(_serviceTrackerMap.values());

		Collections.sort(
			cpRuleTypeServiceWrappers,
			_cpRuleTypeServiceWrapperOrderComparator);

		for (ServiceWrapper<CPRuleType> cpRuleTypeServiceWrapper :
				cpRuleTypeServiceWrappers) {

			cpRuleTypes.add(cpRuleTypeServiceWrapper.getService());
		}

		return Collections.unmodifiableList(cpRuleTypes);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, CPRuleType.class, "commerce.product.rule.type.key",
			ServiceTrackerCustomizerFactory.<CPRuleType>serviceWrapper(
				bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPRuleTypeRegistryImpl.class);

	private final Comparator<ServiceWrapper<CPRuleType>>
		_cpRuleTypeServiceWrapperOrderComparator =
			new CPRuleTypeOrderComparator();
	private ServiceTrackerMap<String, ServiceWrapper<CPRuleType>>
		_serviceTrackerMap;

}