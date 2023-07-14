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

package com.liferay.user.groups.admin.item.selector.web.internal;

import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.ItemSelectorViewDescriptorRenderer;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.user.groups.admin.item.selector.UserGroupItemSelectorCriterion;
import com.liferay.user.groups.admin.item.selector.web.internal.display.context.UserGroupItemSelectorViewDisplayContext;
import com.liferay.users.admin.kernel.util.UsersAdmin;

import java.io.IOException;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletURL;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = ItemSelectorView.class)
public class UserGroupItemSelectorView
	implements ItemSelectorView<UserGroupItemSelectorCriterion> {

	@Override
	public Class<UserGroupItemSelectorCriterion>
		getItemSelectorCriterionClass() {

		return UserGroupItemSelectorCriterion.class;
	}

	@Override
	public List<ItemSelectorReturnType> getSupportedItemSelectorReturnTypes() {
		return _supportedItemSelectorReturnTypes;
	}

	@Override
	public String getTitle(Locale locale) {
		return _language.get(locale, "user-groups");
	}

	@Override
	public void renderHTML(
			ServletRequest servletRequest, ServletResponse servletResponse,
			UserGroupItemSelectorCriterion userGroupItemSelectorCriterion,
			PortletURL portletURL, String itemSelectedEventName, boolean search)
		throws IOException, ServletException {

		UserGroupItemSelectorViewDisplayContext
			userGroupItemSelectorViewDisplayContext =
				new UserGroupItemSelectorViewDisplayContext(
					_userGroupLocalService, userGroupItemSelectorCriterion,
					_usersAdmin, (HttpServletRequest)servletRequest,
					portletURL);

		_itemSelectorViewDescriptorRenderer.renderHTML(
			servletRequest, servletResponse, userGroupItemSelectorCriterion,
			portletURL, itemSelectedEventName, search,
			new UserGroupSelectorViewDescriptor(
				userGroupItemSelectorViewDisplayContext.getSearchContainer()));
	}

	private static final List<ItemSelectorReturnType>
		_supportedItemSelectorReturnTypes = Collections.singletonList(
			new UUIDItemSelectorReturnType());

	@Reference
	private ItemSelectorViewDescriptorRenderer<UserGroupItemSelectorCriterion>
		_itemSelectorViewDescriptorRenderer;

	@Reference
	private Language _language;

	@Reference
	private UserGroupLocalService _userGroupLocalService;

	@Reference
	private UsersAdmin _usersAdmin;

}