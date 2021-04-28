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

package com.liferay.layout.content.page.editor.web.internal.util;

import com.liferay.info.list.provider.InfoListProvider;
import com.liferay.info.list.provider.InfoListProviderTracker;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Víctor Galán
 */
@Component(immediate = true, service = {})
public class InfoListProviderTrackerUtil {

	public static InfoListProvider<?> getInfoListProvider(String key) {
		return _infoListProviderTracker.getInfoListProvider(key);
	}

	@Reference(unbind = "-")
	protected void setInfoListProviderTracker(
		InfoListProviderTracker infoListProviderTracker) {

		_infoListProviderTracker = infoListProviderTracker;
	}

	private static InfoListProviderTracker _infoListProviderTracker;

}