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

import java.util.List;

/**
 * Provides an interface with the necessary information to render the item
 * selector.
 *
 * @author Iván Zaera
 */
public interface ItemSelectorRendering {

	/**
	 * Returns the event name that the view should fire once the selection is
	 * performed.
	 *
	 * @return the event name
	 */
	public String getItemSelectedEventName();

	/**
	 * Returns a list of {@link ItemSelectorViewRenderer} objects of the
	 * selection views to be rendered.
	 *
	 * @return a list of {@link ItemSelectorViewRenderer}
	 */
	public List<ItemSelectorViewRenderer> getItemSelectorViewRenderers();

	/**
	 * Returns the selected tab of the Item Selector dialog to be rendered.
	 *
	 * @return the selected tab
	 */
	public String getSelectedTab();

}