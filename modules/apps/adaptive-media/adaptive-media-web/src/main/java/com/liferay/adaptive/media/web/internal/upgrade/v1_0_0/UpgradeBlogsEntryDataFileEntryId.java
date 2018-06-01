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

package com.liferay.adaptive.media.web.internal.upgrade.v1_0_0;

import com.liferay.adaptive.media.image.html.constants.AMImageHTMLConstants;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alejandro Tardín
 */
public class UpgradeBlogsEntryDataFileEntryId extends UpgradeProcess {

	public UpgradeBlogsEntryDataFileEntryId(
		BlogsEntryLocalService blogsEntryLocalService) {

		_blogsEntryLocalService = blogsEntryLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try {
			ActionableDynamicQuery actionableDynamicQuery =
				_blogsEntryLocalService.getActionableDynamicQuery();

			actionableDynamicQuery.setPerformActionMethod(
				(BlogsEntry blogsEntry) -> _upgradeBlogsEntry(blogsEntry));

			actionableDynamicQuery.performActions();
		}
		catch (PortalException pe) {
			throw new UpgradeException(pe);
		}
	}

	private void _upgradeBlogsEntry(BlogsEntry blogsEntry) {
		String content = blogsEntry.getContent();

		Matcher matcher = _dataFileEntryIdPattern.matcher(content);

		String upgradedContent = matcher.replaceAll(
			AMImageHTMLConstants.ATTRIBUTE_NAME_FILE_ENTRY_ID +
				StringPool.EQUAL);

		if (!content.equals(upgradedContent)) {
			blogsEntry.setContent(upgradedContent);

			_blogsEntryLocalService.updateBlogsEntry(blogsEntry);
		}
	}

	private static final Pattern _dataFileEntryIdPattern = Pattern.compile(
		"data-fileEntryId=", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	private final BlogsEntryLocalService _blogsEntryLocalService;

}