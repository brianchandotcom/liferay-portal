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

package com.liferay.commerce.product.internal.links;

import com.liferay.commerce.product.links.CPDefinitionLinkType;
import com.liferay.commerce.product.links.CPDefinitionLinkTypeRegistry;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Marco Leo
 */
@Component(immediate = true)
public class CPDefinitionLinkTypeRegistryImpl
	implements CPDefinitionLinkTypeRegistry {

	@Override
	public List<String> getTypes() {
		List<String> types = new ArrayList<>();

		for (CPDefinitionLinkType cpDefinitionLinkType : _serviceTrackerList) {
			types.add(cpDefinitionLinkType.getType());
		}

		return types;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerList = ServiceTrackerListFactory.open(
			bundleContext, CPDefinitionLinkType.class);
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerList.close();
	}

	private ServiceTrackerList<CPDefinitionLinkType, CPDefinitionLinkType>
		_serviceTrackerList;

}