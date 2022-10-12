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
@Component(
	immediate = true,
	service = ProductInteractionCommerceMLRecommendationManager.class
)
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
				productInteractionCommerceMLRecommendation.getCompanyId()),
			_commerceMLIndexer.getDocumentType());
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