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

package com.liferay.portal.kernel.dao.search;

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class DisplayTerms {

	public static final String ADVANCED_SEARCH = "advancedSearch";

	public static final String AND_OPERATOR = "andOperator";

	public static final String KEYWORDS = "keywords";

	public DisplayTerms(HttpServletRequest httpServletRequest) {
		advancedSearch = ParamUtil.getBoolean(
			httpServletRequest, ADVANCED_SEARCH);
		andOperator = ParamUtil.getBoolean(
			httpServletRequest, AND_OPERATOR, true);
		keywords = ParamUtil.getString(httpServletRequest, KEYWORDS);
	}

	public DisplayTerms(PortletRequest portletRequest) {
		advancedSearch = ParamUtil.getBoolean(portletRequest, ADVANCED_SEARCH);
		andOperator = ParamUtil.getBoolean(portletRequest, AND_OPERATOR, true);
		keywords = ParamUtil.getString(portletRequest, KEYWORDS);
	}

	public String getKeywords() {
		return keywords;
	}

	public boolean isAdvancedSearch() {
		return advancedSearch;
	}

	public boolean isAndOperator() {
		return andOperator;
	}

	public boolean isSearch() {
		if (advancedSearch || Validator.isNotNull(keywords)) {
			return true;
		}

		return false;
	}

	public void setAdvancedSearch(boolean advancedSearch) {
		this.advancedSearch = advancedSearch;
	}

	protected boolean advancedSearch;
	protected boolean andOperator;
	protected String keywords;

}