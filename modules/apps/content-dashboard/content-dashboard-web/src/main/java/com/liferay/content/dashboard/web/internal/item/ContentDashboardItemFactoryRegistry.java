/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.dashboard.web.internal.item;

import com.liferay.asset.kernel.model.AssetVocabularyConstants;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.content.dashboard.item.ContentDashboardItemFactory;
import com.liferay.content.dashboard.web.internal.constants.ContentDashboardConstants;
import com.liferay.content.dashboard.web.internal.util.AssetVocabularyUtil;
import com.liferay.info.search.InfoSearchClassMapperRegistry;
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
@Component(service = ContentDashboardItemFactoryRegistry.class)
public class ContentDashboardItemFactoryRegistry {

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
				new ContentDashboardItemFactoryServiceTrackerCustomizer(
					bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ContentDashboardItemFactoryRegistry.class);

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private InfoSearchClassMapperRegistry _infoSearchClassMapperRegistry;

	private volatile ServiceTrackerMap<String, ContentDashboardItemFactory<?>>
		_serviceTrackerMap;

	private class ContentDashboardItemFactoryServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<ContentDashboardItemFactory, ContentDashboardItemFactory> {

		public ContentDashboardItemFactoryServiceTrackerCustomizer(
			BundleContext bundleContext) {

			_bundleContext = bundleContext;
		}

		@Override
		public ContentDashboardItemFactory addingService(
			ServiceReference<ContentDashboardItemFactory> serviceReference) {

			ContentDashboardItemFactory contentDashboardItemFactory =
				_bundleContext.getService(serviceReference);

			long classNameId = _classNameLocalService.getClassNameId(
				_infoSearchClassMapperRegistry.getSearchClassName(
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