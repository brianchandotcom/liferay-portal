/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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