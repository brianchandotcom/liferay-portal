/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.model.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class JournalArticleResourceImpl extends JournalArticleResourceBaseImpl {

	@Override
	public long getLatestArticlePK() {
		JournalArticle journalArticle =
			JournalArticleLocalServiceUtil.fetchLatestArticle(
				getResourcePrimKey());

		return journalArticle.getId();
	}

}