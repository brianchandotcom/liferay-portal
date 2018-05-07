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

package com.liferay.asset.display.page.item.selector.web.internal.display.context;

import com.liferay.asset.display.contributor.AssetDisplayContributor;
import com.liferay.asset.display.contributor.AssetDisplayContributorTracker;
import com.liferay.asset.display.page.item.selector.criterion.AssetDisplayPageSelectorCriterion;
import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.ClassType;
import com.liferay.asset.kernel.model.ClassTypeReader;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.SafeConsumer;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItemList;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class AssetDisplayPagesItemSelectorViewDisplayContext {

	public AssetDisplayPagesItemSelectorViewDisplayContext(
		HttpServletRequest request,
		AssetDisplayContributorTracker assetDisplayContributorTracker,
		AssetDisplayPageSelectorCriterion assetDisplayPageSelectorCriterion,
		String itemSelectedEventName, PortletURL portletURL) {

		_request = request;
		_assetDisplayContributorTracker = assetDisplayContributorTracker;
		_assetDisplayPageSelectorCriterion = assetDisplayPageSelectorCriterion;
		_itemSelectedEventName = itemSelectedEventName;
		_portletURL = portletURL;

		_portletRequest = (PortletRequest)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);
		_portletResponse = (PortletResponse)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE);
		_themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public SearchContainer getAssetDisplayPageSearchContainer()
		throws PortalException {

		if (_assetDisplayPageSearchContainer != null) {
			return _assetDisplayPageSearchContainer;
		}

		RenderRequest renderRequest = (RenderRequest)_portletRequest;

		RenderResponse renderResponse = (RenderResponse)_portletResponse;

		SearchContainer assetDisplayPageSearchContainer = new SearchContainer(
			renderRequest, renderResponse.createRenderURL(), null,
			"there-are-no-display-pages");

		List<LayoutPageTemplateEntry> layoutPageTemplateEntries =
			LayoutPageTemplateEntryServiceUtil.getLayoutPageTemplateEntries(
				_themeDisplay.getScopeGroupId(),
				_assetDisplayPageSelectorCriterion.getClassNameId(),
				_assetDisplayPageSelectorCriterion.getClassTypeId(),
				LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE);

		int layoutPageTemplateEntriesCount =
			LayoutPageTemplateEntryServiceUtil.
				getLayoutPageTemplateEntriesCount(
					_themeDisplay.getScopeGroupId(),
					_assetDisplayPageSelectorCriterion.getClassNameId(),
					_assetDisplayPageSelectorCriterion.getClassTypeId(),
					LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE);

		assetDisplayPageSearchContainer.setResults(layoutPageTemplateEntries);
		assetDisplayPageSearchContainer.setTotal(
			layoutPageTemplateEntriesCount);

		_assetDisplayPageSearchContainer = assetDisplayPageSearchContainer;

		return _assetDisplayPageSearchContainer;
	}

	public String getDisplayStyle() {
		if (Validator.isNotNull(_displayStyle)) {
			return _displayStyle;
		}

		_displayStyle = ParamUtil.getString(
			_portletRequest, "displayStyle", "icon");

		return _displayStyle;
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
			}
		};
	}

	public String getItemSelectedEventName() {
		return _itemSelectedEventName;
	}

	public String getSubtypeLabel(
			LayoutPageTemplateEntry layoutPageTemplateEntry)
		throws PortalException {

		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				layoutPageTemplateEntry.getClassName());

		if ((assetRendererFactory == null) ||
			(layoutPageTemplateEntry.getClassTypeId() <= 0)) {

			return StringPool.BLANK;
		}

		ClassTypeReader classTypeReader =
			assetRendererFactory.getClassTypeReader();

		ClassType classType = classTypeReader.getClassType(
			layoutPageTemplateEntry.getClassTypeId(),
			_themeDisplay.getLocale());

		return classType.getName();
	}

	public int getTotalItems() throws Exception {
		SearchContainer assetDisplayPageSearchContainer =
			getAssetDisplayPageSearchContainer();

		return assetDisplayPageSearchContainer.getTotal();
	}

	public String getTypeLabel(
		LayoutPageTemplateEntry layoutPageTemplateEntry) {

		AssetDisplayContributor assetDisplayContributor =
			_assetDisplayContributorTracker.getAssetDisplayContributor(
				layoutPageTemplateEntry.getClassName());

		if (assetDisplayContributor == null) {
			return StringPool.BLANK;
		}

		return assetDisplayContributor.getLabel(_themeDisplay.getLocale());
	}

	public List<ViewTypeItem> getViewTypeItems() throws PortletException {
		return new ViewTypeItemList(_getPortletURL(), getDisplayStyle()) {
			{
				addCardViewTypeItem();
			}
		};
	}

	private List<DropdownItem> _getFilterNavigationDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					SafeConsumer.ignore(
						dropdownItem -> {
							dropdownItem.setActive(true);
							dropdownItem.setHref(_getPortletURL());
							dropdownItem.setLabel(
								LanguageUtil.get(_request, "all"));
						}));
			}
		};
	}

	private PortletURL _getPortletURL() throws PortletException {
		return PortletURLUtil.clone(
			_portletURL,
			PortalUtil.getLiferayPortletResponse(_portletResponse));
	}

	private final AssetDisplayContributorTracker
		_assetDisplayContributorTracker;
	private SearchContainer _assetDisplayPageSearchContainer;
	private final AssetDisplayPageSelectorCriterion
		_assetDisplayPageSelectorCriterion;
	private String _displayStyle;
	private final String _itemSelectedEventName;
	private final PortletRequest _portletRequest;
	private final PortletResponse _portletResponse;
	private final PortletURL _portletURL;
	private final HttpServletRequest _request;
	private final ThemeDisplay _themeDisplay;

}