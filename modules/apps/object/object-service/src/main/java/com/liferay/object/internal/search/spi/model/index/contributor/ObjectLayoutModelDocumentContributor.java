/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.search.spi.model.index.contributor;

import com.liferay.object.model.ObjectLayout;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import org.osgi.service.component.annotations.Component;

/**
 * @author Carolina Barbosa
 */
@Component(
	property = "indexer.class.name=com.liferay.object.model.ObjectLayout",
	service = ModelDocumentContributor.class
)
public class ObjectLayoutModelDocumentContributor
	implements ModelDocumentContributor<ObjectLayout> {

	@Override
	public void contribute(Document document, ObjectLayout objectLayout) {
		document.addLocalizedText(Field.NAME, objectLayout.getNameMap());
		document.addLocalizedKeyword(
			"localized_name", objectLayout.getNameMap(), true, true);
		document.addKeyword(
			"objectDefinitionId", objectLayout.getObjectDefinitionId());

		document.remove(Field.USER_NAME);
	}

}