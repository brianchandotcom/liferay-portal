/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public void populateThreadsResultsAndTotal(SearchContainer searchContainer)
		throws PortalException;

	public void setCategoryEntriesDelta(SearchContainer searchContainer);

	public void setThreadEntriesDelta(SearchContainer searchContainer);

}