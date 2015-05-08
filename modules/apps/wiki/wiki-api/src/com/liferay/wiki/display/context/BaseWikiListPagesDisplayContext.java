/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p/>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p/>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.wiki.display.context;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.servlet.taglib.ui.Menu;
import com.liferay.portal.kernel.servlet.taglib.ui.MenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.ToolbarItem;
import com.liferay.wiki.model.WikiPage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

/**
 * @author Iván Zaera
 */
public class BaseWikiListPagesDisplayContext
	extends  BaseWikiDisplayContext<WikiListPagesDisplayContext>
	implements WikiListPagesDisplayContext {

	public BaseWikiListPagesDisplayContext(
		UUID uuid, WikiListPagesDisplayContext parentDisplayContext,
		HttpServletRequest request, HttpServletResponse response) {

		super(uuid, parentDisplayContext, request, response);
	}

	@Override
	public List<ToolbarItem> getToolbarItems() throws PortalException {
		return parentDisplayContext.getToolbarItems();
	}

	@Override
	public Menu getMenu(WikiPage wikiPage) throws PortalException {
		return parentDisplayContext.getMenu(wikiPage);
	}

}
