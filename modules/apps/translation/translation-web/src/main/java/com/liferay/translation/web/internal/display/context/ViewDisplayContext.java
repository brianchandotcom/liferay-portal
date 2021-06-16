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

package com.liferay.translation.web.internal.display.context;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchContextFactory;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.translation.model.TranslationEntry;
import com.liferay.translation.service.TranslationEntryLocalService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.portlet.ActionRequest;
import javax.portlet.ActionURL;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 */
public class ViewDisplayContext {

	public ViewDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		ModelResourcePermission<TranslationEntry> modelResourcePermission,
		TranslationEntryLocalService translationEntryLocalService) {

		_httpServletRequest = httpServletRequest;
		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_modelResourcePermission = modelResourcePermission;
		_translationEntryLocalService = translationEntryLocalService;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public DropdownItemList getActionDropdownItems(
		TranslationEntry translationEntry) {

		return DropdownItemListBuilder.add(
			() -> _modelResourcePermission.contains(
				_themeDisplay.getPermissionChecker(), translationEntry,
				ActionKeys.UPDATE),
			dropdownItem -> {
				dropdownItem.setHref(getTranslatePortletURL(translationEntry));

				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "edit"));
			}
		).add(
			() -> _modelResourcePermission.contains(
				_themeDisplay.getPermissionChecker(), translationEntry,
				ActionKeys.DELETE),
			dropdownItem -> {
				dropdownItem.putData("action", "delete");

				ActionURL deleteTranslationEntryURL =
					_liferayPortletResponse.createActionURL();

				deleteTranslationEntryURL.setParameter(
					ActionRequest.ACTION_NAME,
					"/translation/delete_translation_entry");
				deleteTranslationEntryURL.setParameter(
					"translationEntryId",
					String.valueOf(translationEntry.getTranslationEntryId()));

				dropdownItem.putData(
					"delete-url", deleteTranslationEntryURL.toString());

				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "delete"));
			}
		).build();
	}

	public PortletURL getActionURL() throws PortalException {
		SearchContainer<TranslationEntry> searchContainer =
			getSearchContainer();

		return searchContainer.getIteratorURL();
	}

	public String getAvailableActions(TranslationEntry translationEntry)
		throws PortalException {

		if (_modelResourcePermission.contains(
				_themeDisplay.getPermissionChecker(), translationEntry,
				ActionKeys.DELETE)) {

			return "deleteSelectedTranslationEntries";
		}

		return StringPool.BLANK;
	}

	public String getLanguageIcon(TranslationEntry translationEntry) {
		return StringUtil.lowerCase(getLanguageLabel(translationEntry));
	}

	public String getLanguageLabel(TranslationEntry translationEntry) {
		return StringUtil.replace(
			translationEntry.getLanguageId(), CharPool.UNDERLINE,
			CharPool.DASH);
	}

	public SearchContainer<TranslationEntry> getSearchContainer()
		throws PortalException {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		_searchContainer = new SearchContainer<>(
			_liferayPortletRequest, _liferayPortletResponse.createRenderURL(),
			null, "no-entries-were-found");

		String orderByCol = ParamUtil.getString(
			_httpServletRequest, "orderByCol", "title");

		_searchContainer.setOrderByCol(orderByCol);

		String orderByType = ParamUtil.getString(
			_httpServletRequest, "orderByType", "asc");

		_searchContainer.setOrderByType(orderByType);

		_searchContainer.setRowChecker(
			new EmptyOnClickRowChecker(_liferayPortletResponse));

		SearchContext searchContext = SearchContextFactory.getInstance(
			_httpServletRequest);

		searchContext.setAttribute("paginationType", "regular");
		searchContext.setBooleanClauses(_getBooleanClauses());
		searchContext.setCompanyId(_themeDisplay.getCompanyId());
		searchContext.setEnd(_searchContainer.getEnd());
		searchContext.setKeywords(
			ParamUtil.getString(_httpServletRequest, "keywords"));
		searchContext.setSorts(_getSorts());
		searchContext.setStart(_searchContainer.getStart());
		searchContext.setUserId(_themeDisplay.getUserId());

		Indexer<TranslationEntry> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(TranslationEntry.class);

		Hits hits = indexer.search(searchContext);

		List<TranslationEntry> results = new ArrayList<>();

		for (Document document : hits.getDocs()) {
			long translationEntryId = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			TranslationEntry translationEntry =
				_translationEntryLocalService.fetchTranslationEntry(
					translationEntryId);

			if (translationEntry != null) {
				results.add(translationEntry);
			}
		}

		_searchContainer.setResults(results);

		_searchContainer.setTotal(hits.getLength());

		return _searchContainer;
	}

	public String getTitle(TranslationEntry translationEntry)
		throws PortalException {

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				TranslationEntry.class.getName());

		AssetRenderer<?> assetRenderer = assetRendererFactory.getAssetRenderer(
			translationEntry.getTranslationEntryId());

		return assetRenderer.getTitle(_themeDisplay.getLocale());
	}

	public PortletURL getTranslatePortletURL(
		TranslationEntry translationEntry) {

		return PortletURLBuilder.createRenderURL(
			_liferayPortletResponse
		).setMVCRenderCommandName(
			"/translation/translate"
		).setRedirect(
			String.valueOf(_liferayPortletResponse.createRenderURL())
		).setParameter(
			"classNameId", translationEntry.getClassNameId()
		).setParameter(
			"classPK", translationEntry.getClassPK()
		).setParameter(
			"targetLanguageId", translationEntry.getLanguageId()
		).build();
	}

	public TranslationEntryManagementToolbarDisplayContext
			getTranslationEntryManagementToolbarDisplayContext()
		throws PortalException {

		return new TranslationEntryManagementToolbarDisplayContext(
			_httpServletRequest, _liferayPortletRequest,
			_liferayPortletResponse, getSearchContainer());
	}

	private BooleanClause[] _getBooleanClauses() {
		long status = ParamUtil.getLong(
			_httpServletRequest, "status", WorkflowConstants.STATUS_ANY);

		if (status == WorkflowConstants.STATUS_ANY) {
			return null;
		}

		BooleanQuery booleanQuery = new BooleanQueryImpl();

		BooleanFilter booleanFilter = new BooleanFilter();

		TermsFilter termsFilter = new TermsFilter(Field.STATUS);

		termsFilter.addValue(String.valueOf(status));

		booleanFilter.add(termsFilter, BooleanClauseOccur.MUST);

		booleanQuery.setPreBooleanFilter(booleanFilter);

		return new BooleanClause[] {
			BooleanClauseFactoryUtil.create(
				booleanQuery, BooleanClauseOccur.MUST.getName())
		};
	}

	private Sort[] _getSorts() {
		boolean reverse = false;

		if (Objects.equals(_searchContainer.getOrderByType(), "desc")) {
			reverse = true;
		}

		if (Objects.equals(_searchContainer.getOrderByCol(), "modified-date")) {
			return new Sort[] {
				new Sort(
					Field.getSortableFieldName(Field.MODIFIED_DATE),
					Sort.LONG_TYPE, reverse)
			};
		}
		else if (Objects.equals(_searchContainer.getOrderByCol(), "status")) {
			return new Sort[] {
				new Sort(
					Field.getSortableFieldName(Field.STATUS), Sort.INT_TYPE,
					reverse)
			};
		}
		else if (Objects.equals(_searchContainer.getOrderByCol(), "title")) {
			return new Sort[] {
				new Sort(
					Field.getSortableFieldName(Field.TITLE), Sort.STRING_TYPE,
					reverse)
			};
		}
		else {
			return null;
		}
	}

	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final ModelResourcePermission<TranslationEntry>
		_modelResourcePermission;
	private SearchContainer<TranslationEntry> _searchContainer;
	private final ThemeDisplay _themeDisplay;
	private final TranslationEntryLocalService _translationEntryLocalService;

}