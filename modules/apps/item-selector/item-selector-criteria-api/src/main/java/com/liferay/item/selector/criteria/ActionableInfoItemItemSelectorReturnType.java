/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.item.selector.criteria;

import com.liferay.item.selector.ItemSelectorReturnType;

/**
 * This return type should return the following information of an info item as a
 * JSON object:
 *
 * <ul>
 * <li>
 * <code>className</code>: The class name of the selected info item
 * </li>
 * <li>
 * <code>classNameId</code>: The class name ID of the selected info item
 * </li>
 * <li>
 * <code>classPK</code>: The class pk of the selected info item
 * </li>
 * <li>
 * <code>classTypeId</code>: The class type ID of the selected info item
 * </li>
 * <li>
 * <code>subtype</code>: The sub type of the selected info item
 * </li>
 * <li>
 * <code>title</code>: The title of the selected info item
 * </li>
 * <li>
 * <code>type</code>: The type of the selected info item
 * </li>
 * </ul>
 *
 * @author Rubén Pulido
 * @review
 */
public class ActionableInfoItemItemSelectorReturnType
	implements ItemSelectorReturnType {
}