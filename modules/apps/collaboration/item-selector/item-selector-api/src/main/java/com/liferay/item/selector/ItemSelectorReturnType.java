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
 * Provides an interface that defines what information should be returned by the
 * item selector view when any entity is selected.
 *
 * This information is also used by the item selector to decide which item
 * selector views will be displayed to the user. Only the item selector views
 * that can return the item selector return type will be displayed.
 *
 * The item selector return types are used in two different cases:
 *
 * 1. The item selector view specifies a list of the supported item selector
 * return types for that view using the method
 * {@link ItemSelectorView#getSupportedItemSelectorReturnTypes()}
 *
 * 2. When creating a new item selector criterion we need to specify a list of
 * the desired item selector return types that the client is expecting to
 * receive when an entity is selected.
 *
 * @author Roberto Díaz
 */
public interface ItemSelectorReturnType {
}