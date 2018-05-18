/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.item.selector.web.internal.display.context;

import com.liferay.commerce.product.display.context.util.CPRequestHelper;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public abstract class BaseCommerceItemSelectorViewDisplayContext<T> {

	public BaseCommerceItemSelectorViewDisplayContext(
		HttpServletRequest httpServletRequest, PortletURL portletURL,
		String itemSelectedEventName) {

		this.httpServletRequest = httpServletRequest;
		_portletURL = portletURL;
		this.itemSelectedEventName = itemSelectedEventName;

		cpRequestHelper = new CPRequestHelper(httpServletRequest);

		_defaultOrderByCol = "name";
		_defaultOrderByType = "asc";
	}

	public String getItemSelectedEventName() {
		return itemSelectedEventName;
	}

	public String getOrderByCol() {
		return ParamUtil.getString(
			cpRequestHelper.getRenderRequest(),
			SearchContainer.DEFAULT_ORDER_BY_COL_PARAM, _defaultOrderByCol);
	}

	public String getOrderByType() {
		return ParamUtil.getString(
			cpRequestHelper.getRenderRequest(),
			SearchContainer.DEFAULT_ORDER_BY_TYPE_PARAM, _defaultOrderByType);
	}

	public PortletURL getPortletURL() {
		return _portletURL;
	}

	public abstract SearchContainer<T> getSearchContainer()
		throws PortalException;

	public void setDefaultOrderByCol(String defaultOrderByCol) {
		_defaultOrderByCol = defaultOrderByCol;
	}

	public void setDefaultOrderByType(String defaultOrderByType) {
		_defaultOrderByType = defaultOrderByType;
	}

	protected String getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(
			cpRequestHelper.getRenderRequest(), "keywords");

		return _keywords;
	}

	protected final CPRequestHelper cpRequestHelper;
	protected final HttpServletRequest httpServletRequest;
	protected final String itemSelectedEventName;
	protected SearchContainer<T> searchContainer;

	private String _defaultOrderByCol;
	private String _defaultOrderByType;
	private String _keywords;
	private final PortletURL _portletURL;

}