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

package com.liferay.change.tracking.internal.definition;

import com.liferay.change.tracking.definition.CTDefinition;
import com.liferay.change.tracking.definition.CTDefinitionRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Gergely Mathe
 */
@Component(immediate = true, service = CTDefinitionRegistry.class)
public class CTDefinitionRegistryImpl implements CTDefinitionRegistry {

	@Override
	public List<CTDefinition<?, ?>> getAllCTDefinitions() {
		return new ArrayList<>(_ctDefinitionsByVersionClassName.values());
	}

	public Optional<CTDefinition<?, ?>> getCTDefinitionOptionalByResourceClass(
		Class<?> clazz) {

		return getCTDefinitionOptionalByResourceClassName(clazz.getName());
	}

	public Optional<CTDefinition<?, ?>>
		getCTDefinitionOptionalByResourceClassName(String className) {

		return Optional.ofNullable(
			_ctDefinitionsByResourceClassName.get(className));
	}

	public Optional<CTDefinition<?, ?>> getCTDefinitionOptionalByVersionClass(
		Class<?> clazz) {

		return getCTDefinitionOptionalByVersionClassName(clazz.getName());
	}

	public Optional<CTDefinition<?, ?>>
		getCTDefinitionOptionalByVersionClassName(String className) {

		return Optional.ofNullable(
			_ctDefinitionsByVersionClassName.get(className));
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC, unbind = "_removeCTDefinition"
	)
	private void _addCTConfiguration(CTDefinition<?, ?> ctDefinition) {
		Class<?> resourceEntityClass = ctDefinition.getResourceEntityClass();

		_ctDefinitionsByResourceClassName.put(
			resourceEntityClass.getName(), ctDefinition);

		Class<?> versionEntityClass = ctDefinition.getVersionEntityClass();

		_ctDefinitionsByVersionClassName.put(
			versionEntityClass.getName(), ctDefinition);
	}

	private void _removeCTDefinition(CTDefinition<?, ?> ctDefinition) {
		Class<?> resourceEntityClass = ctDefinition.getResourceEntityClass();

		_ctDefinitionsByResourceClassName.remove(resourceEntityClass.getName());

		Class<?> versionEntityClass = ctDefinition.getVersionEntityClass();

		_ctDefinitionsByVersionClassName.remove(versionEntityClass.getName());
	}

	private final Map<String, CTDefinition<?, ?>>
		_ctDefinitionsByResourceClassName = new HashMap<>();
	private final Map<String, CTDefinition<?, ?>>
		_ctDefinitionsByVersionClassName = new HashMap<>();

}