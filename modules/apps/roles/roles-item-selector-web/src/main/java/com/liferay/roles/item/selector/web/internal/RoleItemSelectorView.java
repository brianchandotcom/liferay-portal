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
import com.liferay.portal.kernel.service.RoleLocalService;
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
import java.util.ResourceBundle;

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
		ResourceBundle resourceBundle = _portal.getResourceBundle(locale);

		return LanguageUtil.get(resourceBundle, "roles");
	}

	@Override
	public boolean isVisible(ThemeDisplay themeDisplay) {
		return true;
	}

	@Override
	public void renderHTML(
			ServletRequest request, ServletResponse response,
			RoleItemSelectorCriterion roleItemSelectorCriterion,
			PortletURL portletURL, String itemSelectedEventName, boolean search)
		throws IOException, ServletException {

		HttpServletRequest httpServletRequest = (HttpServletRequest)request;

		RoleItemSelectorViewDisplayContext roleItemSelectorViewDisplayContext =
			new RoleItemSelectorViewDisplayContext(
				_roleLocalService, _usersAdmin, httpServletRequest, portletURL,
				itemSelectedEventName, roleItemSelectorCriterion.getType());

		request.setAttribute(
			RoleItemSelectorViewConstants.
				ROLE_ITEM_SELECTOR_VIEW_DISPLAY_CONTEXT,
			roleItemSelectorViewDisplayContext);

		ServletContext servletContext = getServletContext();

		RequestDispatcher requestDispatcher =
			servletContext.getRequestDispatcher("/role_item_selector.jsp");

		requestDispatcher.include(request, response);
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
	private RoleLocalService _roleLocalService;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.roles.item.selector.web)"
	)
	private ServletContext _servletContext;

	@Reference
	private UsersAdmin _usersAdmin;

}