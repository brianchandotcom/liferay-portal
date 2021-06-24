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

package com.liferay.info.internal.item.selector;

import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.selector.InfoItemSelector;
import com.liferay.info.item.selector.InfoItemSelectorTracker;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = InfoItemSelectorTracker.class)
public class InfoItemSelectorTrackerImpl implements InfoItemSelectorTracker {

	@Override
	public InfoItemSelector<?> getInfoItemSelector(String key) {
		return _infoItemServiceTracker.getInfoItemService(
			InfoItemSelector.class, key);
	}

	@Override
	public List<InfoItemSelector<?>> getInfoItemSelectors() {
		return (List<InfoItemSelector<?>>)
			(List<?>)_infoItemServiceTracker.getAllInfoItemServices(
				InfoItemSelector.class);
	}

	@Override
	public List<InfoItemSelector<?>> getInfoItemSelectors(
		String itemClassName) {

		return (List<InfoItemSelector<?>>)
			(List<?>)_infoItemServiceTracker.getAllInfoItemServices(
				InfoItemSelector.class, itemClassName);
	}

	@Override
	public Set<String> getInfoItemSelectorsClassNames() {
		return new HashSet(
			_infoItemServiceTracker.getInfoItemClassNames(
				InfoItemSelector.class));
	}

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

}