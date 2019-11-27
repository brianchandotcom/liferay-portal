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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
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
		List<ServiceReference<InfoItemRenderer>> serviceReferences =
			_itemClassNameInfoItemRendererServiceReferences.get(itemClassName);

		if (serviceReferences != null) {
			List<InfoItemRenderer> infoItemRenderers = new ArrayList<>();

			for (ServiceReference<InfoItemRenderer> serviceReference :
					serviceReferences) {

				Bundle bundle = serviceReference.getBundle();

				BundleContext bundleContext = bundle.getBundleContext();

				InfoItemRenderer infoItemRenderer = bundleContext.getService(
					serviceReference);

				infoItemRenderers.add(infoItemRenderer);
			}

			return infoItemRenderers;
		}

		return Collections.emptyList();
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC, service = InfoItemRenderer.class
	)
	protected void setInfoItemRenderer(
		ServiceReference<InfoItemRenderer> serviceReference) {

		Bundle bundle = serviceReference.getBundle();

		BundleContext bundleContext = bundle.getBundleContext();

		InfoItemRenderer infoItemRenderer = bundleContext.getService(
			serviceReference);

		_infoItemRenderers.put(infoItemRenderer.getKey(), infoItemRenderer);

		List<ServiceReference<InfoItemRenderer>> itemClassInfoItemRenderers =
			_itemClassNameInfoItemRendererServiceReferences.computeIfAbsent(
				GenericsUtil.getItemClassName(infoItemRenderer),
				itemClass -> new ArrayList<>());

		itemClassInfoItemRenderers.add(serviceReference);

		Collections.sort(
			itemClassInfoItemRenderers,
			Collections.reverseOrder(
				(serviceReference1, serviceReference2) -> {
					Integer serviceRanking1 = GetterUtil.getInteger(
						serviceReference1.getProperty("service.ranking"));
					Integer serviceRanking2 = GetterUtil.getInteger(
						serviceReference2.getProperty("service.ranking"));

					return serviceRanking1.compareTo(serviceRanking2);
				}));
	}

	protected void unsetInfoItemRenderer(
		ServiceReference<InfoItemRenderer> serviceReference) {

		Bundle bundle = serviceReference.getBundle();

		BundleContext bundleContext = bundle.getBundleContext();

		InfoItemRenderer infoItemRenderer = bundleContext.getService(
			serviceReference);

		_infoItemRenderers.remove(infoItemRenderer.getKey());

		List<ServiceReference<InfoItemRenderer>> itemClassInfoItemRenderers =
			_itemClassNameInfoItemRendererServiceReferences.get(
				GenericsUtil.getItemClassName(infoItemRenderer));

		if (itemClassInfoItemRenderers != null) {
			itemClassInfoItemRenderers.remove(serviceReference);
		}
	}

	private final Map<String, InfoItemRenderer> _infoItemRenderers =
		new ConcurrentHashMap<>();
	private final Map<String, List<ServiceReference<InfoItemRenderer>>>
		_itemClassNameInfoItemRendererServiceReferences =
			new ConcurrentHashMap<>();

}