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

/**
 * This is an object that helps to the Item Selector Framework to know if the
 * caller and the registered {@link com.liferay.item.selector.ItemSelectorView}
 * if they are compatibles, using the {@link
 * com.liferay.item.selector.ItemSelectorCriterion}.
 *
 * The client sets in each {@link
 * com.liferay.item.selector.ItemSelectorCriterion}
 * a list of the desired {@link
 * com.liferay.item.selector.ItemSelectorReturnType},
 * and each {@link com.liferay.item.selector.ItemSelectorView} declares a list
 * of
 * the supported {@link com.liferay.item.selector.ItemSelectorReturnType}.
 *
 * In order to decide if an {@link com.liferay.item.selector.ItemSelectorView}
 * is shown or not in the Item Selector popup, the desired {@link
 * com.liferay.item.selector.ItemSelectorReturnType}
 * of the {@link com.liferay.item.selector.ItemSelectorCriterion} should be one
 * of the supported {@link com.liferay.item.selector.ItemSelectorReturnType}
 * of the {@link com.liferay.item.selector.ItemSelectorView} registered under
 * this {@link com.liferay.item.selector.ItemSelectorCriterion}.
 *
 * @author Roberto Díaz
 */
public interface ItemSelectorReturnType {
}