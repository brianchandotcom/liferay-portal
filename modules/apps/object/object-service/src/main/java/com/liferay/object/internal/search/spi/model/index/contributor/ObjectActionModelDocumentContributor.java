/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.search.spi.model.index.contributor;

import com.liferay.object.model.ObjectAction;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	property = "indexer.class.name=com.liferay.object.model.ObjectAction",
	service = ModelDocumentContributor.class
)
public class ObjectActionModelDocumentContributor
	implements ModelDocumentContributor<ObjectAction> {

	@Override
	public void contribute(Document document, ObjectAction objectAction) {
		document.addText(Field.NAME, objectAction.getName());
		document.addLocalizedText(
			"label",
			_localization.populateLocalizationMap(
				objectAction.getLabelMap(), objectAction.getDefaultLanguageId(),
				0));
		document.addLocalizedKeyword(
			"localized_label", objectAction.getLabelMap(), true, true);
		document.addKeyword(
			"objectDefinitionId", objectAction.getObjectDefinitionId());

		document.remove(Field.USER_NAME);
	}

	@Reference
	private Localization _localization;

}