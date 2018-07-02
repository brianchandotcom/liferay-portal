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
 * Provides an interface to render an item selector view for a particular item
 * selector criterion.
 *
 * <p>
 * If one item selector view can be displayed for multiple different criteria,
 * it needs as many implementations of this interface as criteria.
 * </p>
 *
 * @author Iván Zaera
 */
public interface ItemSelectorView<T extends ItemSelectorCriterion> {

	/**
	 * Returns the item selector criterion associated to this item selector
	 * view.
	 *
	 * @return the item selector criterion associated to this item selector view
	 */
	public Class<T> getItemSelectorCriterionClass();

	/**
	 * Returns the item selector return types that this view supports.
	 *
	 * @return the {@link ItemSelectorReturnType}s that this view supports
	 */
	public List<ItemSelectorReturnType> getSupportedItemSelectorReturnTypes();

	/**
	 * Returns the localized title of the tab to display in the Item Selector
	 * dialog.
	 *
	 * @param  locale the current locale
	 * @return the localized title of the tab
	 */
	public String getTitle(Locale locale);

	/**
	 * Returns whether the item selector view should show the search field. If
	 * the view supports search, this method should return <code>true</code>.
	 *
	 * @return     <code>true</code> if the item selector view should show the
	 *             search field
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public default boolean isShowSearch() {
		return true;
	}

	/**
	 * Returns whether the item selector view is visible.
	 *
	 * <p>
	 * Most of the implementations of this method will return <code>true</code>.
	 * However, there are certain cases where the view should not be displayed:
	 * the view isn't ready, the view needs some additional third-party
	 * configuration, etc.
	 * </p>
	 *
	 * @param  themeDisplay the current page {@link ThemeDisplay}
	 * @return <code>true</code> if the view is visible
	 */
	public boolean isVisible(ThemeDisplay themeDisplay);

	/**
	 * Renders the HTML code for the item selector view.
	 *
	 * @param servletRequest the current {@link ServletRequest}
	 * @param servletResponse the current {@link ServletResponse}
	 * @param itemSelectorCriterion the item selector criterion that was used to
	 *        render this view
	 * @param portletURL the portlet render URL to the item selector. This URL
	 *        should be used to create URLs in the view.
	 * @param itemSelectedEventName the event name that the caller will be
	 *        listening for. When an element is selected, the view should fire a
	 *        JavaScript event with this name.
	 * @param search set to <code>true</code> when the view should render search
	 *        results because the user performed a search.
	 */
	public void renderHTML(
			ServletRequest servletRequest, ServletResponse servletResponse,
			T itemSelectorCriterion, PortletURL portletURL,
			String itemSelectedEventName, boolean search)
		throws IOException, ServletException;

}