/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.list.item.selector.web.internal;

import com.liferay.info.collection.provider.item.selector.criterion.InfoCollectionProviderItemSelectorCriterion;
import com.liferay.item.selector.BaseItemSelectorCriterionHandler;
import com.liferay.item.selector.ItemSelectorCriterionHandler;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(service = ItemSelectorCriterionHandler.class)
public class AssetListItemSelectorCriterionHandler
	extends BaseItemSelectorCriterionHandler
		<InfoCollectionProviderItemSelectorCriterion> {

	@Override
	public Class<InfoCollectionProviderItemSelectorCriterion>
		getItemSelectorCriterionClass() {

		return InfoCollectionProviderItemSelectorCriterion.class;
	}

}