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

package com.liferay.knowledge.base.web.internal.search;

import com.liferay.knowledge.base.constants.KBPortletKeys;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.SearchOrderByUtil;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Peter Shin
 * @author Brian Wing Shun Chan
 */
public class KBObjectsSearch extends SearchContainer<Object> {

	public static final String EMPTY_RESULTS_MESSAGE = "no-articles-were-found";

	public KBObjectsSearch(
		PortletRequest portletRequest, PortletURL iteratorURL) {

		super(
			portletRequest, null, null, DEFAULT_CUR_PARAM, DEFAULT_DELTA,
			iteratorURL, null, EMPTY_RESULTS_MESSAGE);

		try {
			setOrderByCol(
				SearchOrderByUtil.getOrderByCol(
					portletRequest, KBPortletKeys.KNOWLEDGE_BASE_ADMIN,
					"kb-articles-order-by-col", "priority"));
			setOrderByType(
				SearchOrderByUtil.getOrderByType(
					portletRequest, KBPortletKeys.KNOWLEDGE_BASE_ADMIN,
					"kb-articles-order-by-type", "asc"));
		}
		catch (Exception exception) {
			_log.error(
				"Unable to initialize knowledge base objects search",
				exception);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		KBObjectsSearch.class);

}