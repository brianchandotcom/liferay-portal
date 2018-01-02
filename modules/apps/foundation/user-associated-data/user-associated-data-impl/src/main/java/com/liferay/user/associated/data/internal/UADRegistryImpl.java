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

package com.liferay.user.associated.data.internal;

import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.user.associated.data.aggregator.UADEntityAggregator;
import com.liferay.user.associated.data.anonymizer.UADEntityAnonymizer;
import com.liferay.user.associated.data.entity.UADEntity;
import com.liferay.user.associated.data.exporter.UADEntityExporter;
import com.liferay.user.associated.data.registry.UADRegistry;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
* @author William Newbury
*/
@Component(immediate = true, service = UADRegistry.class)
public class UADRegistryImpl implements UADRegistry {

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

	public void notify(long userId) {
		Map<String, List<UADEntity>> uadAssetsMap = new HashMap<>();

		for (String key : getUADEntityAggregatorKeySet()) {
			UADEntityAggregator uadAggregator = getUADEntityAggregator(key);

			uadAssetsMap.put(key, uadAggregator.getUADEntities(userId));
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_uadEntityAggregatorTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, UADEntityAggregator.class,
				"(model.class.name=*)",
				new UADEntityAggregatorReferenceMapper());

		_uadEntityAnonymizerTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, UADEntityAnonymizer.class,
				"(model.class.name=*)",
				new UADEntityAnonymizerReferenceMapper());

		_uadEntityExporterTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, UADEntityExporter.class, "(model.class.name=*)",
				new UADEntityExporterReferenceMapper());
	}

	private ServiceTrackerMap<String, UADEntityAggregator>
		_uadEntityAggregatorTrackerMap;
	private ServiceTrackerMap<String, UADEntityAnonymizer>
		_uadEntityAnonymizerTrackerMap;
	private ServiceTrackerMap<String, UADEntityExporter>
		_uadEntityExporterTrackerMap;

	private class UADEntityAggregatorReferenceMapper
		implements ServiceReferenceMapper<String, UADEntityAggregator> {

		@Override
		public void map(
			ServiceReference<UADEntityAggregator> serviceReference,
			Emitter<String> emitter) {

			String uadEntityClassName = (String)serviceReference.getProperty(
				"model.class.name");

			if (Validator.isNotNull(uadEntityClassName)) {
				emitter.emit(uadEntityClassName);
			}
		}

	}

	private class UADEntityAnonymizerReferenceMapper
		implements ServiceReferenceMapper<String, UADEntityAnonymizer> {

		@Override
		public void map(
			ServiceReference<UADEntityAnonymizer> serviceReference,
			Emitter<String> emitter) {

			String uadEntityClassName = (String)serviceReference.getProperty(
				"model.class.name");

			if (Validator.isNotNull(uadEntityClassName)) {
				emitter.emit(uadEntityClassName);
			}
		}

	}

	private class UADEntityExporterReferenceMapper
		implements ServiceReferenceMapper<String, UADEntityExporter> {

		@Override
		public void map(
			ServiceReference<UADEntityExporter> serviceReference,
			Emitter<String> emitter) {

			String uadEntityClassName = (String)serviceReference.getProperty(
				"model.class.name");

			if (Validator.isNotNull(uadEntityClassName)) {
				emitter.emit(uadEntityClassName);
			}
		}

	}

}