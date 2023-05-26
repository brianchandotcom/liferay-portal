/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.machine.learning.internal.recommendation.info.collection.provider;

import com.liferay.account.model.AccountEntry;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.machine.learning.recommendation.UserCommerceMLRecommendation;
import com.liferay.commerce.machine.learning.recommendation.UserCommerceMLRecommendationManager;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.info.collection.provider.CollectionQuery;
import com.liferay.info.collection.provider.InfoCollectionProvider;
import com.liferay.info.pagination.InfoPage;
import com.liferay.info.pagination.Pagination;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Joao Victor Alves
 */
@Component(
	configurationPid = "com.liferay.commerce.machine.learning.internal.recommendation.configuration.CommerceMLRecommendationsCollectionProviderConfiguration",
	service = InfoCollectionProvider.class
)
public class UserCommerceMLRecommendationInfoItemCollectionProvider
	extends BaseCommerceMLRecommendationCollectionProvider
	implements InfoCollectionProvider<CPDefinition> {

	@Override
	public InfoPage<CPDefinition> getCollectionInfoPage(
		CollectionQuery collectionQuery) {

		Object relatedItem = collectionQuery.getRelatedItem();

		Pagination pagination = collectionQuery.getPagination();

		try {
			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			HttpServletRequest httpServletRequest = serviceContext.getRequest();

			CommerceContext commerceContext =
				(CommerceContext)httpServletRequest.getAttribute(
					CommerceWebKeys.COMMERCE_CONTEXT);

			if (commerceContext == null) {
				return InfoPage.of(
					Collections.emptyList(), collectionQuery.getPagination(),
					0);
			}

			AccountEntry accountEntry = commerceContext.getAccountEntry();

			if (accountEntry == null) {
				return InfoPage.of(
					Collections.emptyList(), collectionQuery.getPagination(),
					0);
			}

			long[] categoryIds = null;

			if (relatedItem != null) {
				CPDefinition cpDefinition = (CPDefinition)relatedItem;

				AssetEntry assetEntry = assetEntryLocalService.getEntry(
					CPDefinition.class.getName(),
					cpDefinition.getCPDefinitionId());

				categoryIds = assetEntry.getCategoryIds();
			}

			List<UserCommerceMLRecommendation> userCommerceMLRecommendations =
				userCommerceMLRecommendationManager.
					getUserCommerceMLRecommendations(
						accountEntry.getCompanyId(),
						accountEntry.getAccountEntryId(), categoryIds);

			if (userCommerceMLRecommendations.isEmpty()) {
				return InfoPage.of(
					Collections.emptyList(), collectionQuery.getPagination(),
					0);
			}

			return InfoPage.of(
				TransformUtil.transform(
					ListUtil.subList(
						userCommerceMLRecommendations, pagination.getStart(),
						pagination.getEnd()),
					userCommerceMLRecommendation -> {
						try {
							return cpDefinitionService.fetchCPDefinition(
								userCommerceMLRecommendation.
									getRecommendedEntryClassPK());
						}
						catch (PortalException portalException) {
							_log.error(portalException);
						}

						return null;
					}),
				collectionQuery.getPagination(),
				userCommerceMLRecommendations.size());
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}

		return InfoPage.of(Collections.emptyList(), pagination, 0);
	}

	@Override
	public String getLabel(Locale locale) {
		return language.get(locale, "user-personalized-recommendations");
	}

	@Reference
	protected AssetEntryLocalService assetEntryLocalService;

	@Reference(unbind = "-")
	protected CPDefinitionService cpDefinitionService;

	@Reference
	protected Language language;

	@Reference(unbind = "-")
	protected UserCommerceMLRecommendationManager
		userCommerceMLRecommendationManager;

	private static final Log _log = LogFactoryUtil.getLog(
		UserCommerceMLRecommendationInfoItemCollectionProvider.class);

}