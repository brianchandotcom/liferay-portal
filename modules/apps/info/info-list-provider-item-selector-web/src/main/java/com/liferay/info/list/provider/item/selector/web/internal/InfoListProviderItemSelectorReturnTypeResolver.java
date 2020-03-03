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

import com.liferay.info.list.provider.InfoListProvider;
import com.liferay.info.list.provider.item.selector.criterion.InfoListProviderItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorReturnTypeResolver;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eeudaldo Alonso
 */
@Component(service = ItemSelectorReturnTypeResolver.class)
public class InfoListProviderItemSelectorReturnTypeResolver
	implements ItemSelectorReturnTypeResolver
		<InfoListProviderItemSelectorReturnType, InfoListProvider> {

	@Override
	public Class<InfoListProviderItemSelectorReturnType>
		getItemSelectorReturnTypeClass() {

		return InfoListProviderItemSelectorReturnType.class;
	}

	@Override
	public Class<InfoListProvider> getModelClass() {
		return InfoListProvider.class;
	}

	@Override
	public String getValue(
		InfoListProvider infoListProvider, ThemeDisplay themeDisplay) {

		return JSONUtil.put(
			"key", infoListProvider.getKey()
		).put(
			"title", infoListProvider.getLabel(themeDisplay.getLocale())
		).toString();
	}

}