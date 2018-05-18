/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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