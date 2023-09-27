/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.similar.results.web.internal.builder;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolder;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.petra.string.StringPool;
import com.liferay.wiki.model.WikiPage;

/**
 * @author André de Oliveira
 */
public class AssetTypeUtil {

	public static String getAssetTypeByClassName(String className) {
		String assetType = StringPool.BLANK;

		if (className.equals(MBMessage.class.getName())) {
			assetType = "message";
		}
		else if (className.equals(MBCategory.class.getName())) {
			assetType = "category";
		}
		else if (className.equals(JournalArticle.class.getName())) {
			assetType = "content";
		}
		else if (className.equals(WikiPage.class.getName())) {
			assetType = "wiki";
		}
		else if (className.equals(JournalFolder.class.getName())) {
			assetType = "content_folder";
		}
		else if (className.equals(DLFileEntry.class.getName())) {
			assetType = "document";
		}
		else if (className.equals(BlogsEntry.class.getName())) {
			assetType = "blog";
		}
		else if (className.equals(DLFolder.class.getName())) {
			assetType = "document_folder";
		}

		return assetType;
	}

}