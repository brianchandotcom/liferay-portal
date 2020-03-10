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

package com.liferay.redirect.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;

import javax.portlet.PortletURL;
import javax.portlet.RenderURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alejandro Tardín
 */
public class RedirectManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public RedirectManagementToolbarDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		SearchContainer searchContainer) {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			searchContainer);
	}

	@Override
	public String getClearResultsURL() {
		PortletURL clearResultsURL = getPortletURL();

		clearResultsURL.setParameter("keywords", StringPool.BLANK);
		clearResultsURL.setParameter("orderByCol", getOrderByCol());
		clearResultsURL.setParameter("orderByType", getOrderByType());

		return clearResultsURL.toString();
	}

	@Override
	public CreationMenu getCreationMenu() {
		try {
			return CreationMenuBuilder.addPrimaryDropdownItem(
				dropdownItem -> {
					RenderURL editRedirectEntryURL =
						liferayPortletResponse.createRenderURL();

					editRedirectEntryURL.setParameter(
						"mvcRenderCommandName",
						"/redirect/edit_redirect_entry");

					PortletURL portletURL = getPortletURL();

					editRedirectEntryURL.setParameter(
						"redirect", portletURL.toString());

					dropdownItem.setHref(editRedirectEntryURL);

					dropdownItem.setLabel(LanguageUtil.get(request, "add"));
				}
			).build();
		}
		catch (Exception exception) {
		}

		return null;
	}

	@Override
	public String getSearchActionURL() {
		PortletURL searchActionURL = getPortletURL();

		searchActionURL.setParameter("orderByCol", getOrderByCol());
		searchActionURL.setParameter("orderByType", getOrderByType());

		return searchActionURL.toString();
	}

	@Override
	public Boolean isSelectable() {
		return false;
	}

	@Override
	protected String[] getOrderByKeys() {
		return new String[] {
			"create-date", "modified-date", "source-url", "destination-url"
		};
	}

}