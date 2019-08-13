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

package com.liferay.dynamic.data.mapping.data.provider.internal.settings;

import com.liferay.dynamic.data.mapping.data.provider.internal.rest.DDMRESTDataProviderSettings;
import com.liferay.dynamic.data.mapping.data.provider.settings.DDMDataProviderSettingsProvider;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jonathan McCann
 */
@Component(
	immediate = true, property = "ddm.data.provider.type=rest",
	service = DDMDataProviderSettingsProvider.class
)
public class DDMRESTDataProviderSettingsProvider
	implements DDMDataProviderSettingsProvider {

	@Override
	public Class<?> getSettings() {
		return DDMRESTDataProviderSettings.class;
	}

}