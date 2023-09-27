/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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