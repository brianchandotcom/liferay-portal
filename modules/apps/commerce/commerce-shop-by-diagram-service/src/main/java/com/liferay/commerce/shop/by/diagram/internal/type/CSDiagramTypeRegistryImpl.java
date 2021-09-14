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

package com.liferay.commerce.shop.by.diagram.internal.type;

import com.liferay.commerce.shop.by.diagram.internal.type.util.comparator.CSDiagramTypeOrderComparator;
import com.liferay.commerce.shop.by.diagram.type.CSDiagramType;
import com.liferay.commerce.shop.by.diagram.type.CSDiagramTypeRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;

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
@Component(
	enabled = false, immediate = true, service = CSDiagramTypeRegistry.class
)
public class CSDiagramTypeRegistryImpl implements CSDiagramTypeRegistry {

	@Override
	public CSDiagramType getCSDiagramType(String key) {
		ServiceWrapper<CSDiagramType> csDiagramTypeServiceWrapper =
			_serviceTrackerMap.getService(key);

		if (csDiagramTypeServiceWrapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No commerce product definition diagram type registered " +
						"with key " + key);
			}

			return null;
		}

		return csDiagramTypeServiceWrapper.getService();
	}

	@Override
	public List<CSDiagramType> getCSDiagramTypes() {
		List<CSDiagramType> csDiagramTypes = new ArrayList<>();

		List<ServiceWrapper<CSDiagramType>> csDiagramTypeServiceWrappers =
			ListUtil.fromCollection(_serviceTrackerMap.values());

		Collections.sort(
			csDiagramTypeServiceWrappers,
			_csDiagramTypeServiceWrapperOrderComparator);

		for (ServiceWrapper<CSDiagramType> csDiagramTypeServiceWrapper :
				csDiagramTypeServiceWrappers) {

			csDiagramTypes.add(csDiagramTypeServiceWrapper.getService());
		}

		return Collections.unmodifiableList(csDiagramTypes);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, CSDiagramType.class,
			"commerce.product.definition.diagram.type.key",
			ServiceTrackerCustomizerFactory.<CSDiagramType>serviceWrapper(
				bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CSDiagramTypeRegistryImpl.class);

	private final Comparator<ServiceWrapper<CSDiagramType>>
		_csDiagramTypeServiceWrapperOrderComparator =
			new CSDiagramTypeOrderComparator();
	private ServiceTrackerMap<String, ServiceWrapper<CSDiagramType>>
		_serviceTrackerMap;

}