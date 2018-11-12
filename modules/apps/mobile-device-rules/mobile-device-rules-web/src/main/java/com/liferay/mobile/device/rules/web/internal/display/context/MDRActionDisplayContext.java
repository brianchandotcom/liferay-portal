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

package com.liferay.mobile.device.rules.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemList;
import com.liferay.mobile.device.rules.model.MDRAction;
import com.liferay.mobile.device.rules.model.MDRRuleGroup;
import com.liferay.mobile.device.rules.model.MDRRuleGroupInstance;
import com.liferay.mobile.device.rules.service.MDRActionLocalServiceUtil;
import com.liferay.mobile.device.rules.service.MDRRuleGroupInstanceLocalServiceUtil;
import com.liferay.mobile.device.rules.util.comparator.ActionCreateDateComparator;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.ResourceBundle;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Juergen Kappler
 */
public class MDRActionDisplayContext {

	public MDRActionDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		ResourceBundle resourceBundle) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_resourceBundle = resourceBundle;
	}

	public List<NavigationItem> getActionNavigationItems()
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		MDRRuleGroupInstance ruleGroupInstance =
			MDRRuleGroupInstanceLocalServiceUtil.getRuleGroupInstance(
				getRuleGroupInstanceId());

		MDRRuleGroup ruleGroup = ruleGroupInstance.getRuleGroup();

		return new NavigationItemList() {
			{
				add(
					navigationItem -> {
						navigationItem.setActive(true);
						navigationItem.setHref(StringPool.BLANK);
						navigationItem.setLabel(
							LanguageUtil.format(
								_resourceBundle, "actions-for-x",
								ruleGroup.getName(themeDisplay.getLocale()),
								false));
					});
			}
		};
	}

	public SearchContainer getActionSearchContainer() {
		if (_ruleActionSearchContainer != null) {
			return _ruleActionSearchContainer;
		}

		long ruleGroupInstanceId = getRuleGroupInstanceId();

		SearchContainer ruleActionSearchContainer = new SearchContainer(
			_renderRequest, getPortletURL(), null,
			"no-actions-are-configured-for-this-device-family");

		ruleActionSearchContainer.setOrderByCol(getOrderByCol());

		String orderByType = getOrderByType();

		boolean orderByAsc = orderByType.equals("asc");

		OrderByComparator<MDRAction> orderByComparator =
			new ActionCreateDateComparator(orderByAsc);

		ruleActionSearchContainer.setOrderByComparator(orderByComparator);

		ruleActionSearchContainer.setOrderByType(orderByType);

		ruleActionSearchContainer.setRowChecker(
			new EmptyOnClickRowChecker(_renderResponse));

		ruleActionSearchContainer.setTotal(
			MDRActionLocalServiceUtil.getActionsCount(ruleGroupInstanceId));

		ruleActionSearchContainer.setResults(
			MDRActionLocalServiceUtil.getActions(
				ruleGroupInstanceId, ruleActionSearchContainer.getStart(),
				ruleActionSearchContainer.getEnd(), orderByComparator));

		_ruleActionSearchContainer = ruleActionSearchContainer;

		return _ruleActionSearchContainer;
	}

	public String getDisplayStyle() {
		if (_displayStyle != null) {
			return _displayStyle;
		}

		_displayStyle = ParamUtil.getString(
			_renderRequest, "displayStyle", "list");

		return _displayStyle;
	}

	public String getOrderByCol() {
		if (_orderByCol != null) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(
			_renderRequest, "orderByCol", "create-date");

		return _orderByCol;
	}

	public String getOrderByType() {
		if (_orderByType != null) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(
			_renderRequest, "orderByType", "asc");

		return _orderByType;
	}

	public PortletURL getPortletURL() {
		if (_portletURL != null) {
			return _portletURL;
		}

		String redirect = ParamUtil.getString(_renderRequest, "redirect");

		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "/view_actions.jsp");
		portletURL.setParameter("redirect", redirect);
		portletURL.setParameter(
			"ruleGroupInstanceId", String.valueOf(getRuleGroupInstanceId()));

		_portletURL = portletURL;

		return _portletURL;
	}

	public long getRuleGroupInstanceId() {
		if (_ruleGroupInstanceId != null) {
			return _ruleGroupInstanceId;
		}

		_ruleGroupInstanceId = ParamUtil.getLong(
			_renderRequest, "ruleGroupInstanceId");

		return _ruleGroupInstanceId;
	}

	private String _displayStyle;
	private String _orderByCol;
	private String _orderByType;
	private PortletURL _portletURL;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final ResourceBundle _resourceBundle;
	private SearchContainer _ruleActionSearchContainer;
	private Long _ruleGroupInstanceId;

}