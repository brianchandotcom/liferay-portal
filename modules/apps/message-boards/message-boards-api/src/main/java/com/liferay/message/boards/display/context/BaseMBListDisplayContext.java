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

package com.liferay.message.boards.display.context;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Roberto Díaz
 */
public class BaseMBListDisplayContext
	extends BaseMBDisplayContext<MBListDisplayContext>
	implements MBListDisplayContext {

	public BaseMBListDisplayContext(
		UUID uuid, MBListDisplayContext parentDisplayContext,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		super(
			uuid, parentDisplayContext, httpServletRequest,
			httpServletResponse);
	}

	@Override
	public int getCategoryEntriesDelta() {
		return parentDisplayContext.getCategoryEntriesDelta();
	}

	@Override
	public int getThreadEntriesDelta() {
		return parentDisplayContext.getThreadEntriesDelta();
	}

	@Override
	public boolean isShowMyPosts() {
		return parentDisplayContext.isShowMyPosts();
	}

	@Override
	public boolean isShowRecentPosts() {
		return parentDisplayContext.isShowRecentPosts();
	}

	@Override
	public boolean isShowSearch() {
		return parentDisplayContext.isShowSearch();
	}

	@Override
	public void populateCategoriesResultsAndTotal(
			SearchContainer searchContainer)
		throws PortalException {

		parentDisplayContext.populateCategoriesResultsAndTotal(searchContainer);
	}

	@Override
	public void populateThreadsResultsAndTotal(SearchContainer searchContainer)
		throws PortalException {

		parentDisplayContext.populateThreadsResultsAndTotal(searchContainer);
	}

	@Override
	public void setCategoryEntriesDelta(SearchContainer searchContainer) {
		parentDisplayContext.setCategoryEntriesDelta(searchContainer);
	}

	@Override
	public void setThreadEntriesDelta(SearchContainer searchContainer) {
		parentDisplayContext.setThreadEntriesDelta(searchContainer);
	}

}