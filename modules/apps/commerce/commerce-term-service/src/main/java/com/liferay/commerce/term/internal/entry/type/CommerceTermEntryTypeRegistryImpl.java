/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.term.internal.entry.type;

import com.liferay.commerce.term.entry.type.CommerceTermEntryType;
import com.liferay.commerce.term.entry.type.CommerceTermEntryTypeRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = CommerceTermEntryTypeRegistry.class)
public class CommerceTermEntryTypeRegistryImpl
	implements CommerceTermEntryTypeRegistry {

	@Override
	public CommerceTermEntryType getCommerceTermEntryType(String key) {
		ServiceTrackerCustomizerFactory.ServiceWrapper<CommerceTermEntryType>
			commerceTermEntryTypeServiceWrapper = _serviceTrackerMap.getService(
				key);

		if (commerceTermEntryTypeServiceWrapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No commerce term entry type registered with key " + key);
			}

			return null;
		}

		return commerceTermEntryTypeServiceWrapper.getService();
	}

	@Override
	public List<CommerceTermEntryType> getCommerceTermEntryTypes() {
		List<CommerceTermEntryType> commerceTermEntryTypes = new ArrayList<>();

		List
			<ServiceTrackerCustomizerFactory.ServiceWrapper
				<CommerceTermEntryType>> commerceTermEntryTypeServiceWrappers =
					ListUtil.fromCollection(_serviceTrackerMap.values());

		for (ServiceTrackerCustomizerFactory.ServiceWrapper
				<CommerceTermEntryType> commerceTermEntryTypeServiceWrapper :
					commerceTermEntryTypeServiceWrappers) {

			commerceTermEntryTypes.add(
				commerceTermEntryTypeServiceWrapper.getService());
		}

		return Collections.unmodifiableList(commerceTermEntryTypes);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, CommerceTermEntryType.class,
			"commerce.term.entry.type.key",
			ServiceTrackerCustomizerFactory.
				<CommerceTermEntryType>serviceWrapper(bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceTermEntryTypeRegistryImpl.class);

	private ServiceTrackerMap
		<String,
		 ServiceTrackerCustomizerFactory.ServiceWrapper<CommerceTermEntryType>>
			_serviceTrackerMap;

}