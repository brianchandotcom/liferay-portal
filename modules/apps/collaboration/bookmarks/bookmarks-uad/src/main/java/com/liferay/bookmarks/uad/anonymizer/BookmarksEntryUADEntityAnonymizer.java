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

package com.liferay.bookmarks.uad.anonymizer;

import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.service.BookmarksEntryLocalService;
import com.liferay.bookmarks.uad.constants.BookmarksUADConstants;
import com.liferay.bookmarks.uad.entity.BookmarksEntryUADEntity;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.user.associated.data.aggregator.UADEntityAggregator;
import com.liferay.user.associated.data.anonymizer.BaseUADEntityAnonymizer;
import com.liferay.user.associated.data.anonymizer.UADEntityAnonymizer;
import com.liferay.user.associated.data.entity.UADEntity;
import com.liferay.user.associated.data.exception.UADEntityException;
import com.liferay.user.associated.data.util.UADAnonymizerHelper;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Noah Sherrill
 */
@Component(
	immediate = true,
	property = {"model.class.name=" + BookmarksUADConstants.BOOKMARKS_ENTRY},
	service = UADEntityAnonymizer.class
)
public class BookmarksEntryUADEntityAnonymizer extends BaseUADEntityAnonymizer {

	@Override
	public void autoAnonymize(UADEntity uadEntity) throws PortalException {
		BookmarksEntry bookmarksEntry = _getBookmarksEntry(uadEntity);

		User anonymousUser = _uadAnonymizerHelper.getAnonymousUser();

		if (bookmarksEntry.getStatusByUserId() == uadEntity.getUserId()) {
			bookmarksEntry.setStatusByUserId(anonymousUser.getUserId());
			bookmarksEntry.setStatusByUserName(anonymousUser.getScreenName());
		}

		if (bookmarksEntry.getUserId() == uadEntity.getUserId()) {
			bookmarksEntry.setUserId(anonymousUser.getUserId());
			bookmarksEntry.setUserName(anonymousUser.getFullName());
		}

		_bookmarksEntryLocalService.updateBookmarksEntry(bookmarksEntry);
	}

	@Override
	public void delete(UADEntity uadEntity) throws PortalException {
		_bookmarksEntryLocalService.deleteEntry(_getBookmarksEntry(uadEntity));
	}

	@Override
	public List<String> getEntityNonAnonymizableFieldNames() {
		return null;
	}

	@Override
	protected List<UADEntity> getUADEntities(long userId) {
		return _uadEntityAggregator.getUADEntities(userId);
	}

	private BookmarksEntry _getBookmarksEntry(UADEntity uadEntity)
		throws PortalException {

		_validate(uadEntity);

		BookmarksEntryUADEntity bookmarksEntryUADEntity =
			(BookmarksEntryUADEntity)uadEntity;

		return bookmarksEntryUADEntity.getBookmarksEntry();
	}

	private void _validate(UADEntity uadEntity) throws PortalException {
		if (!(uadEntity instanceof BookmarksEntryUADEntity)) {
			throw new UADEntityException();
		}
	}

	@Reference
	private BookmarksEntryLocalService _bookmarksEntryLocalService;

	@Reference
	private UADAnonymizerHelper _uadAnonymizerHelper;

	@Reference(
		target = "(model.class.name=" + BookmarksUADConstants.BOOKMARKS_ENTRY + ")"
	)
	private UADEntityAggregator _uadEntityAggregator;

}