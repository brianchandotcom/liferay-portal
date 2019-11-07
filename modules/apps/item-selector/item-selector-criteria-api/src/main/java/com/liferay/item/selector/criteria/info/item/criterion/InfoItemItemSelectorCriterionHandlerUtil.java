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

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

		if (Validator.isNull(infoItemItemSelectorCriterion.getItemType())) {
			return itemSelectorViews;
		}

		Stream<ItemSelectorView<InfoItemItemSelectorCriterion>> stream =
			itemSelectorViews.stream();

		return stream.filter(
			itemSelectorView -> {
				if (!(itemSelectorView instanceof InfoItemSelectorView)) {
					return false;
				}

				InfoItemSelectorView infoItemSelectorView =
					(InfoItemSelectorView)itemSelectorView;

				return Objects.equals(
					infoItemSelectorView.getClassName(),
					infoItemItemSelectorCriterion.getItemType());
			}
		).collect(
			Collectors.toList()
		);
	}

}