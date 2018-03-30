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
import com.liferay.user.associated.data.exporter.UADEntityExporter;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Stream;

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
		return _uadEntityAggregatorServiceTrackerMap.getService(key);
	}

	public Set<String> getUADEntityAggregatorKeySet() {
		return _uadEntityAggregatorServiceTrackerMap.keySet();
	}

	public Collection<UADEntityAggregator> getUADEntityAggregators() {
		return _uadEntityAggregatorServiceTrackerMap.values();
	}

	public UADEntityAnonymizer getUADEntityAnonymizer(String key) {
		return _uadEntityAnonymizerServiceTrackerMap.getService(key);
	}

	public Set<String> getUADEntityAnonymizerKeySet() {
		return _uadEntityAnonymizerServiceTrackerMap.keySet();
	}

	public Collection<UADEntityAnonymizer> getUADEntityAnonymizers() {
		return _uadEntityAnonymizerServiceTrackerMap.values();
	}

	public UADEntityDisplay getUADEntityDisplay(String key) {
		return _uadEntityDisplayServiceTrackerMap.getService(key);
	}

	public Set<String> getUADEntityDisplayKeySet() {
		return _uadEntityDisplayServiceTrackerMap.keySet();
	}

	public Collection<UADEntityDisplay> getUADEntityDisplays() {
		return _uadEntityDisplayServiceTrackerMap.values();
	}

	public Stream<UADEntityDisplay> getUADEntityDisplayStream() {
		return getUADEntityDisplays().stream();
	}

	public UADEntityExporter getUADEntityExporter(String key) {
		return _uadEntityExporterServiceTrackerMap.getService(key);
	}

	public Set<String> getUADEntityExporterKeySet() {
		return _uadEntityExporterServiceTrackerMap.keySet();
	}

	public Collection<UADEntityExporter> getUADEntityExporters() {
		return _uadEntityExporterServiceTrackerMap.values();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_uadEntityAggregatorServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, UADEntityAggregator.class, "model.class.name");
		_uadEntityAnonymizerServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, UADEntityAnonymizer.class, "model.class.name");
		_uadEntityDisplayServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, UADEntityDisplay.class, "model.class.name");
		_uadEntityExporterServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, UADEntityExporter.class, "model.class.name");
	}

	@Deactivate
	protected void deactivate() {
		_uadEntityAggregatorServiceTrackerMap.close();
		_uadEntityAnonymizerServiceTrackerMap.close();
		_uadEntityDisplayServiceTrackerMap.close();
		_uadEntityExporterServiceTrackerMap.close();
	}

	private ServiceTrackerMap<String, UADEntityAggregator>
		_uadEntityAggregatorServiceTrackerMap;
	private ServiceTrackerMap<String, UADEntityAnonymizer>
		_uadEntityAnonymizerServiceTrackerMap;
	private ServiceTrackerMap<String, UADEntityDisplay>
		_uadEntityDisplayServiceTrackerMap;
	private ServiceTrackerMap<String, UADEntityExporter>
		_uadEntityExporterServiceTrackerMap;

}