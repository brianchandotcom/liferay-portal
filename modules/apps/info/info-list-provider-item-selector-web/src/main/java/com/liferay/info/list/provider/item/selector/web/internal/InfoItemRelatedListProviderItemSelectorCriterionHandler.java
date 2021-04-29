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

package com.liferay.info.list.provider.item.selector.web.internal;

import com.liferay.info.list.provider.item.selector.criterion.InfoItemRelatedListProviderItemSelectorCriterion;
import com.liferay.item.selector.BaseItemSelectorCriterionHandler;
import com.liferay.item.selector.ItemSelectorCriterionHandler;

import org.osgi.service.component.annotations.Component;

/**
 * @author Víctor Galán
 */
@Component(service = ItemSelectorCriterionHandler.class)
public class InfoItemRelatedListProviderItemSelectorCriterionHandler
	extends BaseItemSelectorCriterionHandler
		<InfoItemRelatedListProviderItemSelectorCriterion> {

	@Override
	public Class<InfoItemRelatedListProviderItemSelectorCriterion>
		getItemSelectorCriterionClass() {

		return InfoItemRelatedListProviderItemSelectorCriterion.class;
	}

}