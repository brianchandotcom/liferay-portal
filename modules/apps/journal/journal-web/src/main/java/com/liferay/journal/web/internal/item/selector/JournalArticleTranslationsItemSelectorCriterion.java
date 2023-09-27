/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.web.internal.item.selector;

import com.liferay.item.selector.BaseItemSelectorCriterion;

/**
 * @author Barbara Cabrera
 */
public class JournalArticleTranslationsItemSelectorCriterion
	extends BaseItemSelectorCriterion {

	public String getArticleId() {
		return _articleId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setArticleId(String articleId) {
		_articleId = articleId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	private String _articleId;
	private long _groupId;

}