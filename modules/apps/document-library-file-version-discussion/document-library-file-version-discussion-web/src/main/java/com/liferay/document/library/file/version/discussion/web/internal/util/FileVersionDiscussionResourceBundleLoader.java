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

package com.liferay.document.library.file.version.discussion.web.internal.util;

import com.liferay.portal.kernel.util.ClassResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleLoader;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	property = "bundle.symbolic.name=com.liferay.document.library.file.version.discussion.web",
	service = ResourceBundleLoader.class
)
public class FileVersionDiscussionResourceBundleLoader
	extends ClassResourceBundleLoader {

	public FileVersionDiscussionResourceBundleLoader() {
		super(
			"content.Language",
			FileVersionDiscussionResourceBundleLoader.class);
	}

}