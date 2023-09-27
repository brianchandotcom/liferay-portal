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
 * <code>assetEntryId</code>: The entryId of the selected asset entry
 * </li>
 * <li>
 * <code>assetType</code>: The type of the selected asset entry
 * </li>
 * <li>
 * <code>className</code>: The class name of the selected asset entry
 * </li>
 * <li>
 * <code>classNameId</code>: The class name ID of the selected asset entry
 * </li>
 * <li>
 * <code>classPK</code>: The class pk of the selected asset entry
 * </li>
 * <li>
 * <code>groupDescriptiveName</code>: The group name of the selected asset entry
 * </li>
 * <li>
 * <code>title</code>: The title of the selected asset entry
 * </li>
 * </ul>
 *
 * @author Adolfo Pérez
 * @review
 */
public class AssetEntryItemSelectorReturnType
	implements ItemSelectorReturnType {
}