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

package com.liferay.dynamic.data.mapping.item.selector;

import com.liferay.item.selector.ItemSelectorReturnType;

/**
 * This return type should return the following information of a vocabulary as a
 * JSON object:
 *
 * <ul>
 * <li>
 * <code>ddmTemplateId</code>: The ddmTemplateId of the selected ddm template
 * </li>
 * <li>
 * <code>ddmTemplateKey</code>: The ddmTemplateKey of the selected ddm template
 * </li>
 * <li>
 * <code>description</code>: The localized description of the selected ddm template
 * </li>
 * <li>
 * <code>imageurl</code>: The imageurl of the selected ddm template
 * </li>
 * <li>
 * <code>name</code>: The localized name of the selected ddm template
 * </li>
 * </ul>
 *
 * @author Eudaldo Alonso
 */
public class DDMTemplateItemSelectorReturnType
	implements ItemSelectorReturnType {
}