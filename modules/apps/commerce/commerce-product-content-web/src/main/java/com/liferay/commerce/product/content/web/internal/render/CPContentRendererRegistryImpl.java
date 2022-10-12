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

package com.liferay.commerce.product.content.web.internal.render;

import com.liferay.commerce.product.content.render.CPContentRenderer;
import com.liferay.commerce.product.content.render.CPContentRendererRegistry;
import com.liferay.commerce.product.content.web.internal.render.util.comparator.CPContentRendererServiceWrapperOrderComparator;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Alessio Antonio Rendina
 */
@Component(immediate = true, service = CPContentRendererRegistry.class)
public class CPContentRendererRegistryImpl
	implements CPContentRendererRegistry {

	@Override
	public CPContentRenderer getCPContentRenderer(String key) {
		if (Validator.isNull(key)) {
			return null;
		}

		ServiceWrapper<CPContentRenderer> cpContentRendererServiceWrapper =
			_cpContentRendererServiceTrackerMap.getService(key);

		if (cpContentRendererServiceWrapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("No CPContentRenderer registered with key " + key);
			}

			return null;
		}

		return cpContentRendererServiceWrapper.getService();
	}

	@Override
	public List<CPContentRenderer> getCPContentRenderers(String cpType) {
		List<CPContentRenderer> cpContentRenderers = new ArrayList<>();

		List<ServiceWrapper<CPContentRenderer>>
			cpContentRendererServiceWrappers = ListUtil.fromCollection(
				_cpContentRendererServiceTrackerMap.values());

		Collections.sort(
			cpContentRendererServiceWrappers,
			_cpContentRendererServiceWrapperOrderComparator);

		for (ServiceWrapper<CPContentRenderer> cpContentRendererServiceWrapper :
				cpContentRendererServiceWrappers) {

			if (Validator.isNotNull(cpType)) {
				Map<String, Object> cpContentRendererServiceWrapperProperties =
					cpContentRendererServiceWrapper.getProperties();

				Object typeObject =
					cpContentRendererServiceWrapperProperties.get(
						"commerce.product.content.renderer.type");

				if (typeObject instanceof String[]) {
					String[] types = GetterUtil.getStringValues(typeObject);

					if (ArrayUtil.contains(types, cpType)) {
						cpContentRenderers.add(
							cpContentRendererServiceWrapper.getService());
					}
				}
				else if ((typeObject instanceof String) &&
						 cpType.equals(GetterUtil.getString(typeObject))) {

					cpContentRenderers.add(
						cpContentRendererServiceWrapper.getService());
				}
			}
		}

		return Collections.unmodifiableList(cpContentRenderers);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_cpContentRendererServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, CPContentRenderer.class,
				"commerce.product.content.renderer.key",
				ServiceTrackerCustomizerFactory.
					<CPContentRenderer>serviceWrapper(bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_cpContentRendererServiceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPContentRendererRegistryImpl.class);

	private ServiceTrackerMap<String, ServiceWrapper<CPContentRenderer>>
		_cpContentRendererServiceTrackerMap;
	private final Comparator<ServiceWrapper<CPContentRenderer>>
		_cpContentRendererServiceWrapperOrderComparator =
			new CPContentRendererServiceWrapperOrderComparator();

}