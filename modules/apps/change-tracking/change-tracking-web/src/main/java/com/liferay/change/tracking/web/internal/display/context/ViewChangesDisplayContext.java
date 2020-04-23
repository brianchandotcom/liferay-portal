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

import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.reference.closure.CTClosure;
import com.liferay.change.tracking.reference.closure.CTClosureFactory;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.change.tracking.web.internal.display.BasePersistenceRegistry;
import com.liferay.change.tracking.web.internal.display.CTDisplayRendererRegistry;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemListBuilder;
import com.liferay.petra.lang.SafeClosable;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Stack;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Samuel Trong Tran
 */
public class ViewChangesDisplayContext {

	public ViewChangesDisplayContext(
		BasePersistenceRegistry basePersistenceRegistry,
		CTClosureFactory ctClosureFactory, CTCollection ctCollection,
		CTDisplayRendererRegistry ctDisplayRendererRegistry,
		CTEntryLocalService ctEntryLocalService, Language language,
		Portal portal, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_basePersistenceRegistry = basePersistenceRegistry;
		_ctClosureFactory = ctClosureFactory;
		_ctCollection = ctCollection;
		_ctDisplayRendererRegistry = ctDisplayRendererRegistry;

		_ctEntryLocalService = ctEntryLocalService;

		int count = _ctEntryLocalService.getCTCollectionCTEntriesCount(
			_ctCollection.getCtCollectionId());

		if (count > 0) {
			_hasChanges = true;
		}
		else {
			_hasChanges = false;
		}

		_language = language;

		_portal = portal;

		_httpServletRequest = _portal.getHttpServletRequest(renderRequest);

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
	}

	public String getBackURL() {
		String backURL = ParamUtil.getString(_renderRequest, "backURL");

		if (Validator.isNotNull(backURL)) {
			return backURL;
		}

		PortletURL portletURL = _renderResponse.createRenderURL();

		return portletURL.toString();
	}

	public CTCollection getCtCollection() {
		return _ctCollection;
	}

	public String getDisplayStyle() {
		if (!hasChanges()) {
			return "all-items";
		}

		return ParamUtil.getString(_renderRequest, "displayStyle", "all-items");
	}

	public long getModelClassNameId() {
		return ParamUtil.getLong(_renderRequest, "modelClassNameId");
	}

	public long getModelClassPK() {
		return ParamUtil.getLong(_renderRequest, "modelClassPK");
	}

	public Map<String, Object> getReactData() throws PortalException {
		return HashMapBuilder.<String, Object>put(
			"headerTitles",
			() -> JSONUtil.put(
				"change", _language.get(_httpServletRequest, "change"))
		).put(
			"spritemap",
			() -> {
				ThemeDisplay themeDisplay =
					(ThemeDisplay)_renderRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				return themeDisplay.getPathThemeImages() + "/lexicon/icons.svg";
			}
		).put(
			"tree", _getTreeJsonObject()
		).build();
	}

	public SearchContainer<CTEntry> getSearchContainer() {
		if (_searchContainer != null) {
			return _searchContainer;
		}

		SearchContainer<CTEntry> searchContainer = new SearchContainer<>(
			_renderRequest, new DisplayTerms(_renderRequest), null,
			SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA,
			PortletURLUtil.getCurrent(_renderRequest, _renderResponse), null,
			_language.get(_httpServletRequest, "no-changes-were-found"));

		searchContainer.setId("reviewChanges");
		searchContainer.setOrderByCol(_getOrderByCol());
		searchContainer.setOrderByType(_getOrderByType());

		String column = searchContainer.getOrderByCol();

		if (column.equals("modified-date")) {
			column = "modifiedDate";
		}

		int count = _ctEntryLocalService.getCTCollectionCTEntriesCount(
			_ctCollection.getCtCollectionId());

		searchContainer.setTotal(count);

		List<CTEntry> results = _ctEntryLocalService.getCTCollectionCTEntries(
			_ctCollection.getCtCollectionId(), searchContainer.getStart(),
			searchContainer.getEnd(),
			OrderByComparatorFactoryUtil.create(
				"CTCollection", column,
				Objects.equals(searchContainer.getOrderByType(), "asc")));

		searchContainer.setResults(results);

		_searchContainer = searchContainer;

		return _searchContainer;
	}

	public String getStatusLabel(int status) {
		if (status == WorkflowConstants.STATUS_APPROVED) {
			return "published";
		}
		else if (status == WorkflowConstants.STATUS_DRAFT) {
			return "in-progress";
		}
		else if (status == WorkflowConstants.STATUS_DENIED) {
			return "failed";
		}

		return StringPool.BLANK;
	}

	public String getTreeViewChildURL(CTEntry ctEntry) throws PortletException {
		CTClosure ctClosure = _getCTClosure();

		Map<Long, List<Long>> childPKsMap = ctClosure.getChildPKsMap(
			ctEntry.getModelClassNameId(), ctEntry.getModelClassPK());

		if (childPKsMap.isEmpty()) {
			return null;
		}

		PortletURL currentURL = PortletURLUtil.clone(
			_getCurrentURLObj(), _renderResponse);

		currentURL.setParameter(
			"modelClassNameId", String.valueOf(ctEntry.getModelClassNameId()));
		currentURL.setParameter(
			"modelClassPK", String.valueOf(ctEntry.getModelClassPK()));

		return currentURL.toString();
	}

	public List<NavigationItem> getViewNavigationItems() {
		if (!hasChanges()) {
			return NavigationItemListBuilder.add(
				navigationItem -> {
					navigationItem.setActive(true);
					navigationItem.setHref(
						PortalUtil.getCurrentURL(_renderRequest));
					navigationItem.setLabel(
						_language.get(_httpServletRequest, "all-items"));
				}
			).build();
		}

		return NavigationItemListBuilder.add(
			navigationItem -> {
				navigationItem.setActive(
					Objects.equals(getDisplayStyle(), "all-items"));
				navigationItem.setHref(
					_renderResponse.createRenderURL(), "mvcRenderCommandName",
					"/change_lists/view_changes", "ctCollectionId",
					_ctCollection.getCtCollectionId(), "displayStyle",
					"all-items");
				navigationItem.setLabel(
					_language.get(_httpServletRequest, "all-items"));
			}
		).add(
			navigationItem -> {
				navigationItem.setActive(
					Objects.equals(getDisplayStyle(), "tree-view"));
				navigationItem.setHref(
					_renderResponse.createRenderURL(), "mvcRenderCommandName",
					"/change_lists/view_changes", "ctCollectionId",
					_ctCollection.getCtCollectionId(), "displayStyle",
					"tree-view");
				navigationItem.setLabel(
					_language.get(_httpServletRequest, "tree-view"));
			}
		).build();
	}

	public boolean hasChanges() {
		return _hasChanges;
	}

	public boolean isPublished() {
		if (_ctCollection.getStatus() == WorkflowConstants.STATUS_APPROVED) {
			return true;
		}

		return false;
	}

	private CTClosure _getCTClosure() {
		if (_ctClosure != null) {
			return _ctClosure;
		}

		_ctClosure = _ctClosureFactory.create(
			_ctCollection.getCtCollectionId());

		return _ctClosure;
	}

	private PortletURL _getCurrentURLObj() {
		if (_currentURLObj != null) {
			return _currentURLObj;
		}

		_currentURLObj = PortletURLUtil.getCurrent(
			_renderRequest, _renderResponse);

		return _currentURLObj;
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

	private JSONObject _getTreeJsonObject() throws PortalException {
		CTClosure ctClosure = _getCTClosure();
		LiferayPortletRequest liferayPortletRequest =
			_portal.getLiferayPortletRequest(_renderRequest);
		LiferayPortletResponse liferayPortletResponse =
			_portal.getLiferayPortletResponse(_renderResponse);

		int nodeIdCounter = 1;

		JSONObject treeJSONObject = JSONFactoryUtil.createJSONObject();

		Stack<JSONObject> stack = new Stack<>();

		stack.push(
			treeJSONObject.put(
				"id", 0
			).put(
				"title", _language.get(_httpServletRequest, "home")
			));

		while (!stack.isEmpty()) {
			Map<Long, List<Long>> childPKsMap = null;
			JSONObject stackJSONObject = stack.pop();

			if (stackJSONObject.getInt("id") == 0) {
				childPKsMap = ctClosure.getRootPKsMap();
			}
			else {
				childPKsMap = ctClosure.getChildPKsMap(
					stackJSONObject.getLong("modelClassNameId"),
					stackJSONObject.getLong("modelClassPK"));
			}

			if (!childPKsMap.isEmpty()) {
				List<JSONObject> childJSONObjects = new ArrayList<>();

				List<Long> childClassNameIds = new ArrayList<>(
					childPKsMap.keySet());

				for (long childClassNameId : childClassNameIds) {
					_putChildClassJSONObjects(
						childClassNameId, childJSONObjects,
						childPKsMap.get(childClassNameId),
						liferayPortletRequest, liferayPortletResponse);
				}

				childJSONObjects.sort(new SortJSONObjectsByTitle());

				JSONArray childrenJSONArray = JSONFactoryUtil.createJSONArray();

				for (JSONObject childJSONObject : childJSONObjects) {
					childJSONObject.put("id", nodeIdCounter++);

					childrenJSONArray.put(childJSONObject);

					stack.push(childJSONObject);
				}

				if (childrenJSONArray.length() > 0) {
					stackJSONObject.put("children", childrenJSONArray);
				}
			}
		}

		return treeJSONObject;
	}

	private <T extends BaseModel<T>> void _putChildClassJSONObjects(
		long childClassNameId, List<JSONObject> childJSONObjects,
		List<Long> childPKs, LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(
					_ctCollection.getCtCollectionId())) {

			Collection<T> baseModels = _basePersistenceRegistry.fetchBaseModels(
				childClassNameId, childPKs);

			for (T baseModel : baseModels) {
				long childClassPK = (long)baseModel.getPrimaryKeyObj();

				ResourceURL renderURL = _renderResponse.createResourceURL();

				renderURL.setResourceID("/change_lists/render_tree_view_entry");
				renderURL.setParameter(
					"ctCollectionId",
					String.valueOf(_ctCollection.getCtCollectionId()));
				renderURL.setParameter(
					"modelClassNameId", String.valueOf(childClassNameId));
				renderURL.setParameter(
					"modelClassPK", String.valueOf(childClassPK));

				JSONObject childJSONObject = JSONUtil.put(
					"modelClassNameId", childClassNameId
				).put(
					"modelClassPK", childClassPK
				).put(
					"renderURL", renderURL.toString()
				).put(
					"title",
					_ctDisplayRendererRegistry.getTypeName(
						_renderRequest.getLocale(), baseModel, childClassNameId)
				);

				JSONArray dropdownItemsJSONArray =
					JSONFactoryUtil.createJSONArray();

				CTEntry ctEntry = _ctEntryLocalService.fetchCTEntry(
					_ctCollection.getCtCollectionId(), childClassNameId,
					childClassPK);

				if (ctEntry != null) {
					childJSONObject.put(
						"ctEntry", "true"
					).put(
						"description",
						_ctDisplayRendererRegistry.getEntryTitle(
							ctEntry, _httpServletRequest)
					);

					String editURL = _ctDisplayRendererRegistry.getEditURL(
						_httpServletRequest, ctEntry);

					if (Validator.isNotNull(editURL)) {
						dropdownItemsJSONArray.put(
							JSONUtil.put(
								"href", editURL
							).put(
								"label",
								_language.get(_httpServletRequest, "edit")
							));
					}

					dropdownItemsJSONArray.put(
						JSONUtil.put(
							"href",
							_ctDisplayRendererRegistry.getViewURL(
								liferayPortletRequest, liferayPortletResponse,
								ctEntry, true)
						).put(
							"label",
							_language.get(_httpServletRequest, "view-diff")
						));
				}
				else {
					childJSONObject.put("ctEntry", "false");

					dropdownItemsJSONArray.put(
						JSONUtil.put(
							"href",
							_ctDisplayRendererRegistry.getViewURL(
								liferayPortletRequest, liferayPortletResponse,
								childClassNameId, childClassPK)
						).put(
							"label", _language.get(_httpServletRequest, "view")
						));
				}

				childJSONObject.put("dropdownItems", dropdownItemsJSONArray);

				childJSONObjects.add(childJSONObject);
			}
		}
	}

	private final BasePersistenceRegistry _basePersistenceRegistry;
	private CTClosure _ctClosure;
	private final CTClosureFactory _ctClosureFactory;
	private final CTCollection _ctCollection;
	private final CTDisplayRendererRegistry _ctDisplayRendererRegistry;
	private final CTEntryLocalService _ctEntryLocalService;
	private PortletURL _currentURLObj;
	private final boolean _hasChanges;
	private final HttpServletRequest _httpServletRequest;
	private final Language _language;
	private final Portal _portal;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private SearchContainer<CTEntry> _searchContainer;

	private static class SortJSONObjectsByTitle
		implements Comparator<JSONObject> {

		@Override
		public int compare(JSONObject o1, JSONObject o2) {
			String title1 = o1.getString("title");
			String title2 = o2.getString("title");

			return title1.compareTo(title2);
		}

	}

}