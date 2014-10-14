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

package com.liferay.portlet.bookmarks.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @author Brian Wing Shun Chan
 */
@ProviderType
public class BookmarksEntryFinderUtil {
	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> findByNoAssets() {
		return getFinder().findByNoAssets();
	}

	public static BookmarksEntryFinder getFinder() {
		if (_finder == null) {
			_finder = (BookmarksEntryFinder)PortalBeanLocatorUtil.locate(BookmarksEntryFinder.class.getName());

			ReferenceRegistry.registerReference(BookmarksEntryFinderUtil.class,
				"_finder");
		}

		return _finder;
	}

	public void setFinder(BookmarksEntryFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(BookmarksEntryFinderUtil.class,
			"_finder");
	}

	private static BookmarksEntryFinder _finder;
}