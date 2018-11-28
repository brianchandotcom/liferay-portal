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

package com.liferay.structured.content.apio.internal.architect.filter;

import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.odata.entity.CollectionEntityField;
import com.liferay.portal.odata.entity.ComplexEntityField;
import com.liferay.portal.odata.entity.DateTimeEntityField;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.entity.IdEntityField;
import com.liferay.portal.odata.entity.StringEntityField;
import com.liferay.structured.content.apio.internal.architect.resource.StructuredContentNestedCollectionResource;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Provides the entity data model from the indexed entity ({@code
 * com.liferay.journal.model.JournalArticle}).
 *
 * @author Julio Camarero
 */
public class StructuredContentEntityModel implements EntityModel {

	public static final String NAME = "StructuredContent";

	public StructuredContentEntityModel(List<EntityField> entityFields) {
		_entityFieldsMap = Stream.of(
			new ComplexEntityField(
				StructuredContentNestedCollectionResource.VALUES_NAME,
				entityFields),
			new DateTimeEntityField(
				"dateCreated",
				locale -> Field.getSortableFieldName(Field.CREATE_DATE),
				locale -> Field.CREATE_DATE),
			new DateTimeEntityField(
				"dateModified",
				locale -> Field.getSortableFieldName(Field.MODIFIED_DATE),
				locale -> Field.MODIFIED_DATE),
			new DateTimeEntityField(
				"datePublished",
				locale -> Field.getSortableFieldName(Field.DISPLAY_DATE),
				locale -> Field.DISPLAY_DATE),
			new IdEntityField(
				"contentStructure", locale -> Field.CLASS_TYPE_ID,
				contentStructureLink -> {
					List<String> parts = StringUtil.split(
						String.valueOf(contentStructureLink), '/');

					return parts.get(parts.size() - 1);
				}),
			new IdEntityField(
				"creator", locale -> Field.USER_ID,
				creatorLink -> {
					List<String> parts = StringUtil.split(
						String.valueOf(creatorLink), '/');

					return parts.get(parts.size() - 1);
				}),
			new CollectionEntityField(
				new StringEntityField(
					"keywords", locale -> "assetTagNames.raw")),
			new StringEntityField(
				"title",
				locale -> Field.getSortableFieldName(
					"localized_title_".concat(LocaleUtil.toLanguageId(locale))))
		).collect(
			Collectors.toMap(EntityField::getName, Function.identity())
		);
	}

	@Override
	public Map<String, EntityField> getEntityFieldsMap() {
		return _entityFieldsMap;
	}

	@Override
	public String getName() {
		return NAME;
	}

	private final Map<String, EntityField> _entityFieldsMap;

}