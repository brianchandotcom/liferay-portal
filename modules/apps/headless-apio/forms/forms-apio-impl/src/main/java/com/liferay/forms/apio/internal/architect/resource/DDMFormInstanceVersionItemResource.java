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

package com.liferay.forms.apio.internal.architect.resource;

import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.ItemResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceVersion;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceVersionService;
import com.liferay.forms.apio.architect.identifier.FormInstanceVersionIdentifier;
import com.liferay.person.apio.identifier.PersonIdentifier;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose {@code FormInstanceVersion}
 * resources through a web API. The resources are mapped from the internal model
 * {@code DDMFormInstanceVersion}.
 *
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class DDMFormInstanceVersionItemResource implements
	ItemResource<DDMFormInstanceVersion, Long, FormInstanceVersionIdentifier> {

	@Override
	public String getName() {
		return "form-instance-version";
	}

	@Override
	public ItemRoutes<DDMFormInstanceVersion, Long> itemRoutes(
		ItemRoutes.Builder<DDMFormInstanceVersion, Long> builder) {

		return builder.addGetter(
			_ddmFormInstanceVersionService::getFormInstanceVersion
		).build();
	}

	@Override
	public Representor<DDMFormInstanceVersion, Long> representor(
		Representor.Builder<DDMFormInstanceVersion, Long> builder) {

		return builder.types(
			"FormInstanceVersion"
		).identifier(
			DDMFormInstanceVersion::getFormInstanceVersionId
		).addLinkedModel(
			"author", PersonIdentifier.class, DDMFormInstanceVersion::getUserId
		).addString(
			"name", DDMFormInstanceVersion::getVersion
		).build();
	}

	@Reference
	private DDMFormInstanceVersionService _ddmFormInstanceVersionService;

}