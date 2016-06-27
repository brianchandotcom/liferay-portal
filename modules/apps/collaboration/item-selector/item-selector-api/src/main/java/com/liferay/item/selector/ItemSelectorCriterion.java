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
 * Provides an interface that indicates the type of entity that shall be
 * selected and what information should be returned as well. The item selector
 * will use this criterion to display only the {@link ItemSelectorView} that can
 * select that particular entity type and return the specified
 * {@link ItemSelectorReturnType}.
 *
 * Implementations of this interface can provide additional information to have
 * a fine grained detail about which entities can be selected. This should be
 * done ideally using primitive types in the constructor (or very simple types
 * that can be JSON serialized) and it is mandatory to have an empty
 * constructor.
 *
 * For simplicity, it is recommended that implementations of this class extend
 * {@link BaseItemSelectorCriterion}
 *
 * @author Iván Zaera
 */
public interface ItemSelectorCriterion {

	/**
	 * Returns a List of the return types that the caller expects because he can
	 * handle them.
	 *
	 * Order is important because in case that one item selector view can return
	 * multiple item selector return types, the first return type specified as
	 * desired that can be returned by the view will be used.
	 *
	 * @return a List of return types ordered by preference.
	 */
	public List<ItemSelectorReturnType> getDesiredItemSelectorReturnTypes();

	/**
	 * Sets a List of desired return types that the caller expects because he
	 * can handle them.
	 *
	 * Order is important because in case that one item selector view can return
	 * multiple item selector return types, the first return type specified as
	 * desired that can be returned by the view will be used.
	 *
	 * @param desiredItemSelectorReturnTypes a preference ordered list of the
	 *        return types that can be handled by the caller
	 */
	public void setDesiredItemSelectorReturnTypes(
		List<ItemSelectorReturnType> desiredItemSelectorReturnTypes);

}