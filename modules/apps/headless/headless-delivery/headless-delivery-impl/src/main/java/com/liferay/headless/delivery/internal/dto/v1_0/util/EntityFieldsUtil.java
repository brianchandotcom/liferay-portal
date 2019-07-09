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
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.expando.kernel.util.ExpandoBridgeIndexerUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.odata.entity.BooleanEntityField;
import com.liferay.portal.odata.entity.DateTimeEntityField;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.StringEntityField;
import com.liferay.portal.odata.normalizer.Normalizer;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Javier Gamarra
 */
public class EntityFieldsUtil {

	public static List<EntityField> getEntityFields(
		long classNameId, long companyId,
		ExpandoColumnLocalService expandoColumnLocalService,
		ExpandoTableLocalService expandoTableLocalService) {

		ExpandoTable expandoTable = expandoTableLocalService.fetchDefaultTable(
			companyId, classNameId);

		if (expandoTable == null) {
			return Collections.emptyList();
		}

		List<ExpandoColumn> expandoColumns =
			expandoColumnLocalService.getColumns(expandoTable.getTableId());

		Stream<ExpandoColumn> expandoColumnStream = expandoColumns.stream();

		return expandoColumnStream.map(
			EntityFieldsUtil::_getEntityField
		).filter(
			Objects::nonNull
		).collect(
			Collectors.toList()
		);
	}

	private static EntityField _getEntityField(ExpandoColumn expandoColumn) {
		UnicodeProperties unicodeProperties =
			expandoColumn.getTypeSettingsProperties();

		int indexType = GetterUtil.getInteger(
			unicodeProperties.get(ExpandoColumnConstants.INDEX_TYPE));

		if (indexType == ExpandoColumnConstants.INDEX_TYPE_NONE) {
			return null;
		}

		int type = expandoColumn.getType();

		String externalName = Normalizer.normalizeIdentifier(
			expandoColumn.getName());

		String internalName = ExpandoBridgeIndexerUtil.encodeFieldName(
			expandoColumn.getName(), indexType);

		if (type == ExpandoColumnConstants.BOOLEAN) {
			return new BooleanEntityField(externalName, locale -> internalName);
		}
		else if (type == ExpandoColumnConstants.DATE) {
			return new DateTimeEntityField(
				externalName,
				locale -> Field.getSortableFieldName(internalName),
				locale -> internalName);
		}
		else if (type == ExpandoColumnConstants.STRING_LOCALIZED) {
			return new StringEntityField(
				externalName,
				locale -> Field.getLocalizedName(locale, internalName));
		}

		return new StringEntityField(externalName, locale -> internalName);
	}

}