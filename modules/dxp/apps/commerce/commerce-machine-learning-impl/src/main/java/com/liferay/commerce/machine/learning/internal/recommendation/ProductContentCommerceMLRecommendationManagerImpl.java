/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.machine.learning.internal.recommendation;

import com.liferay.commerce.machine.learning.internal.recommendation.constants.CommerceMLRecommendationField;
import com.liferay.commerce.machine.learning.internal.search.api.CommerceMLIndexer;
import com.liferay.commerce.machine.learning.recommendation.ProductContentCommerceMLRecommendation;
import com.liferay.commerce.machine.learning.recommendation.ProductContentCommerceMLRecommendationManager;
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
@Component(service = ProductContentCommerceMLRecommendationManager.class)
public class ProductContentCommerceMLRecommendationManagerImpl
	extends BaseCommerceMLRecommendationServiceImpl
		<ProductContentCommerceMLRecommendation>
	implements ProductContentCommerceMLRecommendationManager {

	@Override
	public ProductContentCommerceMLRecommendation
			addProductContentCommerceMLRecommendation(
				ProductContentCommerceMLRecommendation
					productContentCommerceMLRecommendation)
		throws PortalException {

		return addCommerceMLRecommendation(
			productContentCommerceMLRecommendation,
			_commerceMLIndexer.getIndexName(
				productContentCommerceMLRecommendation.getCompanyId()));
	}

	@Override
	public ProductContentCommerceMLRecommendation create() {
		return new ProductContentCommerceMLRecommendationImpl();
	}

	@Override
	public List<ProductContentCommerceMLRecommendation>
			getProductContentCommerceMLRecommendations(
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
		ProductContentCommerceMLRecommendation model) {

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
	protected ProductContentCommerceMLRecommendation toModel(
		Document document) {

		ProductContentCommerceMLRecommendation
			productContentCommerceMLRecommendation =
				getCommerceMLRecommendation(
					new ProductContentCommerceMLRecommendationImpl(), document);

		productContentCommerceMLRecommendation.setEntryClassPK(
			GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)));
		productContentCommerceMLRecommendation.setRank(
			GetterUtil.getInteger(
				document.get(CommerceMLRecommendationField.RANK)));

		return productContentCommerceMLRecommendation;
	}

	@Reference(
		target = "(component.name=com.liferay.commerce.machine.learning.internal.recommendation.search.index.ProductContentCommerceMLRecommendationIndexer)"
	)
	private CommerceMLIndexer _commerceMLIndexer;

}