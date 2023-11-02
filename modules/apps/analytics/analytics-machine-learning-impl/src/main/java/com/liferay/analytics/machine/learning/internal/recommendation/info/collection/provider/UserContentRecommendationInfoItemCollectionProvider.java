/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.machine.learning.internal.recommendation.info.collection.provider;

import com.liferay.analytics.machine.learning.content.UserContentRecommendationManager;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.info.collection.provider.CollectionQuery;
import com.liferay.info.collection.provider.FilteredInfoCollectionProvider;
import com.liferay.info.collection.provider.InfoCollectionProvider;
import com.liferay.info.filter.CategoriesInfoFilter;
import com.liferay.info.filter.InfoFilter;
import com.liferay.info.pagination.InfoPage;
import com.liferay.info.pagination.Pagination;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManager;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(service = InfoCollectionProvider.class)
public class UserContentRecommendationInfoItemCollectionProvider
	implements FilteredInfoCollectionProvider<AssetEntry> {

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

			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			long count =
				_userContentRecommendationManager.
					getUserContentRecommendationsCount(
						categoryIds, serviceContext.getCompanyId(),
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
							categoryIds, serviceContext.getCompanyId(),
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
		return _featureFlagManager.isEnabled("LRAC-14771");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UserContentRecommendationInfoItemCollectionProvider.class);

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private FeatureFlagManager _featureFlagManager;

	@Reference
	private Language _language;

	@Reference
	private UserContentRecommendationManager _userContentRecommendationManager;

}