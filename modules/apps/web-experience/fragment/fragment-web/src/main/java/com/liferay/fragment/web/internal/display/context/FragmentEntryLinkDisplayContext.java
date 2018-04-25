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

import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalServiceUtil;
import com.liferay.fragment.service.FragmentEntryLocalServiceUtil;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItemList;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalServiceUtil;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Objects;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pavel Savinov
 */
public class FragmentEntryLinkDisplayContext {

	public FragmentEntryLinkDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_request = PortalUtil.getHttpServletRequest(renderRequest);
	}

	public List<DropdownItem> getActionItemsDropdownItemList() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setHref(
							"javascript:" + _renderResponse.getNamespace() +
								"propagate();");
						dropdownItem.setIcon("upload");
						dropdownItem.setLabel(
							LanguageUtil.get(_request, "propagate"));
						dropdownItem.setQuickAction(true);
					});
			}
		};
	}

	public int getAllUsageCount() throws PortalException {
		FragmentEntry fragmentEntry = getFragmentEntry();

		return FragmentEntryLinkLocalServiceUtil.getFragmentEntryLinksCount(
			fragmentEntry.getGroupId(), getFragmentEntryId());
	}

	public int getDisplayPagesUsageCount() throws PortalException {
		FragmentEntry fragmentEntry = getFragmentEntry();

		return FragmentEntryLinkLocalServiceUtil.getFragmentEntryLinksCount(
			fragmentEntry.getGroupId(), getFragmentEntryId(),
			PortalUtil.getClassNameId(LayoutPageTemplateEntry.class),
			LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE);
	}

	public List<DropdownItem> getFilterItemsDropdownItems() {
		return new DropdownItemList() {
			{
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

	public long getFragmentCollectionId() {
		if (Validator.isNotNull(_fragmentCollectionId)) {
			return _fragmentCollectionId;
		}

		_fragmentCollectionId = ParamUtil.getLong(
			_renderRequest, "fragmentCollectionId");

		return _fragmentCollectionId;
	}

	public FragmentEntry getFragmentEntry() throws PortalException {
		if (Validator.isNotNull(_fragmentEntry)) {
			return _fragmentEntry;
		}

		_fragmentEntry = FragmentEntryLocalServiceUtil.getFragmentEntry(
			getFragmentEntryId());

		return _fragmentEntry;
	}

	public long getFragmentEntryId() {
		if (Validator.isNotNull(_fragmentEntryId)) {
			return _fragmentEntryId;
		}

		_fragmentEntryId = ParamUtil.getLong(_renderRequest, "fragmentEntryId");

		return _fragmentEntryId;
	}

	public String getFragmentEntryLinkName(FragmentEntryLink fragmentEntryLink)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long classNameId = fragmentEntryLink.getClassNameId();

		if (classNameId == PortalUtil.getClassNameId(Layout.class)) {
			Layout layout = LayoutLocalServiceUtil.getLayout(
				fragmentEntryLink.getClassPK());

			return layout.getName(themeDisplay.getLocale());
		}
		else {
			LayoutPageTemplateEntry layoutPageTemplateEntry =
				LayoutPageTemplateEntryLocalServiceUtil.
					getLayoutPageTemplateEntry(fragmentEntryLink.getClassPK());

			return layoutPageTemplateEntry.getName();
		}
	}

	public String getFragmentEntryLinkTypeLabel(
			FragmentEntryLink fragmentEntryLink)
		throws PortalException {

		if (fragmentEntryLink.getClassNameId() ==
				PortalUtil.getClassNameId(Layout.class)) {

			return "page";
		}

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			LayoutPageTemplateEntryLocalServiceUtil.getLayoutPageTemplateEntry(
				fragmentEntryLink.getClassPK());

		if (layoutPageTemplateEntry.getType() ==
				LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE) {

			return "display-page";
		}

		return "page-template";
	}

	public String getKeywords() {
		if (Validator.isNotNull(_keywords)) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_renderRequest, "keywords", null);

		return _keywords;
	}

	public String getNavigation() {
		if (Validator.isNotNull(_navigation)) {
			return _navigation;
		}

		_navigation = ParamUtil.getString(_renderRequest, "navigation", "all");

		return _navigation;
	}

	public String getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(_renderRequest, "orderByCol", "name");

		return _orderByCol;
	}

	public String getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(
			_renderRequest, "orderByType", "asc");

		return _orderByType;
	}

	public int getPagesUsageCount() throws PortalException {
		FragmentEntry fragmentEntry = getFragmentEntry();

		return FragmentEntryLinkLocalServiceUtil.getFragmentEntryLinksCount(
			fragmentEntry.getGroupId(), getFragmentEntryId(),
			PortalUtil.getClassNameId(Layout.class));
	}

	public int getPageTemplatesUsageCount() throws PortalException {
		FragmentEntry fragmentEntry = getFragmentEntry();

		return FragmentEntryLinkLocalServiceUtil.getFragmentEntryLinksCount(
			fragmentEntry.getGroupId(), getFragmentEntryId(),
			PortalUtil.getClassNameId(LayoutPageTemplateEntry.class),
			LayoutPageTemplateEntryTypeConstants.TYPE_BASIC);
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "/fragment/view_fragment_entry_usages");
		portletURL.setParameter("redirect", getRedirect());
		portletURL.setParameter(
			"fragmentCollectionId", String.valueOf(getFragmentCollectionId()));
		portletURL.setParameter(
			"fragmentEntryId", String.valueOf(getFragmentEntryId()));

		return portletURL;
	}

	public String getRedirect() {
		if (_redirect != null) {
			return _redirect;
		}

		_redirect = ParamUtil.getString(_renderRequest, "redirect");

		return _redirect;
	}

	public SearchContainer getSearchContainer() throws PortalException {
		if (_searchContainer != null) {
			return _searchContainer;
		}

		SearchContainer fragmentEntryLinksSearchContainer = new SearchContainer(
			_renderRequest, _renderResponse.createRenderURL(), null,
			"there-are-no-fragment-usages");

		fragmentEntryLinksSearchContainer.setRowChecker(
			new EmptyOnClickRowChecker(_renderResponse));

		OrderByComparator<FragmentEntryLink> orderByComparator = null;

		fragmentEntryLinksSearchContainer.setOrderByCol(getOrderByCol());
		fragmentEntryLinksSearchContainer.setOrderByComparator(
			orderByComparator);
		fragmentEntryLinksSearchContainer.setOrderByType(getOrderByType());

		List<FragmentEntryLink> fragmentEntryLinks = null;
		int fragmentEntryLinksCount = 0;

		FragmentEntry fragmentEntry = getFragmentEntry();

		if (Objects.equals(getNavigation(), "pages")) {
			fragmentEntryLinks =
				FragmentEntryLinkLocalServiceUtil.getFragmentEntryLinks(
					fragmentEntry.getGroupId(), getFragmentEntryId(),
					PortalUtil.getClassNameId(Layout.class),
					fragmentEntryLinksSearchContainer.getStart(),
					fragmentEntryLinksSearchContainer.getEnd(),
					orderByComparator);

			fragmentEntryLinksCount =
				FragmentEntryLinkLocalServiceUtil.getFragmentEntryLinksCount(
					fragmentEntry.getGroupId(), getFragmentEntryId(),
					PortalUtil.getClassNameId(Layout.class));
		}
		else if (Objects.equals(getNavigation(), "page-templates")) {
			fragmentEntryLinks =
				FragmentEntryLinkLocalServiceUtil.getFragmentEntryLinks(
					fragmentEntry.getGroupId(), getFragmentEntryId(),
					PortalUtil.getClassNameId(LayoutPageTemplateEntry.class),
					LayoutPageTemplateEntryTypeConstants.TYPE_BASIC,
					fragmentEntryLinksSearchContainer.getStart(),
					fragmentEntryLinksSearchContainer.getEnd(),
					orderByComparator);

			fragmentEntryLinksCount =
				FragmentEntryLinkLocalServiceUtil.getFragmentEntryLinksCount(
					fragmentEntry.getGroupId(), getFragmentEntryId(),
					PortalUtil.getClassNameId(LayoutPageTemplateEntry.class),
					LayoutPageTemplateEntryTypeConstants.TYPE_BASIC);
		}
		else if (Objects.equals(getNavigation(), "display-pages")) {
			fragmentEntryLinks =
				FragmentEntryLinkLocalServiceUtil.getFragmentEntryLinks(
					fragmentEntry.getGroupId(), getFragmentEntryId(),
					PortalUtil.getClassNameId(LayoutPageTemplateEntry.class),
					LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE,
					fragmentEntryLinksSearchContainer.getStart(),
					fragmentEntryLinksSearchContainer.getEnd(),
					orderByComparator);

			fragmentEntryLinksCount =
				FragmentEntryLinkLocalServiceUtil.getFragmentEntryLinksCount(
					fragmentEntry.getGroupId(), getFragmentEntryId(),
					PortalUtil.getClassNameId(LayoutPageTemplateEntry.class),
					LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE);
		}
		else {
			fragmentEntryLinks =
				FragmentEntryLinkLocalServiceUtil.getFragmentEntryLinks(
					fragmentEntry.getGroupId(), getFragmentEntryId(),
					fragmentEntryLinksSearchContainer.getStart(),
					fragmentEntryLinksSearchContainer.getEnd(),
					orderByComparator);

			fragmentEntryLinksCount =
				FragmentEntryLinkLocalServiceUtil.getFragmentEntryLinksCount(
					fragmentEntry.getGroupId(), getFragmentEntryId());
		}

		fragmentEntryLinksSearchContainer.setResults(fragmentEntryLinks);
		fragmentEntryLinksSearchContainer.setTotal(fragmentEntryLinksCount);

		_searchContainer = fragmentEntryLinksSearchContainer;

		return _searchContainer;
	}

	public List<ViewTypeItem> getViewTypeItems() {
		return new ViewTypeItemList(getPortletURL(), "list") {
			{
				addTableViewTypeItem();
			}
		};
	}

	private List<DropdownItem> _getOrderByDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setActive(true);
						dropdownItem.setHref(
							getPortletURL(), "orderByCol", "last-propagation");
						dropdownItem.setLabel(
							LanguageUtil.get(_request, "last-propagation"));
					});
			}
		};
	}

	private Long _fragmentCollectionId;
	private FragmentEntry _fragmentEntry;
	private Long _fragmentEntryId;
	private String _keywords;
	private String _navigation;
	private String _orderByCol;
	private String _orderByType;
	private String _redirect;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final HttpServletRequest _request;
	private SearchContainer _searchContainer;

}