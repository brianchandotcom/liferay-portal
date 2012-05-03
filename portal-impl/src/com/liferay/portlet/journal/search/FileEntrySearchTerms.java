/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.search;

import com.liferay.portal.kernel.dao.search.DAOParamUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.PortletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Vilmos Papp
 */
public class FileEntrySearchTerms extends FileEntryDisplayTerms {

	public FileEntrySearchTerms(PortletRequest portletRequest) {
		super(portletRequest);

		document = DAOParamUtil.getString(portletRequest, DOCUMENT);
		downloads = ParamUtil.getInteger(portletRequest, DOWNLOADS);
		selectedGroupId = DAOParamUtil.getLong(
			portletRequest, SELECTED_GROUP_ID);

		locked = DAOParamUtil.getBoolean(portletRequest, LOCKED);
		size = DAOParamUtil.getString(portletRequest, SIZE);
	}

	public void setSelectedGroupId(long selectedGroupId) {
		this.selectedGroupId = selectedGroupId;
	}

}