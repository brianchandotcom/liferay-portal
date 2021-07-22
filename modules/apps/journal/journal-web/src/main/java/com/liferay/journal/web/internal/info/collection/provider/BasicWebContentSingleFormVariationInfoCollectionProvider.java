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

package com.liferay.journal.web.internal.info.collection.provider;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.info.collection.provider.CollectionQuery;
import com.liferay.info.collection.provider.InfoCollectionProvider;
import com.liferay.info.collection.provider.SingleFormVariationInfoCollectionProvider;
import com.liferay.info.pagination.InfoPage;
import com.liferay.info.pagination.Pagination;
import com.liferay.info.sort.Sort;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.journal.web.internal.util.JournalPortletUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	enabled = false, immediate = true, service = InfoCollectionProvider.class
)
public class BasicWebContentSingleFormVariationInfoCollectionProvider
	implements SingleFormVariationInfoCollectionProvider<JournalArticle> {

	@Override
	public InfoPage<JournalArticle> getCollectionInfoPage(
		CollectionQuery collectionQuery) {

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Pagination pagination = collectionQuery.getPagination();

		OrderByComparator<JournalArticle> orderByComparator = null;

		Optional<Sort> sortOptional = collectionQuery.getSortOptional();

		if (sortOptional.isPresent()) {
			String orderByType = "ASC";

			Sort sort = sortOptional.get();

			if (sort.isReverse()) {
				orderByType = "DESC";
			}

			orderByComparator = JournalPortletUtil.getArticleOrderByComparator(
				sort.getFieldName(), orderByType);
		}

		return InfoPage.of(
			_journalArticleService.getArticlesByStructureId(
				serviceContext.getScopeGroupId(), "BASIC-WEB-CONTENT",
				WorkflowConstants.STATUS_APPROVED, pagination.getStart(),
				pagination.getEnd(), orderByComparator),
			pagination,
			_journalArticleService.getArticlesCountByStructureId(
				serviceContext.getScopeGroupId(), "BASIC-WEB-CONTENT",
				WorkflowConstants.STATUS_APPROVED));
	}

	@Override
	public String getFormVariationKey() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		DDMStructure ddmStructure = _ddmStructureLocalService.fetchStructure(
			serviceContext.getScopeGroupId(),
			_portal.getClassNameId(JournalArticle.class.getName()),
			"BASIC-WEB-CONTENT", true);

		return String.valueOf(ddmStructure.getStructureId());
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(locale);

		return LanguageUtil.get(resourceBundle, "basic-web-content");
	}

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private JournalArticleService _journalArticleService;

	@Reference
	private Portal _portal;

	@Reference(target = "(bundle.symbolic.name=com.liferay.journal.web)")
	private ResourceBundleLoader _resourceBundleLoader;

}