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

package com.liferay.item.selector.web.internal.provider;

import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.ItemSelectorViewRenderer;
import com.liferay.item.selector.provider.ItemSelectorViewRendererProvider;
import com.liferay.item.selector.web.internal.ItemSelectorViewRendererImpl;

import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Tardín
 */
@Component(service = ItemSelectorViewRendererProvider.class)
public class ItemSelectorViewRendererProviderImpl
	implements ItemSelectorViewRendererProvider {

	@Override
	public ItemSelectorViewRenderer getItemSelectorViewRenderer(
		ItemSelectorView<ItemSelectorCriterion> itemSelectorView,
		ItemSelectorCriterion itemSelectorCriterion, PortletURL portletURL,
		String itemSelectedEventName, boolean search) {

		return new ItemSelectorViewRendererImpl(
			itemSelectorView, itemSelectorCriterion, portletURL,
			itemSelectedEventName, search);
	}

}