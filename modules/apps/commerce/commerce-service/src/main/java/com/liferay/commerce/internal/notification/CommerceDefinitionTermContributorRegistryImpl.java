/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.notification;

import com.liferay.commerce.order.CommerceDefinitionTermContributor;
import com.liferay.commerce.order.CommerceDefinitionTermContributorRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Luca Pellizzon
 */
@Component(service = CommerceDefinitionTermContributorRegistry.class)
public class CommerceDefinitionTermContributorRegistryImpl
	implements CommerceDefinitionTermContributorRegistry {

	@Override
	public List<CommerceDefinitionTermContributor>
		getDefinitionTermContributorsByContributorKey(String key) {

		return _getCommerceDefinitionTermContributors(
			key, _serviceTrackerMapByTermContributorKey);
	}

	@Override
	public List<CommerceDefinitionTermContributor>
		getDefinitionTermContributorsByNotificationTypeKey(String key) {

		return _getCommerceDefinitionTermContributors(
			key, _serviceTrackerMapByNotificationTypeKey);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMapByNotificationTypeKey =
			ServiceTrackerMapFactory.openMultiValueMap(
				bundleContext, CommerceDefinitionTermContributor.class,
				"commerce.notification.type.key",
				ServiceTrackerCustomizerFactory.
					<CommerceDefinitionTermContributor>serviceWrapper(
						bundleContext));

		_serviceTrackerMapByTermContributorKey =
			ServiceTrackerMapFactory.openMultiValueMap(
				bundleContext, CommerceDefinitionTermContributor.class,
				"commerce.definition.term.contributor.key",
				ServiceTrackerCustomizerFactory.
					<CommerceDefinitionTermContributor>serviceWrapper(
						bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMapByNotificationTypeKey.close();

		_serviceTrackerMapByTermContributorKey.close();
	}

	private List<CommerceDefinitionTermContributor>
		_getCommerceDefinitionTermContributors(
			String key,
			ServiceTrackerMap
				<String,
				 List
					 <ServiceTrackerCustomizerFactory.ServiceWrapper
						 <CommerceDefinitionTermContributor>>>
							serviceTrackerMap) {

		List
			<ServiceTrackerCustomizerFactory.ServiceWrapper
				<CommerceDefinitionTermContributor>>
					commerceDefinitionTermContributorWrappers =
						serviceTrackerMap.getService(key);

		if (commerceDefinitionTermContributorWrappers == null) {
			return Collections.emptyList();
		}

		List<CommerceDefinitionTermContributor>
			commerceDefinitionTermContributors = new ArrayList<>();

		for (ServiceTrackerCustomizerFactory.ServiceWrapper
				<CommerceDefinitionTermContributor>
					tableActionProviderServiceWrapper :
						commerceDefinitionTermContributorWrappers) {

			commerceDefinitionTermContributors.add(
				tableActionProviderServiceWrapper.getService());
		}

		return commerceDefinitionTermContributors;
	}

	private ServiceTrackerMap
		<String,
		 List
			 <ServiceTrackerCustomizerFactory.ServiceWrapper
				 <CommerceDefinitionTermContributor>>>
					_serviceTrackerMapByNotificationTypeKey;
	private ServiceTrackerMap
		<String,
		 List
			 <ServiceTrackerCustomizerFactory.ServiceWrapper
				 <CommerceDefinitionTermContributor>>>
					_serviceTrackerMapByTermContributorKey;

}