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