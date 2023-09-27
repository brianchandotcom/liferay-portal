/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.item.selector;

import com.liferay.item.selector.ItemSelectorReturnType;

/**
 * This return type should return the following information of a layout page
 * template entry as a JSON object:
 *
 * <ul>
 * <li>
 * <code>layoutPageTemplateEntryId</code>: The ID of the selected layout page
 * template entry
 * </li>
 * <li>
 * <code>name</code>: The name of the selected layout page template entry
 * </li>
 * <li>
 * <code>previewURL</code>: The URL to preview the selected layout page template
 * entry
 * </li>
 * <li>
 * <code>url</code>: The URL of the selected layout page template entry
 * </li>
 * <li>
 * <code>uuid</code>: The UUID of the selected layout page template entry
 * </li>
 * </ul>
 *
 * @author Lourdes Fernández Besada
 */
public class LayoutPageTemplateEntryItemSelectorReturnType
	implements ItemSelectorReturnType {
}