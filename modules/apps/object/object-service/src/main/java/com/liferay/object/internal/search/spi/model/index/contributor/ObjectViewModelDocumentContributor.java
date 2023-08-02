/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.search.spi.model.index.contributor;

import com.liferay.object.model.ObjectView;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import org.osgi.service.component.annotations.Component;

/**
 * @author Gabriel Albuquerque
 */
@Component(
	property = "indexer.class.name=com.liferay.object.model.ObjectView",
	service = ModelDocumentContributor.class
)
public class ObjectViewModelDocumentContributor
	implements ModelDocumentContributor<ObjectView> {

	@Override
	public void contribute(Document document, ObjectView objectView) {
		document.addLocalizedText(Field.NAME, objectView.getNameMap());
		document.addLocalizedKeyword(
			"localized_name", objectView.getNameMap(), true, true);
		document.addKeyword(
			"objectDefinitionId", objectView.getObjectDefinitionId());

		document.remove(Field.USER_NAME);
	}

}