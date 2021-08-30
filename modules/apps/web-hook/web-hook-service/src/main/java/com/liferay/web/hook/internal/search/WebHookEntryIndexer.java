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

package com.liferay.web.hook.internal.search;

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
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.web.hook.model.WebHookEntry;
import com.liferay.web.hook.service.WebHookEntryLocalService;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(immediate = true, service = Indexer.class)
public class WebHookEntryIndexer extends BaseIndexer<WebHookEntry> {

	public static final String CLASS_NAME = WebHookEntry.class.getName();

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
	protected void doDelete(WebHookEntry webHookEntry) throws Exception {
		deleteDocument(
			webHookEntry.getCompanyId(), webHookEntry.getWebHookEntryId());
	}

	@Override
	protected Document doGetDocument(WebHookEntry webHookEntry)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug("Indexing web hook entry " + webHookEntry);
		}

		Document document = getBaseModelDocument(CLASS_NAME, webHookEntry);

		Localization localization = getLocalization();

		String[] nameAvailableLanguageIds =
			localization.getAvailableLanguageIds(webHookEntry.getName());

		String nameDefaultLanguageId = LocalizationUtil.getDefaultLanguageId(
			webHookEntry.getName());

		for (String nameAvailableLanguageId : nameAvailableLanguageIds) {
			String name = webHookEntry.getName(nameAvailableLanguageId);

			if (nameDefaultLanguageId.equals(nameAvailableLanguageId)) {
				document.addText(Field.NAME, name);
			}

			document.addText(
				localization.getLocalizedName(
					Field.NAME, nameAvailableLanguageId),
				name);
		}

		document.addText(Field.URL, webHookEntry.getUrl());

		if (_log.isDebugEnabled()) {
			_log.debug("Document " + webHookEntry + " indexed successfully");
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
	protected void doReindex(String className, long classPK) throws Exception {
		doReindex(_webHookEntryLocalService.getWebHookEntry(classPK));
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexWebHookEntries(companyId);
	}

	@Override
	protected void doReindex(WebHookEntry webHookEntry) throws Exception {
		_indexWriterHelper.updateDocument(
			getSearchEngineId(), webHookEntry.getCompanyId(),
			getDocument(webHookEntry), isCommitImmediately());
	}

	protected Localization getLocalization() {

		// See LPS-72507

		if (_localization != null) {
			return _localization;
		}

		return LocalizationUtil.getLocalization();
	}

	protected void reindexWebHookEntries(long companyId)
		throws PortalException {

		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			_webHookEntryLocalService.getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			(WebHookEntry webHookEntry) -> {
				try {
					indexableActionableDynamicQuery.addDocuments(
						getDocument(webHookEntry));
				}
				catch (PortalException portalException) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to index web hook entry " +
								webHookEntry.getWebHookEntryId(),
							portalException);
					}
				}
			});
		indexableActionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		indexableActionableDynamicQuery.performActions();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WebHookEntryIndexer.class);

	@Reference
	private IndexWriterHelper _indexWriterHelper;

	private Localization _localization;

	@Reference
	private WebHookEntryLocalService _webHookEntryLocalService;

}