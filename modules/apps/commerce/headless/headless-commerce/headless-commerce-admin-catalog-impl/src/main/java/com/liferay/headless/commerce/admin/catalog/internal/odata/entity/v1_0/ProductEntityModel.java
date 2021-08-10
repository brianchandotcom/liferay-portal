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

package com.liferay.headless.commerce.admin.catalog.internal.odata.entity.v1_0;

import com.liferay.commerce.product.constants.CPField;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalServiceUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.odata.entity.CollectionEntityField;
import com.liferay.portal.odata.entity.DateTimeEntityField;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.entity.IntegerEntityField;
import com.liferay.portal.odata.entity.StringEntityField;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Alessio Antonio Rendina
 */
public class ProductEntityModel implements EntityModel {

	public ProductEntityModel() {
		_entityFieldsMap = Stream.of(
			new CollectionEntityField(
				new StringEntityField(
					"categoryIds", locale -> "assetCategoryIds")),
			new CollectionEntityField(
				new EntityField(
					"channelId", EntityField.Type.INTEGER,
					locale -> Field.getSortableFieldName(
						CPField.COMMERCE_CHANNEL_GROUP_IDS),
					locale -> CPField.COMMERCE_CHANNEL_GROUP_IDS,
					object -> _getCommerceChannelGroupId(object))),
			new CollectionEntityField(
				new IntegerEntityField("statusCode", locale -> Field.STATUS)),
			new DateTimeEntityField(
				"createDate",
				locale -> Field.getSortableFieldName(Field.CREATE_DATE),
				locale -> Field.CREATE_DATE),
			new DateTimeEntityField(
				"modifiedDate",
				locale -> Field.getSortableFieldName(Field.MODIFIED_DATE),
				locale -> Field.MODIFIED_DATE),
			new IntegerEntityField("catalogId", locale -> "commerceCatalogId"),
			new StringEntityField(
				"name", locale -> Field.getSortableFieldName("name")),
			new StringEntityField("productType", locale -> "productTypeName")
		).collect(
			Collectors.toMap(EntityField::getName, Function.identity())
		);
	}

	@Override
	public Map<String, EntityField> getEntityFieldsMap() {
		return _entityFieldsMap;
	}

	private String _getCommerceChannelGroupId(Object channelId) {
		CommerceChannel commerceChannel =
			CommerceChannelLocalServiceUtil.fetchCommerceChannel(
				GetterUtil.getLong(channelId));

		if (commerceChannel == null) {
			return "-1";
		}

		return String.valueOf(commerceChannel.getGroupId());
	}

	private final Map<String, EntityField> _entityFieldsMap;

}