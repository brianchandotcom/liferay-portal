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

package com.liferay.item.selector;

import com.liferay.portal.kernel.theme.ThemeDisplay;

import java.io.IOException;

import java.util.List;
import java.util.Locale;

import javax.portlet.PortletURL;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Renders the HTML that is going to be shown in the Item Selector dialog.
 *
 * @author Iván Zaera
 */
public interface ItemSelectorView<T extends ItemSelectorCriterion> {

	/**
	 * The {@link ItemSelectorCriterion} of the view.
	 *
	 * @return the {@link ItemSelectorCriterion} of the view
	 */
	public Class<T> getItemSelectorCriterionClass();

	/**
	 * A list of the {@link ItemSelectorReturnType} that this view could return.
	 *
	 * @return a list of the {@link ItemSelectorReturnType} that this view could
	 *         return
	 */
	public List<ItemSelectorReturnType> getSupportedItemSelectorReturnTypes();

	/**
	 * The title of the tab that will be displayed in the Item Selector popup.
	 *
	 * @return the title if the tab
	 */
	public String getTitle(Locale locale);

	/**
	 * @return <code>true</code> if the Item Selector should show the search
	 *         field.
	 */
	public boolean isShowSearch();

	/**
	 * @param  themeDisplay the current page {@link ThemeDisplay}
	 * @return <code>true</code> if the view is visible
	 */
	public boolean isVisible(ThemeDisplay themeDisplay);

	/**
	 * Renders the HTML code that will be seen in the Item Selector Dialgog
	 * under a tab named with {@link #getTitle(Locale)} value. The parameters
	 * are used to pass information to the view. Pass the eventName is mandatory
	 * in order to fire an event with this name.
	 *
	 * @param servletRequest the current {@link ServletRequest}
	 * @param servletResponse the current {@link ServletResponse}
	 * @param itemSelectorCriterion the instance of {@link
	 *        ItemSelectorCriterion} with all the data that the caller of the
	 *        Item Selector passed
	 * @param portletURL the currentURL
	 * @param itemSelectedEventName the event name that the caller will be
	 *        listen. It's mandatory to pass it to the view in order to fire
	 *        this event.
	 * @param search is <code>true</code> if the view is rendered after a search
	 *        has been performed by the user
	 */
	public void renderHTML(
			ServletRequest servletRequest, ServletResponse servletResponse,
			T itemSelectorCriterion, PortletURL portletURL,
			String itemSelectedEventName, boolean search)
		throws IOException, ServletException;

}