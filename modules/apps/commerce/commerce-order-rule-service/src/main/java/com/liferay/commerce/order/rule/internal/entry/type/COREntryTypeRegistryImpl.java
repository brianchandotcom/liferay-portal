/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.rule.internal.entry.type;

import com.liferay.commerce.order.rule.entry.type.COREntryType;
import com.liferay.commerce.order.rule.entry.type.COREntryTypeRegistry;
import com.liferay.commerce.order.rule.internal.entry.type.comparator.COREntryTypeOrderComparator;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
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
 * @author Luca Pellizzon
 */
@Component(service = COREntryTypeRegistry.class)
public class COREntryTypeRegistryImpl implements COREntryTypeRegistry {

	@Override
	public COREntryType getCOREntryType(String key) {
		ServiceTrackerCustomizerFactory.ServiceWrapper<COREntryType>
			corEntryTypeServiceWrapper = _serviceTrackerMap.getService(key);

		if (corEntryTypeServiceWrapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No commerce order rule entry type registered with key " +
						key);
			}

			return null;
		}

		return corEntryTypeServiceWrapper.getService();
	}

	@Override
	public List<COREntryType> getCOREntryTypes() {
		List<COREntryType> corEntryTypes = new ArrayList<>();

		List<ServiceTrackerCustomizerFactory.ServiceWrapper<COREntryType>>
			corEntryTypeServiceWrappers = ListUtil.fromCollection(
				_serviceTrackerMap.values());

		Collections.sort(
			corEntryTypeServiceWrappers,
			_corEntryTypeServiceWrapperOrderComparator);

		for (ServiceTrackerCustomizerFactory.ServiceWrapper<COREntryType>
				corEntryTypeServiceWrapper : corEntryTypeServiceWrappers) {

			corEntryTypes.add(corEntryTypeServiceWrapper.getService());
		}

		return Collections.unmodifiableList(corEntryTypes);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, COREntryType.class,
			"commerce.order.rule.entry.type.key",
			ServiceTrackerCustomizerFactory.<COREntryType>serviceWrapper(
				bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		COREntryTypeRegistryImpl.class);

	private final Comparator
		<ServiceTrackerCustomizerFactory.ServiceWrapper<COREntryType>>
			_corEntryTypeServiceWrapperOrderComparator =
				new COREntryTypeOrderComparator();
	private ServiceTrackerMap
		<String, ServiceTrackerCustomizerFactory.ServiceWrapper<COREntryType>>
			_serviceTrackerMap;

}