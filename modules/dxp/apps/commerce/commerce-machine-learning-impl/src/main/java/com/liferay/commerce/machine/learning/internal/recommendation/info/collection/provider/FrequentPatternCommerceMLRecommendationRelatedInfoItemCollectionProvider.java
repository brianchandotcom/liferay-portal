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

import com.liferay.commerce.machine.learning.recommendation.FrequentPatternCommerceMLRecommendation;
import com.liferay.commerce.machine.learning.recommendation.FrequentPatternCommerceMLRecommendationManager;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.info.collection.provider.CollectionQuery;
import com.liferay.info.collection.provider.RelatedInfoItemCollectionProvider;
import com.liferay.info.pagination.InfoPage;
import com.liferay.info.pagination.Pagination;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(
	enabled = false, immediate = true,
	service = RelatedInfoItemCollectionProvider.class
)
public class
	FrequentPatternCommerceMLRecommendationRelatedInfoItemCollectionProvider
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
			List<FrequentPatternCommerceMLRecommendation>
				frequentPatternCommerceMLRecommendations =
					_frequentPatternCommerceMLRecommendationManager.
						getFrequentPatternCommerceMLRecommendations(
							cpDefinition.getCompanyId(),
							new long[] {cpDefinition.getCPDefinitionId()});

			if (frequentPatternCommerceMLRecommendations.isEmpty()) {
				return InfoPage.of(
					Collections.emptyList(), collectionQuery.getPagination(),
					0);
			}

			List<FrequentPatternCommerceMLRecommendation>
				frequentPatternCommerceMLRecommendationList = ListUtil.subList(
					frequentPatternCommerceMLRecommendations,
					pagination.getStart(), pagination.getEnd());

			Stream<FrequentPatternCommerceMLRecommendation> stream =
				frequentPatternCommerceMLRecommendationList.stream();

			return InfoPage.of(
				stream.map(
					FrequentPatternCommerceMLRecommendation::
						getRecommendedEntryClassPK
				).map(
					cpDefinitionId -> {
						try {
							return _cpDefinitionService.fetchCPDefinition(
								cpDefinitionId);
						}
						catch (PortalException portalException) {
							_log.error(portalException, portalException);
						}

						return null;
					}
				).filter(
					Objects::nonNull
				).collect(
					Collectors.toList()
				),
				collectionQuery.getPagination(),
				frequentPatternCommerceMLRecommendations.size());
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);
		}

		return InfoPage.of(Collections.emptyList(), pagination, 0);
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "frequent-pattern-recommendations");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FrequentPatternCommerceMLRecommendationRelatedInfoItemCollectionProvider.class);

	@Reference(unbind = "-")
	private CPDefinitionService _cpDefinitionService;

	@Reference(unbind = "-")
	private FrequentPatternCommerceMLRecommendationManager
		_frequentPatternCommerceMLRecommendationManager;

}