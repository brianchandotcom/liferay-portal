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

import com.liferay.change.tracking.constants.CTActionKeys;
import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTPreferences;
import com.liferay.change.tracking.service.CTCollectionService;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.change.tracking.service.CTPreferencesLocalService;
import com.liferay.change.tracking.web.internal.display.CTDisplayRendererRegistry;
import com.liferay.change.tracking.web.internal.security.permission.resource.CTCollectionPermission;
import com.liferay.change.tracking.web.internal.util.PublicationsPortletURLUtil;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemListBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.util.PropsValues;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Samuel Trong Tran
 */
public class PublicationsDisplayContext {

	public PublicationsDisplayContext(
		CTCollectionService ctCollectionService,
		CTDisplayRendererRegistry ctDisplayRendererRegistry,
		CTEntryLocalService ctEntryLocalService,
		CTPreferencesLocalService ctPreferencesLocalService,
		HttpServletRequest httpServletRequest, Language language,
		RenderRequest renderRequest, RenderResponse renderResponse) {

		_ctCollectionService = ctCollectionService;
		_ctDisplayRendererRegistry = ctDisplayRendererRegistry;
		_ctEntryLocalService = ctEntryLocalService;
		_httpServletRequest = httpServletRequest;
		_language = language;

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

	public Map<String, Object> getDropdownReactData(
			CTCollection ctCollection, PermissionChecker permissionChecker)
		throws Exception {

		return Collections.singletonMap(
			"dropdownItems",
			_getDropdownItemsJSONArray(ctCollection, permissionChecker));
	}

	public String getReviewChangesURL(long ctCollectionId) {
		PortletURL reviewURL = _renderResponse.createRenderURL();

		reviewURL.setParameter(
			"mvcRenderCommandName", "/change_tracking/view_changes");
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
			_themeDisplay.getCompanyId(),
			new int[] {
				WorkflowConstants.STATUS_DRAFT, WorkflowConstants.STATUS_EXPIRED
			},
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
			_themeDisplay.getCompanyId(),
			new int[] {
				WorkflowConstants.STATUS_DRAFT, WorkflowConstants.STATUS_EXPIRED
			},
			keywords, searchContainer.getStart(), searchContainer.getEnd(),
			orderByComparator);

		searchContainer.setResults(results);

		_searchContainer = searchContainer;

		return _searchContainer;
	}

	public String getStatusLabel(int status) {
		if (status == WorkflowConstants.STATUS_APPROVED) {
			return "published";
		}
		else if (status == WorkflowConstants.STATUS_EXPIRED) {
			return "out-of-date";
		}
		else if (status == WorkflowConstants.STATUS_DRAFT) {
			return "in-progress";
		}
		else if (status == WorkflowConstants.STATUS_DENIED) {
			return "failed";
		}
		else if (status == WorkflowConstants.STATUS_SCHEDULED) {
			return "scheduled";
		}

		return StringPool.BLANK;
	}

	public String getStatusStyle(int status) {
		if (status == WorkflowConstants.STATUS_EXPIRED) {
			return "warning";
		}

		return WorkflowConstants.getStatusStyle(status);
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
			() -> PropsValues.SCHEDULER_ENABLED,
			navigationItem -> {
				navigationItem.setActive(false);
				navigationItem.setHref(
					_renderResponse.createRenderURL(), "mvcRenderCommandName",
					"/change_tracking/view_scheduled", "displayStyle",
					getDisplayStyle());
				navigationItem.setLabel(
					_language.get(_httpServletRequest, "scheduled"));
			}
		).add(
			navigationItem -> {
				navigationItem.setActive(false);
				navigationItem.setHref(
					_renderResponse.createRenderURL(), "mvcRenderCommandName",
					"/change_tracking/view_history", "displayStyle",
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

	private JSONArray _getDropdownItemsJSONArray(
			CTCollection ctCollection, PermissionChecker permissionChecker)
		throws Exception {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		if (CTCollectionPermission.contains(
				permissionChecker, ctCollection, ActionKeys.UPDATE)) {

			if (ctCollection.getCtCollectionId() != _ctCollectionId) {
				jsonArray.put(
					JSONUtil.put(
						"disabled",
						ctCollection.getStatus() ==
							WorkflowConstants.STATUS_EXPIRED
					).put(
						"href",
						PublicationsPortletURLUtil.getHref(
							_renderResponse.createActionURL(),
							ActionRequest.ACTION_NAME,
							"/change_tracking/checkout_ct_collection",
							"redirect", _themeDisplay.getURLCurrent(),
							"ctCollectionId",
							String.valueOf(ctCollection.getCtCollectionId()))
					).put(
						"label",
						_language.get(
							_httpServletRequest, "work-on-publication")
					).put(
						"symbolLeft", "radio-button"
					));
			}

			jsonArray.put(
				JSONUtil.put(
					"href",
					PublicationsPortletURLUtil.getHref(
						_renderResponse.createRenderURL(),
						"mvcRenderCommandName",
						"/change_tracking/edit_ct_collection", "redirect",
						_themeDisplay.getURLCurrent(), "ctCollectionId",
						String.valueOf(ctCollection.getCtCollectionId()))
				).put(
					"label", _language.get(_httpServletRequest, "edit")
				).put(
					"symbolLeft", "pencil"
				));
		}

		jsonArray.put(
			JSONUtil.put(
				"href",
				PublicationsPortletURLUtil.getHref(
					_renderResponse.createRenderURL(), "mvcRenderCommandName",
					"/change_tracking/view_changes", "ctCollectionId",
					String.valueOf(ctCollection.getCtCollectionId()))
			).put(
				"label", _language.get(_httpServletRequest, "review-changes")
			).put(
				"symbolLeft", "list-ul"
			));

		if (CTCollectionPermission.contains(
				permissionChecker, ctCollection, ActionKeys.PERMISSIONS)) {

			jsonArray.put(
				JSONUtil.put(
					"href",
					PublicationsPortletURLUtil.getPermissionsHref(
						_httpServletRequest, ctCollection, _language)
				).put(
					"label", _language.get(_httpServletRequest, "permissions")
				).put(
					"symbolLeft", "password-policies"
				));
		}

		if (CTCollectionPermission.contains(
				permissionChecker, ctCollection, ActionKeys.DELETE)) {

			jsonArray.put(
				JSONUtil.put("type", "divider")
			).put(
				JSONUtil.put(
					"href",
					PublicationsPortletURLUtil.getDeleteHref(
						_httpServletRequest, _renderResponse,
						_themeDisplay.getURLCurrent(),
						ctCollection.getCtCollectionId(), _language)
				).put(
					"label", _language.get(_httpServletRequest, "delete")
				).put(
					"symbolLeft", "times-circle"
				)
			);
		}

		if (isPublishEnabled(ctCollection.getCtCollectionId()) &&
			CTCollectionPermission.contains(
				permissionChecker, ctCollection, CTActionKeys.PUBLISH)) {

			jsonArray.put(JSONUtil.put("type", "divider"));

			if (PropsValues.SCHEDULER_ENABLED) {
				jsonArray.put(
					JSONUtil.put(
						"disabled",
						ctCollection.getStatus() ==
							WorkflowConstants.STATUS_EXPIRED
					).put(
						"href",
						PublicationsPortletURLUtil.getHref(
							_renderResponse.createRenderURL(),
							"mvcRenderCommandName",
							"/change_tracking/view_conflicts", "redirect",
							_themeDisplay.getURLCurrent(), "ctCollectionId",
							String.valueOf(ctCollection.getCtCollectionId()),
							"schedule", Boolean.TRUE.toString())
					).put(
						"label", _language.get(_httpServletRequest, "schedule")
					).put(
						"symbolLeft", "calendar"
					));
			}

			jsonArray.put(
				JSONUtil.put(
					"disabled",
					ctCollection.getStatus() == WorkflowConstants.STATUS_EXPIRED
				).put(
					"href",
					PublicationsPortletURLUtil.getHref(
						_renderResponse.createRenderURL(),
						"mvcRenderCommandName",
						"/change_tracking/view_conflicts", "redirect",
						_themeDisplay.getURLCurrent(), "ctCollectionId",
						String.valueOf(ctCollection.getCtCollectionId()))
				).put(
					"label", _language.get(_httpServletRequest, "publish")
				).put(
					"symbolLeft", "change"
				));
		}

		return jsonArray;
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
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private SearchContainer<CTCollection> _searchContainer;
	private final ThemeDisplay _themeDisplay;

}