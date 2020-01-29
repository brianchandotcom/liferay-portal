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
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.CollectionEntityField;
import com.liferay.portal.odata.entity.ComplexEntityField;
import com.liferay.portal.odata.entity.DateTimeEntityField;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.entity.IdEntityField;
import com.liferay.portal.odata.entity.IntegerEntityField;
import com.liferay.portal.odata.entity.StringEntityField;

import java.util.List;
import java.util.Map;

/**
 * @author Sarai Díaz
 */
public class DocumentEntityModel implements EntityModel {

	public DocumentEntityModel(List<EntityField> entityFields) {
		_entityFieldsMap =
			EntityFieldsFactory.createEntityFieldsMapWithEntityId(
				new CollectionEntityField(
					new IntegerEntityField(
						"taxonomyCategoryIds", locale -> "assetCategoryIds")),
				new CollectionEntityField(
					new StringEntityField(
						"keywords", locale -> "assetTagNames.raw")),
				new ComplexEntityField("customFields", entityFields),
				new DateTimeEntityField(
					"dateCreated",
					locale -> Field.getSortableFieldName(Field.CREATE_DATE),
					locale -> Field.CREATE_DATE),
				new DateTimeEntityField(
					"dateModified",
					locale -> Field.getSortableFieldName(Field.MODIFIED_DATE),
					locale -> Field.MODIFIED_DATE),
				new IdEntityField(
					"encodingFormat",
					locale -> Field.getSortableFieldName(
						StringBundler.concat(
							"mimeType", StringPool.UNDERLINE, "String")),
					mimeType -> {
						String encodingFormat = String.valueOf(mimeType);

						return StringUtil.replace(
							encodingFormat, CharPool.SLASH, CharPool.UNDERLINE);
					}),
				new IntegerEntityField("creatorId", locale -> Field.USER_ID),
				new IntegerEntityField(
					"sizeInBytes",
					locale -> Field.getSortableFieldName("size")),
				new StringEntityField(
					"fileExtension",
					locale -> Field.getSortableFieldName(
						StringBundler.concat(
							"extension", StringPool.UNDERLINE, "String"))),
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