/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.item.selector.criteria;

import com.liferay.item.selector.ItemSelectorReturnType;

/**
 * This return type should return the following information of a group as a JSON
 * object:
 *
 * <ul>
 * <li>
 * <code>groupDescriptiveName</code>: The name of the selected group
 * </li>
 * <li>
 * <code>groupId</code>: The group ID of the selected group
 * </li>
 * <li>
 * <code>groupType</code>: The type of the selected group
 * </li>
 * <li>
 * <code>url</code>: The URL of the selected group
 * </li>
 * <li>
 * <code>uuid</code>: The UUID of the selected group
 * </li>
 * </ul>
 *
 * @author Eudaldo Alonso
 */
public class GroupItemSelectorReturnType implements ItemSelectorReturnType {
}