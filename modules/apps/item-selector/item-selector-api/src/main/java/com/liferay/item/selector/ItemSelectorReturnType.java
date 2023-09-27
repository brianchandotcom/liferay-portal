/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.item.selector;

/**
 * Provides an interface that defines information to be returned by the item
 * selector view, when any entity is selected.
 *
 * <p>
 * This information is also used by the item selector to decide which item
 * selector views to display to the user. Only the item selector views that
 * support the item selector return type are displayed.
 * </p>
 *
 * <p>
 * The item selector return types are used in two different cases:
 * </p>
 *
 * <p>
 * 1. The item selector view specifies a list of the supported item selector
 * return types for that view, via the method {@link
 * ItemSelectorView#getSupportedItemSelectorReturnTypes()}
 * </p>
 *
 * <p>
 * 2. When creating a new item selector criterion, you need to specify a list of
 * the desired item selector return types that the client is expecting to
 * receive when an entity is selected.
 * </p>
 *
 * @author Roberto Díaz
 */
public interface ItemSelectorReturnType {
}