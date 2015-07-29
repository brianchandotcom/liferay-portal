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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.AssetRendererFactory;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author Adolfo Pérez
 */
public abstract class BaseSearchResultManager implements SearchResultManager {

	public static final int SUMMARY_MAX_CONTENT_LENGTH = 200;

	@Override
	public SearchResult createSearchResult(Document document) {
		long classNameId = GetterUtil.getLong(
			document.get(Field.CLASS_NAME_ID));
		long classPK = GetterUtil.getLong(document.get(Field.CLASS_PK));

		if ((classPK > 0) && (classNameId > 0)) {
			String className = PortalUtil.getClassName(classNameId);

			return new SearchResult(className, classPK);
		}

		String entryClassName = GetterUtil.getString(
			document.get(Field.ENTRY_CLASS_NAME));
		long entryClassPK = GetterUtil.getLong(
			document.get(Field.ENTRY_CLASS_PK));

		return new SearchResult(entryClassName, entryClassPK);
	}

	@Override
	public void updateSearchResult(
			SearchResult searchResult, Document document, Locale locale,
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws PortalException {

		long classNameId = GetterUtil.getLong(
			document.get(Field.CLASS_NAME_ID));
		long classPK = GetterUtil.getLong(document.get(Field.CLASS_PK));

		if ((classPK > 0) && (classNameId > 0)) {
			addRelatedModel(
				searchResult, document, locale, portletRequest,
				portletResponse);

			if (searchResult.getSummary() == null) {
				Summary summary = getSummary(
					searchResult.getClassName(), searchResult.getClassPK(),
					locale);

				searchResult.setSummary(summary);
			}
		}
		else {
			String entryClassName = GetterUtil.getString(
				document.get(Field.ENTRY_CLASS_NAME));
			long entryClassPK = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			searchResult.setSummary(
				getSummary(
					document, entryClassName, entryClassPK, locale,
					portletRequest, portletResponse));
		}
	}

	protected static Summary getSummary(
			Document document, String className, long classPK, Locale locale,
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws PortalException {

		Indexer<?> indexer = IndexerRegistryUtil.getIndexer(className);

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

	/**
	 * @throws PortalException
	 */
	protected void addRelatedModel(
			SearchResult searchResult, Document document, Locale locale,
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws PortalException {
	}

}