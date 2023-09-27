/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blogs.internal.model.listener;

import com.liferay.blogs.model.BlogsEntry;
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
public class BlogsEntryStagingModelListener
	extends BaseModelListener<BlogsEntry> {

	@Override
	public void onAfterCreate(BlogsEntry blogsEntry)
		throws ModelListenerException {

		_stagingModelListener.onAfterCreate(blogsEntry);
	}

	@Override
	public void onAfterRemove(BlogsEntry blogsEntry)
		throws ModelListenerException {

		_stagingModelListener.onAfterRemove(blogsEntry);
	}

	@Override
	public void onAfterUpdate(
			BlogsEntry originalBlogsEntry, BlogsEntry blogsEntry)
		throws ModelListenerException {

		_stagingModelListener.onAfterUpdate(blogsEntry);
	}

	@Reference
	private StagingModelListener<BlogsEntry> _stagingModelListener;

}