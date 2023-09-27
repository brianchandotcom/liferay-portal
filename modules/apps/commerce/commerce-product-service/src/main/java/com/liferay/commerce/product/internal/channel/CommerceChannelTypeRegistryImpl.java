/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.channel;

import com.liferay.commerce.product.channel.CommerceChannelType;
import com.liferay.commerce.product.channel.CommerceChannelTypeRegistry;
import com.liferay.commerce.product.internal.channel.comparator.CommerceChannelTypeOrderComparator;
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
 * @author Alec Sloan
 */
@Component(service = CommerceChannelTypeRegistry.class)
public class CommerceChannelTypeRegistryImpl
	implements CommerceChannelTypeRegistry {

	@Override
	public CommerceChannelType getCommerceChannelType(String key) {
		ServiceWrapper<CommerceChannelType> commerceChannelTypeServiceWrapper =
			_serviceTrackerMap.getService(key);

		if (commerceChannelTypeServiceWrapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("No CommerceChannelType registered with key " + key);
			}

			return null;
		}

		return commerceChannelTypeServiceWrapper.getService();
	}

	@Override
	public List<CommerceChannelType> getCommerceChannelTypes() {
		List<CommerceChannelType> commerceChannelTypes = new ArrayList<>();

		List<ServiceWrapper<CommerceChannelType>>
			commerceChannelTypeServiceWrappers = ListUtil.fromCollection(
				_serviceTrackerMap.values());

		Collections.sort(
			commerceChannelTypeServiceWrappers,
			_commerceChannelTypeServiceWrapperOrderComparator);

		for (ServiceWrapper<CommerceChannelType>
				commerceChannelTypeServiceWrapper :
					commerceChannelTypeServiceWrappers) {

			commerceChannelTypes.add(
				commerceChannelTypeServiceWrapper.getService());
		}

		return Collections.unmodifiableList(commerceChannelTypes);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, CommerceChannelType.class,
			"commerce.product.channel.type.key",
			ServiceTrackerCustomizerFactory.<CommerceChannelType>serviceWrapper(
				bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceChannelTypeRegistryImpl.class);

	private final Comparator<ServiceWrapper<CommerceChannelType>>
		_commerceChannelTypeServiceWrapperOrderComparator =
			new CommerceChannelTypeOrderComparator();
	private ServiceTrackerMap<String, ServiceWrapper<CommerceChannelType>>
		_serviceTrackerMap;

}