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

package com.liferay.content.dashboard.web.internal.servlet.taglib.util;

import com.liferay.content.dashboard.item.action.ContentDashboardItemAction;
import com.liferay.content.dashboard.web.internal.item.ContentDashboardItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.info.item.InfoItemReference;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.List;
import java.util.Locale;

import javax.portlet.ResourceURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Cristina González
 */
public class ContentDashboardDropdownItemsProvider {

	public ContentDashboardDropdownItemsProvider(
		Http http, Language language,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse, Portal portal) {

		_http = http;
		_language = language;
		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_portal = portal;

		_currentURL = String.valueOf(
			PortletURLUtil.getCurrent(
				_liferayPortletRequest, liferayPortletResponse));
	}

	public List<DropdownItem> getDropdownItems(
		ContentDashboardItem contentDashboardItem) {

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			_liferayPortletRequest);

		Locale locale = _portal.getLocale(_liferayPortletRequest);

		DropdownItemList dropdownItemList = DropdownItemList.of(
			(DropdownItem[])TransformUtil.transformToArray(
				(List<ContentDashboardItemAction>)
					contentDashboardItem.getContentDashboardItemActions(
						httpServletRequest,
						ContentDashboardItemAction.Type.VIEW,
						ContentDashboardItemAction.Type.EDIT),
				contentDashboardItemAction -> _toDropdownItem(
					contentDashboardItemAction, locale),
				DropdownItem.class));

		dropdownItemList.addAll(
			DropdownItemList.of(
				() -> {
					DropdownItem dropdownItem = new DropdownItem();

					ResourceURL resourceURL =
						_liferayPortletResponse.createResourceURL();

					resourceURL.setParameter(
						"backURL",
						_portal.getCurrentURL(_liferayPortletRequest));

					InfoItemReference infoItemReference =
						contentDashboardItem.getInfoItemReference();

					resourceURL.setParameter(
						"className", infoItemReference.getClassName());
					resourceURL.setParameter(
						"classPK",
						String.valueOf(infoItemReference.getClassPK()));

					resourceURL.setResourceID(
						"/content_dashboard/get_content_dashboard_item_info");

					dropdownItem.setData(
						HashMapBuilder.<String, Object>put(
							"action", "showInfo"
						).put(
							"className", infoItemReference.getClassName()
						).put(
							"classPK", infoItemReference.getClassPK()
						).put(
							"fetchURL", String.valueOf(resourceURL)
						).build());

					dropdownItem.setIcon("info-circle-open");
					dropdownItem.setLabel(_language.get(locale, "info"));
					dropdownItem.setQuickAction(true);

					return dropdownItem;
				}));

		dropdownItemList.addAll(
			TransformUtil.transform(
				(List<ContentDashboardItemAction>)
					contentDashboardItem.getContentDashboardItemActions(
						httpServletRequest,
						ContentDashboardItemAction.Type.VIEW_IN_PANEL),
				contentDashboardItemAction -> _toViewInPanelDropdownItem(
					contentDashboardItem, contentDashboardItemAction, locale)));

		return dropdownItemList;
	}

	private String _getURLWithBackURL(String url) {
		String backURL = ParamUtil.getString(_liferayPortletRequest, "backURL");

		if (Validator.isNotNull(backURL)) {
			return _http.setParameter(url, "p_l_back_url", backURL);
		}

		return _http.setParameter(url, "p_l_back_url", _currentURL);
	}

	private DropdownItem _toDropdownItem(
		ContentDashboardItemAction contentDashboardItemAction, Locale locale) {

		if (contentDashboardItemAction == null) {
			return null;
		}

		DropdownItem dropdownItem = new DropdownItem();

		dropdownItem.setHref(
			_getURLWithBackURL(contentDashboardItemAction.getURL(locale)));
		dropdownItem.setIcon(contentDashboardItemAction.getIcon());
		dropdownItem.setLabel(contentDashboardItemAction.getLabel(locale));
		dropdownItem.setQuickAction(true);

		return dropdownItem;
	}

	private DropdownItem _toViewInPanelDropdownItem(
		ContentDashboardItem contentDashboardItem,
		ContentDashboardItemAction contentDashboardItemAction, Locale locale) {

		DropdownItem dropdownItem = new DropdownItem();

		InfoItemReference infoItemReference =
			contentDashboardItem.getInfoItemReference();

		dropdownItem.setData(
			HashMapBuilder.<String, Object>put(
				"action", "showMetrics"
			).put(
				"className", infoItemReference.getClassName()
			).put(
				"classPK", infoItemReference.getClassPK()
			).put(
				"fetchURL", contentDashboardItemAction.getURL(locale)
			).build());

		dropdownItem.setIcon(contentDashboardItemAction.getIcon());
		dropdownItem.setLabel(contentDashboardItemAction.getLabel(locale));
		dropdownItem.setQuickAction(true);

		return dropdownItem;
	}

	private final String _currentURL;
	private final Http _http;
	private final Language _language;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final Portal _portal;

}