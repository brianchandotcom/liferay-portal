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

package com.liferay.user.associated.data.internal.registry;

import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.user.associated.data.aggregator.UADEntityAggregator;
import com.liferay.user.associated.data.anonymizer.UADEntityAnonymizer;
import com.liferay.user.associated.data.display.UADEntityDisplay;
import com.liferay.user.associated.data.entity.UADEntity;
import com.liferay.user.associated.data.exporter.UADEntityExporter;
import com.liferay.user.associated.data.registry.UADRegistry;
import com.liferay.user.associated.data.util.UADBundleComposite;
import com.liferay.user.associated.data.util.UADEntityTypeComposite;

import java.util.ArrayList;
import java.util.Collection;
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

	@Override
	public List<UADBundleComposite> getUADBundleComposites(long userId) {
		Map<String, List<UADEntityTypeComposite>> uadEntityTypeCompositesMap =
			new HashMap<>();

		for (String key : getUADEntityAggregatorKeySet()) {
			UADEntityAggregator uadAggregator = getUADEntityAggregator(key);

			String bundleId = uadAggregator.getBundleId();

			List<UADEntity> uadEntities = uadAggregator.getUADEntities(userId);

			UADEntityDisplay uadEntityDisplay = getUADEntityDisplay(key);

			UADEntityTypeComposite uadEntityTypeComposite =
				new UADEntityTypeComposite(
					userId, key, uadEntityDisplay, uadEntities);

			List<UADEntityTypeComposite> uadEntityTypeComposites =
				uadEntityTypeCompositesMap.getOrDefault(
					bundleId, new ArrayList<UADEntityTypeComposite>());

			uadEntityTypeComposites.add(uadEntityTypeComposite);

			uadEntityTypeCompositesMap.put(bundleId, uadEntityTypeComposites);
		}

		List<UADBundleComposite> uadBundleComposites = new ArrayList<>();

		for (Map.Entry<String, List<UADEntityTypeComposite>> entry :
				uadEntityTypeCompositesMap.entrySet()) {

			String bundleId = entry.getKey();

			List<UADEntityTypeComposite> uadEntityTypeComposites =
				entry.getValue();

			UADBundleComposite uadBundleComposite = new UADBundleComposite(
				userId, bundleId, uadEntityTypeComposites);

			uadBundleComposites.add(uadBundleComposite);
		}

		return uadBundleComposites;
	}

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

	@Override
	public UADEntityDisplay getUADEntityDisplay(String key) {
		return _uadEntityDisplayTrackerMap.getService(key);
	}

	@Override
	public UADEntityDisplay getUADEntityDisplay(UADEntity uadEntity) {
		return getUADEntityDisplay(uadEntity.getUADRegistryKey());
	}

	@Override
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

	@Override
	public UADEntityTypeComposite getUADEntityTypeComposite(
		long userId, String key) {

		UADEntityAggregator uadAggregator = getUADEntityAggregator(key);

		List<UADEntity> uadEntities = uadAggregator.getUADEntities(userId);

		UADEntityDisplay uadEntityDisplay = getUADEntityDisplay(key);

		return new UADEntityTypeComposite(
			userId, key, uadEntityDisplay, uadEntities);
	}

	@Override
	public List<UADEntityTypeComposite> getUADEntityTypeComposites(
		long userId, String bundleId) {

		List<UADEntityTypeComposite> uadEntityTypeComposites =
			new ArrayList<>();

		for (String key : getUADEntityAggregatorKeySet()) {
			UADEntityAggregator uadAggregator = getUADEntityAggregator(key);

			if (bundleId.equals(uadAggregator.getBundleId())) {
				List<UADEntity> uadEntities = uadAggregator.getUADEntities(
					userId);

				UADEntityDisplay uadEntityDisplay = getUADEntityDisplay(key);

				UADEntityTypeComposite uadEntityTypeComposite =
					new UADEntityTypeComposite(
						userId, key, uadEntityDisplay, uadEntities);

				uadEntityTypeComposites.add(uadEntityTypeComposite);
			}
		}

		return uadEntityTypeComposites;
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

		_uadEntityDisplayTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, UADEntityDisplay.class, "(model.class.name=*)",
				new UADEntityDisplayReferenceMapper());

		_uadEntityExporterTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, UADEntityExporter.class, "(model.class.name=*)",
				new UADEntityExporterReferenceMapper());
	}

	private ServiceTrackerMap<String, UADEntityAggregator>
		_uadEntityAggregatorTrackerMap;
	private ServiceTrackerMap<String, UADEntityAnonymizer>
		_uadEntityAnonymizerTrackerMap;
	private ServiceTrackerMap<String, UADEntityDisplay>
		_uadEntityDisplayTrackerMap;
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

	private class UADEntityDisplayReferenceMapper
		implements ServiceReferenceMapper<String, UADEntityDisplay> {

		@Override
		public void map(
			ServiceReference<UADEntityDisplay> serviceReference,
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