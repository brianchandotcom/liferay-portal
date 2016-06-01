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

package com.liferay.portal.lpkg.deployer.internal;

import com.liferay.portal.lpkg.deployer.LPKGWARBundleRegistry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.Bundle;
import org.osgi.service.component.annotations.Component;

/**
 * @author Shuyang Zhou
 */
@Component(immediate = true)
public class DefaultLPKGWARBundleRegistry implements LPKGWARBundleRegistry {

	@Override
	public Bundle register(Bundle warWrapperBundle, Bundle warBundle) {
		return _warBundles.put(warWrapperBundle, warBundle);
	}

	@Override
	public Bundle unregister(Bundle warWrapperBundle) {
		return _warBundles.remove(warWrapperBundle);
	}

	private final Map<Bundle, Bundle> _warBundles = new ConcurrentHashMap<>();

}