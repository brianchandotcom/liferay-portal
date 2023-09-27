/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bookmarks.internal.model.listener;

import com.liferay.bookmarks.model.BookmarksFolder;
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
public class BookmarksFolderStagingModelListener
	extends BaseModelListener<BookmarksFolder> {

	@Override
	public void onAfterCreate(BookmarksFolder bookmarksFolder)
		throws ModelListenerException {

		_stagingModelListener.onAfterCreate(bookmarksFolder);
	}

	@Override
	public void onAfterRemove(BookmarksFolder bookmarksFolder)
		throws ModelListenerException {

		_stagingModelListener.onAfterRemove(bookmarksFolder);
	}

	@Override
	public void onAfterUpdate(
			BookmarksFolder originalBookmarksFolder,
			BookmarksFolder bookmarksFolder)
		throws ModelListenerException {

		_stagingModelListener.onAfterUpdate(bookmarksFolder);
	}

	@Reference
	private StagingModelListener<BookmarksFolder> _stagingModelListener;

}