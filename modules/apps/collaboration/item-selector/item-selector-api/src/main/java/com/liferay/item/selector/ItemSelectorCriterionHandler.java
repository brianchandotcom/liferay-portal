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
 * Provides an interface that returns the {@link ItemSelectorView} for a
 * particular {@link ItemSelectorCriterion}. Every item selector criterion
 * should have each own associated item selector criterion handler.
 *
 * Implementations of this interface needs to be registered as OSGi component
 * using the service {@link ItemSelectorCriterionHandler}.
 *
 * Most of the implementations of this interface will simply need to extend
 * {@link BaseItemSelectorCriterionHandler} and only implement the method
 * {@link #getItemSelectorCriterionClass()}, unless you need custom logic to
 * filter the item selector views.
 *
 * @author Iván Zaera
 */
public interface ItemSelectorCriterionHandler<T extends ItemSelectorCriterion> {

	/**
	 * Returns the item selector criterion associated to this handler.
	 *
	 * @return the item selector criterion associated to this handler.
	 */
	public Class<T> getItemSelectorCriterionClass();

	/**
	 * Returns a List of the item selector views that meets the needs of a
	 * particular item selector criterion and therefore will be displayed.
	 *
	 * @param  itemSelectorCriterion the instance of the item selector
	 *         criterion.
	 * @return a List of the item selector views that will be displayed.
	 */
	public List<ItemSelectorView<T>> getItemSelectorViews(
		T itemSelectorCriterion);

}