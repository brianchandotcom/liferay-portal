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

package com.liferay.asset.list.item.selector.web.internal;

import com.liferay.asset.list.item.selector.AssetListItemSelectorReturnType;
import com.liferay.asset.list.item.selector.criterion.AssetListItemSelectorCriterion;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryService;
import com.liferay.asset.list.util.AssetListPortletUtil;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.item.selector.ItemSelectorViewDescriptorRenderer;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = ItemSelectorView.class)
public class AssetListItemSelectorView
	implements ItemSelectorView<AssetListItemSelectorCriterion> {

	@Override
	public Class<? extends AssetListItemSelectorCriterion>
		getItemSelectorCriterionClass() {

		return AssetListItemSelectorCriterion.class;
	}

	@Override
	public List<ItemSelectorReturnType> getSupportedItemSelectorReturnTypes() {
		return _supportedItemSelectorReturnTypes;
	}

	@Override
	public String getTitle(Locale locale) {
		return _language.get(locale, "asset-list");
	}

	@Override
	public boolean isVisible(ThemeDisplay themeDisplay) {
		return true;
	}

	@Override
	public void renderHTML(
			ServletRequest servletRequest, ServletResponse servletResponse,
			AssetListItemSelectorCriterion itemSelectorCriterion,
			PortletURL portletURL, String itemSelectedEventName, boolean search)
		throws IOException, ServletException {

		_itemSelectorViewDescriptorRenderer.renderHTML(
			servletRequest, servletResponse, itemSelectorCriterion, portletURL,
			itemSelectedEventName, search,
			new AssetListItemSelectorViewDescriptor(
				(HttpServletRequest)servletRequest, portletURL));
	}

	private static final List<ItemSelectorReturnType>
		_supportedItemSelectorReturnTypes = Collections.singletonList(
			new AssetListItemSelectorReturnType());

	@Reference
	private AssetListEntryService _assetListEntryService;

	@Reference
	private ItemSelectorViewDescriptorRenderer<AssetListItemSelectorCriterion>
		_itemSelectorViewDescriptorRenderer;

	@Reference
	private Language _language;

	@Reference(target = "(bundle.symbolic.name=com.liferay.asset.list.web)")
	private ResourceBundleLoader _resourceBundleLoader;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.asset.list.item.selector.web)"
	)
	private ServletContext _servletContext;

	private class AssetListItemSelectorViewDescriptor
		implements ItemSelectorViewDescriptor<AssetListEntry> {

		public AssetListItemSelectorViewDescriptor(
			HttpServletRequest httpServletRequest, PortletURL portletURL) {

			_httpServletRequest = httpServletRequest;
			_portletURL = portletURL;
		}

		@Override
		public ItemDescriptor getItemDescriptor(AssetListEntry assetListEntry) {
			return new ItemDescriptor() {

				@Override
				public String getIcon() {
					return "list";
				}

				@Override
				public String getImageURL() {
					return StringPool.BLANK;
				}

				@Override
				public String getPayload() {
					return JSONUtil.put(
						"assetListEntryId", assetListEntry.getAssetListEntryId()
					).put(
						"assetListEntryTitle", assetListEntry.getTitle()
					).toString();
				}

				@Override
				public String getSubtitle(Locale locale) {
					ResourceBundle resourceBundle =
						_resourceBundleLoader.loadResourceBundle(locale);

					return _language.get(
						resourceBundle, assetListEntry.getTypeLabel());
				}

				@Override
				public String getTitle(Locale locale) {
					try {
						return assetListEntry.getUnambiguousTitle(locale);
					}
					catch (PortalException portalException) {
						return ReflectionUtil.throwException(portalException);
					}
				}

			};
		}

		@Override
		public ItemSelectorReturnType getItemSelectorReturnType() {
			return new AssetListItemSelectorReturnType();
		}

		@Override
		public String[] getOrderByKeys() {
			return new String[] {"title", "create-date"};
		}

		@Override
		public SearchContainer getSearchContainer() throws PortalException {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)_httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			PortletRequest portletRequest =
				(PortletRequest)_httpServletRequest.getAttribute(
					JavaConstants.JAVAX_PORTLET_REQUEST);

			ResourceBundle resourceBundle =
				_resourceBundleLoader.loadResourceBundle(
					themeDisplay.getLocale());

			SearchContainer<AssetListEntry> searchContainer =
				new SearchContainer<>(
					portletRequest, _portletURL, null,
					_language.get(resourceBundle, "there-are-no-content-sets"));

			String orderByCol = ParamUtil.getString(
				_httpServletRequest, "orderByCol", "create-date");

			String orderByType = ParamUtil.getString(
				_httpServletRequest, "orderByType", "asc");

			OrderByComparator<AssetListEntry> orderByComparator =
				AssetListPortletUtil.getAssetListEntryOrderByComparator(
					orderByCol, orderByType);

			searchContainer.setOrderByCol(orderByCol);
			searchContainer.setOrderByComparator(orderByComparator);
			searchContainer.setOrderByType(orderByType);

			String keywords = ParamUtil.getString(
				_httpServletRequest, "keywords");

			List<AssetListEntry> assetListEntries = null;
			int assetListEntriesCount = 0;

			if (Validator.isNotNull(keywords)) {
				assetListEntries = _assetListEntryService.getAssetListEntries(
					PortalUtil.getCurrentAndAncestorSiteGroupIds(
						themeDisplay.getScopeGroupId()),
					keywords, searchContainer.getStart(),
					searchContainer.getEnd(),
					searchContainer.getOrderByComparator());

				assetListEntriesCount =
					_assetListEntryService.getAssetListEntriesCount(
						PortalUtil.getCurrentAndAncestorSiteGroupIds(
							themeDisplay.getScopeGroupId()),
						keywords);
			}
			else {
				assetListEntries = _assetListEntryService.getAssetListEntries(
					PortalUtil.getCurrentAndAncestorSiteGroupIds(
						themeDisplay.getScopeGroupId()),
					searchContainer.getStart(), searchContainer.getEnd(),
					searchContainer.getOrderByComparator());

				assetListEntriesCount =
					_assetListEntryService.getAssetListEntriesCount(
						PortalUtil.getCurrentAndAncestorSiteGroupIds(
							themeDisplay.getScopeGroupId()));
			}

			searchContainer.setResults(assetListEntries);
			searchContainer.setTotal(assetListEntriesCount);

			return searchContainer;
		}

		@Override
		public boolean isShowBreadcrumb() {
			return true;
		}

		@Override
		public boolean isShowManagementToolbar() {
			return true;
		}

		private final HttpServletRequest _httpServletRequest;
		private final PortletURL _portletURL;

	}

}