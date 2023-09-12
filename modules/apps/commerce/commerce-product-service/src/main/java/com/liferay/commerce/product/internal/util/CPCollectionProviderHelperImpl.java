/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		return getCPDefinitionOptionValueRels(
			0, 0, cpDefinitionOptionRel, keywords, pagination);
	}

	@Override
	public List<CPDefinitionOptionValueRel> getCPDefinitionOptionValueRels(
		long companyId, long groupId,
		CPDefinitionOptionRel cpDefinitionOptionRel, String keywords,
		Pagination pagination) {

		InfoPage<CPDefinitionOptionValueRel>
			cpDefinitionOptionValueRelInfoPage =
				_getCPDefinitionOptionValueRelInfoPage(
					companyId, groupId, cpDefinitionOptionRel, keywords,
					pagination);

		if (cpDefinitionOptionValueRelInfoPage != null) {
			return (List<CPDefinitionOptionValueRel>)
				cpDefinitionOptionValueRelInfoPage.getPageItems();
		}

		return Collections.emptyList();
	}

	@Override
	public int getCPDefinitionOptionValueRelsCount(
		CPDefinitionOptionRel cpDefinitionOptionRel, String keywords) {

		return getCPDefinitionOptionValueRelsCount(
			0, 0, cpDefinitionOptionRel, keywords);
	}

	@Override
	public int getCPDefinitionOptionValueRelsCount(
		long companyId, long groupId,
		CPDefinitionOptionRel cpDefinitionOptionRel, String keywords) {

		InfoPage<CPDefinitionOptionValueRel>
			cpDefinitionOptionValueRelInfoPage =
				_getCPDefinitionOptionValueRelInfoPage(
					companyId, groupId, cpDefinitionOptionRel, keywords, null);

		if (cpDefinitionOptionValueRelInfoPage != null) {
			return cpDefinitionOptionValueRelInfoPage.getTotalCount();
		}

		return 0;
	}

	private InfoPage<CPDefinitionOptionValueRel>
		_getCPDefinitionOptionValueRelInfoPage(
			long companyId, long groupId,
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
					"categoryIds",
					() -> {
						UnicodeProperties typeSettingsUnicodeProperties =
							cpDefinitionOptionRel.
								getTypeSettingsUnicodeProperties();

						return StringUtil.split(
							typeSettingsUnicodeProperties.getProperty(
								"categoryIds", StringPool.BLANK));
					}
				).put(
					"companyIds", new String[] {String.valueOf(companyId)}
				).put(
					"groupIds", new String[] {String.valueOf(groupId)}
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