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

package com.liferay.notifications.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.SafeConsumer;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.List;
import java.util.Objects;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Sergio González
 */
public class NotificationsManagementToolbarDisplayContext {

	public NotificationsManagementToolbarDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		HttpServletRequest request, PortletURL currentURLObj) {

		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_request = request;
		_currentURLObj = currentURLObj;
	}

	public List<DropdownItem> getActionDropdownItems() {
		return new DropdownItemList() {
			{
				if (!_isActionRequired()) {
					add(
						SafeConsumer.ignore(
							dropdownItem -> {
								dropdownItem.putData(
									"action", "markNotificationsAsRead");
								dropdownItem.setIcon("envelope-open");
								dropdownItem.setLabel(
									LanguageUtil.get(_request, "mark-as-read"));
								dropdownItem.setQuickAction(true);
							}));

					add(
						SafeConsumer.ignore(
							dropdownItem -> {
								dropdownItem.putData(
									"action", "markNotificationsAsUnread");
								dropdownItem.setIcon("envelope-closed");
								dropdownItem.setLabel(
									LanguageUtil.get(
										_request, "mark-as-unread"));
								dropdownItem.setQuickAction(true);
							}));
				}

				add(
					SafeConsumer.ignore(
						dropdownItem -> {
							dropdownItem.putData(
								"action", "deleteNotifications");
							dropdownItem.setIcon("times-circle");
							dropdownItem.setLabel(
								LanguageUtil.get(_request, "delete"));
							dropdownItem.setQuickAction(true);
						}));
			}
		};
	}

	public String getClearResultsURL() {
		PortletURL clearResultsURL = _liferayPortletResponse.createRenderURL();

		clearResultsURL.setParameter(
			"actionRequired", String.valueOf(_isActionRequired()));

		return clearResultsURL.toString();
	}

	public List<DropdownItem> getFilterDropdownItems() {
		return new DropdownItemList() {
			{
				if (!_isActionRequired()) {
					addGroup(
						SafeConsumer.ignore(
							dropdownGroupItem -> {
								dropdownGroupItem.setDropdownItems(
									_getFilterNavigationDropdownItems());
								dropdownGroupItem.setLabel(
									LanguageUtil.get(
										_request, "filter-by-navigation"));
							}));
				}

				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(
							_getOrderByDropdownItems());
						dropdownGroupItem.setLabel(
							LanguageUtil.get(_request, "order-by"));
					});
			}
		};
	}

	public List<LabelItem> getFilterLabelItems() {
		return new LabelItemList() {
			{
				String navigation = _getNavigation();

				if (navigation.equals("read") || navigation.equals("unread")) {
					add(
						SafeConsumer.ignore(
							labelItem -> {
								PortletURL removeLabelURL =
									PortletURLUtil.clone(
										_currentURLObj,
										_liferayPortletResponse);

								removeLabelURL.setParameter(
									"navigation", (String)null);

								labelItem.putData(
									"removeLabelURL",
									removeLabelURL.toString());

								labelItem.setCloseable(true);

								labelItem.setLabel(
									LanguageUtil.get(_request, navigation));
							}));
				}
			}
		};
	}

	public String getOrderByType() {
		return ParamUtil.getString(_request, "orderByType", "desc");
	}

	public PortletURL getSortingURL() throws PortletException {
		PortletURL sortingURL = PortletURLUtil.clone(
			_currentURLObj, _liferayPortletResponse);

		sortingURL.setParameter(SearchContainer.DEFAULT_CUR_PARAM, "0");
		sortingURL.setParameter("orderByCol", "date");
		sortingURL.setParameter(
			"orderByType",
			Objects.equals(getOrderByType(), "asc") ? "desc" : "asc");

		return sortingURL;
	}

	private List<DropdownItem> _getFilterNavigationDropdownItems() {
		String navigation = _getNavigation();

		return new DropdownItemList() {
			{
				add(
					SafeConsumer.ignore(
						dropdownItem -> {
							dropdownItem.setHref(
								PortletURLUtil.clone(
									_currentURLObj, _liferayPortletResponse),
								SearchContainer.DEFAULT_CUR_PARAM, "0",
								"navigation", "all");
							dropdownItem.setActive(navigation.equals("all"));
							dropdownItem.setLabel(
								LanguageUtil.get(_request, "all"));
						}));
				add(
					SafeConsumer.ignore(
						dropdownItem -> {
							dropdownItem.setHref(
								PortletURLUtil.clone(
									_currentURLObj, _liferayPortletResponse),
								SearchContainer.DEFAULT_CUR_PARAM, "0",
								"navigation", "unread");
							dropdownItem.setActive(navigation.equals("unread"));
							dropdownItem.setLabel(
								LanguageUtil.get(_request, "unread"));
						}));
				add(
					SafeConsumer.ignore(
						dropdownItem -> {
							dropdownItem.setHref(
								PortletURLUtil.clone(
									_currentURLObj, _liferayPortletResponse),
								SearchContainer.DEFAULT_CUR_PARAM, "0",
								"navigation", "read");
							dropdownItem.setActive(navigation.equals("read"));
							dropdownItem.setLabel(
								LanguageUtil.get(_request, "read"));
						}));
			}
		};
	}

	private String _getNavigation() {
		return ParamUtil.getString(_request, "navigation", "all");
	}

	private List<DropdownItem> _getOrderByDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					SafeConsumer.ignore(
						dropdownItem -> {
							dropdownItem.setActive(true);
							dropdownItem.setHref(getSortingURL());
							dropdownItem.setLabel(
								LanguageUtil.get(_request, "date"));
						}));
			}
		};
	}

	private boolean _isActionRequired() {
		return ParamUtil.getBoolean(_request, "actionRequired");
	}

	private final PortletURL _currentURLObj;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final HttpServletRequest _request;

}