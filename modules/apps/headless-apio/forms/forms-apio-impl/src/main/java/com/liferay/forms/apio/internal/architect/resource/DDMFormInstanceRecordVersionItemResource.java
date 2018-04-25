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
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersionModel;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordVersionService;
import com.liferay.forms.apio.architect.identifier.FormInstanceRecordVersionIdentifier;
import com.liferay.person.apio.identifier.PersonIdentifier;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose {@code
 * FormInstanceRecordVersion} resources through a web API. The resources are
 * mapped from the internal model {@code DDMFormInstanceRecordVersion}.
 *
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class DDMFormInstanceRecordVersionItemResource implements
	ItemResource<DDMFormInstanceRecordVersion, Long,
		FormInstanceRecordVersionIdentifier> {

	@Override
	public String getName() {
		return "form-instance-record-version";
	}

	@Override
	public ItemRoutes<DDMFormInstanceRecordVersion, Long> itemRoutes(
		ItemRoutes.Builder<DDMFormInstanceRecordVersion, Long> builder) {

		return builder.addGetter(
			_ddmFormInstanceRecordVersionService::getFormInstanceRecordVersion
		).build();
	}

	@Override
	public Representor<DDMFormInstanceRecordVersion, Long> representor(
		Representor.Builder<DDMFormInstanceRecordVersion, Long> builder) {

		return builder.types(
			"FormInstanceRecordVersion"
		).identifier(
			DDMFormInstanceRecordVersion::getFormInstanceRecordVersionId
		).addLinkedModel(
			"author", PersonIdentifier.class,
			DDMFormInstanceRecordVersion::getUserId
		).addString(
			"name", DDMFormInstanceRecordVersionModel::getVersion
		).build();
	}

	@Reference
	private DDMFormInstanceRecordVersionService
		_ddmFormInstanceRecordVersionService;

}