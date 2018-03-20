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

import com.liferay.blogs.uad.constants.BlogsUADConstants;
import com.liferay.blogs.uad.entity.BlogsEntryUADEntity;

import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;

import com.liferay.user.associated.data.anonymizer.UADEntityAnonymizer;
import com.liferay.user.associated.data.display.UADEntityDisplay;
import com.liferay.user.associated.data.entity.UADEntity;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(immediate = true, property =  {
	"model.class.name=" + BlogsUADConstants.CLASS_NAME_BLOGS_ENTRY}, service = UADEntityDisplay.class)
public class BlogsEntryUADEntityDisplay implements UADEntityDisplay {
	public String getApplicationName() {
		return BlogsUADConstants.UAD_ENTITY_SET_NAME;
	}

	public String[] getDisplayFieldNames() {
		return _blogsEntryUADEntityDisplayHelper.getDisplayFieldNames();
	}

	@Override
	public String getEditURL(UADEntity uadEntity,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse)
		throws Exception {
		BlogsEntryUADEntity blogsEntryUADEntity = (BlogsEntryUADEntity)uadEntity;

		return _blogsEntryUADEntityDisplayHelper.getBlogsEntryEditURL(blogsEntryUADEntity.getBlogsEntry(),
			liferayPortletRequest, liferayPortletResponse);
	}

	public String getKey() {
		return BlogsUADConstants.CLASS_NAME_BLOGS_ENTRY;
	}

	@Override
	public Map<String, Object> getUADEntityNonanonymizableFieldValues(
		UADEntity uadEntity) {
		BlogsEntryUADEntity blogsEntryUADEntity = (BlogsEntryUADEntity)uadEntity;

		return _blogsEntryUADEntityDisplayHelper.getUADEntityNonanonymizableFieldValues(blogsEntryUADEntity.getBlogsEntry());
	}

	@Override
	public String getUADEntityTypeDescription() {
		return "A blog post";
	}

	@Override
	public String getUADEntityTypeName() {
		return "BlogsEntry";
	}

	@Reference
	private BlogsEntryUADEntityDisplayHelper _blogsEntryUADEntityDisplayHelper;
	@Reference(target = "(model.class.name=" +
	BlogsUADConstants.CLASS_NAME_BLOGS_ENTRY + ")")
	private UADEntityAnonymizer _uadEntityAnonymizer;
}