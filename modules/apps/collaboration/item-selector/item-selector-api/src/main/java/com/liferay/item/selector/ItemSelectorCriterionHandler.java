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
 * Returns the {@link ItemSelectorView} registered under the {@link
 * ItemSelectorCriterion} handled by this class. Each {@link
 * ItemSelectorCriterion} should have one {@link ItemSelectorCriterionHandler}.
 *
 * @author Iván Zaera
 */
public interface ItemSelectorCriterionHandler<T extends ItemSelectorCriterion> {

	/**
	 * Returns the handled {@link ItemSelectorCriterion}. This method is a way
	 * to declare which {@link ItemSelectorCriterion} this class belongs to.
	 *
	 * @return the {@link ItemSelectorCriterion} class.
	 */
	public Class<T> getItemSelectorCriterionClass();

	/**
	 * Returns a List of {@link ItemSelectorView} for the handled {@link
	 * ItemSelectorCriterion}.
	 *
	 * @param  itemSelectorCriterion the instance of the {@link
	 *         ItemSelectorCriterion}.
	 * @return a List of ItemSelectorViews.
	 */
	public List<ItemSelectorView<T>> getItemSelectorViews(
		T itemSelectorCriterion);

}