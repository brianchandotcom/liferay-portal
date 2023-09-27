/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.item.selector;

import com.liferay.item.selector.ItemSelectorReturnType;

/**
 * This return type should return the following information of a vocabulary as a
 * JSON object:
 *
 * <ul>
 * <li>
 * <code>ddmStructureId</code>: The ddmStructureId of the selected ddm structure
 * </li>
 * <li>
 * <code>ddmStructureKey</code>: The ddmStructureKey of the selected ddm structure
 * </li>
 * <li>
 * <code>name</code>: The localized name of the selected ddm structure
 * </li>
 * </ul>
 *
 * @author Eudaldo Alonso
 */
public class DDMStructureItemSelectorReturnType
	implements ItemSelectorReturnType {
}