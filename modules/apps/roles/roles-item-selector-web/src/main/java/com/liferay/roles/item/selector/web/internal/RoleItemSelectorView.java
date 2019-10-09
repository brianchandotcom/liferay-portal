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

package com.liferay.roles.item.selector.web.internal;

import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.service.RoleService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.roles.item.selector.RoleItemSelectorCriterion;
import com.liferay.roles.item.selector.web.internal.constants.RoleItemSelectorViewConstants;
import com.liferay.roles.item.selector.web.internal.display.context.RoleItemSelectorViewDisplayContext;
import com.liferay.users.admin.kernel.util.UsersAdmin;

import java.io.IOException;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletURL;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(immediate = true, service = ItemSelectorView.class)
public class RoleItemSelectorView
	implements ItemSelectorView<RoleItemSelectorCriterion> {

	@Override
	public Class<RoleItemSelectorCriterion> getItemSelectorCriterionClass() {
		return RoleItemSelectorCriterion.class;
	}

	public ServletContext getServletContext() {
		return _servletContext;
	}

	@Override
	public List<ItemSelectorReturnType> getSupportedItemSelectorReturnTypes() {
		return _supportedItemSelectorReturnTypes;
	}

	@Override
	public String getTitle(Locale locale) {
		return LanguageUtil.get(_portal.getResourceBundle(locale), "roles");
	}

	@Override
	public boolean isVisible(ThemeDisplay themeDisplay) {
		return true;
	}

	@Override
	public void renderHTML(
			ServletRequest servletRequest, ServletResponse servletResponse,
			RoleItemSelectorCriterion roleItemSelectorCriterion,
			PortletURL portletURL, String itemSelectedEventName, boolean search)
		throws IOException, ServletException {

		HttpServletRequest httpServletRequest =
			(HttpServletRequest)servletRequest;

		RoleItemSelectorViewDisplayContext roleItemSelectorViewDisplayContext =
			new RoleItemSelectorViewDisplayContext(
				_roleService, _usersAdmin, httpServletRequest, portletURL,
				itemSelectedEventName, roleItemSelectorCriterion.getType());

		servletRequest.setAttribute(
			RoleItemSelectorViewConstants.
				ROLE_ITEM_SELECTOR_VIEW_DISPLAY_CONTEXT,
			roleItemSelectorViewDisplayContext);

		ServletContext servletContext = getServletContext();

		RequestDispatcher requestDispatcher =
			servletContext.getRequestDispatcher("/role_item_selector.jsp");

		requestDispatcher.include(servletRequest, servletResponse);
	}

	private static final List<ItemSelectorReturnType>
		_supportedItemSelectorReturnTypes = Collections.unmodifiableList(
			ListUtil.fromArray(
				new ItemSelectorReturnType[] {
					new UUIDItemSelectorReturnType()
				}));

	@Reference
	private Portal _portal;

	@Reference
	private RoleService _roleService;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.roles.item.selector.web)"
	)
	private ServletContext _servletContext;

	@Reference
	private UsersAdmin _usersAdmin;

}