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

package com.liferay.blogs.web.internal.util;

import com.liferay.blogs.web.internal.display.context.BlogEntriesDisplayContextProvider;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(immediate = true, service = {})
public class BlogsWebComponentProvider {

	public static BlogsWebComponentProvider getBlogsWebComponentProvider() {
		return _blogsWebComponentProvider;
	}

	public BlogEntriesDisplayContextProvider
		getBlogEntriesDisplayContextProvider() {

		return _blogEntriesDisplayContextProvider;
	}

	@Activate
	protected void activate() {
		_blogsWebComponentProvider = this;
	}

	@Deactivate
	protected void deactivate() {
		_blogsWebComponentProvider = null;
	}

	private static BlogsWebComponentProvider _blogsWebComponentProvider;

	@Reference
	private BlogEntriesDisplayContextProvider
		_blogEntriesDisplayContextProvider;

}