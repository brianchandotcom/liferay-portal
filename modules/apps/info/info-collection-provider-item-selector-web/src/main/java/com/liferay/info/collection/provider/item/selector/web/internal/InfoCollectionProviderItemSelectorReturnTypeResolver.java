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

package com.liferay.info.collection.provider.item.selector.web.internal;

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