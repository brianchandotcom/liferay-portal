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

package com.liferay.info.internal.item.renderer;

import com.liferay.info.item.provider.InfoItemServiceTracker;
import com.liferay.info.item.renderer.InfoItemRenderer;
import com.liferay.info.item.renderer.InfoItemRendererTracker;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Ferrer
 */
@Component(immediate = true, service = InfoItemRendererTracker.class)
public class InfoItemRendererTrackerImpl implements InfoItemRendererTracker {

	@Override
	public InfoItemRenderer<?> getInfoItemRenderer(String key) {
		return _infoItemServiceTracker.getInfoItemProviderByKey(
			InfoItemRenderer.class, key);
	}

	@Override
	public List<InfoItemRenderer<?>> getInfoItemRenderers() {
		return (List<InfoItemRenderer<?>>)
			(List<?>)_infoItemServiceTracker.getAllInfoItemServices(
				InfoItemRenderer.class);
	}

	@Override
	public List<InfoItemRenderer<?>> getInfoItemRenderers(
		String itemClassName) {

		return (List<InfoItemRenderer<?>>)
			(List<?>)_infoItemServiceTracker.getAllInfoItemServices(
				InfoItemRenderer.class, itemClassName);
	}

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

}