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

package com.liferay.commerce.vat.internal.search;

import com.liferay.commerce.vat.model.CommerceVatNumber;
import com.liferay.commerce.vat.service.CommerceVatNumberLocalService;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(immediate = true, service = Indexer.class)
public class CommerceVatNumberIndexer extends BaseIndexer<CommerceVatNumber> {

	public static final String CLASS_NAME = CommerceVatNumber.class.getName();

	public static final String FIELD_VAT_NUMBER = "vatNumber";

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, BooleanFilter fullQueryBooleanFilter,
			SearchContext searchContext)
		throws Exception {

		addSearchTerm(searchQuery, searchContext, FIELD_VAT_NUMBER, false);
	}

	@Override
	protected void doDelete(CommerceVatNumber commerceVatNumber)
		throws Exception {

		deleteDocument(
			commerceVatNumber.getCompanyId(),
			commerceVatNumber.getCommerceVatNumberId());
	}

	@Override
	protected Document doGetDocument(CommerceVatNumber commerceVatNumber)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug("Indexing vat number " + commerceVatNumber);
		}

		Document document = getBaseModelDocument(CLASS_NAME, commerceVatNumber);

		document.addText(FIELD_VAT_NUMBER, commerceVatNumber.getValue());

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Document " + commerceVatNumber + " indexed successfully");
		}

		return document;
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		Summary summary = createSummary(
			document, FIELD_VAT_NUMBER, FIELD_VAT_NUMBER);

		summary.setMaxContentLength(200);

		return summary;
	}

	@Override
	protected void doReindex(CommerceVatNumber commerceVatNumber)
		throws Exception {

		Document document = getDocument(commerceVatNumber);

		_indexWriterHelper.updateDocument(
			getSearchEngineId(), commerceVatNumber.getCompanyId(), document,
			isCommitImmediately());
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		CommerceVatNumber commerceVatNumber =
			_commerceVatNumberLocalService.getCommerceVatNumber(classPK);

		doReindex(commerceVatNumber);
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexCommerceVatNumbers(companyId);
	}

	protected void reindexCommerceVatNumbers(long companyId)
		throws PortalException {

		final IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			_commerceVatNumberLocalService.getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.
				PerformActionMethod<CommerceVatNumber>() {

				@Override
				public void performAction(CommerceVatNumber commerceVatNumber) {
					try {
						Document document = getDocument(commerceVatNumber);

						indexableActionableDynamicQuery.addDocuments(document);
					}
					catch (PortalException pe) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"Unable to index commerce vat number " +
									commerceVatNumber.getCommerceVatNumberId(),
								pe);
						}
					}
				}

			});
		indexableActionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		indexableActionableDynamicQuery.performActions();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceVatNumberIndexer.class);

	@Reference
	private CommerceVatNumberLocalService _commerceVatNumberLocalService;

	@Reference
	private IndexWriterHelper _indexWriterHelper;

}