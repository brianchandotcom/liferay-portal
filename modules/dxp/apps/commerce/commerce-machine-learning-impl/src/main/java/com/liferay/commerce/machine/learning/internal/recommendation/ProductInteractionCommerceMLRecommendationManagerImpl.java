/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.machine.learning.internal.recommendation;

import com.liferay.commerce.machine.learning.internal.recommendation.constants.CommerceMLRecommendationField;
import com.liferay.commerce.machine.learning.internal.search.api.CommerceMLIndexer;
import com.liferay.commerce.machine.learning.recommendation.ProductInteractionCommerceMLRecommendation;
import com.liferay.commerce.machine.learning.recommendation.ProductInteractionCommerceMLRecommendationManager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(service = ProductInteractionCommerceMLRecommendationManager.class)
public class ProductInteractionCommerceMLRecommendationManagerImpl
	extends BaseCommerceMLRecommendationServiceImpl
		<ProductInteractionCommerceMLRecommendation>
	implements ProductInteractionCommerceMLRecommendationManager {

	@Override
	public ProductInteractionCommerceMLRecommendation
			addProductInteractionCommerceMLRecommendation(
				ProductInteractionCommerceMLRecommendation
					productInteractionCommerceMLRecommendation)
		throws PortalException {

		return addCommerceMLRecommendation(
			productInteractionCommerceMLRecommendation,
			_commerceMLIndexer.getIndexName(
				productInteractionCommerceMLRecommendation.getCompanyId()));
	}

	@Override
	public ProductInteractionCommerceMLRecommendation create() {
		return new ProductInteractionCommerceMLRecommendationImpl();
	}

	@Override
	public List<ProductInteractionCommerceMLRecommendation>
			getProductInteractionCommerceMLRecommendations(
				long companyId, long cpDefinition)
		throws PortalException {

		SearchSearchRequest searchSearchRequest = getSearchSearchRequest(
			_commerceMLIndexer.getIndexName(companyId), companyId,
			cpDefinition);

		Sort sort = SortFactoryUtil.create(
			CommerceMLRecommendationField.RANK, Sort.INT_TYPE, false);

		searchSearchRequest.setSorts(new Sort[] {sort});

		return getSearchResults(searchSearchRequest);
	}

	@Override
	protected Document toDocument(
		ProductInteractionCommerceMLRecommendation model) {

		Document document = getDocument(model);

		document.addKeyword(
			Field.UID,
			String.valueOf(
				getHash(
					model.getEntryClassPK(),
					model.getRecommendedEntryClassPK())));
		document.addNumber(CommerceMLRecommendationField.RANK, model.getRank());
		document.addNumber(Field.ENTRY_CLASS_PK, model.getEntryClassPK());

		return document;
	}

	@Override
	protected ProductInteractionCommerceMLRecommendation toModel(
		Document document) {

		ProductInteractionCommerceMLRecommendation
			productInteractionCommerceMLRecommendationModel =
				getCommerceMLRecommendation(
					new ProductInteractionCommerceMLRecommendationImpl(),
					document);

		productInteractionCommerceMLRecommendationModel.setEntryClassPK(
			GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)));
		productInteractionCommerceMLRecommendationModel.setRank(
			GetterUtil.getInteger(
				document.get(CommerceMLRecommendationField.RANK)));

		return productInteractionCommerceMLRecommendationModel;
	}

	@Reference(
		target = "(component.name=com.liferay.commerce.machine.learning.internal.recommendation.search.index.ProductInteractionCommerceMLRecommendationIndexer)"
	)
	private CommerceMLIndexer _commerceMLIndexer;

}