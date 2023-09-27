/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.item.selector;

import com.liferay.item.selector.ItemSelectorReturnType;

/**
 * This return type should return the following information of a layout page
 * template entry as a JSON object:
 *
 * <ul>
 * <li>
 * <code>layoutId</code>: The layoutId of the selected layout
 * </li>
 * <li>
 * <code>name</code>: The name of the selected layout
 * </li>
 * <li>
 * <code>plid</code>: The plid of the selected layout
 * </li>
 * <li>
 * <code>previewURL</code>: The URL to preview the selected layout page template
 * entry
 * </li>
 * <li>
 * <code>private</code>: Is true if the layout is private, false another case
 * </li>
 * <li>
 * <code>url</code>: The URL of the selected layout
 * </li>
 * <li>
 * <code>uuid</code>: The UUID of the selected layout
 * </li>
 * </ul>
 *
 * @author Lourdes Fernández Besada
 */
public class LayoutItemSelectorReturnType implements ItemSelectorReturnType {
}