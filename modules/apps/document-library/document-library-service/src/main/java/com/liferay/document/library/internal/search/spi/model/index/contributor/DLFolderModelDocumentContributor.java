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

package com.liferay.document.library.internal.search.spi.model.index.contributor;

import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;
import com.liferay.trash.TrashHelper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	property = "indexer.class.name=com.liferay.document.library.kernel.model.DLFolder",
	service = ModelDocumentContributor.class
)
public class DLFolderModelDocumentContributor
	implements ModelDocumentContributor<DLFolder> {

	@Override
	public void contribute(Document document, DLFolder dlFolder) {
		if (_log.isDebugEnabled()) {
			_log.debug("Indexing folder " + dlFolder);
		}

		document.addText(Field.DESCRIPTION, dlFolder.getDescription());
		document.addKeyword(Field.FOLDER_ID, dlFolder.getParentFolderId());
		document.addKeyword(
			Field.HIDDEN, dlFolder.isHidden() || dlFolder.isInHiddenFolder());

		String title = dlFolder.getName();

		if (dlFolder.isInTrash()) {
			title = trashHelper.getOriginalTitle(title);
		}

		document.addText(Field.TITLE, title);

		document.addKeyword(Field.TREE_PATH, dlFolder.getTreePath());
		document.addKeyword(
			Field.TREE_PATH,
			StringUtil.split(dlFolder.getTreePath(), CharPool.SLASH));

		if (_log.isDebugEnabled()) {
			_log.debug("Document " + dlFolder + " indexed successfully");
		}
	}

	@Reference
	protected TrashHelper trashHelper;

	private static final Log _log = LogFactoryUtil.getLog(
		DLFolderModelDocumentContributor.class);

}