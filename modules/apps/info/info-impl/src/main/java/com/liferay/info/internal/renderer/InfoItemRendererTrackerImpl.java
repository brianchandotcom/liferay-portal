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

package com.liferay.info.internal.renderer;

import com.liferay.info.internal.util.GenericsUtil;
import com.liferay.info.renderer.InfoItemRenderer;
import com.liferay.info.renderer.InfoItemRendererTracker;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Jorge Ferrer
 */
@Component(immediate = true, service = InfoItemRendererTracker.class)
public class InfoItemRendererTrackerImpl implements InfoItemRendererTracker {

	@Override
	public InfoItemRenderer getInfoItemRenderer(String className) {
		if (Validator.isNull(className)) {
			return null;
		}

		return _infoItemRenderers.get(className);
	}

	@Override
	public List<InfoItemRenderer> getInfoItemRenderers() {
		return new ArrayList<>(_infoItemRenderers.values());
	}

	@Override
	public List<InfoItemRenderer> getInfoItemRenderers(Class<?> itemClass) {
		List<InfoItemRenderer> infoItemRenderers =
			_itemClassInfoItemRenderers.get(itemClass);

		if (infoItemRenderers != null) {
			return new ArrayList<>(infoItemRenderers);
		}

		return Collections.emptyList();
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void setInfoItemRenderer(InfoItemRenderer infoItemRenderer) {
		Class<?> clazz = infoItemRenderer.getClass();

		_infoItemRenderers.put(clazz.getName(), infoItemRenderer);

		List<InfoItemRenderer> itemClassInfoItemRenderers =
			_itemClassInfoItemRenderers.computeIfAbsent(
				GenericsUtil.getItemClass(infoItemRenderer),
				itemClass -> new ArrayList<>());

		itemClassInfoItemRenderers.add(infoItemRenderer);
	}

	protected void unsetInfoItemRenderer(InfoItemRenderer infoItemRenderer) {
		Class<?> clazz = infoItemRenderer.getClass();

		_infoItemRenderers.remove(clazz.getName());

		List<InfoItemRenderer> itemClassInfoItemRenderers =
			_itemClassInfoItemRenderers.get(
				GenericsUtil.getItemClass(infoItemRenderer));

		if (itemClassInfoItemRenderers != null) {
			_itemClassInfoItemRenderers.remove(infoItemRenderer);
		}
	}

	private final Map<String, InfoItemRenderer> _infoItemRenderers =
		new ConcurrentHashMap<>();
	private final Map<Class, List<InfoItemRenderer>>
		_itemClassInfoItemRenderers = new ConcurrentHashMap<>();

}