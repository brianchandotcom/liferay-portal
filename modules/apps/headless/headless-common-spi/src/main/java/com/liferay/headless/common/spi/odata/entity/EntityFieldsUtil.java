/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.common.spi.odata.entity;

import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.odata.entity.BooleanEntityField;
import com.liferay.portal.odata.entity.DateTimeEntityField;
import com.liferay.portal.odata.entity.DoubleEntityField;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.StringEntityField;
import com.liferay.portal.odata.normalizer.Normalizer;
import com.liferay.portal.odata.sort.InvalidSortException;
import com.liferay.portal.search.expando.ExpandoBridgeUtil;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;

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

		return TransformUtil.transform(
			expandoColumnLocalService.getColumns(expandoTable.getTableId()),
			expandoColumn -> _getEntityField(expandoColumn));
	}

	private static EntityField _getEntityField(ExpandoColumn expandoColumn) {
		UnicodeProperties unicodeProperties =
			expandoColumn.getTypeSettingsProperties();

		int indexType = GetterUtil.getInteger(
			unicodeProperties.get(ExpandoColumnConstants.INDEX_TYPE));

		if (indexType == ExpandoColumnConstants.INDEX_TYPE_NONE) {
			return null;
		}

		String externalName = Normalizer.normalizeIdentifier(
			expandoColumn.getName());

		Function<Locale, String> function = locale -> {
			throw new InvalidSortException(
				"Unable to sort by property: " + externalName);
		};

		String internalName = ExpandoBridgeUtil.encodeFieldName(expandoColumn);

		int type = expandoColumn.getType();

		if (type == ExpandoColumnConstants.BOOLEAN) {
			return new BooleanEntityField(
				externalName, function, locale -> internalName);
		}
		else if (type == ExpandoColumnConstants.DATE) {
			return new DateTimeEntityField(
				externalName,
				locale -> Field.getSortableFieldName(internalName),
				locale -> internalName);
		}
		else if ((type == ExpandoColumnConstants.DOUBLE) ||
				 (type == ExpandoColumnConstants.FLOAT)) {

			return new DoubleEntityField(
				externalName,
				locale -> Field.getSortableFieldName(
					internalName + ExpandoBridgeUtil.NUMBER_SUFFIX));
		}
		else if (type == ExpandoColumnConstants.STRING_LOCALIZED) {
			return new StringEntityField(
				externalName, function,
				locale -> Field.getLocalizedName(locale, internalName));
		}

		return new StringEntityField(
			externalName, function,
			locale -> {
				String numericSuffix = ExpandoBridgeUtil.getNumericSuffix(type);

				if (!numericSuffix.equals(StringPool.BLANK)) {
					return internalName.concat(".keyword");
				}

				return internalName;
			});
	}

}