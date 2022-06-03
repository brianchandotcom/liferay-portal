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

import com.liferay.asset.kernel.model.AssetCategoryConstants;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.model.AssetVocabularyConstants;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.content.dashboard.web.internal.constants.ContentDashboardConstants;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapperFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.reflect.GenericUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portlet.asset.util.AssetVocabularySettingsHelper;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

	public Collection<Long> getClassIds() {
		Collection<String> classNames = getClassNames();

		Stream<String> stream = classNames.stream();

		return stream.map(
			_classNameLocalService::getClassNameId
		).collect(
			Collectors.toSet()
		);
	}

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

	private void _addClassNameIdToDefaultAssetVocabularyIfNotExist(
		long classNameId, String defaultAssetVocabularyName) {

		try {
			_companyLocalService.forEachCompany(
				company -> {
					AssetVocabulary assetVocabulary =
						_assetVocabularyLocalService.fetchGroupVocabulary(
							company.getGroupId(),
							StringUtil.toLowerCase(
								GetterUtil.getString(
									defaultAssetVocabularyName)));

					if (assetVocabulary == null) {
						_addDefaultAssetVocabulary(
							company, classNameId, defaultAssetVocabularyName,
							AssetVocabularyConstants.VISIBILITY_TYPE_INTERNAL);

						return;
					}

					AssetVocabularySettingsHelper
						assetVocabularySettingsHelper =
							new AssetVocabularySettingsHelper();

					long[] selectedClassNameIds =
						assetVocabulary.getSelectedClassNameIds();

					if (!ArrayUtil.contains(
							selectedClassNameIds, classNameId)) {

						long[] classNameIds = ArrayUtil.append(
							assetVocabulary.getSelectedClassNameIds(),
							classNameId);
						long[] classTypePKs = ArrayUtil.append(
							assetVocabulary.getSelectedClassTypePKs(),
							AssetCategoryConstants.ALL_CLASS_TYPE_PK);

						boolean[] requireds =
							new boolean[selectedClassNameIds.length + 1];

						for (int i = 0; i < requireds.length; i++) {
							requireds[i] = false;
						}

						assetVocabularySettingsHelper.
							setClassNameIdsAndClassTypePKs(
								classNameIds, classTypePKs, requireds);
					}

					_assetVocabularyLocalService.updateVocabulary(
						assetVocabulary.getVocabularyId(),
						assetVocabulary.getTitleMap(),
						assetVocabulary.getDescriptionMap(),
						assetVocabularySettingsHelper.toString(),
						AssetVocabularyConstants.VISIBILITY_TYPE_INTERNAL);
				});
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	private void _addDefaultAssetVocabulary(
			Company company, long classNameId,
			String defaultAssetVocabularyName, int visibilityType)
		throws Exception {

		User defaultUser = company.getDefaultUser();

		Map<Locale, String> titleMap = new HashMap<>();

		for (Locale locale :
				_language.getCompanyAvailableLocales(company.getCompanyId())) {

			titleMap.put(
				locale, LanguageUtil.get(locale, defaultAssetVocabularyName));
		}

		AssetVocabularySettingsHelper assetVocabularySettingsHelper =
			new AssetVocabularySettingsHelper();

		assetVocabularySettingsHelper.setClassNameIdsAndClassTypePKs(
			new long[] {classNameId},
			new long[] {AssetCategoryConstants.ALL_CLASS_TYPE_PK},
			new boolean[] {false});

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		_assetVocabularyLocalService.addVocabulary(
			null, defaultUser.getUserId(), company.getGroupId(),
			defaultAssetVocabularyName, StringPool.BLANK, titleMap,
			Collections.emptyMap(), assetVocabularySettingsHelper.toString(),
			visibilityType, serviceContext);
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
	private Language _language;

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
				GenericUtil.getGenericClassName(contentDashboardItemFactory));

			for (ContentDashboardConstants.DefaultInternalAssetVocabularyName
					defaultInternalVocabularyName :
						ContentDashboardConstants.
							DefaultInternalAssetVocabularyName.values()) {

				_addClassNameIdToDefaultAssetVocabularyIfNotExist(
					classNameId, defaultInternalVocabularyName.toString());
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