/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.product.internal.util;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.util.CPCollectionProviderHelper;
import com.liferay.info.collection.provider.CollectionQuery;
import com.liferay.info.collection.provider.ConfigurableInfoCollectionProvider;
import com.liferay.info.collection.provider.RelatedInfoItemCollectionProvider;
import com.liferay.info.filter.InfoFilter;
import com.liferay.info.filter.KeywordsInfoFilter;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.pagination.InfoPage;
import com.liferay.info.pagination.Pagination;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Danny Situ
 */
@Component(service = CPCollectionProviderHelper.class)
public class CPCollectionProviderHelperImpl
	implements CPCollectionProviderHelper {

	@Override
	public List<CPDefinitionOptionValueRel> getCPDefinitionOptionValueRels(
		CPDefinitionOptionRel cpDefinitionOptionRel, String keywords,
		Pagination pagination) {

		InfoPage<CPDefinitionOptionValueRel>
			cpDefinitionOptionValueRelInfoPage =
				_getCPDefinitionOptionValueRelInfoPage(
					cpDefinitionOptionRel, keywords, pagination);

		if (cpDefinitionOptionValueRelInfoPage != null) {
			return (List<CPDefinitionOptionValueRel>)
				cpDefinitionOptionValueRelInfoPage.getPageItems();
		}

		return Collections.emptyList();
	}

	@Override
	public int getCPDefinitionOptionValueRelsCount(
		CPDefinitionOptionRel cpDefinitionOptionRel, String keywords) {

		InfoPage<CPDefinitionOptionValueRel>
			cpDefinitionOptionValueRelInfoPage =
				_getCPDefinitionOptionValueRelInfoPage(
					cpDefinitionOptionRel, keywords, null);

		if (cpDefinitionOptionValueRelInfoPage != null) {
			return cpDefinitionOptionValueRelInfoPage.getTotalCount();
		}

		return 0;
	}

	private InfoPage<CPDefinitionOptionValueRel>
		_getCPDefinitionOptionValueRelInfoPage(
			CPDefinitionOptionRel cpDefinitionOptionRel, String keywords,
			Pagination pagination) {

		ConfigurableInfoCollectionProvider<?>
			configurableInfoCollectionProvider =
				(ConfigurableInfoCollectionProvider<?>)
					_infoItemServiceRegistry.getInfoItemService(
						RelatedInfoItemCollectionProvider.class,
						cpDefinitionOptionRel.getInfoItemServiceKey());

		if (configurableInfoCollectionProvider != null) {
			CollectionQuery collectionQuery = new CollectionQuery();

			CPDefinition cpDefinition =
				_cpDefinitionLocalService.fetchCPDefinition(
					cpDefinitionOptionRel.getCPDefinitionId());

			collectionQuery.setConfiguration(
				HashMapBuilder.put(
					"category",
					() -> {
						UnicodeProperties typeSettingsUnicodeProperties =
							cpDefinitionOptionRel.
								getTypeSettingsUnicodeProperties();

						return StringUtil.split(
							typeSettingsUnicodeProperties.getProperty(
								"categoryIds", StringPool.BLANK));
					}
				).build());

			if (Validator.isNotNull(keywords)) {
				collectionQuery.setInfoFilters(
					HashMapBuilder.<String, InfoFilter>put(
						KeywordsInfoFilter.class.getName(),
						() -> {
							KeywordsInfoFilter keywordsInfoFilter =
								new KeywordsInfoFilter();

							keywordsInfoFilter.setKeywords(keywords);

							return keywordsInfoFilter;
						}
					).build());
			}

			if (pagination == null) {
				collectionQuery.setPagination(
					Pagination.of(QueryUtil.ALL_POS, QueryUtil.ALL_POS));
			}
			else {
				collectionQuery.setPagination(pagination);
			}

			collectionQuery.setRelatedItemObject(cpDefinition);

			return (InfoPage<CPDefinitionOptionValueRel>)
				configurableInfoCollectionProvider.getCollectionInfoPage(
					collectionQuery);
		}

		return null;
	}

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Reference
	private InfoItemServiceRegistry _infoItemServiceRegistry;

}