/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.search.spi.model.index.contributor;

import com.liferay.object.model.ObjectFolder;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import org.osgi.service.component.annotations.Component;

/**
 * @author Murilo Stodolni
 */
@Component(
	property = "indexer.class.name=com.liferay.object.model.ObjectFolder",
	service = ModelDocumentContributor.class
)
public class ObjectFolderModelDocumentContributor
	implements ModelDocumentContributor<ObjectFolder> {

	@Override
	public void contribute(Document document, ObjectFolder objectFolder) {
		document.addLocalizedKeyword(
			"localized_label", objectFolder.getLabelMap(), true, true);
		document.addText(Field.NAME, objectFolder.getName());

		document.remove(Field.USER_NAME);
	}

}