/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blogs.reading.time.internal.model.listener;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.reading.time.service.ReadingTimeEntryLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = ModelListener.class)
public class BlogsEntryReadingTimeModelListener
	extends BaseModelListener<BlogsEntry> {

	@Override
	public void onAfterCreate(BlogsEntry blogsEntry)
		throws ModelListenerException {

		super.onAfterCreate(blogsEntry);

		_readingTimeEntryLocalService.updateReadingTimeEntry(blogsEntry);
	}

	@Override
	public void onAfterUpdate(
			BlogsEntry originalBlogsEntry, BlogsEntry blogsEntry)
		throws ModelListenerException {

		super.onAfterUpdate(originalBlogsEntry, blogsEntry);

		_readingTimeEntryLocalService.updateReadingTimeEntry(blogsEntry);
	}

	@Override
	public void onBeforeRemove(BlogsEntry blogsEntry)
		throws ModelListenerException {

		super.onBeforeRemove(blogsEntry);

		_readingTimeEntryLocalService.deleteReadingTimeEntry(blogsEntry);
	}

	@Reference
	private ReadingTimeEntryLocalService _readingTimeEntryLocalService;

}