/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.item.selector;

import java.util.List;

/**
 * Provides an interface that returns the {@link ItemSelectorView} for a
 * particular {@link ItemSelectorCriterion}. Every item selector criterion
 * should have its own associated item selector criterion handler.
 *
 * <p>
 * Implementations of this interface need to be registered as OSGi components
 * using the service {@link ItemSelectorCriterionHandler}.
 * </p>
 *
 * <p>
 * Unless an implementation needs custom logic to filter item selector views, it
 * will simply need to extend {@link BaseItemSelectorCriterionHandler} and only
 * implement the method {@link #getItemSelectorCriterionClass()}.
 * </p>
 *
 * @author Iván Zaera
 */
public interface ItemSelectorCriterionHandler<T extends ItemSelectorCriterion> {

	/**
	 * Returns the item selector criterion associated to this handler.
	 *
	 * @return the item selector criterion associated to this handler
	 */
	public Class<T> getItemSelectorCriterionClass();

	/**
	 * Returns the item selector views to display that meet the needs of the
	 * item selector criterion.
	 *
	 * @param  itemSelectorCriterion the item selector criterion instance
	 * @return the item selector views to display
	 */
	public List<ItemSelectorView<T>> getItemSelectorViews(
		T itemSelectorCriterion);

}