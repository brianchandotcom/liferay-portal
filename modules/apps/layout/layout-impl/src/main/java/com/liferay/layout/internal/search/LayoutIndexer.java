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

package com.liferay.layout.internal.search;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.highlight.HighlightUtil;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.batch.BatchIndexingHelper;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(immediate = true, service = Indexer.class)
public class LayoutIndexer extends BaseIndexer<Layout> {

	@Override
	public String getClassName() {
		return Layout.class.getName();
	}

	@Override
	protected void doDelete(Layout layout) throws Exception {
		deleteDocument(layout.getCompanyId(), layout.getPlid());
	}

	@Override
	protected Document doGetDocument(Layout layout) throws Exception {
		return null;
	}

	@Override
	protected Summary doGetSummary(
			Document document, Locale locale, String snippet,
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws Exception {

		Locale defaultLocale = LocaleUtil.fromLanguageId(
			document.get(Field.DEFAULT_LANGUAGE_ID));

		String localizedFieldName = Field.getLocalizedName(locale, Field.NAME);

		if (Validator.isNull(document.getField(localizedFieldName))) {
			locale = defaultLocale;
		}

		String name = document.get(locale, Field.NAME);

		String content = document.get(locale, Field.CONTENT);

		content = StringUtil.replace(
			content, _HIGHLIGHT_TAGS, _ESCAPE_SAFE_HIGHLIGHTS);

		content = HtmlUtil.extractText(content);

		content = StringUtil.replace(
			content, _ESCAPE_SAFE_HIGHLIGHTS, _HIGHLIGHT_TAGS);

		snippet = document.get(
			locale, Field.SNIPPET + StringPool.UNDERLINE + Field.CONTENT);

		Set<String> highlights = new HashSet<>();

		HighlightUtil.addSnippet(document, highlights, snippet, "temp");

		content = HighlightUtil.highlight(
			content, ArrayUtil.toStringArray(highlights),
			HighlightUtil.HIGHLIGHT_TAG_OPEN,
			HighlightUtil.HIGHLIGHT_TAG_CLOSE);

		Summary summary = new Summary(locale, name, content);

		summary.setMaxContentLength(200);

		return summary;
	}

	@Override
	protected void doReindex(Layout layout) throws Exception {
		Document document = getDocument(layout);

		_indexWriterHelper.updateDocument(
			getSearchEngineId(), layout.getCompanyId(), document,
			isCommitImmediately());
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		Layout layout = _layoutLocalService.getLayout(classPK);

		doReindex(layout);
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		_reindexLayouts(companyId);
	}

	private void _reindexLayouts(long companyId) throws PortalException {
		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			_layoutLocalService.getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setInterval(
			_batchIndexingHelper.getBulkSize(Layout.class.getName()));
		indexableActionableDynamicQuery.setPerformActionMethod(
			(Layout layout) -> {
				try {
					Document document = getDocument(layout);

					indexableActionableDynamicQuery.addDocuments(document);
				}
				catch (PortalException pe) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to index layout " + layout.getPlid(), pe);
					}
				}
			});

		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		indexableActionableDynamicQuery.performActions();
	}

	private static final String[] _ESCAPE_SAFE_HIGHLIGHTS =
		{"[@HIGHLIGHT1@]", "[@HIGHLIGHT2@]"};

	private static final String[] _HIGHLIGHT_TAGS =
		{HighlightUtil.HIGHLIGHT_TAG_OPEN, HighlightUtil.HIGHLIGHT_TAG_CLOSE};

	private static final Log _log = LogFactoryUtil.getLog(LayoutIndexer.class);

	@Reference
	private BatchIndexingHelper _batchIndexingHelper;

	@Reference
	private IndexWriterHelper _indexWriterHelper;

	@Reference
	private LayoutLocalService _layoutLocalService;

}