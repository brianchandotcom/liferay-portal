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

package com.liferay.wiki.item.selector.web;

import com.liferay.item.selector.ItemSelectorCriterionHandler;
import com.liferay.item.selector.ItemSelectorView;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Iván Zaera
 */
@Component(service = ItemSelectorCriterionHandler.class)
public class WikiAttachmentItemSelectorCriterionHandler
	implements ItemSelectorCriterionHandler
		<WikiAttachmentItemSelectorCriterion,
		 WikiAttachmentItemSelectorReturnTypes> {

	@Override
	public Class<WikiAttachmentItemSelectorCriterion>
		getItemSelectorCriterionClass() {

		return WikiAttachmentItemSelectorCriterion.class;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<ItemSelectorView
		<WikiAttachmentItemSelectorCriterion,
			WikiAttachmentItemSelectorReturnTypes>>
			getItemSelectorViews(
				WikiAttachmentItemSelectorCriterion
					wikiAttachmentItemSelectorCriterion) {

		List<ItemSelectorView> itemSelectorViews = new ArrayList<>();

		itemSelectorViews.add(new WikiAttachmentItemSelectorView());

		return (List)itemSelectorViews;
	}

}