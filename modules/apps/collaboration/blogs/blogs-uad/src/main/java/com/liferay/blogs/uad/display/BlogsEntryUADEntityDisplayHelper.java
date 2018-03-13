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

import com.liferay.petra.string.StringPool;

import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = BlogsEntryUADEntityDisplayHelper.class)
public class BlogsEntryUADEntityDisplayHelper {
	/**
	 * Implement getBlogsEntryEditURL() to enable editing BlogsEntries from the GDPR UI.
	 *
	 * <p>
	 * Editing BlogsEntries in the GDPR UI depends on generating valid edit URLs. Implement getBlogsEntryEditURL() such that it returns a valid edit URL for the specified BlogsEntry.
	 * </p>
	 *
	 */
	public String getBlogsEntryEditURL(BlogsEntry blogsEntry,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {
		return StringPool.BLANK;
	}
}