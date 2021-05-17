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

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.ClassType;
import com.liferay.asset.kernel.model.ClassTypeReader;
import com.liferay.asset.list.constants.AssetListPortletKeys;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryService;
import com.liferay.asset.list.util.AssetListPortletUtil;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.item.selector.ItemSelectorViewDescriptorRenderer;
import com.liferay.item.selector.PortletItemSelectorView;
import com.liferay.item.selector.criteria.InfoListItemSelectorReturnType;
import com.liferay.item.selector.criteria.info.item.criterion.InfoListItemSelectorCriterion;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.Collections;
import java.util.Date;
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
public class AssetListPortletItemSelectorView
	implements PortletItemSelectorView<InfoListItemSelectorCriterion> {

	@Override
	public Class<? extends InfoListItemSelectorCriterion>
		getItemSelectorCriterionClass() {

		return InfoListItemSelectorCriterion.class;
	}

	@Override
	public List<String> getPortletIds() {
		return Collections.singletonList(AssetListPortletKeys.ASSET_LIST);
	}

	@Override
	public List<ItemSelectorReturnType> getSupportedItemSelectorReturnTypes() {
		return _supportedItemSelectorReturnTypes;
	}

	@Override
	public String getTitle(Locale locale) {
		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(locale);

		return _language.get(resourceBundle, "collections");
	}

	@Override
	public void renderHTML(
			ServletRequest servletRequest, ServletResponse servletResponse,
			InfoListItemSelectorCriterion infoListItemSelectorCriterion,
			PortletURL portletURL, String itemSelectedEventName, boolean search)
		throws IOException, ServletException {

		_itemSelectorViewDescriptorRenderer.renderHTML(
			servletRequest, servletResponse, infoListItemSelectorCriterion,
			portletURL, itemSelectedEventName, search,
			new AssetListItemSelectorViewDescriptor(
				(HttpServletRequest)servletRequest,
				infoListItemSelectorCriterion, portletURL));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetListPortletItemSelectorView.class);

	private static final List<ItemSelectorReturnType>
		_supportedItemSelectorReturnTypes = Collections.singletonList(
			new InfoListItemSelectorReturnType());

	@Reference
	private AssetListEntryService _assetListEntryService;

	@Reference
	private ItemSelectorViewDescriptorRenderer<InfoListItemSelectorCriterion>
		_itemSelectorViewDescriptorRenderer;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference(target = "(bundle.symbolic.name=com.liferay.asset.list.web)")
	private ResourceBundleLoader _resourceBundleLoader;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.asset.list.item.selector.web)"
	)
	private ServletContext _servletContext;

	private class AssetListItemSelectorViewDescriptor
		implements ItemSelectorViewDescriptor<AssetListEntry> {

		public AssetListItemSelectorViewDescriptor(
			HttpServletRequest httpServletRequest,
			InfoListItemSelectorCriterion infoListItemSelectorCriterion,
			PortletURL portletURL) {

			_httpServletRequest = httpServletRequest;
			_infoListItemSelectorCriterion = infoListItemSelectorCriterion;
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
				public Date getModifiedDate() {
					return assetListEntry.getModifiedDate();
				}

				@Override
				public String getPayload() {
					return JSONUtil.put(
						"classNameId",
						_portal.getClassNameId(AssetListEntry.class)
					).put(
						"classPK", assetListEntry.getAssetListEntryId()
					).put(
						"itemSubtype", assetListEntry.getAssetEntrySubtype()
					).put(
						"itemType", assetListEntry.getAssetEntryType()
					).put(
						"title", assetListEntry.getTitle()
					).toString();
				}

				@Override
				public String getSubtitle(Locale locale) {
					String subtitle = ResourceActionsUtil.getModelResource(
						locale, assetListEntry.getAssetEntryType());

					if (Validator.isNull(
							assetListEntry.getAssetEntrySubtype())) {

						return subtitle;
					}

					String subtypeLabel = _getAssetEntrySubtypeSubtypeLabel();

					if (Validator.isNull(subtypeLabel)) {
						return subtitle;
					}

					return subtitle + " - " + subtypeLabel;
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

				@Override
				public long getUserId() {
					return assetListEntry.getUserId();
				}

				@Override
				public String getUserName() {
					return assetListEntry.getUserName();
				}

				private String _getAssetEntrySubtypeSubtypeLabel() {
					long classTypeId = GetterUtil.getLong(
						assetListEntry.getAssetEntrySubtype(), -1);

					if (classTypeId < 0) {
						return StringPool.BLANK;
					}

					AssetRendererFactory<?> assetRendererFactory =
						AssetRendererFactoryRegistryUtil.
							getAssetRendererFactoryByClassName(
								assetListEntry.getAssetEntryType());

					if ((assetRendererFactory == null) ||
						!assetRendererFactory.isSupportsClassTypes()) {

						return StringPool.BLANK;
					}

					ClassTypeReader classTypeReader =
						assetRendererFactory.getClassTypeReader();

					ThemeDisplay themeDisplay =
						(ThemeDisplay)_httpServletRequest.getAttribute(
							WebKeys.THEME_DISPLAY);

					try {
						ClassType classType = classTypeReader.getClassType(
							classTypeId, themeDisplay.getLocale());

						return classType.getName();
					}
					catch (PortalException portalException) {
						if (_log.isDebugEnabled()) {
							_log.debug(portalException, portalException);
						}
					}

					return StringPool.BLANK;
				}

			};
		}

		@Override
		public ItemSelectorReturnType getItemSelectorReturnType() {
			return new InfoListItemSelectorReturnType();
		}

		@Override
		public String[] getOrderByKeys() {
			return new String[] {"title", "create-date"};
		}

		@Override
		public SearchContainer<AssetListEntry> getSearchContainer()
			throws PortalException {

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
					_language.get(resourceBundle, "there-are-no-collections"));

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

			List<String> itemTypes =
				_infoListItemSelectorCriterion.getItemTypes();

			if (ListUtil.isEmpty(itemTypes)) {
				if (Validator.isNotNull(keywords)) {
					assetListEntries =
						_assetListEntryService.getAssetListEntries(
							themeDisplay.getScopeGroupId(), keywords,
							searchContainer.getStart(),
							searchContainer.getEnd(),
							searchContainer.getOrderByComparator());

					assetListEntriesCount =
						_assetListEntryService.getAssetListEntriesCount(
							themeDisplay.getScopeGroupId(), keywords);
				}
				else {
					assetListEntries =
						_assetListEntryService.getAssetListEntries(
							themeDisplay.getScopeGroupId(),
							searchContainer.getStart(),
							searchContainer.getEnd(),
							searchContainer.getOrderByComparator());

					assetListEntriesCount =
						_assetListEntryService.getAssetListEntriesCount(
							themeDisplay.getScopeGroupId());
				}
			}
			else if (Validator.isNull(
						_infoListItemSelectorCriterion.getItemSubtype())) {

				if (Validator.isNotNull(keywords)) {
					assetListEntries =
						_assetListEntryService.getAssetListEntries(
							new long[] {themeDisplay.getScopeGroupId()},
							keywords, itemTypes.toArray(new String[0]),
							searchContainer.getStart(),
							searchContainer.getEnd(),
							searchContainer.getOrderByComparator());

					assetListEntriesCount =
						_assetListEntryService.getAssetListEntriesCount(
							new long[] {themeDisplay.getScopeGroupId()},
							keywords, itemTypes.toArray(new String[0]));
				}
				else {
					assetListEntries =
						_assetListEntryService.getAssetListEntries(
							new long[] {themeDisplay.getScopeGroupId()},
							itemTypes.toArray(new String[0]),
							searchContainer.getStart(),
							searchContainer.getEnd(),
							searchContainer.getOrderByComparator());

					assetListEntriesCount =
						_assetListEntryService.getAssetListEntriesCount(
							new long[] {themeDisplay.getScopeGroupId()},
							itemTypes.toArray(new String[0]));
				}
			}
			else {
				if (Validator.isNotNull(keywords)) {
					assetListEntries =
						_assetListEntryService.getAssetListEntries(
							new long[] {themeDisplay.getScopeGroupId()},
							keywords,
							_infoListItemSelectorCriterion.getItemSubtype(),
							_infoListItemSelectorCriterion.getItemType(),
							searchContainer.getStart(),
							searchContainer.getEnd(),
							searchContainer.getOrderByComparator());

					assetListEntriesCount =
						_assetListEntryService.getAssetListEntriesCount(
							new long[] {themeDisplay.getScopeGroupId()},
							keywords,
							_infoListItemSelectorCriterion.getItemSubtype(),
							_infoListItemSelectorCriterion.getItemType());
				}
				else {
					assetListEntries =
						_assetListEntryService.getAssetListEntries(
							new long[] {themeDisplay.getScopeGroupId()},
							_infoListItemSelectorCriterion.getItemSubtype(),
							_infoListItemSelectorCriterion.getItemType(),
							searchContainer.getStart(),
							searchContainer.getEnd(),
							searchContainer.getOrderByComparator());

					assetListEntriesCount =
						_assetListEntryService.getAssetListEntriesCount(
							new long[] {themeDisplay.getScopeGroupId()},
							_infoListItemSelectorCriterion.getItemSubtype(),
							_infoListItemSelectorCriterion.getItemType());
				}
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

		@Override
		public boolean isShowSearch() {
			return true;
		}

		private final HttpServletRequest _httpServletRequest;
		private final InfoListItemSelectorCriterion
			_infoListItemSelectorCriterion;
		private final PortletURL _portletURL;

	}

}