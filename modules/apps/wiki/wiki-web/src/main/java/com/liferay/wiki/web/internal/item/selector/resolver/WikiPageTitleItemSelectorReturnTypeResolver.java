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

import com.liferay.item.selector.ItemSelectorReturnTypeResolver;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.wiki.item.selector.WikiPageTitleItemSelectorReturnType;
import com.liferay.wiki.model.WikiPage;

import org.osgi.service.component.annotations.Component;

/**
 * @author Roberto Díaz
 */
@Component(
	immediate = true, property = "service.ranking:Integer=100",
	service = ItemSelectorReturnTypeResolver.class
)
public class WikiPageTitleItemSelectorReturnTypeResolver
	implements WikiPageItemSelectorReturnTypeResolver
		<WikiPageTitleItemSelectorReturnType, WikiPage> {

	@Override
	public Class<WikiPageTitleItemSelectorReturnType>
		getItemSelectorReturnTypeClass() {

		return WikiPageTitleItemSelectorReturnType.class;
	}

	@Override
	public Class<WikiPage> getModelClass() {
		return WikiPage.class;
	}

	@Override
	public String getTitle(WikiPage page, ThemeDisplay themeDisplay) {
		return getValue(page, themeDisplay);
	}

	@Override
	public String getValue(WikiPage page, ThemeDisplay themeDisplay) {
		return page.getTitle();
	}

}