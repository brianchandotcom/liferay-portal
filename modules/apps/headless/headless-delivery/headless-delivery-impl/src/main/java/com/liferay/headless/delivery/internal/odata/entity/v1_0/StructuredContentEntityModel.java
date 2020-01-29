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

package com.liferay.headless.delivery.internal.odata.entity.v1_0;

import com.liferay.headless.common.spi.odata.entity.EntityFieldsFactory;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.odata.entity.CollectionEntityField;
import com.liferay.portal.odata.entity.ComplexEntityField;
import com.liferay.portal.odata.entity.DateTimeEntityField;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.entity.IntegerEntityField;
import com.liferay.portal.odata.entity.StringEntityField;

import java.util.List;
import java.util.Map;

/**
 * @author Julio Camarero
 * @author Cristina González
 * @author Brian Wing Shun Chan
 */
public class StructuredContentEntityModel implements EntityModel {

	public StructuredContentEntityModel(
		List<EntityField> entityFields, List<EntityField> customEntityFields) {

		_entityFieldsMap =
			EntityFieldsFactory.createEntityFieldsMapWithEntityId(
				new CollectionEntityField(
					new IntegerEntityField(
						"taxonomyCategoryIds", locale -> "assetCategoryIds")),
				new CollectionEntityField(
					new StringEntityField(
						"keywords", locale -> "assetTagNames.raw")),
				new ComplexEntityField("contentFields", entityFields),
				new ComplexEntityField("customFields", customEntityFields),
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
				new IntegerEntityField(
					"contentStructureId", locale -> Field.CLASS_TYPE_ID),
				new IntegerEntityField("creatorId", locale -> Field.USER_ID),
				new StringEntityField(
					"title",
					locale -> Field.getSortableFieldName(
						"localized_title_".concat(
							LocaleUtil.toLanguageId(locale)))));
	}

	@Override
	public Map<String, EntityField> getEntityFieldsMap() {
		return _entityFieldsMap;
	}

	private final Map<String, EntityField> _entityFieldsMap;

}