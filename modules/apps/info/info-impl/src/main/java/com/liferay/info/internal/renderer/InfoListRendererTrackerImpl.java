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
import com.liferay.info.renderer.InfoListRenderer;
import com.liferay.info.renderer.InfoListRendererTracker;
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
@Component(immediate = true, service = InfoListRendererTracker.class)
public class InfoListRendererTrackerImpl implements InfoListRendererTracker {

	@Override
	public InfoListRenderer getInfoListRenderer(String className) {
		if (Validator.isNull(className)) {
			return null;
		}

		return _infoListRenderers.get(className);
	}

	@Override
	public List<InfoListRenderer> getInfoListRenderers() {
		return new ArrayList<>(_infoListRenderers.values());
	}

	@Override
	public List<InfoListRenderer> getInfoListRenderers(Class<?> itemClass) {
		List<InfoListRenderer> infoListRenderers =
			_itemClassInfoListRenderers.get(itemClass);

		if (infoListRenderers != null) {
			return new ArrayList<>(infoListRenderers);
		}

		return Collections.emptyList();
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void setInfoListRenderer(InfoListRenderer infoListRenderer) {
		Class<?> clazz = infoListRenderer.getClass();

		_infoListRenderers.put(clazz.getName(), infoListRenderer);

		List<InfoListRenderer> itemClassInfoListRenderers =
			_itemClassInfoListRenderers.computeIfAbsent(
				GenericsUtil.getItemClass(infoListRenderer),
				itemClass -> new ArrayList<>());

		itemClassInfoListRenderers.add(infoListRenderer);
	}

	protected void unsetInfoListRenderer(InfoListRenderer infoListRenderer) {
		Class<?> clazz = infoListRenderer.getClass();

		_infoListRenderers.remove(clazz.getName());

		List<InfoListRenderer> itemClassInfoListRenderers =
			_itemClassInfoListRenderers.get(
				GenericsUtil.getItemClass(infoListRenderer));

		if (itemClassInfoListRenderers != null) {
			_itemClassInfoListRenderers.remove(infoListRenderer);
		}
	}

	private final Map<String, InfoListRenderer> _infoListRenderers =
		new ConcurrentHashMap<>();
	private final Map<Class, List<InfoListRenderer>>
		_itemClassInfoListRenderers = new ConcurrentHashMap<>();

}