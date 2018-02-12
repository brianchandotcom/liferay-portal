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

package com.liferay.user.associated.data.web.internal.registry;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.user.associated.data.aggregator.UADEntityAggregator;
import com.liferay.user.associated.data.anonymizer.UADEntityAnonymizer;
import com.liferay.user.associated.data.display.UADEntityDisplay;
import com.liferay.user.associated.data.entity.UADEntity;
import com.liferay.user.associated.data.exporter.UADEntityExporter;

import java.util.Collection;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
* @author William Newbury
*/
@Component(immediate = true, service = UADRegistry.class)
public class UADRegistry {

	public UADEntityAggregator getUADEntityAggregator(String key) {
		return _uadEntityAggregatorTrackerMap.getService(key);
	}

	public UADEntityAggregator getUADEntityAggregator(UADEntity uadEntity) {
		return getUADEntityAggregator(uadEntity.getUADRegistryKey());
	}

	public Set<String> getUADEntityAggregatorKeySet() {
		return _uadEntityAggregatorTrackerMap.keySet();
	}

	public Collection<UADEntityAggregator> getUADEntityAggregators() {
		return _uadEntityAggregatorTrackerMap.values();
	}

	public UADEntityAnonymizer getUADEntityAnonymizer(String key) {
		return _uadEntityAnonymizerTrackerMap.getService(key);
	}

	public UADEntityAnonymizer getUADEntityAnonymizer(UADEntity uadEntity) {
		return getUADEntityAnonymizer(uadEntity.getUADRegistryKey());
	}

	public Set<String> getUADEntityAnonymizerKeySet() {
		return _uadEntityAnonymizerTrackerMap.keySet();
	}

	public Collection<UADEntityAnonymizer> getUADEntityAnonymizers() {
		return _uadEntityAnonymizerTrackerMap.values();
	}

	public UADEntityDisplay getUADEntityDisplay(String key) {
		return _uadEntityDisplayTrackerMap.getService(key);
	}

	public UADEntityDisplay getUADEntityDisplay(UADEntity uadEntity) {
		return getUADEntityDisplay(uadEntity.getUADRegistryKey());
	}

	public Set<String> getUADEntityDisplayKeySet() {
		return _uadEntityDisplayTrackerMap.keySet();
	}

	public UADEntityExporter getUADEntityExporter(String key) {
		return _uadEntityExporterTrackerMap.getService(key);
	}

	public UADEntityExporter getUADEntityExporter(UADEntity uadEntity) {
		return getUADEntityExporter(uadEntity.getUADRegistryKey());
	}

	public Set<String> getUADEntityExporterKeySet() {
		return _uadEntityExporterTrackerMap.keySet();
	}

	public Collection<UADEntityExporter> getUADEntityExporters() {
		return _uadEntityExporterTrackerMap.values();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_uadEntityAggregatorTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, UADEntityAggregator.class, "model.class.name");
		_uadEntityAnonymizerTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, UADEntityAnonymizer.class, "model.class.name");
		_uadEntityDisplayTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, UADEntityDisplay.class, "model.class.name");
		_uadEntityExporterTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, UADEntityExporter.class, "model.class.name");
	}

	@Deactivate
	protected void deactivate() {
		_uadEntityAggregatorTrackerMap.close();
		_uadEntityAnonymizerTrackerMap.close();
		_uadEntityDisplayTrackerMap.close();
		_uadEntityExporterTrackerMap.close();
	}

	private ServiceTrackerMap<String, UADEntityAggregator>
		_uadEntityAggregatorTrackerMap;
	private ServiceTrackerMap<String, UADEntityAnonymizer>
		_uadEntityAnonymizerTrackerMap;
	private ServiceTrackerMap<String, UADEntityDisplay>
		_uadEntityDisplayTrackerMap;
	private ServiceTrackerMap<String, UADEntityExporter>
		_uadEntityExporterTrackerMap;

}