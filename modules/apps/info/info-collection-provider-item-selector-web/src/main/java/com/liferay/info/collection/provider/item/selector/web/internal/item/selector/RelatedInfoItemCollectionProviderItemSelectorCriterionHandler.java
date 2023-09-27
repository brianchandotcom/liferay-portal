/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.collection.provider.item.selector.web.internal.item.selector;

import com.liferay.info.collection.provider.item.selector.criterion.RelatedInfoItemCollectionProviderItemSelectorCriterion;
import com.liferay.item.selector.BaseItemSelectorCriterionHandler;
import com.liferay.item.selector.ItemSelectorCriterionHandler;

import org.osgi.service.component.annotations.Component;

/**
 * @author Víctor Galán
 */
@Component(service = ItemSelectorCriterionHandler.class)
public class RelatedInfoItemCollectionProviderItemSelectorCriterionHandler
	extends BaseItemSelectorCriterionHandler
		<RelatedInfoItemCollectionProviderItemSelectorCriterion> {

	@Override
	public Class<RelatedInfoItemCollectionProviderItemSelectorCriterion>
		getItemSelectorCriterionClass() {

		return RelatedInfoItemCollectionProviderItemSelectorCriterion.class;
	}

}