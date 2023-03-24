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

import com.liferay.content.dashboard.item.action.ContentDashboardItemAction;
import com.liferay.content.dashboard.item.action.ContentDashboardItemActionProviderRegistry;
import com.liferay.content.dashboard.item.action.provider.ContentDashboardItemActionProvider;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapperFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.reflect.GenericUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;
import java.util.Objects;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Cristina González
 */
@Component(service = ContentDashboardItemActionProviderRegistry.class)
public class ContentDashboardItemActionProviderRegistryImpl
	implements ContentDashboardItemActionProviderRegistry {

	@Override
	public ContentDashboardItemActionProvider
		getContentDashboardItemActionProvider(
			String className, ContentDashboardItemAction.Type type) {

		List<ContentDashboardItemActionProvider>
			contentDashboardItemActionProviders = _serviceTrackerMap.getService(
				className);

		if (ListUtil.isEmpty(contentDashboardItemActionProviders)) {
			return null;
		}

		for (ContentDashboardItemActionProvider
				contentDashboardItemActionProvider :
					contentDashboardItemActionProviders) {

			if (Objects.equals(
					type, contentDashboardItemActionProvider.getType())) {

				return contentDashboardItemActionProvider;
			}
		}

		return null;
	}

	@Override
	public List<ContentDashboardItemActionProvider>
		getContentDashboardItemActionProviders(
			String className, ContentDashboardItemAction.Type... types) {

		return TransformUtil.transformToList(
			types,
			type -> getContentDashboardItemActionProvider(className, type));
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, ContentDashboardItemActionProvider.class, null,
			ServiceReferenceMapperFactory.create(
				bundleContext,
				(contentDashboardItemActionProvider, emitter) -> emitter.emit(
					GenericUtil.getGenericClassName(
						contentDashboardItemActionProvider))));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap<String, List<ContentDashboardItemActionProvider>>
		_serviceTrackerMap;

}