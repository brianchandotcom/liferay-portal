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

import com.liferay.fragment.constants.FragmentEntryLinkConstants;
import com.liferay.layout.constants.LayoutConstants;
import com.liferay.layout.internal.search.util.LayoutPageTemplateStructureRenderUtil;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.search.highlight.HighlightUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.batch.BatchIndexingHelper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Pavel Savinov
 * @deprecated As of Judson (7.1.x), since 7.1.0
 */
@Deprecated
public class LayoutIndexer extends BaseIndexer<Layout> {

	public static final String CLASS_NAME = Layout.class.getName();

	public LayoutIndexer() {
		setDefaultSelectedFieldNames(
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.DEFAULT_LANGUAGE_ID, Field.GROUP_ID, Field.MODIFIED_DATE,
			Field.SCOPE_GROUP_ID, Field.UID);
		setDefaultSelectedLocalizedFieldNames(Field.CONTENT, Field.TITLE);
		setFilterSearch(true);
		setPermissionAware(true);
		setSelectAllLocales(true);
	}

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, String entryClassName,
			long entryClassPK, String actionId)
		throws Exception {

		Layout layout = _layoutLocalService.getLayout(entryClassPK);

		return LayoutPermissionUtil.contains(
			permissionChecker, layout, true, ActionKeys.VIEW);
	}

	@Override
	public void postProcessContextBooleanFilter(
			BooleanFilter contextBooleanFilter, SearchContext searchContext)
		throws Exception {

		String[] types = GetterUtil.getStringValues(
			searchContext.getAttribute(Field.TYPE),
			new String[] {LayoutConstants.LAYOUT_TYPE_CONTENT});

		if (ArrayUtil.isNotEmpty(types)) {
			TermsFilter typeTermsFilter = new TermsFilter(Field.TYPE);

			typeTermsFilter.addValues(types);

			contextBooleanFilter.add(typeTermsFilter, BooleanClauseOccur.MUST);
		}
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, BooleanFilter fullQueryBooleanFilter,
			SearchContext searchContext)
		throws Exception {

		addSearchLocalizedTerm(
			searchQuery, searchContext, Field.CONTENT, false);
		addSearchLocalizedTerm(searchQuery, searchContext, Field.TITLE, false);
	}

	@Override
	protected void doDelete(Layout layout) throws Exception {
		deleteDocument(layout.getCompanyId(), layout.getPlid());
	}

	@Override
	protected Document doGetDocument(Layout layout) throws Exception {
		Document document = getBaseModelDocument(CLASS_NAME, layout);

		document.addUID(CLASS_NAME, layout.getPlid());
		document.addText(
			Field.DEFAULT_LANGUAGE_ID, layout.getDefaultLanguageId());
		document.addLocalizedText(Field.NAME, layout.getNameMap());
		document.addNumberSortable("leftPlid", layout.getLeftPlid());
		document.addText(
			"privateLayout", String.valueOf(layout.isPrivateLayout()));
		document.addText(Field.TYPE, layout.getType());

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					layout.getGroupId(), _portal.getClassNameId(Layout.class),
					layout.getPlid());

		for (String languageId : layout.getAvailableLanguageIds()) {
			document.addText(
				LocalizationUtil.getLocalizedName(Field.TITLE, languageId),
				layout.getName(LocaleUtil.fromLanguageId(languageId)));
		}

		if (layoutPageTemplateStructure == null) {
			return document;
		}

		HttpServletRequest request = null;
		HttpServletResponse response = null;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext != null) {
			request = serviceContext.getRequest();
			response = serviceContext.getResponse();
		}

		for (String languageId : layout.getAvailableLanguageIds()) {
			Locale locale = LocaleUtil.fromLanguageId(languageId);

			String content =
				LayoutPageTemplateStructureRenderUtil.renderLayoutContent(
					request, response, layoutPageTemplateStructure,
					FragmentEntryLinkConstants.VIEW, new HashMap<>(), locale);

			document.addText(
				LocalizationUtil.getLocalizedName(Field.CONTENT, languageId),
				content);
		}

		return document;
	}

	@Override
	protected Summary doGetSummary(
			Document document, Locale locale, String snippet,
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws Exception {

		String localizedFieldName = Field.getLocalizedName(locale, Field.NAME);

		if (Validator.isNull(document.getField(localizedFieldName))) {
			locale = LocaleUtil.fromLanguageId(
				document.get(Field.DEFAULT_LANGUAGE_ID));
		}

		String name = document.get(
			locale, Field.SNIPPET + StringPool.UNDERLINE + Field.TITLE,
			Field.TITLE);

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

		indexableActionableDynamicQuery.setCompanyId(companyId);
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
		indexableActionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		indexableActionableDynamicQuery.performActions();
	}

	private static final String[] _ESCAPE_SAFE_HIGHLIGHTS = {
		"[@HIGHLIGHT1@]", "[@HIGHLIGHT2@]"
	};

	private static final String[] _HIGHLIGHT_TAGS = {
		HighlightUtil.HIGHLIGHT_TAG_OPEN, HighlightUtil.HIGHLIGHT_TAG_CLOSE
	};

	private static final Log _log = LogFactoryUtil.getLog(LayoutIndexer.class);

	private BatchIndexingHelper _batchIndexingHelper;
	private IndexWriterHelper _indexWriterHelper;
	private LayoutLocalService _layoutLocalService;
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;
	private Portal _portal;

}