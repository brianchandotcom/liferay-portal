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

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import java.util.List;
import java.util.Map;

import javax.portlet.PortletURL;

/**
 * A helper class to retrieve the ItemSelectorRendering and the PortletURL for
 * the Item Selector.
 *
 * @author Iván Zaera
 * @author Roberto Díaz
 */
public interface ItemSelector {

	public List<ItemSelectorCriterion> getItemSelectorCriteria(
		Map<String, String[]> parameters);

	/**
	 * Returns the ItemSelectorRendering.
	 *
	 * @param  requestBackedPortletURLFactory the factory used to generate the
	 *         PortletURL
	 * @param  parameters the map of parameters received in the url. Item
	 *         Selector framework will use them to get the views and render the
	 *         views
	 * @param  themeDisplay the current theme display
	 * @return the ItemSelectorRendering.
	 */
	public ItemSelectorRendering getItemSelectorRendering(
		RequestBackedPortletURLFactory requestBackedPortletURLFactory,
		Map<String, String[]> parameters, ThemeDisplay themeDisplay);

	/**
	 * Generates the item selector PortletURL according with the parameters.
	 * This method is not recommended for external use. Should be used by the
	 * client to render the Item Selector and show the selection views scoped to
	 * the group matching the {@link ItemSelectorCriterion} and the {@link
	 * ItemSelectorReturnType}.
	 *
	 * @param  requestBackedPortletURLFactory the factory used to generate the
	 *         PortletURL
	 * @param  group the group we want to select elements from
	 * @param  refererGroupId the group ID of the Item Selector client
	 * @param  itemSelectedEventName the event name that should be fired by
	 *         views.
	 * @param  itemSelectorCriteria an array of the Criteria that item selector
	 *         should use to retrive the views.
	 * @return the item selector PortletURL.
	 */
	public PortletURL getItemSelectorURL(
		RequestBackedPortletURLFactory requestBackedPortletURLFactory,
		Group group, long refererGroupId, String itemSelectedEventName,
		ItemSelectorCriterion... itemSelectorCriteria);

	/**
	 * Generates the item selector PortletURL according with the parameters.
	 * Should be used by the client to render the Item Selector and show the
	 * selection views matching the {@link ItemSelectorCriterion} and the {@link
	 * ItemSelectorReturnType}.
	 *
	 * @param  requestBackedPortletURLFactory the factory used to generate the
	 *         PortletURL
	 * @param  itemSelectedEventName the event name that should be fired by
	 *         views.
	 * @param  itemSelectorCriteria an array of the Criteria that item selector
	 *         should use to retrive the views.
	 * @return the item selector PortletURL.
	 */
	public PortletURL getItemSelectorURL(
		RequestBackedPortletURLFactory requestBackedPortletURLFactory,
		String itemSelectedEventName,
		ItemSelectorCriterion... itemSelectorCriteria);

}