/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.machine.learning.internal.recommendation.info.collection.provider;

import com.liferay.analytics.machine.learning.content.UserContentRecommendationManager;
import com.liferay.analytics.settings.rest.manager.AnalyticsSettingsManager;
import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.util.comparator.AssetRendererFactoryTypeNameComparator;
import com.liferay.info.collection.provider.BetaInfoCollectionProvider;
import com.liferay.info.collection.provider.CollectionQuery;
import com.liferay.info.collection.provider.ConfigurableInfoCollectionProvider;
import com.liferay.info.collection.provider.FilteredInfoCollectionProvider;
import com.liferay.info.collection.provider.InfoCollectionProvider;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.type.MultiselectInfoFieldType;
import com.liferay.info.field.type.OptionInfoFieldType;
import com.liferay.info.filter.CategoriesInfoFilter;
import com.liferay.info.filter.InfoFilter;
import com.liferay.info.form.InfoForm;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.info.localized.bundle.ModelResourceLocalizedValue;
import com.liferay.info.pagination.InfoPage;
import com.liferay.info.pagination.Pagination;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManager;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(service = InfoCollectionProvider.class)
public class UserContentRecommendationInfoItemCollectionProvider
	implements BetaInfoCollectionProvider<AssetEntry>,
			   ConfigurableInfoCollectionProvider<AssetEntry>,
			   FilteredInfoCollectionProvider<AssetEntry> {

	@Override
	public InfoPage<AssetEntry> getCollectionInfoPage(
		CollectionQuery collectionQuery) {

		Pagination pagination = collectionQuery.getPagination();

		try {
			long[] categoryIds = null;

			CategoriesInfoFilter categoriesInfoFilter =
				collectionQuery.getInfoFilter(CategoriesInfoFilter.class);

			if (categoriesInfoFilter != null) {
				categoryIds = ArrayUtil.append(
					categoriesInfoFilter.getCategoryIds());

				categoryIds = ArrayUtil.unique(categoryIds);
			}

			long[] classNameIds = _getClassNameIds(collectionQuery);

			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			long count =
				_userContentRecommendationManager.
					getUserContentRecommendationsCount(
						categoryIds, classNameIds,
						serviceContext.getCompanyId(),
						serviceContext.getUserId());

			if (count <= 0) {
				return InfoPage.of(
					Collections.emptyList(), collectionQuery.getPagination(),
					0);
			}

			return InfoPage.of(
				TransformUtil.transform(
					_userContentRecommendationManager.
						getUserContentRecommendations(
							categoryIds, classNameIds,
							serviceContext.getCompanyId(),
							serviceContext.getUserId(), pagination.getStart(),
							pagination.getEnd()),
					userContentRecommendation ->
						_assetEntryLocalService.fetchEntry(
							userContentRecommendation.getEntryClassPK())),
				collectionQuery.getPagination(), (int)count);
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}

		return InfoPage.of(Collections.emptyList(), pagination, 0);
	}

	@Override
	public InfoForm getConfigurationInfoForm() {
		return InfoForm.builder(
		).infoFieldSetEntry(
			_getItemTypesInfoField()
		).build();
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(
			locale, "user's-personalized-content-recommendations");
	}

	@Override
	public List<InfoFilter> getSupportedInfoFilters() {
		return Collections.singletonList(new CategoriesInfoFilter());
	}

	@Override
	public boolean isAvailable() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		try {
			if (_featureFlagManager.isEnabled("LRAC-14771") &&
				_analyticsSettingsManager.isAnalyticsEnabled(
					serviceContext.getCompanyId())) {

				return true;
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return false;
	}

	private long[] _getClassNameIds(CollectionQuery collectionQuery) {
		Map<String, String[]> configuration =
			collectionQuery.getConfiguration();

		if (MapUtil.isNotEmpty(configuration) &&
			ArrayUtil.isNotEmpty(configuration.get("item_types"))) {

			List<Long> classNameIds = new ArrayList<>();

			String[] itemTypes = configuration.get("item_types");

			for (String itemType : itemTypes) {
				if (Validator.isNotNull(itemType)) {
					classNameIds.add(_portal.getClassNameId(itemType));
				}
			}

			if (ListUtil.isNotEmpty(classNameIds)) {
				return ArrayUtil.toArray(classNameIds.toArray(new Long[0]));
			}
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		return AssetRendererFactoryRegistryUtil.getIndexableClassNameIds(
			serviceContext.getCompanyId(), true);
	}

	private InfoField<?> _getItemTypesInfoField() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		List<AssetRendererFactory<?>> assetRendererFactories = ListUtil.filter(
			AssetRendererFactoryRegistryUtil.getAssetRendererFactories(
				serviceContext.getCompanyId(), true),
			assetRendererFactory -> {
				if (!assetRendererFactory.isCategorizable()) {
					return false;
				}

				Indexer<?> indexer = IndexerRegistryUtil.getIndexer(
					assetRendererFactory.getClassName());

				if (indexer == null) {
					return false;
				}

				return true;
			});

		assetRendererFactories.sort(
			new AssetRendererFactoryTypeNameComparator(
				serviceContext.getLocale()));

		return InfoField.builder(
		).infoFieldType(
			MultiselectInfoFieldType.INSTANCE
		).namespace(
			StringPool.BLANK
		).name(
			"item_types"
		).attribute(
			MultiselectInfoFieldType.OPTIONS,
			TransformUtil.transform(
				assetRendererFactories,
				assetRendererFactory -> new OptionInfoFieldType(
					new ModelResourceLocalizedValue(
						assetRendererFactory.getClassName()),
					assetRendererFactory.getClassName()))
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(getClass(), "item-type")
		).localizable(
			true
		).build();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UserContentRecommendationInfoItemCollectionProvider.class);

	@Reference
	private AnalyticsSettingsManager _analyticsSettingsManager;

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private FeatureFlagManager _featureFlagManager;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference
	private UserContentRecommendationManager _userContentRecommendationManager;

}