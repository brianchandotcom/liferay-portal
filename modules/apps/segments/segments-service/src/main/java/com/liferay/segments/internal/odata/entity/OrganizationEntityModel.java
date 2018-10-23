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

package com.liferay.segments.internal.odata.entity;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;

/**
 * Provides the entity data model from the Indexed Entity (Organization).
 *
 * @author David Arques
 * @review
 */
@Component(
	immediate = true,
	property = "entity.model.name=" + OrganizationEntityModel.NAME,
	service = EntityModel.class
)
public class OrganizationEntityModel implements EntityModel {

	public static final String NAME = "Organization";

	@Override
	public Map<String, EntityField> getEntityFieldsMap() {
		return _entityFieldsMap;
	}

	@Override
	public String getName() {
		return NAME;
	}

	private static final Map<String, EntityField> _entityFieldsMap = Stream.of(
		new EntityField(
			Field.NAME, EntityField.Type.STRING,
			locale -> Field.getSortableFieldName(Field.NAME)),
		new EntityField(
			"parentOrganizationId", EntityField.Type.STRING,
			locale -> "parentOrganizationId")
	).collect(
		Collectors.toMap(EntityField::getName, Function.identity())
	);

}