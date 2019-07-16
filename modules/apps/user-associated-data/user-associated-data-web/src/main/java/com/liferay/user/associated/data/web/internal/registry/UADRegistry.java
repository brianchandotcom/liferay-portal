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

import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapperFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.user.associated.data.anonymizer.UADAnonymizer;
import com.liferay.user.associated.data.component.UADComponent;
import com.liferay.user.associated.data.display.UADDisplay;
import com.liferay.user.associated.data.display.UADHierarchyDeclaration;
import com.liferay.user.associated.data.exporter.UADExporter;
import com.liferay.user.associated.data.web.internal.display.UADHierarchyDisplay;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author William Newbury
 */
@Component(immediate = true, service = UADRegistry.class)
public class UADRegistry {

	public List<UADAnonymizer> getApplicationUADAnonymizers(
		String applicationKey) {

		return _bundleUADAnonymizerServiceTrackerMap.getService(applicationKey);
	}

	public Set<String> getApplicationUADAnonymizersKeySet() {
		return _bundleUADAnonymizerServiceTrackerMap.keySet();
	}

	public Stream<UADAnonymizer> getApplicationUADAnonymizerStream(
		String applicationKey) {

		List<UADAnonymizer> uadAnonymizerList = getApplicationUADAnonymizers(
			applicationKey);

		return uadAnonymizerList.stream();
	}

	public List<UADDisplay> getApplicationUADDisplays(String applicationKey) {
		return _bundleUADDisplayServiceTrackerMap.getService(applicationKey);
	}

	public Set<String> getApplicationUADDisplaysKeySet() {
		return _bundleUADDisplayServiceTrackerMap.keySet();
	}

	public Stream<UADDisplay> getApplicationUADDisplayStream(
		String applicationKey) {

		List<UADDisplay> uadDisplayList = getApplicationUADDisplays(
			applicationKey);

		if (uadDisplayList == null) {
			return Stream.empty();
		}

		return uadDisplayList.stream();
	}

	public List<UADExporter> getApplicationUADExporters(String applicationKey) {
		return _bundleUADExporterServiceTrackerMap.getService(applicationKey);
	}

	public Set<String> getApplicationUADExportersKeySet() {
		return _bundleUADDisplayServiceTrackerMap.keySet();
	}

	public List<UADAnonymizer> getNonreviewableApplicationUADAnonymizers(
		String applicationKey) {

		return new ArrayList<>(
			_getNonreviewableUADAnonymizers(
				getApplicationUADAnonymizers(applicationKey),
				getApplicationUADDisplayStream(applicationKey)));
	}

	public Collection<UADAnonymizer> getNonreviewableUADAnonymizers() {
		return _getNonreviewableUADAnonymizers(
			getUADAnonymizers(), getUADDisplayStream());
	}

	public Stream<UADAnonymizer> getNonreviewableUADAnonymizerStream() {
		return getNonreviewableUADAnonymizers().stream();
	}

	public UADAnonymizer getUADAnonymizer(String key) {
		return _uadAnonymizerServiceTrackerMap.getService(key);
	}

	public Collection<UADAnonymizer> getUADAnonymizers() {
		return _uadAnonymizerServiceTrackerMap.values();
	}

	public Stream<UADAnonymizer> getUADAnonymizerStream() {
		return getUADAnonymizers().stream();
	}

	public UADDisplay getUADDisplay(String key) {
		return _uadDisplayServiceTrackerMap.getService(key);
	}

	public Collection<UADDisplay> getUADDisplays() {
		return _uadDisplayServiceTrackerMap.values();
	}

	public Stream<UADDisplay> getUADDisplayStream() {
		return getUADDisplays().stream();
	}

	public UADExporter getUADExporter(String key) {
		return _uadExporterServiceTrackerMap.getService(key);
	}

	public UADHierarchyDisplay getUADHierarchyDisplay(String applicationKey) {
		UADHierarchyDeclaration uadHierarchyDeclaration =
			_getUADHierarchyDeclaration(applicationKey);

		if (uadHierarchyDeclaration == null) {
			return null;
		}

		return new UADHierarchyDisplay(uadHierarchyDeclaration);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleUADAnonymizerServiceTrackerMap = getMultiValueServiceTrackerMap(
			bundleContext, UADAnonymizer.class);
		_bundleUADDisplayServiceTrackerMap = getMultiValueServiceTrackerMap(
			bundleContext, UADDisplay.class);
		_bundleUADExporterServiceTrackerMap = getMultiValueServiceTrackerMap(
			bundleContext, UADExporter.class);
		_bundleUADHierarchyDeclarationServiceTrackerMap =
			getUADHierachyDeclarationServiceTrackerMap(
				bundleContext, UADHierarchyDeclaration.class);
		_uadAnonymizerServiceTrackerMap = getSingleValueServiceTrackerMap(
			bundleContext, UADAnonymizer.class);
		_uadDisplayServiceTrackerMap = getSingleValueServiceTrackerMap(
			bundleContext, UADDisplay.class);
		_uadExporterServiceTrackerMap = getSingleValueServiceTrackerMap(
			bundleContext, UADExporter.class);
	}

	@Deactivate
	protected void deactivate() {
		_bundleUADAnonymizerServiceTrackerMap.close();
		_bundleUADDisplayServiceTrackerMap.close();
		_bundleUADExporterServiceTrackerMap.close();
		_bundleUADHierarchyDeclarationServiceTrackerMap.close();
		_uadAnonymizerServiceTrackerMap.close();
		_uadDisplayServiceTrackerMap.close();
		_uadExporterServiceTrackerMap.close();
	}

	protected <T extends UADComponent> ServiceTrackerMap<String, List<T>>
		getMultiValueServiceTrackerMap(
			BundleContext bundleContext, Class<T> clazz) {

		return ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, clazz, null,
			ServiceReferenceMapperFactory.create(
				bundleContext,
				(uadDisplay, emitter) -> {
					Bundle bundle = FrameworkUtil.getBundle(
						uadDisplay.getClass());

					emitter.emit(bundle.getSymbolicName());
				}));
	}

	protected <T extends UADComponent> ServiceTrackerMap<String, T>
		getSingleValueServiceTrackerMap(
			BundleContext bundleContext, Class<T> clazz) {

		return ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, clazz, null,
			ServiceReferenceMapperFactory.create(
				bundleContext,
				(uadComponent, emitter) -> {
					Class<?> uadClass = uadComponent.getTypeClass();

					emitter.emit(uadClass.getName());
				}));
	}

	protected <T> ServiceTrackerMap<String, T>
		getUADHierachyDeclarationServiceTrackerMap(
			BundleContext bundleContext, Class<T> clazz) {

		return ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, clazz, null,
			ServiceReferenceMapperFactory.create(
				bundleContext,
				(uadHierachyDeclaration, emitter) -> {
					Bundle bundle = FrameworkUtil.getBundle(
						uadHierachyDeclaration.getClass());

					emitter.emit(bundle.getSymbolicName());
				}));
	}

	private Collection<UADAnonymizer> _getNonreviewableUADAnonymizers(
		Collection<UADAnonymizer> uadAnonymizers,
		Stream<UADDisplay> uadDisplayStream) {

		Stream<Class> typeClassStream = uadDisplayStream.map(
			UADDisplay::getTypeClass);

		List<Class> uadDisplayTypeClasses = typeClassStream.collect(
			Collectors.toList());

		List<UADAnonymizer> nonreviewableUADAnonymizers = new ArrayList<>(
			uadAnonymizers);

		for (UADAnonymizer uadAnonymizer : uadAnonymizers) {
			if (uadDisplayTypeClasses.contains(uadAnonymizer.getTypeClass())) {
				nonreviewableUADAnonymizers.remove(uadAnonymizer);
			}
		}

		return nonreviewableUADAnonymizers;
	}

	private UADHierarchyDeclaration _getUADHierarchyDeclaration(
		String applicationKey) {

		return _bundleUADHierarchyDeclarationServiceTrackerMap.getService(
			applicationKey);
	}

	private ServiceTrackerMap<String, List<UADAnonymizer>>
		_bundleUADAnonymizerServiceTrackerMap;
	private ServiceTrackerMap<String, List<UADDisplay>>
		_bundleUADDisplayServiceTrackerMap;
	private ServiceTrackerMap<String, List<UADExporter>>
		_bundleUADExporterServiceTrackerMap;
	private ServiceTrackerMap<String, UADHierarchyDeclaration>
		_bundleUADHierarchyDeclarationServiceTrackerMap;
	private ServiceTrackerMap<String, UADAnonymizer>
		_uadAnonymizerServiceTrackerMap;
	private ServiceTrackerMap<String, UADDisplay> _uadDisplayServiceTrackerMap;
	private ServiceTrackerMap<String, UADExporter>
		_uadExporterServiceTrackerMap;

}