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
@Component(immediate = true, service = ModelListener.class)
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
	public void onAfterUpdate(BookmarksFolder bookmarksFolder)
		throws ModelListenerException {

		_stagingModelListener.onAfterUpdate(bookmarksFolder);
	}

	@Reference
	private StagingModelListener<BookmarksFolder> _stagingModelListener;

}