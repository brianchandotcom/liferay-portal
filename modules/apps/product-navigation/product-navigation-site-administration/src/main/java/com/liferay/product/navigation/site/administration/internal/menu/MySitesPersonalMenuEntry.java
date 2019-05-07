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

package com.liferay.product.navigation.site.administration.internal.menu;

import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.criteria.URLItemSelectorReturnType;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.util.PropsValues;
import com.liferay.product.navigation.personal.menu.PersonalMenuEntry;
import com.liferay.site.item.selector.criterion.SiteItemSelectorCriterion;
import com.liferay.site.util.RecentGroupManager;
import com.liferay.taglib.aui.AUIUtil;

import java.util.List;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(
	immediate = true,
	property = {
		"product.navigation.personal.menu.entry.order:Integer=100",
		"product.navigation.personal.menu.group:Integer=100"
	},
	service = PersonalMenuEntry.class
)
public class MySitesPersonalMenuEntry implements PersonalMenuEntry {

	@Override
	public String getIcon(PortletRequest portletRequest) {
		return "sites";
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "my-sites");
	}

	@Override
	public String getPortletURL(HttpServletRequest httpServletRequest)
		throws PortalException {

		String namespace = AUIUtil.getNamespace(httpServletRequest);

		String eventName = namespace + "selectSite";

		ItemSelectorCriterion itemSelectorCriterion =
			new SiteItemSelectorCriterion();

		itemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new URLItemSelectorReturnType());

		PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
			RequestBackedPortletURLFactoryUtil.create(httpServletRequest),
			eventName, itemSelectorCriterion);

		StringBuilder sb = new StringBuilder(11);

		sb.append("javascript:Liferay.Util.selectEntity({dialog: ");
		sb.append("{constrain: true, destroyOnHide: true, modal: true}, ");
		sb.append("eventName: '");
		sb.append(eventName);
		sb.append("', id:'");
		sb.append(namespace);
		sb.append("selectSite', title: '");
		sb.append(LanguageUtil.get(httpServletRequest, "select-site"));
		sb.append("', uri:'");
		sb.append(HtmlUtil.escapeJS(itemSelectorURL.toString()));
		sb.append("'}, function(event) {location.href = event.url;});");

		return sb.toString();
	}

	@Override
	public boolean isShow(
			PortletRequest portletRequest, PermissionChecker permissionChecker)
		throws PortalException {

		User user = permissionChecker.getUser();

		List<Group> mySiteGroups = user.getMySiteGroups(
			new String[] {
				Company.class.getName(), Group.class.getName(),
				Organization.class.getName()
			},
			PropsValues.MY_SITES_MAX_ELEMENTS);

		List<Group> recentGroups = _recentGroupManager.getRecentGroups(
			_portal.getHttpServletRequest(portletRequest));

		if (mySiteGroups.isEmpty() && recentGroups.isEmpty()) {
			return false;
		}

		return true;
	}

	@Reference(unbind = "-")
	public void setItemSelector(ItemSelector itemSelector) {
		_itemSelector = itemSelector;
	}

	@Reference(unbind = "-")
	public void setRecentGroupManager(RecentGroupManager recentGroupManager) {
		_recentGroupManager = recentGroupManager;
	}

	private ItemSelector _itemSelector;

	@Reference
	private Portal _portal;

	private RecentGroupManager _recentGroupManager;

}