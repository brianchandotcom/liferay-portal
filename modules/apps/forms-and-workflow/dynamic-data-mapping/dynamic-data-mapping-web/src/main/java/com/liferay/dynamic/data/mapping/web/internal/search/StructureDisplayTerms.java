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

package com.liferay.dynamic.data.mapping.web.internal.search;

import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.PortletRequest;

/**
 * @author Eduardo Lundgren
 */
public class StructureDisplayTerms extends DisplayTerms {

	public static final String DESCRIPTION = "description";

	public static final String NAME = "name";

	public static final String SEARCH_RESTRICTION = "searchRestriction";

	public static final String STORAGE_TYPE = "storageType";

	public StructureDisplayTerms(PortletRequest portletRequest) {
		super(portletRequest);

		description = ParamUtil.getString(portletRequest, DESCRIPTION);
		name = ParamUtil.getString(portletRequest, NAME);
		searchRestriction = ParamUtil.getBoolean(
			portletRequest, SEARCH_RESTRICTION);
		storageType = ParamUtil.getString(portletRequest, STORAGE_TYPE);
	}

	public String getDescription() {
		return description;
	}

	public String getName() {
		return name;
	}

	public String getStorageType() {
		return storageType;
	}

	public Boolean isSearchRestriction() {
		return searchRestriction;
	}

	protected String description;
	protected String name;
	protected Boolean searchRestriction;
	protected String storageType;

}