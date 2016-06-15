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
 * When used in an {@link com.liferay.item.selector.ItemSelectorView} it should
 * return, in order to be fully compatible with other cases in portal, a
 * JSONObject containing:
 *
 * {
 * 		fileEntryId: The ID of the uploaded File
 * 		groupId: The group of the uploaded File
 * 		title: The title of the uploaded File
 * 		type: The type of the uploaded File
 * 		url: The url of the uploaded File
 * 		uuid: The UUID of the uploaded File
 * 	}
 *
 * @author Roberto Díaz
 */
public class UploadableFileReturnType implements ItemSelectorReturnType {
}