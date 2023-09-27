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
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.StringEntityField;
import com.liferay.portal.odata.normalizer.Normalizer;
import com.liferay.portal.search.expando.ExpandoBridgeIndexer;

import java.util.Collections;
import java.util.List;

/**
 * @author Javier Gamarra
 */
public class EntityFieldsUtil {

	public static List<EntityField> getEntityFields(
		long classNameId, long companyId,
		ExpandoBridgeIndexer expandoBridgeIndexer,
		ExpandoColumnLocalService expandoColumnLocalService,
		ExpandoTableLocalService expandoTableLocalService) {

		ExpandoTable expandoTable = expandoTableLocalService.fetchDefaultTable(
			companyId, classNameId);

		if (expandoTable == null) {
			return Collections.emptyList();
		}

		return TransformUtil.transform(
			expandoColumnLocalService.getColumns(expandoTable.getTableId()),
			expandoColumn -> _getEntityField(
				expandoBridgeIndexer, expandoColumn));
	}

	private static EntityField _getEntityField(
		ExpandoBridgeIndexer expandoBridgeIndexer,
		ExpandoColumn expandoColumn) {

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

		String internalName = expandoBridgeIndexer.encodeFieldName(
			expandoColumn);

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

		return new StringEntityField(
			externalName,
			locale -> {
				String numericSuffix = expandoBridgeIndexer.getNumericSuffix(
					type);

				if (!numericSuffix.equals(StringPool.BLANK)) {
					return internalName.concat(".keyword");
				}

				return internalName;
			});
	}

}