/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.search.spi.model.index.contributor;

import com.liferay.object.model.ObjectField;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

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
		document.addKeyword("state", objectField.isState());

		document.remove(Field.USER_NAME);
	}

	@Reference
	protected ClassNameLocalService classNameLocalService;

	@Reference
	private Localization _localization;

}