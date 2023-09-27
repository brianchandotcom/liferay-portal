/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
public class BlogsEntryDataFileEntryIdUpgradeProcess extends UpgradeProcess {

	public BlogsEntryDataFileEntryIdUpgradeProcess(
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
		catch (PortalException portalException) {
			throw new UpgradeException(portalException);
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