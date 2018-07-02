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

/**
 * @author Roberto Díaz
 */
public interface MBListDisplayContext extends MBDisplayContext {

	public int getCategoryEntriesDelta();

	public int getThreadEntriesDelta();

	public boolean isShowMyPosts();

	public boolean isShowRecentPosts();

	public boolean isShowSearch();

	public void populateCategoriesResultsAndTotal(
			SearchContainer searchContainer)
		throws PortalException;

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public void populateResultsAndTotal(SearchContainer searchContainer)
		throws PortalException;

	public void populateThreadsResultsAndTotal(SearchContainer searchContainer)
		throws PortalException;

	public void setCategoryEntriesDelta(SearchContainer searchContainer);

	public void setThreadEntriesDelta(SearchContainer searchContainer);

}