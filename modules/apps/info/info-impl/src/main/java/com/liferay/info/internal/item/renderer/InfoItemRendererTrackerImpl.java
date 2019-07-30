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

import com.liferay.info.internal.util.GenericsUtil;
import com.liferay.info.item.renderer.InfoItemRenderer;
import com.liferay.info.item.renderer.InfoItemRendererTracker;
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
	public InfoItemRenderer getInfoItemRenderer(String key) {
		if (Validator.isNull(key)) {
			return null;
		}

		return _infoItemRenderers.get(key);
	}

	@Override
	public List<InfoItemRenderer> getInfoItemRenderers() {
		return new ArrayList<>(_infoItemRenderers.values());
	}

	@Override
	public List<InfoItemRenderer> getInfoItemRenderers(String itemClassName) {
		List<InfoItemRenderer> infoItemRenderers =
			_itemClassNameInfoItemRenderers.get(itemClassName);

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
		_infoItemRenderers.put(infoItemRenderer.getKey(), infoItemRenderer);

		List<InfoItemRenderer> itemClassInfoItemRenderers =
			_itemClassNameInfoItemRenderers.computeIfAbsent(
				GenericsUtil.getItemClassName(infoItemRenderer),
				itemClass -> new ArrayList<>());

		itemClassInfoItemRenderers.add(infoItemRenderer);
	}

	protected void unsetInfoItemRenderer(InfoItemRenderer infoItemRenderer) {
		_infoItemRenderers.remove(infoItemRenderer.getKey());

		List<InfoItemRenderer> itemClassInfoItemRenderers =
			_itemClassNameInfoItemRenderers.get(
				GenericsUtil.getItemClassName(infoItemRenderer));

		if (itemClassInfoItemRenderers != null) {
			itemClassInfoItemRenderers.remove(infoItemRenderer);
		}
	}

	private final Map<String, InfoItemRenderer> _infoItemRenderers =
		new ConcurrentHashMap<>();
	private final Map<String, List<InfoItemRenderer>>
		_itemClassNameInfoItemRenderers = new ConcurrentHashMap<>();

}