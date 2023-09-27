/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.wiki.internal.search;

import com.liferay.portal.kernel.comment.Comment;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.capabilities.RelatedModelCapability;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.search.BaseRelatedEntryIndexer;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.RelatedEntryIndexer;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiPageLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Joao Victor Alves
 */
@Component(
	property = "related.entry.indexer.class.name=com.liferay.wiki.model.WikiPage",
	service = RelatedEntryIndexer.class
)
public class WikiPageRelatedEntryIndexer extends BaseRelatedEntryIndexer {

	@Override
	public void addRelatedEntryFields(Document document, Object object)
		throws Exception {

		long classPK = 0;

		if (object instanceof Comment) {
			Comment comment = (Comment)object;

			classPK = comment.getClassPK();
		}
		else if (object instanceof FileEntry) {
			FileEntry fileEntry = (FileEntry)object;

			RelatedModelCapability relatedModelCapability =
				fileEntry.getRepositoryCapability(RelatedModelCapability.class);

			classPK = relatedModelCapability.getClassPK(fileEntry);
		}

		WikiPage page = null;

		try {
			page = _wikiPageLocalService.getPage(classPK);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return;
		}

		document.addKeyword(Field.NODE_ID, page.getNodeId());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WikiPageRelatedEntryIndexer.class);

	@Reference
	private WikiPageLocalService _wikiPageLocalService;

}