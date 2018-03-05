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

package com.liferay.bookmarks.uad.display;

import com.liferay.bookmarks.uad.constants.BookmarksUADConstants;
import com.liferay.bookmarks.uad.entity.BookmarksEntryUADEntity;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.user.associated.data.anonymizer.UADEntityAnonymizer;
import com.liferay.user.associated.data.display.BaseUADEntityDisplay;
import com.liferay.user.associated.data.display.UADEntityDisplay;
import com.liferay.user.associated.data.entity.UADEntity;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author William Newbury
 */
@Component(
	immediate = true,
	property = "model.class.name=" + BookmarksUADConstants.CLASS_NAME_BOOKMARKS_ENTRY,
	service = UADEntityDisplay.class
)
public class BookmarksEntryUADEntityDisplay extends BaseUADEntityDisplay {

	@Override
	public String getEditURL(
			UADEntity uadEntity, LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		BookmarksEntryUADEntity bookmarksEntryUADEntity =
			(BookmarksEntryUADEntity)uadEntity;

		return _bookmarksEntryUADEntityDisplayHelper.getBookmarksEntryEditURL(
			bookmarksEntryUADEntity.getBookmarksEntry(), liferayPortletRequest,
			liferayPortletResponse);
	}

	@Override
	public String getUADEntityTypeDescription() {
		return "A link to another page or website";
	}

	@Override
	public String getUADEntityTypeName() {
		return "BookmarksEntry";
	}

	@Override
	public List<String> getUADEntityTypeNonanonymizableFieldNamesList() {
		return _uadEntityAnonymizer.getUADEntityNonanonymizableFieldNames();
	}

	@Reference
	private BookmarksEntryUADEntityDisplayHelper
		_bookmarksEntryUADEntityDisplayHelper;

	@Reference(
		target = "(model.class.name=" + BookmarksUADConstants.CLASS_NAME_BOOKMARKS_ENTRY + ")"
	)
	private UADEntityAnonymizer _uadEntityAnonymizer;

}