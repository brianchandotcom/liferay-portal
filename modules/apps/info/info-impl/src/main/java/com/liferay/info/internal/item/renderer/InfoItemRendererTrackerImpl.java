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
import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceComparator;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapperFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

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

		return _infoItemRenderersServiceTrackerMap.getService(key);
	}

	@Override
	public List<InfoItemRenderer> getInfoItemRenderers() {
		return new ArrayList<>(_infoItemRenderersServiceTrackerMap.values());
	}

	@Override
	public List<InfoItemRenderer> getInfoItemRenderers(String itemClassName) {
		List<InfoItemRenderer> infoItemRenderers =
			_itemClassNameInfoItemRendererServiceTrackerMap.getService(
				itemClassName);

		if (infoItemRenderers != null) {
			return new ArrayList<>(infoItemRenderers);
		}

		return Collections.emptyList();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_itemClassNameInfoItemRendererServiceTrackerMap =
			ServiceTrackerMapFactory.openMultiValueMap(
				bundleContext, InfoItemRenderer.class, null,
				ServiceReferenceMapperFactory.create(
					bundleContext,
					(infoItemRenderer, emitter) -> emitter.emit(
						GenericsUtil.getItemClassName(infoItemRenderer))),
				Collections.reverseOrder(
					new PropertyServiceReferenceComparator<>(
						"service.ranking")));

		_infoItemRenderersServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, InfoItemRenderer.class, null,
				ServiceReferenceMapperFactory.createFromFunction(
					bundleContext, InfoItemRenderer::getKey));
	}

	@Deactivate
	protected void deactivate() {
		_itemClassNameInfoItemRendererServiceTrackerMap.close();
	}

	private ServiceTrackerMap<String, InfoItemRenderer>
		_infoItemRenderersServiceTrackerMap;
	private ServiceTrackerMap<String, List<InfoItemRenderer>>
		_itemClassNameInfoItemRendererServiceTrackerMap;

}