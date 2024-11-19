/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.search.spi.model.index.contributor;

import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marco Leo
 */
@Component(
	property = "indexer.class.name=com.liferay.object.model.ObjectEntryFolder",
	service = ModelDocumentContributor.class
)
public class ObjectEntryFolderModelDocumentContributor
	implements ModelDocumentContributor<ObjectEntryFolder> {

	@Override
	public void contribute(
		Document document, ObjectEntryFolder objectEntryFolder) {

		document.addLocalizedKeyword(
			"localized_label", objectEntryFolder.getLabelMap(), true, true);
		document.addKeyword(
			Field.FOLDER_ID, objectEntryFolder.getParentObjectEntryFolderId());
	}

}