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

package com.liferay.change.tracking.internal.configuration;

import com.liferay.change.tracking.configuration.CTConfiguration;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Gergely Mathe
 */
@Component(immediate = true, service = CTConfigurationRegistry.class)
public class CTConfigurationRegistry {

	public Optional<CTConfiguration<?, ?>>
		getCTConfigurationOptionalByResourceClass(Class<?> clazz) {

		return getCTConfigurationOptionalByResourceClassName(clazz.getName());
	}

	public Optional<CTConfiguration<?, ?>>
		getCTConfigurationOptionalByResourceClassName(String className) {

		return Optional.ofNullable(
			_configurationsByResourceClassName.get(className));
	}

	public Optional<CTConfiguration<?, ?>>
		getCTConfigurationOptionalByVersionClass(Class<?> clazz) {

		return getCTConfigurationOptionalByVersionClassName(clazz.getName());
	}

	public Optional<CTConfiguration<?, ?>>
		getCTConfigurationOptionalByVersionClassName(String className) {

		return Optional.ofNullable(
			_configurationsByVersionClassName.get(className));
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC, unbind = "_removeCTConfiguration"
	)
	private void _addCTConfiguration(CTConfiguration<?, ?> ctConfiguration) {
		Class<?> resourceEntityClass = ctConfiguration.getResourceEntityClass();
		Class<?> versionEntityClass = ctConfiguration.getVersionEntityClass();

		_configurationsByResourceClassName.put(
			resourceEntityClass.getName(), ctConfiguration);
		_configurationsByVersionClassName.put(
			versionEntityClass.getName(), ctConfiguration);
	}

	private void _removeCTConfiguration(CTConfiguration<?, ?> ctConfiguration) {
		Class<?> resourceEntityClass = ctConfiguration.getResourceEntityClass();
		Class<?> versionEntityClass = ctConfiguration.getVersionEntityClass();

		_configurationsByResourceClassName.remove(
			resourceEntityClass.getName());
		_configurationsByVersionClassName.remove(versionEntityClass.getName());
	}

	private final Map<String, CTConfiguration<?, ?>>
		_configurationsByResourceClassName = new HashMap<>();
	private final Map<String, CTConfiguration<?, ?>>
		_configurationsByVersionClassName = new HashMap<>();

}