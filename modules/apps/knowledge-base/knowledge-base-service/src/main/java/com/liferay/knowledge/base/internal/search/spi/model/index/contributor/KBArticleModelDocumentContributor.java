/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.internal.search.spi.model.index.contributor;

import com.liferay.knowledge.base.constants.KBFolderConstants;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.knowledge.base.service.KBFolderLocalService;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.HtmlParser;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import java.util.ArrayList;
import java.util.Collection;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tibor Lipusz
 */
@Component(
	property = "indexer.class.name=com.liferay.knowledge.base.model.KBArticle",
	service = ModelDocumentContributor.class
)
public class KBArticleModelDocumentContributor
	implements ModelDocumentContributor<KBArticle> {

	@Override
	public void contribute(Document document, KBArticle kbArticle) {
		document.addText(
			Field.CONTENT, _htmlParser.extractText(kbArticle.getContent()));
		document.addText(Field.DESCRIPTION, kbArticle.getDescription());
		document.addKeyword(Field.FOLDER_ID, kbArticle.getKbFolderId());
		document.addText(Field.TITLE, kbArticle.getTitle());

		try {
			document.addKeyword(
				Field.TREE_PATH,
				StringUtil.split(kbArticle.buildTreePath(), CharPool.SLASH));
			document.addKeyword("folderNames", _getKBFolderNames(kbArticle));
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}
		}

		document.addKeyword(
			"parentMessageId", kbArticle.getParentResourcePrimKey());
		document.addKeyword("titleKeyword", kbArticle.getTitle(), true);
		document.addKeywordSortable("urlTitle", kbArticle.getUrlTitle());
	}

	@Reference
	protected KBFolderLocalService kbFolderLocalService;

	private String[] _getKBFolderNames(KBArticle kbArticle)
		throws PortalException {

		long kbFolderId = kbArticle.getKbFolderId();

		Collection<String> kbFolderNames = new ArrayList<>();

		while (kbFolderId != KBFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			KBFolder kbFolder = kbFolderLocalService.getKBFolder(kbFolderId);

			kbFolderNames.add(kbFolder.getName());

			kbFolderId = kbFolder.getParentKBFolderId();
		}

		return kbFolderNames.toArray(new String[0]);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		KBArticleModelDocumentContributor.class);

	@Reference
	private HtmlParser _htmlParser;

}