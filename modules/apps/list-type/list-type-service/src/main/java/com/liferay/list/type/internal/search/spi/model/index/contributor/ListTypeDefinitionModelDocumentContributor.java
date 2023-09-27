/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.list.type.internal.search.spi.model.index.contributor;

import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import org.osgi.service.component.annotations.Component;

/**
 * @author Carolina Barbosa
 */
@Component(
	property = "indexer.class.name=com.liferay.list.type.model.ListTypeDefinition",
	service = ModelDocumentContributor.class
)
public class ListTypeDefinitionModelDocumentContributor
	implements ModelDocumentContributor<ListTypeDefinition> {

	@Override
	public void contribute(
		Document document, ListTypeDefinition listTypeDefinition) {

		document.addLocalizedText(Field.NAME, listTypeDefinition.getNameMap());
		document.addLocalizedKeyword(
			Field.getSortableFieldName(Field.NAME),
			listTypeDefinition.getNameMap(), true);
		document.remove(Field.USER_NAME);
	}

}