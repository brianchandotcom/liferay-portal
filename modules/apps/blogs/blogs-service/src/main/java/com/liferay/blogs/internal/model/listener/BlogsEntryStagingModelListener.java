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
@Component(immediate = true, service = ModelListener.class)
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
	public void onAfterUpdate(BlogsEntry blogsEntry)
		throws ModelListenerException {

		_stagingModelListener.onAfterUpdate(blogsEntry);
	}

	@Reference
	private StagingModelListener<BlogsEntry> _stagingModelListener;

}