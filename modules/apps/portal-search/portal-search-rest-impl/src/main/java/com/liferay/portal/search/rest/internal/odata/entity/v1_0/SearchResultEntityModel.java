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

package com.liferay.portal.search.rest.internal.odata.entity.v1_0;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.odata.entity.CollectionEntityField;
import com.liferay.portal.odata.entity.DateTimeEntityField;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.entity.IntegerEntityField;
import com.liferay.portal.odata.entity.StringEntityField;

import java.util.Map;

/**
 * @author Petteri Karttunen
 */
public class SearchResultEntityModel implements EntityModel {

	public SearchResultEntityModel() {
		_entityFieldsMap = EntityModel.toEntityFieldsMap(
			new CollectionEntityField(
				new IntegerEntityField("groupIds", locale -> Field.GROUP_ID)),
			new CollectionEntityField(
				new IntegerEntityField(
					"taxonomyCategoryIds", locale -> "assetCategoryIds")),
			new CollectionEntityField(
				new StringEntityField(
					"keywords", locale -> "assetTagNames.raw")),
			new DateTimeEntityField(
				"dateCreated",
				locale -> Field.getSortableFieldName(Field.CREATE_DATE),
				locale -> Field.CREATE_DATE),
			new DateTimeEntityField(
				"dateModified",
				locale -> Field.getSortableFieldName(Field.MODIFIED_DATE),
				locale -> Field.MODIFIED_DATE),
			new IntegerEntityField("creatorId", locale -> Field.USER_ID),
			new StringEntityField(
				"description",
				locale -> Field.getSortableFieldName(
					LocalizationUtil.getLocalizedName(
						Field.DESCRIPTION, LocaleUtil.toLanguageId(locale)))),
			new StringEntityField(
				"title",
				locale -> Field.getSortableFieldName(
					LocalizationUtil.getLocalizedName(
						"localized_title", LocaleUtil.toLanguageId(locale)))));
	}

	@Override
	public Map<String, EntityField> getEntityFieldsMap() {
		return _entityFieldsMap;
	}

	private final Map<String, EntityField> _entityFieldsMap;

}