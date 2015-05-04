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

package com.liferay.document.library.item.selector.web.search;

import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.PortletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Vilmos Papp
 */
public class FileEntryDisplayTerms extends DisplayTerms {

	public static final String DOCUMENT = "document";

	public static final String DOWNLOADS = "downloads";

	public static final String LOCKED = "locked";

	public static final String SELECTED_GROUP_ID = "selectedGroupId";

	public static final String SIZE = "size";

	public FileEntryDisplayTerms(PortletRequest portletRequest) {
		super(portletRequest);

		document = ParamUtil.getString(portletRequest, DOCUMENT);
		downloads = ParamUtil.getInteger(portletRequest, DOWNLOADS);
		keywords = ParamUtil.getString(portletRequest, DisplayTerms.KEYWORDS);
		locked = ParamUtil.getBoolean(portletRequest, LOCKED);
		selectedGroupId = ParamUtil.getLong(portletRequest, SELECTED_GROUP_ID);
		size = ParamUtil.getString(portletRequest, SIZE);
	}

	public String getDocument() {
		return document;
	}

	public int getDownloads() {
		return downloads;
	}

	public long getSelectedGroupId() {
		return selectedGroupId;
	}

	public String getSize() {
		return size;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setSelectedGroupId(long selectedGroupId) {
		this.selectedGroupId = selectedGroupId;
	}

	protected String document;
	protected int downloads;
	protected boolean locked;
	protected long selectedGroupId;
	protected String size;

}