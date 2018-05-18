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
@Component(immediate = true)
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