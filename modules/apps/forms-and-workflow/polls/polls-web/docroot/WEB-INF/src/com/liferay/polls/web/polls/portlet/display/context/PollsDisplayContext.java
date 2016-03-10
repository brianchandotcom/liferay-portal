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

package com.liferay.polls.web.polls.portlet.display.context;

import com.liferay.polls.model.PollsQuestion;
import com.liferay.polls.util.comparator.PollsQuestionCreateDateComparator;
import com.liferay.polls.util.comparator.PollsQuestionTitleComparator;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Rafael Praxedes
 */
public class PollsDisplayContext {

	public PollsDisplayContext(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortalException {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
	}

	public PortletURL getBasePortletURL() throws PortalException {
		PortletURL portletURL = _renderResponse.createRenderURL();

		String keywords = ParamUtil.getString(_renderRequest, "keywords");

		if (Validator.isNotNull(keywords)) {
			portletURL.setParameter("keywords", keywords);
		}

		return portletURL;
	}

	public String getOrderByCol() {
		return ParamUtil.getString(_renderRequest, "orderByCol", "create-date");
	}

	public String getOrderByType() {
		return ParamUtil.getString(_renderRequest, "orderByType", "desc");
	}

	public String[] getOrderColumns() {
		return _ORDER_COLUMNS;
	}

	public String getPollsQuestionDisplayStyle() {
		return _DISPLAY_VIEWS[0];
	}

	public String[] getPollsQuestionDisplayViews() {
		return _DISPLAY_VIEWS;
	}

	public OrderByComparator<PollsQuestion>
		getPollsQuestionOrderByComparator() {

		boolean orderByAsc = false;
		String orderByCol = getOrderByCol();
		String orderByType = getOrderByType();

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<PollsQuestion> orderByComparator = null;

		if (orderByCol.equals("create-date")) {
			orderByComparator = new PollsQuestionCreateDateComparator(
				orderByAsc);
		}
		else if (orderByCol.equals("title")) {
			orderByComparator = new PollsQuestionTitleComparator(orderByAsc);
		}

		return orderByComparator;
	}

	private static final String[] _DISPLAY_VIEWS = {"list"};

	private static final String[] _ORDER_COLUMNS = {"create-date", "title"};

	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;

}