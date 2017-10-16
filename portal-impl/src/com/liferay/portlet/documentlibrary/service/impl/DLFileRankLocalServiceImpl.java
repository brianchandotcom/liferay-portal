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

package com.liferay.portlet.documentlibrary.service.impl;

import com.liferay.document.library.kernel.model.DLFileRank;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portlet.documentlibrary.service.base.DLFileRankLocalServiceBaseImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @deprecated As of 7.0.0, replaced by {@link
 *            com.liferay.document.library.file.rank.service.impl.DLFileRankLocalServiceImpl}
 */
@Deprecated
public class DLFileRankLocalServiceImpl extends DLFileRankLocalServiceBaseImpl {

	@Override
	public DLFileRank addFileRank(
		long groupId, long companyId, long userId, long fileEntryId,
		ServiceContext serviceContext) {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.document.library.file.rank.service.impl." +
					"DLFileRankLocalServiceImpl");
	}

	@Override
	public void checkFileRanks() {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.document.library.file.rank.service.impl." +
					"DLFileRankLocalServiceImpl");
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public void deleteFileRank(DLFileRank dlFileRank) {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.document.library.file.rank.service.impl." +
					"DLFileRankLocalServiceImpl");
	}

	@Override
	public void deleteFileRank(long fileRankId) throws PortalException {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.document.library.file.rank.service.impl." +
					"DLFileRankLocalServiceImpl");
	}

	@Override
	public void deleteFileRanksByFileEntryId(long fileEntryId) {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.document.library.file.rank.service.impl." +
					"DLFileRankLocalServiceImpl");
	}

	@Override
	public void deleteFileRanksByUserId(long userId) {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.document.library.file.rank.service.impl." +
					"DLFileRankLocalServiceImpl");
	}

	@Override
	public void disableFileRanks(long fileEntryId) {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.document.library.file.rank.service.impl." +
					"DLFileRankLocalServiceImpl");
	}

	@Override
	public void disableFileRanksByFolderId(long folderId)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.document.library.file.rank.service.impl." +
					"DLFileRankLocalServiceImpl");
	}

	@Override
	public void enableFileRanks(long fileEntryId) {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.document.library.file.rank.service.impl." +
					"DLFileRankLocalServiceImpl");
	}

	@Override
	public void enableFileRanksByFolderId(long folderId)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.document.library.file.rank.service.impl." +
					"DLFileRankLocalServiceImpl");
	}

	@Override
	public List<DLFileRank> getFileRanks(long groupId, long userId) {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.document.library.file.rank.service.impl." +
					"DLFileRankLocalServiceImpl");
	}

	@Override
	public DLFileRank updateFileRank(
		long groupId, long companyId, long userId, long fileEntryId,
		ServiceContext serviceContext) {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.document.library.file.rank.service.impl." +
					"DLFileRankLocalServiceImpl");
	}

	protected void updateFileRanks(DLFolder dlFolder, boolean active) {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.document.library.file.rank.service.impl." +
					"DLFileRankLocalServiceImpl");
	}

}