/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bookmarks.internal.model.listener;

import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.staging.model.listener.StagingModelListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Akos Thurzo
 */
@Component(service = ModelListener.class)
public class BookmarksEntryStagingModelListener
	extends BaseModelListener<BookmarksEntry> {

	@Override
	public void onAfterCreate(BookmarksEntry bookmarksEntry)
		throws ModelListenerException {

		_stagingModelListener.onAfterCreate(bookmarksEntry);
	}

	@Override
	public void onAfterRemove(BookmarksEntry bookmarksEntry)
		throws ModelListenerException {

		_stagingModelListener.onAfterRemove(bookmarksEntry);
	}

	@Override
	public void onAfterUpdate(
			BookmarksEntry originalBookmarksEntry,
			BookmarksEntry bookmarksEntry)
		throws ModelListenerException {

		_stagingModelListener.onAfterUpdate(bookmarksEntry);
	}

	@Reference
	private StagingModelListener<BookmarksEntry> _stagingModelListener;

}