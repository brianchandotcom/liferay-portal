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

package com.liferay.wiki.web.internal.item.selector.resolver;

import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorReturnTypeResolver;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.wiki.model.WikiPage;

/**
 * @author Roberto Díaz
 */
public interface WikiPageItemSelectorReturnTypeResolver<
	T extends ItemSelectorReturnType, S>
		extends ItemSelectorReturnTypeResolver<T, WikiPage> {

	public String getTitle(WikiPage page, ThemeDisplay themeDisplay)
		throws Exception;

}