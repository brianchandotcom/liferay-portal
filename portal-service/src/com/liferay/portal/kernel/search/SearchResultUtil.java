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

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author Eudaldo Alonso
 */
public class SearchResultUtil {

	public static List<SearchResult> getSearchResults(
		Hits hits, Locale locale) {

		return getSearchResults(hits, locale, null, null);
	}

	public static List<SearchResult> getSearchResults(
		Hits hits, Locale locale, PortletRequest portletRequest,
		PortletResponse portletResponse) {

		List<SearchResult> searchResults = new ArrayList<>();

		for (Document document : hits.getDocs()) {
			String entryClassName = GetterUtil.getString(
				document.get(Field.ENTRY_CLASS_NAME));
			long entryClassPK = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			try {
				String className = entryClassName;
				long classPK = entryClassPK;

				FileEntry fileEntry = null;
				MBMessage mbMessage = null;

				if (entryClassName.equals(DLFileEntry.class.getName()) ||
					entryClassName.equals(MBMessage.class.getName())) {

					classPK = GetterUtil.getLong(document.get(Field.CLASS_PK));
					long classNameId = GetterUtil.getLong(
						document.get(Field.CLASS_NAME_ID));

					if ((classPK > 0) && (classNameId > 0)) {
						className = PortalUtil.getClassName(classNameId);

						if (entryClassName.equals(
								DLFileEntry.class.getName())) {

							fileEntry = DLAppLocalServiceUtil.getFileEntry(
								entryClassPK);
						}
						else if (entryClassName.equals(
									MBMessage.class.getName())) {

							mbMessage = MBMessageLocalServiceUtil.getMessage(
								entryClassPK);
						}
					}
					else {
						className = entryClassName;
						classPK = entryClassPK;
					}
				}

				SearchResult searchResult =
					SearchResultManagerUtil.createSearchResult(document);

				int index = searchResults.indexOf(searchResult);

				if (index < 0) {
					searchResults.add(searchResult);
				}
				else {
					searchResult = searchResults.get(index);
				}

				if (fileEntry != null) {
					Summary summary = getSummary(
						document, DLFileEntry.class.getName(),
						fileEntry.getFileEntryId(), locale, portletRequest,
						portletResponse);

					searchResult.addFileEntry(fileEntry, summary);
				}

				if (mbMessage != null) {
					searchResult.addMBMessage(mbMessage);
				}

				String version = document.get(Field.VERSION);

				if (Validator.isNotNull(version)) {
					searchResult.addVersion(version);
				}

				if ((mbMessage == null) && (fileEntry == null)) {
					Summary summary = getSummary(
						document, searchResult.getClassName(),
						searchResult.getClassPK(), locale, portletRequest,
						portletResponse);

					searchResult.setSummary(summary);
				}
				else {
					if (searchResult.getSummary() == null) {
						Summary summary = getSummary(
							searchResult.getClassName(),
							searchResult.getClassPK(), locale);

						searchResult.setSummary(summary);
					}
				}
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Search index is stale and contains entry {" +
							entryClassPK + "}");
				}
			}
		}

		return searchResults;
	}

	protected static Summary getSummary(
			Document document, String className, long classPK, Locale locale,
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws PortalException {

		Indexer indexer = IndexerRegistryUtil.getIndexer(className);

		if (indexer != null) {
			String snippet = document.get(Field.SNIPPET);

			return indexer.getSummary(
				document, snippet, portletRequest, portletResponse);
		}

		return getSummary(className, classPK, locale);
	}

	protected static Summary getSummary(
			String className, long classPK, Locale locale)
		throws PortalException {

		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				className);

		if (assetRendererFactory == null) {
			return null;
		}

		AssetRenderer assetRenderer = assetRendererFactory.getAssetRenderer(
			classPK);

		if (assetRenderer == null) {
			return null;
		}

		Summary summary = new Summary(
			assetRenderer.getTitle(locale),
			assetRenderer.getSearchSummary(locale));

		summary.setMaxContentLength(SUMMARY_MAX_CONTENT_LENGTH);

		return summary;
	}

	protected static final int SUMMARY_MAX_CONTENT_LENGTH = 200;

	private static final Log _log = LogFactoryUtil.getLog(
		SearchResultUtil.class);

}