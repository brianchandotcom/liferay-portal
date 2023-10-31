/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.machine.learning.internal.recommendation.info.collection.provider;

import com.liferay.analytics.machine.learning.content.UserContentRecommendation;
import com.liferay.analytics.machine.learning.content.UserContentRecommendationManager;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.info.collection.provider.CollectionQuery;
import com.liferay.info.collection.provider.InfoCollectionProvider;
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
import com.liferay.portal.kernel.util.ListUtil;

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
	implements InfoCollectionProvider<AssetEntry> {

	@Override
	public InfoPage<AssetEntry> getCollectionInfoPage(
		CollectionQuery collectionQuery) {

		Pagination pagination = collectionQuery.getPagination();

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		try {
			List<UserContentRecommendation> userContentRecommendations =
				_userContentRecommendationManager.getUserContentRecommendations(
					null, serviceContext.getCompanyId(),
					serviceContext.getUserId());

			if (userContentRecommendations.isEmpty()) {
				return InfoPage.of(
					Collections.emptyList(), collectionQuery.getPagination(),
					0);
			}

			return InfoPage.of(
				TransformUtil.transform(
					ListUtil.subList(
						userContentRecommendations, pagination.getStart(),
						pagination.getEnd()),
					userContentRecommendation ->
						_assetEntryLocalService.fetchEntry(
							userContentRecommendation.getEntryClassPK())),
				collectionQuery.getPagination(),
				userContentRecommendations.size());
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