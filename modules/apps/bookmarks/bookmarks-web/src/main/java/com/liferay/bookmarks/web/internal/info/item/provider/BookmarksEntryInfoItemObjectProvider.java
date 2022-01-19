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

package com.liferay.bookmarks.web.internal.info.item.provider;

import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.service.BookmarksEntryLocalService;
import com.liferay.info.exception.NoSuchInfoItemException;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Yang Cao
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"info.item.identifier=com.liferay.info.item.ClassPKInfoItemIdentifier",
		"item.class.name=com.liferay.bookmarks.model.BookmarksEntry",
		"service.ranking:Integer=100"
	},
	service = InfoItemObjectProvider.class
)
public class BookmarksEntryInfoItemObjectProvider
	implements InfoItemObjectProvider<BookmarksEntry> {

	@Override
	public BookmarksEntry getInfoItem(InfoItemIdentifier infoItemIdentifier)
		throws NoSuchInfoItemException {

		if (!(infoItemIdentifier instanceof ClassPKInfoItemIdentifier)) {
			throw new NoSuchInfoItemException(
				"Unsupported info item identifier type " + infoItemIdentifier);
		}

		BookmarksEntry bookmarksEntry = null;

		try {
			ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
				(ClassPKInfoItemIdentifier)infoItemIdentifier;

			bookmarksEntry = _bookmarksEntryLocalService.getEntry(
				classPKInfoItemIdentifier.getClassPK());
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}

		if (bookmarksEntry == null) {
			throw new NoSuchInfoItemException(
				"Unable to get bookmarks entry with info item identifier " +
					infoItemIdentifier);
		}

		return bookmarksEntry;
	}

	@Override
	public BookmarksEntry getInfoItem(long classPK)
		throws NoSuchInfoItemException {

		InfoItemIdentifier infoItemIdentifier = new ClassPKInfoItemIdentifier(
			classPK);

		return getInfoItem(infoItemIdentifier);
	}

	@Reference
	private BookmarksEntryLocalService _bookmarksEntryLocalService;

}