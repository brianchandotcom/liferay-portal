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

package com.liferay.item.selector.criteria;

import com.liferay.item.selector.ItemSelectorReturnType;

/**
 * This return type should return the following information of a file entry as a
 * JSON object:
 *
 * <ul>
 * <li>
 * <code>fileEntryId</code>: The ID of the selected file entry
 * </li>
 * <li>
 * <code>groupId</code>: The group ID of the selected file entry
 * </li>
 * <li>
 * <code>title</code>: The title of the selected file entry
 * </li>
 * <li>
 * <code>url</code>: The download URL of the selected file entry
 * </li>
 * <li>
 * <code>uuid</code>: The UUID of the selected file entry
 * </li>
 * </ul>
 *
 * @author Alejandro Tardín
 */
public class DownloadFileEntryItemSelectorReturnType
	implements ItemSelectorReturnType {
}