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

package com.liferay.commerce.product.content.web.internal.render.list;

import com.liferay.commerce.product.content.render.list.CPContentListRenderer;
import com.liferay.commerce.product.content.render.list.CPContentListRendererRegistry;
import com.liferay.commerce.product.content.web.internal.render.util.comparator.CPContentListRendererServiceWrapperOrderComparator;
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
@Component(immediate = true, service = CPContentListRendererRegistry.class)
public class CPContentListRendererRegistryImpl
	implements CPContentListRendererRegistry {

	@Override
	public CPContentListRenderer getCPContentListRenderer(String key) {
		if (Validator.isNull(key)) {
			return null;
		}

		ServiceWrapper<CPContentListRenderer>
			cpContentListRendererServiceWrapper =
				_cpContentListRendererServiceTrackerMap.getService(key);

		if (cpContentListRendererServiceWrapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No CPContentListRenderer registered with key " + key);
			}

			return null;
		}

		return cpContentListRendererServiceWrapper.getService();
	}

	@Override
	public List<CPContentListRenderer> getCPContentListRenderers(
		String portletName) {

		List<CPContentListRenderer> cpContentListRenderers = new ArrayList<>();

		List<ServiceWrapper<CPContentListRenderer>>
			cpContentListRendererServiceWrappers = ListUtil.fromCollection(
				_cpContentListRendererServiceTrackerMap.values());

		Collections.sort(
			cpContentListRendererServiceWrappers,
			_cpContentListRendererServiceWrapperOrderComparator);

		for (ServiceWrapper<CPContentListRenderer>
				cpContentListRendererServiceWrapper :
					cpContentListRendererServiceWrappers) {

			if (Validator.isNotNull(portletName)) {
				Map<String, Object>
					cpContentListRendererServiceWrapperProperties =
						cpContentListRendererServiceWrapper.getProperties();

				Object valueObject =
					cpContentListRendererServiceWrapperProperties.get(
						"commerce.product.content.list.renderer.portlet.name");

				if (valueObject instanceof String[]) {
					String[] values = GetterUtil.getStringValues(valueObject);

					if (ArrayUtil.contains(values, portletName)) {
						cpContentListRenderers.add(
							cpContentListRendererServiceWrapper.getService());
					}
				}
				else if ((valueObject instanceof String) &&
						 portletName.equals(
							 GetterUtil.getString(valueObject))) {

					cpContentListRenderers.add(
						cpContentListRendererServiceWrapper.getService());
				}
			}
		}

		return Collections.unmodifiableList(cpContentListRenderers);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_cpContentListRendererServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, CPContentListRenderer.class,
				"commerce.product.content.list.renderer.key",
				ServiceTrackerCustomizerFactory.
					<CPContentListRenderer>serviceWrapper(bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_cpContentListRendererServiceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPContentListRendererRegistryImpl.class);

	private ServiceTrackerMap<String, ServiceWrapper<CPContentListRenderer>>
		_cpContentListRendererServiceTrackerMap;
	private final Comparator<ServiceWrapper<CPContentListRenderer>>
		_cpContentListRendererServiceWrapperOrderComparator =
			new CPContentListRendererServiceWrapperOrderComparator();

}