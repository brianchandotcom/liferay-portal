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

package com.liferay.info.collection.provider.item.selector.web.internal.item.selector;

import com.liferay.item.selector.ItemSelectorReturnType;

/**
 * This return type should return the following information of a file entry as a
 * JSON object:
 *
 * <ul>
 * <li>
 * <code>itemSubtype</code>: The subtyoe of the related info item provider
 * </li>
 * <li>
 * <code>itemType</code>: The type of the related info item provider
 * </li>
 * <li>
 * <code>key</code>: The key of the related info item provider
 * </li>
 * <li>
 * <code>sourceItemType</code>: The source item type of the related info item provider
 * </li>
 * <li>
 * <code>title</code>: The title of the related info item provider
 * </li>
 * </ul>
 *
 * @author Diego Hu
 */
public class RelatedInfoItemCollectionProviderItemSelectorReturnType
	implements ItemSelectorReturnType {
}