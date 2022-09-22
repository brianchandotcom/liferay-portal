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

package com.liferay.content.dashboard.web.internal.item;

import com.liferay.asset.kernel.model.AssetVocabularyConstants;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.content.dashboard.item.ContentDashboardItemFactory;
import com.liferay.content.dashboard.web.internal.constants.ContentDashboardConstants;
import com.liferay.content.dashboard.web.internal.util.AssetVocabularyUtil;
import com.liferay.info.search.InfoSearchClassMapperTracker;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapperFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.reflect.GenericUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;

import java.util.Collection;
import java.util.Collections;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Cristina González
 */
@Component(service = ContentDashboardItemFactoryTracker.class)
public class ContentDashboardItemFactoryTracker {

	public Collection<String> getClassNames() {
		return Collections.unmodifiableCollection(_serviceTrackerMap.keySet());
	}

	public ContentDashboardItemFactory<?> getContentDashboardItemFactory(
		String className) {

		return _serviceTrackerMap.getService(className);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap =
			(ServiceTrackerMap)ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, ContentDashboardItemFactory.class, null,
				ServiceReferenceMapperFactory.create(
					bundleContext,
					(contentDashboardItemFactory, emitter) -> emitter.emit(
						GenericUtil.getGenericClassName(
							contentDashboardItemFactory))),
				new ContentDashboardItemFactoryTrackerCustomizer(
					bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ContentDashboardItemFactoryTracker.class);

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private InfoSearchClassMapperTracker _infoSearchClassMapperTracker;

	private volatile ServiceTrackerMap<String, ContentDashboardItemFactory<?>>
		_serviceTrackerMap;

	private class ContentDashboardItemFactoryTrackerCustomizer
		implements ServiceTrackerCustomizer
			<ContentDashboardItemFactory, ContentDashboardItemFactory> {

		public ContentDashboardItemFactoryTrackerCustomizer(
			BundleContext bundleContext) {

			_bundleContext = bundleContext;
		}

		@Override
		public ContentDashboardItemFactory addingService(
			ServiceReference<ContentDashboardItemFactory> serviceReference) {

			ContentDashboardItemFactory contentDashboardItemFactory =
				_bundleContext.getService(serviceReference);

			long classNameId = _classNameLocalService.getClassNameId(
				_infoSearchClassMapperTracker.getSearchClassName(
					GenericUtil.getGenericClassName(
						contentDashboardItemFactory)));

			for (ContentDashboardConstants.DefaultInternalAssetVocabularyName
					defaultInternalAssetVocabularyName :
						ContentDashboardConstants.
							DefaultInternalAssetVocabularyName.values()) {

				try {
					_companyLocalService.forEachCompany(
						company -> AssetVocabularyUtil.addAssetVocabulary(
							_assetVocabularyLocalService,
							Collections.singletonList(classNameId), company,
							defaultInternalAssetVocabularyName.toString(),
							AssetVocabularyConstants.VISIBILITY_TYPE_INTERNAL));
				}
				catch (Exception exception) {
					_log.error(exception);
				}
			}

			return contentDashboardItemFactory;
		}

		@Override
		public void modifiedService(
			ServiceReference<ContentDashboardItemFactory> serviceReference,
			ContentDashboardItemFactory contentDashboardItemFactory) {
		}

		@Override
		public void removedService(
			ServiceReference<ContentDashboardItemFactory> serviceReference,
			ContentDashboardItemFactory contentDashboardItemFactory) {
		}

		private final BundleContext _bundleContext;

	}

}