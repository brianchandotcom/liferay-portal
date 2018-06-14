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

package com.liferay.commerce.product.content.web.internal.render.list.entry;

import com.liferay.commerce.product.content.render.list.entry.CPContentListEntryRenderer;
import com.liferay.commerce.product.content.render.list.entry.CPContentListEntryRendererRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Alessio Antonio Rendina
 */
@Component(immediate = true, service = CPContentListEntryRendererRegistry.class)
public class CPContentListEntryRendererRegistryImpl
	implements CPContentListEntryRendererRegistry {

	@Override
	public CPContentListEntryRenderer getCPContentListEntryRenderer(
		String key, String cpContentListRendererKey, String cpType) {

		if (Validator.isNull(key) ||
			Validator.isNull(cpContentListRendererKey) ||
			Validator.isNull(cpType)) {

			return null;
		}

		ServiceWrapper<CPContentListEntryRenderer>
			cpContentListEntryRendererServiceWrapper =
				_cpContentListEntryRendererServiceTrackerMap.getService(key);

		if (cpContentListEntryRendererServiceWrapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No CPContentListEntryRenderer registered with key " + key);
			}

			return null;
		}

		Map<String, Object> cpContentListEntryRendererServiceWrapperProperties =
			cpContentListEntryRendererServiceWrapper.getProperties();

		String value = MapUtil.getString(
			cpContentListEntryRendererServiceWrapperProperties,
			"commerce.product.content.list.renderer.key");

		if (cpContentListRendererKey.equals(value)) {
			String type = MapUtil.getString(
				cpContentListEntryRendererServiceWrapperProperties,
				"commerce.product.content.list.entry.renderer.type");

			if (cpType.equals(type)) {
				return cpContentListEntryRendererServiceWrapper.getService();
			}
		}

		return null;
	}

	@Override
	public List<CPContentListEntryRenderer> getCPContentListEntryRenderers(
		String cpContentListRendererKey, String cpType) {

		List<CPContentListEntryRenderer> cpContentListEntryRenderers =
			new ArrayList<>();

		List<ServiceWrapper<CPContentListEntryRenderer>>
			cpContentListEntryRendererServiceWrappers = ListUtil.fromCollection(
				_cpContentListEntryRendererServiceTrackerMap.values());

		for (ServiceWrapper<CPContentListEntryRenderer>
				cpContentListEntryRendererServiceWrapper :
					cpContentListEntryRendererServiceWrappers) {

			if (Validator.isNotNull(cpContentListRendererKey) &&
				Validator.isNotNull(cpType)) {

				Map<String, Object>
					cpContentListRendererServiceWrapperProperties =
						cpContentListEntryRendererServiceWrapper.
							getProperties();

				String value = MapUtil.getString(
					cpContentListRendererServiceWrapperProperties,
					"commerce.product.content.list.renderer.key");
				String type = MapUtil.getString(
					cpContentListRendererServiceWrapperProperties,
					"commerce.product.content.list.entry.renderer.type");

				if (cpContentListRendererKey.equals(value) &&
					cpType.equals(type)) {

					cpContentListEntryRenderers.add(
						cpContentListEntryRendererServiceWrapper.getService());
				}
			}
		}

		return Collections.unmodifiableList(cpContentListEntryRenderers);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_cpContentListEntryRendererServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, CPContentListEntryRenderer.class,
				"commerce.product.content.list.entry.renderer.key",
				ServiceTrackerCustomizerFactory.
					<CPContentListEntryRenderer>serviceWrapper(bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_cpContentListEntryRendererServiceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPContentListEntryRendererRegistryImpl.class);

	private ServiceTrackerMap
		<String, ServiceWrapper<CPContentListEntryRenderer>>
			_cpContentListEntryRendererServiceTrackerMap;

}