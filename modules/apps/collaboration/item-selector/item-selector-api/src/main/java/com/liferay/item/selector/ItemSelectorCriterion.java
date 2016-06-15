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
 * The type of the views that are going to be shown. This object could be use as
 * a bean to pass information to the view layer by the caller.
 *
 * @author Iván Zaera
 */
public interface ItemSelectorCriterion {

	/**
	 * Returns a List of the ReturnTypes that the caller could handle.
	 *
	 * @return a List of ReturnTypes ordered by insertion.
	 */
	public List<ItemSelectorReturnType> getDesiredItemSelectorReturnTypes();

	/**
	 * Sets a List of desired ReturnTypes. This return types are the ones that
	 * the item selector caller could handle. This return types are ordered by
	 * insertion order so the preferred ones should be introduced first.
	 *
	 * @param desiredItemSelectorReturnTypes the list of ReturnTypes that could
	 *        be handled by the caller.
	 */
	public void setDesiredItemSelectorReturnTypes(
		List<ItemSelectorReturnType> desiredItemSelectorReturnTypes);

}