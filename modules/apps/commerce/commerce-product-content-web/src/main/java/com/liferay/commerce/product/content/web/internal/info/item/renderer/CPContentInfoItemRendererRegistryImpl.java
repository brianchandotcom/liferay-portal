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

package com.liferay.commerce.product.content.web.internal.info.item.renderer;

import com.liferay.commerce.product.content.info.item.renderer.CPContentInfoItemRenderer;
import com.liferay.commerce.product.content.info.item.renderer.CPContentInfoItemRendererRegistry;
import com.liferay.commerce.product.content.web.internal.info.item.renderer.util.comparator.CPContentInfoItemRendererServiceWrapperOrderComparator;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = CPContentInfoItemRendererRegistry.class)
public class CPContentInfoItemRendererRegistryImpl
	implements CPContentInfoItemRendererRegistry {

	@Override
	public CPContentInfoItemRenderer getCPContentInfoItemRenderer(String key) {
		if (Validator.isNull(key)) {
			return null;
		}

		ServiceWrapper<CPContentInfoItemRenderer>
			cpContentInfoItemRendererServiceWrapper =
				_cpContentInfoItemRendererServiceTrackerMap.getService(key);

		if (cpContentInfoItemRendererServiceWrapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No CPContentInfoItemRenderer registered with key " + key);
			}

			return null;
		}

		return cpContentInfoItemRendererServiceWrapper.getService();
	}

	@Override
	public List<CPContentInfoItemRenderer> getCPContentInfoItemRenderers() {
		List<CPContentInfoItemRenderer> cpContentInfoItemRenderers =
			new ArrayList<>();

		List<ServiceWrapper<CPContentInfoItemRenderer>>
			cpContentInfoItemRendererServiceWrappers = ListUtil.fromCollection(
				_cpContentInfoItemRendererServiceTrackerMap.values());

		Collections.sort(
			cpContentInfoItemRendererServiceWrappers,
			_cpContentInfoItemRendererServiceWrapperOrderComparator);

		return Collections.unmodifiableList(cpContentInfoItemRenderers);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_cpContentInfoItemRendererServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, CPContentInfoItemRenderer.class,
				"commerce.product.content.info.item.renderer.key",
				ServiceTrackerCustomizerFactory.
					<CPContentInfoItemRenderer>serviceWrapper(bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_cpContentInfoItemRendererServiceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPContentInfoItemRendererRegistryImpl.class);

	private ServiceTrackerMap<String, ServiceWrapper<CPContentInfoItemRenderer>>
		_cpContentInfoItemRendererServiceTrackerMap;
	private final Comparator<ServiceWrapper<CPContentInfoItemRenderer>>
		_cpContentInfoItemRendererServiceWrapperOrderComparator =
			new CPContentInfoItemRendererServiceWrapperOrderComparator();

}