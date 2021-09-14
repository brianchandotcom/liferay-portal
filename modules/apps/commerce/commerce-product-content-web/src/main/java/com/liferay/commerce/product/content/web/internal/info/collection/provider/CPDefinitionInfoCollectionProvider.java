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

package com.liferay.commerce.product.content.web.internal.info.collection.provider;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.info.collection.provider.CollectionQuery;
import com.liferay.info.collection.provider.InfoCollectionProvider;
import com.liferay.info.filter.KeywordsInfoFilter;
import com.liferay.info.pagination.InfoPage;
import com.liferay.info.pagination.Pagination;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Collections;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alec Sloan
 */
@Component(
	enabled = false, immediate = true, service = InfoCollectionProvider.class
)
public class CPDefinitionInfoCollectionProvider
	implements InfoCollectionProvider<CPDefinition> {

	@Override
	public InfoPage<CPDefinition> getCollectionInfoPage(
		CollectionQuery collectionQuery) {

		try {
			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			BaseModelSearchResult<CPDefinition>
				cpDefinitionBaseModelSearchResult;

			long commerceChannelGroupId =
				_commerceChannelLocalService.
					getCommerceChannelGroupIdBySiteGroupId(
						serviceContext.getScopeGroupId());

			String keywords = null;

			Pagination pagination = collectionQuery.getPagination();

			Optional<KeywordsInfoFilter> keywordsInfoFilterOptional =
				collectionQuery.getInfoFilterOptional(KeywordsInfoFilter.class);

			if (keywordsInfoFilterOptional.isPresent()) {
				KeywordsInfoFilter keywordsInfoFilter =
					keywordsInfoFilterOptional.get();

				keywords = keywordsInfoFilter.getKeywords();
			}

			Sort sort = null;

			Optional<com.liferay.info.sort.Sort> sortOptional =
				collectionQuery.getSortOptional();

			if (sortOptional.isPresent()) {
				com.liferay.info.sort.Sort infoSort = sortOptional.get();

				sort = new Sort(
					infoSort.getFieldName(), Sort.LONG_TYPE,
					infoSort.isReverse());
			}

			if (commerceChannelGroupId != 0) {
				cpDefinitionBaseModelSearchResult =
					_cpDefinitionService.searchCPDefinitionsByChannelGroupId(
						serviceContext.getCompanyId(), commerceChannelGroupId,
						keywords, WorkflowConstants.STATUS_APPROVED,
						pagination.getStart(), pagination.getEnd(), sort);
			}
			else {
				cpDefinitionBaseModelSearchResult =
					_cpDefinitionService.searchCPDefinitions(
						serviceContext.getCompanyId(), keywords,
						WorkflowConstants.STATUS_APPROVED,
						pagination.getStart(), pagination.getEnd(), sort);
			}

			return InfoPage.of(
				cpDefinitionBaseModelSearchResult.getBaseModels(),
				collectionQuery.getPagination(),
				cpDefinitionBaseModelSearchResult.getLength());
		}
		catch (Exception exception) {
			_log.error("Unable to get cpDefinitions", exception);
		}

		return InfoPage.of(
			Collections.emptyList(), collectionQuery.getPagination(), 0);
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "products");
	}

	@Override
	public boolean isAvailable() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		if (Objects.equals(
				portletDisplay.getPortletName(), PortletKeys.ITEM_SELECTOR)) {

			return false;
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPDefinitionInfoCollectionProvider.class);

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CPDefinitionService _cpDefinitionService;

}