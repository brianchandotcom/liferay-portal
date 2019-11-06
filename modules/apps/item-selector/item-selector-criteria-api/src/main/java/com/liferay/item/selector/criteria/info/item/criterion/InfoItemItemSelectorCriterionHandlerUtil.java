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

package com.liferay.item.selector.criteria.info.item.criterion;

import com.liferay.info.item.selector.InfoItemSelectorView;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * @author Jürgen Kappler
 */
public class InfoItemItemSelectorCriterionHandlerUtil {

	public static List<ItemSelectorView<InfoItemItemSelectorCriterion>>
		getFilteredItemSelectorViews(
			ItemSelectorCriterion itemSelectorCriterion,
			List<ItemSelectorView<InfoItemItemSelectorCriterion>>
				itemSelectorViews) {

		InfoItemItemSelectorCriterion infoItemItemSelectorCriterion =
			(InfoItemItemSelectorCriterion)itemSelectorCriterion;

		if (Validator.isNull(infoItemItemSelectorCriterion.getClassName())) {
			return itemSelectorViews;
		}

		List<ItemSelectorView<InfoItemItemSelectorCriterion>>
			visibleItemSelectorViews = new ArrayList<>();

		Iterator<ItemSelectorView<InfoItemItemSelectorCriterion>> iterator =
			itemSelectorViews.iterator();

		while (iterator.hasNext()) {
			ItemSelectorView itemSelectorView = iterator.next();

			if (!(itemSelectorView instanceof InfoItemSelectorView)) {
				continue;
			}

			InfoItemSelectorView infoItemSelectorView =
				(InfoItemSelectorView)itemSelectorView;

			if (Objects.equals(
					infoItemSelectorView.getClassName(),
					infoItemItemSelectorCriterion.getClassName())) {

				visibleItemSelectorViews.add(itemSelectorView);
			}
		}

		return visibleItemSelectorViews;
	}

}