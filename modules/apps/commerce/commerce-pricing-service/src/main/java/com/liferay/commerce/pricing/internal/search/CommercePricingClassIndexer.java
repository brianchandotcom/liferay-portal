/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.pricing.internal.search;

import com.liferay.commerce.pricing.model.CommercePricingClass;
import com.liferay.commerce.pricing.service.CommercePricingClassLocalService;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.filter.BooleanFilter;

import jakarta.portlet.PortletRequest;
import jakarta.portlet.PortletResponse;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Alberti
 */
@Component(service = Indexer.class)
public class CommercePricingClassIndexer
	extends BaseIndexer<CommercePricingClass> {

	public static final String CLASS_NAME =
		CommercePricingClass.class.getName();

	public CommercePricingClassIndexer() {
		setDefaultSelectedFieldNames(
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.TITLE);
		setFilterSearch(true);
		setPermissionAware(true);
	}

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, BooleanFilter fullQueryBooleanFilter,
			SearchContext searchContext)
		throws Exception {

		addSearchLocalizedTerm(
			searchQuery, searchContext, Field.DESCRIPTION, false);
		addSearchTerm(searchQuery, searchContext, Field.ENTRY_CLASS_PK, false);
		addSearchTerm(searchQuery, searchContext, Field.TITLE, false);
		addSearchLocalizedTerm(searchQuery, searchContext, Field.TITLE, false);
		addSearchTerm(searchQuery, searchContext, Field.USER_NAME, false);
	}

	@Override
	protected void doDelete(CommercePricingClass commercePricingClass)
		throws Exception {

		deleteDocument(
			commercePricingClass.getCompanyId(),
			commercePricingClass.getCommercePricingClassId());
	}

	@Override
	protected Document doGetDocument(CommercePricingClass commercePricingClass)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug("Indexing pricing class " + commercePricingClass);
		}

		Document document = getBaseModelDocument(
			CLASS_NAME, commercePricingClass);

		document.addText(Field.TITLE, commercePricingClass.getTitle());

		return document;
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		Summary summary = createSummary(document, Field.TITLE, Field.TITLE);

		summary.setMaxContentLength(200);

		return summary;
	}

	@Override
	protected void doReindex(CommercePricingClass commercePricingClass)
		throws Exception {

		_indexWriterHelper.updateDocument(
			commercePricingClass.getCompanyId(),
			getDocument(commercePricingClass));
	}

	@Override
	protected void doReindex(long companyId) throws Exception {
		_reindexCommercePricingClasses(companyId);
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		doReindex(
			_commercePricingClassLocalService.getCommercePricingClass(classPK));
	}

	private void _reindexCommercePricingClasses(long companyId)
		throws Exception {

		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			_commercePricingClassLocalService.
				getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			this::safeGetDocument);

		indexableActionableDynamicQuery.performActions();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommercePricingClassIndexer.class);

	@Reference
	private CommercePricingClassLocalService _commercePricingClassLocalService;

	@Reference
	private IndexWriterHelper _indexWriterHelper;

}