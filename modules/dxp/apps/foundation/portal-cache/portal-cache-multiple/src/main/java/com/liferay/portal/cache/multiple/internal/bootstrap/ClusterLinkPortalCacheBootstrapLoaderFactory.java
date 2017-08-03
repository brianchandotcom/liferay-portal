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

package com.liferay.portal.cache.multiple.internal.bootstrap;

import com.liferay.portal.cache.PortalCacheBootstrapLoader;
import com.liferay.portal.cache.PortalCacheBootstrapLoaderFactory;

import java.util.Properties;

import org.osgi.service.component.annotations.Component;

/**
 * @author Tina Tian
 */
@Component(
	enabled = false, immediate = true,
	service = PortalCacheBootstrapLoaderFactory.class
)
public class ClusterLinkPortalCacheBootstrapLoaderFactory
	implements PortalCacheBootstrapLoaderFactory {

	@Override
	public PortalCacheBootstrapLoader create(Properties properties) {
		return new ClusterLinkPortalCacheBootstrapLoader(properties);
	}

}