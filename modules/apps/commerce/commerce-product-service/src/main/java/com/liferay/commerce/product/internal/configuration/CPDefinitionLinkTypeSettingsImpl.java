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

package com.liferay.commerce.product.internal.configuration;

import com.liferay.commerce.product.configuration.CPDefinitionLinkTypeSettings;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapperFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.util.ArrayUtil;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = CPDefinitionLinkTypeSettings.class)
public class CPDefinitionLinkTypeSettingsImpl
	implements CPDefinitionLinkTypeSettings {

	@Override
	public String[] getTypes() {
		return ArrayUtil.toStringArray(_serviceTrackerMap.keySet());
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, CPDefinitionLinkTypeConfigurationWrapper.class, null,
			ServiceReferenceMapperFactory.create(
				bundleContext,
				(cpDefinitionLinkTypeConfigurationWrapper, emitter) ->
					emitter.emit(
						cpDefinitionLinkTypeConfigurationWrapper.getType())));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap<String, CPDefinitionLinkTypeConfigurationWrapper>
		_serviceTrackerMap;

}