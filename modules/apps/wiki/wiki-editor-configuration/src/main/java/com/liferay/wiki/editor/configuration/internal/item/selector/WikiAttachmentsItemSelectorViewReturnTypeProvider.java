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

package com.liferay.wiki.editor.configuration.internal.item.selector;

import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.ItemSelectorViewReturnTypeProvider;
import com.liferay.item.selector.criteria.URLItemSelectorReturnType;
import com.liferay.wiki.item.selector.constants.WikiItemSelectorViewConstants;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 */
@Component(
	property = "item.selector.view.key=" + WikiItemSelectorViewConstants.ITEM_SELECTOR_VIEW_KEY,
	service = ItemSelectorViewReturnTypeProvider.class
)
public class WikiAttachmentsItemSelectorViewReturnTypeProvider
	implements ItemSelectorViewReturnTypeProvider {

	@Override
	public List<ItemSelectorReturnType>
		populateSupportedItemSelectorReturnTypes(
			List<ItemSelectorReturnType> supportedItemSelectorReturnTypes) {

		supportedItemSelectorReturnTypes.add(new URLItemSelectorReturnType());

		return supportedItemSelectorReturnTypes;
	}

	@Reference(
		target = "(item.selector.view.key=" + WikiItemSelectorViewConstants.ITEM_SELECTOR_VIEW_KEY + ")"
	)
	private ItemSelectorView _itemSelectorView;

}