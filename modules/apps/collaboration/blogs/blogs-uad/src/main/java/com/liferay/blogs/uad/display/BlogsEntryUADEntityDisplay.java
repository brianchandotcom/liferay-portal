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

package com.liferay.blogs.uad.display;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.uad.constants.BlogsUADConstants;

import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;

import com.liferay.user.associated.data.display.UADEntityDisplay;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(immediate = true, property =  {
	"model.class.name=" + BlogsUADConstants.CLASS_NAME_BLOGS_ENTRY}, service = UADEntityDisplay.class)
public class BlogsEntryUADEntityDisplay implements UADEntityDisplay<BlogsEntry> {
	public String getApplicationName() {
		return BlogsUADConstants.APPLICATION_NAME;
	}

	public String[] getDisplayFieldNames() {
		return _blogsEntryUADEntityDisplayHelper.getDisplayFieldNames();
	}

	@Override
	public String getEditURL(BlogsEntry blogsEntry,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse)
		throws Exception {
		return _blogsEntryUADEntityDisplayHelper.getBlogsEntryEditURL(blogsEntry,
			liferayPortletRequest, liferayPortletResponse);
	}

	public String getKey() {
		return BlogsUADConstants.CLASS_NAME_BLOGS_ENTRY;
	}

	@Override
	public Map<String, Object> getNonanonymizableFieldValues(
		BlogsEntry blogsEntry) {
		return _blogsEntryUADEntityDisplayHelper.getUADEntityNonanonymizableFieldValues(blogsEntry);
	}

	@Override
	public String getTypeDescription() {
		return "A blog post";
	}

	@Override
	public String getTypeName() {
		return "BlogsEntry";
	}

	@Reference
	private BlogsEntryUADEntityDisplayHelper _blogsEntryUADEntityDisplayHelper;
}