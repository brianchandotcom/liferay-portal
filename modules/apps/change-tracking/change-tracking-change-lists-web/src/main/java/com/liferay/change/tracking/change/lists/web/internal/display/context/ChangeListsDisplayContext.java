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

package com.liferay.change.tracking.change.lists.web.internal.display.context;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.definition.CTDefinitionRegistryUtil;
import com.liferay.change.tracking.engine.CTEngineManager;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.model.CTPreferences;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.change.tracking.service.CTPreferencesLocalService;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItemList;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.template.soy.util.SoyContext;
import com.liferay.portal.template.soy.util.SoyContextFactoryUtil;

import java.text.Format;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Máté Thurzó
 */
public class ChangeListsDisplayContext {

	public ChangeListsDisplayContext(
		HttpServletRequest httpServletRequest, RenderRequest renderRequest,
		RenderResponse renderResponse,
		CTPreferencesLocalService ctPreferencesLocalService,
		CTCollectionLocalService ctCollectionLocalService,
		CTEntryLocalService ctEntryLocalService,
		CTEngineManager ctEngineManager) {

		_httpServletRequest = httpServletRequest;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_ctPreferencesLocalService = ctPreferencesLocalService;
		_ctCollectionLocalService = ctCollectionLocalService;
		_ctEntryLocalService = ctEntryLocalService;
		_ctEngineManager = ctEngineManager;

		_themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public SoyContext getChangeListsContext() throws Exception {
		SoyContext soyContext = SoyContextFactoryUtil.createSoyContext();

		soyContext.put(
			"changeEntries", _getCTEntriesJSONArray()
		).put(
			"changeListsDropdownMenu", _getChangeListsDropdownMenuJSONArray()
		).put(
			"namespace", _renderResponse.getNamespace()
		).put(
			"spritemap",
			_themeDisplay.getPathThemeImages() + "/lexicon/icons.svg"
		).put(
			"urlCollectionsBase",
			_themeDisplay.getPortalURL() +
				"/o/change-tracking-legacy/collections"
		).put(
			"urlProductionInformation",
			StringBundler.concat(
				_themeDisplay.getPortalURL(),
				"/o/change-tracking-legacy/processes?companyId=",
				_themeDisplay.getCompanyId(), "&type=published-latest")
		).put(
			"urlProductionView", _themeDisplay.getPortalURL()
		).put(
			"urlUserSettings",
			StringBundler.concat(
				_themeDisplay.getPortalURL(),
				"/o/change-tracking-legacy/configurations/",
				_themeDisplay.getCompanyId(), "/user/",
				_themeDisplay.getUserId())
		);

		PortletURL portletURL = PortletURLFactoryUtil.create(
			_renderRequest, CTPortletKeys.CHANGE_LISTS_HISTORY,
			PortletRequest.RENDER_PHASE);

		soyContext.put("urlChangeListsHistory", portletURL.toString());

		portletURL = PortletURLFactoryUtil.create(
			_renderRequest, CTPortletKeys.CHANGE_LISTS,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("select", "true");

		soyContext.put("urlSelectChangeList", portletURL.toString());

		portletURL.setParameter("refresh", "true");

		soyContext.put("urlSelectProduction", portletURL.toString());

		return soyContext;
	}

	public String getConfirmationMessage(String ctCollectionName) {
		return LanguageUtil.format(
			_httpServletRequest, "do-you-want-to-switch-to-x-change-list",
			ctCollectionName, true);
	}

	public CreationMenu getCreationMenu() {
		CreationMenu creationMenu = new CreationMenu();

		creationMenu.addDropdownItem(
			dropdownItem -> {
				PortletURL portletURL = PortletURLFactoryUtil.create(
					_httpServletRequest, CTPortletKeys.CHANGE_LISTS,
					PortletRequest.RENDER_PHASE);

				portletURL.setParameter(
					"mvcRenderCommandName", "/change_lists/add_ct_collection");
				portletURL.setParameter(
					"backURL", _themeDisplay.getURLCurrent());

				dropdownItem.setHref(portletURL);

				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "add-change-list"));
			});

		return creationMenu;
	}

	public long getCTCollectionAgeTime(CTCollection ctCollection) {
		if (ctCollection == null) {
			return 0L;
		}

		Date modifiedDate = ctCollection.getModifiedDate();

		return System.currentTimeMillis() - modifiedDate.getTime();
	}

	public Map<Integer, Long> getCTCollectionChangeTypeCounts(
		long ctCollectionId) {

		return _ctEngineManager.getCTCollectionChangeTypeCounts(ctCollectionId);
	}

	public String getDisplayStyle() {
		if (_displayStyle != null) {
			return _displayStyle;
		}

		_displayStyle = ParamUtil.getString(
			_httpServletRequest, "displayStyle", "icon");

		return _displayStyle;
	}

	public List<DropdownItem> getFilterDropdownItems() {
		return new DropdownItemList() {
			{
				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(
							_getFilterUserDropdownItems());
						dropdownGroupItem.setLabel(
							LanguageUtil.get(
								_httpServletRequest, "filter-by-user"));
					});

				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(
							_getFilterStatusDropdownItems());
						dropdownGroupItem.setLabel(
							LanguageUtil.get(
								_httpServletRequest, "filter-by-status"));
					});

				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(
							_getOrderByDropdownItems());
						dropdownGroupItem.setLabel(
							LanguageUtil.get(_httpServletRequest, "order-by"));
					});
			}
		};
	}

	public String getOrderByType() {
		if (_orderByType != null) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(
			_httpServletRequest, "orderByType", "asc");

		return _orderByType;
	}

	public CTCollection getProductionCTCollection() {
		Optional<CTCollection> productionCTCollectionOptional =
			_ctEngineManager.getProductionCTCollectionOptional(
				_themeDisplay.getCompanyId());

		return productionCTCollectionOptional.orElse(null);
	}

	public SearchContainer<CTCollection> getSearchContainer() {
		SearchContainer<CTCollection> searchContainer = new SearchContainer<>(
			_renderRequest, new DisplayTerms(_renderRequest), null,
			SearchContainer.DEFAULT_CUR_PARAM, 0, SearchContainer.DEFAULT_DELTA,
			_getIteratorURL(), null, "there-are-no-change-lists");

		QueryDefinition<CTCollection> queryDefinition = new QueryDefinition<>();

		DisplayTerms displayTerms = searchContainer.getDisplayTerms();

		String keywords = displayTerms.getKeywords();

		queryDefinition.setAttribute("keywords", keywords);

		int count = (int)_ctEngineManager.countByKeywords(
			_themeDisplay.getCompanyId(), queryDefinition);

		List<CTCollection> ctCollections = new ArrayList<>();

		Optional<CTCollection> productionCTCollection =
			_ctEngineManager.getProductionCTCollectionOptional(
				_themeDisplay.getCompanyId());

		if (productionCTCollection.isPresent() && Validator.isNull(keywords)) {
			if (searchContainer.getCur() == 1) {
				ctCollections.add(productionCTCollection.get());
			}

			count += 1;
		}

		if (searchContainer.getEnd() < count) {
			queryDefinition.setEnd(searchContainer.getEnd() - 1);
		}
		else {
			queryDefinition.setEnd(searchContainer.getEnd());
		}

		queryDefinition.setOrderByComparator(
			OrderByComparatorFactoryUtil.create(
				"CTCollection", _getOrderByCol(),
				getOrderByType().equals("asc")));

		if (searchContainer.getStart() > 0) {
			queryDefinition.setStart(searchContainer.getStart() - 1);
		}
		else {
			queryDefinition.setStart(searchContainer.getStart());
		}

		ctCollections.addAll(
			_ctEngineManager.searchByKeywords(
				_themeDisplay.getCompanyId(), queryDefinition));

		searchContainer.setResults(ctCollections);

		searchContainer.setTotal(count);

		return searchContainer;
	}

	public String getSortingURL() {
		PortletURL sortingURL = _getPortletURL();

		sortingURL.setParameter(
			"orderByType",
			Objects.equals(getOrderByType(), "desc") ? "desc" : "asc");

		return sortingURL.toString();
	}

	public String getViewSearchActionURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("mvcRenderCommandName", "/change_lists/view");
		portletURL.setParameter("select", "true");
		portletURL.setParameter("displayStyle", getDisplayStyle());

		return portletURL.toString();
	}

	public List<ViewTypeItem> getViewTypeItems() {
		return new ViewTypeItemList(_getPortletURL(), _displayStyle) {
			{
				addCardViewTypeItem();
				addTableViewTypeItem();
			}
		};
	}

	public boolean hasCTEntries(long ctCollectionId) {
		QueryDefinition<CTEntry> queryDefinition = new QueryDefinition<>();

		queryDefinition.setStatus(WorkflowConstants.STATUS_DRAFT);

		int ctEntriesCount = _ctEngineManager.getCTEntriesCount(
			ctCollectionId, queryDefinition);

		if (ctEntriesCount > 0) {
			return true;
		}

		return false;
	}

	public boolean isChangeListActive(long ctCollectionId) {
		long recentCTCollectionId = _ctEngineManager.getRecentCTCollectionId(
			_themeDisplay.getUserId());

		if (recentCTCollectionId == ctCollectionId) {
			return true;
		}

		return false;
	}

	public boolean isCheckoutCtCollectionConfirmationEnabled() {
		CTPreferences ctPreferences =
			_ctPreferencesLocalService.fetchCTPreferences(
				_themeDisplay.getCompanyId(), _themeDisplay.getUserId());

		if (ctPreferences == null) {
			return true;
		}

		return ctPreferences.isConfirmationEnabled();
	}

	private JSONArray _getChangeListsDropdownMenuJSONArray() throws Exception {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		OrderByComparator<CTCollection> orderByComparator =
			OrderByComparatorFactoryUtil.create(
				"CTCollection", "modifiedDate", false);

		List<CTCollection> ctCollections =
			_ctCollectionLocalService.getCTCollections(
				_themeDisplay.getCompanyId(), WorkflowConstants.STATUS_DRAFT,
				false, QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator);

		long ctCollectionId = _getCTCollectionId();

		PortletURL checkoutURL = PortletURLFactoryUtil.create(
			_httpServletRequest, CTPortletKeys.CHANGE_LISTS,
			PortletRequest.ACTION_PHASE);

		checkoutURL.setParameter(
			ActionRequest.ACTION_NAME, "/change_lists/checkout_ct_collection");

		for (CTCollection ctCollection : ctCollections) {
			if (ctCollection.getCtCollectionId() != ctCollectionId) {
				checkoutURL.setParameter(
					"ctCollectionId",
					String.valueOf(ctCollection.getCtCollectionId()));

				JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

				jsonArray.put(
					jsonObject.put(
						"checkoutURL", checkoutURL.toString()
					).put(
						"label", ctCollection.getName()
					));
			}

			if (jsonArray.length() == 5) {
				break;
			}
		}

		return jsonArray;
	}

	private long _getCTCollectionId() {
		CTPreferences ctPreferences =
			_ctPreferencesLocalService.fetchCTPreferences(
				_themeDisplay.getCompanyId(), 0);

		if (ctPreferences == null) {
			return CTConstants.CT_COLLECTION_ID_PRODUCTION;
		}

		ctPreferences = _ctPreferencesLocalService.fetchCTPreferences(
			_themeDisplay.getCompanyId(), _themeDisplay.getUserId());

		if (ctPreferences == null) {
			return CTConstants.CT_COLLECTION_ID_PRODUCTION;
		}

		return ctPreferences.getCtCollectionId();
	}

	private JSONArray _getCTEntriesJSONArray() throws Exception {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		long ctCollectionId = _getCTCollectionId();

		if (ctCollectionId != CTConstants.CT_COLLECTION_ID_PRODUCTION) {
			for (CTEntry ctEntry :
					_ctEntryLocalService.getCTCollectionCTEntries(
						ctCollectionId)) {

				jsonArray.put(_getCTEntryJSONObject(ctEntry));
			}
		}

		return jsonArray;
	}

	private JSONObject _getCTEntryJSONObject(CTEntry ctEntry) throws Exception {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		String changeTypeKey = "added";

		if (ctEntry.getChangeType() == 1) {
			changeTypeKey = "deleted";
		}
		else if (ctEntry.getChangeType() == 2) {
			changeTypeKey = "modified";
		}

		String contentType =
			CTDefinitionRegistryUtil.getVersionEntityContentTypeLanguageKey(
				ctEntry.getModelClassNameId());
		Format format = FastDateFormatFactoryUtil.getDateTime(
			_themeDisplay.getLocale());

		return jsonObject.put(
			"changeType",
			LanguageUtil.get(_themeDisplay.getLocale(), changeTypeKey)
		).put(
			"contentType", _getEntityNameTranslation(contentType)
		).put(
			"lastEdited", format.format(ctEntry.getModifiedDate())
		).put(
			"site",
			CTDefinitionRegistryUtil.getVersionEntitySiteName(
				ctEntry.getModelClassNameId(), ctEntry.getModelClassPK())
		).put(
			"title",
			CTDefinitionRegistryUtil.getVersionEntityTitle(
				ctEntry.getModelClassNameId(), ctEntry.getModelClassPK())
		).put(
			"userName", ctEntry.getUserName()
		).put(
			"version",
			String.valueOf(
				CTDefinitionRegistryUtil.getVersionEntityVersion(
					ctEntry.getModelClassNameId(), ctEntry.getModelClassPK()))
		);
	}

	private String _getEntityNameTranslation(String contentType)
		throws Exception {

		JSONArray entityNameTranslationsJSONArray =
			_getEntityNameTranslationsJSONArray();

		for (int i = 0; i < entityNameTranslationsJSONArray.length(); i++) {
			JSONObject entityNameTranslationJSONObject =
				entityNameTranslationsJSONArray.getJSONObject(i);

			if (contentType.equals(
					entityNameTranslationJSONObject.getString("key"))) {

				return entityNameTranslationJSONObject.getString("translation");
			}
		}

		return StringPool.BLANK;
	}

	private JSONArray _getEntityNameTranslationsJSONArray() throws Exception {
		if (_entityNameTranslationsJSONArray != null) {
			return _entityNameTranslationsJSONArray;
		}

		_entityNameTranslationsJSONArray = JSONUtil.toJSONArray(
			CTDefinitionRegistryUtil.getContentTypeLanguageKeys(),
			contentTypeLanguageKey -> JSONUtil.put(
				"key", contentTypeLanguageKey
			).put(
				"translation",
				LanguageUtil.get(_httpServletRequest, contentTypeLanguageKey)
			));

		return _entityNameTranslationsJSONArray;
	}

	private String _getFilterByStatus() {
		if (_filterByStatus != null) {
			return _filterByStatus;
		}

		_filterByStatus = ParamUtil.getString(
			_httpServletRequest, "status", "all");

		return _filterByStatus;
	}

	private List<DropdownItem> _getFilterStatusDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setActive(
							Objects.equals(_getFilterByStatus(), "all"));
						dropdownItem.setHref(_getPortletURL(), "status", "all");
						dropdownItem.setLabel(
							LanguageUtil.get(_httpServletRequest, "all"));
					});
				add(
					dropdownItem -> {
						dropdownItem.setActive(
							Objects.equals(_getFilterByStatus(), "active"));
						dropdownItem.setHref(
							_getPortletURL(), "status", "active");
						dropdownItem.setLabel(
							LanguageUtil.get(_httpServletRequest, "active"));
					});
			}
		};
	}

	private List<DropdownItem> _getFilterUserDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setHref(_getPortletURL());
						dropdownItem.setLabel(
							LanguageUtil.get(_httpServletRequest, "all"));
					});
			}
		};
	}

	private PortletURL _getIteratorURL() {
		PortletURL currentURL = PortletURLUtil.getCurrent(
			_renderRequest, _renderResponse);

		PortletURL iteratorURL = _renderResponse.createRenderURL();

		iteratorURL.setParameter("mvcPath", "/view.jsp");
		iteratorURL.setParameter("redirect", currentURL.toString());
		iteratorURL.setParameter("displayStyle", getDisplayStyle());
		iteratorURL.setParameter("select", "true");

		return iteratorURL;
	}

	private String _getOrderByCol() {
		if (_orderByCol != null) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(
			_httpServletRequest, "orderByCol", "name");

		return _orderByCol;
	}

	private List<DropdownItem> _getOrderByDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setActive(
							Objects.equals(_getOrderByCol(), "modifiedDate"));
						dropdownItem.setHref(
							_getPortletURL(), "orderByCol", "modifiedDate");
						dropdownItem.setLabel(
							LanguageUtil.get(
								_httpServletRequest, "modified-date"));
					});
				add(
					dropdownItem -> {
						dropdownItem.setActive(
							Objects.equals(_getOrderByCol(), "name"));
						dropdownItem.setHref(
							_getPortletURL(), "orderByCol", "name");
						dropdownItem.setLabel(
							LanguageUtil.get(_httpServletRequest, "name"));
					});
			}
		};
	}

	private PortletURL _getPortletURL() {
		PortletURL portletURL = PortletURLFactoryUtil.create(
			_renderRequest, CTPortletKeys.CHANGE_LISTS,
			PortletRequest.RENDER_PHASE);

		String displayStyle = ParamUtil.getString(
			_httpServletRequest, "displayStyle");

		if (Validator.isNotNull(displayStyle)) {
			portletURL.setParameter("displayStyle", getDisplayStyle());
		}

		String orderByCol = _getOrderByCol();

		if (Validator.isNotNull(orderByCol)) {
			portletURL.setParameter("orderByCol", orderByCol);
		}

		String orderByType = getOrderByType();

		if (Validator.isNotNull(orderByType)) {
			portletURL.setParameter("orderByType", orderByType);
		}

		portletURL.setParameter("select", "true");

		return portletURL;
	}

	private final CTCollectionLocalService _ctCollectionLocalService;
	private final CTEngineManager _ctEngineManager;
	private final CTEntryLocalService _ctEntryLocalService;
	private final CTPreferencesLocalService _ctPreferencesLocalService;
	private String _displayStyle;
	private JSONArray _entityNameTranslationsJSONArray;
	private String _filterByStatus;
	private final HttpServletRequest _httpServletRequest;
	private String _orderByCol;
	private String _orderByType;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;

}