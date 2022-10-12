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

import com.liferay.commerce.machine.learning.recommendation.ProductContentCommerceMLRecommendation;
import com.liferay.commerce.machine.learning.recommendation.ProductContentCommerceMLRecommendationManager;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.info.collection.provider.CollectionQuery;
import com.liferay.info.collection.provider.RelatedInfoItemCollectionProvider;
import com.liferay.info.pagination.InfoPage;
import com.liferay.info.pagination.Pagination;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(
	configurationPid = "com.liferay.commerce.machine.learning.internal.recommendation.configuration.CommerceMLRecommendationsCollectionProviderConfiguration",
	immediate = true, service = RelatedInfoItemCollectionProvider.class
)
public class
	ProductContentCommerceMLRecommendationRelatedInfoItemCollectionProvider
		extends BaseCommerceMLRecommendationCollectionProvider
		implements RelatedInfoItemCollectionProvider
			<CPDefinition, CPDefinition> {

	@Override
	public InfoPage<CPDefinition> getCollectionInfoPage(
		CollectionQuery collectionQuery) {

		Optional<Object> relatedItemOptional =
			collectionQuery.getRelatedItemObjectOptional();

		Object relatedItem = relatedItemOptional.orElse(null);

		Pagination pagination = collectionQuery.getPagination();

		if (!(relatedItem instanceof CPDefinition)) {
			return InfoPage.of(Collections.emptyList(), pagination, 0);
		}

		CPDefinition cpDefinition = (CPDefinition)relatedItem;

		try {
			List<ProductContentCommerceMLRecommendation>
				productContentCommerceMLRecommendations =
					_productContentCommerceMLRecommendationManager.
						getProductContentCommerceMLRecommendations(
							cpDefinition.getCompanyId(),
							cpDefinition.getCPDefinitionId());

			if (productContentCommerceMLRecommendations.isEmpty()) {
				return InfoPage.of(
					Collections.emptyList(), collectionQuery.getPagination(),
					0);
			}

			return InfoPage.of(
				TransformUtil.transform(
					ListUtil.subList(
						productContentCommerceMLRecommendations,
						pagination.getStart(), pagination.getEnd()),
					productContentCommerceMLRecommendation -> {
						try {
							return _cpDefinitionService.fetchCPDefinition(
								productContentCommerceMLRecommendation.
									getRecommendedEntryClassPK());
						}
						catch (PortalException portalException) {
							_log.error(portalException);
						}

						return null;
					}),
				collectionQuery.getPagination(),
				productContentCommerceMLRecommendations.size());
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}

		return InfoPage.of(Collections.emptyList(), pagination, 0);
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "content-based-product-recommendations");
	}

	@Override
	public boolean isAvailable() {
		return commerceMLRecommendationsCollectionProviderConfiguration.
			contentBasedProductRecommendationsCollectionProviderEnabled();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ProductContentCommerceMLRecommendationRelatedInfoItemCollectionProvider.
			class);

	@Reference(unbind = "-")
	private CPDefinitionService _cpDefinitionService;

	@Reference
	private Language _language;

	@Reference(unbind = "-")
	private ProductContentCommerceMLRecommendationManager
		_productContentCommerceMLRecommendationManager;

}