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

package com.liferay.notification.internal.term;

import com.liferay.notification.term.contributor.DefinitionTermContributor;
import com.liferay.notification.term.contributor.DefinitionTermContributorRegistry;
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
@Component(immediate = true, service = DefinitionTermContributorRegistry.class)
public class DefinitionTermContributorRegistryImpl
	implements DefinitionTermContributorRegistry {

	@Override
	public List<DefinitionTermContributor>
		getDefinitionTermContributorsByContributorKey(String key) {

		return _getDefinitionTermContributors(
			key, _serviceTrackerMapByTermContributorKey);
	}

	@Override
	public List<DefinitionTermContributor>
		getDefinitionTermContributorsByNotificationTypeKey(String key) {

		return _getDefinitionTermContributors(
			key, _serviceTrackerMapByNotificationTypeKey);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMapByNotificationTypeKey =
			ServiceTrackerMapFactory.openMultiValueMap(
				bundleContext, DefinitionTermContributor.class,
				"notification.type.key",
				ServiceTrackerCustomizerFactory.
					<DefinitionTermContributor>serviceWrapper(bundleContext));

		_serviceTrackerMapByTermContributorKey =
			ServiceTrackerMapFactory.openMultiValueMap(
				bundleContext, DefinitionTermContributor.class,
				"definition.term.contributor.key",
				ServiceTrackerCustomizerFactory.
					<DefinitionTermContributor>serviceWrapper(bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMapByNotificationTypeKey.close();

		_serviceTrackerMapByTermContributorKey.close();
	}

	private List<DefinitionTermContributor> _getDefinitionTermContributors(
		String key,
		ServiceTrackerMap
			<String,
			 List
				 <ServiceTrackerCustomizerFactory.ServiceWrapper
					 <DefinitionTermContributor>>> serviceTrackerMap) {

		List
			<ServiceTrackerCustomizerFactory.ServiceWrapper
				<DefinitionTermContributor>> definitionTermContributorWrappers =
					serviceTrackerMap.getService(key);

		if (definitionTermContributorWrappers == null) {
			return Collections.emptyList();
		}

		List<DefinitionTermContributor> definitionTermContributors =
			new ArrayList<>();

		for (ServiceTrackerCustomizerFactory.ServiceWrapper
				<DefinitionTermContributor> tableActionProviderServiceWrapper :
					definitionTermContributorWrappers) {

			definitionTermContributors.add(
				tableActionProviderServiceWrapper.getService());
		}

		return definitionTermContributors;
	}

	private ServiceTrackerMap
		<String,
		 List
			 <ServiceTrackerCustomizerFactory.ServiceWrapper
				 <DefinitionTermContributor>>>
					_serviceTrackerMapByNotificationTypeKey;
	private ServiceTrackerMap
		<String,
		 List
			 <ServiceTrackerCustomizerFactory.ServiceWrapper
				 <DefinitionTermContributor>>>
					_serviceTrackerMapByTermContributorKey;

}