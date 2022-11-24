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

package com.liferay.info.internal.list.renderer;

import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.list.renderer.InfoListRenderer;
import com.liferay.info.list.renderer.InfoListRendererRegistry;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Ferrer
 */
@Component(service = InfoListRendererRegistry.class)
public class InfoListRendererRegistryImpl implements InfoListRendererRegistry {

	@Override
	public InfoListRenderer<?> getInfoListRenderer(String key) {
		return _infoItemServiceRegistry.getInfoItemService(
			InfoListRenderer.class, key);
	}

	@Override
	public List<InfoListRenderer<?>> getInfoListRenderers() {
		return (List<InfoListRenderer<?>>)
			(List<?>)_infoItemServiceRegistry.getAllInfoItemServices(
				InfoListRenderer.class);
	}

	@Override
	public List<InfoListRenderer<?>> getInfoListRenderers(
		String itemClassName) {

		return (List<InfoListRenderer<?>>)
			(List<?>)_infoItemServiceRegistry.getAllInfoItemServices(
				InfoListRenderer.class, itemClassName);
	}

	@Reference
	private InfoItemServiceRegistry _infoItemServiceRegistry;

}