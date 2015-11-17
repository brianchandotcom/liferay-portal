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

package com.liferay.portal.search.internal.result;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.SummaryFactory;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.AssetRendererFactory;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 * @author Adolfo Pérez
 * @author André de Oliveira
 */
@Component(immediate = true, service = SummaryFactory.class)
public class SummaryFactoryImpl implements SummaryFactory {

	public static final int SUMMARY_MAX_CONTENT_LENGTH = 200;

	@Override
	public Summary getSummary(
			Document document, String className, long classPK, Locale locale,
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws PortalException {

		Indexer<?> indexer = _indexerRegistry.getIndexer(className);

		if (indexer != null) {
			String snippet = document.get(Field.SNIPPET);

			return indexer.getSummary(
				document, snippet, portletRequest, portletResponse);
		}

		return getSummary(className, classPK, locale);
	}

	@Override
	public Summary getSummary(String className, long classPK, Locale locale)
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

	@Reference(unbind = "-")
	public void setIndexerRegistry(IndexerRegistry indexerRegistry) {
		_indexerRegistry = indexerRegistry;
	}

	private IndexerRegistry _indexerRegistry;

}