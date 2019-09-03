/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mail.reader.internal.search.spi.model.index.contributor;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeIndexerUtil;
import com.liferay.mail.reader.model.Folder;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import org.osgi.service.component.annotations.Component;

/**
 * @author Scott Lee
 * @author Peter Fellwock
 * @author Luan Maoski
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.mail.reader.model.Folder",
	service = ModelDocumentContributor.class
)
public class FolderModelDocumentContributor
	implements ModelDocumentContributor<Folder> {

	@Override
	public void contribute(Document document, Folder folder) {
		ExpandoBridge expandoBridge = folder.getExpandoBridge();

		document.addKeyword(Field.FOLDER_ID, folder.getFolderId());
		document.addText(Field.NAME, folder.getDisplayName());

		document.addKeyword("accountId", folder.getAccountId());

		ExpandoBridgeIndexerUtil.addAttributes(document, expandoBridge);
	}

}