/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.display.context;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Sergio González
 */
public interface MBAdminListDisplayContext extends MBDisplayContext {

	public int getEntriesDelta();

	public boolean isShowSearch();

	public void populateResultsAndTotal(SearchContainer searchContainer)
		throws PortalException;

	public void setEntriesDelta(SearchContainer searchContainer);

}