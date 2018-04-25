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

package com.liferay.forms.apio.internal.resource;

import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.ItemResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.DDMStructureVersionService;
import com.liferay.forms.apio.architect.identifier.StructureVersionIdentifier;
import com.liferay.person.apio.identifier.PersonIdentifier;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose {@code StructureVersion}
 * resources through a web API. The resources are mapped from the internal model
 * {@code DDMStructureVersion}.
 *
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class StructureVersionItemResource implements
	ItemResource<DDMStructureVersion, Long, StructureVersionIdentifier> {

	@Override
	public String getName() {
		return "structure-version";
	}

	@Override
	public ItemRoutes<DDMStructureVersion, Long> itemRoutes(
		ItemRoutes.Builder<DDMStructureVersion, Long> builder) {

		return builder.addGetter(
			_ddmStructureVersionService::getStructureVersion
		).build();
	}

	@Override
	public Representor<DDMStructureVersion, Long> representor(
		Representor.Builder<DDMStructureVersion, Long> builder) {

		return builder.types(
			"StructureVersion"
		).identifier(
			DDMStructureVersion::getStructureVersionId
		).addLinkedModel(
			"author", PersonIdentifier.class, DDMStructureVersion::getUserId
		).addString(
			"name", DDMStructureVersion::getVersion
		).build();
	}

	@Reference
	private DDMStructureVersionService _ddmStructureVersionService;

}