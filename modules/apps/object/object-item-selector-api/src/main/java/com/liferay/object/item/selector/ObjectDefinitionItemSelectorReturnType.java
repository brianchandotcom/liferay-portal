/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.item.selector;

import com.liferay.item.selector.ItemSelectorReturnType;

/**
 * This return type should return the following information of a segments entry
 * as a JSON object:
 *
 * <ul>
 * <li>
 * <code>label</code>: The label of the selected object definition
 * </li>
 * <li>
 * <code>objectDefinitionId</code>: The objectDefinitionId of the selected object definition
 * </li>
 * </ul>
 *
 * @author Jonathan McCann
 */
public class ObjectDefinitionItemSelectorReturnType
	implements ItemSelectorReturnType {
}