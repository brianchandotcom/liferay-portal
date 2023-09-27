/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.collection.provider.item.selector.web.internal.item.selector;

import com.liferay.info.collection.provider.InfoCollectionProvider;
import com.liferay.info.list.provider.item.selector.criterion.InfoListProviderItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorReturnTypeResolver;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eeudaldo Alonso
 */
@Component(service = ItemSelectorReturnTypeResolver.class)
public class InfoCollectionProviderItemSelectorReturnTypeResolver
	implements ItemSelectorReturnTypeResolver
		<InfoListProviderItemSelectorReturnType, InfoCollectionProvider<?>> {

	@Override
	public Class<InfoListProviderItemSelectorReturnType>
		getItemSelectorReturnTypeClass() {

		return InfoListProviderItemSelectorReturnType.class;
	}

	@Override
	public Class<InfoCollectionProvider<?>> getModelClass() {
		return (Class<InfoCollectionProvider<?>>)
			(Class<?>)InfoCollectionProvider.class;
	}

	@Override
	public String getValue(
		InfoCollectionProvider<?> infoCollectionProvider,
		ThemeDisplay themeDisplay) {

		return JSONUtil.put(
			"key", infoCollectionProvider.getKey()
		).put(
			"title", infoCollectionProvider.getLabel(themeDisplay.getLocale())
		).toString();
	}

}