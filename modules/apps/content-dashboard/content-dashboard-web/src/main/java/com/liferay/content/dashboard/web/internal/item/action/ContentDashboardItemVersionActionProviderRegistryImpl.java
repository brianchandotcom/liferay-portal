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

package com.liferay.content.dashboard.web.internal.item.action;

import com.liferay.content.dashboard.item.action.ContentDashboardItemVersionActionProviderRegistry;
import com.liferay.content.dashboard.item.action.provider.ContentDashboardItemVersionActionProvider;
import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceComparator;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapperFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.reflect.GenericUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.Collections;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Stefan Tanasie
 */
@Component(service = ContentDashboardItemVersionActionProviderRegistry.class)
public class ContentDashboardItemVersionActionProviderRegistryImpl
	implements ContentDashboardItemVersionActionProviderRegistry {

	@Override
	public List<ContentDashboardItemVersionActionProvider>
		getContentDashboardItemVersionActionProviders(String className) {

		List<ContentDashboardItemVersionActionProvider>
			contentDashboardItemVersionActionProviders =
				_serviceTrackerMap.getService(className);

		if (ListUtil.isEmpty(contentDashboardItemVersionActionProviders)) {
			return Collections.emptyList();
		}

		return contentDashboardItemVersionActionProviders;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, ContentDashboardItemVersionActionProvider.class,
			null,
			ServiceReferenceMapperFactory.create(
				bundleContext,
				(contentDashboardItemVersionAction, emitter) -> emitter.emit(
					GenericUtil.getGenericClassName(
						contentDashboardItemVersionAction))),
			new PropertyServiceReferenceComparator<>("service.ranking"));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap
		<String, List<ContentDashboardItemVersionActionProvider>>
			_serviceTrackerMap;

}