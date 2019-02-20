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

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentHelper;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.RelatedEntryIndexer;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.document.library.kernel.model.DLFileEntry",
	service = ModelDocumentContributor.class
)
public class DLFileEntryRelatedEntryModelDocumentContributor
	implements ModelDocumentContributor<DLFileEntry> {

	@Override
	public void contribute(Document document, DLFileEntry dlFileEntry) {
		if (!dlFileEntry.isInHiddenFolder()) {
			return;
		}

		try {
			relatedEntryIndexer.addRelatedEntryFields(
				document, new LiferayFileEntry(dlFileEntry));

			DocumentHelper documentHelper = new DocumentHelper(document);

			documentHelper.setAttachmentOwnerKey(
				portal.getClassNameId(dlFileEntry.getClassName()),
				dlFileEntry.getClassPK());

			document.addKeyword(Field.RELATED_ENTRY, true);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@Reference
	protected Portal portal;

	@Reference(
		target = "(related.entry.indexer.class.name=com.liferay.message.boards.model.MBMessage)"
	)
	protected RelatedEntryIndexer relatedEntryIndexer;

}