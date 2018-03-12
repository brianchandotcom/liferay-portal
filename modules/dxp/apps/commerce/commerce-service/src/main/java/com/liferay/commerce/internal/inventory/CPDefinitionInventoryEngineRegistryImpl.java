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

package com.liferay.commerce.internal.inventory;

import com.liferay.commerce.internal.inventory.comparator.CPDefinitionInventoryEngineServiceWrapperPriorityComparator;
import com.liferay.commerce.inventory.CPDefinitionInventoryEngine;
import com.liferay.commerce.inventory.CPDefinitionInventoryEngineRegistry;
import com.liferay.commerce.model.CPDefinitionInventory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
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

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Alessio Antonio Rendina
 */
@Component(immediate = true)
public class CPDefinitionInventoryEngineRegistryImpl
	implements CPDefinitionInventoryEngineRegistry {

	@Override
	public CPDefinitionInventoryEngine getCPDefinitionInventoryEngine(
		CPDefinitionInventory cpDefinitionInventory) {

		if ((cpDefinitionInventory == null) ||
			Validator.isNull(cpDefinitionInventory.
				getCPDefinitionInventoryEngine())) {

			return getCPDefinitionInventoryEngine(
				CPDefinitionInventoryEngineImpl.KEY);
		}

		return getCPDefinitionInventoryEngine(
			cpDefinitionInventory.getCPDefinitionInventoryEngine());
	}

	@Override
	public CPDefinitionInventoryEngine getCPDefinitionInventoryEngine(
		String key) {

		if (Validator.isNull(key)) {
			return null;
		}

		ServiceTrackerCustomizerFactory.
			ServiceWrapper<CPDefinitionInventoryEngine>
				cpDefinitionInventoryEngineServiceWrapper =
					_serviceTrackerMap.getService(key);

		if (cpDefinitionInventoryEngineServiceWrapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No commerce product definition inventory registered " +
						"with key " + key);
			}

			return null;
		}

		return cpDefinitionInventoryEngineServiceWrapper.getService();
	}

	@Override
	public List<CPDefinitionInventoryEngine> getCPDefinitionInventoryEngines() {
		List<CPDefinitionInventoryEngine> cpDefinitionInventoryEngines =
			new ArrayList<>();

		List<ServiceTrackerCustomizerFactory.
			ServiceWrapper<CPDefinitionInventoryEngine>>
				cpDefinitionInventoryEngineServiceWrappers =
					ListUtil.fromCollection(_serviceTrackerMap.values());

		Collections.sort(
			cpDefinitionInventoryEngineServiceWrappers,
			_cpDefinitionInventoryEngineServiceWrapperPriorityComparator);

		for (ServiceTrackerCustomizerFactory.
				ServiceWrapper<CPDefinitionInventoryEngine>
					cpDefinitionInventoryEngineServiceWrapper :
						cpDefinitionInventoryEngineServiceWrappers) {

			cpDefinitionInventoryEngines.add(
				cpDefinitionInventoryEngineServiceWrapper.getService());
		}

		return Collections.unmodifiableList(cpDefinitionInventoryEngines);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, CPDefinitionInventoryEngine.class,
			"cp.definition.inventory.engine.key",
			ServiceTrackerCustomizerFactory.
				<CPDefinitionInventoryEngine>serviceWrapper(bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPDefinitionInventoryEngineRegistryImpl.class);

	private static final
		Comparator<ServiceTrackerCustomizerFactory.
			ServiceWrapper<CPDefinitionInventoryEngine>>
				_cpDefinitionInventoryEngineServiceWrapperPriorityComparator =
					new CPDefinitionInventoryEngineServiceWrapperPriorityComparator();

	private
		ServiceTrackerMap<String, ServiceTrackerCustomizerFactory.
			ServiceWrapper<CPDefinitionInventoryEngine>> _serviceTrackerMap;

}