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

package com.liferay.info.internal.item.provider;

import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.info.item.provider.InfoItemObjectProviderTracker;
import com.liferay.info.item.provider.InfoItemServiceTracker;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 * @author Jorge Ferrer
 */
@Component(immediate = true, service = InfoItemObjectProviderTracker.class)
public class InfoItemObjectProviderTrackerImpl
	implements InfoItemObjectProviderTracker {

	@Override
	public InfoItemObjectProvider<Object> getInfoItemObjectProvider(
		String itemClassName) {


		return _infoItemServiceTracker.getInfoItemService(
			InfoItemObjectProvider.class, itemClassName);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
	}

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

}