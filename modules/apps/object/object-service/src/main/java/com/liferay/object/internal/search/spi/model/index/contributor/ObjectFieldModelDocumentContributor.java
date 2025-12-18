/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.search.spi.model.index.contributor;

import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.object.model.ObjectFieldSettingTable;
import com.liferay.object.service.ObjectFieldSettingLocalService;
import com.liferay.petra.sql.dsl.DSLFunctionFactoryUtil;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.ReindexCacheThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import java.util.HashSet;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carolina Barbosa
 */
@Component(
	property = "indexer.class.name=com.liferay.object.model.ObjectField",
	service = ModelDocumentContributor.class
)
public class ObjectFieldModelDocumentContributor
	implements ModelDocumentContributor<ObjectField> {

	@Override
	public void contribute(Document document, ObjectField objectField) {
		document.addText(Field.NAME, objectField.getName());
		document.addLocalizedText(
			"label",
			_localization.populateLocalizationMap(
				objectField.getLabelMap(), objectField.getDefaultLanguageId(),
				0));
		document.addLocalizedKeyword(
			"localized_label", objectField.getLabelMap(), true, true);
		document.addKeyword(
			"objectDefinitionId", objectField.getObjectDefinitionId());
		document.addKeyword("objectFieldId", objectField.getObjectFieldId());
		document.addKeyword("state", objectField.isState());

		if (objectField.hasUniqueValues()) {
			document.addKeyword(
				"unique", _isUnique(objectField.getObjectFieldId()));
		}

		document.remove(Field.USER_NAME);
	}

	private boolean _isUnique(long objectFieldId) {
		Set<Long> uniqueObjectFieldIds =
			ReindexCacheThreadLocal.getGlobalReindexCache(
				() -> -1, ObjectFieldModelDocumentContributor.class.getName(),
				count -> new HashSet<>(
					_objectFieldSettingLocalService.dslQuery(
						DSLQueryFactoryUtil.select(
							ObjectFieldSettingTable.INSTANCE.objectFieldId
						).from(
							ObjectFieldSettingTable.INSTANCE
						).where(
							ObjectFieldSettingTable.INSTANCE.name.eq(
								ObjectFieldSettingConstants.NAME_UNIQUE_VALUES
							).and(
								DSLFunctionFactoryUtil.lower(
									DSLFunctionFactoryUtil.castClobText(
										ObjectFieldSettingTable.INSTANCE.value)
								).eq(
									StringPool.TRUE
								)
							)
						))));

		if (uniqueObjectFieldIds == null) {
			ObjectFieldSetting objectFieldSetting =
				_objectFieldSettingLocalService.fetchObjectFieldSetting(
					objectFieldId,
					ObjectFieldSettingConstants.NAME_UNIQUE_VALUES);

			if (objectFieldSetting == null) {
				return false;
			}

			return GetterUtil.getBoolean(objectFieldSetting.getValue());
		}

		return uniqueObjectFieldIds.contains(objectFieldId);
	}

	@Reference
	private Localization _localization;

	@Reference
	private ObjectFieldSettingLocalService _objectFieldSettingLocalService;

}