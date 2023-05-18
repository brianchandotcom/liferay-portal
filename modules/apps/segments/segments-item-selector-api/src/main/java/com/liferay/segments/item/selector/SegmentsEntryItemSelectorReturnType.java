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

package com.liferay.segments.item.selector;

import com.liferay.item.selector.ItemSelectorReturnType;

/**
 * This return type should return the following information of a vocabulary as a
 * JSON object:
 *
 * <ul>
 * <li>
 * <code>segmentEntryId</code>: The segmentEntryId of the selected segments entry
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