/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.util;

import com.liferay.portal.kernel.upgrade.util.BaseUpgradeColumnImpl;
import com.liferay.portal.kernel.upgrade.util.UpgradeColumn;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.blogs.util.BlogsUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
public class BlogsEntryUrlTitleUpgradeColumnImpl extends BaseUpgradeColumnImpl {

	public BlogsEntryUrlTitleUpgradeColumnImpl(
		UpgradeColumn entryIdColumn, UpgradeColumn titleColumn) {

		super("urlTitle");

		_entryIdColumn = entryIdColumn;
		_titleColumn = titleColumn;
		_urlTitles = new HashSet<String>();
	}

	@Override
	public Object getNewValue(Object oldValue) throws Exception {
		//String oldUrlTitle = (String)oldValue;
		String oldUrlTitle = StringPool.BLANK;

		String newUrlTitle = oldUrlTitle;

		if (Validator.isNull(oldUrlTitle)) {
			long entryId = ((Long)_entryIdColumn.getOldValue()).longValue();

			String title = (String)_titleColumn.getOldValue();

			newUrlTitle = getUrlTitle(entryId, title);

			_urlTitles.add(newUrlTitle);
		}

		return newUrlTitle;
	}

	protected String getUrlTitle(long entryId, String title) {
		String urlTitle = BlogsUtil.getUrlTitle(entryId, title);

		String newUrlTitle = urlTitle;

		for (int i = 1;; i++) {
			if (!_urlTitles.contains(newUrlTitle)) {
				break;
			}

			newUrlTitle = urlTitle + "_" + i;
		}

		return newUrlTitle;
	}

	private UpgradeColumn _entryIdColumn;
	private UpgradeColumn _titleColumn;
	private Set<String> _urlTitles;

}