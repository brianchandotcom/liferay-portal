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

package com.liferay.item.selector;

import com.liferay.portal.kernel.registry.ServiceTrackerCustomizerFactory;
import com.liferay.portal.kernel.util.PredicateFilter;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerList;

import java.util.Collections;
import java.util.List;

/**
 * @author Roberto Díaz
 */
public abstract class
	BaseItemSelectorCriterionHandler<T extends ItemSelectorCriterion>
		implements ItemSelectorCriterionHandler {

	@Override
	@SuppressWarnings("unchecked")
	public List<ItemSelectorView<T>>
		getItemSelectorViews(
			ItemSelectorCriterion fileItemSelectorCriterion) {

		return (List)Collections.unmodifiableList(_itemSelectorViews);
	}

	private final ServiceTrackerList<ItemSelectorView> _itemSelectorViews =
		ServiceTrackerCollections.list(
			ItemSelectorView.class,
			ServiceTrackerCustomizerFactory.create(
				new PredicateFilter<ItemSelectorView>() {

					@Override
					public boolean filter(ItemSelectorView itemSelectorView) {
						Class<?> itemSelectorCriterionClass =
							itemSelectorView.getItemSelectorCriterionClass();

						return itemSelectorCriterionClass.isAssignableFrom(
							BaseItemSelectorCriterionHandler.this.
								getItemSelectorCriterionClass());
					}

				}));

}