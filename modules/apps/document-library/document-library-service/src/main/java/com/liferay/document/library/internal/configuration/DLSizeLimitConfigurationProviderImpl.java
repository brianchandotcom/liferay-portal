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

package com.liferay.document.library.internal.configuration;

import com.liferay.document.library.configuration.DLSizeLimitConfigurationProvider;
import com.liferay.document.library.internal.configuration.admin.service.DLSizeLimitManagedServiceFactory;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = DLSizeLimitConfigurationProvider.class)
public class DLSizeLimitConfigurationProviderImpl
	implements DLSizeLimitConfigurationProvider {

	@Override
	public Map<String, Long> getGroupMimeTypeSizeLimit(long groupId) {
		return _dlSizeLimitManagedServiceFactory.getGroupMimeTypeSizeLimit(
			groupId);
	}

	@Reference
	private DLSizeLimitManagedServiceFactory _dlSizeLimitManagedServiceFactory;

}