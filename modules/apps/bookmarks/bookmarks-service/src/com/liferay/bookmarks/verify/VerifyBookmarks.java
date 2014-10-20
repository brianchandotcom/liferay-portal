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

package com.liferay.bookmarks.verify;

import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.bookmarks.service.BookmarksEntryLocalServiceUtil;
import com.liferay.bookmarks.service.BookmarksFolderLocalServiceUtil;
import com.liferay.bookmarks.upgrade.BookmarksServicesUpgrade;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.verify.VerifyProcess;

import java.util.List;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Raymond Augé
 * @author Alexander Chow
 */
@Component(immediate = true, service = VerifyBookmarks.class)
public class VerifyBookmarks extends VerifyProcess {

	@Activate
	@Override
	protected void doVerify() throws Exception {
		updateEntryAssets();
		updateFolderAssets();
		verifyTree();
	}

	@Reference(unbind = "-")
	protected void setBookmarksServicesUpgrade(
		BookmarksServicesUpgrade bookmarksServicesUpgrade) {
	}

	protected void updateEntryAssets() throws Exception {
		List<BookmarksEntry> entries =
			BookmarksEntryLocalServiceUtil.getNoAssetEntries();

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Processing " + entries.size() + " entries with no asset");
		}

		for (BookmarksEntry entry : entries) {
			try {
				BookmarksEntryLocalServiceUtil.updateAsset(
					entry.getUserId(), entry, null, null, null);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to update asset for entry " +
							entry.getEntryId() + ": " + e.getMessage());
				}
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Assets verified for entries");
		}
	}

	protected void updateFolderAssets() throws Exception {
		List<BookmarksFolder> folders =
			BookmarksFolderLocalServiceUtil.getNoAssetFolders();

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Processing " + folders.size() + " folders with no asset");
		}

		for (BookmarksFolder folder : folders) {
			try {
				BookmarksFolderLocalServiceUtil.updateAsset(
					folder.getUserId(), folder, null, null, null);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to update asset for folder " +
							folder.getFolderId() + ": " + e.getMessage());
				}
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Assets verified for folders");
		}
	}

	protected void verifyTree() throws Exception {
		long[] companyIds = PortalInstances.getCompanyIdsBySQL();

		for (long companyId : companyIds) {
			BookmarksFolderLocalServiceUtil.rebuildTree(companyId);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		VerifyBookmarks.class);

}