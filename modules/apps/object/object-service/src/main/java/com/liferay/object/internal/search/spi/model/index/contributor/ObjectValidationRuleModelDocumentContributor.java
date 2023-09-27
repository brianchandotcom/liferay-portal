/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.search.spi.model.index.contributor;

import com.liferay.object.model.ObjectValidationRule;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcela Cunha
 */
@Component(
	property = "indexer.class.name=com.liferay.object.model.ObjectValidationRule",
	service = ModelDocumentContributor.class
)
public class ObjectValidationRuleModelDocumentContributor
	implements ModelDocumentContributor<ObjectValidationRule> {

	@Override
	public void contribute(
		Document document, ObjectValidationRule objectValidationRule) {

		document.addLocalizedText(
			Field.NAME,
			_localization.populateLocalizationMap(
				objectValidationRule.getNameMap(),
				objectValidationRule.getDefaultLanguageId(), 0));
		document.addLocalizedKeyword(
			"localized_name", objectValidationRule.getNameMap(), true, true);
		document.addKeyword(
			"objectDefinitionId", objectValidationRule.getObjectDefinitionId());

		document.remove(Field.USER_NAME);
	}

	@Reference
	protected ClassNameLocalService classNameLocalService;

	@Reference
	private Localization _localization;

}