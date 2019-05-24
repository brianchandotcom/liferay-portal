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

package com.liferay.headless.delivery.internal.dto.v1_0.util;

import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.model.ExpandoTableConstants;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.expando.kernel.util.ExpandoBridgeIndexerUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.odata.entity.BooleanEntityField;
import com.liferay.portal.odata.entity.DateTimeEntityField;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.StringEntityField;
import com.liferay.portal.odata.normalizer.Normalizer;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Javier Gamarra
 */
public class EntityFieldsUtil {

	public static Optional<EntityField> getEntityFieldOptional(
		ExpandoColumn expandoColumn) {

		UnicodeProperties unicodeProperties =
			expandoColumn.getTypeSettingsProperties();

		int indexType = GetterUtil.getInteger(
			unicodeProperties.get(ExpandoColumnConstants.INDEX_TYPE));

		if (indexType == ExpandoColumnConstants.INDEX_TYPE_NONE) {
			return Optional.empty();
		}

		String encodedFieldName = ExpandoBridgeIndexerUtil.encodeFieldName(
			expandoColumn.getName(), indexType);

		String encodedName = Normalizer.normalizeIdentifier(
			expandoColumn.getName());

		return Optional.of(
			_getEntityField(encodedFieldName, encodedName, expandoColumn));
	}

	public static Map<Long, EntityField> getEntityFields(
			long classNameId,
			ExpandoColumnLocalService expandoColumnLocalService,
			ExpandoTableLocalService expandoTableLocalService)
		throws PortalException {

		Map<Long, EntityField> fieldMap = new HashMap<>();

		ActionableDynamicQuery actionableDynamicQuery =
			expandoColumnLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property property = PropertyFactoryUtil.forName("tableId");

				dynamicQuery.add(
					property.in(
						_getTableDynamicQuery(
							classNameId, expandoTableLocalService)));
			});
		actionableDynamicQuery.setPerformActionMethod(
			(ActionableDynamicQuery.PerformActionMethod<ExpandoColumn>)
				expandoColumn -> {
					Optional<EntityField> entityFieldOptional =
						getEntityFieldOptional(expandoColumn);

					entityFieldOptional.ifPresent(
						entityField -> fieldMap.put(
							expandoColumn.getColumnId(), entityField));
				});

		actionableDynamicQuery.performActions();

		return fieldMap;
	}

	private static EntityField _getEntityField(
		String encodedFieldName, String encodedName,
		ExpandoColumn expandoColumn) {

		int type = expandoColumn.getType();

		if (type == ExpandoColumnConstants.BOOLEAN) {
			return new BooleanEntityField(
				encodedName, locale -> encodedFieldName);
		}
		else if (type == ExpandoColumnConstants.DATE) {
			return new DateTimeEntityField(
				encodedName,
				locale -> Field.getSortableFieldName(encodedFieldName),
				locale -> encodedFieldName);
		}
		else if (type == ExpandoColumnConstants.STRING_LOCALIZED) {
			return new StringEntityField(
				encodedName,
				locale -> Field.getLocalizedName(locale, encodedFieldName));
		}
		else {
			return new StringEntityField(
				encodedName, locale -> encodedFieldName);
		}
	}

	private static DynamicQuery _getTableDynamicQuery(
		long classNameId, ExpandoTableLocalService expandoTableLocalService) {

		DynamicQuery dynamicQuery = expandoTableLocalService.dynamicQuery();

		Property classNameIdProperty = PropertyFactoryUtil.forName(
			"classNameId");

		dynamicQuery.add(classNameIdProperty.eq(classNameId));

		Property nameProperty = PropertyFactoryUtil.forName("name");

		dynamicQuery.add(
			nameProperty.eq(ExpandoTableConstants.DEFAULT_TABLE_NAME));

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("tableId"));

		return dynamicQuery;
	}

}