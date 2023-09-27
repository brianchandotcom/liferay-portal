/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.wiki.web.internal.item.selector;

import com.liferay.item.selector.ItemSelectorReturnTypeResolver;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.wiki.item.selector.WikiPageTitleItemSelectorReturnType;
import com.liferay.wiki.model.WikiPage;

import org.osgi.service.component.annotations.Component;

/**
 * @author Roberto Díaz
 */
@Component(
	property = "service.ranking:Integer=100",
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