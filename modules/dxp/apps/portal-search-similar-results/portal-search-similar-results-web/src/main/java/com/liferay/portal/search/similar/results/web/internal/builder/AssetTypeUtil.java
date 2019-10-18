/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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