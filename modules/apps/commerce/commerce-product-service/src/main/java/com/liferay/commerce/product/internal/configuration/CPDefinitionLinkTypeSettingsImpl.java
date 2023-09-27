/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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