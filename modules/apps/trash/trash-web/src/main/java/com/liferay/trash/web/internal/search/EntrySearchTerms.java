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

package com.liferay.trash.web.internal.search;

import com.liferay.portal.kernel.dao.search.DAOParamUtil;

import javax.portlet.PortletRequest;

/**
 * Defines search terms used by the <code>SearchContainer</code> (in
 * <code>com.liferay.portal.kernel</code>) to filter Recycle Bin entries.
 *
 * <p>
 * Supported search parameters:
 * </p>
 *
 * <ul>
 * <li>
 * <code>keywords</code> - the keywords for which to search in the entries
 * content
 * </li>
 * <li>
 * <code>name</code> - the name of the entry
 * </li>
 * <li>
 * <code>removedDate</code> - the date the entry was moved to the Recycle Bin
 * </li>
 * <li>
 * <code>removedBy</code> - the user who moved the entry to the Recycle Bin
 * </li>
 * <li>
 * <code>type</code> - the type of entry that was moved to the Recycle Bin
 * </li>
 * </ul>
 *
 * @author Sergio González
 */
public class EntrySearchTerms extends EntryDisplayTerms {

	public EntrySearchTerms(PortletRequest portletRequest) {
		super(portletRequest);

		keywords = DAOParamUtil.getString(portletRequest, KEYWORDS);
		name = DAOParamUtil.getString(portletRequest, NAME);
		removedDate = DAOParamUtil.getString(portletRequest, REMOVED_DATE);
		removedBy = DAOParamUtil.getString(portletRequest, REMOVED_BY);
		type = DAOParamUtil.getString(portletRequest, TYPE);
	}

}