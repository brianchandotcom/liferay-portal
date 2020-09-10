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

package com.liferay.change.tracking.web.internal.display.context;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTPreferences;
import com.liferay.change.tracking.service.CTCollectionService;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.change.tracking.service.CTPreferencesLocalService;
import com.liferay.change.tracking.web.internal.display.CTDisplayRendererRegistry;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemListBuilder;
import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;
import java.util.Objects;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Samuel Trong Tran
 */
public class ChangeListsDisplayContext {

	public ChangeListsDisplayContext(
		CTCollectionService ctCollectionService,
		CTDisplayRendererRegistry ctDisplayRendererRegistry,
		CTEntryLocalService ctEntryLocalService,
		CTPreferencesLocalService ctPreferencesLocalService, Language language,
		Portal portal, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_ctCollectionService = ctCollectionService;
		_ctDisplayRendererRegistry = ctDisplayRendererRegistry;
		_ctEntryLocalService = ctEntryLocalService;
		_language = language;

		_portal = portal;

		_httpServletRequest = _portal.getHttpServletRequest(renderRequest);

		_renderRequest = renderRequest;

		_themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		CTPreferences ctPreferences =
			ctPreferencesLocalService.fetchCTPreferences(
				_themeDisplay.getCompanyId(), _themeDisplay.getUserId());

		if (ctPreferences == null) {
			_ctCollectionId = CTConstants.CT_COLLECTION_ID_PRODUCTION;
		}
		else {
			_ctCollectionId = ctPreferences.getCtCollectionId();
		}

		_renderResponse = renderResponse;
	}

	public long getCtCollectionId() {
		return _ctCollectionId;
	}

	public CTDisplayRendererRegistry getCtDisplayRendererRegistry() {
		return _ctDisplayRendererRegistry;
	}

	public String getDisplayStyle() {
		return ParamUtil.getString(_renderRequest, "displayStyle", "list");
	}

	public String getReviewChangesURL(long ctCollectionId) {
		PortletURL reviewURL = _renderResponse.createRenderURL();

		reviewURL.setParameter(
			"mvcRenderCommandName", "/change_lists/view_changes");
		reviewURL.setParameter(
			"backURL", _portal.getCurrentURL(_renderRequest));
		reviewURL.setParameter(
			"ctCollectionId", String.valueOf(ctCollectionId));

		return reviewURL.toString();
	}

	public SearchContainer<CTCollection> getSearchContainer() {
		if (_searchContainer != null) {
			return _searchContainer;
		}

		SearchContainer<CTCollection> searchContainer = new SearchContainer<>(
			_renderRequest, new DisplayTerms(_renderRequest), null,
			SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA,
			PortletURLUtil.getCurrent(_renderRequest, _renderResponse), null,
			_language.get(_httpServletRequest, "no-publications-were-found"));

		searchContainer.setId("ongoing");
		searchContainer.setOrderByCol(_getOrderByCol());
		searchContainer.setOrderByType(_getOrderByType());

		DisplayTerms displayTerms = searchContainer.getDisplayTerms();

		String keywords = displayTerms.getKeywords();

		int count = _ctCollectionService.getCTCollectionsCount(
			_themeDisplay.getCompanyId(), WorkflowConstants.STATUS_DRAFT,
			keywords);

		searchContainer.setTotal(count);

		String column = searchContainer.getOrderByCol();

		if (column.equals("modified-date")) {
			column = "modifiedDate";
		}

		OrderByComparator<CTCollection> orderByComparator =
			OrderByComparatorFactoryUtil.create(
				"CTCollection", column,
				Objects.equals(searchContainer.getOrderByType(), "asc"));

		List<CTCollection> results = _ctCollectionService.getCTCollections(
			_themeDisplay.getCompanyId(), WorkflowConstants.STATUS_DRAFT,
			keywords, searchContainer.getStart(), searchContainer.getEnd(),
			orderByComparator);

		searchContainer.setResults(results);

		_searchContainer = searchContainer;

		return _searchContainer;
	}

	public List<NavigationItem> getViewNavigationItems() {
		return NavigationItemListBuilder.add(
			navigationItem -> {
				navigationItem.setActive(true);
				navigationItem.setHref(_renderResponse.createRenderURL());
				navigationItem.setLabel(
					_language.get(_httpServletRequest, "ongoing"));
			}
		).add(
			navigationItem -> {
				navigationItem.setActive(false);
				navigationItem.setHref(
					_renderResponse.createRenderURL(), "mvcRenderCommandName",
					"/change_lists/view_scheduled", "displayStyle",
					getDisplayStyle());
				navigationItem.setLabel(
					_language.get(_httpServletRequest, "scheduled"));
			}
		).add(
			navigationItem -> {
				navigationItem.setActive(false);
				navigationItem.setHref(
					_renderResponse.createRenderURL(), "mvcRenderCommandName",
					"/change_lists/view_history", "displayStyle",
					getDisplayStyle());
				navigationItem.setLabel(
					_language.get(_httpServletRequest, "history"));
			}
		).build();
	}

	public boolean isPublishEnabled(long ctCollectionId) {
		int count = _ctEntryLocalService.getCTCollectionCTEntriesCount(
			ctCollectionId);

		if (count > 0) {
			return true;
		}

		return false;
	}

	private String _getOrderByCol() {
		return ParamUtil.getString(
			_renderRequest, SearchContainer.DEFAULT_ORDER_BY_COL_PARAM,
			"modified-date");
	}

	private String _getOrderByType() {
		return ParamUtil.getString(
			_renderRequest, SearchContainer.DEFAULT_ORDER_BY_TYPE_PARAM,
			"desc");
	}

	private final long _ctCollectionId;
	private final CTCollectionService _ctCollectionService;
	private final CTDisplayRendererRegistry _ctDisplayRendererRegistry;
	private final CTEntryLocalService _ctEntryLocalService;
	private final HttpServletRequest _httpServletRequest;
	private final Language _language;
	private final Portal _portal;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private SearchContainer<CTCollection> _searchContainer;
	private final ThemeDisplay _themeDisplay;

}