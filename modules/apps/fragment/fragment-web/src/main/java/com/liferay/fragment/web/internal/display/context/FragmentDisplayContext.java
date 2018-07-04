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

package com.liferay.fragment.web.internal.display.context;

import com.liferay.fragment.constants.FragmentActionKeys;
import com.liferay.fragment.constants.FragmentPortletKeys;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.FragmentCollectionLocalServiceUtil;
import com.liferay.fragment.service.FragmentEntryLocalServiceUtil;
import com.liferay.fragment.service.FragmentEntryServiceUtil;
import com.liferay.fragment.web.internal.configuration.FragmentPortletConfiguration;
import com.liferay.fragment.web.internal.constants.FragmentWebKeys;
import com.liferay.fragment.web.internal.security.permission.resource.FragmentPermission;
import com.liferay.fragment.web.util.FragmentPortletUtil;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType;
import com.liferay.item.selector.criteria.upload.criterion.UploadItemSelectorCriterion;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadServletRequestConfigurationHelperUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class FragmentDisplayContext {

	public FragmentDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		HttpServletRequest request) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_request = request;

		_fragmentPortletConfiguration =
			(FragmentPortletConfiguration)_request.getAttribute(
				FragmentPortletConfiguration.class.getName());

		_itemSelector = (ItemSelector)request.getAttribute(
			FragmentWebKeys.ITEM_SELECTOR);
	}

	public List<DropdownItem> getActionDropdownItems() {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setHref(
							_renderResponse.createRenderURL(),
							"mvcRenderCommandName",
							"/fragment/edit_fragment_collection", "redirect",
							themeDisplay.getURLCurrent());
						dropdownItem.setLabel(
							LanguageUtil.get(_request, "collection"));
					});

				add(
					dropdownItem -> {
						dropdownItem.putData("action", "openImportView");
						dropdownItem.setLabel(
							LanguageUtil.get(_request, "import"));
					});
			}
		};
	}

	public List<DropdownItem> getCollectionsDropdownItems() {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.putData("action", "exportCollections");
						dropdownItem.setLabel(
							LanguageUtil.get(_request, "export"));
					});

				if (FragmentPermission.contains(
						themeDisplay.getPermissionChecker(),
						themeDisplay.getScopeGroupId(),
						FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES)) {

					add(
						dropdownItem -> {
							dropdownItem.putData("action", "openImportView");
							dropdownItem.setLabel(
								LanguageUtil.get(_request, "import"));
						});

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

	public String getCssContent() {
		if (Validator.isNotNull(_cssContent)) {
			return _cssContent;
		}

		_cssContent = ParamUtil.getString(_request, "cssContent");

		FragmentEntry fragmentEntry = getFragmentEntry();

		if ((fragmentEntry != null) && Validator.isNull(_cssContent)) {
			_cssContent = fragmentEntry.getCss();

			if (Validator.isNull(_cssContent)) {
				StringBundler sb = new StringBundler(3);

				sb.append(".fragment_");
				sb.append(fragmentEntry.getFragmentEntryId());
				sb.append(" {\n}");

				_cssContent = sb.toString();
			}
		}

		return _cssContent;
	}

	public FragmentCollection getFragmentCollection() {
		if (_fragmentCollection != null) {
			return _fragmentCollection;
		}

		_fragmentCollection =
			FragmentCollectionLocalServiceUtil.fetchFragmentCollection(
				getFragmentCollectionId());

		return _fragmentCollection;
	}

	public long getFragmentCollectionId() {
		if (Validator.isNotNull(_fragmentCollectionId)) {
			return _fragmentCollectionId;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long defaultFragmentCollectionId = 0;

		List<FragmentCollection> fragmentCollections =
			FragmentCollectionLocalServiceUtil.getFragmentCollections(
				themeDisplay.getScopeGroupId(), 0, 1);

		if (ListUtil.isNotEmpty(fragmentCollections)) {
			FragmentCollection fragmentCollection = fragmentCollections.get(0);

			defaultFragmentCollectionId =
				fragmentCollection.getFragmentCollectionId();
		}

		_fragmentCollectionId = ParamUtil.getLong(
			_request, "fragmentCollectionId", defaultFragmentCollectionId);

		return _fragmentCollectionId;
	}

	public SearchContainer getFragmentEntriesSearchContainer() {
		if (_fragmentEntriesSearchContainer != null) {
			return _fragmentEntriesSearchContainer;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		SearchContainer fragmentEntriesSearchContainer = new SearchContainer(
			_renderRequest, _getPortletURL(), null, "there-are-no-fragments");

		fragmentEntriesSearchContainer.setRowChecker(
			new EmptyOnClickRowChecker(_renderResponse));

		OrderByComparator<FragmentEntry> orderByComparator =
			FragmentPortletUtil.getFragmentEntryOrderByComparator(
				_getOrderByCol(), getOrderByType());

		fragmentEntriesSearchContainer.setOrderByCol(_getOrderByCol());
		fragmentEntriesSearchContainer.setOrderByComparator(orderByComparator);
		fragmentEntriesSearchContainer.setOrderByType(getOrderByType());

		List<FragmentEntry> fragmentEntries = null;
		int fragmentEntriesCount = 0;

		if (_isSearch()) {
			fragmentEntries = FragmentEntryServiceUtil.getFragmentEntries(
				themeDisplay.getScopeGroupId(), getFragmentCollectionId(),
				_getKeywords(), fragmentEntriesSearchContainer.getStart(),
				fragmentEntriesSearchContainer.getEnd(), orderByComparator);

			fragmentEntriesCount =
				FragmentEntryServiceUtil.getFragmentCollectionsCount(
					themeDisplay.getScopeGroupId(), getFragmentCollectionId(),
					_getKeywords());
		}
		else {
			fragmentEntries = FragmentEntryServiceUtil.getFragmentEntries(
				themeDisplay.getScopeGroupId(), getFragmentCollectionId(),
				fragmentEntriesSearchContainer.getStart(),
				fragmentEntriesSearchContainer.getEnd(), orderByComparator);

			fragmentEntriesCount =
				FragmentEntryServiceUtil.getFragmentCollectionsCount(
					themeDisplay.getScopeGroupId(), getFragmentCollectionId());
		}

		fragmentEntriesSearchContainer.setResults(fragmentEntries);
		fragmentEntriesSearchContainer.setTotal(fragmentEntriesCount);

		_fragmentEntriesSearchContainer = fragmentEntriesSearchContainer;

		return _fragmentEntriesSearchContainer;
	}

	public FragmentEntry getFragmentEntry() {
		if (_fragmentEntry != null) {
			return _fragmentEntry;
		}

		_fragmentEntry = FragmentEntryLocalServiceUtil.fetchFragmentEntry(
			getFragmentEntryId());

		return _fragmentEntry;
	}

	public List<DropdownItem> getFragmentEntryActionItemsDropdownItems() {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.putData(
							"action", "exportSelectedFragmentEntries");
						dropdownItem.setIcon("import-export");
						dropdownItem.setLabel(
							LanguageUtil.get(_request, "export"));
						dropdownItem.setQuickAction(true);
					});

				if (FragmentPermission.contains(
						themeDisplay.getPermissionChecker(),
						themeDisplay.getScopeGroupId(),
						FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES)) {

					add(
						dropdownItem -> {
							dropdownItem.putData(
								"action", "deleteSelectedFragmentEntries");
							dropdownItem.setIcon("trash");
							dropdownItem.setLabel(
								LanguageUtil.get(_request, "delete"));
							dropdownItem.setQuickAction(true);
						});
				}
			}
		};
	}

	public String getFragmentEntryClearResultsURL() {
		PortletURL clearResultsURL = _getPortletURL();

		clearResultsURL.setParameter("keywords", StringPool.BLANK);

		return clearResultsURL.toString();
	}

	public List<DropdownItem> getFragmentEntryFilterItemsDropdownItems() {
		return new DropdownItemList() {
			{
				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(
							_getFragmentEntryFilterNavigationDropdownItems());
						dropdownGroupItem.setLabel(
							LanguageUtil.get(_request, "filter-by-navigation"));
					});

				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(
							_getFragmentEntryOrderByDropdownItems());
						dropdownGroupItem.setLabel(
							LanguageUtil.get(_request, "order-by"));
					});
			}
		};
	}

	public long getFragmentEntryId() {
		if (Validator.isNotNull(_fragmentEntryId)) {
			return _fragmentEntryId;
		}

		_fragmentEntryId = ParamUtil.getLong(_request, "fragmentEntryId");

		return _fragmentEntryId;
	}

	public String getFragmentEntrySearchActionURL() {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletURL searchActionURL = _renderResponse.createRenderURL();

		searchActionURL.setParameter("mvcRenderCommandName", "/fragment/view");
		searchActionURL.setParameter("redirect", themeDisplay.getURLCurrent());
		searchActionURL.setParameter(
			"fragmentCollectionId", String.valueOf(getFragmentCollectionId()));

		return searchActionURL.toString();
	}

	public String getFragmentEntrySortingURL() {
		PortletURL sortingURL = _getPortletURL();

		sortingURL.setParameter(
			"orderByType",
			Objects.equals(getOrderByType(), "asc") ? "desc" : "asc");

		return sortingURL.toString();
	}

	public String getFragmentEntryTitle() {
		FragmentEntry fragmentEntry = getFragmentEntry();

		if (fragmentEntry == null) {
			return LanguageUtil.get(_request, "add-fragment");
		}

		return fragmentEntry.getName();
	}

	public int getFragmentEntryTotalItems() {
		SearchContainer fragmentEntriesSearchContainer =
			getFragmentEntriesSearchContainer();

		return fragmentEntriesSearchContainer.getTotal();
	}

	public String getHtmlContent() {
		if (Validator.isNotNull(_htmlContent)) {
			return _htmlContent;
		}

		_htmlContent = ParamUtil.getString(_request, "htmlContent");

		FragmentEntry fragmentEntry = getFragmentEntry();

		if ((fragmentEntry != null) && Validator.isNull(_htmlContent)) {
			_htmlContent = fragmentEntry.getHtml();

			if (Validator.isNull(_htmlContent)) {
				StringBundler sb = new StringBundler(3);

				sb.append("<div class=\"fragment_");
				sb.append(fragmentEntry.getFragmentEntryId());
				sb.append("\">\n</div>");

				_htmlContent = sb.toString();
			}
		}

		return _htmlContent;
	}

	public PortletURL getItemSelectorURL() {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletURL uploadURL = _renderResponse.createActionURL();

		uploadURL.setParameter(
			ActionRequest.ACTION_NAME,
			"/fragment/upload_fragment_entry_preview");

		ItemSelectorCriterion uploadItemSelectorCriterion =
			new UploadItemSelectorCriterion(
				FragmentPortletKeys.FRAGMENT, uploadURL.toString(),
				LanguageUtil.get(themeDisplay.getLocale(), "fragments"),
				UploadServletRequestConfigurationHelperUtil.getMaxSize(),
				_fragmentPortletConfiguration.thumbnailExtensions());

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

	public String getJsContent() {
		if (Validator.isNotNull(_jsContent)) {
			return _jsContent;
		}

		_jsContent = ParamUtil.getString(_request, "jsContent");

		FragmentEntry fragmentEntry = getFragmentEntry();

		if ((fragmentEntry != null) && Validator.isNull(_jsContent)) {
			_jsContent = fragmentEntry.getJs();
		}

		return _jsContent;
	}

	public String getName() {
		if (Validator.isNotNull(_name)) {
			return _name;
		}

		_name = ParamUtil.getString(_request, "name");

		FragmentEntry fragmentEntry = getFragmentEntry();

		if ((fragmentEntry != null) && Validator.isNull(_name)) {
			_name = fragmentEntry.getName();
		}

		return _name;
	}

	public String getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(_request, "orderByType", "asc");

		return _orderByType;
	}

	public String getRedirect() {
		String redirect = ParamUtil.getString(_request, "redirect");

		if (Validator.isNull(redirect)) {
			PortletURL portletURL = _renderResponse.createRenderURL();

			portletURL.setParameter("mvcRenderCommandName", "/fragment/view");

			if (getFragmentCollectionId() > 0) {
				portletURL.setParameter(
					"fragmentCollectionId",
					String.valueOf(getFragmentCollectionId()));
			}

			redirect = portletURL.toString();
		}

		return redirect;
	}

	public long getRenderLayoutPlid() {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout renderLayout = LayoutLocalServiceUtil.fetchFirstLayout(
			themeDisplay.getScopeGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

		if (renderLayout != null) {
			return renderLayout.getPlid();
		}

		renderLayout = LayoutLocalServiceUtil.fetchFirstLayout(
			themeDisplay.getScopeGroupId(), true,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

		if (renderLayout != null) {
			return renderLayout.getPlid();
		}

		return themeDisplay.getPlid();
	}

	public boolean isDisabledFragmentEntriesManagementBar() {
		if (_hasFragmentEntriesResults()) {
			return false;
		}

		if (_isSearch()) {
			return false;
		}

		return true;
	}

	private List<DropdownItem>
		_getFragmentEntryFilterNavigationDropdownItems() {

		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setActive(true);
						dropdownItem.setHref(_getPortletURL());
						dropdownItem.setLabel(
							LanguageUtil.get(_request, "all"));
					});
			}
		};
	}

	private List<DropdownItem> _getFragmentEntryOrderByDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setActive(
							Objects.equals(_getOrderByCol(), "name"));
						dropdownItem.setHref(
							_getPortletURL(), "orderByCol", "name");
						dropdownItem.setLabel(
							LanguageUtil.get(_request, "name"));
					});

				add(
					dropdownItem -> {
						dropdownItem.setActive(
							Objects.equals(_getOrderByCol(), "create-date"));
						dropdownItem.setHref(
							_getPortletURL(), "orderByCol", "create-date");
						dropdownItem.setLabel(
							LanguageUtil.get(_request, "create-date"));
					});
			}
		};
	}

	private String _getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_request, "keywords");

		return _keywords;
	}

	private String _getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(
			_request, "orderByCol", "create-date");

		return _orderByCol;
	}

	private PortletURL _getPortletURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("mvcRenderCommandName", "/fragment/view");
		portletURL.setParameter(
			"fragmentCollectionId", String.valueOf(getFragmentCollectionId()));

		String keywords = _getKeywords();

		if (Validator.isNotNull(keywords)) {
			portletURL.setParameter("keywords", keywords);
		}

		String orderByCol = _getOrderByCol();

		if (Validator.isNotNull(orderByCol)) {
			portletURL.setParameter("orderByCol", orderByCol);
		}

		String orderByType = getOrderByType();

		if (Validator.isNotNull(orderByType)) {
			portletURL.setParameter("orderByType", orderByType);
		}

		return portletURL;
	}

	private boolean _hasFragmentEntriesResults() {
		SearchContainer searchContainer = getFragmentEntriesSearchContainer();

		if (searchContainer.getTotal() > 0) {
			return true;
		}

		return false;
	}

	private boolean _isSearch() {
		if (Validator.isNotNull(_getKeywords())) {
			return true;
		}

		return false;
	}

	private String _cssContent;
	private FragmentCollection _fragmentCollection;
	private Long _fragmentCollectionId;
	private SearchContainer _fragmentEntriesSearchContainer;
	private FragmentEntry _fragmentEntry;
	private Long _fragmentEntryId;
	private final FragmentPortletConfiguration _fragmentPortletConfiguration;
	private String _htmlContent;
	private final ItemSelector _itemSelector;
	private String _jsContent;
	private String _keywords;
	private String _name;
	private String _orderByCol;
	private String _orderByType;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final HttpServletRequest _request;

}