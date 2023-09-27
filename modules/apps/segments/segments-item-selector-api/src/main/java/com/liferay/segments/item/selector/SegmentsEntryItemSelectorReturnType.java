/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.item.selector;

import com.liferay.item.selector.ItemSelectorReturnType;

/**
 * This return type should return the following information of a segments entry
 * as a JSON object:
 *
 * <ul>
 * <li>
 * <code>segmentsEntryId</code>: The segmentsEntryId of the selected segments entry
 * </li>
 * <li>
 * <code>segmentsEntryName</code>: The name of the selected segments entry
 * </li>
 * </ul>
 *
 * @author Lourdes Fernández Besada
 */
public class SegmentsEntryItemSelectorReturnType
	implements ItemSelectorReturnType {
}