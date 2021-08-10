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

package com.liferay.custom.elements.internal.search;

import com.liferay.custom.elements.model.CustomElementsSource;
import com.liferay.custom.elements.service.CustomElementsSourceLocalService;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
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
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera Avellón
 */
@Component(immediate = true, service = Indexer.class)
public class CustomElementsSourceIndexer
	extends BaseIndexer<CustomElementsSource> {

	public static final String CLASS_NAME =
		CustomElementsSource.class.getName();

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, BooleanFilter fullQueryBooleanFilter,
			SearchContext searchContext)
		throws Exception {

		addSearchTerm(searchQuery, searchContext, Field.ENTRY_CLASS_PK, false);
		addSearchTerm(searchQuery, searchContext, Field.NAME, true);
		addSearchTerm(searchQuery, searchContext, Field.URL, true);
	}

	@Override
	protected void doDelete(CustomElementsSource customElementsSource)
		throws Exception {

		deleteDocument(
			customElementsSource.getCompanyId(),
			customElementsSource.getCustomElementsSourceId());
	}

	@Override
	protected Document doGetDocument(CustomElementsSource customElementsSource)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Indexing custom element source " + customElementsSource);
		}

		Document document = getBaseModelDocument(
			CLASS_NAME, customElementsSource);

		document.addText(Field.NAME, customElementsSource.getName());
		document.addText(Field.URL, customElementsSource.getURL());

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Document " + customElementsSource + " indexed successfully");
		}

		return document;
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		Summary summary = createSummary(document, Field.NAME, Field.URL);

		summary.setMaxContentLength(200);

		return summary;
	}

	@Override
	protected void doReindex(CustomElementsSource customElementsSource)
		throws Exception {

		_indexWriterHelper.updateDocument(
			getSearchEngineId(), customElementsSource.getCompanyId(),
			getDocument(customElementsSource), isCommitImmediately());
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		doReindex(
			_customElementsSourceLocalService.getCustomElementsSource(classPK));
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexRemoteAppEntries(companyId);
	}

	protected void reindexRemoteAppEntries(long companyId)
		throws PortalException {

		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			_customElementsSourceLocalService.
				getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			(CustomElementsSource customElementsSource) -> {
				try {
					indexableActionableDynamicQuery.addDocuments(
						getDocument(customElementsSource));
				}
				catch (PortalException portalException) {
					if (_log.isWarnEnabled()) {
						long customElementsSourceId =
							customElementsSource.getCustomElementsSourceId();

						_log.warn(
							"Unable to index custom elements source " +
								customElementsSourceId,
							portalException);
					}
				}
			});
		indexableActionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		indexableActionableDynamicQuery.performActions();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CustomElementsSourceIndexer.class);

	@Reference
	private CustomElementsSourceLocalService _customElementsSourceLocalService;

	@Reference
	private IndexWriterHelper _indexWriterHelper;

}