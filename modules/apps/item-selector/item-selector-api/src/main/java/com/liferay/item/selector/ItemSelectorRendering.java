/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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