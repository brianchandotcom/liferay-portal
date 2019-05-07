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

package com.liferay.wiki.display.context;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.servlet.taglib.ui.Menu;
import com.liferay.wiki.model.WikiPage;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Iván Zaera
 * @author Roberto Díaz
 */
public class BaseWikiListPagesDisplayContext
	extends BaseWikiDisplayContext<WikiListPagesDisplayContext>
	implements WikiListPagesDisplayContext {

	public BaseWikiListPagesDisplayContext(
		UUID uuid, WikiListPagesDisplayContext parentDisplayContext,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		super(
			uuid, parentDisplayContext, httpServletRequest,
			httpServletResponse);
	}

	@Override
	public String getEmptyResultsMessage() {
		return parentDisplayContext.getEmptyResultsMessage();
	}

	@Override
	public Menu getMenu(WikiPage wikiPage) throws PortalException {
		return parentDisplayContext.getMenu(wikiPage);
	}

	@Override
	public void populateResultsAndTotal(SearchContainer searchContainer)
		throws PortalException {

		parentDisplayContext.populateResultsAndTotal(searchContainer);
	}

}