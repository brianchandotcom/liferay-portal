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

package com.liferay.layout.admin.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType;
import com.liferay.item.selector.criteria.upload.criterion.UploadItemSelectorCriterion;
import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.layout.admin.web.internal.configuration.LayoutAdminWebConfiguration;
import com.liferay.layout.admin.web.internal.constants.LayoutAdminWebKeys;
import com.liferay.layout.admin.web.internal.security.permission.resource.LayoutPageTemplateCollectionPermission;
import com.liferay.layout.admin.web.internal.security.permission.resource.LayoutPageTemplatePermission;
import com.liferay.layout.admin.web.internal.util.LayoutPageTemplatePortletUtil;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionServiceUtil;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.LayoutPrototypeServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadServletRequestConfigurationHelperUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class LayoutPageTemplateDisplayContext {

	public LayoutPageTemplateDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		HttpServletRequest request) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_request = request;

		_itemSelector = (ItemSelector)request.getAttribute(
			LayoutAdminWebKeys.ITEM_SELECTOR);
		_layoutAdminWebConfiguration =
			(LayoutAdminWebConfiguration)_renderRequest.getAttribute(
				LayoutAdminWebConfiguration.class.getName());
		_themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<DropdownItem> geLayoutPageTemplateEntriesActionDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.putData(
							"action", "deleteLayoutPageTemplateEntries");
						dropdownItem.setIcon("times-circle");
						dropdownItem.setLabel(
							LanguageUtil.get(_request, "delete"));
						dropdownItem.setQuickAction(true);
					});
			}
		};
	}

	public List<DropdownItem> getActionDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setHref(
							_renderResponse.createRenderURL(),
							"mvcRenderCommandName",
							"/layout/edit_layout_page_template_collection",
							"redirect", _themeDisplay.getURLCurrent());
						dropdownItem.setLabel(
							LanguageUtil.get(_request, "new"));
					});
			}
		};
	}

	public String getClearResultsURL() {
		PortletURL clearResultsURL = getPortletURL();

		clearResultsURL.setParameter("keywords", StringPool.BLANK);

		return clearResultsURL.toString();
	}

	public List<DropdownItem> getCollectionsDropdownItems() throws Exception {
		return new DropdownItemList() {
			{
				if (LayoutPageTemplateCollectionPermission.contains(
						_themeDisplay.getPermissionChecker(),
						getLayoutPageTemplateCollectionId(),
						ActionKeys.DELETE)) {

					add(
						dropdownItem -> {
							dropdownItem.putData("action", "deleteCollections");
							dropdownItem.setLabel(
								LanguageUtil.get(_request, "delete"));
						});
				}
			}
		};
	}

	public CreationMenu getCreationMenu() {
		return new CreationMenu() {
			{
				addPrimaryDropdownItem(
					dropdownItem -> {
						dropdownItem.putData(
							"action", "addLayoutPageTemplateEntry");
						dropdownItem.putData(
							"addPageTemplateURL",
							_getAddLayoutPageTemplateEntryURL());
						dropdownItem.setHref("#");
						dropdownItem.setLabel(
							LanguageUtil.get(
								_request, "content-page-template"));
					});

				addPrimaryDropdownItem(
					dropdownItem -> {
						dropdownItem.putData(
							"action", "addLayoutPageTemplateEntry");
						dropdownItem.putData(
							"addPageTemplateURL", _getAddLayoutPrototypeURL());
						dropdownItem.setHref("#");
						dropdownItem.setLabel(
							LanguageUtil.get(_request, "widget-page-template"));
					});
			}
		};
	}

	public String getEditLayoutPageTemplateEntryURL(
			LayoutPageTemplateEntry layoutPageTemplateEntry)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (Objects.equals(
				layoutPageTemplateEntry.getType(),
				LayoutPageTemplateEntryTypeConstants.TYPE_WIDGET_PAGE)) {

			LayoutPrototype layoutPrototype = getLayoutPrototype(
				layoutPageTemplateEntry);

			if (layoutPrototype == null) {
				return null;
			}

			Group layoutPrototypeGroup = layoutPrototype.getGroup();

			return layoutPrototypeGroup.getDisplayURL(themeDisplay, true);
		}

		PortletURL editLayoutPageTemplateEntryURL =
			_renderResponse.createRenderURL();

		editLayoutPageTemplateEntryURL.setParameter(
			"mvcRenderCommandName", "/layout/edit_layout_page_template_entry");
		editLayoutPageTemplateEntryURL.setParameter(
			"redirect", themeDisplay.getURLCurrent());
		editLayoutPageTemplateEntryURL.setParameter(
			"layoutPageTemplateEntryId",
			String.valueOf(
				layoutPageTemplateEntry.getLayoutPageTemplateEntryId()));
		editLayoutPageTemplateEntryURL.setParameter(
			"layoutPageTemplateCollectionId",
			String.valueOf(
				layoutPageTemplateEntry.getLayoutPageTemplateCollectionId()));

		return editLayoutPageTemplateEntryURL.toString();
	}

	public List<DropdownItem> getFilterDropdownItems() {
		return new DropdownItemList() {
			{
				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(
							_getFilterNavigationDropdownItems());
						dropdownGroupItem.setLabel(
							LanguageUtil.get(_request, "filter-by-navigation"));
					});

				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(
							_getOrderByDropdownItems());
						dropdownGroupItem.setLabel(
							LanguageUtil.get(_request, "order-by"));
					});
			}
		};
	}

	public PortletURL getItemSelectorURL(long layoutPageTemplateEntryId) {
		PortletURL uploadURL = _renderResponse.createActionURL();

		uploadURL.setParameter(
			ActionRequest.ACTION_NAME,
			"/layout/upload_layout_page_template_entry_preview");
		uploadURL.setParameter(
			"layoutPageTemplateEntryId",
			String.valueOf(layoutPageTemplateEntryId));

		ItemSelectorCriterion uploadItemSelectorCriterion =
			new UploadItemSelectorCriterion(
				LayoutAdminPortletKeys.GROUP_PAGES, uploadURL.toString(),
				LanguageUtil.get(_themeDisplay.getLocale(), "page-template"),
				UploadServletRequestConfigurationHelperUtil.getMaxSize(),
				_layoutAdminWebConfiguration.thumbnailExtensions());

		List<ItemSelectorReturnType> uploadDesiredItemSelectorReturnTypes =
			new ArrayList<>();

		uploadDesiredItemSelectorReturnTypes.add(
			new FileEntryItemSelectorReturnType());

		uploadItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			uploadDesiredItemSelectorReturnTypes);

		return _itemSelector.getItemSelectorURL(
			RequestBackedPortletURLFactoryUtil.create(_request),
			_renderResponse.getNamespace() + "changePreview",
			uploadItemSelectorCriterion);
	}

	public String getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_request, "keywords");

		return _keywords;
	}

	public LayoutPageTemplateCollection getLayoutPageTemplateCollection()
		throws PortalException {

		if (_layoutPageTemplateCollection != null) {
			return _layoutPageTemplateCollection;
		}

		_layoutPageTemplateCollection =
			LayoutPageTemplateCollectionServiceUtil.
				fetchLayoutPageTemplateCollection(
					getLayoutPageTemplateCollectionId());

		return _layoutPageTemplateCollection;
	}

	public long getLayoutPageTemplateCollectionId() {
		if (Validator.isNotNull(_layoutPageTemplateCollectionId)) {
			return _layoutPageTemplateCollectionId;
		}

		long defaultLayoutPageTemplateCollectionId = 0;

		List<LayoutPageTemplateCollection> layoutPageTemplateCollections =
			getLayoutPageTemplateCollections();

		if (ListUtil.isNotEmpty(layoutPageTemplateCollections)) {
			LayoutPageTemplateCollection layoutPageTemplateCollection =
				layoutPageTemplateCollections.get(0);

			defaultLayoutPageTemplateCollectionId =
				layoutPageTemplateCollection.
					getLayoutPageTemplateCollectionId();
		}

		_layoutPageTemplateCollectionId = ParamUtil.getLong(
			_request, "layoutPageTemplateCollectionId",
			defaultLayoutPageTemplateCollectionId);

		return _layoutPageTemplateCollectionId;
	}

	public List<LayoutPageTemplateCollection>
		getLayoutPageTemplateCollections() {

		if (_layoutPageTemplateCollections != null) {
			return _layoutPageTemplateCollections;
		}

		_layoutPageTemplateCollections =
			LayoutPageTemplateCollectionServiceUtil.
				getLayoutPageTemplateCollections(
					_themeDisplay.getScopeGroupId());

		return _layoutPageTemplateCollections;
	}

	public SearchContainer getLayoutPageTemplateEntriesSearchContainer() {
		if (_layoutPageTemplateEntriesSearchContainer != null) {
			return _layoutPageTemplateEntriesSearchContainer;
		}

		SearchContainer layoutPageTemplateEntriesSearchContainer =
			new SearchContainer(
				_renderRequest, _renderResponse.createRenderURL(), null,
				"there-are-no-page-templates");

		layoutPageTemplateEntriesSearchContainer.setRowChecker(
			new EmptyOnClickRowChecker(_renderResponse));

		layoutPageTemplateEntriesSearchContainer.setOrderByCol(getOrderByCol());

		OrderByComparator<LayoutPageTemplateEntry> orderByComparator =
			LayoutPageTemplatePortletUtil.
				getLayoutPageTemplateEntryOrderByComparator(
					getOrderByCol(), getOrderByType());

		layoutPageTemplateEntriesSearchContainer.setOrderByComparator(
			orderByComparator);

		layoutPageTemplateEntriesSearchContainer.setOrderByType(
			getOrderByType());

		List<LayoutPageTemplateEntry> layoutPageTemplateEntries = null;
		int layoutPageTemplateEntriesCount = 0;

		if (isSearch()) {
			layoutPageTemplateEntries =
				LayoutPageTemplateEntryServiceUtil.getLayoutPageTemplateEntries(
					_themeDisplay.getScopeGroupId(),
					getLayoutPageTemplateCollectionId(), getKeywords(),
					layoutPageTemplateEntriesSearchContainer.getStart(),
					layoutPageTemplateEntriesSearchContainer.getEnd(),
					orderByComparator);

			layoutPageTemplateEntriesCount =
				LayoutPageTemplateEntryServiceUtil.
					getLayoutPageTemplateEntriesCount(
						_themeDisplay.getScopeGroupId(),
						getLayoutPageTemplateCollectionId(), getKeywords());
		}
		else {
			layoutPageTemplateEntries =
				LayoutPageTemplateEntryServiceUtil.getLayoutPageTemplateEntries(
					_themeDisplay.getScopeGroupId(),
					getLayoutPageTemplateCollectionId(),
					layoutPageTemplateEntriesSearchContainer.getStart(),
					layoutPageTemplateEntriesSearchContainer.getEnd(),
					orderByComparator);

			layoutPageTemplateEntriesCount =
				LayoutPageTemplateEntryServiceUtil.
					getLayoutPageTemplateEntriesCount(
						_themeDisplay.getScopeGroupId(),
						getLayoutPageTemplateCollectionId());
		}

		layoutPageTemplateEntriesSearchContainer.setResults(
			layoutPageTemplateEntries);
		layoutPageTemplateEntriesSearchContainer.setTotal(
			layoutPageTemplateEntriesCount);

		_layoutPageTemplateEntriesSearchContainer =
			layoutPageTemplateEntriesSearchContainer;

		return _layoutPageTemplateEntriesSearchContainer;
	}

	public LayoutPageTemplateEntry getLayoutPageTemplateEntry()
		throws PortalException {

		if (_layoutPageTemplateEntry != null) {
			return _layoutPageTemplateEntry;
		}

		_layoutPageTemplateEntry =
			LayoutPageTemplateEntryServiceUtil.fetchLayoutPageTemplateEntry(
				getLayoutPageTemplateEntryId());

		return _layoutPageTemplateEntry;
	}

	public long getLayoutPageTemplateEntryId() {
		if (Validator.isNotNull(_layoutPageTemplateEntryId)) {
			return _layoutPageTemplateEntryId;
		}

		_layoutPageTemplateEntryId = ParamUtil.getLong(
			_request, "layoutPageTemplateEntryId");

		return _layoutPageTemplateEntryId;
	}

	public String getLayoutPageTemplateEntryTitle() throws PortalException {
		LayoutPageTemplateEntry layoutPageTemplateEntry =
			getLayoutPageTemplateEntry();

		if (layoutPageTemplateEntry == null) {
			return LanguageUtil.get(_request, "add-page-template");
		}

		return layoutPageTemplateEntry.getName();
	}

	public LayoutPrototype getLayoutPrototype(
			LayoutPageTemplateEntry layoutPageTemplateEntry)
		throws PortalException {

		return LayoutPrototypeServiceUtil.fetchLayoutPrototype(
			layoutPageTemplateEntry.getLayoutPrototypeId());
	}

	public String getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(
			_request, "orderByCol", "create-date");

		return _orderByCol;
	}

	public String getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(_request, "orderByType", "asc");

		return _orderByType;
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("tabs1", "page-templates");
		portletURL.setParameter("redirect", _themeDisplay.getURLCurrent());

		long layoutPageTemplateCollectionId =
			getLayoutPageTemplateCollectionId();

		if (layoutPageTemplateCollectionId > 0) {
			portletURL.setParameter(
				"layoutPageTemplateCollectionId",
				String.valueOf(layoutPageTemplateCollectionId));
		}

		String keywords = getKeywords();

		if (Validator.isNotNull(keywords)) {
			portletURL.setParameter("keywords", keywords);
		}

		String orderByCol = getOrderByCol();

		if (Validator.isNotNull(orderByCol)) {
			portletURL.setParameter("orderByCol", orderByCol);
		}

		String orderByType = getOrderByType();

		if (Validator.isNotNull(orderByType)) {
			portletURL.setParameter("orderByType", orderByType);
		}

		return portletURL;
	}

	public String getSearchActionURL() {
		PortletURL searchActionURL = getPortletURL();

		return searchActionURL.toString();
	}

	public String getSortingURL() {
		PortletURL sortingURL = getPortletURL();

		sortingURL.setParameter(
			"orderByType",
			Objects.equals(getOrderByType(), "asc") ? "desc" : "asc");

		return sortingURL.toString();
	}

	public int getTotalItems() {
		SearchContainer layoutPageTemplateEntriesSearchContainer =
			getLayoutPageTemplateEntriesSearchContainer();

		return layoutPageTemplateEntriesSearchContainer.getTotal();
	}

	public Map<String, Object> getUpdateLayoutPageTemplateEntryData(
			LayoutPageTemplateEntry layoutPageTemplateEntry)
		throws PortalException {

		String formSubmitURL = _getUpdateLayoutPageTemplateEntryURL(
			layoutPageTemplateEntry);
		String idFieldName = "layoutPageTemplateEntryId";
		long idFieldValue =
			layoutPageTemplateEntry.getLayoutPageTemplateEntryId();

		if (Objects.equals(
				layoutPageTemplateEntry.getType(),
				LayoutPageTemplateEntryTypeConstants.TYPE_WIDGET_PAGE)) {

			LayoutPrototype layoutPrototype = getLayoutPrototype(
				layoutPageTemplateEntry);

			if (layoutPrototype == null) {
				return null;
			}

			formSubmitURL = _getUpdateLayoutPrototypeURL(layoutPrototype);
			idFieldName = "layoutPrototypeId";
			idFieldValue = layoutPrototype.getLayoutPrototypeId();
		}

		Map<String, Object> updateLayoutPageTemplateEntryData = new HashMap<>();

		updateLayoutPageTemplateEntryData.put("form-submit-url", formSubmitURL);
		updateLayoutPageTemplateEntryData.put("id-field-name", idFieldName);
		updateLayoutPageTemplateEntryData.put("id-field-value", idFieldValue);
		updateLayoutPageTemplateEntryData.put(
			"main-field-value", layoutPageTemplateEntry.getName());

		return updateLayoutPageTemplateEntryData;
	}

	public boolean isDisabledLayoutPageTemplateEntriesManagementBar() {
		if (_hasLayoutPageTemplateEntriesResults()) {
			return false;
		}

		return true;
	}

	public boolean isDisplayPage() throws PortalException {
		LayoutPageTemplateEntry layoutPageTemplateEntry =
			getLayoutPageTemplateEntry();

		if (Objects.equals(
				layoutPageTemplateEntry.getType(),
				LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE)) {

			return true;
		}

		return false;
	}

	public boolean isSearch() {
		if (Validator.isNotNull(getKeywords())) {
			return true;
		}

		return false;
	}

	public boolean isShowAddButton(String actionId) {
		if (LayoutPageTemplatePermission.contains(
				_themeDisplay.getPermissionChecker(),
				_themeDisplay.getSiteGroupId(), actionId)) {

			return true;
		}

		return false;
	}

	public boolean isShowLayoutPageTemplateEntriesSearch() {
		if (_hasLayoutPageTemplateEntriesResults()) {
			return true;
		}

		if (isSearch()) {
			return true;
		}

		return false;
	}

	private String _getAddLayoutPageTemplateEntryURL() {
		PortletURL actionURL = _renderResponse.createActionURL();

		actionURL.setParameter(
			ActionRequest.ACTION_NAME,
			"/layout/add_layout_page_template_entry");
		actionURL.setParameter(
			"mvcRenderCommandName", "/layout/edit_layout_page_template_entry");
		actionURL.setParameter("redirect", _themeDisplay.getURLCurrent());
		actionURL.setParameter(
			"layoutPageTemplateCollectionId",
			String.valueOf(getLayoutPageTemplateCollectionId()));

		return actionURL.toString();
	}

	private String _getAddLayoutPrototypeURL() {
		PortletURL actionURL = _renderResponse.createActionURL();

		actionURL.setParameter(
			ActionRequest.ACTION_NAME,
			"/layout_prototype/add_layout_prototype");
		actionURL.setParameter(
			"layoutPageTemplateCollectionId",
			String.valueOf(getLayoutPageTemplateCollectionId()));

		return actionURL.toString();
	}

	private List<DropdownItem> _getFilterNavigationDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setActive(true);
						dropdownItem.setHref(getPortletURL());
						dropdownItem.setLabel(
							LanguageUtil.get(_request, "all"));
					});
			}
		};
	}

	private List<DropdownItem> _getOrderByDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setActive(
							Objects.equals(getOrderByCol(), "create-date"));
						dropdownItem.setHref(
							getPortletURL(), "orderByCol", "create-date");
						dropdownItem.setLabel(
							LanguageUtil.get(_request, "create-date"));
					});

				add(
					dropdownItem -> {
						dropdownItem.setActive(
							Objects.equals(getOrderByCol(), "name"));
						dropdownItem.setHref(
							getPortletURL(), "orderByCol", "name");
						dropdownItem.setLabel(
							LanguageUtil.get(_request, "name"));
					});
			}
		};
	}

	private String _getUpdateLayoutPageTemplateEntryURL(
		LayoutPageTemplateEntry layoutPageTemplateEntry) {

		PortletURL updateLayoutPageTemplateEntryURL =
			_renderResponse.createActionURL();

		updateLayoutPageTemplateEntryURL.setParameter(
			ActionRequest.ACTION_NAME,
			"/layout/update_layout_page_template_entry");
		updateLayoutPageTemplateEntryURL.setParameter(
			"redirect", _themeDisplay.getURLCurrent());
		updateLayoutPageTemplateEntryURL.setParameter(
			"layoutPageTemplateCollectionId",
			String.valueOf(
				layoutPageTemplateEntry.getLayoutPageTemplateCollectionId()));
		updateLayoutPageTemplateEntryURL.setParameter(
			"layoutPageTemplateEntryId",
			String.valueOf(
				layoutPageTemplateEntry.getLayoutPageTemplateEntryId()));

		return updateLayoutPageTemplateEntryURL.toString();
	}

	private String _getUpdateLayoutPrototypeURL(
		LayoutPrototype layoutPrototype) {

		PortletURL updateLayoutPrototypeURL = _renderResponse.createActionURL();

		updateLayoutPrototypeURL.setParameter(
			ActionRequest.ACTION_NAME,
			"/layout_prototype/update_layout_prototype");
		updateLayoutPrototypeURL.setParameter(
			"redirect", _themeDisplay.getURLCurrent());
		updateLayoutPrototypeURL.setParameter(
			"layoutPrototypeId",
			String.valueOf(layoutPrototype.getLayoutPrototypeId()));

		return updateLayoutPrototypeURL.toString();
	}

	private boolean _hasLayoutPageTemplateEntriesResults() {
		SearchContainer searchContainer =
			getLayoutPageTemplateEntriesSearchContainer();

		if (searchContainer.getTotal() > 0) {
			return true;
		}

		return false;
	}

	private final ItemSelector _itemSelector;
	private String _keywords;
	private final LayoutAdminWebConfiguration _layoutAdminWebConfiguration;
	private LayoutPageTemplateCollection _layoutPageTemplateCollection;
	private Long _layoutPageTemplateCollectionId;
	private List<LayoutPageTemplateCollection> _layoutPageTemplateCollections;
	private SearchContainer _layoutPageTemplateEntriesSearchContainer;
	private LayoutPageTemplateEntry _layoutPageTemplateEntry;
	private Long _layoutPageTemplateEntryId;
	private String _orderByCol;
	private String _orderByType;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final HttpServletRequest _request;
	private final ThemeDisplay _themeDisplay;

}