/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.search.spi.model.index.contributor;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import org.osgi.service.component.annotations.Component;

/**
 * @author Carolina Barbosa
 */
@Component(
	property = "indexer.class.name=com.liferay.object.model.ObjectDefinition",
	service = ModelDocumentContributor.class
)
public class ObjectDefinitionModelDocumentContributor
	implements ModelDocumentContributor<ObjectDefinition> {

	@Override
	public void contribute(
		Document document, ObjectDefinition objectDefinition) {

		document.addText(Field.NAME, objectDefinition.getShortName());
		document.addLocalizedKeyword(
			"localized_label", objectDefinition.getLabelMap(), true, true);
		document.addKeyword(
			"objectFolderExternalReferenceCode",
			objectDefinition.getObjectFolderExternalReferenceCode(), true);

		document.remove(Field.USER_NAME);
	}

}