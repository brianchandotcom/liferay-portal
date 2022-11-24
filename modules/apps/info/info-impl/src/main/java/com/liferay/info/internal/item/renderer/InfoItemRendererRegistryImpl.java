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

import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.renderer.InfoItemRenderer;
import com.liferay.info.item.renderer.InfoItemRendererRegistry;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Ferrer
 */
@Component(service = InfoItemRendererRegistry.class)
public class InfoItemRendererRegistryImpl implements InfoItemRendererRegistry {

	@Override
	public InfoItemRenderer<?> getInfoItemRenderer(String key) {
		return _infoItemServiceRegistry.getInfoItemService(
			InfoItemRenderer.class, key);
	}

	@Override
	public List<InfoItemRenderer<?>> getInfoItemRenderers() {
		return (List<InfoItemRenderer<?>>)
			(List<?>)_infoItemServiceRegistry.getAllInfoItemServices(
				InfoItemRenderer.class);
	}

	@Override
	public List<InfoItemRenderer<?>> getInfoItemRenderers(
		String itemClassName) {

		return (List<InfoItemRenderer<?>>)
			(List<?>)_infoItemServiceRegistry.getAllInfoItemServices(
				InfoItemRenderer.class, itemClassName);
	}

	@Reference
	private InfoItemServiceRegistry _infoItemServiceRegistry;

}