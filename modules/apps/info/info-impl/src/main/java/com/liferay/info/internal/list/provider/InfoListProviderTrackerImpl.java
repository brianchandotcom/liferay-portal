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

import com.liferay.info.internal.util.GenericsUtil;
import com.liferay.info.list.provider.InfoListProvider;
import com.liferay.info.list.provider.InfoListProviderTracker;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = InfoListProviderTracker.class)
public class InfoListProviderTrackerImpl implements InfoListProviderTracker {

	@Override
	public InfoListProvider getInfoListProvider(String className) {
		if (Validator.isNull(className)) {
			return null;
		}

		return _infoListProviders.get(className);
	}

	@Override
	public List<InfoListProvider> getInfoListProviders() {
		return new ArrayList<>(_infoListProviders.values());
	}

	@Override
	public List<InfoListProvider> getInfoListProviders(Class<?> itemClass) {
		List<InfoListProvider> infoListProviders =
			_itemClassInfoListProviders.get(itemClass);

		if (infoListProviders != null) {
			return new ArrayList<>(infoListProviders);
		}

		return Collections.emptyList();
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void setInfoListProvider(InfoListProvider infoListProvider) {
		_infoListProviders.put(infoListProvider.getKey(), infoListProvider);

		List<InfoListProvider> itemClassInfoListProviders =
			_itemClassInfoListProviders.computeIfAbsent(
				GenericsUtil.getItemClass(infoListProvider),
				itemClass -> new ArrayList<>());

		itemClassInfoListProviders.add(infoListProvider);
	}

	protected void unsetInfoListProvider(InfoListProvider infoListProvider) {
		_infoListProviders.remove(infoListProvider.getKey());

		List<InfoListProvider> itemClassInfoListProviders =
			_itemClassInfoListProviders.get(
				GenericsUtil.getItemClass(infoListProvider));

		if (itemClassInfoListProviders != null) {
			itemClassInfoListProviders.remove(infoListProvider);
		}
	}

	private final Map<String, InfoListProvider> _infoListProviders =
		new ConcurrentHashMap<>();
	private final Map<Class, List<InfoListProvider>>
		_itemClassInfoListProviders = new ConcurrentHashMap<>();

}