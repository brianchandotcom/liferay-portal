/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.notification.internal.type;

import com.liferay.commerce.notification.internal.type.comparator.CommerceNotificationTypeOrderComparator;
import com.liferay.commerce.notification.type.CommerceNotificationType;
import com.liferay.commerce.notification.type.CommerceNotificationTypeRegistry;
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
@Component(service = CommerceNotificationTypeRegistry.class)
public class CommerceNotificationTypeRegistryImpl
	implements CommerceNotificationTypeRegistry {

	@Override
	public CommerceNotificationType getCommerceNotificationType(String key) {
		ServiceWrapper<CommerceNotificationType>
			commerceEmailNotificationTypeServiceWrapper =
				_serviceTrackerMap.getService(key);

		if (commerceEmailNotificationTypeServiceWrapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No CommerceNotificationType registered with key " + key);
			}

			return null;
		}

		return commerceEmailNotificationTypeServiceWrapper.getService();
	}

	@Override
	public List<CommerceNotificationType> getCommerceNotificationTypes() {
		List<CommerceNotificationType> commerceNotificationTypes =
			new ArrayList<>();

		List<ServiceWrapper<CommerceNotificationType>>
			commerceNotificationTypeServiceWrappers = ListUtil.fromCollection(
				_serviceTrackerMap.values());

		Collections.sort(
			commerceNotificationTypeServiceWrappers,
			_commerceNotificationTypeServiceWrapperOrderComparator);

		for (ServiceWrapper<CommerceNotificationType>
				commerceNotificationTypeServiceWrapper :
					commerceNotificationTypeServiceWrappers) {

			commerceNotificationTypes.add(
				commerceNotificationTypeServiceWrapper.getService());
		}

		return Collections.unmodifiableList(commerceNotificationTypes);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, CommerceNotificationType.class,
			"commerce.notification.type.key",
			ServiceTrackerCustomizerFactory.
				<CommerceNotificationType>serviceWrapper(bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceNotificationTypeRegistryImpl.class);

	private final Comparator<ServiceWrapper<CommerceNotificationType>>
		_commerceNotificationTypeServiceWrapperOrderComparator =
			new CommerceNotificationTypeOrderComparator();
	private ServiceTrackerMap<String, ServiceWrapper<CommerceNotificationType>>
		_serviceTrackerMap;

}