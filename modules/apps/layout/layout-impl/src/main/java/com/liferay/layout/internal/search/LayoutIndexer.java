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
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.page.template.util.LayoutPageTemplateStructureRenderUtil;
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

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(immediate = true, service = Indexer.class)
public class LayoutIndexer extends BaseIndexer<Layout> {

	public static final String CLASS_NAME = Layout.class.getName();

	public LayoutIndexer() {
		setDefaultSelectedFieldNames(
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.DEFAULT_LANGUAGE_ID, Field.GROUP_ID, Field.MODIFIED_DATE,
			Field.SCOPE_GROUP_ID, Field.UID);
		setDefaultSelectedLocalizedFieldNames(Field.CONTENT, Field.NAME);
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

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					layout.getGroupId(), _portal.getClassNameId(Layout.class),
					layout.getPlid());

		if (layoutPageTemplateStructure == null) {
			return document;
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		HttpServletRequest request = serviceContext.getRequest();
		HttpServletResponse response = serviceContext.getResponse();

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

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Reference
	private Portal _portal;

}