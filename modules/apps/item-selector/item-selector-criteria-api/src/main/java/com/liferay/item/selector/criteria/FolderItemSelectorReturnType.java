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
 * <code>folderId</code>: The ID of the selected folder
 * </li>
 * <li>
 * <code>groupId</code>: The group ID of the selected folder
 * </li>
 * <li>
 * <code>name</code>: The name of the selected folder
 * </li>
 * <li>
 * <code>repositoryId</code>: The repository ID of the selected folder
 * </li>
 * </ul>
 *
 * @author Adolfo Pérez
 * @review
 */
public class FolderItemSelectorReturnType implements ItemSelectorReturnType {
}