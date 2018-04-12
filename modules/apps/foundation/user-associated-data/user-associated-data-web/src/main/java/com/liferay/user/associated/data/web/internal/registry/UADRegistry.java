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
import com.liferay.user.associated.data.aggregator.UADAggregator;
import com.liferay.user.associated.data.anonymizer.UADAnonymizer;
import com.liferay.user.associated.data.display.UADEntityDisplay;
import com.liferay.user.associated.data.exporter.UADExporter;

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

	public UADAggregator getUADAggregator(String key) {
		return _uadAggregatorServiceTrackerMap.getService(key);
	}

	public Set<String> getUADAggregatorKeySet() {
		return _uadAggregatorServiceTrackerMap.keySet();
	}

	public Collection<UADAggregator> getUADAggregators() {
		return _uadAggregatorServiceTrackerMap.values();
	}

	public UADAnonymizer getUADAnonymizer(String key) {
		return _uadAnonymizerServiceTrackerMap.getService(key);
	}

	public Set<String> getUADAnonymizerKeySet() {
		return _uadAnonymizerServiceTrackerMap.keySet();
	}

	public Collection<UADAnonymizer> getUADAnonymizers() {
		return _uadAnonymizerServiceTrackerMap.values();
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

	public UADExporter getUADExporter(String key) {
		return _uadExporterServiceTrackerMap.getService(key);
	}

	public Set<String> getUADExporterKeySet() {
		return _uadExporterServiceTrackerMap.keySet();
	}

	public Collection<UADExporter> getUADExporters() {
		return _uadExporterServiceTrackerMap.values();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_uadAggregatorServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, UADAggregator.class, "model.class.name");
		_uadAnonymizerServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, UADAnonymizer.class, "model.class.name");
		_uadEntityDisplayServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, UADEntityDisplay.class, "model.class.name");
		_uadExporterServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, UADExporter.class, "model.class.name");
	}

	@Deactivate
	protected void deactivate() {
		_uadAggregatorServiceTrackerMap.close();
		_uadAnonymizerServiceTrackerMap.close();
		_uadEntityDisplayServiceTrackerMap.close();
		_uadExporterServiceTrackerMap.close();
	}

	private ServiceTrackerMap<String, UADAggregator>
		_uadAggregatorServiceTrackerMap;
	private ServiceTrackerMap<String, UADAnonymizer>
		_uadAnonymizerServiceTrackerMap;
	private ServiceTrackerMap<String, UADEntityDisplay>
		_uadEntityDisplayServiceTrackerMap;
	private ServiceTrackerMap<String, UADExporter>
		_uadExporterServiceTrackerMap;

}