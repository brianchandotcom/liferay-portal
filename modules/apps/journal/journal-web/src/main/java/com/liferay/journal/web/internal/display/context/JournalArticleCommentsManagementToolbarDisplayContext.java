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

package com.liferay.journal.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.trash.TrashHelper;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class JournalArticleCommentsManagementToolbarDisplayContext
	extends JournalManagementToolbarDisplayContext {

	public JournalArticleCommentsManagementToolbarDisplayContext(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			HttpServletRequest httpServletRequest,
			JournalDisplayContext journalDisplayContext,
			TrashHelper trashHelper)
		throws PortalException {

		super(
			liferayPortletRequest, liferayPortletResponse, httpServletRequest,
			journalDisplayContext, trashHelper);
	}

	@Override
	public List<DropdownItem> getFilterDropdownItems() {
		return null;
	}

	@Override
	public Boolean isSelectable() {
		return false;
	}

}