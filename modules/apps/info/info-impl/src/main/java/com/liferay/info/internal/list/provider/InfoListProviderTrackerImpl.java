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

package com.liferay.info.internal.list.provider;

import com.liferay.info.item.provider.InfoItemServiceTracker;
import com.liferay.info.list.provider.InfoListProvider;
import com.liferay.info.list.provider.InfoListProviderTracker;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = InfoListProviderTracker.class)
public class InfoListProviderTrackerImpl implements InfoListProviderTracker {

	@Override
	public InfoListProvider<?> getInfoListProvider(String key) {
		return _infoItemServiceTracker.getInfoItemProviderByKey(
			InfoListProvider.class, key);
	}

	@Override
	public List<InfoListProvider<?>> getInfoListProviders() {
		return (List<InfoListProvider<?>>)
			(List<?>)_infoItemServiceTracker.getAllInfoItemServices(
				InfoListProvider.class);
	}

	@Override
	public List<InfoListProvider<?>> getInfoListProviders(Class<?> itemClass) {
		return getInfoListProviders(itemClass.getName());
	}

	@Override
	public List<InfoListProvider<?>> getInfoListProviders(
		String itemClassName) {

		return (List<InfoListProvider<?>>)
			(List<?>)_infoItemServiceTracker.getAllInfoItemServices(
				InfoListProvider.class, itemClassName);
	}

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

}