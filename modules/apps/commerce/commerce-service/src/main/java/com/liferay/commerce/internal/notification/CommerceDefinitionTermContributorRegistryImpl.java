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
@Component(
	immediate = true, service = CommerceDefinitionTermContributorRegistry.class
)
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