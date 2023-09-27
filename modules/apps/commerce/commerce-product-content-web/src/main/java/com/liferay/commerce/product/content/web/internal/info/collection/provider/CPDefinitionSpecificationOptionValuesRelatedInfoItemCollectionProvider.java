/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.content.web.internal.info.collection.provider;

import com.liferay.commerce.product.constants.CPOptionCategoryConstants;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue;
import com.liferay.commerce.product.model.CPOptionCategory;
import com.liferay.commerce.product.service.CPDefinitionSpecificationOptionValueLocalService;
import com.liferay.commerce.product.service.CPOptionCategoryLocalService;
import com.liferay.info.collection.provider.CollectionQuery;
import com.liferay.info.collection.provider.RelatedInfoItemCollectionProvider;
import com.liferay.info.pagination.InfoPage;
import com.liferay.info.pagination.Pagination;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = RelatedInfoItemCollectionProvider.class)
public class
	CPDefinitionSpecificationOptionValuesRelatedInfoItemCollectionProvider
		implements RelatedInfoItemCollectionProvider
			<CPDefinition, CPDefinitionSpecificationOptionValue> {

	@Override
	public InfoPage<CPDefinitionSpecificationOptionValue> getCollectionInfoPage(
		CollectionQuery collectionQuery) {

		Object relatedItem = collectionQuery.getRelatedItem();

		Pagination pagination = collectionQuery.getPagination();

		if (!(relatedItem instanceof CPDefinition)) {
			return InfoPage.of(Collections.emptyList(), pagination, 0);
		}

		CPDefinition cpDefinition = (CPDefinition)relatedItem;

		List<CPDefinitionSpecificationOptionValue>
			cpDefinitionSpecificationOptionValues =
				_getCPDefinitionSpecificationOptionValues(cpDefinition);

		if (cpDefinitionSpecificationOptionValues.isEmpty()) {
			return InfoPage.of(Collections.emptyList(), pagination, 0);
		}

		return InfoPage.of(
			ListUtil.subList(
				cpDefinitionSpecificationOptionValues, pagination.getStart(),
				pagination.getEnd()),
			pagination, cpDefinitionSpecificationOptionValues.size());
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "product-specifications");
	}

	private List<CPDefinitionSpecificationOptionValue>
		_getCPDefinitionSpecificationOptionValues(CPDefinition cpDefinition) {

		List<CPDefinitionSpecificationOptionValue>
			cpDefinitionSpecificationOptionValues = new ArrayList<>();

		cpDefinitionSpecificationOptionValues.addAll(
			_cpDefinitionSpecificationOptionValueLocalService.
				getCPDefinitionSpecificationOptionValues(
					cpDefinition.getCPDefinitionId(),
					CPOptionCategoryConstants.DEFAULT_CP_OPTION_CATEGORY_ID));

		List<CPOptionCategory> cpOptionCategories =
			_cpOptionCategoryLocalService.getCPOptionCategories(
				cpDefinition.getCompanyId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		for (CPOptionCategory cpOptionCategory : cpOptionCategories) {
			cpDefinitionSpecificationOptionValues.addAll(
				_cpDefinitionSpecificationOptionValueLocalService.
					getCPDefinitionSpecificationOptionValues(
						cpDefinition.getCPDefinitionId(),
						cpOptionCategory.getCPOptionCategoryId()));
		}

		return cpDefinitionSpecificationOptionValues;
	}

	@Reference
	private CPDefinitionSpecificationOptionValueLocalService
		_cpDefinitionSpecificationOptionValueLocalService;

	@Reference
	private CPOptionCategoryLocalService _cpOptionCategoryLocalService;

	@Reference
	private Language _language;

}