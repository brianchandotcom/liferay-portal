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

package com.liferay.depot.web.internal.item.selector.util;

import com.liferay.item.selector.ItemSelector;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(immediate = true, service = {})
public class ItemSelectorUtil {

	public static final ItemSelector getItemSelector() {
		return _itemSelectorUtil._getItemSelector();
	}

	@Activate
	protected void activate() {
		_itemSelectorUtil = this;
	}

	@Deactivate
	protected void deactivate() {
		_itemSelectorUtil = null;
	}

	private ItemSelector _getItemSelector() {
		return _itemSelector;
	}

	private static ItemSelectorUtil _itemSelectorUtil;

	@Reference
	private ItemSelector _itemSelector;

}