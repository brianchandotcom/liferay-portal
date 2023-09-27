/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
@Component(service = CPDefinitionLinkTypeRegistry.class)
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

	private ServiceTrackerList<CPDefinitionLinkType> _serviceTrackerList;

}