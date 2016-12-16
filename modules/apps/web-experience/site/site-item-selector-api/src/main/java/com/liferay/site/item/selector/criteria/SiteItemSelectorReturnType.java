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

package com.liferay.site.item.selector.criteria;

import com.liferay.item.selector.ItemSelectorReturnType;

/**
 * This return type should return the following information of a group as a
 * JSON object:
 *
 * <code>
 * {
 * 		groupDescriptiveName: The name of the selected group
 * 		groupId: The group ID of the selected group
 * 		groupType: The type of the selected group
 * 		url: The URL of the selected group
 * 		uuid: The UUID of the selected group
 * }
 * </code>
 *
 * @author Eudaldo Alonso
 */
public class SiteItemSelectorReturnType implements ItemSelectorReturnType {
}