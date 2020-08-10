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
import com.liferay.commerce.product.content.web.internal.render.util.comparator.CPContentListEntryRendererServiceWrapperOrderComparator;
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
import java.util.Objects;

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
		String key, String portletName, String cpType) {

		if (Validator.isNull(key) || Validator.isNull(portletName)) {
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

		Object portletNameObject =
			cpContentListEntryRendererServiceWrapperProperties.get(
				"commerce.product.content.list.entry.renderer.portlet.name");

		if (portletNameObject instanceof String[]) {
			String[] portletNames = GetterUtil.getStringValues(
				portletNameObject);

			if (!ArrayUtil.contains(portletNames, portletName)) {
				return null;
			}
		}
		else if ((portletNameObject instanceof String) &&
				 !portletName.equals(GetterUtil.getString(portletNameObject))) {

			return null;
		}

		Object typeObject =
			cpContentListEntryRendererServiceWrapperProperties.get(
				"commerce.product.content.list.entry.renderer.type");

		if (typeObject == null) {
			return cpContentListEntryRendererServiceWrapper.getService();
		}
		else if (typeObject instanceof String[]) {
			String[] types = GetterUtil.getStringValues(typeObject);

			if ((cpType != null) && ArrayUtil.contains(types, cpType)) {
				return cpContentListEntryRendererServiceWrapper.getService();
			}
		}
		else if ((typeObject instanceof String) &&
				 Objects.equals(cpType, GetterUtil.getString(typeObject))) {

			return cpContentListEntryRendererServiceWrapper.getService();
		}

		return null;
	}

	@Override
	public List<CPContentListEntryRenderer> getCPContentListEntryRenderers(
		String portletName, String cpType) {

		List<CPContentListEntryRenderer> cpContentListEntryRenderers =
			new ArrayList<>();

		List<ServiceWrapper<CPContentListEntryRenderer>>
			cpContentListEntryRendererServiceWrappers = ListUtil.fromCollection(
				_cpContentListEntryRendererServiceTrackerMap.values());

		Collections.sort(
			cpContentListEntryRendererServiceWrappers,
			_cpContentListEntryRendererServiceWrapperOrderComparator);

		for (ServiceWrapper<CPContentListEntryRenderer>
				cpContentListEntryRendererServiceWrapper :
					cpContentListEntryRendererServiceWrappers) {

			if (Validator.isNull(portletName)) {
				continue;
			}

			Map<String, Object>
				cpContentListEntryRendererServiceWrapperProperties =
					cpContentListEntryRendererServiceWrapper.getProperties();

			Object portletNameObject =
				cpContentListEntryRendererServiceWrapperProperties.get(
					"commerce.product.content.list.entry.renderer.portlet." +
						"name");

			if (portletNameObject instanceof String[]) {
				String[] portletNames = GetterUtil.getStringValues(
					portletNameObject);

				if (!ArrayUtil.contains(portletNames, portletName)) {
					continue;
				}
			}
			else if ((portletNameObject instanceof String) &&
					 !portletName.equals(
						 GetterUtil.getString(portletNameObject))) {

				continue;
			}

			Object typeObject =
				cpContentListEntryRendererServiceWrapperProperties.get(
					"commerce.product.content.list.entry.renderer.type");

			if (typeObject == null) {
				cpContentListEntryRenderers.add(
					cpContentListEntryRendererServiceWrapper.getService());
			}

			if (typeObject instanceof String[]) {
				String[] types = GetterUtil.getStringValues(typeObject);

				if ((cpType != null) && ArrayUtil.contains(types, cpType)) {
					cpContentListEntryRenderers.add(
						cpContentListEntryRendererServiceWrapper.getService());
				}
			}
			else if ((typeObject instanceof String) &&
					 Objects.equals(cpType, GetterUtil.getString(typeObject))) {

				cpContentListEntryRenderers.add(
					cpContentListEntryRendererServiceWrapper.getService());
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
	private final Comparator<ServiceWrapper<CPContentListEntryRenderer>>
		_cpContentListEntryRendererServiceWrapperOrderComparator =
			new CPContentListEntryRendererServiceWrapperOrderComparator();

}