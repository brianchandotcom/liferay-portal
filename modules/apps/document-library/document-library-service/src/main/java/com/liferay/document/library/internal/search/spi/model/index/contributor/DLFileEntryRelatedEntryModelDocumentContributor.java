/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		catch (Exception exception) {
			throw new SystemException(exception);
		}
	}

	@Reference
	protected Portal portal;

	@Reference(
		target = "(related.entry.indexer.class.name=com.liferay.message.boards.model.MBMessage)"
	)
	protected RelatedEntryIndexer relatedEntryIndexer;

}